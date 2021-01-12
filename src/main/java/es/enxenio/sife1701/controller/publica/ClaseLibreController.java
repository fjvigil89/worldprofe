package es.enxenio.sife1701.controller.publica;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.custom.filter.ClaseLibreFilter;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.claselibre.ClaseLibre;
import es.enxenio.sife1701.model.claselibre.ClaseLibreService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/clase-libre")
public class ClaseLibreController {

    @Inject
    private ClaseLibreService claseLibreService;

    // Consulta

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ClaseLibre get(@PathVariable("id") Long id) {
        return claseLibreService.get(id);
    }

    @JsonView(JsonViews.DetailedList.class)
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<Page<ClaseLibre>> findAll(ClaseLibreFilter filter) {
        if (filter != null) {
            return new ResponseEntity<>(claseLibreService.filter(filter), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Gestión

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ClaseLibre> saveOrUpdate(@Valid @RequestBody ClaseLibre claseLibre) {
        try {
            if (claseLibre.getId() == null) {
                return new ResponseEntity<>(claseLibreService.save(claseLibre), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(claseLibreService.update(claseLibre), HttpStatus.OK);
            }
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/subtract", method = RequestMethod.POST)
    public ResponseEntity<ClaseLibre> subtract(@Valid @RequestBody ClaseLibre claseLibre) {
        try {
            claseLibreService.delete(claseLibre.getId());
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public ResponseEntity addList(@Valid @RequestBody List<ClaseLibre> clasesLibres) {
        try {
            for (ClaseLibre claseLibre : clasesLibres) {
                claseLibreService.save(claseLibre);
            }
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/duplicar-semana", method = RequestMethod.POST)
    public ResponseEntity duplicarSemana(@Valid @RequestBody ClaseLibreFilter claseLibreFilter) {
        claseLibreService.duplicarSemana(claseLibreFilter);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
