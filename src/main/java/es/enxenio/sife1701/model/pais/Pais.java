package es.enxenio.sife1701.model.pais;

import es.enxenio.sife1701.model.util.tesauro.Tesauro;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "comun", name = "pais")
public class Pais extends Tesauro {

    private String codigo;

    public Pais() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
