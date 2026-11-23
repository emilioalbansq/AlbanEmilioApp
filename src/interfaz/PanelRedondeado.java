package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class PanelRedondeado extends JPanel {
    private int radioEsquinas;
    private Color colorFondo;
    private Color colorDegradadoFin;
    private Color colorBorde;

    public PanelRedondeado(int radioEsquinas, Color colorFondo, Color colorBorde) {
        this.radioEsquinas = radioEsquinas;
        this.colorFondo = colorFondo;
        this.colorDegradadoFin = null;
        this.colorBorde = colorBorde;
        setOpaque(false);
    }

    public PanelRedondeado(int radioEsquinas, Color colorInicio, Color colorFin, Color colorBorde) {
        this.radioEsquinas = radioEsquinas;
        this.colorFondo = colorInicio;
        this.colorDegradadoFin = colorFin;
        this.colorBorde = colorBorde;
        setOpaque(false);
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
        repaint();
    }

    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();
        RoundRectangle2D forma = new RoundRectangle2D.Float(1, 1, ancho - 2, alto - 2, radioEsquinas, radioEsquinas);

        if (colorDegradadoFin != null) {
            GradientPaint gradiente = new GradientPaint(0, 0, colorFondo, ancho, alto, colorDegradadoFin);
            g2.setPaint(gradiente);
        } else {
            g2.setColor(colorFondo);
        }
        g2.fill(forma);

        if (colorBorde != null) {
            g2.setColor(colorBorde);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(forma);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
