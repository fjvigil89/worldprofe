package es.enxenio.sife1701.controller.publica;

import es.enxenio.sife1701.config.util.SecurityUtils;
import es.enxenio.sife1701.model.datosfacturacion.DatosFacturacion;
import es.enxenio.sife1701.model.datosfacturacion.DatosFacturacionService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by crodriguez on 21/11/2018.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/datosfacturacion")
@PreAuthorize(ConstantesRest.IS_AUTHENTICATED)
public class DatosFacturacionController {

    @Inject
    private DatosFacturacionService datosFacturacionService;

    @RequestMapping(value = "/getDatosFacturacionDeUsuario", method = RequestMethod.GET)
    public ResponseEntity<DatosFacturacion> getDatosFacturacionDeUsuario() {

        return new ResponseEntity<>(datosFacturacionService.getByUsuarioEmail(SecurityUtils.getCurrentUserEmail()), HttpStatus.OK);
    }

    // Gestión

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DatosFacturacion> saveDatosFacturacion(@Valid @RequestBody DatosFacturacion datosFacturacion) {

        String usuarioAutenticado = SecurityUtils.getCurrentUserEmail();
        datosFacturacionService.saveDatosFacturacionDeUsuario(datosFacturacion, usuarioAutenticado);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
