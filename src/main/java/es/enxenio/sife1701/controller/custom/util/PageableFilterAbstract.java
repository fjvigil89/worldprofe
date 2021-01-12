package es.enxenio.sife1701.controller.custom.util;

import es.enxenio.sife1701.util.StringUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by crodriguez on 22/06/2016.
 */
public abstract class PageableFilterAbstract implements Serializable {

    private int page;

    private int size;

    private String sortProperty;

    private Sort.Direction sortDirection;

    private String genericFilter;

    public PageableFilterAbstract() {
    }

    // *****

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getGenericFilter() {
        return genericFilter;
    }

    public void setGenericFilter(String genericFilter) {
        this.genericFilter = genericFilter;
    }

    // ******

    /**
     * Genera un PageRequest a partir de los campos por separado.
     *
     * @return PageRequest
     */
    public Pageable getPageable() {
        Sort sort = null;
        if (sortProperty != null) {
            sort = new Sort(sortDirection != null ? sortDirection : Sort.Direction.ASC, sortProperty);
        }
        if (size == 0) size = Integer.MAX_VALUE;
        return new PageRequest(page, size, sort);
    }

    /**
     * Genera una Query a partir de los campos por separado.
     *
     * @return Query
     */
    public Query getQuery() {
        if (genericFilter != null && !genericFilter.isEmpty()) {
            return new Query("", StringUtil.normalize(genericFilter));
        } else {
            return new Query("", "");
        }
    }

    /**
     * @return filtro genérico para utilizar en un like.
     */
    public String getFilterToLike(String valor) {
        return "%" + StringUtil.stripAccents(valor) + "%";
    }
}
