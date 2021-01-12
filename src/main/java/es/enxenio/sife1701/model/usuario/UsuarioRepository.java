package es.enxenio.sife1701.model.usuario;

import es.enxenio.sife1701.controller.custom.EmpresaDTO;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findOneByEmail(String email);

    Optional<Usuario> findOneByLogin(String login);

    Optional<Usuario> findOneByClaveActivacion(String clave);

    Optional<Usuario> findOneByClaveResetAndEmail(String clave, String email);

    List<Usuario> findByEmailContaining(String email);

    @Query(value = "SELECT u FROM Usuario u" +
        " WHERE (:email is null OR u.email <> :email)" +
        " AND lower(unaccent(u.email)) like lower(unaccent(concat('%', :query, '%')))" +
        " ORDER BY u.email")
    List<Usuario> filterByEmail(@Param("query") String query, @Param("email") String email);

    @Query(value = "SELECT u FROM Usuario u" +
        " WHERE (:login is null OR u.email <> :login)" +
        " AND lower(unaccent(u.login)) like lower(unaccent(concat('%', :query, '%')))" +
        " ORDER BY u.login")
    List<Usuario> filterByLogin(@Param("query") String query, @Param("login") String login);

    @Query(value = "SELECT r.alumno FROM Reserva r WHERE r.id = :id")
    Alumno getAlumnoPorReserva(@Param("id") Long id);

    @Query(value = "SELECT r.profesor FROM Reserva r WHERE r.id = :id")
    Profesor getProfesorPorReserva(@Param("id") Long id);

    @Query(value = "SELECT cl.profesor FROM ClaseLibre cl WHERE cl.id = :id")
    Profesor getProfesorPorClaseLibre(@Param("id") Long id);

    @Query(value = "SELECT new es.enxenio.sife1701.controller.custom.EmpresaDTO(d.nombre, e.creditosTotales, e.creditosDisponibles, e.creditosDistribuidos)" +
        " FROM Empresa e" +
        " LEFT JOIN e.datosFacturacion d WHERE e.id = :id")
    EmpresaDTO getEmpresaDTO(@Param("id") Long id);

    @Query(value = "SELECT new es.enxenio.sife1701.controller.custom.EmpresaDTO(" +
        " d.nombre, e.creditosTotales, e.creditosDisponibles, e.creditosDistribuidos," +
        " SUM(a.creditosConsumidos), e.email, e.fechaRegistro, e.datosFacturacion.nif)" +
        " FROM Empresa e LEFT JOIN Alumno a ON e.id = a.empresa.id JOIN e.datosFacturacion d" +
        " WHERE e.eliminado = false GROUP BY e, d, e.email, e.fechaRegistro, e.datosFacturacion.nif" +
        " ORDER by d.nombre ASC")
    List<EmpresaDTO> getEmpresasDTO();

    @Query(value = "SELECT e.datosFacturacion.nombre FROM Alumno a JOIN a.empresa e WHERE a.id = :id")
    String getNombreEmpresaByAlumno(@Param("id") Long id);

    @Query(value = "SELECT a FROM Alumno a JOIN a.empresa e WHERE e.email = :empresaEmail")
    List<Usuario> findAlumnosByEmpresa(@Param("empresaEmail") String empresaEmail);

}
