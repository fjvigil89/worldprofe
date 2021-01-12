package es.enxenio.sife1701.model.paquetecreditos;

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
public class PaqueteCreditosServiceImpl implements PaqueteCreditosService {

    @Inject
    private PaqueteCreditosRepository paqueteCreditosRepository;

    @Override
    public PaqueteCreditos get(Long id) {
        return paqueteCreditosRepository.findOne(id);
    }

    @Override
    public Page<PaqueteCreditos> filter(PageableFilter filter) {
        return paqueteCreditosRepository.findAll(filter.getPageable());
    }

    @Override
    public PaqueteCreditos save(PaqueteCreditos paqueteCreditos) throws InstanceAlreadyExistsException {
        return paqueteCreditosRepository.save(paqueteCreditos);
    }

    @Override
    public PaqueteCreditos update(PaqueteCreditos paqueteCreditos) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        return save(paqueteCreditos);
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        paqueteCreditosRepository.delete(id);
    }
}
