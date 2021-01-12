package es.enxenio.sife1701.util.upload;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author José Luis (14/12/2009)
 */
public class ImageHelper {

    /**
     * @param pathImaxeEntrada path coa imaxe orixinal
     * @param pathImaxeSaida   path coa imaxe creada
     * @param anchoMaximo      máximo ancho da imaxe a escalar, en píxeles
     * @param forzarAncho      Se a imaxe non chega ao ancho máximo, forzar ese ancho máximo (escalado a ancho maior)
     */
    public static void limitaAnchoMaximo(String pathImaxeEntrada, String pathImaxeSaida, Integer anchoMaximo
        , boolean forzarAncho) throws Exception {

        try {
            Image image = obterImaxe(pathImaxeEntrada);
            engadirMediaTracker(image);

            if (forzarAncho || image.getWidth(null) > anchoMaximo) {
                image = image.getScaledInstance(anchoMaximo, -1, Image.SCALE_SMOOTH);
            }
            engadirMediaTracker(image);
            ImageIO.write(toBufferedImage(image, FilenameUtils.getExtension(pathImaxeEntrada)), FilenameUtils.getExtension(pathImaxeSaida), new File(pathImaxeSaida));

        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * @param pathImaxeEntrada O path coa imaxe orixinal
     * @param pathImaxeSaida   O path coa imaxe creada
     * @param altoMaximo       o máximo alto da imaxe a escalar, en píxeles
     * @param forzarAlto       Se a imaxe non chega ao alto máximo, forzar ese alto máximo (escalado a alto maior)
     * @throws Exception
     */
    public static void limitaAltoMaximo(String pathImaxeEntrada, String pathImaxeSaida,
                                        Integer altoMaximo, boolean forzarAlto) throws Exception {

        try {
            Image image = obterImaxe(pathImaxeEntrada);
            engadirMediaTracker(image);

            if (forzarAlto || image.getHeight(null) > altoMaximo) {
                image = image.getScaledInstance(-1, altoMaximo, Image.SCALE_SMOOTH);
            }
            engadirMediaTracker(image);
            ImageIO.write(toBufferedImage(image, FilenameUtils.getExtension(pathImaxeEntrada)), FilenameUtils.getExtension(pathImaxeSaida), new File(pathImaxeSaida));

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param pathImaxeEntrada O path coa imaxe orixinal
     * @param pathImaxeSaida   O path coa imaxe creada
     * @param tamanho          Tamaño da imaxe a crear. Escala pola dimensión maior e recorta a parte sobrante
     */
    public static void crearImaxeCadrada(String pathImaxeEntrada, String pathImaxeSaida, Integer tamanho) throws Exception {

        Image image = obterImaxe(pathImaxeEntrada);
        //Limitar alto máximo e recortar ancho
        engadirMediaTracker(image);
        if (image.getWidth(null) < image.getHeight(null)) {
            // Reescala fixando ancho
            image = image.getScaledInstance(tamanho, -1, Image.SCALE_SMOOTH);
        } else {
            // Reescala fixando alto
            image = image.getScaledInstance(-1, tamanho, Image.SCALE_SMOOTH);
        }
        ImageIO.write(toBufferedImage(image, tamanho, FilenameUtils.getExtension(pathImaxeEntrada)), FilenameUtils.getExtension(pathImaxeSaida), new File(pathImaxeSaida));
    }

    /**
     * Encaixa unha imaxe en un rectángulo, que como máximo ten as dimensions indicadas
     * Reescala pola dimensión de maior porcentaxe de reducción, deixando a outra libre
     *
     * @param pathImaxeEntrada O path coa imaxe orixinal
     * @param pathImaxeSaida   O path coa imaxe creada
     * @param anchoMaximo      Ancho máximo da imaxe
     * @param altoMaximo       o máximo alto da imaxe a escalar, en píxeles
     */
    public static void encaixaLimites(String pathImaxeEntrada, String pathImaxeSaida, Integer anchoMaximo, Integer altoMaximo) throws Exception {

        Image image = obterImaxe(pathImaxeEntrada);
        engadirMediaTracker(image);
        // double porcentaxeReducionAncho =  new Double(image.getWidth(null) - anchoMaximo) / new Double(anchoMaximo);
        // double porcentaxeReducionAlto = new Double(image.getHeight(null) - altoMaximo) / new Double(altoMaximo);
        double porcentaxeReducionAncho = (double) (image.getWidth(null) - anchoMaximo) / (double) anchoMaximo;
        double porcentaxeReducionAlto = (double) (image.getHeight(null) - altoMaximo) / (double) altoMaximo;

        if (porcentaxeReducionAncho <= 0 && porcentaxeReducionAlto <= 0) {

            image = image.getScaledInstance(-1, -1, Image.SCALE_SMOOTH);
        } else if (porcentaxeReducionAncho > porcentaxeReducionAlto) {
            // Reescalar por ancho
            image = image.getScaledInstance(anchoMaximo, -1, Image.SCALE_SMOOTH);
        } else {
            // Reescalar por alto
            image = image.getScaledInstance(-1, altoMaximo, Image.SCALE_SMOOTH);
        }
        ImageIO.write(toBufferedImage(image, FilenameUtils.getExtension(pathImaxeEntrada)), FilenameUtils.getExtension(pathImaxeSaida), new File(pathImaxeSaida));
    }

    private static void engadirMediaTracker(Image image) throws InterruptedException {
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 1);
        mediaTracker.waitForID(1);
    }

    private static BufferedImage toBufferedImage(Image image, String extension) {
        return toBufferedImage(image, null, extension);
    }

    /**
     * Crea unha nova imaxe a partir dunha dada. Se o tamanho vale null devolve unha Buffered imaxe
     * coa imaxe orixinal. En outro caso, recorta a imaxe pola dimensión máis grande para crear unha imaxe cadrada,
     * e manter as proporcións
     *
     * @return
     */
    private static BufferedImage toBufferedImage(Image image, Integer tamanho, String extension) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        BufferedImage bimage = null;
        image = new ImageIcon(image).getImage();

        boolean hasAlpha = hasAlpha(extension);


        Integer ancho = tamanho == null ? image.getWidth(null) : tamanho;
        Integer alto = tamanho == null ? image.getHeight(null) : tamanho;

        // FIX: O seguinte código estaba a meter "ruido" nas imaxes (só en maquinas con windows 2008 / 7 / Vista...?)
        // Mantense no caso dos GIFs xa que non ten ese problema.
        if ("gif".equalsIgnoreCase(extension)) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            try {
                // Determine the type of transparency of the new buffered image
                int transparency = Transparency.OPAQUE;
                if (hasAlpha) {
                    transparency = Transparency.BITMASK;
                }
                // Create the buffered image
                GraphicsDevice gs = ge.getDefaultScreenDevice();
                GraphicsConfiguration gc = gs.getDefaultConfiguration();
                bimage = gc.createCompatibleImage(ancho, alto, transparency);
            } catch (HeadlessException e) {
                // The system does not have a screen
            }
        }
        // ---

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(ancho, alto, type);
        }


        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        if (tamanho == null) {
            //A imaxe final enxaixa coa imaxe orixinal
            g.drawImage(image, 0, 0, null);
        } else {
            // É necesario recortar a imaxe orixinal (imaxes cadradas)
            if (image.getHeight(null) > tamanho) {
                // Recorte vertical
                Integer recorte = (image.getHeight(null) - tamanho) / 2;
                g.drawImage(image, 0, 0, tamanho, tamanho, 0, 0 + recorte, tamanho, image.getHeight(null) - recorte, null);
            } else {
                // Recorte horizontal
                Integer recorte = (image.getWidth(null) - tamanho) / 2;
                g.drawImage(image, 0, 0, tamanho, tamanho, 0 + recorte, 0, image.getWidth(null) - recorte, tamanho, null);
            }
        }
        g.dispose();

        return bimage;
    }

    private static Image obterImaxe(String pathImaxeEntrada) {
        // Image image = Toolkit.getDefaultToolkit().getImage(pathImaxeEntrada);
        Image image = new ImageIcon(pathImaxeEntrada).getImage();
        return image;
    }


    /**
     * Comproba se unha imaxe ten transparencia
     */
    public static boolean hasAlpha(String extension) {

        return ("png".equals(extension) || "gif".equals(extension));
    }


    // Método de probas. Descomentar para probas
    //
    //public static void main(String [] args) throws Exception{
    //	ImageHelper.encaixaLimites("C:\\001.jpg", "C:\\001_m.jpg", 400, 300);
    //}
}
