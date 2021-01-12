package es.enxenio.sife1701.model.estatica;


import es.enxenio.sife1701.model.estatica.estatica.Estatica;
import es.enxenio.sife1701.model.estatica.estaticai18n.EstaticaI18n;

import java.util.List;

public interface EstaticaService {

    // Cpmsiñta

    Estatica get(Long id);

    Estatica getByNombre(String nombre);

    EstaticaI18n getByNombreIdioma(String nombre, Estatica.Idioma idioma);

    List<Estatica> findAll();

    // Gestión

    void save(Estatica estatica);
}
