package es.enxenio.sife1701.model.configuracion;

import es.enxenio.sife1701.model.generic.GenericService;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface ConfiguracionService extends GenericService<Configuracion> {

    // Consulta

    Configuracion get();

}
