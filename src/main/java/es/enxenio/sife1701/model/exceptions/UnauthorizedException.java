package es.enxenio.sife1701.model.exceptions;

/**
 * Created by crodriguez on 21/09/2016.
 */
public class UnauthorizedException extends Exception {

    /**
     * Default constructor.
     */
    public UnauthorizedException() {
        super();
    }

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message the detail message.
     */
    public UnauthorizedException(String message) {
        super(message);
    }

}
