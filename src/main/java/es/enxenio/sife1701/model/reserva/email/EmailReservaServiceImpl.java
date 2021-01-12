package es.enxenio.sife1701.model.reserva.email;

import es.enxenio.sife1701.controller.custom.ActualizarReservaTask;
import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.model.configuracion.ConfiguracionService;
import es.enxenio.sife1701.model.reserva.ReservaService;
import es.enxenio.sife1701.util.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jlosa on 25/08/2017.
 */
@Service
@Transactional
public class EmailReservaServiceImpl implements EmailReservaService {

    @Inject
    private EmailReservaRepository emailReservaRepository;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Inject
    private ReservaService reservaService;

    @Inject
    private EmailReservaService emailReservaService;

    @Inject
    private MailService mailService;

    @Inject
    private ConfiguracionService configuracionService;

    // Consulta

    @Override
    @Transactional(readOnly = true)
    public EmailReserva get(Long id) {
        return emailReservaRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailReserva> filter(PageableFilter filter) {
        return emailReservaRepository.findAll(filter.getPageable());
    }

    // Gestión

    @Override
    public EmailReserva save(EmailReserva emailReserva) throws InstanceAlreadyExistsException {
        return emailReservaRepository.save(emailReserva);
    }

    @Override
    public EmailReserva update(EmailReserva emailReserva) throws InstanceNotFoundException, InstanceAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws InstanceNotFoundException {
        emailReservaRepository.deleteByReserva(id);
    }

    @Override
    @PostConstruct
    public void cargarTareasPendientes() {
        List<EmailReserva> emails = emailReservaRepository.findAll();
        for (EmailReserva email : emails) {
            if (email.getFecha().isBefore(ZonedDateTime.now(ZoneId.systemDefault()))) {
                taskScheduler.execute(new ActualizarReservaTask(email.getReserva(), reservaService, this,
                    email.getUrlBase(), Locale.forLanguageTag(email.getLocale()), mailService, configuracionService, taskScheduler));
            } else {
                taskScheduler.schedule(new ActualizarReservaTask(email.getReserva(), reservaService, this,
                    email.getUrlBase(), Locale.forLanguageTag(email.getLocale()), mailService, configuracionService, taskScheduler), Date.from(email.getFecha().toInstant()));
            }
        }
    }

}
