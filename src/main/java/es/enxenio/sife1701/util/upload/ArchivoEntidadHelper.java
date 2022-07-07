package es.enxenio.sife1701.util.upload;

//import com.sun.javafx.binding.StringFormatter;
import es.enxenio.sife1701.util.ConstantesModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class ArchivoEntidadHelper {

    private final static Logger logger = Logger.getLogger(ArchivoEntidadHelper.class);

    @Autowired
    private FileUploadHelper fileUploadHelper;

    public ArchivoEntidadHelper() {
    }

    public String gestionarArchivo(boolean eliminar, String nombreArchivoTemporal, String nombreArchivoDto,
                                   String nombreArchivoPersistente, String rutaCarpeta, Long entidadId) {

//        rutaCarpeta = StringFormatter.format(rutaCarpeta, entidadId).getValue();
    	rutaCarpeta = String.format(rutaCarpeta, entidadId);
        String rutaCarpetacompleta = fileUploadHelper.getRutaBase() + rutaCarpeta;

        // Mantenemos el mismo archivo
        if (nombreArchivoPersistente != null && nombreArchivoTemporal == null && !eliminar) {
            return nombreArchivoPersistente;
        }

        // Borramos fichero si hay que borrarlo
        boolean habiaArchivoYAhoraYaNo = nombreArchivoPersistente != null && nombreArchivoDto == null;

        boolean hayArchivoTemporal = nombreArchivoTemporal != null;

        if ((habiaArchivoYAhoraYaNo || hayArchivoTemporal) && nombreArchivoPersistente != null) {
            fileUploadHelper.eliminarArchivo(new File(rutaCarpetacompleta + "/" + nombreArchivoPersistente));
            fileUploadHelper.eliminarArchivo(
                new File(rutaCarpetacompleta + "/" + ConstantesModel.PREFIJO_IMAGEN_NORMAL + nombreArchivoPersistente));
        }

        // Creamos fichero si hay que crearlo
        boolean elUsuarioSubioUnArchivoYDecidioGuardarlo = nombreArchivoTemporal != null && nombreArchivoDto != null;

        if (elUsuarioSubioUnArchivoYDecidioGuardarlo) {

            File ficheroTemporal = new File(fileUploadHelper.getRutaBase()
                + ConstantesModel.URL_CARPETA_ARCHIVOS_TEMPORALES + File.separator + nombreArchivoTemporal);
            File directorio = fileUploadHelper.obtenerDirectorio(rutaCarpeta);
            return fileUploadHelper.copiarArchivoEnDirectorio(
                ficheroTemporal, directorio, getTimestamp() + nombreArchivoDto);
        }

        return null;
    }

    public void eliminarArchivos(String rutaCarpeta, Long entidadId) {
        File carpetaEntidad = fileUploadHelper.obtenerDirectorio(/*StringFormatter.format(rutaCarpeta, entidadId).getValue()*/String.format(rutaCarpeta, entidadId));
        fileUploadHelper.eliminarArchivo(carpetaEntidad);
    }

    public void descargar(HttpServletResponse response, String rutaCarpeta, Long entidadId) throws IOException {

        List<File> archivosCarpeta = (List<File>) FileUtils.listFiles(
            fileUploadHelper.obtenerDirectorio(rutaCarpeta + File.separator + entidadId), TrueFileFilter.INSTANCE,
            TrueFileFilter.INSTANCE);

        // Damos por hecho que en la carpeta va a haber 1 y solo 1 archivo.
        File downloadFile = archivosCarpeta.get(0);

        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = Files.probeContentType(downloadFile.toPath());
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return formatter.format(LocalDateTime.now()) + "_";
    }

    public void guardarThumbnailsImagen(List<Thumbnail> thumbnails, String rutaImagen, String rutaCarpetaDestino) {

        File imagen = new File(fileUploadHelper.getRutaBase() + rutaImagen);
        File carpetaDestino = new File(fileUploadHelper.getRutaBase() + rutaCarpetaDestino);
        try {
            ImagenUtil.gardarImaxeConThumbnails(imagen, carpetaDestino, thumbnails);
        } catch (IOException e) {
            logger.error(
                "Error al tratar de crear thumbnails de imagen " + imagen.getName() + "en " + rutaCarpetaDestino, e);
        }
    }

}
