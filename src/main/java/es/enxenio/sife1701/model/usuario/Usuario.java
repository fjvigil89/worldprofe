package es.enxenio.sife1701.model.usuario;

import com.fasterxml.jackson.annotation.*;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.datosfacturacion.DatosFacturacion;
import es.enxenio.sife1701.model.generic.GenericEntity;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.empresa.Empresa;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import es.enxenio.sife1701.model.util.Imagen;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "comun", name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Usuario.class, name = "usuario"),
    @JsonSubTypes.Type(value = Alumno.class, name = "alumno"),
    @JsonSubTypes.Type(value = Empresa.class, name = "empresa"),
    @JsonSubTypes.Type(value = Profesor.class, name = "profesor")})
public class Usuario extends GenericEntity {

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "rol")
    private Rol rol;

    private String login;

    private String email;

    @JsonIgnore
    private String password;

    // En empresas se usa como persona de contacto
    @JsonView(JsonViews.DetailedList.class)
    private String nombre;

    @JsonView(JsonViews.Details.class)
    private String foto;

    @JsonView(JsonViews.Details.class)
    @Column(name = "telefono_movil")
    private String telefonoMovil;

    @JsonView(JsonViews.Details.class)
    private String observaciones;

    // Validada la cuenta de email
    @JsonView(JsonViews.Details.class)
    private boolean validado;

    // Activado el acceso a la plataforma (validación por parte del administrador)
    @JsonView(JsonViews.Details.class)
    private boolean activado;

    // Borrado lógico
    @JsonView(JsonViews.Details.class)
    private boolean eliminado;

    // Borrado fuerte (anonimizado)
    @JsonView(JsonViews.Details.class)
    private boolean baja;

    @JsonView(JsonViews.Details.class)
    @Column(name = "fecha_registro")
    private ZonedDateTime fechaRegistro;

    @JsonView(JsonViews.Details.class)
    @Column(name = "fecha_ultimo_acceso")
    private ZonedDateTime fechaUltimoAcceso;

    @JsonView(JsonViews.Details.class)
    private int accesos;

    // Clave de validación del email
    @Size(max = 20)
    @Column(name = "clave_activacion", length = 20)
    @JsonIgnore
    private String claveActivacion;

    @JsonView(JsonViews.Details.class)
    @Size(max = 20)
    @Column(name = "clave_reset", length = 20)
    private String claveReset;

    @JsonView(JsonViews.Details.class)
    @Column(name = "fecha_reset")
    private ZonedDateTime fechaReset;

    @Version
    @JsonIgnore
    private long version;

    @JsonView(JsonViews.DetailedList.class)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "datos_facturacion_id")
    private DatosFacturacion datosFacturacion;

    @Transient
    @JsonView(JsonViews.ExtraDetails.class)
    private String contrasenaClaro;

    @JsonIgnore
    @Transient
    private Imagen imagen;

    //

    public Usuario() {
        fechaRegistro = ZonedDateTime.now();
        validado = false;
        eliminado = false;
        baja = false;
    }

    public void update(Usuario u) {
        this.login = u.getLogin();
        this.nombre = u.getNombre();
        this.telefonoMovil = u.getTelefonoMovil();
        this.observaciones = u.getObservaciones();
        this.version = u.getVersion();
        this.datosFacturacion = u.getDatosFacturacion();
    }

    //

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    public ZonedDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(ZonedDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public ZonedDateTime getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(ZonedDateTime fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public int getAccesos() {
        return accesos;
    }

    public void setAccesos(int accesos) {
        this.accesos = accesos;
    }

    public String getClaveActivacion() {
        return claveActivacion;
    }

    public void setClaveActivacion(String claveActivacion) {
        this.claveActivacion = claveActivacion;
    }

    public String getClaveReset() {
        return claveReset;
    }

    public void setClaveReset(String claveReset) {
        this.claveReset = claveReset;
    }

    public ZonedDateTime getFechaReset() {
        return fechaReset;
    }

    public void setFechaReset(ZonedDateTime fechaReset) {
        this.fechaReset = fechaReset;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public String getContrasenaClaro() {
        return contrasenaClaro;
    }

    public void setContrasenaClaro(String contrasenaClaro) {
        this.contrasenaClaro = contrasenaClaro;
    }

    //

    @JsonView(JsonViews.Details.class)
    @JsonProperty("authorities")
    public List<String> getAuthorities() {
        return Arrays.asList(rol.toString());
    }

    public void setAuthorities(List<String> authorities) {
    }

    @JsonView(JsonViews.Details.class)
    @JsonProperty("imagen")
    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }


}
