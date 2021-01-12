package es.enxenio.sife1701.model.claselibre;

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
public interface ClaseLibreRepository extends JpaRepository<ClaseLibre, Long> {

    @Query(value = "SELECT cl FROM ClaseLibre cl WHERE :id = cl.profesor.id AND :fechaInicio <= cl.fecha AND :fechaFin >= cl.fecha ORDER BY cl.fecha")
    Page<ClaseLibre> filter(@Param("fechaInicio") ZonedDateTime fechaInicio,
                            @Param("fechaFin") ZonedDateTime fechaFin, @Param("id") Long id, Pageable pageable);

    @Query(value = "SELECT cl FROM ClaseLibre cl WHERE (:ocupada IS NULL OR :ocupada IS NOT NULL AND cl.ocupada IS :ocupada) " +
        "AND :id = cl.profesor.id AND :fechaInicio <= cl.fecha AND :fechaFin >= cl.fecha ORDER BY cl.fecha")
    Page<ClaseLibre> filter(@Param("fechaInicio") ZonedDateTime fechaInicio,
                         @Param("fechaFin") ZonedDateTime fechaFin, @Param("ocupada") Boolean ocupada, @Param("id") Long id, Pageable pageable);

    @Query(value = "SELECT cl FROM ClaseLibre cl WHERE cl.ocupada IS :ocupada " +
        "AND :id = cl.profesor.id AND :fechaInicio <= cl.fecha AND :fechaFin >= cl.fecha ORDER BY cl.fecha")
    List<ClaseLibre> findAll(@Param("fechaInicio") ZonedDateTime fechaInicio,
                            @Param("fechaFin") ZonedDateTime fechaFin, @Param("ocupada") Boolean ocupada, @Param("id") Long id);

    @Query(value = "SELECT cl FROM ClaseLibre cl WHERE cl.ocupada IS :ocupada " +
        "AND :id = cl.profesor.id AND :fechaInicio = cl.fecha")
    ClaseLibre find(@Param("fechaInicio") ZonedDateTime fechaInicio,
                            @Param("ocupada") Boolean ocupada, @Param("id") Long id);

    @Query(value = "SELECT cl FROM ClaseLibre cl WHERE cl.ocupada IS :ocupada " +
        "AND :id = cl.profesor.id ORDER BY cl.fecha")
    Page<ClaseLibre> filter(@Param("ocupada") Boolean ocupada, @Param("id") Long id, Pageable pageable);

    Boolean existsByProfesorIdAndFecha(Long profesorId, ZonedDateTime fecha);

}
