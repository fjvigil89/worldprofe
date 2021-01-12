package es.enxenio.sife1701.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by crodriguez on 11/10/2016.
 */
public class EurosUtil {

    /**
     * Aplica el IVA a un precio y lo redondea con dos decimales.
     *
     * @param precio Precio base.
     * @param iva    IVA que se le aplicará.
     * @return BigDecimal con el nuevo valor con dos decimales. 0 en caso de que sea null.
     */
    public static BigDecimal getPrecioConIva(BigDecimal precio, Integer iva) {
        if (precio != null && iva != null) {
            return precio.multiply(BigDecimal.valueOf(100 + iva))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    /**
     * Aplica un descuento (%) a un precio.
     *
     * @param precio    Precio base.
     * @param descuento Descuento a aplicar.
     * @return BigDecimal con el nuevo valor. 0 en caso de que sea null.
     */
    public static BigDecimal aplicarDescuento(BigDecimal precio, Integer descuento) {
        if (precio != null && descuento != null) {
            BigDecimal desc = BigDecimal.ONE.subtract(BigDecimal.valueOf(descuento).divide(BigDecimal.valueOf(100)));
            return precio.multiply(desc);
        } else {
            return new BigDecimal(0);
        }
    }

    /**
     * Formatea las cantidades con dos decimales separando por ","
     *
     * @param precio Precio a formatear.
     * @return Precio con el nuevo formato.
     */
    public static String getPrecioConFormato(BigDecimal precio) {
        return getPrecioConFormato(precio, ',');
    }

    /**
     * Formatea las cantidades con dos decimales separando por ".". Necesario para PayPal.
     *
     * @param precio Precio a formatear.
     * @return Precio con el nuevo formato.
     */
    public static String getPrecioConFormatoIngles(BigDecimal precio) {
        return getPrecioConFormato(precio, '.');
    }

    private static String getPrecioConFormato(BigDecimal precio, char separator) {
        DecimalFormatSymbols fs = new DecimalFormatSymbols(Locale.getDefault());
        fs.setDecimalSeparator(separator);
        DecimalFormat format = new DecimalFormat("0.00", fs);
        if (precio != null) {
            return format.format(precio);
        }
        return null;
    }

}
