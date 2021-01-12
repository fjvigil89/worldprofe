package es.enxenio.sife1701.controller.publica;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.capacidad.Capacidad;
import es.enxenio.sife1701.model.capacidad.CapacidadService;
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
@RequestMapping(ConstantesRest.URL_PUBLIC + "/capacidad")
public class CapacidadController {

    @Inject
    private CapacidadService capacidadService;

    // Consulta

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Capacidad get(@PathVariable("id") Long id) {
        return capacidadService.get(id);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<Page<Capacidad>> query(PageableFilter filter) {
        return new ResponseEntity<>(capacidadService.filter(filter), HttpStatus.OK);
    }

}
