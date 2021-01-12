package es.enxenio.sife1701.model.social;

import es.enxenio.sife1701.model.usuario.Usuario;
import org.springframework.social.connect.Connection;

import javax.servlet.http.Cookie;

public interface SocialService {

    Cookie getCookie(String email);

    Usuario createSocialUser(Connection<?> connection);

    void deleteUserSocialConnection(String email);

}
