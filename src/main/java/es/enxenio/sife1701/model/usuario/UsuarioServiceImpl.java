package es.enxenio.sife1701.model.usuario;

//import com.sun.javafx.binding.StringFormatter;
import es.enxenio.sife1701.config.util.SecurityUtils;
import es.enxenio.sife1701.controller.custom.EmpresaDTO;
import es.enxenio.sife1701.controller.custom.ProfesorAdminDTO;
import es.enxenio.sife1701.controller.custom.UserAdminDTO;
import es.enxenio.sife1701.controller.custom.filter.ProfesorAdminFilter;
import es.enxenio.sife1701.controller.custom.filter.ProfesorFilter;
import es.enxenio.sife1701.controller.custom.filter.UsuarioFilter;
import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.model.datosfacturacion.DatosFacturacionService;
import es.enxenio.sife1701.model.exceptions.CreditosInsuficientesException;
import es.enxenio.sife1701.model.social.SocialService;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.alumno.AlumnoRepository;
import es.enxenio.sife1701.model.usuario.empresa.Empresa;
import es.enxenio.sife1701.model.usuario.exception.ContrasenaIncorrectaException;
import es.enxenio.sife1701.model.usuario.exception.EmailAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.exception.LoginAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.exception.SkypeAlreadyUsedException;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import es.enxenio.sife1701.model.usuario.profesor.ProfesorIdiomaRepository;
import es.enxenio.sife1701.model.usuario.profesor.ProfesorRepository;
import es.enxenio.sife1701.model.util.CodeUtilModel;
import es.enxenio.sife1701.model.util.Imagen;
import es.enxenio.sife1701.util.ConstantesModel;
import es.enxenio.sife1701.util.RandomUtil;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import es.enxenio.sife1701.util.upload.ArchivoEntidadHelper;
import es.enxenio.sife1701.util.upload.ImagenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by jlosa on 25/08/2017.
 */
