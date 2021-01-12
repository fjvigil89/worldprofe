package es.enxenio.sife1701.model.usuario.alumno;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findOneBySkype(String skype);

}
