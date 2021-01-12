package es.enxenio.sife1701.model.estatica.estatica;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.enxenio.sife1701.model.estatica.estaticai18n.EstaticaI18n;
import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jlosa on 05/06/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "estatica")
public class Estatica extends GenericEntity {

    @Column(name = "identificador")
    private String nombre;

    @JsonManagedReference
    @OneToMany(mappedBy = "estatica", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("titulo ASC")
    private Set<EstaticaI18n> estaticasI18n;

    //

    public Estatica() {
    }

    //

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<EstaticaI18n> getEstaticasI18n() {
        return estaticasI18n;
    }

    public void setEstaticasi18n(Set<EstaticaI18n> estaticasI18n) {
        this.estaticasI18n = estaticasI18n;
    }

    // TODO: Refactorizar y utilizar también para los idiomas que se mostrarán.
    public enum Idioma {
        ES, EN
    }
}
