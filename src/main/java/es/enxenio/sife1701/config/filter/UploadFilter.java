package es.enxenio.sife1701.config.filter;

import es.enxenio.sife1701.config.jwt.TokenProvider;
import org.springframework.web.filter.GenericFilterBean;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Filtro de los archivos de la carpeta /upload.
 * El JWT se envía como parámetro al querer obtener los archivos protegidos (PDF y EPUB).
 * Created by crodriguez on 22/11/2016.
 */
public class UploadFilter extends GenericFilterBean {

    // Constante que se concatena al JWT en el lado cliente para confundir ante posibles ataques y robos del jwt...
    private final String JWT_CONSTANT = "xJhbGciO";

    @Inject
    private TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        // No se establece ningún filtro

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
