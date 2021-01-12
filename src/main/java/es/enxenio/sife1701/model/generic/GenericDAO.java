package es.enxenio.sife1701.model.generic;

import java.io.Serializable;

public interface GenericDAO<E, PK extends Serializable> {

    void create(E entity);

    E get(PK id);

    boolean exists(PK id);

    void update(E entity);

    void save(E entity);

    void delete(PK id);

}
