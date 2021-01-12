package es.enxenio.sife1701.util.email;

public class EnvioEmailException extends Exception {

    private String destinatario;

    public EnvioEmailException(String destinatario, Exception e) {
        super(e);
        this.destinatario = destinatario;
    }

    public String getDestinatario() {
        return destinatario;
    }

}
