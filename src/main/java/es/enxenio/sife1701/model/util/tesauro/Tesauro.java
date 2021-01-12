package es.enxenio.sife1701.model.util.tesauro;

import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by crodriguez on 31/05/2016.
 */
@MappedSuperclass
public abstract class Tesauro extends GenericEntity {

    @NotNull
    private String nombre;

    //

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
