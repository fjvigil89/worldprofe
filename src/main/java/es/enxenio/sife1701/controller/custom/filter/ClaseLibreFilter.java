package es.enxenio.sife1701.controller.custom.filter;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;

/**
 * Created by crodriguez on 13/06/2017.
 */
public class ClaseLibreFilter extends PageableFilter {

    private Boolean ocupada;
    private String fechaInicio;
    private String fechaFin;

    public Boolean getOcupada() {
        return ocupada;
    }

    public void setOcupada(Boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
