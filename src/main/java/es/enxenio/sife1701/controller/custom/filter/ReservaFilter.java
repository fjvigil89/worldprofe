package es.enxenio.sife1701.controller.custom.filter;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.reserva.Estado;
import es.enxenio.sife1701.model.usuario.Rol;

/**
 * Created by crodriguez on 13/06/2017.
 */
public class ReservaFilter extends PageableFilter {

    private Rol rol;
    private Estado estado;
    private String fechaInicio;
    private String fechaFin;
    private boolean canceladasRechazadas;
    private Boolean revisadas;

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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

    public boolean getCanceladasRechazadas() {
        return canceladasRechazadas;
    }

    public void setCanceladasRechazadas(boolean canceladasRechazadas) {
        this.canceladasRechazadas = canceladasRechazadas;
    }

    public Boolean getRevisadas() {
        return revisadas;
    }

    public void setRevisadas(Boolean revisadas) {
        this.revisadas = revisadas;
    }
}
