package es.enxenio.sife1701.model.capacidad;

import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "capacidad")
public class Capacidad extends GenericEntity {

    private String nombre;

    public Capacidad() {
    }

    public Capacidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
