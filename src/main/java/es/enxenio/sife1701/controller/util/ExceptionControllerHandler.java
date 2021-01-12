package es.enxenio.sife1701.controller.util;

import es.enxenio.sife1701.model.util.AppException;
import es.enxenio.sife1701.model.util.AppRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(AppException.class)
    public @ResponseBody
    ResponseEntity<Void> appExceptionHandler(AppException e) {
        return ResponseEntity.status(e.getStatus())
            .headers(HeaderUtil.createErrorAlert(e.getErrorCode(), e.getParameter(), e)).build();
    }

    @ExceptionHandler(AppRuntimeException.class)
    public @ResponseBody
    ResponseEntity<Void> appRuntimeExceptionHandler(AppRuntimeException e) {
        return ResponseEntity.status(e.getStatus())
            .headers(HeaderUtil.createErrorAlert(e.getErrorCode(), e.getParameter(), e)).build();
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ResponseEntity<Void> exceptionHandler(Exception e) {
        if (e instanceof AccessDeniedException) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .headers(HeaderUtil.createErrorAlert("error.internalservererror", null, e)).build();
    }
}
