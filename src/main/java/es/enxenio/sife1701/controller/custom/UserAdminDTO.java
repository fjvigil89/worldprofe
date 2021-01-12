package es.enxenio.sife1701.controller.custom;


import es.enxenio.sife1701.model.usuario.Rol;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by crodriguez on 24/05/2016.
 */
public class UserAdminDTO {

    private Long id;

    private String email;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    UserAdminDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
