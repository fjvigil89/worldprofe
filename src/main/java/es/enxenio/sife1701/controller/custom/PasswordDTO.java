package es.enxenio.sife1701.controller.custom;

/**
 * Created by crodriguez on 08/06/2016.
 */
public class PasswordDTO {

    private String password;
    private String newpassword;

    public PasswordDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
