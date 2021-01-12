package es.enxenio.sife1701.controller.publica;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.config.util.SecurityUtils;
import es.enxenio.sife1701.controller.custom.util.MessageJSON;
import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.controller.util.HeaderUtil;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.compra.Compra;
import es.enxenio.sife1701.model.compra.CompraService;
import es.enxenio.sife1701.model.exceptions.CompraException;
import es.enxenio.sife1701.model.exceptions.UnauthorizedException;
import es.enxenio.sife1701.util.ConstantesRest;
import es.enxenio.sife1701.util.email.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by crodriguez on 05/10/2016.
 */
@RestController
@RequestMapping(ConstantesRest.URL_PUBLIC + "/compra")
@PreAuthorize(ConstantesRest.IS_AUTHENTICATED)
public class CompraController {

    private final Logger log = LoggerFactory.getLogger(CompraController.class);

    @Inject
    private CompraService compraService;

    @Inject
    private MailService mailService;

    /**
     * Obtiene las compras del usuario actual.
     *
     * @return Lista de compras.
     */
    @JsonView(JsonViews.List.class)
    @RequestMapping(value = "/findAllByUsuarioActivo", method = RequestMethod.GET)
    public ResponseEntity<Page<Compra>> findAllByUsuarioActivo(PageableFilter pageable) {
        if (pageable != null) {
            return new ResponseEntity<>(
                compraService.findAllByUsuarioActivo(pageable.getQuery(), pageable.getPageable()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Obtiene una compra por ID.
     *
     * @param id ID de la compra.
     * @return Devuelve la compra, o un 403 en caso de que no tener acceso.
     */
    @JsonView(JsonViews.Details.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Compra> getCompra(@PathVariable Long id) {
        Compra compra = compraService.get(id);
        if (compra.getUsuario().getEmail().equals(SecurityUtils.getCurrentUserEmail())) {
            return new ResponseEntity<>(compra, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Creamos el objeto compra con el paquete de créditos y lo devolvemos.
     *
     * @return Compra
     */
    @RequestMapping(value = "/crear", method = RequestMethod.GET)
    public ResponseEntity<Compra> startCompra(@RequestParam Long paquetecreditosId, @RequestParam boolean solicitarFactura) {
        try {
            return new ResponseEntity<>(compraService.crearCompra(paquetecreditosId, solicitarFactura), HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Iniciamos el proceso de compra con PayPal.
     *
     * @return Devuelve la URL a la que tenemos que redirigir.
     */
    @RequestMapping(value = "/iniciar-paypal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageJSON> iniciarCompraPaypal(Long compraId, HttpServletRequest request) {
        String paypalUrl = compraService.iniciarCompraPaypal(compraId, CodeUtil.getUrlBase(request));
        return new ResponseEntity<>(new MessageJSON(paypalUrl), HttpStatus.OK);
    }

    /*@PreAuthorize(ConstantesRest.PERMIT_ALL)*/
    @RequestMapping(value = "/iniciar-paypal-alumno", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageJSON> iniciarCompraAlumnoPaypal(HttpServletRequest request, @RequestParam Long compraId) {
        String paypalUrl = compraService.iniciarCompraPaypalAlumno(CodeUtil.getUrlBase(request), compraId);
        return new ResponseEntity<>(new MessageJSON(paypalUrl), HttpStatus.OK);
    }

    /**
     * Operación de callback una vez que se realiza el pago en PayPal.
     * El pago no se confirma hasta que no se ejecuta este método correctamente.
     *
     * @param compraId  ID de la compra.
     * @param paymentId ID de paypal con el que obtendremos la compra.
     * @param token Token generado por paypal
     */
    /*@PreAuthorize(ConstantesRest.PERMIT_ALL)*/
    @RequestMapping(value = "/confirmar-paypal", method = RequestMethod.GET)
    public ResponseEntity confirmarCompraPaypal(@RequestParam Long compraId,
                                                @RequestParam String paymentId,
                                                @RequestParam String token, HttpServletRequest request, Locale locale) {
        try {
            Compra compra = compraService.confirmarCompraPaypal(compraId, paymentId, token);
            compraService.sendEmails(compra, CodeUtil.getUrlBase(request), locale);
            return new ResponseEntity(HeaderUtil.createAlert("compra.success"), HttpStatus.OK);
        } catch (CompraException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Si cancelamos el proceso, eliminamos la compra.
     */
    @RequestMapping(value = "/cancelar", method = RequestMethod.GET)
    public ResponseEntity cancelarCompra(Long compraId) {
        compraService.cancelar(compraId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
