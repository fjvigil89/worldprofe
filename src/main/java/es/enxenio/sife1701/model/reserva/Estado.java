package es.enxenio.sife1701.model.reserva;

/**
 * Created by jlosa on 25/08/2017.
 */
public enum Estado {

    //Una clase libre fue reservada por un alumno pero el profesor está pendiente de contestar
    SIN_CONTESTAR,
    //Pasaron Xh y el profesor no contestó
    NO_CONTESTADA,
    //El profesor confirmó la reserva del alumno
    CONFIRMADA,
    //El profesor rechazó la reserva del alumno
    RECHAZADA,
    // Ambos roles cancelan la reserva hasta unas horas antes de producirse el encuentro
    CANCELADA_POR_ALUMNO, CANCELADA_POR_PROFESOR,
    // El encuentro se realizó pero el profesor no indicó si tuvo éxito o no
    PENDIENTE,
    // El encuentro finalmente no sucedió, ambos roles deberán indicar un motivo que coincidirá o no
    INCIDENCIA,
    // El encuentro se realizó
    REALIZADA
}
