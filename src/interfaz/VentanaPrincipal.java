package interfaz;

import controladores.ControladorTutorias;
import entidades.ClaseTutoria;
import entidades.EjercicioPlanificado;
import entidades.Estudiante;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame {
    private ControladorTutorias controlador;

    // Componentes Pestaña 1: Cronograma
    private JComboBox<String> comboFiltroMateria;
    private DefaultTableModel modeloTablaClases;
    private JTable tablaClases;
    private JTextArea areaDetalleClase;
    private List<ClaseTutoria> clasesMostradasActuales;

    // Componentes Pestaña 2: Estudiantes
    private JTextField campoNombreEstudiante;
    private JComboBox<String> comboUniversidad;
    private JComboBox<String> comboNivel;
    private DefaultTableModel modeloTablaEstudiantes;
    private JTable tablaEstudiantes;

    // Componentes Pestaña 3: Caja y Resumen
    private JLabel etiquetaTotalHoras;
    private JLabel etiquetaPorCobrar;
    private JLabel etiquetaCobrado;
    private DefaultTableModel modeloTablaCobros;
    private JTable tablaCobros;

    public VentanaPrincipal() {
        this.controlador = new ControladorTutorias();

        setTitle("AulEmi - Gestión de Tutorías Privadas");
        setSize(1120, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(PaletaOscura.FONDO_PRINCIPAL);
        setLayout(new BorderLayout());

        construirEncabezado();
        construirPestanas();
        refrescarTodaLaInterfaz();
    }

    private void construirEncabezado() {
        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(PaletaOscura.FONDO_TARJETA);
        barraSuperior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, PaletaOscura.BORDE_SUTIL),
                BorderFactory.createEmptyBorder(14, 22, 14, 22)));

        JLabel titulo = new JLabel("AulEmi");
        titulo.setForeground(PaletaOscura.TEXTO_PRINCIPAL);
        titulo.setFont(PaletaOscura.FUENTE_TITULO);

        JLabel subtitulo = new JLabel("Cronograma, Planificación de Temas y Control de Cobros");
        subtitulo.setForeground(PaletaOscura.TEXTO_SECUNDARIO);
        subtitulo.setFont(PaletaOscura.FUENTE_NORMAL);

        JPanel bloqueIzquierdo = new JPanel();
        bloqueIzquierdo.setLayout(new BoxLayout(bloqueIzquierdo, BoxLayout.Y_AXIS));
        bloqueIzquierdo.setBackground(PaletaOscura.FONDO_TARJETA);
        bloqueIzquierdo.add(titulo);
        bloqueIzquierdo.add(subtitulo);

        JLabel etiquetaTutor = new JLabel("Tutor: Emilio Albán  |  Cupo máx: 10 alumnos/clase");
        etiquetaTutor.setForeground(PaletaOscura.AZUL_ACENTO);
        etiquetaTutor.setFont(PaletaOscura.FUENTE_NEGRITA);

        barraSuperior.add(bloqueIzquierdo, BorderLayout.WEST);
        barraSuperior.add(etiquetaTutor, BorderLayout.EAST);

        add(barraSuperior, BorderLayout.NORTH);
    }

    private void construirPestanas() {
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setBackground(PaletaOscura.FONDO_TARJETA);
        pestanas.setForeground(PaletaOscura.TEXTO_PRINCIPAL);
        pestanas.setFont(PaletaOscura.FUENTE_NEGRITA);

        pestanas.addTab("  1. Cronograma y Clases  ", crearPanelCronograma());
        pestanas.addTab("  2. Estudiantes  ", crearPanelEstudiantes());
        pestanas.addTab("  3. Horas y Cobros  ", crearPanelCobros());

        add(pestanas, BorderLayout.CENTER);
    }

    // ==================== PESTAÑA 1: CRONOGRAMA ====================
    private JPanel crearPanelCronograma() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // Barra de acciones superior
        JPanel barraAcciones = new JPanel(new BorderLayout());
        barraAcciones.setBackground(PaletaOscura.FONDO_PRINCIPAL);

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelFiltro.setBackground(PaletaOscura.FONDO_PRINCIPAL);

        JLabel lblFiltro = new JLabel("Filtrar materia:");
        lblFiltro.setForeground(PaletaOscura.TEXTO_SECUNDARIO);
        lblFiltro.setFont(PaletaOscura.FUENTE_NEGRITA);

        String[] opcionesFiltro = new String[ControladorTutorias.MATERIAS_DISPONIBLES.length + 1];
        opcionesFiltro[0] = "Todas";
        for (int i = 0; i < ControladorTutorias.MATERIAS_DISPONIBLES.length; i++) {
            opcionesFiltro[i + 1] = ControladorTutorias.MATERIAS_DISPONIBLES[i];
        }

        comboFiltroMateria = new JComboBox<>(opcionesFiltro);
        PaletaOscura.estilizarComboBox(comboFiltroMateria);
        comboFiltroMateria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescarTablaClases();
            }
        });

        panelFiltro.add(lblFiltro);
        panelFiltro.add(comboFiltroMateria);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBotones.setBackground(PaletaOscura.FONDO_PRINCIPAL);

        JButton btnNuevaClase = PaletaOscura.crearBoton("+ Agendar Clase", PaletaOscura.AZUL_ACENTO);
        btnNuevaClase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoNuevaClase dialogo = new DialogoNuevaClase(VentanaPrincipal.this, controlador);
                dialogo.setVisible(true);
                if (dialogo.isClaseGuardada()) {
                    refrescarTodaLaInterfaz();
                }
            }
        });

        JButton btnDictada = PaletaOscura.crearBoton("Marcar Dictada", PaletaOscura.BORDE_SUTIL);
        btnDictada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClaseTutoria seleccionada = obtenerClaseSeleccionada();
                if (seleccionada != null) {
                    controlador.alternarClaseDictada(seleccionada);
                    refrescarTodaLaInterfaz();
                }
            }
        });

        JButton btnPagada = PaletaOscura.crearBoton("Marcar Pagada ($)", PaletaOscura.VERDE_EXITO);
        btnPagada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClaseTutoria seleccionada = obtenerClaseSeleccionada();
                if (seleccionada != null) {
                    controlador.alternarClasePagada(seleccionada);
                    refrescarTodaLaInterfaz();
                }
            }
        });

        JButton btnEliminar = PaletaOscura.crearBoton("Eliminar", PaletaOscura.ROJO_PELIGRO);
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClaseTutoria seleccionada = obtenerClaseSeleccionada();
                if (seleccionada != null) {
                    controlador.eliminarClase(seleccionada.getId());
                    refrescarTodaLaInterfaz();
                }
            }
        });

        panelBotones.add(btnNuevaClase);
        panelBotones.add(btnDictada);
        panelBotones.add(btnPagada);
        panelBotones.add(btnEliminar);

        barraAcciones.add(panelFiltro, BorderLayout.WEST);
        barraAcciones.add(panelBotones, BorderLayout.EAST);

        // Tabla de clases
        String[] columnas = {
            "Fecha", "Horario", "Horas", "Materia", "Tema / Capítulo", "Estudiantes", "Total ($)", "Estado"
        };
        modeloTablaClases = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClases = new JTable(modeloTablaClases);
        PaletaOscura.estilizarTabla(tablaClases);
        tablaClases.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                mostrarDetalleClaseSeleccionada();
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaClases);
        scrollTabla.getViewport().setBackground(PaletaOscura.FONDO_TARJETA);
        scrollTabla.setBorder(BorderFactory.createLineBorder(PaletaOscura.BORDE_SUTIL));

        // Panel inferior: Planificación de ejercicios de la clase seleccionada
        JPanel panelDetalle = new JPanel(new BorderLayout(8, 8));
        panelDetalle.setBackground(PaletaOscura.FONDO_TARJETA);
        panelDetalle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaOscura.BORDE_SUTIL),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        panelDetalle.setPreferredSize(new Dimension(0, 145));

        JLabel lblDetalle = new JLabel("Planificación de Temas y Ejercicios de la Clase Seleccionada:");
        lblDetalle.setForeground(PaletaOscura.AZUL_ACENTO);
        lblDetalle.setFont(PaletaOscura.FUENTE_NEGRITA);

        areaDetalleClase = new JTextArea("Selecciona una clase en la tabla para ver los ejercicios y estudiantes asignados.");
        areaDetalleClase.setEditable(false);
        areaDetalleClase.setBackground(PaletaOscura.FONDO_TARJETA);
        areaDetalleClase.setForeground(PaletaOscura.TEXTO_PRINCIPAL);
        areaDetalleClase.setFont(PaletaOscura.FUENTE_NORMAL);

        panelDetalle.add(lblDetalle, BorderLayout.NORTH);
        panelDetalle.add(new JScrollPane(areaDetalleClase), BorderLayout.CENTER);

        panel.add(barraAcciones, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);
        panel.add(panelDetalle, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== PESTAÑA 2: ESTUDIANTES ====================
    private JPanel crearPanelEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout(16, 0));
        panel.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // Formulario izquierdo
        JPanel tarjetaRegistro = new JPanel();
        tarjetaRegistro.setLayout(new BoxLayout(tarjetaRegistro, BoxLayout.Y_AXIS));
        tarjetaRegistro.setBackground(PaletaOscura.FONDO_TARJETA);
        tarjetaRegistro.setPreferredSize(new Dimension(330, 0));
        tarjetaRegistro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaOscura.BORDE_SUTIL),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel tituloForm = new JLabel("Registrar Estudiante");
        tituloForm.setForeground(PaletaOscura.TEXTO_PRINCIPAL);
        tituloForm.setFont(PaletaOscura.FUENTE_SUBTITULO);

        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setForeground(PaletaOscura.TEXTO_SECUNDARIO);
        campoNombreEstudiante = new JTextField();
        campoNombreEstudiante.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        PaletaOscura.estilizarCampoTexto(campoNombreEstudiante);

        JLabel lblUni = new JLabel("Universidad:");
        lblUni.setForeground(PaletaOscura.TEXTO_SECUNDARIO);
        String[] universidades = {"EPN", "ESPE", "PUCE", "UDLA", "Otra"};
        comboUniversidad = new JComboBox<>(universidades);
        comboUniversidad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        PaletaOscura.estilizarComboBox(comboUniversidad);

        JLabel lblNivel = new JLabel("Nivel o Semestre:");
        lblNivel.setForeground(PaletaOscura.TEXTO_SECUNDARIO);
        String[] niveles = {"Nivelación", "1er Semestre", "2do Semestre", "3er Semestre"};
        comboNivel = new JComboBox<>(niveles);
        comboNivel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        PaletaOscura.estilizarComboBox(comboNivel);

        JButton btnGuardarEst = PaletaOscura.crearBoton("Guardar Estudiante", PaletaOscura.AZUL_ACENTO);
        btnGuardarEst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String res = controlador.registrarEstudiante(
                        campoNombreEstudiante.getText(),
                        (String) comboUniversidad.getSelectedItem(),
                        (String) comboNivel.getSelectedItem());
                if (res.equals("OK")) {
                    campoNombreEstudiante.setText("");
                    refrescarTablaEstudiantes();
                } else {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, res);
                }
            }
        });

        tarjetaRegistro.add(tituloForm);
        tarjetaRegistro.add(crearEspacioVertical(16));
        tarjetaRegistro.add(lblNombre);
        tarjetaRegistro.add(crearEspacioVertical(4));
        tarjetaRegistro.add(campoNombreEstudiante);
        tarjetaRegistro.add(crearEspacioVertical(12));
        tarjetaRegistro.add(lblUni);
        tarjetaRegistro.add(crearEspacioVertical(4));
        tarjetaRegistro.add(comboUniversidad);
        tarjetaRegistro.add(crearEspacioVertical(12));
        tarjetaRegistro.add(lblNivel);
        tarjetaRegistro.add(crearEspacioVertical(4));
        tarjetaRegistro.add(comboNivel);
        tarjetaRegistro.add(crearEspacioVertical(20));
        tarjetaRegistro.add(btnGuardarEst);

        // Tabla derecha
        JPanel panelLista = new JPanel(new BorderLayout(0, 10));
        panelLista.setBackground(PaletaOscura.FONDO_PRINCIPAL);

        String[] columnas = {"ID", "Estudiante", "Universidad", "Nivel / Semestre", "Horas Agendadas"};
        modeloTablaEstudiantes = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstudiantes = new JTable(modeloTablaEstudiantes);
        PaletaOscura.estilizarTabla(tablaEstudiantes);

        JScrollPane scrollEst = new JScrollPane(tablaEstudiantes);
        scrollEst.getViewport().setBackground(PaletaOscura.FONDO_TARJETA);
        scrollEst.setBorder(BorderFactory.createLineBorder(PaletaOscura.BORDE_SUTIL));

        JButton btnEliminarEst = PaletaOscura.crearBoton("Eliminar Seleccionado", PaletaOscura.ROJO_PELIGRO);
        btnEliminarEst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaEstudiantes.getSelectedRow();
                if (fila >= 0) {
                    String id = (String) modeloTablaEstudiantes.getValueAt(fila, 0);
                    controlador.eliminarEstudiante(id);
                    refrescarTablaEstudiantes();
                }
            }
        });

        JPanel pieEst = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pieEst.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        pieEst.add(btnEliminarEst);

        panelLista.add(scrollEst, BorderLayout.CENTER);
        panelLista.add(pieEst, BorderLayout.SOUTH);

        panel.add(tarjetaRegistro, BorderLayout.WEST);
        panel.add(panelLista, BorderLayout.CENTER);

        return panel;
    }

    // ==================== PESTAÑA 3: HORAS Y COBROS ====================
    private JPanel crearPanelCobros() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // Tarjetas KPI superiores
        JPanel filaIndicadores = new JPanel(new GridLayout(1, 3, 14, 0));
        filaIndicadores.setBackground(PaletaOscura.FONDO_PRINCIPAL);

        etiquetaTotalHoras = new JLabel("0.0 h");
        etiquetaPorCobrar = new JLabel("$0.00");
        etiquetaCobrado = new JLabel("$0.00");

        filaIndicadores.add(crearTarjetaIndicador("Horas Totales Planificadas", etiquetaTotalHoras, PaletaOscura.AZUL_ACENTO));
        filaIndicadores.add(crearTarjetaIndicador("Saldo Por Cobrar", etiquetaPorCobrar, PaletaOscura.AMBAR_PENDIENTE));
        filaIndicadores.add(crearTarjetaIndicador("Total Ya Cobrado", etiquetaCobrado, PaletaOscura.VERDE_EXITO));

        // Tabla resumen financiero
        String[] columnas = {
            "Fecha", "Materia", "Tema", "Duración (h)", "Tarifa/h", "Modalidad", "Total ($)", "Estado Pago"
        };
        modeloTablaCobros = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCobros = new JTable(modeloTablaCobros);
        PaletaOscura.estilizarTabla(tablaCobros);

        JScrollPane scrollCobros = new JScrollPane(tablaCobros);
        scrollCobros.getViewport().setBackground(PaletaOscura.FONDO_TARJETA);
        scrollCobros.setBorder(BorderFactory.createLineBorder(PaletaOscura.BORDE_SUTIL));

        panel.add(filaIndicadores, BorderLayout.NORTH);
        panel.add(scrollCobros, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearTarjetaIndicador(String titulo, JLabel valorLabel, Color colorValor) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 8));
        tarjeta.setBackground(PaletaOscura.FONDO_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaOscura.BORDE_SUTIL),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(PaletaOscura.TEXTO_SECUNDARIO);
        lblTitulo.setFont(PaletaOscura.FUENTE_NEGRITA);

        valorLabel.setForeground(colorValor);
        valorLabel.setFont(PaletaOscura.FUENTE_TITULO);

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(valorLabel, BorderLayout.CENTER);
        return tarjeta;
    }

    // ==================== MÉTODOS DE ACTUALIZACIÓN ====================
    private void refrescarTodaLaInterfaz() {
        refrescarTablaClases();
        refrescarTablaEstudiantes();
        refrescarPanelCobros();
    }

    private void refrescarTablaClases() {
        modeloTablaClases.setRowCount(0);
        String filtro = (String) comboFiltroMateria.getSelectedItem();
        clasesMostradasActuales = controlador.obtenerClases(filtro);

        for (ClaseTutoria c : clasesMostradasActuales) {
            Object[] fila = {
                c.getFecha(),
                c.getHoraInicio() + " - " + c.getHoraFin(),
                c.calcularDuracionHoras() + " h",
                c.getMateria(),
                c.getTemaCapitulo(),
                c.getListaEstudiantes().size() + "/10: " + c.obtenerNombresEstudiantes(),
                String.format("$%.2f", c.calcularTotalCobro()),
                c.obtenerEstadoTexto()
            };
            modeloTablaClases.addRow(fila);
        }
    }

    private void refrescarTablaEstudiantes() {
        modeloTablaEstudiantes.setRowCount(0);
        for (Estudiante est : controlador.obtenerEstudiantes()) {
            double horas = controlador.calcularHorasPorEstudiante(est.getId());
            Object[] fila = {
                est.getId(),
                est.getNombreCompleto(),
                est.getUniversidad(),
                est.getNivelAcademico(),
                horas + " h"
            };
            modeloTablaEstudiantes.addRow(fila);
        }
    }

    private void refrescarPanelCobros() {
        etiquetaTotalHoras.setText(controlador.calcularTotalHoras() + " h");
        etiquetaPorCobrar.setText(String.format("$%.2f", controlador.calcularTotalPorCobrar()));
        etiquetaCobrado.setText(String.format("$%.2f", controlador.calcularTotalCobrado()));

        modeloTablaCobros.setRowCount(0);
        for (ClaseTutoria c : controlador.obtenerClases("Todas")) {
            String modalidad = c.isCobroPorEstudiante()
                    ? "Por alumno (" + c.getListaEstudiantes().size() + ")"
                    : "Tarifa grupal fija";
            Object[] fila = {
                c.getFecha(),
                c.getMateria(),
                c.getTemaCapitulo(),
                c.calcularDuracionHoras() + " h",
                String.format("$%.2f", c.getTarifaPorHora()),
                modalidad,
                String.format("$%.2f", c.calcularTotalCobro()),
                c.isPagada() ? "Pagada" : "Pendiente"
            };
            modeloTablaCobros.addRow(fila);
        }
    }

    private ClaseTutoria obtenerClaseSeleccionada() {
        int fila = tablaClases.getSelectedRow();
        if (fila < 0 || clasesMostradasActuales == null || fila >= clasesMostradasActuales.size()) {
            JOptionPane.showMessageDialog(this, "Selecciona primero una clase de la tabla.");
            return null;
        }
        return clasesMostradasActuales.get(fila);
    }

    private void mostrarDetalleClaseSeleccionada() {
        int fila = tablaClases.getSelectedRow();
        if (fila < 0 || clasesMostradasActuales == null || fila >= clasesMostradasActuales.size()) {
            return;
        }
        ClaseTutoria c = clasesMostradasActuales.get(fila);
        StringBuilder sb = new StringBuilder();
        sb.append("Materia: ").append(c.getMateria()).append("  |  Tema: ").append(c.getTemaCapitulo()).append("\n");
        sb.append("Estudiantes (").append(c.getListaEstudiantes().size()).append("/10): ").append(c.obtenerNombresEstudiantes()).append("\n");
        sb.append("Ejercicios planificados: ");
        if (c.getListaEjercicios().isEmpty()) {
            sb.append("Ningún ejercicio específico registrado.");
        } else {
            for (EjercicioPlanificado ej : c.getListaEjercicios()) {
                sb.append("\n  • ").append(ej.toString());
            }
        }
        areaDetalleClase.setText(sb.toString());
    }

    private JPanel crearEspacioVertical(int alto) {
        JPanel espacio = new JPanel();
        espacio.setBackground(PaletaOscura.FONDO_TARJETA);
        espacio.setMaximumSize(new Dimension(Integer.MAX_VALUE, alto));
        return espacio;
    }
}
