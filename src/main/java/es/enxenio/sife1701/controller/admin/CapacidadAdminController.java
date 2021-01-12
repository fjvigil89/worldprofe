package es.enxenio.sife1701.controller.admin;

import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.model.capacidad.Capacidad;
import es.enxenio.sife1701.model.capacidad.CapacidadService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_ADMIN + "/capacidad")
@PreAuthorize(ConstantesRest.HAS_ROLE_ADMIN)
public class CapacidadAdminController {

    @Inject
    private CapacidadService capacidadService;

    // Gestión

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Capacidad> saveOrUpdate(@Valid @RequestBody Capacidad capacidad) {
        try {
            if (capacidad.getId() == null) {
                return new ResponseEntity<>(capacidadService.save(capacidad),
                    HeaderUtil.createAlert("admin.capacidad.create.success"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(capacidadService.update(capacidad),
                    HeaderUtil.createAlert("admin.capacidad.edit.success"), HttpStatus.OK);
            }
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("admin.form.error.alreadyexists"), HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            capacidadService.delete(id);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HeaderUtil.createAlert("admin.capacidad.delete.success"), HttpStatus.OK);
    }
}
