package es.enxenio.sife1701.controller.publica.usuario.social;


import es.enxenio.sife1701.config.util.MyProperties;
import es.enxenio.sife1701.model.social.SocialService;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import javax.inject.Inject;

public class CustomSignInAdapter implements SignInAdapter {

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(CustomSignInAdapter.class);

    @Inject
    private MyProperties properties;

    @Inject
    private SocialService socialService;

    @Inject
    private UsuarioService usuarioService;

    @Override
    public String signIn(String email, Connection<?> connection, NativeWebRequest request) {

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        try {
            servletWebRequest.getResponse().addCookie(socialService.getCookie(email));
            usuarioService.nuevoAcceso(email);
        } catch (AuthenticationException exception) {
            log.error("Social authentication error", exception);
        }
        return properties.getSocial().getRedirectAfterSignIn();
    }

}
