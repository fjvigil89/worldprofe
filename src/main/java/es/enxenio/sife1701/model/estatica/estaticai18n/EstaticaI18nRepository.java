package es.enxenio.sife1701.model.estatica.estaticai18n;

import es.enxenio.sife1701.model.estatica.estatica.Estatica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by jlosa on 05/06/2017.
 */
public interface EstaticaI18nRepository extends JpaRepository<EstaticaI18n, Long> {

    @Query(value = "SELECT ei" +
        " FROM EstaticaI18n ei JOIN Estatica e ON e.id = ei.estatica.id" +
        " WHERE e.nombre = :nombre AND ei.idioma = :idioma")
    EstaticaI18n findByNombreIdioma(@Param("nombre") String nombre, @Param("idioma") Estatica.Idioma idioma);

}
