package es.enxenio.sife1701.model.util;

/**
 * Clase auxiliar para enviar la información del cliente al servidor.
 * Created by crodriguez on 19/09/2016.
 */
public class Imagen {

    private String path;

    private String archivoTemporal;

    private boolean eliminar = false;

    public Imagen() {
    }

    public Imagen(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArchivoTemporal() {
        return archivoTemporal;
    }

    public void setArchivoTemporal(String archivoTemporal) {
        this.archivoTemporal = archivoTemporal;
    }

    public boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }
}
