package es.enxenio.sife1701.model.claselibre;

import es.enxenio.sife1701.model.generic.GenericEntity;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "clase_libre")
public class ClaseLibre extends GenericEntity {

    private ZonedDateTime fecha;
    private Boolean ocupada;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    //

    public ClaseLibre() {
    }

    //

    public ClaseLibre(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getOcupada() {
        return ocupada;
    }

    public void setOcupada(Boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}
