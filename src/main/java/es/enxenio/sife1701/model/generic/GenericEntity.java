package es.enxenio.sife1701.model.generic;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.util.JsonViews;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by crodriguez on 16/09/2016.
 */
@MappedSuperclass
public abstract class GenericEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JsonViews.List.class)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
