package es.enxenio.sife1701.controller.publica.usuario;

import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.model.usuario.Rol;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import es.enxenio.sife1701.util.ConstantesRest;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by crodriguez on 28/09/2016.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/cuenta")
public class CuentaController {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private MailService mailService;

    /**
     * Validar cuenta tras un registro normal.
     *
     * @param clave campo claveActivacion de Usuario
     */
    @RequestMapping(value = "/validar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rol> validarCuenta(@RequestParam(value = "key") String clave) {
        try {
            Usuario usuario = usuarioService.validarCuenta(clave);
            return new ResponseEntity<>(usuario.getRol(), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Envía un email al usuario para recuperar la contraseña.
     *
     * @param email email del usuario
     */
    @RequestMapping(value = "/reset-contrasena/init", method = RequestMethod.GET)
    public ResponseEntity<?> requestPasswordReset(@RequestParam String email, Locale locale, HttpServletRequest request) {

        return usuarioService.solicitarContrasena(email, true)
            .map(user -> {
                try {
                    mailService.sendPasswordResetMail(user, CodeUtil.getUrlBase(request), locale);
                } catch (EnvioEmailException e) {
                    return new ResponseEntity<>(HeaderUtil.createFailureAlert("registro.error.emailerror"), HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HeaderUtil.createFailureAlert("recuperar.error"), HttpStatus.BAD_REQUEST));
    }

    /**
     * Modifica la contraseña del usuario por una nueva.
     *
     * @param email       email del usuario
     * @param key         campo claveReset del usuario
     * @param newPassword nueva contraseña
     */
    @RequestMapping(value = "/reset-contrasena/finish", method = RequestMethod.GET)
    public ResponseEntity<String> finishPasswordReset(@RequestParam String email, @RequestParam String key, @RequestParam String newPassword) {
        // FIXME: Problema cuando el email contiene el símbolo +
        return usuarioService.completarSolicitudContrasena(email, newPassword, key)
            .map(user -> new ResponseEntity<String>(HeaderUtil.createAlert("recuperar.finish.success"), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HeaderUtil.createFailureAlert("recuperar.finish.error"), HttpStatus.BAD_REQUEST));
    }


}
