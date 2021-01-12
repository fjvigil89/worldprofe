package es.enxenio.sife1701.model.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

/**
 * Métodos comunes para generalizar código.
 * Created by crodriguez on 04/10/2016.
 */
@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity, U extends Serializable> extends JpaRepository<T, U> {

    boolean existsByUrl(@Param("url") String url, @Param("id") Long id);

}
