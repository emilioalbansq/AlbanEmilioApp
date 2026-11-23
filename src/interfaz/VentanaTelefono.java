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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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

public class VentanaTelefono extends JFrame {
    private ControladorTutorias controlador;
    private String materiaSeleccionada;
    private String pestanaActiva;

    private CardLayout cardLayoutPantallas;
    private JPanel contenedorPantallas;

    // Contenedores dinámicos
    private JPanel listaTarjetasClases;
    private List<BotonModerno> botonesFiltroMateria;
    private JPanel listaTarjetasEstudiantes;
    private JPanel contenedorCaja;

    // Botón flotante superior según pestaña
    private BotonModerno botonAccionRapida;

    // Pestañas de la barra inferior
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

        setTitle("AulEmi • Mobile");
        setSize(435, 845);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Marco exterior estilo carcasa de teléfono
        JPanel carcasaTelefono = new JPanel(new BorderLayout());
        carcasaTelefono.setBackground(PaletaMarina.NEGRO_FONDO);
        carcasaTelefono.setBorder(BorderFactory.createLineBorder(PaletaMarina.BORDE_MARINO, 2));
        setContentPane(carcasaTelefono);

        construirBarraSuperiorTelefono();
        construirPantallasCentrales();
        construirBarraNavegacionInferior();

