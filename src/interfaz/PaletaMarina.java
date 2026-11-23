package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PaletaMarina {
    // Paleta: Negro profundo, Azul de mar oscuro y Celeste brillante
    public static final Color NEGRO_FONDO = new Color(7, 11, 18);
    public static final Color AZUL_MAR_OSCURO = new Color(13, 23, 38);
    public static final Color AZUL_MAR_TARJETA = new Color(18, 32, 54);
    public static final Color AZUL_MAR_MEDIO = new Color(26, 48, 79);
    public static final Color BORDE_MARINO = new Color(36, 65, 105);

    public static final Color CELESTE_BRILLANTE = new Color(56, 189, 248);
    public static final Color CELESTE_PROFUNDO = new Color(2, 132, 199);
    public static final Color CELESTE_SUAVE = new Color(125, 211, 252);

    public static final Color TURQUESA_EXITO = new Color(45, 212, 191);
    public static final Color AMBAR_AVISO = new Color(251, 191, 36);
    public static final Color CORAL_ELIMINAR = new Color(248, 113, 113);

    public static final Color TEXTO_BLANCO = new Color(240, 249, 255);
    public static final Color TEXTO_GRIS_AZUL = new Color(148, 163, 184);

    // Tipografías limpias estilo móvil
    public static final Font FUENTE_APP_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_NUMERO_GRANDE = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FUENTE_CUERPO = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_NEGRITA = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FUENTE_PEQUENA = new Font("Segoe UI", Font.PLAIN, 11);

    public static void estilizarCampo(JTextField campo) {
        campo.setBackground(AZUL_MAR_TARJETA);
        campo.setForeground(TEXTO_BLANCO);
        campo.setCaretColor(CELESTE_BRILLANTE);
        campo.setFont(FUENTE_CUERPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_MARINO, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    }

    public static void estilizarCombo(JComboBox<String> combo) {
        combo.setBackground(AZUL_MAR_TARJETA);
        combo.setForeground(TEXTO_BLANCO);
        combo.setFont(FUENTE_CUERPO);
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
