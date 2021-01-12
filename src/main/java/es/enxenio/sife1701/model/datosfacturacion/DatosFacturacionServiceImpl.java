package es.enxenio.sife1701.model.datosfacturacion;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

/**
 * Created by jlosa on 25/08/2017.
 */
@Service
@Transactional
public class DatosFacturacionServiceImpl implements DatosFacturacionService {

    @Inject
    private DatosFacturacionRepository datosFacturacionRepository;

    @Inject
    private UsuarioService usuarioService;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public DatosFacturacion get(Long id) {
        return datosFacturacionRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DatosFacturacion> filter(PageableFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public DatosFacturacion getByUsuarioEmail(String email) {
        return datosFacturacionRepository.getByUsuarioEmail(email);
    }

    // Gestión

    @Override
    public DatosFacturacion save(DatosFacturacion datosFacturacion) throws InstanceAlreadyExistsException {
        return datosFacturacionRepository.save(datosFacturacion);
    }

    @Override
    public void saveDatosFacturacionDeUsuario(DatosFacturacion datosFacturacion, String email) {
        // Guardamos los datos de facturación y los asociamos al usuario
        Usuario usuario = usuarioService.findByEmail(email);
        usuario.setDatosFacturacion(datosFacturacionRepository.save(datosFacturacion));
    }

    @Override
    public DatosFacturacion update(DatosFacturacion datosFacturacion) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        return save(datosFacturacion);
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        datosFacturacionRepository.delete(id);
    }

}
