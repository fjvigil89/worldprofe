package es.enxenio.sife1701.model.idioma;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

/**
 * Created by jlosa on 25/08/2017.
 */
@Service
@Transactional
public class IdiomaServiceImpl implements IdiomaService {

    @Inject
    private IdiomaRepository idiomaRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Idioma get(Long id) {
        return idiomaRepository.findOne(id);
    }

    @Override
    public Page<Idioma> filter(PageableFilter filter) {
        // FIXME: Filtrar por el texto que se introduzca...
        return idiomaRepository.findAll(filter.getPageable());
    }

    // Gestión

    @Override
    public Idioma save(Idioma idioma) throws InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Idioma update(Idioma idioma) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }
}
