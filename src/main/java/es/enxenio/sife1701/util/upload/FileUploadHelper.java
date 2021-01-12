package es.enxenio.sife1701.util.upload;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Component
public class FileUploadHelper implements ServletContextAware {

    private final Logger logger = Logger.getLogger(FileUploadHelper.class);

    private final String SEPARADOR_PREFIJO = "---"; // Debe tener más de 2 letras

    private ServletContext servletContext;

    public FileUploadHelper() {
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getRutaBase() {
        return servletContext.getRealPath("/");
    }

    /**
     * Obtiene un File que referencia al directorio pasado por parámetro. Si no
     * existe, lo crea.
     **/
    public File obtenerDirectorio(String directorio) {

        File directorioFile = new File(getRutaBase() + directorio);

        try {
            FileUtils.forceMkdir(directorioFile);
            return directorioFile;
        } catch (IOException e) {
            logger.error("Error al intentar obtener la carpeta " + directorio, e);
            return null;
        }
    }

    /**
     * Obtiene el prefijo del nombre de un archivo temporal creado con esta clase
     */
    public String obtenerPrefijoNombre(String nombre) {
        return nombre != null ? nombre.substring(0, nombre.indexOf(SEPARADOR_PREFIJO)) : null;
    }

    /**
     * Elimina un archivo o directorio (de forma recursiva) si existe.
     */
    public void eliminarArchivo(File file) {
        FileUtils.deleteQuietly(file);
    }

    /**
     * Guarda un archivo con un nombre autogenerado único, usando de prefijo la cadena
     */
    public String guardarArchivoTemporal(MultipartFile multipartFile, File directorio, String prefijo) {

        if ((multipartFile != null) && !multipartFile.isEmpty()) {

            File nuevoArchivo = null;

            try {
                nuevoArchivo = File.createTempFile(prefijo + SEPARADOR_PREFIJO, null, directorio);
            } catch (IOException e) {
                logger.error("Imposible crear archivo en directorio " + directorio.getPath(), e);
                return null;
            }

            try {
                multipartFile.transferTo(nuevoArchivo);
            } catch (IllegalStateException | IOException e) {
                logger.error("Error al intentar guardar el archivo multipart " + multipartFile.getName()
                    + " en la carpeta " + directorio.getPath(), e);
                return null;
            }

            return nuevoArchivo.getName();

        } else {
            return null;
        }

    }

    /**
     * Guarda un archivo en un directorio previamente creado y normaliza su
     * nombre. Si es un archivo temporal genera un nombre único, en caso
     * contrario usa el del archivo original.
     */
    public String guardarArchivo(MultipartFile multipartFile, File directorio) {

        if ((multipartFile != null) && !multipartFile.isEmpty()) {

            File nuevoArchivo = new File(directorio.getAbsolutePath() + File.separator
                + normalizarNombreArchivo(multipartFile.getOriginalFilename()));
            try {
                multipartFile.transferTo(nuevoArchivo);
            } catch (IllegalStateException | IOException e) {
                logger.error("Error al intentar guardar el archivo multipart " + multipartFile.getName()
                    + " en la carpeta " + directorio.getPath(), e);
                return null;
            }

            return nuevoArchivo.getName();

        } else {
            return null;
        }
    }

    /**
     * Mira se a extensión do arquivo está na lista de extensións permitidas. No
     * caso de que a lista de extensións sexa null, acéptanse calquera tipo de
     * arquivos.
     *
     * @param nomeFicheiro      Nome de ficheiro con extensión
     * @param extensionsValidas Array de extensión de ficheiros válidas
     * @return True se a extensión é valida, false no caso contrario
     */
    public boolean eExtensionValida(String nomeFicheiro, String[] extensionsValidas) {
        if (extensionsValidas == null) {
            return true;
        } else {
            return Arrays.asList(extensionsValidas).contains(FilenameUtils.getExtension(nomeFicheiro).toLowerCase());
        }
    }

    /**
     * Copia un archivo en un directorio. Si no se indica un nombre se utiliza
     * el del archivo original
     **/
    public String copiarArchivoEnDirectorio(File archivo, File directorio, String nombre) {
        try {
            String nombreNuevoArchivo = nombre != null ? nombre : archivo.getName();
            String nombreArchivo = normalizarNombreArchivo(nombreNuevoArchivo);
            FileUtils.copyFile(archivo, new File(directorio.getAbsolutePath() + File.separator + nombreArchivo));
            return nombreArchivo;
        } catch (IOException e) {
            logger.error(
                "Error al copiar el archivo " + archivo.getPath() + " en el directorio " + directorio.getPath(), e);
            return null;
        }
    }

    /**
     * Normaliza un string. Elimina os acentos do string orixinal, e os
     * caracteres que non sexan letras (guións, etc) A cadea devolta está en
     * minúsculas.
     *
     * @param s Cadea de entrada
     * @return Cadea normalizada
     */
    public String normalizarNombreArchivo(String s) {

        String normalizedString = s;

        normalizedString = normalizedString.toLowerCase();

        normalizedString = normalizedString.replaceAll("á", "a");
        normalizedString = normalizedString.replaceAll("é", "e");
        normalizedString = normalizedString.replaceAll("í", "i");
        normalizedString = normalizedString.replaceAll("ó", "o");
        normalizedString = normalizedString.replaceAll("ú", "u");

        normalizedString = normalizedString.replaceAll("à", "a");
        normalizedString = normalizedString.replaceAll("è", "e");
        normalizedString = normalizedString.replaceAll("ì", "i");
        normalizedString = normalizedString.replaceAll("ó", "o");
        normalizedString = normalizedString.replaceAll("ú", "u");

        normalizedString = normalizedString.replaceAll("â", "a");
        normalizedString = normalizedString.replaceAll("ê", "e");
        normalizedString = normalizedString.replaceAll("î", "i");
        normalizedString = normalizedString.replaceAll("ô", "o");
        normalizedString = normalizedString.replaceAll("û", "u");

        normalizedString = normalizedString.replaceAll("ä", "a");
        normalizedString = normalizedString.replaceAll("ë", "e");
        normalizedString = normalizedString.replaceAll("ï", "i");
        normalizedString = normalizedString.replaceAll("ö", "o");
        normalizedString = normalizedString.replaceAll("ü", "u");

        normalizedString = normalizedString.replaceAll("º", "");
        normalizedString = normalizedString.replaceAll("ª", "");

        normalizedString = normalizedString.replaceAll("ñ", "n");

        return StringUtils.trimAllWhitespace(normalizedString);

    }
}
