package es.enxenio.sife1701.controller.publica;

import es.enxenio.sife1701.model.pais.Pais;
import es.enxenio.sife1701.model.pais.PaisService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/pais")
public class PaisController {

    @Inject
    private PaisService paisService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pais get(@PathVariable("id") Long id) {
        return paisService.get(id);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<List<Pais>> query(@RequestParam(value = "query", required = false) String query) {
        if (query == null) query = "";
        return new ResponseEntity<>(paisService.query(query), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllWithProfesores", method = RequestMethod.GET)
    public ResponseEntity<List<Pais>> getAllWithProfesores() {
        return new ResponseEntity<>(paisService.getAllWithProfesores(), HttpStatus.OK);
    }

}
