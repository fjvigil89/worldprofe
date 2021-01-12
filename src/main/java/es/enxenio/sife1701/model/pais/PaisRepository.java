package es.enxenio.sife1701.model.pais;

import es.enxenio.sife1701.model.util.tesauro.TesauroRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface PaisRepository extends TesauroRepository<Pais, Long> {

    String findByCodigo(String codigo);

    @Query(value = "SELECT DISTINCT(pa) FROM Profesor pr JOIN pr.pais pa ORDER BY pa.nombre")
    List<Pais> getAllWithProfesores();

    @Query(value = "SELECT p FROM Pais p ORDER BY p.nombre")
    List<Pais> getAll();

}
