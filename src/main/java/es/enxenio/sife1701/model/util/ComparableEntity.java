package es.enxenio.sife1701.model.util;

import es.enxenio.sife1701.model.generic.GenericEntity;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Se puede utilizar cuando hay que comparar entidades. En caso de que los campos que se estén comparando coincidan, se utilizará el ID.
 * Si no se tiene en cuenta el ID y los campos coinciden, se consideran duplicados y se queda sólo con un elemento.
 * <p>
 * Created by crodriguez on 22/08/2017.
 */
public class ComparableEntity<T extends GenericEntity> extends GenericEntity implements Comparable<T> {

    // FIXME: Intentar que la ordenación la primera vez que se crea sea por orden de inserción. Ahora parece que se está desordenando.

    @Override
    public int compareTo(T o) {
        int result = ObjectUtils.compare(this.getId(), o.getId());
        if (result == 0) {
            result = ObjectUtils.compare(this.toString(), o.toString());
        }
        return result;
    }

}
