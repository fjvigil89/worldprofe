package es.enxenio.sife1701.util.upload;

/**
 * @author José Luis (02/11/2009)
 */
public class FileInvalidTypeException extends Exception {

    private String arquivo;
    private String[] extensionesValidas;

    public FileInvalidTypeException() {
    }

    public FileInvalidTypeException(String arquivo, String[] extensionesValidas) {
        this.arquivo = arquivo;
        this.extensionesValidas = extensionesValidas;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String[] getExtensionesValidas() {
        return extensionesValidas;
    }

    public void setExtensionesValidas(String[] extensionesValidas) {
        this.extensionesValidas = extensionesValidas;
    }

}
