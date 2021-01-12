package es.enxenio.sife1701.model.compra;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.generic.GenericEntity;
import es.enxenio.sife1701.model.paquetecreditos.PaqueteCreditos;
import es.enxenio.sife1701.model.usuario.Usuario;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "compra")
public class Compra extends GenericEntity {

    private ZonedDateTime fecha;

    private Integer creditos;

    // Precio final después del descuento
    private BigDecimal precio;

    // Precio final una vez aplicado el IVA
    @Column(name = "precio_con_iva")
    private BigDecimal precioConIva;

    @Column(name = "porcentaje_iva")
    private Integer porcentajeIva;

    @Column(name = "paypal_payment_id")
    private String paypalPaymentId;

    private boolean realizada;

    @Column(name = "solicitar_factura")
    private boolean solicitarFactura;

    @JsonView(JsonViews.Details.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paquete_creditos_id")
    private PaqueteCreditos paqueteCreditos;

    @JsonView(JsonViews.Details.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Compra() {
        fecha = ZonedDateTime.now();
    }

    public Compra(Integer creditos, BigDecimal precio, BigDecimal precioConIva, Integer porcentajeIva, boolean solicitarFactura, PaqueteCreditos paqueteCreditos, Usuario usuario) {
        this.fecha = ZonedDateTime.now();
        this.realizada = false;
        this.creditos = creditos;
        this.precio = precio;
        this.precioConIva = precioConIva;
        this.porcentajeIva = porcentajeIva;
        this.solicitarFactura = solicitarFactura;
        this.paqueteCreditos = paqueteCreditos;
        this.usuario = usuario;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getPrecioConIva() {
        return precioConIva;
    }

    public void setPrecioConIva(BigDecimal precioConIva) {
        this.precioConIva = precioConIva;
    }

    public Integer getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(Integer porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public boolean getRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public boolean isSolicitarFactura() {
        return solicitarFactura;
    }

    public void setSolicitarFactura(boolean solicitarFactura) {
        this.solicitarFactura = solicitarFactura;
    }

    public PaqueteCreditos getPaqueteCreditos() {
        return paqueteCreditos;
    }

    public void setPaqueteCreditos(PaqueteCreditos paqueteCreditos) {
        this.paqueteCreditos = paqueteCreditos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
