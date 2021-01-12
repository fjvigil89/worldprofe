package es.enxenio.sife1701.model.configuracion;

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
public class ConfiguracionServiceImpl implements ConfiguracionService {

    @Inject
    private ConfiguracionRepository configuracionRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Configuracion get() {
        return configuracionRepository.findOne(1L);
    }

    @Override
    @Transactional(readOnly = true)
    public Configuracion get(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Configuracion> filter(PageableFilter filter) {
        throw new UnsupportedOperationException();
    }

    // Gestión

    @Override
    public Configuracion save(Configuracion configuracion) throws InstanceAlreadyExistsException {
        return configuracionRepository.save(configuracion);
    }

    @Override
    public Configuracion update(Configuracion configuracion) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        return save(configuracion);
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }
}
