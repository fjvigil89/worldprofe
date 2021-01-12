package es.enxenio.sife1701.controller.custom.util;

/**
 * Rango de años
 * Created by crodriguez on 11/07/2016.
 */
public class Rango {

    private Integer ini;

    private Integer fin;

    public Rango(Integer ini, Integer fin) {
        this.ini = ini;
        this.fin = fin;
    }

    public Integer getIni() {
        return ini;
    }

    public void setIni(Integer ini) {
        this.ini = ini;
    }

    public Integer getFin() {
        return fin;
    }

    public void setFin(Integer fin) {
        this.fin = fin;
    }
}
