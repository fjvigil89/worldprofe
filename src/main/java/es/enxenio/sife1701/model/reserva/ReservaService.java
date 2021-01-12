package es.enxenio.sife1701.model.reserva;

import es.enxenio.sife1701.controller.custom.CrearReservaDTO;
import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.exceptions.CreditosInsuficientesException;
import es.enxenio.sife1701.model.generic.GenericService;
import org.springframework.data.domain.Page;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;

/**
 * Created by jlosa on 25/08/2017.
 */
public interface ReservaService extends GenericService<Reserva> {

    Page<Reserva> filterByAdmin(PageableFilter filter);

    List<Reserva> findByClaseLibre(Long id);

    Reserva crearReserva(CrearReservaDTO crearReservaDTO) throws InstanceAlreadyExistsException, CreditosInsuficientesException, InstanceNotFoundException;

    Reserva devolverCreditos(Long id) throws InstanceNotFoundException, InstanceAlreadyExistsException;

    Reserva rechazarCreditos(Long id) throws InstanceNotFoundException, InstanceAlreadyExistsException;

}
