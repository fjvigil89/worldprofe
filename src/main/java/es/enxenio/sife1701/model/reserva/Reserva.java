package es.enxenio.sife1701.model.reserva;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.claselibre.ClaseLibre;
import es.enxenio.sife1701.model.generic.GenericEntity;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "reserva")
public class Reserva extends GenericEntity {

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "motivo_profesor")
    private String motivoProfesor;

    @Column(name = "motivo_alumno")
    private String motivoAlumno;

    private Boolean revisada;

    private ZonedDateTime fecha;

    @Column(name = "fecha_solicitud")
    private ZonedDateTime fechaSolicitud;

    @Version
    @JsonIgnore
    private long version;

    @JsonView(JsonViews.DetailedList.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "claselibre_id")
    private ClaseLibre claseLibre;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonView(JsonViews.DetailedList.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    public Reserva() {
    }

    public Reserva(Estado estado, String motivoProfesor, String motivoAlumno, Alumno alumno, ClaseLibre claseLibre, Profesor profesor, ZonedDateTime fecha) {
        this.estado = estado;
        this.motivoProfesor = motivoProfesor;
        this.motivoAlumno = motivoAlumno;
        this.alumno = alumno;
        this.claseLibre = claseLibre;
        this.profesor = profesor;
        this.fecha = fecha;
        this.fechaSolicitud = ZonedDateTime.now();
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getMotivoProfesor() {
        return motivoProfesor;
    }

    public void setMotivoProfesor(String motivoProfesor) {
        this.motivoProfesor = motivoProfesor;
    }

    public String getMotivoAlumno() {
        return motivoAlumno;
    }

    public void setMotivoAlumno(String motivoAlumno) {
        this.motivoAlumno = motivoAlumno;
    }

    public Boolean isRevisada() {
        return revisada;
    }

    public void setRevisada(Boolean revisada) {
        this.revisada = revisada;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(ZonedDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public ClaseLibre getClaseLibre() {
        return claseLibre;
    }

    public void setClaseLibre(ClaseLibre claseLibre) {
        this.claseLibre = claseLibre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}
