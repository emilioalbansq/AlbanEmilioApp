package interfaz;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LogoApp extends JPanel {
    private static final BufferedImage IMAGEN_LOGO = cargarLogoPng();
    private int tamano;

    public LogoApp(int tamano) {
        this.tamano = tamano;
        setPreferredSize(new Dimension(tamano, tamano));
        setMinimumSize(new Dimension(tamano, tamano));
        setMaximumSize(new Dimension(tamano, tamano));
        setOpaque(false);
    }

    private static BufferedImage cargarLogoPng() {
        try {
            InputStream stream = LogoApp.class.getResourceAsStream("/recursos/logo_aulemi.png");
            if (stream != null) {
                BufferedImage img = ImageIO.read(stream);
                stream.close();
                return img;
            }
            String[] rutasDisco = {
                "src/recursos/logo_aulemi.png",
                "recursos/logo_aulemi.png"
            };
            for (String ruta : rutasDisco) {
                File archivo = new File(ruta);
                if (archivo.exists()) {
                    return ImageIO.read(archivo);
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo cargar logo_aulemi.png: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (IMAGEN_LOGO != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(IMAGEN_LOGO, 0, 0, tamano, tamano, null);
            g2.dispose();
        }
    }

    public static BufferedImage generarIconoVentana(int tamano) {
        BufferedImage icono = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
        if (IMAGEN_LOGO != null) {
            Graphics2D g2 = icono.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.drawImage(IMAGEN_LOGO, 0, 0, tamano, tamano, null);
            g2.dispose();
        }
        return icono;
    }
}
