package es.enxenio.sife1701.util;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;

/**
 * Created by crodriguez on 31/05/2016.
 */
public class StringUtil {

    public static String normalize(String value) {
        if (value == null) return "";

        String result = value.toLowerCase();
        result = Normalizer.normalize(result, Normalizer.Form.NFC);
        result = StringUtils.stripAccents(result);
        return result;
    }

    public static String stripAccents(String text) {
        return StringUtils.stripAccents(text);
    }

}
