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
                g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                if (tipo.equals("AGENDA")) {
                    g2.draw(new RoundRectangle2D.Double(2, 3, tamano - 4, tamano - 5, 5, 5));
                    g2.draw(new Line2D.Double(2, 8, tamano - 2, 8));
                    g2.draw(new Line2D.Double(6, 1, 6, 5));
                    g2.draw(new Line2D.Double(tamano - 6, 1, tamano - 6, 5));
                    g2.fill(new Ellipse2D.Double(5.8, 11, 2.4, 2.4));
                    g2.fill(new Ellipse2D.Double(10.2, 11, 2.4, 2.4));
                } else if (tipo.equals("ALUMNOS")) {
                    g2.draw(new Ellipse2D.Double(5, 2, 6, 6));
                    Path2D cuerpo = new Path2D.Double();
                    cuerpo.moveTo(2, tamano - 3);
                    cuerpo.quadTo(8, 10, 14, tamano - 3);
                    g2.draw(cuerpo);
                    g2.draw(new Ellipse2D.Double(12, 4, 4.5, 4.5));
                } else {
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
                g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                String m = materia == null ? "" : materia.toLowerCase();
                if (m.contains("cálculo") || m.contains("calculo")) {
                    Path2D integral = new Path2D.Double();
                    integral.moveTo(tamano * 0.68, 2.5);
                    integral.curveTo(tamano * 0.45, 2.5, tamano * 0.55, tamano - 2.5, tamano * 0.32, tamano - 2.5);
                    g2.draw(integral);
                } else if (m.contains("álgebra") || m.contains("algebra")) {
                    g2.draw(new Line2D.Double(4, 2, 2, 2));
                    g2.draw(new Line2D.Double(2, 2, 2, tamano - 2));
                    g2.draw(new Line2D.Double(2, tamano - 2, 4, tamano - 2));

                    g2.draw(new Line2D.Double(tamano - 4, 2, tamano - 2, 2));
                    g2.draw(new Line2D.Double(tamano - 2, 2, tamano - 2, tamano - 2));
                    g2.draw(new Line2D.Double(tamano - 2, tamano - 2, tamano - 4, tamano - 2));

                    g2.fill(new Ellipse2D.Double(5, 5, 2.2, 2.2));
                    g2.fill(new Ellipse2D.Double(tamano - 7.2, tamano - 7.2, 2.2, 2.2));
                } else if (m.contains("física") || m.contains("fisica")) {
                    g2.draw(new Ellipse2D.Double(2, 4.5, tamano - 4, tamano - 9));
                    g2.fill(new Ellipse2D.Double(tamano / 2.0 - 2, tamano / 2.0 - 2, 4, 4));
                } else if (m.contains("geometría") || m.contains("geometria")) {
                    Path2D tri = new Path2D.Double();
                    tri.moveTo(3, tamano - 3);
                    tri.lineTo(3, 3);
                    tri.lineTo(tamano - 3, tamano - 3);
                    tri.closePath();
                    g2.draw(tri);
                } else {
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

    public static Icon crearIconoCalendario(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                g2.draw(new RoundRectangle2D.Double(1.5, 2.5, tamano - 3, tamano - 4, 4, 4));
                g2.draw(new Line2D.Double(1.5, 6.5, tamano - 1.5, 6.5));
                g2.draw(new Line2D.Double(4.5, 1, 4.5, 4));
                g2.draw(new Line2D.Double(tamano - 4.5, 1, tamano - 4.5, 4));
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
                g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                g2.draw(new Ellipse2D.Double(1.5, 1.5, tamano - 3, tamano - 3));
                double centro = tamano / 2.0;
                g2.draw(new Line2D.Double(centro, centro, centro, 3.8));
                g2.draw(new Line2D.Double(centro, centro, tamano - 3.8, centro));
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

    public static Icon crearIconoCapitulo(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Marcador / etiqueta de capítulo
                Path2D bookmark = new Path2D.Double();
                bookmark.moveTo(3, 2);
                bookmark.lineTo(tamano - 3, 2);
                bookmark.lineTo(tamano - 3, tamano - 2);
                bookmark.lineTo(tamano / 2.0, tamano - 5);
                bookmark.lineTo(3, tamano - 2);
                bookmark.closePath();
                g2.draw(bookmark);
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

    public static Icon crearIconoTema(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Cuaderno / tema abierto
                g2.draw(new RoundRectangle2D.Double(2, 1.5, tamano - 4, tamano - 3, 3, 3));
                g2.draw(new Line2D.Double(5, 5, tamano - 5, 5));
                g2.draw(new Line2D.Double(5, 8, tamano - 5, 8));
                g2.draw(new Line2D.Double(5, 11, tamano - 7, 11));
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

    public static Icon crearIconoGuia(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Portapapeles de guía de ejercicios
                g2.draw(new RoundRectangle2D.Double(2.5, 3, tamano - 5, tamano - 4.5, 3, 3));
                g2.draw(new RoundRectangle2D.Double(5, 1.5, tamano - 10, 3, 2, 2));
                g2.draw(new Line2D.Double(5, 7.5, tamano - 5, 7.5));
                g2.draw(new Line2D.Double(5, 10.5, tamano - 6.5, 10.5));
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

    public static Icon crearIconoCheckEjercicio(
            final boolean completado,
            final Color colorActivo,
            final Color colorInactivo,
            final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);

                RoundRectangle2D caja = new RoundRectangle2D.Double(1, 1, tamano - 2, tamano - 2, 4, 4);
                if (completado) {
                    g2.setColor(colorActivo);
                    g2.fill(caja);
                    g2.setColor(PaletaMarina.NEGRO_FONDO);
                    g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    Path2D paloma = new Path2D.Double();
                    paloma.moveTo(3.5, tamano * 0.52);
                    paloma.lineTo(tamano * 0.43, tamano - 3.8);
                    paloma.lineTo(tamano - 3.2, 3.8);
                    g2.draw(paloma);
                } else {
                    g2.setColor(colorInactivo);
                    g2.setStroke(new BasicStroke(1.35f));
                    g2.draw(caja);
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

    public static Icon crearIconoBirrete(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

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

    public static Icon crearIconoCobro(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.45f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                g2.draw(new Ellipse2D.Double(1.5, 1.5, tamano - 3, tamano - 3));
                double cx = tamano / 2.0;
                g2.draw(new Line2D.Double(cx, 3.5, cx, tamano - 3.5));
                Path2D sCurve = new Path2D.Double();
                sCurve.moveTo(cx + 2.2, 5.2);
                sCurve.curveTo(cx - 3.0, 4.5, cx - 2.5, 7.2, cx, 7.5);
                sCurve.curveTo(cx + 2.5, 7.8, cx + 3.0, 10.5, cx - 2.2, 9.8);
                g2.draw(sCurve);
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

    public static Icon crearIconoBasurero(final Color color, final int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.translate(x, y);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Tapa y cubo minimalista
                g2.draw(new Line2D.Double(2, 4, tamano - 2, 4));
                g2.draw(new Line2D.Double(5.5, 2, tamano - 5.5, 2));
                Path2D cubo = new Path2D.Double();
                cubo.moveTo(3.5, 4);
                cubo.lineTo(4.5, tamano - 2);
                cubo.lineTo(tamano - 4.5, tamano - 2);
                cubo.lineTo(tamano - 3.5, 4);
                g2.draw(cubo);
                g2.draw(new Line2D.Double(6.5, 6.5, 6.5, tamano - 4.5));
                g2.draw(new Line2D.Double(tamano - 6.5, 6.5, tamano - 6.5, tamano - 4.5));
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
