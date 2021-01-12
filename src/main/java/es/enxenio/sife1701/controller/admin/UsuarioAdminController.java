package es.enxenio.sife1701.controller.admin;

import es.enxenio.sife1701.controller.custom.EmpresaDTO;
import es.enxenio.sife1701.controller.custom.ProfesorAdminDTO;
import es.enxenio.sife1701.controller.custom.UserAdminDTO;
import es.enxenio.sife1701.controller.custom.filter.ProfesorAdminFilter;
import es.enxenio.sife1701.controller.custom.filter.UsuarioFilter;
import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.model.datosfacturacion.DatosFacturacionService;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import es.enxenio.sife1701.model.usuario.empresa.EmpresaAlumnosExcel;
import es.enxenio.sife1701.model.usuario.empresa.EmpresaExcel;
import es.enxenio.sife1701.model.usuario.exception.LoginAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.profesor.ProfesorExcel;
import es.enxenio.sife1701.util.ConstantesRest;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_ADMIN + "/user")
@PreAuthorize(ConstantesRest.HAS_ROLE_ADMIN)
public class UsuarioAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DatosFacturacionService datosFacturacionService;

    @Autowired
    private MailService mailService;

    @Autowired
    private EmpresaExcel empresaExcel;

    @Autowired
    private EmpresaAlumnosExcel empresaAlumnosExcel;

    @Autowired
    private ProfesorExcel profesorExcel;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Usuario> getUser(@RequestParam String email) {

        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario == null) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("admin.list-users.error.notfound"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<Usuario> getUserId(@RequestParam Long id) {
        Usuario usuario = usuarioService.get(id);
        if (usuario == null) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("admin.list-users.error.notfound"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/getEmpresaDTO/id", method = RequestMethod.GET)
    public ResponseEntity<EmpresaDTO> getEmpresaDTO(@RequestParam Long id) {
        EmpresaDTO empresa = usuarioService.getEmpresaDTO(id);
        if (empresa == null) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("admin.empresa.error.notfound"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<Page<Usuario>> findAll(UsuarioFilter pageable) {

        if (pageable != null) {
            return new ResponseEntity<>(usuarioService.findAll(pageable), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(value = "/findProfesoresAdmin", method = RequestMethod.POST)
    public ResponseEntity<Page<ProfesorAdminDTO>> findProfesores(@Valid @RequestBody(required = false) ProfesorAdminFilter pageable) {

        if (pageable != null) {
            return new ResponseEntity<>(usuarioService.findAllProfesorAdmin(pageable), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity registrarAdministrador(@RequestBody UserAdminDTO userAdminDTO, HttpServletRequest request, Locale locale) {

        try {
            if (userAdminDTO.getId() == null) {
                usuarioService.registrarAdministrador(userAdminDTO, CodeUtil.getUrlBase(request), locale);
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("admin.admin.success.create")).build();
            } else {
                usuarioService.actualizarAdministrador(userAdminDTO);
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("admin.admin.success.edit")).build();
            }
        } catch (InstanceAlreadyExistsException e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("admin.admin.error.alreadyexists")).build();
        } catch (LoginAlreadyUsedException e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("admin.admin.error.alreadyexistslogin")).build();
        } catch (EnvioEmailException e) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("registro.error.emailerror"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity desactivar(@RequestParam Long id) {

        try {
            // Desactivar
            usuarioService.desactivar(id, true);
        } catch (InstanceNotFoundException e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("admin.list-users.delete.error")).build();
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("admin.list-users.delete.success")).build();
    }

    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public ResponseEntity eliminar(@RequestParam Long id) {

        try {
            // Eliminar
            usuarioService.delete(id);
        } catch (InstanceNotFoundException e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("admin.list-users.delete.error")).build();
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("admin.list-users.delete.success")).build();
    }

    // "Desborra" un usuario
    @RequestMapping(value = "/restaurar", method = RequestMethod.POST)
    public ResponseEntity recuperarUsuario(@RequestParam Long id) {

        try {
            usuarioService.desactivar(id, false);
        } catch (InstanceNotFoundException e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("admin.list-users.delete.error")).build();
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("admin.list-users.gestion.restaurar.success")).build();
    }

    @RequestMapping(value = "/baja", method = RequestMethod.GET)
    public ResponseEntity darseDeBaja(@RequestParam String email) {

        try {
            usuarioService.baja(email);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HeaderUtil.createAlert("miespacio.miperfil.baja.success"), HttpStatus.OK);
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public ResponseEntity enviarEmailValidacion(@RequestParam String email, HttpServletRequest request, Locale locale) {

        Usuario usuario = usuarioService.generarClaveActivacion(email);
        try {
            mailService.sendValidationEmail(usuario, CodeUtil.getUrlBase(request), locale);
        } catch (EnvioEmailException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("registro.error.emailerror"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HeaderUtil.createAlert("registro.success.email"), HttpStatus.OK);
    }

    @PreAuthorize(ConstantesRest.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/activar", method = RequestMethod.POST)
    public ResponseEntity activarUsuario(@RequestParam String email, HttpServletRequest request, Locale locale) {

        Usuario usuario = usuarioService.activarCuenta(email, true);
        try {
            mailService.sendActivationEmail(usuario, CodeUtil.getUrlBase(request), locale);
        } catch (EnvioEmailException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("registro.error.emailerror"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HeaderUtil.createAlert("admin.list-users.gestion.activar.success"), HttpStatus.OK);
    }

    @RequestMapping(value = "/empresasExcel", method = RequestMethod.GET)
    public void empresasExcel(Locale locale, HttpServletResponse response) throws Exception {
        response.setHeader("Content-disposition", "attachment; filename=Empresas.xlsx");
        response.setContentType("application/vnd.ms-excel");

        OutputStream out = response.getOutputStream();
        empresaExcel.generarEmpresas(locale).write(out);
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/empresaAlumnosExcel", method = RequestMethod.GET)
    public void empresaAlumnosExcel(@RequestParam Long id, Locale locale, HttpServletResponse response) throws Exception {
        response.setHeader("Content-disposition", "attachment; filename=EmpresaAlumnos-" + id + ".xlsx");
        response.setContentType("application/vnd.ms-excel");

        OutputStream out = response.getOutputStream();
        empresaAlumnosExcel.generarEmpresaAlumnos(id, locale).write(out);
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/profesorExcel", method = RequestMethod.GET)
    public void profesoresExcel(@RequestParam(required = false) Boolean activos,
                                @RequestParam(required = false) Integer mes, @RequestParam(required = false) Integer ano,
                                Locale locale, HttpServletResponse response) throws Exception {
        response.setHeader("Content-disposition", "attachment; filename=Profesores.xlsx");
        response.setContentType("application/vnd.ms-excel");

        OutputStream out = response.getOutputStream();
        profesorExcel.generarProfesores(activos, mes, ano, locale).write(out);
        out.flush();
        out.close();
    }

}
