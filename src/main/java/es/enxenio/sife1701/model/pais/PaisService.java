package es.enxenio.sife1701.model.pais;

import es.enxenio.sife1701.model.generic.GenericService;

import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface PaisService extends GenericService<Pais> {

    List<Pais> query(String filter);

    List<Pais> getAllWithProfesores();

}
