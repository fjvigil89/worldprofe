package es.enxenio.sife1701.model.generic;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import org.springframework.data.domain.Page;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

/**
 * Created by crodriguez on 13/09/2016.
 */
public interface GenericService<T> {

    // Consulta

    T get(Long id);

    Page<T> filter(PageableFilter filter);

    // Gestión

    T save(T t) throws InstanceAlreadyExistsException;

    T update(T t) throws InstanceNotFoundException, InstanceAlreadyExistsException;

    void delete(Long id) throws InstanceNotFoundException;

}
