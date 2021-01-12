package es.enxenio.sife1701.controller.publica.usuario;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.config.util.SecurityUtils;
import es.enxenio.sife1701.controller.custom.PasswordDTO;
import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.exceptions.CreditosInsuficientesException;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.exception.ContrasenaIncorrectaException;
import es.enxenio.sife1701.model.usuario.exception.EmailAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.exception.LoginAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.exception.SkypeAlreadyUsedException;
import es.enxenio.sife1701.util.ConstantesRest;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Created by crodriguez on 28/09/2016.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/perfil")
public class PerfilController {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private MailService mailService;

    @JsonView(JsonViews.List.class)
    @RequestMapping(value = "/queryByEmail", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> queryByEmail(@RequestParam String query) {

        return new ResponseEntity<>(usuarioService.filterByEmail(query, SecurityUtils.getCurrentUserEmail()), HttpStatus.OK);
    }

    @JsonView(JsonViews.List.class)
    @RequestMapping(value = "/queryByLogin", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> queryByLogin(@RequestParam String query) {

        return new ResponseEntity<>(usuarioService.filterByLogin(query, SecurityUtils.getCurrentUserEmail()), HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> register(@Valid @RequestBody Usuario usuario, Locale locale, HttpServletRequest request) {

        try {
            usuarioService.registrar(usuario, CodeUtil.getUrlBase(request), locale);

            if (usuario instanceof Alumno && ((Alumno) usuario).getEmpresa() == null) {
                mailService.sendValidationEmail(usuario, CodeUtil.getUrlBase(request), locale);
            } else {
                mailService.sendCreacionMail(usuario, CodeUtil.getUrlBase(request), locale);
            }

        } catch (EmailAlreadyUsedException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("registro.error.emailalreadyused"), HttpStatus.BAD_REQUEST);
        } catch (LoginAlreadyUsedException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("registro.error.loginalreadyused"), HttpStatus.BAD_REQUEST);
        } catch (EnvioEmailException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("registro.error.emailerror"), HttpStatus.BAD_REQUEST);
        } catch (SkypeAlreadyUsedException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("registro.error.skypealreadyused"), HttpStatus.BAD_REQUEST);
        } catch (CreditosInsuficientesException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("registro.error.creditosinsuficientes"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PreAuthorize(ConstantesRest.IS_AUTHENTICATED)
    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> edit(@Valid @RequestBody Usuario usuario) {

        try {
            usuarioService.update(usuario);
        } catch (LoginAlreadyUsedException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("registro.error.loginalreadyused"), HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("perfil.editar.error.instancenotfound"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(usuario, HeaderUtil.createAlert("perfil.editar.success"), HttpStatus.OK);
    }

    @PreAuthorize(ConstantesRest.IS_AUTHENTICATED)
    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestBody PasswordDTO passwordDTO) {
        try {
            usuarioService.changePassword(passwordDTO.getPassword(), passwordDTO.getNewpassword());
        } catch (ContrasenaIncorrectaException e) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("admin.changepassword.error.incorrect"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HeaderUtil.createAlert("admin.changepassword.success"), HttpStatus.OK);
    }

    @PreAuthorize(ConstantesRest.IS_AUTHENTICATED)
    @RequestMapping(value = "/baja", method = RequestMethod.GET)
    public ResponseEntity darseDeBaja() {
        try {
            String email = SecurityUtils.getCurrentUserEmail();
            usuarioService.baja(email);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HeaderUtil.createAlert("miespacio.miperfil.baja.success"), HttpStatus.OK);
    }

}
