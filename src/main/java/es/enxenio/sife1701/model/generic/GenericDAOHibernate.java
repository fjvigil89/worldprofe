package es.enxenio.sife1701.model.generic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.SessionFactoryUtils;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * @author José Luis (26/10/2009)
 */
public class GenericDAOHibernate<E, PK extends Serializable> implements GenericDAO<E, PK> {

    @Inject
    private SessionFactory sessionFactory;

    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDAOHibernate() {
        this.entityClass = (Class<E>) ((ParameterizedType) getClass().
            getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private DataAccessException convertHibernateAccessException(HibernateException e) {
        return SessionFactoryUtils.convertHibernateAccessException(e);
    }

    protected Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (org.hibernate.HibernateException he) {
            return sessionFactory.openSession();
        }
    }

    public void create(E entity) {

        try {
            getSession().persist(entity);
        } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
    }

    public boolean exists(PK id) {

        boolean exists = false;
        try {
            exists = getSession().createCriteria(entityClass).
                add(Restrictions.idEq(id)).
                setProjection(Projections.id()).
                uniqueResult() != null;
        } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
        return exists;
    }

    @SuppressWarnings("unchecked")
    public E get(PK id) {
        E e = null;
        try {
            e = (E) getSession().get(entityClass, id);
        } catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
        return e;
    }

    public void delete(PK id) {
        try {
            getSession().delete(get(id));
        } catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }

    public void update(E entity) {
        try {
            getSession().merge(entity);
        } catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }

    public void save(E entity) {
        try {
            getSession().saveOrUpdate(entity);
        } catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }

}
