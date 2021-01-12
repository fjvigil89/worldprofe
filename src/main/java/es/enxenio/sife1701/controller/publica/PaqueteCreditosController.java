package es.enxenio.sife1701.controller.publica;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditos;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditosService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/paquetecreditos")
public class PaqueteCreditosController {

    @Inject
    private PaqueteCreditosService paqueteCreditosService;

    // Consulta

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PaqueteCreditos get(@PathVariable("id") Long id) {
        return paqueteCreditosService.get(id);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<Page<PaqueteCreditos>> findAll(PageableFilter filter) {
        if (filter != null) {
            return new ResponseEntity<>(paqueteCreditosService.filter(filter), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
