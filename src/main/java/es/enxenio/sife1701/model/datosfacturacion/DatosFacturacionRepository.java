package es.enxenio.sife1701.model.datosfacturacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface DatosFacturacionRepository extends JpaRepository<DatosFacturacion, Long> {

    @Query(value = "SELECT d FROM Usuario u JOIN u.datosFacturacion d WHERE u.email = :email")
    DatosFacturacion getByUsuarioEmail(@Param("email") String email);

}
