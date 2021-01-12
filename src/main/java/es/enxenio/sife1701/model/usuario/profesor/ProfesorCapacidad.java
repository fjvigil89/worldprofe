package es.enxenio.sife1701.model.usuario.profesor;

import es.enxenio.sife1701.model.capacidad.Capacidad;
import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.*;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "profesor_capacidad")
public class ProfesorCapacidad extends GenericEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "capacidad_id")
    private Capacidad capacidad;

    public ProfesorCapacidad() {
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Capacidad getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Capacidad capacidad) {
        this.capacidad = capacidad;
    }
}
