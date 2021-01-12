package es.enxenio.sife1701.model.nivel;

import es.enxenio.sife1701.model.generic.GenericService;

import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface NivelService extends GenericService<Nivel> {

    List<Nivel> query();

}
