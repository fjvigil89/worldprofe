package es.enxenio.sife1701.model.datosfacturacion;

import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "comun", name = "datos_facturacion")
public class DatosFacturacion extends GenericEntity {

    private String nombre;

    private String nif;

    private String provincia;

    private String localidad;

    @Column(name = "cp")
    private String codigoPostal;

    private String direccion;

    //

    public DatosFacturacion() {
    }

    //

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
