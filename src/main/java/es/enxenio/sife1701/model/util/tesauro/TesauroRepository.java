package es.enxenio.sife1701.model.util.tesauro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Métodos genéricos de los tesauros que tienen ID.
 * Created by crodriguez on 31/05/2016.
 */
@NoRepositoryBean
public interface TesauroRepository<T extends Tesauro, U extends Serializable> extends JpaRepository<T, U> {

    @Query(value = "SELECT t FROM #{#entityName} t " +
        " WHERE lower(unaccent(t.nombre)) like lower(unaccent(concat('%', :nombre, '%')))" +
        " ORDER BY t.nombre")
    List<T> filterByNombre(@Param(value = "nombre") String nombre);

    @Query(value = "SELECT t FROM #{#entityName} t " +
        " WHERE lower(unaccent(t.nombre)) like lower(unaccent(concat('%', :nombre, '%')))")
    Page<T> filterByNombre(@Param(value = "nombre") String nombre, Pageable pageable);

    T findByNombre(String nombre);

}
