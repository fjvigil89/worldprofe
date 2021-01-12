package es.enxenio.sife1701.model.usuario.profesor;

import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.datosfacturacion.DatosFacturacionRepository;
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
public class ProfesorServiceImpl implements ProfesorService {

    @Inject
    private DatosFacturacionRepository datosFacturacionRepository;

    @Inject
    private ProfesorRepository profesorRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Profesor get(Long id) {
        return profesorRepository.findOne(id);
    }

    @Override
    public Page<Profesor> filter(PageableFilter filter) {
        throw new UnsupportedOperationException();
    }

    // Gestión

    @Override
    public Profesor save(Profesor profesor) throws InstanceAlreadyExistsException {
        datosFacturacionRepository.save(profesor.getDatosFacturacion());
        return profesorRepository.save(profesor);
    }

    @Override
    public Profesor update(Profesor profesor) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        return save(profesor);
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }

}
