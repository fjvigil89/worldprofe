package es.enxenio.sife1701.model.configuracion;

import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "configuracion")
public class Configuracion extends GenericEntity {

    private Integer iva;

    @Column(name = "tiempo_maximo_respuesta")
    private Integer tiempoMaximoRespuesta;

    @Column(name = "tiempo_antes_inicio_cancelar")
    private Integer tiempoAntesInicioCancelar;

    // TODO: Eliminar de aquí, de configuracion.html, de admin.json y de BD
//    @Column(name = "tiempo_maximo_actualizar_incidencia")
//    private Integer tiempoMaximoActualizarIncidencia;

    @Column(name = "correo_notificaciones")
    private String correoNotificaciones;

    //

    public Configuracion() {
    }

    //

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Integer getTiempoMaximoRespuesta() {
        return tiempoMaximoRespuesta;
    }

    public void setTiempoMaximoRespuesta(Integer tiempoMaximoRespuesta) {
        this.tiempoMaximoRespuesta = tiempoMaximoRespuesta;
    }

    public Integer getTiempoAntesInicioCancelar() {
        return tiempoAntesInicioCancelar;
    }

    public void setTiempoAntesInicioCancelar(Integer tiempoAntesInicioCancelar) {
        this.tiempoAntesInicioCancelar = tiempoAntesInicioCancelar;
    }

//    public Integer getTiempoMaximoActualizarIncidencia() {
//        return tiempoMaximoActualizarIncidencia;
//    }
//
//    public void setTiempoMaximoActualizarIncidencia(Integer tiempoMaximoActualizarIncidencia) {
//        this.tiempoMaximoActualizarIncidencia = tiempoMaximoActualizarIncidencia;
//    }

    public String getCorreoNotificaciones() {
        return correoNotificaciones;
    }

    public void setCorreoNotificaciones(String correoNotificaciones) {
        this.correoNotificaciones = correoNotificaciones;
    }
}
