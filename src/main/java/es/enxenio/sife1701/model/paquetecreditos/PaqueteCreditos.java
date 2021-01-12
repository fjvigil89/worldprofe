package es.enxenio.sife1701.model.paquetecreditos;

import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by jlosa on 25/08/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "paquete_creditos")
public class PaqueteCreditos extends GenericEntity {

    private Integer creditos;
    private BigDecimal coste;

    public PaqueteCreditos() {
    }

    public PaqueteCreditos(Integer creditos, BigDecimal coste) {
        this.creditos = creditos;
        this.coste = coste;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public BigDecimal getCoste() {
        return coste;
    }

    public void setCoste(BigDecimal coste) {
        this.coste = coste;
    }
}
