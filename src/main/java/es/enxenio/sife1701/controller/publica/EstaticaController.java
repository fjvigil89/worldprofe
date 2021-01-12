package es.enxenio.sife1701.controller.publica;

import es.enxenio.sife1701.model.estatica.EstaticaService;
import es.enxenio.sife1701.model.estatica.estatica.Estatica;
import es.enxenio.sife1701.model.estatica.estaticai18n.EstaticaI18n;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jlosa on 05/06/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/estaticas")
public class EstaticaController {

    @Inject
    private EstaticaService estaticaService;

    //

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Estatica get(@PathVariable("id") Long id) {
        return estaticaService.get(id);
    }

    @RequestMapping(value = "/nombre/{nombre}", method = RequestMethod.GET)
    public Estatica getByNombre(@PathVariable("nombre") String nombre) {
        return estaticaService.getByNombre(nombre);
    }

    @RequestMapping(value = "/getByNombreIdioma", method = RequestMethod.GET)
    public EstaticaI18n getByNombreIdioma(@RequestParam("nombre") String nombre, @RequestParam("idioma") String idioma) {
        return estaticaService.getByNombreIdioma(nombre, Estatica.Idioma.valueOf(idioma.toUpperCase()));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<List<Estatica>> findAll() {
        return new ResponseEntity<>(estaticaService.findAll(), HttpStatus.OK);
    }

}
