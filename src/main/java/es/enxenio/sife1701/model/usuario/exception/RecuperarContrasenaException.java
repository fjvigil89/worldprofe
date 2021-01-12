package es.enxenio.sife1701.model.usuario.exception;

@SuppressWarnings("serial")
public class RecuperarContrasenaException extends Exception {

    private String email;

    public RecuperarContrasenaException(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
