package es.enxenio.sife1701.model.zonahoraria;

import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jlosa on 19/10/2017.
 */
@Entity
@Table(schema = "worldprofe", name = "zona_horaria")
public class ZonaHoraria extends GenericEntity {

    private String gmt;

    public ZonaHoraria() {
    }

    public ZonaHoraria(String gmt) {
        this.gmt = gmt;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }
}
