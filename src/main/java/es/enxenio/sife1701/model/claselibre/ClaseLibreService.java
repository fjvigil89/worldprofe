package es.enxenio.sife1701.model.claselibre;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.generic.GenericService;

import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface ClaseLibreService extends GenericService<ClaseLibre> {

    List<ClaseLibre> query();

    void duplicarSemana(PageableFilter filter);

}
