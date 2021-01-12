package es.enxenio.sife1701.model.reserva.email;

import es.enxenio.sife1701.model.generic.GenericEntity;
import es.enxenio.sife1701.model.reserva.Reserva;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by jlosa on 04/10/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "email_reserva")
public class EmailReserva extends GenericEntity {

    private ZonedDateTime fecha;

    @Column(name = "url_base")
    private String urlBase;

    private String locale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    public EmailReserva() {
    }

    public EmailReserva(ZonedDateTime fecha, String urlBase, String locale, Reserva reserva) {
        this.fecha = fecha;
        this.urlBase = urlBase;
        this.locale = locale;
        this.reserva = reserva;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
