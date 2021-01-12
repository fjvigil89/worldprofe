package es.enxenio.sife1701.controller.custom.filter;

import es.enxenio.sife1701.model.capacidad.Capacidad;
import es.enxenio.sife1701.model.idioma.Idioma;
import es.enxenio.sife1701.model.nivel.Nivel;
import es.enxenio.sife1701.model.pais.Pais;
import es.enxenio.sife1701.model.usuario.profesor.Disponibilidad;

import java.util.List;

/**
 * Created by jlosa on 13/09/2017.
 */
public class ProfesorFilter extends UsuarioFilter {

    private Idioma idioma;
    private Nivel nivel;
    private List<Pais> paises;
    private Disponibilidad disponibilidad;
    private List<Capacidad> capacidades;
    private String horadia;
    private String dia;

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public List<Capacidad> getCapacidades() {
        return capacidades;
    }

    public void setCapacidades(List<Capacidad> capacidades) {
        this.capacidades = capacidades;
    }

    public String getHoradia() {
        return horadia;
    }

    public void setHoradia(String horadia) {
        this.horadia = horadia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
