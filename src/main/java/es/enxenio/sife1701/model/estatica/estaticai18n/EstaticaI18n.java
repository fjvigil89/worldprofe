package es.enxenio.sife1701.model.estatica.estaticai18n;

import com.fasterxml.jackson.annotation.JsonBackReference;
import es.enxenio.sife1701.model.estatica.estatica.Estatica;
import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by jlosa on 05/06/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "estatica_i18n")
public class EstaticaI18n extends GenericEntity {

    private String titulo;

    private String contenido;

    @Enumerated(EnumType.STRING)
    @Column(name = "idioma_codigo")
    private Estatica.Idioma idioma;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estatica_id")
    private Estatica estatica;

    //

    public EstaticaI18n() {
    }

    //

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Estatica.Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Estatica.Idioma idioma) {
        this.idioma = idioma;
    }

    public Estatica getEstatica() {
        return estatica;
    }

    public void setEstatica(Estatica estatica) {
        this.estatica = estatica;
    }

}
