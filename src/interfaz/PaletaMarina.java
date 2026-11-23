package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
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

    // Selección automática de tipografía geométrica moderna disponible en el sistema
    public static final String FAMILIA_MODERNA = detectarFuenteModerna();
    public static final String FAMILIA_NUMEROS = detectarFuenteNumeros();

    public static final Font FUENTE_APP_TITULO = new Font(FAMILIA_MODERNA, Font.BOLD, 23);
    public static final Font FUENTE_NUMERO_GRANDE = new Font(FAMILIA_NUMEROS, Font.BOLD, 28);
    public static final Font FUENTE_SUBTITULO = new Font(FAMILIA_MODERNA, Font.BOLD, 15);
    public static final Font FUENTE_CUERPO = new Font(FAMILIA_MODERNA, Font.PLAIN, 13);
    public static final Font FUENTE_NEGRITA = new Font(FAMILIA_MODERNA, Font.BOLD, 12);
    public static final Font FUENTE_PEQUENA = new Font(FAMILIA_MODERNA, Font.PLAIN, 11);

    private static String detectarFuenteModerna() {
        String[] preferidas = {"Bahnschrift", "Century Gothic", "Trebuchet MS", "Verdana", "SansSerif"};
        String[] disponibles = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String candidata : preferidas) {
            for (String instalada : disponibles) {
                if (instalada.equalsIgnoreCase(candidata)) {
                    return instalada;
                }
            }
        }
        return "SansSerif";
    }

    private static String detectarFuenteNumeros() {
        String[] preferidas = {"Bahnschrift", "Consolas", "Trebuchet MS", "SansSerif"};
        String[] disponibles = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String candidata : preferidas) {
            for (String instalada : disponibles) {
                if (instalada.equalsIgnoreCase(candidata)) {
                    return instalada;
                }
            }
        }
        return "SansSerif";
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
