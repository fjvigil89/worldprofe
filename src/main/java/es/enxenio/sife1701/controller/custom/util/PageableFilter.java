package es.enxenio.sife1701.controller.custom.util;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Created by crodriguez on 22/06/2016.
 */
public class PageableFilter extends PageableFilterAbstract {

    public Long id;

    public PageableFilter() {
    }

    public PageableFilter(Long id) {
        this.id = id;
    }

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //

    public DetachedCriteria toDetachedCriteria() {
        return null;
    }

}
