package es.enxenio.sife1701.model.usuario.alumno;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditos;
import es.enxenio.sife1701.model.usuario.Rol;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.empresa.Empresa;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "comun", name = "alumno")
@PrimaryKeyJoinColumn(name = "id")
public class Alumno extends Usuario {

    @JsonView(JsonViews.DetailedList.class)
    private String apellidos;

    @JsonView(JsonViews.Details.class)
    private String skype;

    @JsonView(JsonViews.Details.class)
    @Column(name = "creditos_totales")
    private Integer creditosTotales;

    @JsonView(JsonViews.Details.class)
    @Column(name = "creditos_disponibles")
    private Integer creditosDisponibles;

    @JsonView(JsonViews.Details.class)
    @Column(name = "creditos_consumidos")
    private Integer creditosConsumidos;

    @JsonView(JsonViews.Details.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @JsonView(JsonViews.Details.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "worldprofe", name = "alumno_paquetecreditos",
        joinColumns = @JoinColumn(name = "alumno_id"), inverseJoinColumns = @JoinColumn(name = "paquete_creditos_id"))
    @OrderBy("creditos ASC")
    private Set<PaqueteCreditos> paquetesCreditos;

    public Alumno() {
        super.setRol(Rol.ROLE_ALUMNO);
        super.setActivado(true);
        this.creditosDisponibles = 0;
        this.creditosConsumidos = 0;
        this.creditosTotales = 0;
    }

    public void update(Usuario u) {
        super.update(u);
        Alumno alumno = (Alumno) u;
        this.apellidos = alumno.getApellidos();
        this.skype = alumno.getSkype();
        this.creditosTotales = alumno.getCreditosTotales();
        this.creditosDisponibles = alumno.getCreditosDisponibles();
        this.creditosConsumidos = alumno.getCreditosConsumidos();
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

    public Integer getCreditosTotales() {
        return creditosTotales;
    }

    public void setCreditosTotales(Integer creditosTotales) {
        this.creditosTotales = creditosTotales;
    }

    public Integer getCreditosDisponibles() {
        return creditosDisponibles;
    }

    public void setCreditosDisponibles(Integer creditosDisponibles) {
        this.creditosDisponibles = creditosDisponibles;
    }

    public Integer getCreditosConsumidos() {
        return creditosConsumidos;
    }

    public void setCreditosConsumidos(Integer creditosConsumidos) {
        this.creditosConsumidos = creditosConsumidos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set<PaqueteCreditos> getPaquetesCreditos() {
        return paquetesCreditos;
    }

    public void setPaquetesCreditos(Set<PaqueteCreditos> paquetesCreditos) {
        this.paquetesCreditos = paquetesCreditos;
    }
}
