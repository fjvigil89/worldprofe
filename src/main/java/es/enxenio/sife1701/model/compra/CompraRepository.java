package es.enxenio.sife1701.model.compra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query(value = "SELECT c FROM Compra c" +
        " WHERE str(c.id) like %:query% AND c.realizada = true AND (:usuarioId is null OR c.usuario.id = :usuarioId)")
    Page<Compra> filter(@Param("usuarioId") Long usuarioId, @Param("query") String query, Pageable pageable);

    List<Compra> findByUsuarioId(Long usuarioId);

    Compra findByPaypalPaymentId(String paymentId);

}
