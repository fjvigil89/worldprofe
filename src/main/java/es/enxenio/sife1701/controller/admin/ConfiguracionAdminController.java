package es.enxenio.sife1701.controller.admin;

import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.model.configuracion.Configuracion;
import es.enxenio.sife1701.model.configuracion.ConfiguracionService;
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
@RequestMapping(ConstantesRest.URL_ADMIN + "/configuracion")
@PreAuthorize(ConstantesRest.HAS_ROLE_ADMIN)
public class ConfiguracionAdminController {

    @Inject
    private ConfiguracionService configuracionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Configuracion> saveOrUpdate(@Valid @RequestBody Configuracion configuracion) {
        try {
            if (configuracion.getId() == null) {
                return new ResponseEntity<>(configuracionService.save(configuracion),
                    HeaderUtil.createAlert("admin.configuracion.create.success"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(configuracionService.update(configuracion),
                    HeaderUtil.createAlert("admin.configuracion.edit.success"), HttpStatus.OK);
            }
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("admin.form.error.alreadyexists"), HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
