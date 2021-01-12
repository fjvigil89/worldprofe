package es.enxenio.sife1701.controller.admin;

import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.model.estatica.EstaticaService;
import es.enxenio.sife1701.model.estatica.estatica.Estatica;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_ADMIN + "/estaticas")
public class EstaticaAdminController {

    @Inject
    private EstaticaService estaticaService;

    //

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity updateEstatica(@RequestBody Estatica estatica) {

        estaticaService.save(estatica);
        return new ResponseEntity<>(HeaderUtil.createAlert("estaticas.form.edit.success"), HttpStatus.OK);
    }

}
