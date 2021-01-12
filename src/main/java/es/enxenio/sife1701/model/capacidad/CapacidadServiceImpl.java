package es.enxenio.sife1701.model.capacidad;

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
public class CapacidadServiceImpl implements CapacidadService {

    @Inject
    private CapacidadRepository capacidadRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Capacidad get(Long id) {
        return capacidadRepository.findOne(id);
    }

    @Override
    public Page<Capacidad> filter(PageableFilter filter) {
        return capacidadRepository.findAll(filter.getPageable());
    }

    // Gestión

    @Override
    public Capacidad save(Capacidad capacidad) throws InstanceAlreadyExistsException {
        return capacidadRepository.save(capacidad);
    }

    @Override
    public Capacidad update(Capacidad capacidad) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        return save(capacidad);
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        if (capacidadRepository.findOne(id) == null) {
            throw new InstanceNotFoundException();
        }
        capacidadRepository.delete(id);
    }

}
