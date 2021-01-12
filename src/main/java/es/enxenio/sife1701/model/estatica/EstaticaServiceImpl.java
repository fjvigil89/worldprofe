package es.enxenio.sife1701.model.estatica;

import es.enxenio.sife1701.model.estatica.estatica.Estatica;
import es.enxenio.sife1701.model.estatica.estatica.EstaticaRepository;
import es.enxenio.sife1701.model.estatica.estaticai18n.EstaticaI18n;
import es.enxenio.sife1701.model.estatica.estaticai18n.EstaticaI18nRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jlosa on 05/06/2017.
 */
@Service
@Transactional
public class EstaticaServiceImpl implements EstaticaService {

    @Inject
    private EstaticaRepository estaticaRepository;

    @Inject
    private EstaticaI18nRepository estaticaI18nRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Estatica get(Long id) {
        return estaticaRepository.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Estatica getByNombre(String nombre) {
        return estaticaRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public EstaticaI18n getByNombreIdioma(String nombre, Estatica.Idioma idioma) {
        return estaticaI18nRepository.findByNombreIdioma(nombre, idioma);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estatica> findAll() {
        return estaticaRepository.findAll();
    }

    // Gestión

    @Override
    public void save(Estatica estatica) {
        estaticaRepository.save(estatica);
    }
}
