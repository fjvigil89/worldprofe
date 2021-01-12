package es.enxenio.sife1701.model.usuario.profesor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.capacidad.Capacidad;
import es.enxenio.sife1701.model.claselibre.ClaseLibre;
import es.enxenio.sife1701.model.pais.Pais;
import es.enxenio.sife1701.model.usuario.Rol;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.zonahoraria.ZonaHoraria;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "comun", name = "profesor")
@PrimaryKeyJoinColumn(name = "id")
public class Profesor extends Usuario {

    @JsonView(JsonViews.DetailedList.class)
    private String apellidos;

    @JsonView(JsonViews.Details.class)
    private String skype;

    @JsonView(JsonViews.Details.class)
    @Column(name = "texto_presentacion")
    private String textoPresentacion;

    @JsonView(JsonViews.Details.class)
    @Column(name = "descripcion_capacidades")
    private String descripcionCapacidades;

    @JsonView(JsonViews.Details.class)
    @Column(name = "fecha_nacimiento")
    private ZonedDateTime fechaNacimiento;

    @JsonView(JsonViews.Details.class)
    @Column(name = "clases_impartidas")
    private Integer clasesImpartidas;

    /*@JsonView(JsonViews.Details.class)
    @Enumerated(EnumType.STRING)
    private Disponibilidad disponibilidad;*/

    @JsonView(JsonViews.Details.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pais_id")
    private Pais pais;

    @JsonManagedReference
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProfesorIdioma> idiomas;

    @JsonView(JsonViews.Details.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "worldprofe", name = "profesor_capacidad",
        joinColumns = @JoinColumn(name = "profesor_id"), inverseJoinColumns = @JoinColumn(name = "capacidad_id"))
    @OrderBy("nombre")
    private Set<Capacidad> capacidades;

    @JsonIgnore
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ClaseLibre> clasesLibres;

    @JsonView(JsonViews.Details.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "zona_horaria_id")
    private ZonaHoraria zonaHoraria;

    public Profesor() {
        super.setRol(Rol.ROLE_PROFESOR);
        super.setActivado(true);
        this.setClasesImpartidas(0);
    }

    public void update(Usuario u) {
        super.update(u);
        Profesor profesor = (Profesor) u;
        this.apellidos = profesor.getApellidos();
        this.skype = profesor.getSkype();
        this.textoPresentacion = profesor.getTextoPresentacion();
        this.descripcionCapacidades = profesor.getDescripcionCapacidades();
        this.fechaNacimiento = profesor.getFechaNacimiento();
        /*this.disponibilidad = profesor.getDisponibilidad();*/
        this.pais = profesor.getPais();
        //this.idiomas = profesor.getIdiomas();
        this.idiomas.clear();
        if (profesor.getIdiomas() != null) {
            this.idiomas.addAll(profesor.getIdiomas());
        }
        this.capacidades = profesor.getCapacidades();
        //this.clasesLibres = profesor.getClasesLibres();
        this.zonaHoraria = profesor.getZonaHoraria();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getTextoPresentacion() {
        return textoPresentacion;
    }

    public void setTextoPresentacion(String textoPresentacion) {
        this.textoPresentacion = textoPresentacion;
    }

    public String getDescripcionCapacidades() {
        return descripcionCapacidades;
    }

    public void setDescripcionCapacidades(String descripcionCapacidades) {
        this.descripcionCapacidades = descripcionCapacidades;
    }

    public ZonedDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(ZonedDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /*public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }*/

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Set<ProfesorIdioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<ProfesorIdioma> idiomas) {
        this.idiomas = idiomas;
    }

    public Set<Capacidad> getCapacidades() {
        return capacidades;
    }

    public void setCapacidades(Set<Capacidad> capacidades) {
        this.capacidades = capacidades;
    }

    public Set<ClaseLibre> getClasesLibres() {
        return clasesLibres;
    }

    public void setClasesLibres(Set<ClaseLibre> clasesLibres) {
        this.clasesLibres = clasesLibres;
    }

    public ZonaHoraria getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(ZonaHoraria zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    public Integer getClasesImpartidas() {
        return clasesImpartidas;
    }

    public void setClasesImpartidas(Integer clasesImpartidas) {
        this.clasesImpartidas = clasesImpartidas;
    }
}
