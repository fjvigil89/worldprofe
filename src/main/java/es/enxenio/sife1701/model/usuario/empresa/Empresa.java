package es.enxenio.sife1701.model.usuario.empresa;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.usuario.Rol;
import es.enxenio.sife1701.model.usuario.Usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "comun", name = "empresa")
@PrimaryKeyJoinColumn(name = "id")
public class Empresa extends Usuario {

    @JsonView(JsonViews.Details.class)
    @Column(name = "creditos_totales")
    private Integer creditosTotales;

    @JsonView(JsonViews.Details.class)
    @Column(name = "creditos_disponibles")
    private Integer creditosDisponibles;

    @JsonView(JsonViews.Details.class)
    @Column(name = "creditos_distribuidos")
    private Integer creditosDistribuidos;

    @JsonView(JsonViews.Details.class)
    private ZonedDateTime caducidad;

    public Empresa() {
        super.setRol(Rol.ROLE_EMPRESA);
        super.setActivado(true);
    }

    public void update(Usuario u) {
        super.update(u);
        Empresa empresa = (Empresa) u;
        this.creditosTotales = empresa.getCreditosTotales();
        this.creditosDisponibles = empresa.getCreditosDisponibles();
        this.creditosDistribuidos = empresa.getCreditosDistribuidos();
        this.caducidad = empresa.getCaducidad();
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

    public Integer getCreditosDistribuidos() {
        return creditosDistribuidos;
    }

    public void setCreditosDistribuidos(Integer creditosDistribuidos) {
        this.creditosDistribuidos = creditosDistribuidos;
    }

    public ZonedDateTime getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(ZonedDateTime caducidad) {
        this.caducidad = caducidad;
    }

}
