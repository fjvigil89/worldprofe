package es.enxenio.sife1701.model.usuario.profesor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import es.enxenio.sife1701.model.generic.GenericEntity;
import es.enxenio.sife1701.model.idioma.Idioma;
import es.enxenio.sife1701.model.nivel.Nivel;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "profesor_idioma")
public class ProfesorIdioma extends GenericEntity {

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idioma_id")
    private Idioma idioma;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "worldprofe", name = "profesoridioma_nivel",
        joinColumns = @JoinColumn(name = "profesor_idioma_id"), inverseJoinColumns = @JoinColumn(name = "nivel_id"))
    private Set<Nivel> niveles;

    public ProfesorIdioma() {
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Set<Nivel> getNiveles() {
        return niveles;
    }

    public void setNiveles(Set<Nivel> niveles) {
        this.niveles = niveles;
    }
}
