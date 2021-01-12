package es.enxenio.sife1701.controller.custom;

import es.enxenio.sife1701.model.configuracion.ConfiguracionService;
import es.enxenio.sife1701.model.reserva.Estado;
import es.enxenio.sife1701.model.reserva.Reserva;
import es.enxenio.sife1701.model.reserva.ReservaService;
import es.enxenio.sife1701.model.reserva.email.EmailReservaService;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Locale;

/**
 * Created by jlosa on 19/09/2017.
 */
@Component
public class ActualizarReservaTask implements Runnable {
    private final Logger log = LoggerFactory.getLogger(ActualizarReservaTask.class);

    private Reserva reserva;
    private ReservaService reservaService;
    private EmailReservaService emailReservaService;
    private String urlBase;
    private Locale locale;
    private MailService mailService;
    private ConfiguracionService configuracionService;
    private TaskScheduler taskScheduler;

    public ActualizarReservaTask() {
    }

    public ActualizarReservaTask(Reserva reserva, ReservaService reservaService, EmailReservaService emailReservaService,
                                 String urlBase, Locale locale, MailService mailService, ConfiguracionService configuracionService, TaskScheduler taskScheduler) {
        this.reserva = reserva;
        this.reservaService = reservaService;
        this.emailReservaService = emailReservaService;
        this.urlBase = urlBase;
        this.locale = locale;
        this.mailService = mailService;
        this.configuracionService = configuracionService;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void run() {
        log.debug("ActualizarReservaTask with id {} and date: {}, estado de la reserva: {} on thread {}",
            reserva.getId(), reserva.getFecha(), reserva.getEstado().toString(), Thread.currentThread().getName());
        if (reserva.getEstado().equals(Estado.CONFIRMADA)
            || reserva.getEstado().equals(Estado.PENDIENTE)
            || reserva.getEstado().equals(Estado.REALIZADA)
            || reserva.getEstado().equals(Estado.SIN_CONTESTAR)
            || reserva.getEstado().equals(Estado.INCIDENCIA)
            || reserva.getEstado().equals(Estado.NO_CONTESTADA)) {
            try {
                emailReservaService.delete(reserva.getId());
            } catch (InstanceNotFoundException e) {
                log.debug("ActualizarReservaTask (InstanceNotFoundException) with id {} and date: {}, estado de la reserva: {} on thread {}",
                    reserva.getId(), reserva.getFecha(), reserva.getEstado().toString(), Thread.currentThread().getName());
            }
        }
        if (reserva.getEstado().equals(Estado.CONFIRMADA)) {
            reserva.setEstado(Estado.PENDIENTE);
        } else if (reserva.getEstado().equals(Estado.SIN_CONTESTAR)) {
            reserva.setEstado(Estado.NO_CONTESTADA);
            try {
                mailService.sendDevolucionCreditoEmail(reserva, urlBase, locale);
            } catch (EnvioEmailException e) {
                log.warn("ActualizarReservaTask - FAILED (EnvioEmailException) -> Runnable Task with date: {}, estado de la reserva: {} on thread {}",
                    reserva.getClaseLibre().getFecha(), reserva.getEstado().toString(), Thread.currentThread().getName());
            }
        }
        try {
            if (reserva.getEstado().equals(Estado.NO_CONTESTADA)) {
                Reserva reservaBd = reservaService.get(reserva.getId());
                if (reservaBd.getEstado().equals(Estado.SIN_CONTESTAR)) {
                    reservaService.update(reserva);
                }
            } else {
                reservaService.update(reserva);
            }
            if (reserva.getEstado().equals(Estado.NO_CONTESTADA)) {
                try {
                    mailService.sendPlazoSuperadoContestacionReservaEmail(reserva, urlBase, locale);
                } catch (EnvioEmailException e) {
                    log.warn("ActualizarReservaTask - FAILED (EnvioEmailException) -> Runnable Task with date: {}, estado de la reserva: {} on thread {}",
                        reserva.getClaseLibre().getFecha(), reserva.getEstado().toString(), Thread.currentThread().getName());
                }
            }
        } catch (InstanceAlreadyExistsException e) {
            log.warn("ActualizarReservaTask - FAILED (InstanceAlreadyExistsException) -> Runnable Task with date: {}, estado de la reserva: {} on thread {}",
                reserva.getFecha(), reserva.getEstado().toString(), Thread.currentThread().getName());
        } catch (InstanceNotFoundException e) {
            log.warn("ActualizarReservaTask - FAILED (InstanceNotFoundException) -> Runnable Task with date: {}, estado de la reserva: {} on thread {}",
                reserva.getClaseLibre().getFecha(), reserva.getEstado().toString(), Thread.currentThread().getName());
        }
    }
}
