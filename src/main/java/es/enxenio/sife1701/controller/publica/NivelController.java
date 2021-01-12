package es.enxenio.sife1701.controller.publica;

import es.enxenio.sife1701.model.nivel.Nivel;
import es.enxenio.sife1701.model.nivel.NivelService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/nivel")
public class NivelController {

    @Inject
    private NivelService nivelService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Nivel get(@PathVariable("id") Long id) {
        return nivelService.get(id);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<List<Nivel>> query() {
        return new ResponseEntity<>(nivelService.query(), HttpStatus.OK);
    }

}
