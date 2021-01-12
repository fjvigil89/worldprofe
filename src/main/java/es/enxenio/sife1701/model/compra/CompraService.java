package es.enxenio.sife1701.model.compra;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.controller.custom.util.Query;
import es.enxenio.sife1701.model.exceptions.CompraException;
import es.enxenio.sife1701.model.exceptions.UnauthorizedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;


public interface CompraService {

    // Consulta

    Compra get(Long compraId);

    Page<Compra> findAll(Query query, Pageable pageable);

    Page<Compra> findAllByUsuarioActivo(Query query, Pageable pageable);

    Page<Compra> findAllByUsuario(PageableFilter filter);

    List<Compra> listarComprasDeUsuario(long usuarioId);

    // Gestión

    Compra crearCompra(Long paqueteCreditosId, boolean solicitarFactura) throws UnauthorizedException;

    String iniciarCompraPaypal(Long compraId, String baseUrl);

    String iniciarCompraPaypalAlumno(String baseUrl, Long compraId);

    Compra confirmarCompraPaypal(Long compraId, String paymentId, String token) throws CompraException;

    void cancelar(Long compraId);

    void sendEmails(Compra compra, String baseUrl, Locale locale);

}
