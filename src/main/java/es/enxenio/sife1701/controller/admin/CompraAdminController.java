package es.enxenio.sife1701.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import es.enxenio.sife1701.controller.custom.util.PageableFilter;
import es.enxenio.sife1701.controller.util.JsonViews;
import es.enxenio.sife1701.model.compra.Compra;
import es.enxenio.sife1701.model.compra.CompraService;
import es.enxenio.sife1701.util.ConstantesRest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(ConstantesRest.URL_ADMIN + "/compra")
@PreAuthorize(ConstantesRest.HAS_ROLE_ADMIN)
public class CompraAdminController {

    @Inject
    private CompraService compraService;

    /**
     * Obtiene las compras del usuario.
     *
     * @return Lista de compras.
     */
    @JsonView(JsonViews.List.class)
    @RequestMapping(value = "/findAllByUsuario", method = RequestMethod.GET)
    public ResponseEntity<Page<Compra>> findAll(PageableFilter filter) {
        if (filter != null) {
            return new ResponseEntity<>(
                compraService.findAllByUsuario(filter), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
