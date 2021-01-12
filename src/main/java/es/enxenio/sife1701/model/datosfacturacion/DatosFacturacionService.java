package es.enxenio.sife1701.model.datosfacturacion;

import es.enxenio.sife1701.model.generic.GenericService;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface DatosFacturacionService extends GenericService<DatosFacturacion> {

    // Consulta

    DatosFacturacion getByUsuarioEmail(String email);

    // Gestión

    void saveDatosFacturacionDeUsuario(DatosFacturacion datosFacturacion, String email);

}
