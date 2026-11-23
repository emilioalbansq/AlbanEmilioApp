package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PaletaMarina {
    // Paleta sofisticada: Negro abisal, Azul marino profundo y Celeste luminoso
    public static final Color NEGRO_FONDO = new Color(5, 9, 16);
    public static final Color AZUL_MAR_OSCURO = new Color(11, 21, 37);
    public static final Color AZUL_MAR_TARJETA = new Color(16, 30, 52);
    public static final Color AZUL_MAR_MEDIO = new Color(24, 48, 82);
    public static final Color BORDE_MARINO = new Color(41, 78, 128);
    public static final Color BORDE_BRILLO_CELESTE = new Color(56, 189, 248, 130);

    public static final Color CELESTE_BRILLANTE = new Color(56, 189, 248);
    public static final Color CELESTE_PROFUNDO = new Color(2, 132, 199);
    public static final Color CELESTE_SUAVE = new Color(147, 222, 255);

    public static final Color TURQUESA_EXITO = new Color(45, 212, 191);
    public static final Color AMBAR_AVISO = new Color(251, 191, 36);
    public static final Color CORAL_ELIMINAR = new Color(248, 113, 113);

    public static final Color TEXTO_BLANCO = new Color(244, 250, 255);
    public static final Color TEXTO_GRIS_AZUL = new Color(148, 168, 194);

    // Tipografía Satoshi cargada desde recursos/fuentes/
    private static final Font SATOSHI_REGULAR_BASE = cargarFuenteSatoshi("Satoshi-Regular.ttf", Font.PLAIN);
    private static final Font SATOSHI_MEDIUM_BASE = cargarFuenteSatoshi("Satoshi-Medium.ttf", Font.PLAIN);
    private static final Font SATOSHI_BOLD_BASE = cargarFuenteSatoshi("Satoshi-Bold.ttf", Font.BOLD);
    private static final Font SATOSHI_BLACK_BASE = cargarFuenteSatoshi("Satoshi-Black.ttf", Font.BOLD);

    public static final Font FUENTE_APP_TITULO = SATOSHI_BLACK_BASE.deriveFont(23f);
    public static final Font FUENTE_NUMERO_GRANDE = SATOSHI_BLACK_BASE.deriveFont(29f);
    public static final Font FUENTE_SUBTITULO = SATOSHI_BOLD_BASE.deriveFont(15f);
    public static final Font FUENTE_CUERPO = SATOSHI_REGULAR_BASE.deriveFont(13f);
    public static final Font FUENTE_NEGRITA = SATOSHI_BOLD_BASE.deriveFont(12.5f);
    public static final Font FUENTE_PEQUENA = SATOSHI_MEDIUM_BASE.deriveFont(11.5f);

    public static Font satoshiBold(float tamano) {
        return SATOSHI_BOLD_BASE.deriveFont(tamano);
    }

    public static Font satoshiBlack(float tamano) {
        return SATOSHI_BLACK_BASE.deriveFont(tamano);
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
                BorderFactory.createLineBorder(CELESTE_PROFUNDO, 1),
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
        barraVertical.setPreferredSize(new Dimension(5, 0));
        barraVertical.setBackground(NEGRO_FONDO);
    }
}
