package es.enxenio.sife1701.model.usuario;

import es.enxenio.sife1701.controller.custom.EmpresaDTO;
import es.enxenio.sife1701.controller.custom.ProfesorAdminDTO;
import es.enxenio.sife1701.controller.custom.UserAdminDTO;
import es.enxenio.sife1701.controller.custom.filter.ProfesorAdminFilter;
import es.enxenio.sife1701.controller.custom.filter.ProfesorFilter;
import es.enxenio.sife1701.controller.custom.filter.UsuarioFilter;
import es.enxenio.sife1701.model.exceptions.CreditosInsuficientesException;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.exception.ContrasenaIncorrectaException;
import es.enxenio.sife1701.model.usuario.exception.EmailAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.exception.LoginAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.exception.SkypeAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface UsuarioService extends UserDetailsService {

    // Consulta

    Usuario get(Long id);

    Usuario findByEmail(String email);

    Page<Usuario> findAll(UsuarioFilter filter);

    Page<ProfesorAdminDTO> findAllProfesorAdmin(ProfesorAdminFilter filter);

    Page<Profesor> findAllProfesor(ProfesorFilter filter);

    List<Usuario> filterByEmail(String query, String emailExcluido);

    List<Usuario> filterByLogin(String query, String emailExcluido);

    Usuario getUserWithAuthorities();

    Profesor getProfesorPorReserva(Long id);

    Alumno getAlumnoPorReserva(Long id);

    Profesor getProfesorPorClaseLibre(Long id);

    EmpresaDTO getEmpresaDTO(Long id);

    // Gestión

    Usuario registrar(Usuario usuario, String baseUrl, Locale locale)
        throws EmailAlreadyUsedException, LoginAlreadyUsedException, EnvioEmailException, SkypeAlreadyUsedException, CreditosInsuficientesException;

    Usuario generarClaveActivacion(String email);

    Usuario update(Usuario usuario) throws InstanceNotFoundException, LoginAlreadyUsedException;

    Usuario validarCuenta(String key) throws InstanceNotFoundException;

    Usuario activarCuenta(String email, boolean activar);

    Optional<Usuario> solicitarContrasena(String mail, boolean validado);

    Optional<Usuario> completarSolicitudContrasena(String email, String newPassword, String key);

    Usuario registrarAdministrador(UserAdminDTO usuarioAdminDTO, String baseUrl, Locale locale)
        throws InstanceAlreadyExistsException, EnvioEmailException, LoginAlreadyUsedException;

    void actualizarAdministrador(UserAdminDTO usuarioDTO);

    void delete(Long id) throws InstanceNotFoundException;

    void desactivar(Long id, boolean eliminar) throws InstanceNotFoundException;

    void baja(String email) throws InstanceNotFoundException;

    void changePassword(String oldpassword, String newpassword) throws ContrasenaIncorrectaException;

    void nuevoAcceso(String email);

}
