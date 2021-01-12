package es.enxenio.sife1701.model.reserva;

import es.enxenio.sife1701.controller.custom.CrearReservaDTO;
import es.enxenio.sife1701.controller.custom.filter.ReservaFilter;
import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.claselibre.ClaseLibre;
import es.enxenio.sife1701.model.claselibre.ClaseLibreRepository;
import es.enxenio.sife1701.model.exceptions.CreditosInsuficientesException;
import es.enxenio.sife1701.model.usuario.UsuarioRepository;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
@Service
@Transactional
public class ReservaServiceImpl implements ReservaService {

    @Inject
    private ReservaRepository reservaRepository;

    @Inject
    private ClaseLibreRepository claseLibreRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public Reserva get(Long id) {
        return reservaRepository.findOne(id);
    }

    @Override
    public Page<Reserva> filter(PageableFilter filter) {
        ZonedDateTime fechaInicio = null;
        ZonedDateTime fechaFin = null;
        if (((ReservaFilter) filter).getFechaInicio() != null && ((ReservaFilter) filter).getFechaFin() != null) {
            if (((ReservaFilter) filter).getFechaInicio() != null) {
                fechaInicio = ZonedDateTime.parse(((ReservaFilter) filter).getFechaInicio());
            }
            if (((ReservaFilter) filter).getFechaFin() != null) {
                fechaFin = ZonedDateTime.parse(((ReservaFilter) filter).getFechaFin());
            }
            return reservaRepository.filter(((ReservaFilter) filter).getRol().toString(), fechaInicio, fechaFin,
                ((ReservaFilter) filter).getEstado(), filter.getId(),
                ((ReservaFilter) filter).getCanceladasRechazadas(), filter.getPageable());
        }
        return reservaRepository.filter(((ReservaFilter) filter).getRol().toString(),
            ((ReservaFilter) filter).getEstado(), filter.getId(),
            ((ReservaFilter) filter).getCanceladasRechazadas(), filter.getPageable());
    }

    @Override
    public Page<Reserva> filterByAdmin(PageableFilter filter) {
        return reservaRepository.filterByAdmin(((ReservaFilter) filter).getEstado(), ((ReservaFilter) filter).getRevisadas(), filter.getPageable());
    }

    @Override
    public List<Reserva> findByClaseLibre(Long id) {
        return reservaRepository.findByClaseLibre(id);
    }

    // Gestión

    @Override
    public Reserva save(Reserva reserva) throws InstanceAlreadyExistsException {
        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva crearReserva(CrearReservaDTO crearReservaDTO)
        throws InstanceAlreadyExistsException, CreditosInsuficientesException, InstanceNotFoundException {
        Alumno alumno = (Alumno) usuarioRepository.findOne(crearReservaDTO.getAlumnoId());
        ClaseLibre claseLibre = claseLibreRepository.findOne(crearReservaDTO.getClaseLibreId());
        if (crearReservaDTO.getProfesorId() == null || claseLibre == null) {
            throw new InstanceNotFoundException();
        }
        if (alumno.getCreditosDisponibles() > 0) {
            alumno.setCreditosDisponibles(alumno.getCreditosDisponibles() - 1);
            alumno.setCreditosConsumidos(alumno.getCreditosConsumidos() + 1);
        } else {
            throw new CreditosInsuficientesException();
        }
        usuarioRepository.save(alumno);

        Profesor profesor = (Profesor) usuarioRepository.findOne(crearReservaDTO.getProfesorId());
        Reserva reserva = new Reserva(Estado.SIN_CONTESTAR, "", "", alumno, claseLibre, profesor, claseLibre.getFecha());
        return save(reserva);
    }

    // Admin
    @Override
    public Reserva devolverCreditos(Long id) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        Reserva reserva = reservaRepository.findOne(id);
        devolverCreditosAlumno(reserva.getAlumno().getId(), reserva.getProfesor().getId());
        reserva.setRevisada(true);
        return save(reserva);
    }

    // Admin
    @Override
    public Reserva rechazarCreditos(Long id) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        Reserva reserva = reservaRepository.findOne(id);
        reserva.setRevisada(true);
        return save(reserva);
    }

    @Override
    public Reserva update(Reserva reserva) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        Reserva reservaBd = reservaRepository.findOne(reserva.getId());
        Estado estadoInicial = reservaBd.getEstado();
        if (reserva.getEstado() != null) {
            reservaBd.setEstado(reserva.getEstado());
        }
        if (reserva.getMotivoAlumno() != null && !reserva.getMotivoAlumno().equals("")) {
            reservaBd.setMotivoAlumno(reserva.getMotivoAlumno());
        }
        if (reserva.getMotivoProfesor() != null && !reserva.getMotivoProfesor().equals("")) {
            reservaBd.setMotivoProfesor(reserva.getMotivoProfesor());
        }
        // En caso de cancelamiento o rechazo de una reserva, habrá que poner la clase a libre y añadir el crédito al alumno
        if (reserva.getEstado().equals(Estado.NO_CONTESTADA)
            || reserva.getEstado().equals(Estado.RECHAZADA)
            || reserva.getEstado().equals(Estado.CANCELADA_POR_ALUMNO)
            || reserva.getEstado().equals(Estado.CANCELADA_POR_PROFESOR)) {
            reservaBd.getClaseLibre().setOcupada(false);
            claseLibreRepository.save(reservaBd.getClaseLibre());
            devolverCreditosAlumno(reservaBd.getAlumno().getId(), null);

            // Si la ha rechazado el profesor, marcamos la clase como ocupada para que no se puedan hacer más reservas en esa hora
            if (reserva.getEstado().equals(Estado.RECHAZADA) || reserva.getEstado().equals(Estado.CANCELADA_POR_PROFESOR)) {
                reservaBd.getClaseLibre().setOcupada(true);
            }
        }

        if (reserva.getEstado().equals(Estado.PENDIENTE)) {
            reservaBd.setRevisada(false);
        } else if (reserva.getEstado().equals(Estado.REALIZADA) || reserva.getEstado().equals(Estado.INCIDENCIA)) {
            reservaBd.setRevisada(true);

            // Si se marca como realizada, se incrementan las clases del profesor
            if (!estadoInicial.equals(Estado.REALIZADA) && reserva.getEstado().equals(Estado.REALIZADA)) {
                Profesor profesor = (Profesor) usuarioRepository.findOne(reservaBd.getProfesor().getId());
                profesor.setClasesImpartidas(profesor.getClasesImpartidas() + 1);
                usuarioRepository.save(profesor);
            }

            // Si se ha marcado como incidencia, se devuelven los créditos
            if (!estadoInicial.equals(Estado.INCIDENCIA) && reserva.getEstado().equals(Estado.INCIDENCIA)) {
                devolverCreditosAlumno(reservaBd.getAlumno().getId(), reservaBd.getProfesor().getId());
            }
        }

        return reservaRepository.save(reservaBd);
    }

    private void devolverCreditosAlumno(Long alumnoId, Long profesorId) {
        Alumno alumno = (Alumno) usuarioRepository.findOne(alumnoId);
        alumno.setCreditosDisponibles(alumno.getCreditosDisponibles() + 1);
        alumno.setCreditosConsumidos(alumno.getCreditosConsumidos() - 1);
        usuarioRepository.save(alumno);
        // Si se envía el ID del profesor, se le descuenta una clase
        if (profesorId != null) {
            Profesor profesor = (Profesor) usuarioRepository.findOne(profesorId);
            profesor.setClasesImpartidas(profesor.getClasesImpartidas() - 1);
            usuarioRepository.save(profesor);
        }
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }

}
