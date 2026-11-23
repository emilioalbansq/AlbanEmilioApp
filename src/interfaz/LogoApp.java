package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class LogoApp extends JPanel {
    private int tamano;

    public LogoApp(int tamano) {
        this.tamano = tamano;
        setPreferredSize(new Dimension(tamano, tamano));
        setMinimumSize(new Dimension(tamano, tamano));
        setMaximumSize(new Dimension(tamano, tamano));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        dibujarLogotipo(g2, tamano, tamano);
        g2.dispose();
    }

    public static void dibujarLogotipo(Graphics2D g2, int ancho, int alto) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        double cx = ancho / 2.0;
        double cy = alto / 2.0;
        double radioHex = Math.min(ancho, alto) * 0.44;

        // 1. Hexágono exterior con degradado azul marino -> celeste profundo
        Polygon hexagono = crearHexagono(cx, cy, radioHex);
        GradientPaint fondoHex = new GradientPaint(
                0, 0, PaletaMarina.AZUL_MAR_MEDIO,
                ancho, alto, PaletaMarina.CELESTE_PROFUNDO);
        g2.setPaint(fondoHex);
        g2.fill(hexagono);

        // 2. Doble borde luminoso celeste alrededor del isotipo
        g2.setColor(PaletaMarina.CELESTE_BRILLANTE);
        g2.setStroke(new BasicStroke(Math.max(1.5f, ancho * 0.045f)));
        g2.draw(hexagono);

        Polygon hexagonoInterior = crearHexagono(cx, cy, radioHex * 0.84);
        g2.setColor(new Color(147, 222, 255, 95));
        g2.setStroke(new BasicStroke(Math.max(1.0f, ancho * 0.02f)));
        g2.draw(hexagonoInterior);

        // 3. Órbita elíptica de física/cálculo inclinada
        g2.save();
        g2.rotate(Math.toRadians(-28), cx, cy);
        g2.setColor(new Color(56, 189, 248, 145));
        g2.setStroke(new BasicStroke(Math.max(1.1f, ancho * 0.028f)));
        double rx = radioHex * 0.72;
        double ry = radioHex * 0.28;
        g2.draw(new Ellipse2D.Double(cx - rx, cy - ry, rx * 2, ry * 2));
        g2.restore();

        // 4. Monograma geométrico "A" + Símbolo Integral / Rayo celeste brillante
        Path2D monograma = new Path2D.Double();
        double topY = cy - radioHex * 0.44;
        double botY = cy + radioHex * 0.40;
        double leftX = cx - radioHex * 0.34;
        double rightX = cx + radioHex * 0.34;

        monograma.moveTo(leftX, botY);
        monograma.lineTo(cx, topY);
        monograma.lineTo(rightX, botY);

        g2.setColor(PaletaMarina.TEXTO_BLANCO);
        g2.setStroke(new BasicStroke(
                Math.max(2.0f, ancho * 0.068f),
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.draw(monograma);

        // Barra horizontal de la "A" con estilo onda/integral
        Path2D barraOnda = new Path2D.Double();
        double midY = cy + radioHex * 0.12;
        barraOnda.moveTo(cx - radioHex * 0.24, midY);
        barraOnda.curveTo(
                cx - radioHex * 0.08, midY - radioHex * 0.12,
                cx + radioHex * 0.08, midY + radioHex * 0.12,
                cx + radioHex * 0.26, midY - radioHex * 0.04);
        g2.setColor(PaletaMarina.CELESTE_BRILLANTE);
        g2.setStroke(new BasicStroke(Math.max(1.8f, ancho * 0.055f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(barraOnda);

        // 5. Destello orbital superior derecho
        double puntoX = cx + radioHex * 0.36;
        double puntoY = cy - radioHex * 0.30;
        double rPunto = Math.max(2.0, ancho * 0.055);
        g2.setColor(PaletaMarina.TURQUESA_EXITO);
        g2.fill(new Ellipse2D.Double(puntoX - rPunto, puntoY - rPunto, rPunto * 2, rPunto * 2));
    }

    private static Polygon crearHexagono(double cx, double cy, double radio) {
        Polygon p = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angulo = Math.toRadians(60 * i - 30);
            int x = (int) Math.round(cx + radio * Math.cos(angulo));
            int y = (int) Math.round(cy + radio * Math.sin(angulo));
            p.addPoint(x, y);
        }
        return p;
    }

    public static BufferedImage generarIconoVentana(int tamano) {
        BufferedImage imagen = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagen.createGraphics();
        dibujarLogotipo(g2, tamano, tamano);
        g2.dispose();
        return imagen;
    }
}
