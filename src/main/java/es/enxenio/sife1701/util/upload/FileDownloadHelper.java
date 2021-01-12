package es.enxenio.sife1701.util.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Carlos on 08/12/2016.
 */
@Component
public class FileDownloadHelper {

    private final Logger log = LoggerFactory.getLogger(FileDownloadHelper.class);

    public void descargar(File file, String nombre, String tipo, HttpServletResponse response) {
        response.setContentType("application/" + tipo);
        response.setHeader("Content-Disposition", "attachment; filename=" + nombre);
        response.setContentLength((int) file.length());

        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream responseOutputStream = response.getOutputStream()) {
            int bytes;
            while ((bytes = fileInputStream.read()) != -1) {
                responseOutputStream.write(bytes);
            }
            responseOutputStream.flush();
        } catch (IOException e) {
            log.error("Error al descargar", e);
        }
    }

}
