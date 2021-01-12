package es.enxenio.sife1701.model.compra;

import es.enxenio.sife1701.model.exceptions.CompraException;

/**
 * Created by crodriguez on 13/10/2016.
 */
public interface PaypalService {

    String iniciarTransaccionCompra(Compra compra, String urlConfirmacion, String urlCancelacion);

    void finalizarTransaccionCompra(Compra compra, String paymentId) throws CompraException;

}
