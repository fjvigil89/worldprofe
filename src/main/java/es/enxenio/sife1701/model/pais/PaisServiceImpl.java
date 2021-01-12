package es.enxenio.sife1701.model.pais;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
@Service
@Transactional
public class PaisServiceImpl implements PaisService {

    @Inject
    private PaisRepository paisRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Pais get(Long id) {
        return paisRepository.findOne(id);
    }

    @Override
    public Page<Pais> filter(PageableFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pais> query(String filter) {
        return paisRepository.filterByNombre(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pais> getAllWithProfesores() {
        return paisRepository.getAllWithProfesores();
    }

    // Gestión

    @Override
    public Pais save(Pais pais) throws InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pais update(Pais pais) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }
}
