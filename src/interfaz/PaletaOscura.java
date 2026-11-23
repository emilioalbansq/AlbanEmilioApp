package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

public class PaletaOscura {
    public static final Color FONDO_PRINCIPAL = new Color(16, 18, 23);
    public static final Color FONDO_TARJETA = new Color(25, 29, 37);
    public static final Color FONDO_CAMPO = new Color(34, 39, 50);
    public static final Color BORDE_SUTIL = new Color(52, 59, 73);

    public static final Color AZUL_ACENTO = new Color(59, 130, 246);
    public static final Color VERDE_EXITO = new Color(16, 185, 129);
    public static final Color AMBAR_PENDIENTE = new Color(245, 158, 11);
    public static final Color ROJO_PELIGRO = new Color(239, 68, 68);

    public static final Color TEXTO_PRINCIPAL = new Color(241, 245, 249);
    public static final Color TEXTO_SECUNDARIO = new Color(148, 163, 184);

    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_NEGRITA = new Font("Segoe UI", Font.BOLD, 13);

    public static JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(FUENTE_NEGRITA);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        return boton;
    }

    public static void estilizarCampoTexto(JTextField campo) {
        campo.setBackground(FONDO_CAMPO);
        campo.setForeground(TEXTO_PRINCIPAL);
        campo.setCaretColor(TEXTO_PRINCIPAL);
        campo.setFont(FUENTE_NORMAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_SUTIL, 1),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)));
    }

    public static void estilizarComboBox(JComboBox<String> combo) {
        combo.setBackground(FONDO_CAMPO);
        combo.setForeground(TEXTO_PRINCIPAL);
        combo.setFont(FUENTE_NORMAL);
    }

    public static void estilizarTabla(JTable tabla) {
        tabla.setBackground(FONDO_TARJETA);
        tabla.setForeground(TEXTO_PRINCIPAL);
        tabla.setGridColor(BORDE_SUTIL);
        tabla.setSelectionBackground(new Color(45, 75, 130));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(32);
        tabla.setShowVerticalLines(false);

        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setBackground(new Color(20, 23, 30));
        encabezado.setForeground(TEXTO_SECUNDARIO);
        encabezado.setFont(FUENTE_NEGRITA);
        encabezado.setPreferredSize(new Dimension(0, 36));
        encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE_SUTIL));
    }
}
