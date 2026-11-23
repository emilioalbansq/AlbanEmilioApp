package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class BotonModerno extends JButton {
    private int radio;
    private Color colorBase;
    private Color colorSecundario;
    private Color colorBorde;
    private boolean encima;

    public BotonModerno(String texto, Color colorBase, Color colorTexto, int radio) {
        super(texto);
        this.colorBase = colorBase;
        this.colorSecundario = null;
        this.colorBorde = PaletaMarina.BORDE_MARINO;
        this.radio = radio;
        this.encima = false;

        configurarEstilo(colorTexto, PaletaMarina.FUENTE_NEGRITA, 7, 13);
    }

    public BotonModerno(String texto, Color colorInicio, Color colorFin, Color colorTexto, int radio) {
        super(texto);
        this.colorBase = colorInicio;
        this.colorSecundario = colorFin;
        this.colorBorde = PaletaMarina.CELESTE_SUAVE;
        this.radio = radio;
        this.encima = false;

        configurarEstilo(colorTexto, PaletaMarina.FUENTE_SUBTITULO, 9, 15);
    }

    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
        repaint();
    }

    private void configurarEstilo(Color colorTexto, Font fuente, int padV, int padH) {
        setForeground(colorTexto);
        setFont(fuente);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(padV, padH, padV, padH));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                encima = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                encima = false;
                repaint();
            }
        });
    }

    public void actualizarColores(Color nuevoFondo, Color nuevoTexto) {
        this.colorBase = nuevoFondo;
        this.colorSecundario = null;
        setForeground(nuevoTexto);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D forma = new RoundRectangle2D.Float(
                1f, 1f, getWidth() - 2f, getHeight() - 2f, radio, radio);

        if (colorSecundario != null) {
            GradientPaint grad = new GradientPaint(0, 0, colorBase, getWidth(), getHeight(), colorSecundario);
            g2.setPaint(grad);
        } else {
            Color actual = encima ? colorBase.brighter() : colorBase;
            g2.setColor(actual);
        }
        g2.fill(forma);

        if (colorBorde != null) {
            g2.setColor(encima ? PaletaMarina.TEXTO_BLANCO : colorBorde);
            g2.setStroke(new BasicStroke(1.15f));
            g2.draw(forma);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
