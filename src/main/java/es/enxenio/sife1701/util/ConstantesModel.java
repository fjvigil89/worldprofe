package es.enxenio.sife1701.util;

public class ConstantesModel {

    public static final String URL_CARPETA_ARCHIVOS_TEMPORALES = "/tmp";
    public static final String URL_CARPETA_UPLOAD = "/upload";
    public static final String URL_CARPETA_USUARIOS = "/upload/usuarios/%d";
    public static final String URL_CARPETA_USUARIOS_PERFIL = URL_CARPETA_USUARIOS + "/perfil";

    // Carpetas de archivos


    // Configuración

    public static final String PREFIJO_IMAGEN_NORMAL = "n_";
    public static final int ANCHO_IMAGEN_NORMAL = 180;
    public static final int ALTO_IMAGEN_NORMAL = 240;
    public static final int ANCHO_IMAGEN_PERFIL_NORMAL = 150;
    public static final int ALTO_IMAGEN_PERFIL_NORMAL = 150;
    public static final String[] EXTENSIONES_VALIDAS = {"pdf", "doc", "docx", "jpeg", "png", "gif", "jpg"};

    // Eliminado periódico de imágenes temporales (Para pruebas locales: 0/50 * * * * ?) (0 0 5 * * ?)
    public static final String LIMPIADO_ARCHIVOS_TEMPORALES_PERIODO = "0 0 5 * * ?";

}
