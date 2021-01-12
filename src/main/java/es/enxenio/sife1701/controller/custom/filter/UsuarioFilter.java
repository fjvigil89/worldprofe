package es.enxenio.sife1701.controller.custom.filter;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.model.usuario.Rol;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.empresa.Empresa;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import es.enxenio.sife1701.model.util.CodeUtilModel;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static es.enxenio.sife1701.model.usuario.Rol.ROLE_ALUMNO;

/**
 * Created by jlosa on 25/08/2017.
 */
public class UsuarioFilter extends PageableFilter {

    private Rol rol;

    private List<Rol> roles;

    private Boolean validado;

    private Boolean activado;

    private Boolean eliminado;

    // true -> solo usuarios activos. null o false -> todos los usuarios.
    private Boolean activos;

    private Long empresa;

    //

    public UsuarioFilter() {
    }

    //

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Boolean getActivos() {
        return activos;
    }

    public void setActivos(Boolean activos) {
        this.activos = activos;
    }

    public Long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Long empresa) {
        this.empresa = empresa;
    }
    //

    public DetachedCriteria toDetachedCriteria() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Usuario.class);

        // FIXME: La ordención por nombre no se realiza por apellidos también. Añadir una segunda propiedad de
        // ordenación al PageableFilter?

        if (this.getRol() != null) {
            switch (this.getRol().name()) {
                case "ROLE_ALUMNO":
                    criteria = DetachedCriteria.forClass(Alumno.class);
                    break;
                case "ROLE_PROFESOR":
                    criteria = DetachedCriteria.forClass(Profesor.class);
                    break;
                case "ROLE_EMPRESA":
                    criteria = DetachedCriteria.forClass(Empresa.class);
                    break;
            }
        }

        criteria.setFetchMode("datosFacturacion", FetchMode.SELECT);
        criteria.setFetchMode("capacidades", FetchMode.SELECT);
        criteria.setFetchMode("idiomas", FetchMode.SELECT);
        criteria.setFetchMode("clasesLibres", FetchMode.SELECT);

        boolean esPersona = getRol() != null && (getRol().equals(ROLE_ALUMNO) || getRol().equals(Rol.ROLE_PROFESOR));

        // Campo genérico de búsqueda
        if (CodeUtil.notEmpty(getGenericFilter())) {
            criteria.add(Restrictions.or(
                // FIXME
                // CodeUtilModel.textRestriction("email", getFilterToLike(getGenericFilter())),
                esPersona ? CodeUtilModel.textRestriction("this_1_.nombre", getFilterToLike(getGenericFilter()))
                    : Restrictions.isNull("id"),
                esPersona ? CodeUtilModel.textRestriction("apellidos", getFilterToLike(getGenericFilter()))
                    : Restrictions.isNull("id")));
        }

        if (roles != null && !roles.isEmpty()) {
            criteria.add(Restrictions.in("rol", roles));
        }

        if (rol != null) {
            criteria.add(Restrictions.eq("rol", rol));
        }

        if (validado != null) {
            criteria.add(Restrictions.eq("validado", validado));
        }

        if (activado != null) {
            criteria.add(Restrictions.eq("activado", activado));
        }

        if (eliminado != null) {
            criteria.add(Restrictions.eq("eliminado", eliminado));
        }

        if (activos != null && activos) {
            // Ni usuarios desactivados ni dados de baja
            criteria.add(Restrictions.and(Restrictions.eq("eliminado", false), Restrictions.eq("baja", false)));
        } else {
            // Cuando se marca, únicamente aparecen los eliminados y los desactivados
            criteria.add(Restrictions.or(Restrictions.eq("eliminado", true), Restrictions.eq("baja", true)));
        }

        if (this.getRol().equals(ROLE_ALUMNO)) {
            if (empresa == null) {
                criteria.add(Restrictions.isNull("empresa"));
            } else {
                criteria.add(Restrictions.eq("empresa.id", empresa));
            }
        }

        return criteria;
    }

}
