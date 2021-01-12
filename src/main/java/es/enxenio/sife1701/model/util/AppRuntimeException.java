package es.enxenio.sife1701.model.util;

import org.springframework.http.HttpStatus;

/**
 * Only for situations where the error is a code error
 *
 * @author acortinas
 */
public class AppRuntimeException extends RuntimeException {

    public AppRuntimeException() {
        super();
    }

    public AppRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppRuntimeException(String message) {
        super(message);
    }

    public AppRuntimeException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public String getErrorCode() {
        return "internalServerError";
    }

    public String getParameter() {
        return null;
    }
}
