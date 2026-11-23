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
    private boolean dobleBordeLuminoso;

    public PanelRedondeado(int radioEsquinas, Color colorFondo, Color colorBorde) {
        this.radioEsquinas = radioEsquinas;
        this.colorFondo = colorFondo;
        this.colorDegradadoFin = null;
        this.colorBorde = colorBorde;
        this.dobleBordeLuminoso = false;
        setOpaque(false);
    }

    public PanelRedondeado(int radioEsquinas, Color colorInicio, Color colorFin, Color colorBorde) {
        this.radioEsquinas = radioEsquinas;
        this.colorFondo = colorInicio;
        this.colorDegradadoFin = colorFin;
        this.colorBorde = colorBorde;
        this.dobleBordeLuminoso = true;
        setOpaque(false);
    }

    public void setDobleBordeLuminoso(boolean dobleBordeLuminoso) {
        this.dobleBordeLuminoso = dobleBordeLuminoso;
        repaint();
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
        RoundRectangle2D formaExterior = new RoundRectangle2D.Float(
                1.5f, 1.5f, ancho - 3f, alto - 3f, radioEsquinas, radioEsquinas);

        if (colorDegradadoFin != null) {
            GradientPaint gradiente = new GradientPaint(0, 0, colorFondo, ancho, alto, colorDegradadoFin);
            g2.setPaint(gradiente);
        } else {
            g2.setColor(colorFondo);
        }
        g2.fill(formaExterior);

        if (colorBorde != null) {
            g2.setColor(colorBorde);
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(formaExterior);

            if (dobleBordeLuminoso && ancho > 16 && alto > 16) {
                RoundRectangle2D formaInterior = new RoundRectangle2D.Float(
                        4f, 4f, ancho - 8f, alto - 8f,
                        Math.max(6, radioEsquinas - 4),
                        Math.max(6, radioEsquinas - 4));
                g2.setColor(new Color(147, 222, 255, 65));
                g2.setStroke(new BasicStroke(1.0f));
                g2.draw(formaInterior);
            }
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
