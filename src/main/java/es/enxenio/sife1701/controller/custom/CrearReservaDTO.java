package es.enxenio.sife1701.controller.custom;

/**
 * Created by jlosa on 15/09/2017.
 */
public class CrearReservaDTO {

    private Long claseLibreId;
    private Long alumnoId;
    private Long profesorId;

    public CrearReservaDTO() {
    }

    public CrearReservaDTO(Long claseLibreId, Long alumnoId, Long profesorId) {
        this.claseLibreId = claseLibreId;
        this.alumnoId = alumnoId;
        this.profesorId = profesorId;
    }

    public Long getClaseLibreId() {
        return claseLibreId;
    }

    public void setClaseLibreId(Long claseLibreId) {
        this.claseLibreId = claseLibreId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Long profesorId) {
        this.profesorId = profesorId;
    }
}
