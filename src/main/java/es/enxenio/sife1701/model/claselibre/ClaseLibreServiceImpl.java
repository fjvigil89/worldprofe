package es.enxenio.sife1701.model.claselibre;

import es.enxenio.sife1701.controller.custom.filter.ClaseLibreFilter;
import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
@Service
@Transactional
public class ClaseLibreServiceImpl implements ClaseLibreService {

    @Inject
    private ClaseLibreRepository claseLibreRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public ClaseLibre get(Long id) {
        return claseLibreRepository.findOne(id);
    }

    @Override
    public Page<ClaseLibre> filter(PageableFilter filter) {
        ZonedDateTime fechaInicio = null;
        ZonedDateTime fechaFin = null;
        if (((ClaseLibreFilter) filter).getFechaInicio() != null && ((ClaseLibreFilter) filter).getFechaFin() != null) {
            if (((ClaseLibreFilter) filter).getFechaInicio() != null) {
                fechaInicio = ZonedDateTime.parse(((ClaseLibreFilter) filter).getFechaInicio());
            }
            if (((ClaseLibreFilter) filter).getFechaFin() != null) {
                fechaFin = ZonedDateTime.parse(((ClaseLibreFilter) filter).getFechaFin());
            }
            Page<ClaseLibre> a = claseLibreRepository.filter(fechaInicio, fechaFin, ((ClaseLibreFilter) filter).getOcupada(), filter.getId(), filter.getPageable());
            return a;
        }
        return claseLibreRepository.filter(((ClaseLibreFilter) filter).getOcupada(), filter.getId(), filter.getPageable());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseLibre> query() {
        return claseLibreRepository.findAll();
    }

    // Gestión

    @Override
    public ClaseLibre save(ClaseLibre claseLibre) throws InstanceAlreadyExistsException {
        if (claseLibre.getId() == null && claseLibreRepository.existsByProfesorIdAndFecha(claseLibre.getProfesor().getId(), claseLibre.getFecha())) {
            throw new InstanceAlreadyExistsException();
        }
        return claseLibreRepository.save(claseLibre);
    }

    @Override
    public ClaseLibre update(ClaseLibre claseLibre) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        return save(claseLibre);
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        if (claseLibreRepository.findOne(id) == null) {
            throw new InstanceNotFoundException();
        }
        claseLibreRepository.delete(id);
    }

    @Override
    public void duplicarSemana(PageableFilter filter) {
        ZonedDateTime fechaInicio = null;
        ZonedDateTime fechaFin = null;
        if (((ClaseLibreFilter) filter).getFechaInicio() != null) {
            fechaInicio = ZonedDateTime.parse(((ClaseLibreFilter) filter).getFechaInicio());
        }
        if (((ClaseLibreFilter) filter).getFechaFin() != null) {
            fechaFin = ZonedDateTime.parse(((ClaseLibreFilter) filter).getFechaFin());
        }
        List<ClaseLibre> semanaActual = claseLibreRepository.filter(fechaInicio, fechaFin, filter.getId(), filter.getPageable()).getContent();
        List<ClaseLibre> semanaSiguiente = claseLibreRepository.filter(fechaInicio.plusWeeks(1), fechaFin.plusWeeks(1), filter.getId(), filter.getPageable()).getContent();

        List<ClaseLibre> listaAux = new ArrayList<>();
        for (ClaseLibre claseLibreSiguiente : semanaSiguiente) {
            for (ClaseLibre claseLibreActual : semanaActual) {
                if (claseLibreSiguiente.getFecha().equals(claseLibreActual.getFecha().plusWeeks(1))) {
                    listaAux.add(claseLibreActual);
                }
            }
        }

        List<ClaseLibre> definitiva = new ArrayList<>();
        for (ClaseLibre claseLibreAux : semanaActual) {
            if (!listaAux.contains(claseLibreAux)) {
                definitiva.add(claseLibreAux);
            }
        }

        ClaseLibre nuevaClaseLibre = null;
        for (ClaseLibre claseLibre : definitiva) {
            nuevaClaseLibre = new ClaseLibre();
            nuevaClaseLibre.setFecha(claseLibre.getFecha().plusWeeks(1));
            nuevaClaseLibre.setProfesor(claseLibre.getProfesor());
            nuevaClaseLibre.setOcupada(false);
            claseLibreRepository.save(nuevaClaseLibre);
        }

    }

}
