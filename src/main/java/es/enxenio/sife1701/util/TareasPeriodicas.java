package es.enxenio.sife1701.util;

import es.enxenio.sife1701.util.upload.FileUploadHelper;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Esta clase gestiona las tareas que deben realizarse periódicamente para el
 * buen funcionamiento de la aplicación.
 * Borrado de archivos temporales.
 */
@Component
public class TareasPeriodicas {

    private final static Logger logger = Logger.getLogger(TareasPeriodicas.class);


    @Autowired
    FileUploadHelper fileUploadHelper;

    public TareasPeriodicas() {
    }

    /**
     * Se eliminan los archivos que tienen más de un día de antigüedad.
     */
    @Scheduled(cron = ConstantesModel.LIMPIADO_ARCHIVOS_TEMPORALES_PERIODO)
    public void eliminarArchivosTemporalesAntiguos() {

        File directorioArchivosTemporales = new File(
            fileUploadHelper.getRutaBase() + ConstantesModel.URL_CARPETA_ARCHIVOS_TEMPORALES);

        if (!directorioArchivosTemporales.exists()) {
            return;
        }

        Collection<File> archivosTemporales = FileUtils.listFiles(directorioArchivosTemporales, null, false);

        int numeroArchivosBorrados = 0;

        for (File archivo : archivosTemporales) {
            try {
                BasicFileAttributes attr = Files.readAttributes(archivo.toPath(), BasicFileAttributes.class);
                Long timestampArchivo = attr.creationTime().to(TimeUnit.MILLISECONDS);

                Calendar fechaArchivo = Calendar.getInstance();
                fechaArchivo.setTimeInMillis(timestampArchivo);

                Calendar hace1Dia = Calendar.getInstance();
                hace1Dia.add(Calendar.DAY_OF_MONTH, -1);

                if (fechaArchivo.before(hace1Dia)) {
                    fileUploadHelper.eliminarArchivo(archivo);

                    numeroArchivosBorrados += 1;
                }
            } catch (IOException e) {
                logger.warn("[Borrado periódico] No se han podido obtener los atributos del archivo.", e);
            }
        }
        logger.info("[Borrado periódico] Número de archivos temporales borrados: " + numeroArchivosBorrados);
    }

}
