package es.enxenio.sife1701.controller.publica;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.custom.ActualizarReservaTask;
import es.enxenio.sife1701.controller.custom.CrearReservaDTO;
import es.enxenio.sife1701.controller.custom.filter.ReservaFilter;
import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.claselibre.ClaseLibreService;
import es.enxenio.sife1701.model.configuracion.ConfiguracionService;
import es.enxenio.sife1701.model.exceptions.CreditosInsuficientesException;
import es.enxenio.sife1701.model.reserva.Estado;
import es.enxenio.sife1701.model.reserva.Reserva;
import es.enxenio.sife1701.model.reserva.ReservaService;
import es.enxenio.sife1701.model.reserva.email.EmailReserva;
import es.enxenio.sife1701.model.reserva.email.EmailReservaService;
import es.enxenio.sife1701.util.ConstantesRest;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/reserva")
public class ReservaController {

    private final Logger log = LoggerFactory.getLogger(ReservaController.class);

    @Inject
    private ReservaService reservaService;
    @Inject
    private ConfiguracionService configuracionService;
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private MailService mailService;
    @Inject
    private EmailReservaService emailReservaService;
    @Inject
    private ClaseLibreService claseLibreService;

    // Consulta

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Reserva get(@PathVariable("id") Long id) {
        return reservaService.get(id);
    }

    @JsonView(JsonViews.DetailedList.class)
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<Page<Reserva>> findAll(ReservaFilter filter) {
        if (filter != null) {
            return new ResponseEntity<>(reservaService.filter(filter), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Gestión

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Reserva> save(@Valid @RequestBody CrearReservaDTO crearReservaDTO, HttpServletRequest request,
                                        Locale locale) throws CreditosInsuficientesException {
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, configuracionService.get().getTiempoMaximoRespuesta());
            Instant fechaMaxContestacion = calendar.toInstant();

            Reserva reserva = reservaService.crearReserva(crearReservaDTO);

            // En el caso de que la clase (sumándole el tiempo máximo de respuesta) sea anterior
            if (reserva != null && reserva.getClaseLibre() != null
                && reserva.getClaseLibre().getFecha().toInstant().isBefore(fechaMaxContestacion))
                fechaMaxContestacion = reserva.getClaseLibre().getFecha().toInstant();

            mailService.sendReservaEmail(reserva, fechaMaxContestacion, CodeUtil.getUrlBase(request), locale);
            taskScheduler.schedule(
                new ActualizarReservaTask(reserva, reservaService, emailReservaService,
                    CodeUtil.getUrlBase(request), locale, mailService, configuracionService, taskScheduler),
                calendar.getTime());
            emailReservaService.save(new EmailReserva(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()),
                CodeUtil.getUrlBase(request), locale.toLanguageTag(), reserva));
            return new ResponseEntity<>(reserva, HeaderUtil.createAlert("admin.reserva.create.success"),
                HttpStatus.CREATED);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("admin.form.error.alreadyexists"),
                HttpStatus.BAD_REQUEST);
        } catch (EnvioEmailException e) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("reserva.error.email"), HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            // Si la clase no existe, se cierra el diálogo y se recarga el calendario
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<Reserva> update(@Valid @RequestBody Reserva reserva, HttpServletRequest request,
                                          Locale locale) {
        try {
            emailReservaService.delete(reserva.getId());
        } catch (InstanceNotFoundException e) {
            log.debug(
                "ReservaController (InstanceNotFoundException) with date: {}, estado de la reserva: {} on thread {}",
                reserva.getFecha(), reserva.getEstado().toString(), Thread.currentThread().getName());
        }
        try {
            if (reserva.getEstado() != null && reserva.getEstado().equals(Estado.CONFIRMADA)) {
                Reserva reservaBd = reservaService.get(reserva.getId());
                reservaBd.getClaseLibre().setOcupada(true);
                claseLibreService.save(reservaBd.getClaseLibre());
                List<Reserva> reservasRechazar = reservaService.findByClaseLibre(reservaBd.getClaseLibre().getId());
                for (Reserva rr : reservasRechazar) {
                    if (!reservaBd.getId().equals(rr.getId())) {
                        log.debug("Marcando como rechazada la clase " + rr.getId());
                        rr.setEstado(Estado.RECHAZADA);
                        rr.setMotivoProfesor("Clase ocupada");
                        reservaService.save(rr);
                        mailService.sendRechazoReservaEmail(rr, CodeUtil.getUrlBase(request), locale);
                    }
                }
                taskScheduler.schedule(
                    new ActualizarReservaTask(reserva, reservaService, emailReservaService,
                        CodeUtil.getUrlBase(request), locale, mailService, configuracionService, taskScheduler),
                    Date.from(reserva.getFecha().toInstant()));
                emailReservaService.save(new EmailReserva(reserva.getFecha().toInstant().atZone(ZoneId.systemDefault()),
                    CodeUtil.getUrlBase(request), locale.toLanguageTag(), reserva));
            }
            Reserva reservaActualizada = reservaService.update(reserva);
            if (reservaActualizada.getEstado().equals(Estado.CONFIRMADA)) {
                mailService.sendAceptacionReservaEmail(reservaActualizada, CodeUtil.getUrlBase(request), locale);
            } else if (reservaActualizada.getEstado().equals(Estado.RECHAZADA)) {
                mailService.sendRechazoReservaEmail(reservaActualizada, CodeUtil.getUrlBase(request), locale);
            } else if (reservaActualizada.getEstado().equals(Estado.CANCELADA_POR_ALUMNO)
                || reservaActualizada.getEstado().equals(Estado.CANCELADA_POR_PROFESOR)) {
                mailService.sendCancelacionReservaEmail(reservaActualizada, CodeUtil.getUrlBase(request), locale);
            }
            if (reservaActualizada.getEstado().equals(Estado.RECHAZADA)
                || reservaActualizada.getEstado().equals(Estado.CANCELADA_POR_ALUMNO)
                || reservaActualizada.getEstado().equals(Estado.CANCELADA_POR_PROFESOR)
                || StringUtils.isNotBlank(reservaActualizada.getMotivoProfesor())
                && StringUtils.isNotBlank(reservaActualizada.getMotivoAlumno())
                && reservaActualizada.getMotivoProfesor().equals(reservaActualizada.getMotivoAlumno())) {
                mailService.sendDevolucionCreditoEmail(reservaActualizada, CodeUtil.getUrlBase(request), locale);
            }
            return new ResponseEntity<>(reservaActualizada, HeaderUtil.createAlert("admin.reserva.update.success"),
                HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EnvioEmailException e) {
            return new ResponseEntity<>(HeaderUtil.createFailureAlert("reserva.error.email"), HttpStatus.BAD_REQUEST);
        }
    }

}
