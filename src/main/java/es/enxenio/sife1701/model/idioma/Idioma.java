package es.enxenio.sife1701.model.idioma;

import es.enxenio.sife1701.model.util.tesauro.Tesauro;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "comun", name = "idioma")
public class Idioma extends Tesauro {

    public Idioma() {
    }
}
