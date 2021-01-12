package es.enxenio.sife1701.model.usuario;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import es.enxenio.sife1701.controller.custom.ProfesorAdminDTO;
import es.enxenio.sife1701.controller.custom.filter.ProfesorAdminFilter;
import es.enxenio.sife1701.controller.custom.filter.ProfesorFilter;
import es.enxenio.sife1701.controller.custom.filter.UsuarioFilter;
import es.enxenio.sife1701.model.generic.GenericDAOHibernate;
import es.enxenio.sife1701.model.usuario.profesor.Dia;
import es.enxenio.sife1701.model.usuario.profesor.Horadia;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import es.enxenio.sife1701.model.util.CodeUtilModel;

/**
 * Created by jlosa on 25/08/2017.
 */
@Repository
public class UsuarioDaoImpl extends GenericDAOHibernate<Usuario, Long> implements UsuarioDao {

    @Inject
    EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Page<Usuario> filter(UsuarioFilter filter) {
        return CodeUtilModel.detached(filter, getSession());
    }

    @Override
    public Page<ProfesorAdminDTO> filterProfesorAdmin(ProfesorAdminFilter filter) {

        String whereReservas = "";
        if (filter.getMes() != null)
            whereReservas += " AND month(r.fecha) = :mes";
        if (filter.getAno() != null)
            whereReservas += " AND year(r.fecha) = :year";

        String countReservas = " (SELECT count(r) FROM Reserva r"
                + " WHERE p.id = r.profesor.id AND r.estado = 'REALIZADA'" + whereReservas + ")";

        String select = "SELECT new es.enxenio.sife1701.controller.custom.ProfesorAdminDTO("
                + " p.id, p.rol, p.login, p.email, p.nombre, p.apellidos, p.skype,"
                + " p.fechaRegistro, p.fechaUltimoAcceso, p.validado, p.activado, p.eliminado, p.baja," + countReservas
                + ")";
        String from = " FROM Profesor p";

        String orderBy = " ORDER BY ";
        if (StringUtils.isNotEmpty(filter.getSortProperty())) {
            orderBy += filter.getSortProperty();
            if (filter.getSortDirection() != null) {
                orderBy += " " + filter.getSortDirection();
            }
        } else {
            orderBy += "p.nombre ASC";
        }

        String where = " WHERE 1 = 1";
        where += filter.getActivos() != null && filter.getActivos() ? " AND p.eliminado = FALSE AND p.baja = FALSE"
                : " AND p.eliminado = TRUE OR p.baja = TRUE";

        Query q = getSession().createQuery(select + from + where + orderBy);
        if (filter.getMes() != null)
            q.setParameter("mes", filter.getMes());
        if (filter.getAno() != null)
            q.setParameter("year", filter.getAno());
        q.setMaxResults(filter.getPageable().getPageSize());
        q.setFirstResult(filter.getPageable().getPageNumber() * filter.getPageable().getPageSize());

        Query count = entityManager.unwrap(Session.class).createQuery("SELECT COUNT(p.id)" + from + where);
        long numeroElementos = (long) count.uniqueResult();

        return new PageImpl<>(q.list(), filter.getPageable(), numeroElementos);
    }

