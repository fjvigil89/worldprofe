package es.enxenio.sife1701.model.estatica.estatica;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jlosa on 05/06/2017.
 */
public interface EstaticaRepository extends JpaRepository<Estatica, Long> {

    Estatica findByNombre(String nombre);

}
