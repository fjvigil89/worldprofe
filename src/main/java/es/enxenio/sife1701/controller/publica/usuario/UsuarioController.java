package es.enxenio.sife1701.controller.publica.usuario;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.config.util.SecurityUtils;
import es.enxenio.sife1701.controller.custom.filter.ClaseLibreFilter;
import es.enxenio.sife1701.controller.custom.filter.ProfesorFilter;
import es.enxenio.sife1701.controller.custom.filter.ReservaFilter;
import es.enxenio.sife1701.controller.custom.filter.UsuarioFilter;
import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.usuario.Rol;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import es.enxenio.sife1701.model.usuario.empresa.Empresa;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import es.enxenio.sife1701.model.zonahoraria.ZonaHoraria;
import es.enxenio.sife1701.model.zonahoraria.ZonaHorariaService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by crodriguez on 28/09/2016.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/user")
public class UsuarioController {

    @Inject
    private UsuarioService usuarioService;

    @Autowired
    private ZonaHorariaService zonaHorariaService;

    //TODO: Anotar JsonView a cada método

    @RequestMapping(value = "/findAllProfesor", method = RequestMethod.POST)
    public ResponseEntity<Page<Profesor>> findAllProfesor(@Valid @RequestBody(required=false) ProfesorFilter pageable) {
        if (pageable != null) {
            return new ResponseEntity<>(usuarioService.findAllProfesor(pageable), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(value = "/profesor/id", method = RequestMethod.GET)
    public ResponseEntity<Usuario> getProfesorById(@RequestParam Long id) {

        Usuario usuario = usuarioService.get(id);
        if (usuario == null && !usuario.getRol().equals(Rol.ROLE_PROFESOR)) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("admin.list-users.error.notfound"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUsuarioPorReserva", method = RequestMethod.GET)
    public ResponseEntity<Usuario> getUsuarioPorReserva(ReservaFilter reservaFilter) {
        Usuario usuario = null;
        if (!reservaFilter.getRol().equals(Rol.ROLE_PROFESOR)) {
            usuario = usuarioService.getProfesorPorReserva(reservaFilter.getId());
        }
        if (!reservaFilter.getRol().equals(Rol.ROLE_ALUMNO)) {
            usuario = usuarioService.getAlumnoPorReserva(reservaFilter.getId());
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/getProfesorPorClaseLibre", method = RequestMethod.GET)
    public ResponseEntity<Usuario> getProfesorPorClaseLibre(ClaseLibreFilter claseLibreFilter) {
        return new ResponseEntity<>(usuarioService.getProfesorPorClaseLibre(claseLibreFilter.getId()), HttpStatus.OK);
    }

    @JsonView(JsonViews.Details.class)
    @PreAuthorize(ConstantesRest.HAS_ROLE_TIENE_ALUMNOS)
    @RequestMapping(value = "/findAlumnosByCentro", method = RequestMethod.GET)
    public ResponseEntity<Page<Usuario>> findAll(UsuarioFilter pageable) {

        if (pageable != null) {
            Empresa empresa = (Empresa) usuarioService.findByEmail(SecurityUtils.getCurrentUserEmail());
            pageable.setRol(Rol.ROLE_ALUMNO);
            pageable.setEliminado(false);
            pageable.setEmpresa(empresa.getId());

            return new ResponseEntity<>(usuarioService.findAll(pageable), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(value = "/zonasHorarias", method = RequestMethod.GET)
    public ResponseEntity<List<ZonaHoraria>> query() {
        return new ResponseEntity<>(zonaHorariaService.findAll(), HttpStatus.OK);
    }

}
