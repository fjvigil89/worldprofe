package es.enxenio.sife1701.util.upload;

import es.enxenio.sife1701.util.ConstantesModel;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase para xestionar a creación de imaxes con thumbnails
 *
 * @author José Luis (04/05/2010)
 */
public class ImagenUtil {

    private static final Logger log = LoggerFactory.getLogger(ImagenUtil.class);

    /**
     * @return Lista de Thumbnails para la portada de un libro.
     */
    public static List<Thumbnail> getThumbnailsPortada() {
        List<Thumbnail> thumbnails = new ArrayList<>();
        thumbnails.add(new Thumbnail(ConstantesModel.PREFIJO_IMAGEN_NORMAL, ConstantesModel.ANCHO_IMAGEN_NORMAL,
            ConstantesModel.ALTO_IMAGEN_NORMAL));
        return thumbnails;
    }

    /**
     * @return Lista de Thumbnails para las imágenes de perfil de los usuarios.
     */
    public static List<Thumbnail> getThumbnailsPerfil() {
        List<Thumbnail> thumbnails = new ArrayList<>();
        thumbnails.add(new Thumbnail(ConstantesModel.PREFIJO_IMAGEN_NORMAL, ConstantesModel.ANCHO_IMAGEN_PERFIL_NORMAL,
            ConstantesModel.ALTO_IMAGEN_PERFIL_NORMAL));
        return thumbnails;
    }

    /**
     * @param imaxeOrixinal Ficheiro coa imaxe orixinal
     * @param fileDestino   Directorio de destino, onde se almacenarán a imaxe orixinal e os thumbnails que se especifique
     * @param thumbnails    Configuración para os thumbnails, ou null, se non se desexa crear thumbnails
     * @throws IOException
     */
    public static void gardarImaxeConThumbnails(File imaxeOrixinal,
                                                File fileDestino, List<Thumbnail> thumbnails) throws IOException {

        gardarImaxeConThumbnails(imaxeOrixinal, "", fileDestino, thumbnails, imaxeOrixinal.getName());
    }


    /**
     * @param imaxeOrixinal     Ficheiro coa imaxe orixinal
     * @param prefixoOrixinal   Prefixo para a imaxe orixinal
     * @param directorioDestino Directorio de destino, onde se almacenarán a imaxe orixinal e os thumbnails que se especifique
     * @param thumbnails        Configuración para os thumbnails, ou null, se non se desexa crear thumbnails
     * @throws IOException
     */
    private static void gardarImaxeConThumbnails(File imaxeOrixinal, String prefixoOrixinal,
                                                 File directorioDestino, List<Thumbnail> thumbnails, String nombreFoto) throws IOException {

        FileUtils.forceMkdir(directorioDestino);

        if (thumbnails != null) {
            for (Thumbnail thumbnail : thumbnails) {
                String pathDestino = directorioDestino.getAbsolutePath() + File.separator +
                    thumbnail.getPrefixo() + nombreFoto;
                try {
                    if (Thumbnail.ThumbnailTipo.CADRADO.equals(thumbnail.getTipo())) {
                        ImageHelper.crearImaxeCadrada(imaxeOrixinal.getAbsolutePath(), pathDestino, thumbnail.getAncho());
                    } else {
                        ImageHelper.encaixaLimites(imaxeOrixinal.getAbsolutePath(),
                            pathDestino, thumbnail.getAncho(), thumbnail.getAlto());
                    }
                } catch (Exception e) {
                    log.error("Erro ImageHelper", e);
                    throw new IOException(e);
                }
            }
        }
    }


}
