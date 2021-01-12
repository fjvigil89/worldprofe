package es.enxenio.sife1701.model.util;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.generic.GenericRepository;
import es.enxenio.sife1701.util.slug.SlugUtils;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import java.util.List;

/**
 * Created by crodriguez on 04/10/2016.
 */
public class CodeUtilModel {

    /**
     * @param titulo
     * @param id
     * @param genericRepository
     * @return url normalizada a partir del título. Además se comprueba que no exista una URL igual
     */
    public static String generarUrl(String titulo, Long id, GenericRepository genericRepository) {
        String url = SlugUtils.toSlug(titulo);
        String resultado = url;
        int i = 1;
        while (genericRepository.existsByUrl(resultado, id)) {
            resultado = url + "-" + String.valueOf(i++);
        }
        return resultado;
    }

    /**
     * Método para obtener resultados de una búsqueda mediante un criteria.
     *
     * @param filter  filtro que debe de implementar el método toDetachedCriteria()
     * @param session
     * @return elementos paginados
     */
    @SuppressWarnings("unchecked")
    public static PageImpl detached(PageableFilter filter, Session session) {
        Criteria criteria = filter.toDetachedCriteria().getExecutableCriteria(session);

        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();

        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        criteria.setFirstResult(filter.getPage() * filter.getSize());
        if (filter.getSize() == 0) {
            filter.setSize(Integer.MAX_VALUE);
        }
        criteria.setMaxResults(filter.getSize());
        if (filter.getSortProperty() != null) {
            Order order;
            if (filter.getSortDirection() != null && filter.getSortDirection().equals(Sort.Direction.ASC)) {
                order = Order.asc(filter.getSortProperty()).nulls(NullPrecedence.LAST);
            } else {
                order = Order.desc(filter.getSortProperty()).nulls(NullPrecedence.LAST);
            }
            criteria.addOrder(order);
        }
        List elements = criteria.list();

        return new PageImpl(elements, filter.getPageable(), count);
    }

    /**
     * Método que genera un Criterion de un campo omitiendo acentos y mayúsculas.
     *
     * @param campo
     * @param valor
     * @return criterion
     */
    public static Criterion textRestriction(String campo, String valor) {
        return Restrictions.sqlRestriction("lower(unaccent(" + campo + ")) like lower (?)", valor, StringType.INSTANCE);
    }

    /**
     * @param contrasenaClaro
     * @param email
     * @return contraseña codificada con SHA-256
     */
    public static String passwordEncoder(String contrasenaClaro, String email) {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        return encoder.encodePassword(contrasenaClaro, email);
    }

}
