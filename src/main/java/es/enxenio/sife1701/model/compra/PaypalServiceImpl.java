package es.enxenio.sife1701.model.compra;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import es.enxenio.sife1701.config.util.MyProperties;
import es.enxenio.sife1701.model.exceptions.CompraException;
import es.enxenio.sife1701.util.EurosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crodriguez on 05/10/2016.
 */
@Service
@Transactional
public class PaypalServiceImpl implements PaypalService {

    private static final Logger log = LoggerFactory.getLogger(PaypalServiceImpl.class);

    @Inject
    private CompraRepository compraRepository;

    @Inject
    private MyProperties properties;

    //

    @Override
    public String iniciarTransaccionCompra(Compra compra, String urlConfirmacion, String urlCancelacion) {
        try {
            APIContext apiContext = getAPIContext();
            Payment payment = getParametrosCompra(compra, urlConfirmacion, urlCancelacion);
            payment = payment.create(apiContext);

            compra.setPaypalPaymentId(payment.getId());
            compraRepository.save(compra);

            // Payment Approval Url
            for (Links link : payment.getLinks()) {
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    log.debug("PayPal", "URL de destino: " + link.getHref());
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error("PayPal exception", e);
        }
        return "";
    }

    @Override
    public void finalizarTransaccionCompra(Compra compra, String paymentId) throws CompraException {
        try {
            // Se realizan las comprobaciones necesarias y se confirma el pago.
            APIContext apiContext = getAPIContext();
            Payment payment = Payment.get(apiContext, paymentId);

            if (!compra.getPaypalPaymentId().equals(payment.getId()))
                throw new CompraException("El pago recuperado de PayPal no se corresponde con la información del pedido actual: paymentId");

            if (payment.getTransactions().size() != 1)
                throw new CompraException("El pago recuperado de PayPal no se corresponde con la información del pedido actual: numero de transacciones");

            double paypalAmount = new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal()).doubleValue();
            double prezoTotal = compra.getPrecioConIva().doubleValue();

            if (paypalAmount != prezoTotal)
                throw new CompraException("El pago recuperado de PayPal no se corresponde con la información del pedido actual: prezo total " + paypalAmount + " " + prezoTotal);

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payment.getPayer().getPayerInfo().getPayerId());
            payment.execute(apiContext, paymentExecution);

        } catch (PayPalRESTException e) {
            throw new CompraException(e);
        }
    }

    private APIContext getAPIContext() {
        return new APIContext(properties.getPaypal().getClientID(),
            properties.getPaypal().getClientSecret(), properties.getPaypal().getEnvironment());
    }

    private Payment getParametrosCompra(Compra compra, String urlConfirmacion, String urlCancelacion) {

        //Urls de cancelacion e de confirmacion
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(urlCancelacion);
        redirectUrls.setReturnUrl(urlConfirmacion);

        Details details = new Details();
        details.setSubtotal(EurosUtil.getPrecioConFormatoIngles(compra.getPrecioConIva()));

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(EurosUtil.getPrecioConFormatoIngles(compra.getPrecioConIva()));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        List<Item> items = new ArrayList<>();
        Item item = new Item();

        item.setName(compra.getCreditos().toString());
        item.setCurrency("EUR");
        item.setPrice(EurosUtil.getPrecioConFormatoIngles(compra.getPrecioConIva()));
        item.setQuantity(String.valueOf(1));

        items.add(item);

        ItemList itemList = new ItemList();
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        return payment;
    }

}