        actualizarTodo();
    }

    // =========================================================================
    // ENCABEZADO MÓVIL + STATUS BAR
    // =========================================================================
    private void construirBarraSuperiorTelefono() {
        JPanel bloqueSuperior = new JPanel();
        bloqueSuperior.setLayout(new BoxLayout(bloqueSuperior, BoxLayout.Y_AXIS));
        bloqueSuperior.setBackground(PaletaMarina.NEGRO_FONDO);

        // Barra de estado simulada del celular (hora, señal, batería en celeste)
        JPanel statusBar = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int xBase = getWidth() - 68;
                int yBase = 11;

                // Barras de señal
                g2.setColor(PaletaMarina.CELESTE_BRILLANTE);
                g2.fillRoundRect(xBase, yBase + 6, 3, 4, 1, 1);
                g2.fillRoundRect(xBase + 5, yBase + 4, 3, 6, 1, 1);
                g2.fillRoundRect(xBase + 10, yBase + 2, 3, 8, 1, 1);
                g2.fillRoundRect(xBase + 15, yBase, 3, 10, 1, 1);

                // Icono batería
                g2.setColor(PaletaMarina.CELESTE_SUAVE);
                g2.drawRoundRect(xBase + 26, yBase + 1, 20, 9, 3, 3);
                g2.fillRect(xBase + 47, yBase + 3, 2, 5);
                g2.setColor(PaletaMarina.CELESTE_BRILLANTE);
                g2.fillRoundRect(xBase + 28, yBase + 3, 15, 5, 2, 2);
                g2.dispose();
            }
        };
        statusBar.setBackground(PaletaMarina.NEGRO_FONDO);
        statusBar.setPreferredSize(new Dimension(430, 28));
        statusBar.setMaximumSize(new Dimension(430, 28));
        statusBar.setBorder(BorderFactory.createEmptyBorder(4, 20, 0, 20));

        JLabel lblHoraCelular = new JLabel("23:30  •  AulEmi");
        lblHoraCelular.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        lblHoraCelular.setFont(PaletaMarina.FUENTE_PEQUENA);
        statusBar.add(lblHoraCelular, BorderLayout.WEST);

        // AppBar moderno con logotipo en celeste y botón rápido
        JPanel appBar = new JPanel(new BorderLayout());
        appBar.setBackground(PaletaMarina.NEGRO_FONDO);
        appBar.setBorder(BorderFactory.createEmptyBorder(8, 18, 10, 18));
        appBar.setMaximumSize(new Dimension(430, 62));

        JPanel tituloConLogo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tituloConLogo.setBackground(PaletaMarina.NEGRO_FONDO);

        PanelRedondeado insigniaLogo = new PanelRedondeado(
                14,
                PaletaMarina.AZUL_MAR_TARJETA,
                PaletaMarina.CELESTE_BRILLANTE);
        insigniaLogo.setPreferredSize(new Dimension(38, 38));
        insigniaLogo.setLayout(new BorderLayout());
        JLabel simboloMath = new JLabel("∑", SwingConstants.CENTER);
        simboloMath.setForeground(PaletaMarina.CELESTE_BRILLANTE);
        simboloMath.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 19));
        insigniaLogo.add(simboloMath, BorderLayout.CENTER);

        JPanel textosTitulo = new JPanel();
        textosTitulo.setLayout(new BoxLayout(textosTitulo, BoxLayout.Y_AXIS));
        textosTitulo.setBackground(PaletaMarina.NEGRO_FONDO);

        JLabel lblApp = new JLabel("AulEmi");
        lblApp.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblApp.setFont(PaletaMarina.FUENTE_APP_TITULO);

        JLabel lblSub = new JLabel("Tutorías • Emilio Albán");
        lblSub.setForeground(PaletaMarina.CELESTE_SUAVE);
        lblSub.setFont(PaletaMarina.FUENTE_PEQUENA);

        textosTitulo.add(lblApp);
        textosTitulo.add(lblSub);

        tituloConLogo.add(insigniaLogo);
        tituloConLogo.add(textosTitulo);

        botonAccionRapida = new BotonModerno(
                "+ Clase",
                PaletaMarina.CELESTE_PROFUNDO,
                PaletaMarina.CELESTE_BRILLANTE,
                Color.BLACK,
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
    // PANTALLAS CENTRALES (CARDLAYOUT)
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

    // -------------------- PANTALLA 1: AGENDA --------------------
    private JPanel crearPantallaAgenda() {
        JPanel pantalla = new JPanel(new BorderLayout(0, 8));
        pantalla.setBackground(PaletaMarina.NEGRO_FONDO);

        // Chips horizontales para filtrar materias
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
                    activo ? Color.BLACK : PaletaMarina.TEXTO_GRIS_AZUL,
                    16);
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

        JScrollPane scrollChips = new JScrollPane(
                barraFiltros,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollChips.setBorder(null);
        scrollChips.setBackground(PaletaMarina.NEGRO_FONDO);
        scrollChips.getViewport().setBackground(PaletaMarina.NEGRO_FONDO);
        scrollChips.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 3));

        // Feed vertical de tarjetas de tutorías
        listaTarjetasClases = new JPanel();
        listaTarjetasClases.setLayout(new BoxLayout(listaTarjetasClases, BoxLayout.Y_AXIS));
        listaTarjetasClases.setBackground(PaletaMarina.NEGRO_FONDO);
        listaTarjetasClases.setBorder(BorderFactory.createEmptyBorder(4, 16, 20, 16));

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
                    activo ? Color.BLACK : PaletaMarina.TEXTO_GRIS_AZUL);
        }
    }

    private void renderizarTarjetasClases() {
        listaTarjetasClases.removeAll();
        List<ClaseTutoria> clases = controlador.obtenerClases(materiaSeleccionada);

        if (clases.isEmpty()) {
            JLabel vacio = new JLabel("No hay tutorías en esta materia", SwingConstants.CENTER);
            vacio.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
            vacio.setFont(PaletaMarina.FUENTE_CUERPO);
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            listaTarjetasClases.add(Box.createVerticalStrut(60));
            listaTarjetasClases.add(vacio);
        } else {
            for (ClaseTutoria clase : clases) {
                listaTarjetasClases.add(crearTarjetaClase(clase));
                listaTarjetasClases.add(Box.createVerticalStrut(12));
            }
        }

        listaTarjetasClases.revalidate();
        listaTarjetasClases.repaint();
    }

    private PanelRedondeado crearTarjetaClase(final ClaseTutoria clase) {
        PanelRedondeado tarjeta = new PanelRedondeado(
                20,
                PaletaMarina.AZUL_MAR_OSCURO,
                PaletaMarina.BORDE_MARINO);
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        tarjeta.setMaximumSize(new Dimension(395, 420));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Fila 1: Badge de materia + Estado
        JPanel filaSuperior = new JPanel(new BorderLayout());
        filaSuperior.setOpaque(false);
        filaSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);

        PanelRedondeado pillMateria = new PanelRedondeado(12, PaletaMarina.AZUL_MAR_MEDIO, null);
        pillMateria.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 3));
        JLabel lblMat = new JLabel(clase.getMateria());
        lblMat.setForeground(PaletaMarina.CELESTE_BRILLANTE);
        lblMat.setFont(PaletaMarina.FUENTE_NEGRITA);
        pillMateria.add(lblMat);

        Color colorEstado = clase.isPagada()
                ? PaletaMarina.TURQUESA_EXITO
                : (clase.isDictada() ? PaletaMarina.CELESTE_BRILLANTE : PaletaMarina.AMBAR_AVISO);

        JLabel lblEstado = new JLabel("● " + clase.obtenerEstadoTexto());
        lblEstado.setForeground(colorEstado);
        lblEstado.setFont(PaletaMarina.FUENTE_NEGRITA);

        filaSuperior.add(pillMateria, BorderLayout.WEST);
        filaSuperior.add(lblEstado, BorderLayout.EAST);

        // Fila 2: Fecha y bloque horario
        JLabel lblHorario = new JLabel(
                clase.getFecha() + "  •  " + clase.getHoraInicio() + " - " + clase.getHoraFin()
                        + " (" + clase.calcularDuracionHoras() + "h)");
        lblHorario.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        lblHorario.setFont(PaletaMarina.FUENTE_PEQUENA);
        lblHorario.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Fila 3: Tema / Capítulo principal
        JLabel lblTema = new JLabel(clase.getTemaCapitulo());
        lblTema.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblTema.setFont(PaletaMarina.FUENTE_SUBTITULO);
        lblTema.setAlignmentX(Component.LEFT_ALIGNMENT);

        tarjeta.add(filaSuperior);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblHorario);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(lblTema);

        // Checklist interactivo de ejercicios
        if (!clase.getListaEjercicios().isEmpty()) {
            tarjeta.add(Box.createVerticalStrut(8));
            for (final EjercicioPlanificado ej : clase.getListaEjercicios()) {
                String iconoCheck = ej.isCompletado() ? "☑  " : "☐  ";
                JLabel lblEj = new JLabel(iconoCheck + ej.getDescripcion());
                lblEj.setForeground(ej.isCompletado()
                        ? PaletaMarina.TEXTO_GRIS_AZUL
                        : PaletaMarina.CELESTE_SUAVE);
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
                tarjeta.add(lblEj);
                tarjeta.add(Box.createVerticalStrut(3));
            }
        }

        tarjeta.add(Box.createVerticalStrut(8));

        // Fila de estudiantes asignados
        JLabel lblAlumnos = new JLabel(
                "Alumnos (" + clase.getListaEstudiantes().size() + "/10): " + clase.obtenerNombresEstudiantes());
        lblAlumnos.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        lblAlumnos.setFont(PaletaMarina.FUENTE_PEQUENA);
        lblAlumnos.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjeta.add(lblAlumnos);

        tarjeta.add(Box.createVerticalStrut(10));

        // Fila inferior: Precio Total + Botones rápidos
        JPanel filaInferior = new JPanel(new BorderLayout());
        filaInferior.setOpaque(false);
        filaInferior.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTotal = new JLabel(String.format("$%.2f", clase.calcularTotalCobro()));
        lblTotal.setForeground(PaletaMarina.TURQUESA_EXITO);
        lblTotal.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 17));

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        acciones.setOpaque(false);

        BotonModerno btnDictada = new BotonModerno(
                clase.isDictada() ? "Dictada ✓" : "Dictar",
                clase.isDictada() ? PaletaMarina.AZUL_MAR_MEDIO : PaletaMarina.AZUL_MAR_TARJETA,
                clase.isDictada() ? PaletaMarina.CELESTE_BRILLANTE : PaletaMarina.TEXTO_GRIS_AZUL,
                12);
        btnDictada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.alternarClaseDictada(clase);
                actualizarTodo();
            }
        });

        BotonModerno btnCobrar = new BotonModerno(
                clase.isPagada() ? "Pagada ✓" : "Cobrar",
                clase.isPagada() ? PaletaMarina.TURQUESA_EXITO : PaletaMarina.CELESTE_PROFUNDO,
                clase.isPagada() ? Color.BLACK : PaletaMarina.TEXTO_BLANCO,
                12);
        btnCobrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.alternarClasePagada(clase);
                actualizarTodo();
            }
        });

        BotonModerno btnBorrar = new BotonModerno(
                "✕",
                PaletaMarina.AZUL_MAR_TARJETA,
                PaletaMarina.CORAL_ELIMINAR,
                12);
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

        filaInferior.add(lblTotal, BorderLayout.WEST);
        filaInferior.add(acciones, BorderLayout.EAST);

        tarjeta.add(filaInferior);
        return tarjeta;
    }

    // -------------------- PANTALLA 2: ALUMNOS --------------------
    private JPanel crearPantallaAlumnos() {
        JPanel pantalla = new JPanel(new BorderLayout());
        pantalla.setBackground(PaletaMarina.NEGRO_FONDO);

        listaTarjetasEstudiantes = new JPanel();
        listaTarjetasEstudiantes.setLayout(new BoxLayout(listaTarjetasEstudiantes, BoxLayout.Y_AXIS));
        listaTarjetasEstudiantes.setBackground(PaletaMarina.NEGRO_FONDO);
        listaTarjetasEstudiantes.setBorder(BorderFactory.createEmptyBorder(8, 16, 20, 16));

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
                    18,
                    PaletaMarina.AZUL_MAR_OSCURO,
                    PaletaMarina.BORDE_MARINO);
            card.setLayout(new BorderLayout(12, 0));
            card.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
            card.setMaximumSize(new Dimension(395, 76));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Avatar circular con iniciales
            PanelRedondeado avatar = new PanelRedondeado(
                    24,
                    PaletaMarina.AZUL_MAR_MEDIO,
                    PaletaMarina.CELESTE_BRILLANTE);
            avatar.setPreferredSize(new Dimension(46, 46));
            avatar.setLayout(new BorderLayout());
            JLabel lblIni = new JLabel(est.obtenerIniciales(), SwingConstants.CENTER);
            lblIni.setForeground(PaletaMarina.CELESTE_BRILLANTE);
            lblIni.setFont(PaletaMarina.FUENTE_SUBTITULO);
            avatar.add(lblIni, BorderLayout.CENTER);

            // Datos del alumno
            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setOpaque(false);

            JLabel lblNombre = new JLabel(est.getNombreCompleto());
            lblNombre.setForeground(PaletaMarina.TEXTO_BLANCO);
            lblNombre.setFont(PaletaMarina.FUENTE_SUBTITULO);

            double horas = controlador.calcularHorasPorEstudiante(est.getId());
            JLabel lblDetalle = new JLabel(
                    est.getUniversidad() + "  •  " + est.getNivelAcademico() + "  •  " + horas + "h");
            lblDetalle.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
            lblDetalle.setFont(PaletaMarina.FUENTE_PEQUENA);

            info.add(lblNombre);
            info.add(Box.createVerticalStrut(3));
            info.add(lblDetalle);

            BotonModerno btnEliminar = new BotonModerno(
                    "✕",
                    PaletaMarina.AZUL_MAR_TARJETA,
                    PaletaMarina.CORAL_ELIMINAR,
                    12);
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
        contenedorCaja.setBorder(BorderFactory.createEmptyBorder(8, 16, 20, 16));

        JScrollPane scroll = new JScrollPane(contenedorCaja);
        PaletaMarina.estilizarScrollMovil(scroll);

        pantalla.add(scroll, BorderLayout.CENTER);
        return pantalla;
    }

    private void renderizarPantallaCaja() {
        contenedorCaja.removeAll();

        // Tarjeta Hero con degradado océano -> celeste
        PanelRedondeado tarjetaHero = new PanelRedondeado(
                22,
                PaletaMarina.AZUL_MAR_MEDIO,
                PaletaMarina.CELESTE_PROFUNDO,
                PaletaMarina.CELESTE_BRILLANTE);
        tarjetaHero.setLayout(new BoxLayout(tarjetaHero, BoxLayout.Y_AXIS));
        tarjetaHero.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        tarjetaHero.setMaximumSize(new Dimension(395, 165));
        tarjetaHero.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTituloHero = new JLabel("SALDO POR COBRAR");
        lblTituloHero.setForeground(PaletaMarina.CELESTE_SUAVE);
        lblTituloHero.setFont(PaletaMarina.FUENTE_PEQUENA);
        lblTituloHero.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMontoPendiente = new JLabel(
                String.format("$%.2f", controlador.calcularTotalPorCobrar()));
        lblMontoPendiente.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblMontoPendiente.setFont(PaletaMarina.FUENTE_NUMERO_GRANDE);
        lblMontoPendiente.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filaMiniKpis = new JPanel(new GridLayout(1, 2, 12, 0));
        filaMiniKpis.setOpaque(false);
        filaMiniKpis.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblCobrado = new JLabel(
                String.format("Cobrado: $%.2f", controlador.calcularTotalCobrado()));
        lblCobrado.setForeground(PaletaMarina.TURQUESA_EXITO);
        lblCobrado.setFont(PaletaMarina.FUENTE_NEGRITA);

        JLabel lblHoras = new JLabel("Horas: " + controlador.calcularTotalHoras() + " h");
        lblHoras.setForeground(PaletaMarina.TEXTO_BLANCO);
        lblHoras.setFont(PaletaMarina.FUENTE_NEGRITA);

        filaMiniKpis.add(lblCobrado);
        filaMiniKpis.add(lblHoras);

        tarjetaHero.add(lblTituloHero);
        tarjetaHero.add(Box.createVerticalStrut(4));
        tarjetaHero.add(lblMontoPendiente);
        tarjetaHero.add(Box.createVerticalStrut(14));
        tarjetaHero.add(filaMiniKpis);

        contenedorCaja.add(tarjetaHero);
        contenedorCaja.add(Box.createVerticalStrut(18));

        JLabel lblHistorial = new JLabel("Desglose por sesión (toca para cobrar)");
        lblHistorial.setForeground(PaletaMarina.CELESTE_SUAVE);
        lblHistorial.setFont(PaletaMarina.FUENTE_SUBTITULO);
        lblHistorial.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenedorCaja.add(lblHistorial);
        contenedorCaja.add(Box.createVerticalStrut(10));

        for (final ClaseTutoria c : controlador.obtenerClases("Todas")) {
            PanelRedondeado item = new PanelRedondeado(
                    16,
                    PaletaMarina.AZUL_MAR_OSCURO,
                    PaletaMarina.BORDE_MARINO);
            item.setLayout(new BorderLayout(10, 0));
            item.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
            item.setMaximumSize(new Dimension(395, 68));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel izq = new JPanel();
            izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
            izq.setOpaque(false);

            JLabel t1 = new JLabel(c.getMateria() + " • " + c.getTemaCapitulo());
            t1.setForeground(PaletaMarina.TEXTO_BLANCO);
            t1.setFont(PaletaMarina.FUENTE_NEGRITA);

            String modo = c.isCobroPorEstudiante() ? "/alumno" : "/h";
            JLabel t2 = new JLabel(
                    c.getFecha() + " • " + c.calcularDuracionHoras() + "h × $"
                            + String.format("%.2f", c.getTarifaPorHora()) + modo);
            t2.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
            t2.setFont(PaletaMarina.FUENTE_PEQUENA);

            izq.add(t1);
            izq.add(Box.createVerticalStrut(3));
            izq.add(t2);

            BotonModerno pillMonto = new BotonModerno(
                    String.format("$%.2f", c.calcularTotalCobro()),
                    c.isPagada() ? PaletaMarina.TURQUESA_EXITO : PaletaMarina.AZUL_MAR_MEDIO,
                    c.isPagada() ? Color.BLACK : PaletaMarina.AMBAR_AVISO,
                    14);
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
    // BARRA DE NAVEGACIÓN INFERIOR (BOTTOM NAV BAR)
    // =========================================================================
    private void construirBarraNavegacionInferior() {
        JPanel navBar = new JPanel(new GridLayout(1, 3, 10, 0));
        navBar.setBackground(PaletaMarina.AZUL_MAR_OSCURO);
        navBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, PaletaMarina.BORDE_MARINO),
                BorderFactory.createEmptyBorder(10, 16, 12, 16)));

        lblTabAgenda = new JLabel("Agenda", SwingConstants.CENTER);
        lblTabAlumnos = new JLabel("Alumnos", SwingConstants.CENTER);
        lblTabCaja = new JLabel("Caja", SwingConstants.CENTER);

        tabAgenda = crearItemNavegacion("AGENDA", lblTabAgenda);
        tabAlumnos = crearItemNavegacion("ALUMNOS", lblTabAlumnos);
        tabCaja = crearItemNavegacion("CAJA", lblTabCaja);

        navBar.add(tabAgenda);
        navBar.add(tabAlumnos);
        navBar.add(tabCaja);

        add(navBar, BorderLayout.SOUTH);
        actualizarEstiloNavegacion();
    }

    private PanelRedondeado crearItemNavegacion(final String clavePantalla, JLabel etiqueta) {
        PanelRedondeado pill = new PanelRedondeado(16, PaletaMarina.AZUL_MAR_OSCURO, null);
        pill.setLayout(new BorderLayout());
        pill.setPreferredSize(new Dimension(110, 38));
        pill.setCursor(new Cursor(Cursor.HAND_CURSOR));

        etiqueta.setFont(PaletaMarina.FUENTE_NEGRITA);
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
        aplicarEstadoTab(tabAgenda, lblTabAgenda, pestanaActiva.equals("AGENDA"));
        aplicarEstadoTab(tabAlumnos, lblTabAlumnos, pestanaActiva.equals("ALUMNOS"));
        aplicarEstadoTab(tabCaja, lblTabCaja, pestanaActiva.equals("CAJA"));

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

    private void aplicarEstadoTab(PanelRedondeado tab, JLabel label, boolean activo) {
        if (activo) {
            tab.setColorFondo(PaletaMarina.AZUL_MAR_MEDIO);
            tab.setColorBorde(PaletaMarina.CELESTE_BRILLANTE);
            label.setForeground(PaletaMarina.CELESTE_BRILLANTE);
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
}
