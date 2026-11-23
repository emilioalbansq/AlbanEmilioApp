package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;

public class IconosVectoriales {

    public static Icon crearIconoNavegacion(final String tipo, final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                if (tipo.equals("AGENDA")) {
                    // Calendario moderno
                    g2.draw(new RoundRectangle2D.Double(2, 3, tamano - 4, tamano - 5, 5, 5));
                    g2.draw(new Line2D.Double(2, 8, tamano - 2, 8));
                    g2.draw(new Line2D.Double(6, 1, 6, 5));
                    g2.draw(new Line2D.Double(tamano - 6, 1, tamano - 6, 5));
                    g2.fill(new Ellipse2D.Double(6, 11, 2.5, 2.5));
                    g2.fill(new Ellipse2D.Double(10.5, 11, 2.5, 2.5));
                } else if (tipo.equals("ALUMNOS")) {
                    // Silueta de grupo de estudiantes
                    g2.draw(new Ellipse2D.Double(5, 2, 6, 6));
                    Path2D cuerpo = new Path2D.Double();
                    cuerpo.moveTo(2, tamano - 3);
                    cuerpo.quadTo(8, 10, 14, tamano - 3);
                    g2.draw(cuerpo);
                    g2.draw(new Ellipse2D.Double(12, 4, 4.5, 4.5));
                } else {
                    // Billetera / Moneda
                    g2.draw(new RoundRectangle2D.Double(2, 4, tamano - 4, tamano - 7, 5, 5));
                    g2.draw(new RoundRectangle2D.Double(tamano - 8, 8, 6, 5, 2, 2));
                    g2.fill(new Ellipse2D.Double(tamano - 6, 9.8, 2, 2));
                }
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return tamano;
            }

            @Override
            public int getIconHeight() {
                return tamano;
            }
        };
    }

    public static Icon crearIconoMateria(final String materia, final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                String m = materia == null ? "" : materia.toLowerCase();
                if (m.contains("cálculo") || m.contains("calculo")) {
                    // Símbolo integral estilizado
                    Path2D integral = new Path2D.Double();
                    integral.moveTo(tamano * 0.68, 2.5);
                    integral.curveTo(tamano * 0.45, 2.5, tamano * 0.55, tamano - 2.5, tamano * 0.32, tamano - 2.5);
                    g2.draw(integral);
                } else if (m.contains("álgebra") || m.contains("algebra")) {
                    // Matriz 2x2
                    g2.draw(new Line2D.Double(4, 2, 2, 2));
                    g2.draw(new Line2D.Double(2, 2, 2, tamano - 2));
                    g2.draw(new Line2D.Double(2, tamano - 2, 4, tamano - 2));

                    g2.draw(new Line2D.Double(tamano - 4, 2, tamano - 2, 2));
                    g2.draw(new Line2D.Double(tamano - 2, 2, tamano - 2, tamano - 2));
                    g2.draw(new Line2D.Double(tamano - 2, tamano - 2, tamano - 4, tamano - 2));

                    g2.fill(new Ellipse2D.Double(5, 5, 2.3, 2.3));
                    g2.fill(new Ellipse2D.Double(tamano - 7.3, tamano - 7.3, 2.3, 2.3));
                } else if (m.contains("física") || m.contains("fisica")) {
                    // Órbita atómica
                    g2.draw(new Ellipse2D.Double(2, 5, tamano - 4, tamano - 10));
                    g2.fill(new Ellipse2D.Double(tamano / 2.0 - 2, tamano / 2.0 - 2, 4, 4));
                } else if (m.contains("geometría") || m.contains("geometria")) {
                    // Triángulo rectángulo
                    Path2D tri = new Path2D.Double();
                    tri.moveTo(3, tamano - 3);
                    tri.lineTo(3, 3);
                    tri.lineTo(tamano - 3, tamano - 3);
                    tri.closePath();
                    g2.draw(tri);
                } else {
                    // Fundamentos: Cubo / Lógica
                    g2.draw(new RoundRectangle2D.Double(3, 3, tamano - 6, tamano - 6, 4, 4));
                    g2.draw(new Line2D.Double(6, tamano / 2.0, tamano - 6, tamano / 2.0));
                    g2.draw(new Line2D.Double(tamano / 2.0, 6, tamano / 2.0, tamano - 6));
                }
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return tamano;
            }

            @Override
            public int getIconHeight() {
                return tamano;
            }
        };
    }

    public static Icon crearIconoReloj(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                g2.draw(new Ellipse2D.Double(1.5, 1.5, tamano - 3, tamano - 3));
                double centro = tamano / 2.0;
                g2.draw(new Line2D.Double(centro, centro, centro, 4));
                g2.draw(new Line2D.Double(centro, centro, tamano - 4, centro));
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return tamano;
            }

            @Override
            public int getIconHeight() {
                return tamano;
            }
        };
    }

    public static Icon crearIconoBirrete(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                Path2D rombo = new Path2D.Double();
                rombo.moveTo(tamano / 2.0, 2);
                rombo.lineTo(tamano - 1, 6);
                rombo.lineTo(tamano / 2.0, 10);
                rombo.lineTo(1, 6);
                rombo.closePath();
                g2.draw(rombo);

                Path2D base = new Path2D.Double();
                base.moveTo(4, 8);
                base.lineTo(4, 12);
                base.quadTo(tamano / 2.0, 14, tamano - 4, 12);
                base.lineTo(tamano - 4, 8);
                g2.draw(base);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return tamano;
            }

            @Override
            public int getIconHeight() {
                return tamano;
            }
        };
    }
}
