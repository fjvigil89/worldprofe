package es.enxenio.sife1701.model.compra;

import es.enxenio.sife1701.config.util.SecurityUtils;
import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.controller.custom.util.Query;
import es.enxenio.sife1701.model.configuracion.Configuracion;
import es.enxenio.sife1701.model.configuracion.ConfiguracionService;
import es.enxenio.sife1701.model.exceptions.CompraException;
import es.enxenio.sife1701.model.exceptions.UnauthorizedException;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditos;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditosRepository;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.empresa.Empresa;
import es.enxenio.sife1701.model.usuario.exception.LoginAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import es.enxenio.sife1701.util.EurosUtil;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Locale;

/**
 * Created by crodriguez on 05/10/2016.
 */
@Service
@Transactional
public class CompraServiceImpl implements CompraService {

    private static final Logger log = LoggerFactory.getLogger(CompraServiceImpl.class);

    @Inject
    private CompraRepository compraRepository;

    @Inject
    private PaqueteCreditosRepository paqueteCreditosRepository;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private PaypalService paypalService;

    @Inject
    private ConfiguracionService configuracionService;

    @Inject
    private MailService mailService;

    // Callback

    private static String getUrlCompraCorrecta(String base, Compra compra) {
        return base.concat("/#/compra/confirmar-paypal?compraId=").concat(String.valueOf(compra.getId()));
    }

    private static String getUrlCancelacion(String base, Compra compra) {
        return base.concat("/#/compra/cancelar?compraId=").concat(String.valueOf(compra.getId()));
    }

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Compra get(Long compraId) {
        return compraRepository.findOne(compraId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Compra> findAll(Query query, Pageable pageable) {
        return compraRepository.filter(null, query.getValue(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Compra> findAllByUsuarioActivo(Query query, Pageable pageable) {
        Usuario usuario = usuarioService.findByEmail(SecurityUtils.getCurrentUserEmail());
        return compraRepository.filter(usuario.getId(), query.getValue(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Compra> findAllByUsuario(PageableFilter filter) {
        return compraRepository.filter(filter.getId(), filter.getQuery().getValue(), filter.getPageable());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> listarComprasDeUsuario(long usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }

    // Gestión

    @Override
    public Compra crearCompra(Long paqueteCreditosId, boolean solicitarFactura) throws UnauthorizedException {
        Usuario usuario = usuarioService.findByEmail(SecurityUtils.getCurrentUserEmail());
        if (usuario instanceof Profesor || usuario instanceof Empresa
            || (usuario instanceof Alumno && ((Alumno) usuario).getEmpresa() != null)) {
            // Los profesores, empresas y alumnos de empresas no pueden realizar compras
            throw new UnauthorizedException();
        }
        PaqueteCreditos paqueteCreditos = paqueteCreditosRepository.findOne(paqueteCreditosId);
        Configuracion configuracion = configuracionService.get();
        Compra compra = new Compra(paqueteCreditos.getCreditos(), EurosUtil.aplicarDescuento(paqueteCreditos.getCoste(), configuracion.getIva()),
            paqueteCreditos.getCoste(), configuracion.getIva(), solicitarFactura, paqueteCreditos, usuario);
        return compraRepository.save(compra);
    }

    @Override
    public String iniciarCompraPaypal(Long compraId, String baseUrl) {
        Compra compra = get(compraId);
        return paypalService.iniciarTransaccionCompra(compra, getUrlCompraCorrecta(baseUrl, compra), getUrlCancelacion(baseUrl, compra));
    }

    @Override
    public String iniciarCompraPaypalAlumno(String baseUrl, Long compraId) {
        Compra compra = get(compraId);
        return paypalService.iniciarTransaccionCompra(compra, getUrlCompraCorrecta(baseUrl, compra), getUrlCancelacion(baseUrl, compra));
    }

    @Override
    public Compra confirmarCompraPaypal(Long compraId, String paymentId, String token) throws CompraException {
        Compra compra = compraRepository.findByPaypalPaymentId(paymentId);
        if (compra.getUsuario() instanceof Alumno) {
            Alumno alumno = (Alumno) usuarioService.get(compra.getUsuario().getId());
            alumno.setCreditosDisponibles(alumno.getCreditosDisponibles() + compra.getCreditos());
            alumno.setCreditosTotales(alumno.getCreditosTotales() + compra.getCreditos());
            try {
                usuarioService.update(alumno);
            } catch (InstanceNotFoundException e) {
                e.printStackTrace();
            } catch (LoginAlreadyUsedException e) {
                e.printStackTrace();
            }
        }
        paypalService.finalizarTransaccionCompra(compra, paymentId);
        return finalizarCompra(compra);
    }

    private Compra finalizarCompra(Compra compra) {
        compra.setRealizada(true);
        compraRepository.save(compra);
        return compra;
    }

    @Override
    public void cancelar(Long compraId) {
        compraRepository.delete(compraId);
    }

    @Override
    public void sendEmails(Compra compra, String baseUrl, Locale locale) {
        try {
            // Confirmación de la compra
            mailService.sendConfirmacionCompraEmail(compra, baseUrl, locale);
            // Notificación al administrador para que le genere una factura
            String emailAdmin = configuracionService.get().getCorreoNotificaciones();
            mailService.sendAdminConfirmacionCompraEmail(compra, emailAdmin, baseUrl, locale);
        } catch (EnvioEmailException e) {
            log.warn("Error al enviar correos de compra", e);
        }
    }

}
