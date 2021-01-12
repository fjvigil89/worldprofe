package es.enxenio.sife1701.config.util;

import es.enxenio.sife1701.model.usuario.Rol;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserEmail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        if (authorities == null) {
            return false;
        }
        for (GrantedAuthority authority : authorities) {
            if (!authority.getAuthority().equals(Rol.ROLE_ADMIN.name())) {
                return false;
            }
        }
        return true;
    }

    /**
     * If the current user has a specific authority (security role).
     *
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String rol) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority(rol));
    }

    /**
     * If the current user has a any of the authorities (security role).
     *
     * @param roles the authorities to check
     * @return true if the current user has any of the authorities, false otherwise
     */
    public static boolean isCurrentUserInRoles(String[] roles) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (String rol : roles) {
                authorities.add(new SimpleGrantedAuthority(rol));
            }
            return authorities.containsAll(authentication.getAuthorities());
        }
        return false;
    }
}
