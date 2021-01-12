package es.enxenio.sife1701.util;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by crodriguez on 03/03/2017.
 */
public class DateUtil {

    /**
     * Convierte un ZonedDateTime a un String de la forma: X horas y Y minutos.
     *
     * @param fecha ZonedDateTime.
     * @return Fecha formateada.
     */
    public static String fechaALenguajeNatural(ZonedDateTime fecha) {
        int diferencia = (int) ChronoUnit.MINUTES.between(ZonedDateTime.now(), fecha);
        int hours = diferencia / 60;
        int minutes = diferencia % 60;
        String tiempo;
        if (hours == 0) {
            tiempo = String.format("%02d minutos", minutes);
        } else {
            tiempo = String.format("%d horas y %02d minutos", hours, minutes);
        }
        return tiempo;
    }

}
