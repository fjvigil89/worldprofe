package es.enxenio.sife1701.model.reserva;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    //FIXME: Refactorizar

    @Query(value = "SELECT r FROM Reserva r WHERE (" +
        "(:estado IS NOT NULL AND r.estado = :estado) OR (:estado IS NULL)) " +
        "AND ((:rol IS NOT NULL AND (" +
            "(:rol = 'ROLE_ALUMNO' AND r.alumno.id = :id) OR (:rol = 'ROLE_PROFESOR' AND :id = r.profesor.id)" +
        ")) OR :rol IS NULL) " +
        "AND ((:canceladasRechazadas IS FALSE AND r.estado IN ('SIN_CONTESTAR', 'CONFIRMADA', 'INCIDENCIA', 'PENDIENTE', 'REALIZADA')) " +
        "OR (:canceladasRechazadas IS TRUE AND r.estado IN ('NO_CONTESTADA', 'RECHAZADA', 'CANCELADA_POR_ALUMNO', 'CANCELADA_POR_PROFESOR'))) " +
        "AND :fechaInicio <= r.fecha AND :fechaFin >= r.fecha")
    Page<Reserva> filter(@Param("rol") String rol, @Param("fechaInicio") ZonedDateTime fechaInicio,
                         @Param("fechaFin") ZonedDateTime fechaFin, @Param("estado") Estado estado, @Param("id") Long id,
                         @Param("canceladasRechazadas") boolean canceladasRechazadas, Pageable pageable);

    @Query(value = "SELECT r FROM Reserva r WHERE (" +
        "(:estado IS NOT NULL AND r.estado = :estado) OR (:estado IS NULL)) " +
        "AND ((:rol IS NOT NULL AND (" +
        "(:rol = 'ROLE_ALUMNO' AND r.alumno.id = :id) OR (:rol = 'ROLE_PROFESOR' AND :id = r.profesor.id)" +
        ")) OR :rol IS NULL) " +
        "AND ((:canceladasRechazadas IS FALSE AND r.estado IN ('SIN_CONTESTAR', 'CONFIRMADA', 'INCIDENCIA', 'PENDIENTE', 'REALIZADA')) " +
        "OR (:canceladasRechazadas IS TRUE AND r.estado IN ('NO_CONTESTADA', 'RECHAZADA', 'CANCELADA_POR_ALUMNO', 'CANCELADA_POR_PROFESOR'))) ")
    Page<Reserva> filter(@Param("rol") String rol, @Param("estado") Estado estado, @Param("id") Long id,
                         @Param("canceladasRechazadas") boolean canceladasRechazadas, Pageable pageable);

    @Query(value = "SELECT r FROM Reserva r " +
        "WHERE ((:revisadas IS NOT NULL AND r.revisada = :revisadas) OR (:revisadas IS NULL)) " +
        "AND (:estado IS NULL OR r.estado = :estado)")
    Page<Reserva> filterByAdmin(@Param("estado") Estado estado, @Param("revisadas") Boolean revisadas, Pageable pageable);

    @Query(value = "SELECT r FROM Reserva r WHERE r.claseLibre.id = :id")

    List<Reserva> findByClaseLibre(@Param("id") Long id);    
    
    List<Reserva> findAllByEstado(Estado estado);

}
