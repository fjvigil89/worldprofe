package es.enxenio.sife1701.controller.admin;

import es.enxenio.sife1701.config.jwt.JWTConfigurer;
import es.enxenio.sife1701.config.jwt.TokenProvider;
import es.enxenio.sife1701.controller.custom.LoginRequest;
import es.enxenio.sife1701.controller.custom.util.JWTToken;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_ACCOUNT)
public class AccountAdminController {

    private final Logger log = LoggerFactory.getLogger(AccountAdminController.class);

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    //FIXME: @PreAuthorize(ConstantesRest.PERMIT_ALL)
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    //FIXME: @PreAuthorize(ConstantesRest.PERMIT_ALL)
    public ResponseEntity authorize(@Valid @RequestBody LoginRequest login, HttpServletResponse response) {
        login.setUsername(login.getUsername().toLowerCase());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            login.getUsername(), login.getPassword() + "{" + login.getUsername() + "}");

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            boolean rememberMe = login.getRememberMe() != null ? login.getRememberMe() : false;
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);

            usuarioService.nuevoAcceso(login.getUsername());

            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>(
                Collections.singletonMap("AuthenticationException", exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * GET  /account : Obtiene el usuario actual
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getAccount(Locale locale) {
        Usuario usuario = usuarioService.getUserWithAuthorities();
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

}