    @Override
    public Page<Profesor> filterProfesor(ProfesorFilter filter) {
        StringBuilder queryStr = new StringBuilder("");

        StringBuilder from = new StringBuilder(" FROM Profesor p");
        queryStr.append(from.toString());

        StringBuilder where = new StringBuilder(
                " WHERE p.eliminado = false AND p.baja = false AND p.validado = true AND p.activado = true ");
        where.append((filter.getIdioma() != null && filter.getNivel() == null)
                ? " AND EXISTS (SELECT pi FROM p.idiomas pi WHERE pi.idioma = (:idioma))"
                : "");
        where.append((filter.getNivel() != null && filter.getIdioma() == null)
                ? " AND EXISTS (SELECT pi FROM p.idiomas pi WHERE (:nivel) IN (SELECT n FROM pi.niveles n))"
                : "");
        where.append((filter.getIdioma() != null && filter.getNivel() != null)
                ? " AND EXISTS (SELECT pi FROM p.idiomas pi WHERE pi.idioma = (:idioma) AND (:nivel) IN (SELECT n FROM pi.niveles n))"
                : "");
        where.append((filter.getPaises() != null && filter.getPaises().size() > 0) ? " AND p.pais IN (:paises)" : "");
        // where.append((filter.getDisponibilidad() != null && !filter.getDisponibilidad().equals(Disponibilidad.AMBOS))
        // ? " AND (p.disponibilidad = :disponibilidad OR p.disponibilidad = 'AMBOS')" : "");
        where.append((filter.getCapacidades() != null && filter.getCapacidades().size() > 0)
                ? " AND EXISTS (SELECT c FROM p.capacidades c WHERE c IN (:capacidades))"
                : "");
        if (!StringUtils.isBlank(filter.getHoradia()) && StringUtils.isBlank(filter.getDia())) {
            where.append(
                    " AND EXISTS (SELECT cl.fecha FROM p.clasesLibres cl WHERE (cast(to_char(cl.fecha,'HH24') as int) between :horaInicio and :horaFin) AND current_timestamp() < cl.fecha)");
        }
        if (!StringUtils.isBlank(filter.getDia()) && StringUtils.isBlank(filter.getHoradia())) {
            where.append(
                    " AND EXISTS (SELECT cl.fecha FROM p.clasesLibres cl WHERE EXTRACT(DOW FROM cl.fecha) = :dia AND current_timestamp() < cl.fecha)");
        }
        if (!StringUtils.isBlank(filter.getHoradia()) && !StringUtils.isBlank(filter.getDia())) {
            where.append(
                    " AND EXISTS (SELECT cl.fecha FROM p.clasesLibres cl WHERE (cast(to_char(cl.fecha,'HH24') as int) between :horaInicio and :horaFin) AND EXTRACT(DOW FROM cl.fecha) = :dia AND current_timestamp() < cl.fecha)");
        }
        queryStr.append(where.toString());

        Query q = getSession().createQuery("SELECT p" + queryStr.toString() + " ORDER BY p.apellidos");
        setParametersProfesor(filter, q);
        q.setMaxResults(filter.getPageable().getPageSize());
        q.setFirstResult(filter.getPageable().getPageNumber() * filter.getPageable().getPageSize());

        Query count = entityManager.unwrap(Session.class).createQuery("SELECT COUNT(p.id)" + queryStr.toString());
        setParametersProfesor(filter, count);
        long numeroElementos = (long) count.uniqueResult();

        return new PageImpl<>(q.list(), filter.getPageable(), numeroElementos);
    }

    //

    private void setParametersProfesor(ProfesorFilter filter, Query q) {
        if (filter.getIdioma() != null)
            q.setParameter("idioma", filter.getIdioma());
        if (filter.getNivel() != null)
            q.setParameter("nivel", filter.getNivel());
        if (filter.getPaises() != null && filter.getPaises().size() > 0)
            q.setParameterList("paises", filter.getPaises());
        /*
         * if (profesorFilter.getDisponibilidad() != null &&
         * !profesorFilter.getDisponibilidad().equals(Disponibilidad.AMBOS)) q.setParameter("disponibilidad",
         * profesorFilter.getDisponibilidad());
         */
        if (filter.getCapacidades() != null && filter.getCapacidades().size() > 0)
            q.setParameterList("capacidades", filter.getCapacidades());
        if (!StringUtils.isBlank(filter.getHoradia())) {
            switch (Horadia.valueOf(filter.getHoradia())) {
                case MADRUGADA:
                    q.setParameter("horaInicio", 0);
                    q.setParameter("horaFin", 5);
                    break;
                case MANHANA:
                    q.setParameter("horaInicio", 6);
                    q.setParameter("horaFin", 11);
                    break;
                case TARDE:
                    q.setParameter("horaInicio", 12);
                    q.setParameter("horaFin", 17);
                    break;
                case NOCHE:
                    q.setParameter("horaInicio", 18);
                    q.setParameter("horaFin", 23);
                    break;
            }
        }
        if (!StringUtils.isBlank(filter.getDia())) {
            switch (Dia.valueOf(filter.getDia())) {
                case LUNES:
                    q.setParameter("dia", 1);
                    break;
                case MARTES:
                    q.setParameter("dia", 2);
                    break;
                case MIERCOLES:
                    q.setParameter("dia", 3);
                    break;
                case JUEVES:
                    q.setParameter("dia", 4);
                    break;
                case VIERNES:
                    q.setParameter("dia", 5);
                    break;
                case SABADO:
                    q.setParameter("dia", 6);
                    break;
                case DOMINGO:
                    q.setParameter("dia", 0);
                    break;
            }
        }
    }

}
