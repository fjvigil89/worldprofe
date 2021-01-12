package es.enxenio.sife1701.controller.publica;

import es.enxenio.sife1701.model.configuracion.Configuracion;
import es.enxenio.sife1701.model.configuracion.ConfiguracionService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/configuracion")
public class ConfiguracionController {

    @Inject
    private ConfiguracionService configuracionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Configuracion get() {
        // Se ignora el ID. Sólo se almacena 1 fila en la tabla configuración
        return configuracionService.get();
    }

}
