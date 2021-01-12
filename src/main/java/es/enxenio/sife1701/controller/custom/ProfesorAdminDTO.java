package es.enxenio.sife1701.controller.custom;

import es.enxenio.sife1701.model.usuario.Rol;

import java.time.ZonedDateTime;

public class ProfesorAdminDTO {

    // Datos del usuario
    private Long id;
    private Rol rol;
    private String login;
    private String email;
    private String nombre;
    private String apellidos;
    private String skype;
    private ZonedDateTime fechaRegistro;
    private ZonedDateTime fechaUltimoAcceso;
    private boolean validado;
    private boolean activado;
    private boolean eliminado;
    private boolean baja;

    // Datos agregados
    private Long clasesImpartidas;

    //

    public ProfesorAdminDTO(Long id, Rol rol, String login, String email, String nombre, String apellidos, String skype,
                            ZonedDateTime fechaRegistro, ZonedDateTime fechaUltimoAcceso,
                            boolean validado, boolean activado, boolean eliminado, boolean baja,
                            Long clasesImpartidas) {
        this.id = id;
        this.rol = rol;
        this.login = login;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.skype = skype;
        this.fechaRegistro = fechaRegistro;
        this.fechaUltimoAcceso = fechaUltimoAcceso;
        this.validado = validado;
        this.activado = activado;
        this.eliminado = eliminado;
        this.baja = baja;
        this.clasesImpartidas = clasesImpartidas;
    }

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Long getClasesImpartidas() {
        return clasesImpartidas;
    }

    public void setClasesImpartidas(Long clasesImpartidas) {
        this.clasesImpartidas = clasesImpartidas;
    }
}
