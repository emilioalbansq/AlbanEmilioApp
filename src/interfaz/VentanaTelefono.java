package interfaz;

import controladores.ControladorTutorias;
import entidades.ClaseTutoria;
import entidades.EjercicioPlanificado;
import entidades.Estudiante;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class VentanaTelefono extends JFrame {
    private ControladorTutorias controlador;
    private String materiaSeleccionada;
    private String pestanaActiva;

    private CardLayout cardLayoutPantallas;
    private JPanel contenedorPantallas;

    private JPanel listaTarjetasClases;
    private List<BotonModerno> botonesFiltroMateria;
    private JPanel listaTarjetasEstudiantes;
    private JPanel contenedorCaja;

    private BotonModerno botonAccionRapida;

    private PanelRedondeado tabAgenda;
    private PanelRedondeado tabAlumnos;
    private PanelRedondeado tabCaja;
    private JLabel lblTabAgenda;
    private JLabel lblTabAlumnos;
    private JLabel lblTabCaja;

    public VentanaTelefono() {
        this.controlador = new ControladorTutorias();
        this.materiaSeleccionada = "Todas";
        this.pestanaActiva = "AGENDA";
        this.botonesFiltroMateria = new ArrayList<>();

        setTitle("AulEmi • Gestión de Tutorías");
        setIconImage(LogoApp.generarIconoVentana(64));
        setSize(446, 875);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel carcasaTelefono = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PaletaMarina.BORDE_MARINO);
                g2.setStroke(new BasicStroke(2.2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 28, 28);
                g2.setColor(PaletaMarina.BORDE_BRILLO_CELESTE);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(4, 4, getWidth() - 9, getHeight() - 9, 24, 24);
                g2.dispose();
            }
        };
        carcasaTelefono.setBackground(PaletaMarina.NEGRO_FONDO);
        carcasaTelefono.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(carcasaTelefono);

        construirBarraSuperiorTelefono();
        construirPantallasCentrales();
        construirBarraNavegacionInferior();

        actualizarTodo();
    }

    // =========================================================================
    // ENCABEZADO MÓVIL + DYNAMIC ISLAND + LOGO BÚHO PLATA HIELO
    // =========================================================================
    private void construirBarraSuperiorTelefono() {
        JPanel bloqueSuperior = new JPanel();
        bloqueSuperior.setLayout(new BoxLayout(bloqueSuperior, BoxLayout.Y_AXIS));
        bloqueSuperior.setBackground(PaletaMarina.NEGRO_FONDO);

        JPanel statusBar = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int islaAncho = 96;
                int islaAlto = 18;
                int islaX = (getWidth() - islaAncho) / 2;
                int islaY = 6;
                g2.setColor(PaletaMarina.AZUL_MAR_OSCURO);
                g2.fillRoundRect(islaX, islaY, islaAncho, islaAlto, 16, 16);
                g2.setColor(PaletaMarina.BORDE_MARINO);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(islaX, islaY, islaAncho, islaAlto, 16, 16);

                g2.setColor(PaletaMarina.AZUL_MAR_MEDIO);
                g2.fillOval(islaX + islaAncho - 22, islaY + 5, 8, 8);
                g2.setColor(PaletaMarina.CELESTE_BRILLANTE);
                g2.fillOval(islaX + islaAncho - 20, islaY + 7, 4, 4);

                int xBase = getWidth() - 68;
                int yBase = 10;
                g2.setColor(PaletaMarina.TEXTO_BLANCO);
                g2.fillRoundRect(xBase, yBase + 6, 3, 4, 1, 1);
                g2.fillRoundRect(xBase + 5, yBase + 4, 3, 6, 1, 1);
                g2.fillRoundRect(xBase + 10, yBase + 2, 3, 8, 1, 1);
                g2.fillRoundRect(xBase + 15, yBase, 3, 10, 1, 1);

                g2.setColor(PaletaMarina.CELESTE_SUAVE);
                g2.drawRoundRect(xBase + 25, yBase + 1, 21, 9, 4, 4);
                g2.fillRect(xBase + 47, yBase + 3, 2, 5);
                g2.setColor(PaletaMarina.TEXTO_BLANCO);
                g2.fillRoundRect(xBase + 27, yBase + 3, 16, 5, 2, 2);
                g2.dispose();
            }
        };
        statusBar.setBackground(PaletaMarina.NEGRO_FONDO);
        statusBar.setPreferredSize(new Dimension(434, 30));
        statusBar.setMaximumSize(new Dimension(434, 30));
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 20));

        JLabel lblHoraCelular = new JLabel("23:34");
        lblHoraCelular.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblHoraCelular.setFont(PaletaMarina.FUENTE_NEGRITA);
        statusBar.add(lblHoraCelular, BorderLayout.WEST);

        JPanel appBar = new JPanel(new BorderLayout());
        appBar.setBackground(PaletaMarina.NEGRO_FONDO);
        appBar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        appBar.setMaximumSize(new Dimension(434, 64));

        JPanel tituloConLogo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tituloConLogo.setBackground(PaletaMarina.NEGRO_FONDO);

        LogoApp logoPrincipal = new LogoApp(44);

        JPanel textosTitulo = new JPanel();
        textosTitulo.setLayout(new BoxLayout(textosTitulo, BoxLayout.Y_AXIS));
        textosTitulo.setBackground(PaletaMarina.NEGRO_FONDO);

        JLabel lblApp = new JLabel("AulEmi");
        lblApp.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblApp.setFont(PaletaMarina.FUENTE_APP_TITULO);

        JLabel lblSub = new JLabel("Tutorías • Emilio Albán");
        lblSub.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        lblSub.setFont(PaletaMarina.FUENTE_PEQUENA);

        textosTitulo.add(lblApp);
        textosTitulo.add(lblSub);

        tituloConLogo.add(logoPrincipal);
        tituloConLogo.add(textosTitulo);

        // Botón principal estilo píldora esmerilada blanca-plata (Byte)
        botonAccionRapida = new BotonModerno(
                "+ Clase",
                PaletaMarina.TEXTO_BLANCO,
                PaletaMarina.CELESTE_BRILLANTE,
                PaletaMarina.NEGRO_FONDO,
                20);
        botonAccionRapida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarAccionPrincipal();
            }
        });

        appBar.add(tituloConLogo, BorderLayout.WEST);
        appBar.add(botonAccionRapida, BorderLayout.EAST);

        bloqueSuperior.add(statusBar);
        bloqueSuperior.add(appBar);
        add(bloqueSuperior, BorderLayout.NORTH);
    }

    // =========================================================================
    // PANTALLAS CENTRALES
    // =========================================================================
    private void construirPantallasCentrales() {
        cardLayoutPantallas = new CardLayout();
        contenedorPantallas = new JPanel(cardLayoutPantallas);
        contenedorPantallas.setBackground(PaletaMarina.NEGRO_FONDO);

        contenedorPantallas.add(crearPantallaAgenda(), "AGENDA");
        contenedorPantallas.add(crearPantallaAlumnos(), "ALUMNOS");
        contenedorPantallas.add(crearPantallaCaja(), "CAJA");

        add(contenedorPantallas, BorderLayout.CENTER);
    }

    // -------------------- PANTALLA 1: AGENDA SEGMENTADA --------------------
    private JPanel crearPantallaAgenda() {
        JPanel pantalla = new JPanel(new BorderLayout(0, 6));
        pantalla.setBackground(PaletaMarina.NEGRO_FONDO);

        JPanel barraFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        barraFiltros.setBackground(PaletaMarina.NEGRO_FONDO);

        List<String> filtros = new ArrayList<>();
        filtros.add("Todas");
        for (String mat : ControladorTutorias.MATERIAS_DISPONIBLES) {
            filtros.add(mat);
        }

        for (final String mat : filtros) {
            String etiquetaCorta = mat.replace("Fundamentos de ", "Fund. ");
            boolean activo = mat.equals(materiaSeleccionada);
            BotonModerno chip = new BotonModerno(
                    etiquetaCorta,
                    activo ? PaletaMarina.CELESTE_BRILLANTE : PaletaMarina.AZUL_MAR_TARJETA,
                    activo ? PaletaMarina.NEGRO_FONDO : PaletaMarina.TEXTO_GRIS_AZUL,
                    18);
            chip.setColorBorde(activo ? PaletaMarina.TEXTO_BLANCO : PaletaMarina.BORDE_MARINO);
            if (!mat.equals("Todas")) {
                chip.setIcon(IconosVectoriales.crearIconoMateria(
                        mat,
                        activo ? PaletaMarina.NEGRO_FONDO : PaletaMarina.CELESTE_BRILLANTE,
                        13));
            }
            chip.putClientProperty("materiaCompleta", mat);
            chip.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    materiaSeleccionada = mat;
                    actualizarEstilosChipsMateria();
                    renderizarTarjetasClases();
                }
            });
            botonesFiltroMateria.add(chip);
            barraFiltros.add(chip);
        }

        // Scroll horizontal sin barra blanca visible
        JScrollPane scrollChips = new JScrollPane(
                barraFiltros,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollChips.setBorder(null);
        scrollChips.setBackground(PaletaMarina.NEGRO_FONDO);
        scrollChips.getViewport().setBackground(PaletaMarina.NEGRO_FONDO);
        scrollChips.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        listaTarjetasClases = new JPanel();
        listaTarjetasClases.setLayout(new BoxLayout(listaTarjetasClases, BoxLayout.Y_AXIS));
        listaTarjetasClases.setBackground(PaletaMarina.NEGRO_FONDO);
        listaTarjetasClases.setBorder(BorderFactory.createEmptyBorder(4, 14, 20, 14));

        JScrollPane scrollFeed = new JScrollPane(listaTarjetasClases);
        PaletaMarina.estilizarScrollMovil(scrollFeed);

        pantalla.add(scrollChips, BorderLayout.NORTH);
        pantalla.add(scrollFeed, BorderLayout.CENTER);
        return pantalla;
    }

    private void actualizarEstilosChipsMateria() {
        for (BotonModerno chip : botonesFiltroMateria) {
            String mat = (String) chip.getClientProperty("materiaCompleta");
            boolean activo = mat.equals(materiaSeleccionada);
            chip.actualizarColores(
                    activo ? PaletaMarina.CELESTE_BRILLANTE : PaletaMarina.AZUL_MAR_TARJETA,
                    activo ? PaletaMarina.NEGRO_FONDO : PaletaMarina.TEXTO_GRIS_AZUL);
            chip.setColorBorde(activo ? PaletaMarina.TEXTO_BLANCO : PaletaMarina.BORDE_MARINO);
            if (!mat.equals("Todas")) {
                chip.setIcon(IconosVectoriales.crearIconoMateria(
                        mat,
                        activo ? PaletaMarina.NEGRO_FONDO : PaletaMarina.CELESTE_BRILLANTE,
                        13));
            }
        }
    }

    private void renderizarTarjetasClases() {
        listaTarjetasClases.removeAll();
        List<ClaseTutoria> clases = controlador.obtenerClases(materiaSeleccionada);

        if (clases.isEmpty()) {
            JLabel vacio = new JLabel("No hay tutorías agendadas en esta materia", SwingConstants.CENTER);
            vacio.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
            vacio.setFont(PaletaMarina.FUENTE_CUERPO);
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            listaTarjetasClases.add(Box.createVerticalStrut(60));
            listaTarjetasClases.add(vacio);
        } else {
            for (ClaseTutoria clase : clases) {
                listaTarjetasClases.add(crearTarjetaClase(clase));
                listaTarjetasClases.add(Box.createVerticalStrut(14));
            }
        }

        listaTarjetasClases.revalidate();
        listaTarjetasClases.repaint();
    }

    // =========================================================================
    // TARJETA DE CLASE SEGMENTADA EN CUADRITOS CON LOGOS DEDICADOS
    // =========================================================================
    private PanelRedondeado crearTarjetaClase(final ClaseTutoria clase) {
        PanelRedondeado tarjeta = new PanelRedondeado(
                22,
                PaletaMarina.AZUL_MAR_OSCURO,
                PaletaMarina.BORDE_MARINO);
        tarjeta.setDobleBordeLuminoso(true);
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        tarjeta.setMaximumSize(new Dimension(402, 760));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ---------------------------------------------------------------------
        // CABECERA: Píldora de Materia + Píldora de Estado
        // ---------------------------------------------------------------------
        JPanel filaSuperior = new JPanel(new BorderLayout());
        filaSuperior.setOpaque(false);
        filaSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);

        PanelRedondeado pillMateria = new PanelRedondeado(
                14,
                PaletaMarina.AZUL_MAR_MEDIO,
                PaletaMarina.CELESTE_PROFUNDO);
        pillMateria.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 4));
        JLabel lblMat = new JLabel(clase.getMateria());
        lblMat.setIcon(IconosVectoriales.crearIconoMateria(
                clase.getMateria(),
                PaletaMarina.TEXTO_BLANCO,
                14));
        lblMat.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblMat.setFont(PaletaMarina.FUENTE_NEGRITA);
        pillMateria.add(lblMat);

        Color colorEstado = clase.isPagada()
                ? PaletaMarina.TURQUESA_EXITO
                : (clase.isDictada() ? PaletaMarina.CELESTE_BRILLANTE : PaletaMarina.AMBAR_AVISO);

        PanelRedondeado pillEstado = new PanelRedondeado(
                12,
                PaletaMarina.AZUL_MAR_TARJETA,
                PaletaMarina.BORDE_MARINO);
        pillEstado.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 4));
        JLabel lblEstado = new JLabel("● " + clase.obtenerEstadoTexto());
        lblEstado.setForeground(colorEstado);
        lblEstado.setFont(PaletaMarina.FUENTE_NEGRITA);
        pillEstado.add(lblEstado);

        filaSuperior.add(pillMateria, BorderLayout.WEST);
        filaSuperior.add(pillEstado, BorderLayout.EAST);

        tarjeta.add(filaSuperior);
        tarjeta.add(Box.createVerticalStrut(10));

        // ---------------------------------------------------------------------
        // BLOQUE 1 (2 CUADRITOS): DÍA  |  HORARIO Y DURACIÓN
        // ---------------------------------------------------------------------
        JPanel gridDiaHorario = new JPanel(new GridLayout(1, 2, 8, 0));
        gridDiaHorario.setOpaque(false);
        gridDiaHorario.setAlignmentX(Component.LEFT_ALIGNMENT);

        PanelRedondeado cuadritoDia = crearCuadritoDato(
                IconosVectoriales.crearIconoCalendario(PaletaMarina.CELESTE_BRILLANTE, 13),
                "DÍA",
                clase.getFecha(),
                null);

        PanelRedondeado cuadritoHorario = crearCuadritoDato(
                IconosVectoriales.crearIconoReloj(PaletaMarina.CELESTE_BRILLANTE, 13),
                "HORARIO",
                clase.getHoraInicio() + " - " + clase.getHoraFin(),
                clase.calcularDuracionHoras() + "h");

        gridDiaHorario.add(cuadritoDia);
        gridDiaHorario.add(cuadritoHorario);

        tarjeta.add(gridDiaHorario);
        tarjeta.add(Box.createVerticalStrut(8));

        // ---------------------------------------------------------------------
        // BLOQUE 2 (2 CUADRITOS): CAPÍTULO  |  TEMA
        // ---------------------------------------------------------------------
        String rawTemaCap = clase.getTemaCapitulo() == null ? "" : clase.getTemaCapitulo().trim();
        String textoCapitulo = "General";
        String textoTema = rawTemaCap;
        int idxDosPuntos = rawTemaCap.indexOf(':');
        if (idxDosPuntos > 0 && idxDosPuntos < rawTemaCap.length() - 1) {
            textoCapitulo = rawTemaCap.substring(0, idxDosPuntos).trim();
            textoTema = rawTemaCap.substring(idxDosPuntos + 1).trim();
        }

        JPanel gridCapTema = new JPanel(new BorderLayout(8, 0));
        gridCapTema.setOpaque(false);
        gridCapTema.setAlignmentX(Component.LEFT_ALIGNMENT);

        PanelRedondeado cuadritoCap = crearCuadritoDato(
                IconosVectoriales.crearIconoCapitulo(PaletaMarina.CELESTE_SUAVE, 13),
                "CAPÍTULO",
                textoCapitulo,
                null);
        cuadritoCap.setPreferredSize(new Dimension(112, 54));

        PanelRedondeado cuadritoTema = crearCuadritoDato(
                IconosVectoriales.crearIconoTema(PaletaMarina.TEXTO_BLANCO, 13),
                "TEMA",
                textoTema,
                null);

        gridCapTema.add(cuadritoCap, BorderLayout.WEST);
        gridCapTema.add(cuadritoTema, BorderLayout.CENTER);

        tarjeta.add(gridCapTema);
        tarjeta.add(Box.createVerticalStrut(8));

        // ---------------------------------------------------------------------
        // BLOQUE 3 (CUADRITO): GUÍA Y EJERCICIOS INTERACTIVOS
        // ---------------------------------------------------------------------
        PanelRedondeado cuadritoGuia = new PanelRedondeado(
                14,
                PaletaMarina.AZUL_MAR_TARJETA,
                PaletaMarina.BORDE_MARINO);
        cuadritoGuia.setLayout(new BoxLayout(cuadritoGuia, BoxLayout.Y_AXIS));
        cuadritoGuia.setBorder(BorderFactory.createEmptyBorder(9, 11, 9, 11));
        cuadritoGuia.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerGuia = new JPanel(new BorderLayout());
        headerGuia.setOpaque(false);
        headerGuia.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTituloGuia = new JLabel(" GUÍA / EJERCICIOS");
        lblTituloGuia.setIcon(IconosVectoriales.crearIconoGuia(PaletaMarina.CELESTE_BRILLANTE, 13));
        lblTituloGuia.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        lblTituloGuia.setFont(PaletaMarina.FUENTE_MICRO_CAPS);

        int totalEj = clase.getListaEjercicios().size();
        int hechosEj = 0;
        for (EjercicioPlanificado ep : clase.getListaEjercicios()) {
            if (ep.isCompletado()) {
                hechosEj++;
            }
        }
        JLabel lblConteoGuia = new JLabel(totalEj == 0 ? "Sin guía" : (hechosEj + "/" + totalEj + " hechos"));
        lblConteoGuia.setForeground(PaletaMarina.CELESTE_SUAVE);
        lblConteoGuia.setFont(PaletaMarina.FUENTE_MICRO_CAPS);

        headerGuia.add(lblTituloGuia, BorderLayout.WEST);
        headerGuia.add(lblConteoGuia, BorderLayout.EAST);
        cuadritoGuia.add(headerGuia);

        if (totalEj == 0) {
            cuadritoGuia.add(Box.createVerticalStrut(4));
            JLabel sinEj = new JLabel("No hay ejercicios asignados para esta sesión");
            sinEj.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
            sinEj.setFont(PaletaMarina.FUENTE_PEQUENA);
            sinEj.setAlignmentX(Component.LEFT_ALIGNMENT);
            cuadritoGuia.add(sinEj);
        } else {
            cuadritoGuia.add(Box.createVerticalStrut(6));
            for (final EjercicioPlanificado ej : clase.getListaEjercicios()) {
                JLabel lblEj = new JLabel("  " + ej.getDescripcion());
                lblEj.setIcon(IconosVectoriales.crearIconoCheckEjercicio(
                        ej.isCompletado(),
                        PaletaMarina.TURQUESA_EXITO,
                        PaletaMarina.CELESTE_PROFUNDO,
                        14));
                lblEj.setForeground(ej.isCompletado()
                        ? PaletaMarina.TEXTO_GRIS_AZUL
                        : PaletaMarina.TEXTO_BLANCO);
                lblEj.setFont(PaletaMarina.FUENTE_PEQUENA);
                lblEj.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lblEj.setAlignmentX(Component.LEFT_ALIGNMENT);
                lblEj.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ej.alternarCompletado();
                        renderizarTarjetasClases();
                    }
                });
                cuadritoGuia.add(lblEj);
                cuadritoGuia.add(Box.createVerticalStrut(4));
            }
        }

        tarjeta.add(cuadritoGuia);
        tarjeta.add(Box.createVerticalStrut(8));

        // ---------------------------------------------------------------------
        // BLOQUE 4 (CUADRITO): ESTUDIANTES CON CHIPS INDIVIDUALES
        // ---------------------------------------------------------------------
        PanelRedondeado cuadritoEstudiantes = new PanelRedondeado(
                14,
                PaletaMarina.AZUL_MAR_TARJETA,
                PaletaMarina.BORDE_MARINO);
        cuadritoEstudiantes.setLayout(new BoxLayout(cuadritoEstudiantes, BoxLayout.Y_AXIS));
        cuadritoEstudiantes.setBorder(BorderFactory.createEmptyBorder(9, 11, 9, 11));
        cuadritoEstudiantes.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerEst = new JPanel(new BorderLayout());
        headerEst.setOpaque(false);
        headerEst.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTituloEst = new JLabel(" ESTUDIANTES");
        lblTituloEst.setIcon(IconosVectoriales.crearIconoBirrete(PaletaMarina.CELESTE_BRILLANTE, 14));
        lblTituloEst.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        lblTituloEst.setFont(PaletaMarina.FUENTE_MICRO_CAPS);

        JLabel lblCupo = new JLabel(clase.getListaEstudiantes().size() + " / 10 cupos");
        lblCupo.setForeground(PaletaMarina.CELESTE_SUAVE);
        lblCupo.setFont(PaletaMarina.FUENTE_MICRO_CAPS);

        headerEst.add(lblTituloEst, BorderLayout.WEST);
        headerEst.add(lblCupo, BorderLayout.EAST);
        cuadritoEstudiantes.add(headerEst);
        cuadritoEstudiantes.add(Box.createVerticalStrut(6));

        JPanel chipsAlumnos = new JPanel(new WrapLayout(FlowLayout.LEFT, 5, 5));
        chipsAlumnos.setOpaque(false);
        chipsAlumnos.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (clase.getListaEstudiantes().isEmpty()) {
            JLabel sinAl = new JLabel("Sin alumnos asignados");
            sinAl.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
            sinAl.setFont(PaletaMarina.FUENTE_PEQUENA);
            chipsAlumnos.add(sinAl);
        } else {
            for (Estudiante est : clase.getListaEstudiantes()) {
                PanelRedondeado chipAl = new PanelRedondeado(
                        10,
                        PaletaMarina.AZUL_MAR_MEDIO,
                        PaletaMarina.BORDE_MARINO);
                chipAl.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 3));
                JLabel lblNombreAl = new JLabel(est.getNombreCompleto());
                lblNombreAl.setForeground(PaletaMarina.TEXTO_BLANCO);
                lblNombreAl.setFont(PaletaMarina.FUENTE_PEQUENA);
                chipAl.add(lblNombreAl);
                chipsAlumnos.add(chipAl);
            }
        }
        cuadritoEstudiantes.add(chipsAlumnos);

        tarjeta.add(cuadritoEstudiantes);
        tarjeta.add(Box.createVerticalStrut(10));

        // ---------------------------------------------------------------------
        // BLOQUE 5 (CUADRITO): COBRO Y ACCIONES RÁPIDAS
        // ---------------------------------------------------------------------
        PanelRedondeado cuadritoCobro = new PanelRedondeado(
                16,
                PaletaMarina.AZUL_MAR_MEDIO,
                PaletaMarina.CELESTE_PROFUNDO);
        cuadritoCobro.setLayout(new BorderLayout(8, 0));
        cuadritoCobro.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        cuadritoCobro.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel infoCobro = new JPanel();
        infoCobro.setLayout(new BoxLayout(infoCobro, BoxLayout.Y_AXIS));
        infoCobro.setOpaque(false);

        JLabel lblCobroTag = new JLabel(" COBRO TOTAL");
        lblCobroTag.setIcon(IconosVectoriales.crearIconoCobro(PaletaMarina.CELESTE_SUAVE, 12));
        lblCobroTag.setForeground(PaletaMarina.CELESTE_SUAVE);
        lblCobroTag.setFont(PaletaMarina.FUENTE_MICRO_CAPS);

        JLabel lblTotal = new JLabel(String.format("$%.2f", clase.calcularTotalCobro()));
        lblTotal.setForeground(clase.isPagada() ? PaletaMarina.TURQUESA_EXITO : PaletaMarina.TEXTO_BLANCO);
        lblTotal.setFont(PaletaMarina.satoshiBlack(19f));

        String detalleTarifa = String.format("$%.2f/h %s",
                clase.getTarifaPorHora(),
                clase.isCobroPorEstudiante() ? "• c/u" : "• grupo");
        JLabel lblSubTarifa = new JLabel(detalleTarifa);
        lblSubTarifa.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        lblSubTarifa.setFont(PaletaMarina.satoshiMedium(10f));

        infoCobro.add(lblCobroTag);
        infoCobro.add(lblTotal);
        infoCobro.add(lblSubTarifa);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 6));
        acciones.setOpaque(false);

        BotonModerno btnDictada = new BotonModerno(
                clase.isDictada() ? "Dictada ✓" : "Dictar",
                clase.isDictada() ? PaletaMarina.AZUL_MAR_TARJETA : PaletaMarina.AZUL_MAR_OSCURO,
                clase.isDictada() ? PaletaMarina.CELESTE_BRILLANTE : PaletaMarina.TEXTO_GRIS_AZUL,
                14);
        btnDictada.setColorBorde(clase.isDictada() ? PaletaMarina.CELESTE_BRILLANTE : PaletaMarina.BORDE_MARINO);
        btnDictada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.alternarClaseDictada(clase);
                actualizarTodo();
            }
        });

        BotonModerno btnCobrar = new BotonModerno(
                clase.isPagada() ? "Pagada ✓" : "Cobrar",
                clase.isPagada() ? PaletaMarina.TURQUESA_EXITO : PaletaMarina.TEXTO_BLANCO,
                PaletaMarina.NEGRO_FONDO,
                14);
        btnCobrar.setColorBorde(clase.isPagada() ? PaletaMarina.TURQUESA_EXITO : PaletaMarina.CELESTE_BRILLANTE);
        btnCobrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.alternarClasePagada(clase);
                actualizarTodo();
            }
        });

        BotonModerno btnBorrar = new BotonModerno(
                "",
                PaletaMarina.AZUL_MAR_OSCURO,
                PaletaMarina.CORAL_ELIMINAR,
                14);
        btnBorrar.setIcon(IconosVectoriales.crearIconoBasurero(PaletaMarina.CORAL_ELIMINAR, 14));
        btnBorrar.setBorder(BorderFactory.createEmptyBorder(7, 9, 7, 9));
        btnBorrar.setColorBorde(PaletaMarina.BORDE_MARINO);
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.eliminarClase(clase.getId());
                actualizarTodo();
            }
        });

        acciones.add(btnDictada);
        acciones.add(btnCobrar);
        acciones.add(btnBorrar);

        cuadritoCobro.add(infoCobro, BorderLayout.WEST);
        cuadritoCobro.add(acciones, BorderLayout.EAST);

        tarjeta.add(cuadritoCobro);
        return tarjeta;
    }

    private PanelRedondeado crearCuadritoDato(
            javax.swing.Icon icono,
            String etiquetaSuperior,
            String valorPrincipal,
            String badgeDerecho) {
        PanelRedondeado box = new PanelRedondeado(
                14,
                PaletaMarina.AZUL_MAR_TARJETA,
                PaletaMarina.BORDE_MARINO);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JPanel filaTop = new JPanel(new BorderLayout());
        filaTop.setOpaque(false);
        filaTop.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTag = new JLabel(" " + etiquetaSuperior);
        lblTag.setIcon(icono);
        lblTag.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        lblTag.setFont(PaletaMarina.FUENTE_MICRO_CAPS);
        filaTop.add(lblTag, BorderLayout.WEST);

        if (badgeDerecho != null) {
            JLabel lblBadge = new JLabel(badgeDerecho);
            lblBadge.setForeground(PaletaMarina.CELESTE_BRILLANTE);
            lblBadge.setFont(PaletaMarina.FUENTE_MICRO_CAPS);
            filaTop.add(lblBadge, BorderLayout.EAST);
        }

        JLabel lblValor = new JLabel(valorPrincipal);
        lblValor.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblValor.setFont(PaletaMarina.satoshiBold(12.5f));
        lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(filaTop);
        box.add(Box.createVerticalStrut(4));
        box.add(lblValor);
        return box;
    }

    // -------------------- PANTALLA 2: ALUMNOS --------------------
    private JPanel crearPantallaAlumnos() {
        JPanel pantalla = new JPanel(new BorderLayout());
        pantalla.setBackground(PaletaMarina.NEGRO_FONDO);

        listaTarjetasEstudiantes = new JPanel();
        listaTarjetasEstudiantes.setLayout(new BoxLayout(listaTarjetasEstudiantes, BoxLayout.Y_AXIS));
        listaTarjetasEstudiantes.setBackground(PaletaMarina.NEGRO_FONDO);
        listaTarjetasEstudiantes.setBorder(BorderFactory.createEmptyBorder(8, 14, 20, 14));

        JScrollPane scroll = new JScrollPane(listaTarjetasEstudiantes);
        PaletaMarina.estilizarScrollMovil(scroll);

        pantalla.add(scroll, BorderLayout.CENTER);
        return pantalla;
    }

    private void renderizarTarjetasEstudiantes() {
        listaTarjetasEstudiantes.removeAll();

        JLabel tituloSeccion = new JLabel("Mis Estudiantes (" + controlador.obtenerEstudiantes().size() + ")");
        tituloSeccion.setForeground(PaletaMarina.CELESTE_SUAVE);
        tituloSeccion.setFont(PaletaMarina.FUENTE_SUBTITULO);
        tituloSeccion.setAlignmentX(Component.LEFT_ALIGNMENT);
        listaTarjetasEstudiantes.add(tituloSeccion);
        listaTarjetasEstudiantes.add(Box.createVerticalStrut(12));

        for (final Estudiante est : controlador.obtenerEstudiantes()) {
            PanelRedondeado card = new PanelRedondeado(
                    20,
                    PaletaMarina.AZUL_MAR_OSCURO,
                    PaletaMarina.BORDE_MARINO);
            card.setDobleBordeLuminoso(true);
            card.setLayout(new BorderLayout(12, 0));
            card.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
            card.setMaximumSize(new Dimension(402, 82));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);

            PanelRedondeado avatar = new PanelRedondeado(
                    24,
                    PaletaMarina.CELESTE_BRILLANTE,
                    PaletaMarina.TEXTO_BLANCO);
            avatar.setPreferredSize(new Dimension(46, 46));
            avatar.setLayout(new BorderLayout());
            JLabel lblIni = new JLabel(est.obtenerIniciales(), SwingConstants.CENTER);
            lblIni.setForeground(PaletaMarina.NEGRO_FONDO);
            lblIni.setFont(PaletaMarina.FUENTE_SUBTITULO);
            avatar.add(lblIni, BorderLayout.CENTER);

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setOpaque(false);

            JLabel lblNombre = new JLabel(est.getNombreCompleto());
            lblNombre.setForeground(PaletaMarina.TEXTO_BLANCO);
            lblNombre.setFont(PaletaMarina.FUENTE_SUBTITULO);

            double horas = controlador.calcularHorasPorEstudiante(est.getId());
            JLabel lblDetalle = new JLabel(
                    " " + est.getUniversidad() + "  •  " + est.getNivelAcademico() + "  •  " + horas + "h");
            lblDetalle.setIcon(IconosVectoriales.crearIconoBirrete(PaletaMarina.CELESTE_SUAVE, 13));
            lblDetalle.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
            lblDetalle.setFont(PaletaMarina.FUENTE_PEQUENA);

            info.add(lblNombre);
            info.add(Box.createVerticalStrut(4));
            info.add(lblDetalle);

            BotonModerno btnEliminar = new BotonModerno(
                    "",
                    PaletaMarina.AZUL_MAR_TARJETA,
                    PaletaMarina.CORAL_ELIMINAR,
                    14);
            btnEliminar.setIcon(IconosVectoriales.crearIconoBasurero(PaletaMarina.CORAL_ELIMINAR, 14));
            btnEliminar.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            btnEliminar.setColorBorde(PaletaMarina.BORDE_MARINO);
            btnEliminar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controlador.eliminarEstudiante(est.getId());
                    renderizarTarjetasEstudiantes();
                }
            });

            card.add(avatar, BorderLayout.WEST);
            card.add(info, BorderLayout.CENTER);
            card.add(btnEliminar, BorderLayout.EAST);

            listaTarjetasEstudiantes.add(card);
            listaTarjetasEstudiantes.add(Box.createVerticalStrut(10));
        }

        listaTarjetasEstudiantes.revalidate();
        listaTarjetasEstudiantes.repaint();
    }

    // -------------------- PANTALLA 3: CAJA Y HORAS --------------------
    private JPanel crearPantallaCaja() {
        JPanel pantalla = new JPanel(new BorderLayout());
        pantalla.setBackground(PaletaMarina.NEGRO_FONDO);

        contenedorCaja = new JPanel();
        contenedorCaja.setLayout(new BoxLayout(contenedorCaja, BoxLayout.Y_AXIS));
        contenedorCaja.setBackground(PaletaMarina.NEGRO_FONDO);
        contenedorCaja.setBorder(BorderFactory.createEmptyBorder(8, 14, 20, 14));

        JScrollPane scroll = new JScrollPane(contenedorCaja);
        PaletaMarina.estilizarScrollMovil(scroll);

        pantalla.add(scroll, BorderLayout.CENTER);
        return pantalla;
    }

    private void renderizarPantallaCaja() {
        contenedorCaja.removeAll();

        // Tarjeta Hero esmerilada estilo Byte
        PanelRedondeado tarjetaHero = new PanelRedondeado(
                24,
                PaletaMarina.AZUL_MAR_MEDIO,
                PaletaMarina.AZUL_MAR_TARJETA,
                PaletaMarina.CELESTE_PROFUNDO);
        tarjetaHero.setLayout(new BoxLayout(tarjetaHero, BoxLayout.Y_AXIS));
        tarjetaHero.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        tarjetaHero.setMaximumSize(new Dimension(402, 175));
        tarjetaHero.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filaSupHero = new JPanel(new BorderLayout());
        filaSupHero.setOpaque(false);
        filaSupHero.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTituloHero = new JLabel("SALDO POR COBRAR");
        lblTituloHero.setForeground(PaletaMarina.CELESTE_SUAVE);
        lblTituloHero.setFont(PaletaMarina.FUENTE_MICRO_CAPS);

        filaSupHero.add(lblTituloHero, BorderLayout.WEST);
        filaSupHero.add(new LogoApp(30), BorderLayout.EAST);

        JLabel lblMontoPendiente = new JLabel(
                String.format("$%.2f", controlador.calcularTotalPorCobrar()));
        lblMontoPendiente.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblMontoPendiente.setFont(PaletaMarina.FUENTE_NUMERO_GRANDE);
        lblMontoPendiente.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filaMiniKpis = new JPanel(new GridLayout(1, 2, 12, 0));
        filaMiniKpis.setOpaque(false);
        filaMiniKpis.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblCobrado = new JLabel(
                String.format("● Cobrado: $%.2f", controlador.calcularTotalCobrado()));
        lblCobrado.setForeground(PaletaMarina.TURQUESA_EXITO);
        lblCobrado.setFont(PaletaMarina.FUENTE_NEGRITA);

        JLabel lblHoras = new JLabel(" " + controlador.calcularTotalHoras() + " horas");
        lblHoras.setIcon(IconosVectoriales.crearIconoReloj(PaletaMarina.TEXTO_BLANCO, 13));
        lblHoras.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblHoras.setFont(PaletaMarina.FUENTE_NEGRITA);

        filaMiniKpis.add(lblCobrado);
        filaMiniKpis.add(lblHoras);

        tarjetaHero.add(filaSupHero);
        tarjetaHero.add(Box.createVerticalStrut(2));
        tarjetaHero.add(lblMontoPendiente);
        tarjetaHero.add(Box.createVerticalStrut(14));
        tarjetaHero.add(filaMiniKpis);

        contenedorCaja.add(tarjetaHero);
        contenedorCaja.add(Box.createVerticalStrut(18));

        JLabel lblHistorial = new JLabel("Desglose por sesión (toca el monto para cobrar)");
        lblHistorial.setForeground(PaletaMarina.CELESTE_SUAVE);
        lblHistorial.setFont(PaletaMarina.FUENTE_SUBTITULO);
        lblHistorial.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenedorCaja.add(lblHistorial);
        contenedorCaja.add(Box.createVerticalStrut(10));

        for (final ClaseTutoria c : controlador.obtenerClases("Todas")) {
            PanelRedondeado item = new PanelRedondeado(
                    18,
                    PaletaMarina.AZUL_MAR_OSCURO,
                    PaletaMarina.BORDE_MARINO);
            item.setLayout(new BorderLayout(10, 0));
            item.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
            item.setMaximumSize(new Dimension(402, 74));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel izq = new JPanel();
            izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
            izq.setOpaque(false);

            JLabel t1 = new JLabel(" " + c.getMateria() + " • " + c.getTemaCapitulo());
            t1.setIcon(IconosVectoriales.crearIconoMateria(c.getMateria(), PaletaMarina.CELESTE_BRILLANTE, 14));
            t1.setForeground(PaletaMarina.TEXTO_BLANCO);
            t1.setFont(PaletaMarina.FUENTE_NEGRITA);

            String modo = c.isCobroPorEstudiante() ? "/alumno" : "/h";
            JLabel t2 = new JLabel(
                    c.getFecha() + " • " + c.calcularDuracionHoras() + "h × $"
                            + String.format("%.2f", c.getTarifaPorHora()) + modo);
            t2.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
            t2.setFont(PaletaMarina.FUENTE_PEQUENA);

            izq.add(t1);
            izq.add(Box.createVerticalStrut(4));
            izq.add(t2);

            BotonModerno pillMonto = new BotonModerno(
                    String.format("$%.2f", c.calcularTotalCobro()),
                    c.isPagada() ? PaletaMarina.TURQUESA_EXITO : PaletaMarina.CELESTE_BRILLANTE,
                    PaletaMarina.NEGRO_FONDO,
                    16);
            pillMonto.setColorBorde(c.isPagada() ? PaletaMarina.TURQUESA_EXITO : PaletaMarina.TEXTO_BLANCO);
            pillMonto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controlador.alternarClasePagada(c);
                    actualizarTodo();
                }
            });

            item.add(izq, BorderLayout.CENTER);
            item.add(pillMonto, BorderLayout.EAST);

            contenedorCaja.add(item);
            contenedorCaja.add(Box.createVerticalStrut(8));
        }

        contenedorCaja.revalidate();
        contenedorCaja.repaint();
    }

    // =========================================================================
    // BARRA DE NAVEGACIÓN INFERIOR
    // =========================================================================
    private void construirBarraNavegacionInferior() {
        JPanel contenedorInferior = new JPanel(new BorderLayout());
        contenedorInferior.setBackground(PaletaMarina.AZUL_MAR_OSCURO);
        contenedorInferior.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, PaletaMarina.BORDE_MARINO));

        JPanel navBar = new JPanel(new GridLayout(1, 3, 10, 0));
        navBar.setBackground(PaletaMarina.AZUL_MAR_OSCURO);
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 14, 6, 14));

        lblTabAgenda = new JLabel("Agenda", SwingConstants.CENTER);
        lblTabAlumnos = new JLabel("Alumnos", SwingConstants.CENTER);
        lblTabCaja = new JLabel("Caja", SwingConstants.CENTER);

        tabAgenda = crearItemNavegacion("AGENDA", lblTabAgenda);
        tabAlumnos = crearItemNavegacion("ALUMNOS", lblTabAlumnos);
        tabCaja = crearItemNavegacion("CAJA", lblTabCaja);

        navBar.add(tabAgenda);
        navBar.add(tabAlumnos);
        navBar.add(tabCaja);

        JPanel homeIndicator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PaletaMarina.CELESTE_PROFUNDO);
                int w = 120;
                g2.fillRoundRect((getWidth() - w) / 2, 4, w, 4, 4, 4);
                g2.dispose();
            }
        };
        homeIndicator.setBackground(PaletaMarina.AZUL_MAR_OSCURO);
        homeIndicator.setPreferredSize(new Dimension(434, 14));

        contenedorInferior.add(navBar, BorderLayout.CENTER);
        contenedorInferior.add(homeIndicator, BorderLayout.SOUTH);

        add(contenedorInferior, BorderLayout.SOUTH);
        actualizarEstiloNavegacion();
    }

    private PanelRedondeado crearItemNavegacion(final String clavePantalla, JLabel etiqueta) {
        PanelRedondeado pill = new PanelRedondeado(18, PaletaMarina.AZUL_MAR_OSCURO, null);
        pill.setLayout(new BorderLayout());
        pill.setPreferredSize(new Dimension(112, 46));
        pill.setCursor(new Cursor(Cursor.HAND_CURSOR));

        etiqueta.setFont(PaletaMarina.FUENTE_NEGRITA);
        etiqueta.setVerticalTextPosition(SwingConstants.BOTTOM);
        etiqueta.setHorizontalTextPosition(SwingConstants.CENTER);
        etiqueta.setIconTextGap(3);
        pill.add(etiqueta, BorderLayout.CENTER);

        pill.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pestanaActiva = clavePantalla;
                cardLayoutPantallas.show(contenedorPantallas, clavePantalla);
                actualizarEstiloNavegacion();
            }
        });
        return pill;
    }

    private void actualizarEstiloNavegacion() {
        aplicarEstadoTab(tabAgenda, lblTabAgenda, "AGENDA", pestanaActiva.equals("AGENDA"));
        aplicarEstadoTab(tabAlumnos, lblTabAlumnos, "ALUMNOS", pestanaActiva.equals("ALUMNOS"));
        aplicarEstadoTab(tabCaja, lblTabCaja, "CAJA", pestanaActiva.equals("CAJA"));

        if (pestanaActiva.equals("AGENDA")) {
            botonAccionRapida.setVisible(true);
            botonAccionRapida.setText("+ Clase");
        } else if (pestanaActiva.equals("ALUMNOS")) {
            botonAccionRapida.setVisible(true);
            botonAccionRapida.setText("+ Alumno");
        } else {
            botonAccionRapida.setVisible(false);
        }
    }

    private void aplicarEstadoTab(PanelRedondeado tab, JLabel label, String tipoIcono, boolean activo) {
        Color colorIcono = activo ? PaletaMarina.TEXTO_BLANCO : PaletaMarina.TEXTO_GRIS_AZUL;
        label.setIcon(IconosVectoriales.crearIconoNavegacion(tipoIcono, colorIcono, 18));

        if (activo) {
            tab.setColorFondo(PaletaMarina.AZUL_MAR_MEDIO);
            tab.setColorBorde(PaletaMarina.CELESTE_PROFUNDO);
            label.setForeground(PaletaMarina.TEXTO_BLANCO);
        } else {
            tab.setColorFondo(PaletaMarina.AZUL_MAR_OSCURO);
            tab.setColorBorde(null);
            label.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        }
    }

    private void ejecutarAccionPrincipal() {
        if (pestanaActiva.equals("AGENDA")) {
            DialogoNuevaClase dialogo = new DialogoNuevaClase(this, controlador);
            dialogo.setVisible(true);
            if (dialogo.isClaseGuardada()) {
                actualizarTodo();
            }
        } else if (pestanaActiva.equals("ALUMNOS")) {
            DialogoNuevoEstudiante dialogo = new DialogoNuevoEstudiante(this, controlador);
            dialogo.setVisible(true);
            if (dialogo.isGuardado()) {
                actualizarTodo();
            }
        }
    }

    private void actualizarTodo() {
        renderizarTarjetasClases();
        renderizarTarjetasEstudiantes();
        renderizarPantallaCaja();
    }

    // Layout auxiliar para que los chips de estudiantes salten de línea automáticamente
    private static class WrapLayout extends FlowLayout {
        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension minimum = layoutSize(target, false);
            minimum.width -= (getHgap() + 1);
            return minimum;
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                Container container = target;
                while (container.getSize().width == 0 && container.getParent() != null) {
                    container = container.getParent();
                }
                targetWidth = container.getSize().width;
                if (targetWidth == 0) {
                    targetWidth = 340;
                }

                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
                int maxWidth = targetWidth - horizontalInsetsAndGap;

                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0;
                int rowHeight = 0;

                int nmembers = target.getComponentCount();
                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                        if (rowWidth + d.width > maxWidth && rowWidth > 0) {
                            addRow(dim, rowWidth, rowHeight);
                            rowWidth = 0;
                            rowHeight = 0;
                        }
                        if (rowWidth != 0) {
                            rowWidth += hgap;
                        }
                        rowWidth += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }
                addRow(dim, rowWidth, rowHeight);

                dim.width += horizontalInsetsAndGap;
                dim.height += insets.top + insets.bottom + vgap * 2;

                Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
                if (scrollPane != null && target.isValid()) {
                    dim.width -= (hgap + 1);
                }
                return dim;
            }
        }

        private void addRow(Dimension dim, int rowWidth, int rowHeight) {
            dim.width = Math.max(dim.width, rowWidth);
            if (dim.height > 0) {
                dim.height += getVgap();
            }
            dim.height += rowHeight;
        }
    }
}
