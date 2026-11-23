package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class PaletaMarina {
    // Paleta Midnight Slate & Frosted Silver Mist (inspirada en referencia Byte)
    public static final Color NEGRO_FONDO = new Color(9, 16, 25);           // #091019 Tinta nocturna
    public static final Color AZUL_MAR_OSCURO = new Color(16, 26, 39);     // #101A27 Tarjeta principal
    public static final Color AZUL_MAR_TARJETA = new Color(24, 38, 56);    // #182638 Cuadrito interior
    public static final Color AZUL_MAR_MEDIO = new Color(34, 51, 74);      // #22334A Bloque elevado / activo
    public static final Color BORDE_MARINO = new Color(46, 66, 92);        // #2E425C Borde sutil pizarra
    public static final Color BORDE_BRILLO_CELESTE = new Color(196, 211, 228, 70);

    // Superficies esmeriladas plateadas (como los botones y cards de Byte)
    public static final Color CELESTE_BRILLANTE = new Color(198, 212, 228); // #C6D4E4 Bruma plateada clara
    public static final Color CELESTE_PROFUNDO = new Color(138, 157, 181);  // #8A9DB5 Acero bruma medio
    public static final Color CELESTE_SUAVE = new Color(172, 188, 208);     // #ACBCD0 Plata hielo

    // Acentos semánticos suaves y elegantes
    public static final Color TURQUESA_EXITO = new Color(110, 231, 183);    // #6EE7B7 Menta suave (Pagada)
    public static final Color AMBAR_AVISO = new Color(252, 211, 77);        // #FCD34D Ámbar suave (Programada)
    public static final Color CORAL_ELIMINAR = new Color(252, 165, 165);    // #FCA5A5 Coral suave (Eliminar)

    public static final Color TEXTO_BLANCO = new Color(244, 247, 251);      // #F4F7FB Blanco nieve
    public static final Color TEXTO_GRIS_AZUL = new Color(138, 155, 176);   // #8A9BB0 Pizarra legible

    // Tipografía Satoshi cargada desde recursos/fuentes/
    private static final Font SATOSHI_REGULAR_BASE = cargarFuenteSatoshi("Satoshi-Regular.ttf", Font.PLAIN);
    private static final Font SATOSHI_MEDIUM_BASE = cargarFuenteSatoshi("Satoshi-Medium.ttf", Font.PLAIN);
    private static final Font SATOSHI_BOLD_BASE = cargarFuenteSatoshi("Satoshi-Bold.ttf", Font.BOLD);
    private static final Font SATOSHI_BLACK_BASE = cargarFuenteSatoshi("Satoshi-Black.ttf", Font.BOLD);

    public static final Font FUENTE_APP_TITULO = SATOSHI_BLACK_BASE.deriveFont(22f);
    public static final Font FUENTE_NUMERO_GRANDE = SATOSHI_BLACK_BASE.deriveFont(29f);
    public static final Font FUENTE_SUBTITULO = SATOSHI_BOLD_BASE.deriveFont(14.5f);
    public static final Font FUENTE_CUERPO = SATOSHI_REGULAR_BASE.deriveFont(12.5f);
    public static final Font FUENTE_NEGRITA = SATOSHI_BOLD_BASE.deriveFont(12f);
    public static final Font FUENTE_PEQUENA = SATOSHI_MEDIUM_BASE.deriveFont(11f);
    public static final Font FUENTE_MICRO_CAPS = SATOSHI_BOLD_BASE.deriveFont(9.5f);

    public static Font satoshiBold(float tamano) {
        return SATOSHI_BOLD_BASE.deriveFont(tamano);
    }

    public static Font satoshiBlack(float tamano) {
        return SATOSHI_BLACK_BASE.deriveFont(tamano);
    }

    public static Font satoshiMedium(float tamano) {
        return SATOSHI_MEDIUM_BASE.deriveFont(tamano);
    }

    public static Font satoshiRegular(float tamano) {
        return SATOSHI_REGULAR_BASE.deriveFont(tamano);
    }

    private static Font cargarFuenteSatoshi(String nombreArchivo, int estiloRespaldo) {
        String rutaClasspath = "/recursos/fuentes/" + nombreArchivo;
        String[] rutasDisco = {
            "src/recursos/fuentes/" + nombreArchivo,
            "recursos/fuentes/" + nombreArchivo
        };

        try {
            InputStream stream = PaletaMarina.class.getResourceAsStream(rutaClasspath);
            if (stream == null) {
                for (String ruta : rutasDisco) {
                    File archivo = new File(ruta);
                    if (archivo.exists()) {
                        stream = new FileInputStream(archivo);
                        break;
                    }
                }
            }
            if (stream != null) {
                Font fuente = Font.createFont(Font.TRUETYPE_FONT, stream);
                stream.close();
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuente);
                return fuente;
            }
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo cargar " + nombreArchivo + ": " + e.getMessage());
        }
        return new Font("SansSerif", estiloRespaldo, 13);
    }

    public static void estilizarCampo(JTextField campo) {
        campo.setBackground(AZUL_MAR_TARJETA);
        campo.setForeground(TEXTO_BLANCO);
        campo.setCaretColor(CELESTE_BRILLANTE);
        campo.setFont(FUENTE_CUERPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_MARINO, 1),
                BorderFactory.createEmptyBorder(9, 12, 9, 12)));
    }

    public static void estilizarCombo(JComboBox<String> combo) {
        combo.setBackground(AZUL_MAR_TARJETA);
        combo.setForeground(TEXTO_BLANCO);
        combo.setFont(FUENTE_CUERPO);
        combo.setBorder(BorderFactory.createLineBorder(BORDE_MARINO, 1));
    }

    public static void estilizarScrollMovil(JScrollPane scroll) {
        scroll.setBorder(null);
        scroll.setBackground(NEGRO_FONDO);
        scroll.getViewport().setBackground(NEGRO_FONDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JScrollBar barraVertical = scroll.getVerticalScrollBar();
        barraVertical.setPreferredSize(new Dimension(4, 0));
        barraVertical.setBackground(NEGRO_FONDO);
        barraVertical.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = BORDE_MARINO;
                this.trackColor = NEGRO_FONDO;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return botonCero();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return botonCero();
            }

            private JButton botonCero() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                    return;
                }
                g.setColor(BORDE_MARINO);
                g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 4, 4);
            }
        });
    }
}
