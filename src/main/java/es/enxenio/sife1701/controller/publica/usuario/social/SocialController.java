package es.enxenio.sife1701.controller.publica.usuario.social;

import es.enxenio.sife1701.config.util.MyProperties;
import es.enxenio.sife1701.model.social.SocialService;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RestController
@RequestMapping("/social")
public class SocialController {
    private final Logger log = LoggerFactory.getLogger(SocialController.class);

    @Inject
    private SocialService socialService;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private ProviderSignInUtils providerSignInUtils;

    @Inject
    private MyProperties myProperties;

    @Inject
    private MailService mailService;

    /**
     * En caso de que no exista la conexión a la red social, se crea una nueva.
     * En caso de que tampoco exista un usuario con el e-mail obtenido, se crea uno nuevo.
     *
     * @param webRequest
     * @return
     */
    @GetMapping("/signup")
    public RedirectView signUp(WebRequest webRequest, HttpServletRequest request, HttpServletResponse response, Locale locale) {

        try {
            Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);
            Usuario usuario = socialService.createSocialUser(connection);
            try {
                mailService.sendRegistroSocialEmail(usuario, connection.getKey().getProviderId(), locale);
            } catch (EnvioEmailException e) {
            }
            response.addCookie(socialService.getCookie(usuario.getEmail()));
            URIBuilder url = URIBuilder.fromUri(myProperties.getSocial().getRedirectAfterSignUp() + "/" + connection.getKey().getProviderId());
            usuarioService.nuevoAcceso(usuario.getEmail());
            return new RedirectView(url.build().toString(), true);
        } catch (Exception e) {
            log.error("Exception creating social user: ", e);
            return new RedirectView(URIBuilder.fromUri(myProperties.getSocial().getRedirectAfterSignUp() + "/no-provider")
                .build().toString(), true);
        }
    }
}
