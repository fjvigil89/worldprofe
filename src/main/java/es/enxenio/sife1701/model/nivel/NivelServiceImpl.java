package es.enxenio.sife1701.model.nivel;

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
public class NivelServiceImpl implements NivelService {

    @Inject
    private NivelRepository nivelRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Nivel get(Long id) {
        return nivelRepository.findOne(id);
    }

    @Override
    public Page<Nivel> filter(PageableFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nivel> query() {
        return nivelRepository.findAll();
    }

    // Gestión

    @Override
    public Nivel save(Nivel nivel) throws InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Nivel update(Nivel nivel) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }
}