@Service("usuarioService")
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired(required = false)
    private UsuarioRepository usuarioRepository;

    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    private ArchivoEntidadHelper archivoEntidadHelper;

    @Inject
    private DatosFacturacionService datosFacturacionService;

    @Inject
    private ProfesorIdiomaRepository profesorIdiomaRepository;

    @Inject
    private ProfesorRepository profesorRepository;

    @Inject
    private AlumnoRepository alumnoRepository;

    @Inject
    private SocialService socialService;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Usuario get(Long id) {
        return usuarioRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByEmail(String email) {
        return usuarioRepository.findOneByEmail(email).map(usuario -> usuario).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(UsuarioFilter filter) {
        return usuarioDao.filter(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfesorAdminDTO> findAllProfesorAdmin(ProfesorAdminFilter filter) {
        return usuarioDao.filterProfesorAdmin(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Profesor> findAllProfesor(ProfesorFilter filter) {
        return usuarioDao.filterProfesor(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> filterByEmail(String query, String emailExcluido) {
        return usuarioRepository.filterByEmail(query, emailExcluido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> filterByLogin(String query, String emailExcluido) {
        return usuarioRepository.filterByLogin(query, emailExcluido);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUserWithAuthorities() {
        String email = SecurityUtils.getCurrentUserEmail();
        return usuarioRepository.findOneByEmail(email).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Profesor getProfesorPorReserva(Long id) {
        return usuarioRepository.getProfesorPorReserva(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Alumno getAlumnoPorReserva(Long id) {
        return usuarioRepository.getAlumnoPorReserva(id);
    }

    @Override
    public Profesor getProfesorPorClaseLibre(Long id) {
        return usuarioRepository.getProfesorPorClaseLibre(id);
    }

    @Override
    public EmpresaDTO getEmpresaDTO(Long id) {
        return usuarioRepository.getEmpresaDTO(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findOneByEmail(email).map(u -> u)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario " + email + " no existe"));

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(),
            true, true, true,
            usuario.isValidado() && usuario.isActivado() && !usuario.isEliminado(),
            Arrays.asList(new SimpleGrantedAuthority(usuario.getRol().name())));
    }


    // Gestión

    @Override
    public Usuario registrar(Usuario usuario, String baseUrl, Locale locale)
        throws EmailAlreadyUsedException, LoginAlreadyUsedException, EnvioEmailException, SkypeAlreadyUsedException, CreditosInsuficientesException {
        log.debug("Registrando usuario {}", usuario.getEmail());
        usuario.setEmail(usuario.getEmail().toLowerCase());
        if (CodeUtil.notEmpty(usuario.getEmail()) && usuarioRepository.findOneByEmail(usuario.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }
        if (CodeUtil.notEmpty(usuario.getLogin()) && usuarioRepository.findOneByLogin(usuario.getLogin()).isPresent()) {
            throw new LoginAlreadyUsedException();
        }
        if ((usuario instanceof Profesor) && CodeUtil.notEmpty(((Profesor) usuario).getSkype()) && profesorRepository.findOneBySkype(((Profesor) usuario).getSkype()).isPresent()) {
            throw new SkypeAlreadyUsedException();
        }
        if ((usuario instanceof Alumno) && CodeUtil.notEmpty(((Alumno) usuario).getSkype()) && alumnoRepository.findOneBySkype(((Alumno) usuario).getSkype()).isPresent()) {
            throw new SkypeAlreadyUsedException();
        }

        if (usuario instanceof Alumno) {
            ((Alumno) usuario).setCreditosTotales(((Alumno) usuario).getCreditosDisponibles());
            ((Alumno) usuario).setCreditosConsumidos(0);
        }

        if ((usuario instanceof Alumno) && ((Alumno) usuario).getEmpresa() != null) {
            Empresa empresa = (Empresa) usuarioRepository.findOne(((Alumno) usuario).getEmpresa().getId());
            if (empresa.getCreditosDisponibles() >= ((Alumno) usuario).getCreditosTotales() /*&& empresa.getCaducidad().isAfter(ZonedDateTime.now())*/) {
                empresa.setCreditosDisponibles(empresa.getCreditosDisponibles() - ((Alumno) usuario).getCreditosTotales());
                empresa.setCreditosDistribuidos(empresa.getCreditosDistribuidos() + ((Alumno) usuario).getCreditosTotales());
                usuarioRepository.save(empresa);
            } else {
                throw new CreditosInsuficientesException();
            }
        }

        if (usuario instanceof Empresa) {
            ((Empresa) usuario).setCreditosTotales(((Empresa) usuario).getCreditosDisponibles());
            ((Empresa) usuario).setCreditosDistribuidos(0);
        }

        usuario.setPassword(CodeUtilModel.passwordEncoder(usuario.getContrasenaClaro(), usuario.getEmail()));

        usuario.setValidado(false);
        usuario.setClaveActivacion(RandomUtil.generateActivationKey());

        usuarioRepository.save(usuario);

        if ((usuario instanceof Profesor) && (((Profesor) usuario).getIdiomas() != null)) {
            profesorIdiomaRepository.save(((Profesor) usuario).getIdiomas());
        }

        saveFotoPerfil(usuario.getImagen(), usuario);

        if (usuario instanceof Alumno && ((Alumno) usuario).getEmpresa() == null) {
        } else {
            solicitarContrasena(usuario.getEmail(), false);
        }

        return usuario;
    }

    @Override
    public Usuario generarClaveActivacion(String email) {
        Usuario usuario = findByEmail(email);
        usuario.setClaveActivacion(RandomUtil.generateActivationKey());
        usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) throws InstanceNotFoundException, LoginAlreadyUsedException {
        Usuario u = get(usuario.getId());
        if (u == null) {
            throw new InstanceNotFoundException();
        }
        if (CodeUtil.notEmpty(usuario.getLogin())) {
            Optional<Usuario> uAux = usuarioRepository.findOneByLogin(usuario.getLogin());
            if (uAux.isPresent() && !uAux.get().getId().equals(usuario.getId())) {
                throw new LoginAlreadyUsedException();
            }
        }
        if (usuario instanceof Alumno) {
            // Solo el administrador puede modificar el número de creditos disponibles
            if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
                if (!((Alumno) usuario).getCreditosDisponibles().equals(((Alumno) u).getCreditosDisponibles())) {
                    if (((Alumno) usuario).getEmpresa() != null) {
                        int diferenciaCreditos = ((Alumno) usuario).getCreditosDisponibles() - ((Alumno) u).getCreditosDisponibles();
                        Empresa empresa = (Empresa) usuarioRepository.findOne(((Alumno) usuario).getEmpresa().getId());
                        empresa.setCreditosDisponibles(empresa.getCreditosDisponibles() - diferenciaCreditos);
                        empresa.setCreditosDistribuidos(empresa.getCreditosDistribuidos() + diferenciaCreditos);
                        usuarioRepository.save(empresa);
                    }
                    int diferenciaCreditos = ((Alumno) usuario).getCreditosDisponibles() - ((Alumno) u).getCreditosDisponibles();
                    ((Alumno) usuario).setCreditosTotales(((Alumno) usuario).getCreditosTotales() + diferenciaCreditos);
                }
            } else {
                ((Alumno) usuario).setCreditosTotales(((Alumno) u).getCreditosTotales());
                ((Alumno) usuario).setCreditosDisponibles(((Alumno) u).getCreditosDisponibles());
            }
        }

        if (usuario instanceof Empresa) {
            if (((Empresa) u).getCreditosDisponibles() == null) {
                ((Empresa) u).setCreditosDisponibles(0);
            }
            if (((Empresa) usuario).getCreditosDisponibles() == null) {
                ((Empresa) usuario).setCreditosDisponibles(0);
            }
            if (((Empresa) usuario).getCreditosTotales() == null) {
                ((Empresa) usuario).setCreditosTotales(0);
            }
            // Solo el administrador puede modificar el número de creditos disponibles
            if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
                if (!((Empresa) usuario).getCreditosDisponibles().equals(((Empresa) u).getCreditosDisponibles())) {
                    int diferenciaCreditos = ((Empresa) usuario).getCreditosDisponibles() - ((Empresa) u).getCreditosDisponibles();
                    ((Empresa) usuario).setCreditosTotales(((Empresa) usuario).getCreditosTotales() + diferenciaCreditos);
                }
            } else {
                ((Empresa) usuario).setCreditosTotales(((Empresa) u).getCreditosTotales());
                ((Empresa) usuario).setCreditosDisponibles(((Empresa) u).getCreditosDisponibles());
            }
        }

        u.update(usuario);
        saveFotoPerfil(usuario.getImagen(), u);
        return usuarioRepository.save(u);
    }

    private void saveFotoPerfil(Imagen imagen, Usuario usuario) {
        if (imagen == null) {
            imagen = new Imagen();
        }
        String path = archivoEntidadHelper.gestionarArchivo(imagen.getEliminar(), imagen.getArchivoTemporal(), imagen.getPath(),
            usuario.getFoto(), ConstantesModel.URL_CARPETA_USUARIOS_PERFIL, usuario.getId());

        if (path != null) {
            archivoEntidadHelper.guardarThumbnailsImagen(ImagenUtil.getThumbnailsPerfil(),
                String.format(ConstantesModel.URL_CARPETA_USUARIOS_PERFIL, usuario.getId())+"/"+path,
            		//StringFormatter.format(ConstantesModel.URL_CARPETA_USUARIOS_PERFIL, usuario.getId()).getValue() + "/" + path,
                String.format(ConstantesModel.URL_CARPETA_USUARIOS_PERFIL, usuario.getId()));
                //StringFormatter.format(ConstantesModel.URL_CARPETA_USUARIOS_PERFIL, usuario.getId()).getValue());
        }
        usuario.setFoto(path);
    }

    @Override
    public Usuario validarCuenta(String key) throws InstanceNotFoundException {
        log.debug("Validando usuario con clave de activación {}", key);
        return usuarioRepository.findOneByClaveActivacion(key)
            .map(user -> {
                user.setValidado(true);
                user.setClaveActivacion(null);
                usuarioRepository.save(user);
                log.debug("Usuario validado: {}", user);
                return user;
            }).orElseThrow(InstanceNotFoundException::new);
    }

    @Override
    public Usuario activarCuenta(String email, boolean activar) {
        Usuario usuario = findByEmail(email);
        usuario.setActivado(activar);
        usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> solicitarContrasena(String email, boolean validado) {
        return usuarioRepository.findOneByEmail(email)
            .filter(Usuario::isActivado)
            .filter(u -> u.isValidado() == validado)
            .filter(u -> !u.isEliminado())
            .map(user -> {
                user.setClaveReset(RandomUtil.generateResetKey());
                user.setFechaReset(ZonedDateTime.now());
                usuarioRepository.save(user);
                return user;
            });
    }

    @Override
    public Optional<Usuario> completarSolicitudContrasena(String email, String newPassword, String key) {
        log.debug("Cambiando contraseña de usuario {} con clave de reset {}", email, key);
        return usuarioRepository.findOneByClaveResetAndEmail(key, email)
            .filter(user -> {
                ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                return user.getFechaReset().isAfter(oneDayAgo);
            }).map(user -> {
                user.setPassword(CodeUtilModel.passwordEncoder(newPassword, user.getEmail()));
                user.setClaveReset(null);
                user.setFechaReset(null);
                user.setValidado(true);
                usuarioRepository.save(user);
                return user;
            });
    }

    @Override
    public Usuario registrarAdministrador(UserAdminDTO usuarioDTO, String baseUrl, Locale locale)
        throws InstanceAlreadyExistsException, EnvioEmailException, LoginAlreadyUsedException {
        if (usuarioRepository.findOneByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new InstanceAlreadyExistsException();
        }
        if (CodeUtil.notEmpty(usuarioDTO.getLogin()) && usuarioRepository.findOneByLogin(usuarioDTO.getLogin()).isPresent()) {
            throw new LoginAlreadyUsedException();
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setActivado(true);
        usuario.setValidado(true);
        usuario.setFechaRegistro(ZonedDateTime.now());

        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String encryptedPassword = encoder.encodePassword(usuarioDTO.getPassword(), usuario.getEmail());
        usuario.setPassword(encryptedPassword);

        return usuarioRepository.save(usuario);
    }

    @Override
    public void actualizarAdministrador(UserAdminDTO usuarioDTO) {
        // unused
        Usuario usuario = findByEmail(usuarioDTO.getEmail());
        // FIXME: Comprobar login duplicado
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setRol(usuarioDTO.getRol());
        usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        Usuario usuario = usuarioRepository.findOne(id);
        if (usuario == null) {
            throw new InstanceNotFoundException();
        }
        usuarioRepository.delete(id);
    }

    @Override
    public void desactivar(Long id, boolean eliminar) throws InstanceNotFoundException {
        Usuario usuario = usuarioRepository.findOne(id);
        if (usuario == null) {
            throw new InstanceNotFoundException();
        }
        // Borrado lógico (desactivar)
        usuario.setEliminado(eliminar);
        usuarioRepository.save(usuario);

        // Desactivamos los usuarios de la empresa
        if (usuario instanceof Empresa && eliminar) {
            List<Usuario> alumnos = usuarioRepository.findAlumnosByEmpresa(usuario.getEmail());
            for (Usuario usuarioEmpresa : alumnos) {
                desactivar(usuarioEmpresa.getId(), eliminar);
            }
        }
    }

    @Override
    public void baja(String email) throws InstanceNotFoundException {
        Usuario usuario = findByEmail(email);
        if (usuario == null) {
            throw new InstanceNotFoundException();
        }
        socialService.deleteUserSocialConnection(usuario.getEmail());

        // Anonimizamos los datos del usuario
        usuario.setEmail("email" + usuario.getId());
        usuario.setNombre("u" + usuario.getId());
        usuario.setFoto(null);
        usuario.setTelefonoMovil(null);
        usuario.setObservaciones(null);
        usuario.setBaja(true);

        // ELiminamos su carpeta
        archivoEntidadHelper.eliminarArchivos(ConstantesModel.URL_CARPETA_USUARIOS, usuario.getId());

        if (usuario instanceof Alumno) {
            ((Alumno) usuario).setApellidos("");
            ((Alumno) usuario).setSkype("skype" + usuario.getId());
        } else if (usuario instanceof Profesor) {
            ((Profesor) usuario).setApellidos("");
            ((Profesor) usuario).setSkype("skype" + usuario.getId());
            ((Profesor) usuario).setTextoPresentacion("");
            ((Profesor) usuario).setFechaNacimiento(ZonedDateTime.now());
            ((Profesor) usuario).setPais(null);
            ((Profesor) usuario).setZonaHoraria(null);
            ((Profesor) usuario).setDescripcionCapacidades("");
        } else if (usuario instanceof Empresa) {
            List<Usuario> alumnos = usuarioRepository.findAlumnosByEmpresa(usuario.getEmail());
            for (Usuario usuarioEmpresa : alumnos) {
                baja(usuarioEmpresa.getEmail());
            }
        }

        if (usuario.getDatosFacturacion() != null) {
            datosFacturacionService.delete(usuario.getDatosFacturacion().getId());
        }
        usuario.setDatosFacturacion(null);

        usuarioRepository.save(usuario);
    }

    @Override
    public void changePassword(String oldpassword, String newpassword) throws ContrasenaIncorrectaException {
        Usuario usuario = usuarioRepository.findOneByEmail(SecurityUtils.getCurrentUserEmail()).map(u -> u)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));

        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String encryptedOld = encoder.encodePassword(oldpassword, usuario.getEmail());
        if (encryptedOld.equals(usuario.getPassword())) {
            String encryptedPassword = encoder.encodePassword(newpassword, usuario.getEmail());
            usuario.setPassword(encryptedPassword);
            usuarioRepository.save(usuario);
        } else {
            throw new ContrasenaIncorrectaException();
        }
    }

    @Override
    public void nuevoAcceso(String email) {
        Usuario usuario = findByEmail(email);
        usuario.setFechaUltimoAcceso(ZonedDateTime.now());
        usuario.setAccesos(usuario.getAccesos() + 1);
        usuarioRepository.save(usuario);
    }

}
