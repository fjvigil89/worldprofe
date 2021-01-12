package es.enxenio.sife1701.model.usuario.alumno;

import es.enxenio.sife1701.model.generic.GenericEntity;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditos;

import javax.persistence.*;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "alumno_paquetecreditos")
public class AlumnoPaquetecreditos extends GenericEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paquete_creditos_id")
    private PaqueteCreditos paqueteCreditos;

    public AlumnoPaquetecreditos() {
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public PaqueteCreditos getPaqueteCreditos() {
        return paqueteCreditos;
    }

    public void setPaqueteCreditos(PaqueteCreditos paqueteCreditos) {
        this.paqueteCreditos = paqueteCreditos;
    }
}
