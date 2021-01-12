package es.enxenio.sife1701.model.social;

import es.enxenio.sife1701.config.jwt.TokenProvider;
import es.enxenio.sife1701.model.usuario.Usuario;
import es.enxenio.sife1701.model.usuario.UsuarioRepository;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.util.CodeUtilModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@Transactional
public class SocialServiceImpl implements SocialService {

    private final Logger log = LoggerFactory.getLogger(SocialServiceImpl.class);

    @Inject
    private UsersConnectionRepository usersConnectionRepository;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private UsuarioRepository userRepository;

    @Inject
    private TokenProvider tokenProvider;

    @Override
    public Cookie getCookie(String email) {
        UserDetails user = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            user,
            null,
            user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String jwt = tokenProvider.createToken(authenticationToken, false);

        Cookie socialAuthCookie = new Cookie("social-authentication", jwt);
        socialAuthCookie.setPath("/");
        socialAuthCookie.setMaxAge(10);
        return socialAuthCookie;
    }

    public Usuario createSocialUser(Connection<?> connection) {
        if (connection == null) {
            log.error("Cannot create social user because connection is null");
            throw new IllegalArgumentException("Connection cannot be null");
        }
        UserProfile userProfile = connection.fetchUserProfile();
        String providerId = connection.getKey().getProviderId();
        Usuario user = createUserIfNotExist(userProfile, providerId);
        createSocialConnection(user.getEmail(), connection);
        return user;
    }

    private Usuario createUserIfNotExist(UserProfile userProfile, String providerId) {

        String email = userProfile.getEmail();

        if (!StringUtils.isBlank(email)) {
            email = email.toLowerCase();
        }
        if (StringUtils.isBlank(email)) {
            log.error("Cannot create social user because email is null");
            throw new IllegalArgumentException("Email cannot be null");
        } else {
            Optional<Usuario> user = userRepository.findOneByEmail(email);
            if (user.isPresent()) {
                log.info("User already exist associate the connection to this account");
                return user.get();
            }
        }

        String encryptedPassword = CodeUtilModel.passwordEncoder(RandomStringUtils.random(10), email);

        Alumno newUser = new Alumno();
        newUser.setEmail(email);
        newUser.setLogin(getLoginDependingOnProviderId(userProfile, providerId));
        newUser.setPassword(encryptedPassword);
        newUser.setValidado(true);
        newUser.setFechaRegistro(ZonedDateTime.now());

        // TODO: Fotografía de perfil
        // TODO: sexo, fecha nacimiento

        newUser.setNombre(userProfile.getFirstName());
        newUser.setApellidos(userProfile.getLastName());

        return userRepository.save(newUser);
    }

    /**
     * @return login en caso de que el provider sea del estilo de Twitter o Github. En caso contrario null
     * El provider de Google y Facebook devuelven un identificador como login: "12099388847393"
     */
    private String getLoginDependingOnProviderId(UserProfile userProfile, String providerId) {
        switch (providerId) {
            default:
                return null;
        }
    }

    private void createSocialConnection(String login, Connection<?> connection) {
        ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(login);
        connectionRepository.addConnection(connection);
    }

    public void deleteUserSocialConnection(String email) {
        // unused
        ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(email);
        connectionRepository.findAllConnections().keySet().forEach(connectionRepository::removeConnections);
    }
}
