package es.enxenio.sife1701.controller.util;

import es.enxenio.sife1701.model.util.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public class HeaderUtil {

    public static final String HEADER_ALERT = "X-app-alert";
    public static final String HEADER_ERROR = "X-app-error";
    public static final String HEADER_PARAMS = "X-app-params";

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_ALERT, message);
        headers.add(HEADER_PARAMS, param);
        return headers;
    }

    public static HttpHeaders createAlert(String message) {
        return createAlert(message, null);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_ERROR, errorKey);
        headers.add(HEADER_PARAMS, entityName);
        return headers;
    }

    public static HttpHeaders createFailureAlert(String errorKey) {
        log.error("Entity creation failed, {}", errorKey);
        return createFailureAlert(null, errorKey);
    }

    // Error

    public static HttpHeaders createErrorAlert(String errorKey, String params, Exception e) {
        if (e != null) {
            if (e instanceof AppException) {
                String message = "Controlled exception."
                    + " Key: '" + ((AppException) e).getErrorCode() + "'"
                    + " Param: " + ((AppException) e).getParameter();
                log.debug(message);
            } else {
                log.warn(e.getMessage(), e);
            }
        }
        return createErrorAlert(errorKey, params);
    }

    public static HttpHeaders createErrorAlert(String errorKey, String params) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_ERROR, errorKey);
        headers.add(HEADER_PARAMS, params);
        return headers;
    }

    public static HttpHeaders createErrorAlert(String errorKey) {
        return createErrorAlert(errorKey, null, null);
    }

}
