package es.enxenio.sife1701.model.usuario;

import es.enxenio.sife1701.controller.custom.ProfesorAdminDTO;
import es.enxenio.sife1701.controller.custom.filter.ProfesorAdminFilter;
import es.enxenio.sife1701.controller.custom.filter.ProfesorFilter;
import es.enxenio.sife1701.controller.custom.filter.UsuarioFilter;
import es.enxenio.sife1701.model.generic.GenericDAO;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import org.springframework.data.domain.Page;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface UsuarioDao extends GenericDAO<Usuario, Long> {

    Page<Usuario> filter(UsuarioFilter filter);

    Page<ProfesorAdminDTO> filterProfesorAdmin(ProfesorAdminFilter filter);

    Page<Profesor> filterProfesor(ProfesorFilter filter);

}
