package interfaz;

import controladores.ControladorTutorias;
import entidades.Estudiante;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DialogoNuevaClase extends JDialog {
    private ControladorTutorias controlador;
    private boolean claseGuardada;

    private JComboBox<String> comboMateria;
    private JTextField campoFecha;
    private JComboBox<String> comboHoraInicio;
    private JComboBox<String> comboHoraFin;
    private JTextField campoTema;
    private JTextField campoEjercicios;
    private JTextField campoTarifa;
    private JCheckBox checkCobroPorAlumno;
    private JLabel etiquetaCupos;
    private JLabel etiquetaResumenCobro;
    private List<JCheckBox> checksEstudiantes;
    private List<Estudiante> listaEstudiantesDisponibles;

    public DialogoNuevaClase(JFrame padre, ControladorTutorias controlador) {
        super(padre, "Agendar Nueva Tutoría", true);
        this.controlador = controlador;
        this.claseGuardada = false;
        this.checksEstudiantes = new ArrayList<>();
        this.listaEstudiantesDisponibles = controlador.obtenerEstudiantes();

        setSize(520, 620);
        setLocationRelativeTo(padre);
        getContentPane().setBackground(PaletaOscura.FONDO_PRINCIPAL);
        setLayout(new BorderLayout(10, 10));

        construirFormulario();
        actualizarResumenEnVivo();
    }

    private void construirFormulario() {
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(16, 20, 10, 20));

        // Materia
        panelCampos.add(crearEtiqueta("Materia:"));
        comboMateria = new JComboBox<>(ControladorTutorias.MATERIAS_DISPONIBLES);
        PaletaOscura.estilizarComboBox(comboMateria);
        panelCampos.add(comboMateria);
        panelCampos.add(espacio());

        // Fecha y Horarios disponibles
        JPanel filaHorario = new JPanel(new GridLayout(1, 3, 10, 0));
        filaHorario.setBackground(PaletaOscura.FONDO_PRINCIPAL);

        JPanel colFecha = new JPanel(new BorderLayout(0, 4));
        colFecha.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        colFecha.add(crearEtiqueta("Día / Fecha:"), BorderLayout.NORTH);
        campoFecha = new JTextField("Miércoles 25 Nov");
        PaletaOscura.estilizarCampoTexto(campoFecha);
        colFecha.add(campoFecha, BorderLayout.CENTER);

        String[] horasDisponibles = generarBloquesHorario();

        JPanel colInicio = new JPanel(new BorderLayout(0, 4));
        colInicio.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        colInicio.add(crearEtiqueta("Hora Inicio:"), BorderLayout.NORTH);
        comboHoraInicio = new JComboBox<>(horasDisponibles);
        comboHoraInicio.setSelectedItem("16:00");
        PaletaOscura.estilizarComboBox(comboHoraInicio);
        colInicio.add(comboHoraInicio, BorderLayout.CENTER);

        JPanel colFin = new JPanel(new BorderLayout(0, 4));
        colFin.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        colFin.add(crearEtiqueta("Hora Fin:"), BorderLayout.NORTH);
        comboHoraFin = new JComboBox<>(horasDisponibles);
        comboHoraFin.setSelectedItem("18:00");
        PaletaOscura.estilizarComboBox(comboHoraFin);
        colFin.add(comboHoraFin, BorderLayout.CENTER);

        filaHorario.add(colFecha);
        filaHorario.add(colInicio);
        filaHorario.add(colFin);
        panelCampos.add(filaHorario);
        panelCampos.add(espacio());

        // Tema / Capítulo
        panelCampos.add(crearEtiqueta("Tema / Capítulo:"));
        campoTema = new JTextField();
        PaletaOscura.estilizarCampoTexto(campoTema);
        panelCampos.add(campoTema);
        panelCampos.add(espacio());

        // Ejercicios planeados
        panelCampos.add(crearEtiqueta("Ejercicios planeados (separa varios con punto y coma ;):"));
        campoEjercicios = new JTextField();
        PaletaOscura.estilizarCampoTexto(campoEjercicios);
        panelCampos.add(campoEjercicios);
        panelCampos.add(espacio());

        // Selección de estudiantes (máximo 10)
        etiquetaCupos = crearEtiqueta("Estudiantes (0 / 10 cupos):");
        panelCampos.add(etiquetaCupos);

        JPanel panelListaEstudiantes = new JPanel();
        panelListaEstudiantes.setLayout(new BoxLayout(panelListaEstudiantes, BoxLayout.Y_AXIS));
        panelListaEstudiantes.setBackground(PaletaOscura.FONDO_CAMPO);

        for (Estudiante est : listaEstudiantesDisponibles) {
            JCheckBox check = new JCheckBox(est.toString());
            check.setBackground(PaletaOscura.FONDO_CAMPO);
            check.setForeground(PaletaOscura.TEXTO_PRINCIPAL);
            check.setFont(PaletaOscura.FUENTE_NORMAL);
            check.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int seleccionados = contarEstudiantesSeleccionados();
                    if (seleccionados > ControladorTutorias.MAXIMO_ESTUDIANTES_POR_CLASE) {
                        check.setSelected(false);
                        JOptionPane.showMessageDialog(
                                DialogoNuevaClase.this,
                                "Solo puedes asignar hasta 10 estudiantes por clase.",
                                "Límite de cupos",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    actualizarResumenEnVivo();
                }
            });
            checksEstudiantes.add(check);
            panelListaEstudiantes.add(check);
        }

        JScrollPane scrollEstudiantes = new JScrollPane(panelListaEstudiantes);
        scrollEstudiantes.setBorder(BorderFactory.createLineBorder(PaletaOscura.BORDE_SUTIL));
        panelCampos.add(scrollEstudiantes);
        panelCampos.add(espacio());

        // Tarifa y modo de cobro
        JPanel filaCobro = new JPanel(new GridLayout(1, 2, 10, 0));
        filaCobro.setBackground(PaletaOscura.FONDO_PRINCIPAL);

        JPanel colTarifa = new JPanel(new BorderLayout(0, 4));
        colTarifa.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        colTarifa.add(crearEtiqueta("Tarifa por hora ($):"), BorderLayout.NORTH);
        campoTarifa = new JTextField("5.00");
        PaletaOscura.estilizarCampoTexto(campoTarifa);
        colTarifa.add(campoTarifa, BorderLayout.CENTER);

        checkCobroPorAlumno = new JCheckBox("Cobrar tarifa por cada alumno");
        checkCobroPorAlumno.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        checkCobroPorAlumno.setForeground(PaletaOscura.TEXTO_PRINCIPAL);
        checkCobroPorAlumno.setFont(PaletaOscura.FUENTE_NORMAL);

        filaCobro.add(colTarifa);
        filaCobro.add(checkCobroPorAlumno);
        panelCampos.add(filaCobro);

        // Listeners para cálculo automático
        ActionListener refrescarCalculo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarResumenEnVivo();
            }
        };
        comboHoraInicio.addActionListener(refrescarCalculo);
        comboHoraFin.addActionListener(refrescarCalculo);
        checkCobroPorAlumno.addActionListener(refrescarCalculo);
        campoTarifa.addActionListener(refrescarCalculo);

        add(panelCampos, BorderLayout.CENTER);

        // Pie del diálogo: Resumen + Botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(PaletaOscura.FONDO_TARJETA);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        etiquetaResumenCobro = new JLabel("Total estimado: $10.00");
        etiquetaResumenCobro.setForeground(PaletaOscura.VERDE_EXITO);
        etiquetaResumenCobro.setFont(PaletaOscura.FUENTE_SUBTITULO);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBotones.setBackground(PaletaOscura.FONDO_TARJETA);

        JButton botonCancelar = PaletaOscura.crearBoton("Cancelar", PaletaOscura.BORDE_SUTIL);
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton botonGuardar = PaletaOscura.crearBoton("Guardar Clase", PaletaOscura.AZUL_ACENTO);
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarClase();
            }
        });

        panelBotones.add(botonCancelar);
        panelBotones.add(botonGuardar);

        panelInferior.add(etiquetaResumenCobro, BorderLayout.WEST);
        panelInferior.add(panelBotones, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void guardarClase() {
        double tarifa = 0.0;
        try {
            tarifa = Double.parseDouble(campoTarifa.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un precio válido por hora (ej. 5.00).");
            return;
        }

        List<Estudiante> seleccionados = new ArrayList<>();
        for (int i = 0; i < checksEstudiantes.size(); i++) {
            if (checksEstudiantes.get(i).isSelected()) {
                seleccionados.add(listaEstudiantesDisponibles.get(i));
            }
        }

        String resultado = controlador.agendarNuevaClase(
                campoFecha.getText(),
                (String) comboHoraInicio.getSelectedItem(),
                (String) comboHoraFin.getSelectedItem(),
                (String) comboMateria.getSelectedItem(),
                campoTema.getText(),
                campoEjercicios.getText(),
                seleccionados,
                tarifa,
                checkCobroPorAlumno.isSelected());

        if (resultado.equals("OK")) {
            claseGuardada = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Revisa los datos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarResumenEnVivo() {
        int seleccionados = contarEstudiantesSeleccionados();
        etiquetaCupos.setText("Estudiantes (" + seleccionados + " / 10 cupos):");

        try {
            String inicio = (String) comboHoraInicio.getSelectedItem();
            String fin = (String) comboHoraFin.getSelectedItem();
            String[] pI = inicio.split(":");
            String[] pF = fin.split(":");
            int minI = Integer.parseInt(pI[0]) * 60 + Integer.parseInt(pI[1]);
            int minF = Integer.parseInt(pF[0]) * 60 + Integer.parseInt(pF[1]);
            double horas = (minF > minI) ? (minF - minI) / 60.0 : 0.0;

            double tarifa = Double.parseDouble(campoTarifa.getText().trim());
            int mult = (checkCobroPorAlumno.isSelected() && seleccionados > 0) ? seleccionados : 1;
            double total = horas * tarifa * mult;

            etiquetaResumenCobro.setText(String.format("Tiempo: %.1f h  |  Total: $%.2f", horas, total));
        } catch (Exception e) {
            etiquetaResumenCobro.setText("Calculando...");
        }
    }

    private int contarEstudiantesSeleccionados() {
        int total = 0;
        for (JCheckBox check : checksEstudiantes) {
            if (check.isSelected()) {
                total++;
            }
        }
        return total;
    }

    private String[] generarBloquesHorario() {
        List<String> bloques = new ArrayList<>();
        for (int hora = 7; hora <= 21; hora++) {
            bloques.add(String.format("%02d:00", hora));
            bloques.add(String.format("%02d:30", hora));
        }
        return bloques.toArray(new String[0]);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(PaletaOscura.TEXTO_SECUNDARIO);
        etiqueta.setFont(PaletaOscura.FUENTE_NEGRITA);
        return etiqueta;
    }

    private JPanel espacio() {
        JPanel p = new JPanel();
        p.setBackground(PaletaOscura.FONDO_PRINCIPAL);
        return p;
    }

    public boolean isClaseGuardada() {
        return claseGuardada;
    }
}
