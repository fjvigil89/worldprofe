package es.enxenio.sife1701.controller.admin;

import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditos;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditosService;
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
@RequestMapping(ConstantesRest.URL_ADMIN + "/paquetecreditos")
@PreAuthorize(ConstantesRest.HAS_ROLE_ADMIN)
public class PaqueteCreditosAdminController {

    @Inject
    private PaqueteCreditosService paqueteCreditosService;

    // Gestión

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PaqueteCreditos> saveOrUpdate(@Valid @RequestBody PaqueteCreditos paqueteCreditos) {
        try {
            if (paqueteCreditos.getId() == null) {
                return new ResponseEntity<>(paqueteCreditosService.save(paqueteCreditos),
                    HeaderUtil.createAlert("admin.paquetecreditos.create.success"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(paqueteCreditosService.update(paqueteCreditos),
                    HeaderUtil.createAlert("admin.paquetecreditos.edit.success"), HttpStatus.OK);
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
            paqueteCreditosService.delete(id);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HeaderUtil.createAlert("admin.paquetecreditos.delete.success"), HttpStatus.OK);
    }
}
