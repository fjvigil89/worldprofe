package es.enxenio.sife1701.model.usuario.profesor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Optional<Profesor> findOneBySkype(String skype);

}
