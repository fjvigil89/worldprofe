package es.enxenio.sife1701.controller.admin;

import es.enxenio.sife1701.controller.util.HeaderUtil;
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
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_ADMIN + "/datosfacturacion")
@PreAuthorize(ConstantesRest.HAS_ROLE_ADMIN)
public class DatosFacturacionAdminController {

    @Inject
    private DatosFacturacionService datosFacturacionService;

    // Gestión

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DatosFacturacion> saveOrUpdate(@Valid @RequestBody DatosFacturacion datosFacturacion) {
        try {
            if (datosFacturacion.getId() == null) {
                return new ResponseEntity<>(datosFacturacionService.save(datosFacturacion),
                    HeaderUtil.createAlert("admin.datosfacturacion.create.success"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(datosFacturacionService.update(datosFacturacion),
                    HeaderUtil.createAlert("admin.datosfacturacion.edit.success"), HttpStatus.OK);
            }
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("admin.form.error.alreadyexists"), HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
