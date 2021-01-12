package es.enxenio.sife1701.controller.publica;

import es.enxenio.sife1701.model.estatica.estatica.Estatica;
import es.enxenio.sife1701.model.reserva.Estado;
import es.enxenio.sife1701.model.usuario.profesor.Dia;
import es.enxenio.sife1701.model.usuario.profesor.Disponibilidad;
import es.enxenio.sife1701.model.usuario.profesor.Horadia;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/enum")
public class EnumController {

    @RequestMapping(value = "/estado", method = RequestMethod.GET)
    public Estado[] getEstados() {
        return Estado.values();
    }

    @RequestMapping(value = "/disponibilidad", method = RequestMethod.GET)
    public Disponibilidad[] getDisponibilidades() { return Disponibilidad.values(); }

    @RequestMapping(value = "/horadia", method = RequestMethod.GET)
    public Horadia[] getHoradias() { return Horadia.values(); }

    @RequestMapping(value = "/dia", method = RequestMethod.GET)
    public Dia[] getDias() { return Dia.values(); }

    // Estáticas

    @RequestMapping(value = "/estaticas-idioma", method = RequestMethod.GET)
    public Estatica.Idioma[] getIdiomasEstaticas() {
        return Estatica.Idioma.values();
    }

}
