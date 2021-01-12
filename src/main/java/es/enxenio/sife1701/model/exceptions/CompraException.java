package es.enxenio.sife1701.model.exceptions;

public class CompraException extends Exception {

    private static final long serialVersionUID = 1L;

    private String response;

    public CompraException(String msg) {
        super(msg);
    }

    public CompraException(String msg, String response) {
        super(msg);
        this.response = response;
    }

    public CompraException(Exception e) {
        super(e);
    }

    public String getResponse() {
        return response;
    }

}
