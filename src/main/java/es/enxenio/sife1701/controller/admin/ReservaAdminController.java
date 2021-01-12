package es.enxenio.sife1701.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.custom.filter.ReservaFilter;
import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.reserva.Reserva;
import es.enxenio.sife1701.model.reserva.ReservaService;
import es.enxenio.sife1701.util.ConstantesRest;
import es.enxenio.sife1701.util.email.EnvioEmailException;
import es.enxenio.sife1701.util.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by jlosa on 25/08/2017.
 */
@RestController
@RequestMapping(ConstantesRest.URL_ADMIN + "/reserva")
@PreAuthorize(ConstantesRest.HAS_ROLE_ADMIN)
public class ReservaAdminController {

    @Inject
    private ReservaService reservaService;

    @Autowired
    private MailService mailService;

    @JsonView(JsonViews.Details.class)
    @RequestMapping(value = "/filterByAdmin", method = RequestMethod.GET)
    public ResponseEntity<Page<Reserva>> filterByAdmin(ReservaFilter filter) {
        if (filter != null) {
            return new ResponseEntity<>(reservaService.filterByAdmin(filter), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "devolverCreditos", method = RequestMethod.POST)
    public ResponseEntity devolverCreditos(Long id, HttpServletRequest request, Locale locale) {
        try {
            Reserva reserva = reservaService.devolverCreditos(id);
            mailService.sendDevolucionCreditoEmail(reserva, CodeUtil.getUrlBase(request), locale);
            return new ResponseEntity<>(HeaderUtil.createAlert("admin.reserva.update.success"), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EnvioEmailException e) {
            return new ResponseEntity<>(
                HeaderUtil.createFailureAlert("reserva.error.email"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "rechazarCreditos", method = RequestMethod.POST)
    public ResponseEntity rechazarCreditos(Long id) {
        try {
            reservaService.rechazarCreditos(id);
            return new ResponseEntity<>(HeaderUtil.createAlert("admin.reserva.update.success"), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
