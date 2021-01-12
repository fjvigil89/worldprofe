package es.enxenio.sife1701.model.zonahoraria;

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
public class ZonaHorariaServiceImpl implements ZonaHorariaService {

    @Inject
    private ZonaHorariaRepository zonaHorariaRepository;


    @Override
    public List<ZonaHoraria> findAll() {
        return zonaHorariaRepository.findAll();
    }

    @Override
    public ZonaHoraria get(Long id) {
        return zonaHorariaRepository.findOne(id);
    }

    @Override
    public Page<ZonaHoraria> filter(PageableFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ZonaHoraria save(ZonaHoraria zonaHoraria) throws InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ZonaHoraria update(ZonaHoraria zonaHoraria) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }
}
