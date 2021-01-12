package es.enxenio.sife1701.util.upload;

/**
 * @author José Luis (04/05/2010)
 * @author Javi (21/12/2011) Engadese tipo de thumbnail
 */
public class Thumbnail {

    public enum ThumbnailTipo {
        /*
         * Imaxe reescalada que terá como máximo o ancho e alto indicados.
         * Manten 'aspect ratio'
         */
        NORMAL,
        /*
         * Imaxe cadrada de lado 'ancho'. Manten 'aspect ratio' pero recortaa
         * segundo sexa preciso
         */
        CADRADO;
    }

    private String prefixo;
    private Integer ancho;
    private Integer alto;
    private ThumbnailTipo tipo;

    public Thumbnail(Integer ancho, Integer alto) {
        this.prefixo = "";
        this.ancho = ancho;
        this.alto = alto;
        this.tipo = ThumbnailTipo.NORMAL;
    }

    public Thumbnail(ThumbnailTipo tipo, Integer ancho, Integer alto) {
        this(ancho, alto);
        this.tipo = tipo;
    }

    public Thumbnail(String prefixo, Integer ancho, Integer alto) {
        this(ancho, alto);
        this.prefixo = prefixo;
    }

    public Thumbnail(ThumbnailTipo tipo, String prefixo, Integer ancho, Integer alto) {
        this(prefixo, ancho, alto);
        this.tipo = tipo;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public Integer getAncho() {
        return ancho;
    }

    public Integer getAlto() {
        return alto;
    }

    public ThumbnailTipo getTipo() {
        return tipo;
    }

    public void setTipo(ThumbnailTipo tipo) {
        this.tipo = tipo;
    }

}
