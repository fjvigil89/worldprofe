package es.enxenio.sife1701.model.util;

import org.springframework.http.HttpStatus;

public abstract class AppException extends Exception {

    private HttpStatus status;
    private String errorCode;
    private String parameter;

    /**
     * @param errorCode Message key, used in client to retrieve the error message
     * @param parameter Parameter of the message
     * @param status    Response status
     */
    public AppException(String errorCode, String parameter, HttpStatus status) {
        // TODO: Add log info to the exception
        //super(logMessage);
        this.status = status;
        this.errorCode = errorCode;
        this.parameter = parameter;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getParameter() {
        return parameter;
    }
}
