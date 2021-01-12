package es.enxenio.sife1701.controller.custom;

import java.time.ZonedDateTime;

/**
 * Created by jlosa on 02/10/2017.
 */
public class EmpresaDTO {

    private String nombre;
    private Integer creditosTotales;
    private Integer creditosDisponibles;
    private Integer creditosDistribuidos;
    private Long creditosConsumidos;
    private String email;
    private ZonedDateTime fechaRegistro;
    private String nif;

    public EmpresaDTO() {
    }

    public EmpresaDTO(String nombre, Integer creditosTotales, Integer creditosDisponibles, Integer creditosDistribuidos) {
        this.nombre = nombre;
        this.creditosTotales = creditosTotales;
        this.creditosDisponibles = creditosDisponibles;
        this.creditosDistribuidos = creditosDistribuidos;
    }

    public EmpresaDTO(String nombre,
                      Integer creditosTotales, Integer creditosDisponibles, Integer creditosDistribuidos, Long creditosConsumidos,
                      String email, ZonedDateTime fechaRegistro, String nif) {
        this.nombre = nombre;
        this.creditosTotales = creditosTotales;
        this.creditosDisponibles = creditosDisponibles;
        this.creditosDistribuidos = creditosDistribuidos;
        this.creditosConsumidos = creditosConsumidos == null ? 0 : creditosConsumidos;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Long getCreditosConsumidos() {
        return creditosConsumidos;
    }

    public void setCreditosConsumidos(Long creditosConsumidos) {
        this.creditosConsumidos = creditosConsumidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(ZonedDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
