package es.enxenio.sife1701.controller.util;

import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;

/**
 * Métodos para simplificar la programación.
 * Created by crodriguez on 13/09/2016.
 */
public class CodeUtil {

    public static boolean isEmpty(String field) {
        return field == null || field.isEmpty();
    }

    public static boolean notEmpty(String field) {
        return !isEmpty(field);
    }

    public static boolean equals(Object o1, Object o2) {
        return o1 != null && o2 != null && o1.equals(o2);
    }

    public static boolean notEquals(Object o1, Object o2) {
        return !equals(o1, o2);
    }

    public static void checkEntityAlreadyExists(GenericEntity newEntity, GenericEntity persistedEntity) throws InstanceAlreadyExistsException {
        if (newEntity != null && (persistedEntity.getId() == null || (notEquals(persistedEntity.getId(), newEntity.getId())))) {
            throw new InstanceAlreadyExistsException();
        }
    }

    /**
     * @param request request obtenida en el controller
     * @return Ruta base de la aplicación
     */
    public static String getUrlBase(HttpServletRequest request) {
        return request.getScheme() +                // "http"
            "://" +                                // "://"
            request.getServerName() +              // "myhost"
            ":" +                                  // ":"
            request.getServerPort() +              // "80"
            request.getContextPath();
    }


}
