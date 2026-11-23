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
import javax.swing.Box;
import javax.swing.BoxLayout;
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
    private JTextField campoCapitulo;
    private JTextField campoTema;
    private JTextField campoEjercicios;
    private JTextField campoTarifa;
    private JCheckBox checkCobroPorAlumno;
    private JLabel etiquetaCupos;
    private JLabel etiquetaTotalVivo;
    private List<JCheckBox> checksEstudiantes;
    private List<Estudiante> estudiantesDisponibles;

    public DialogoNuevaClase(JFrame padre, ControladorTutorias controlador) {
        super(padre, "Agendar Tutoría • AulEmi", true);
        this.controlador = controlador;
        this.claseGuardada = false;
        this.checksEstudiantes = new ArrayList<>();
        this.estudiantesDisponibles = controlador.obtenerEstudiantes();

        setSize(415, 745);
        setLocationRelativeTo(padre);
        getContentPane().setBackground(PaletaMarina.NEGRO_FONDO);
        setLayout(new BorderLayout());

        construirFormulario();
        recalcularEnVivo();
    }

    private void construirFormulario() {
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(PaletaMarina.NEGRO_FONDO);
        contenido.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        cabecera.setBackground(PaletaMarina.NEGRO_FONDO);
        cabecera.setAlignmentX(LEFT_ALIGNMENT);
        cabecera.add(new LogoApp(38));

        JLabel titulo = new JLabel("Agendar Tutoría");
        titulo.setForeground(PaletaMarina.TEXTO_BLANCO);
        titulo.setFont(PaletaMarina.FUENTE_APP_TITULO);
        cabecera.add(titulo);

        contenido.add(cabecera);
        contenido.add(Box.createVerticalStrut(14));

        // Materia
        contenido.add(crearEtiqueta("Materia"));
        contenido.add(Box.createVerticalStrut(4));
        comboMateria = new JComboBox<>(ControladorTutorias.MATERIAS_DISPONIBLES);
        PaletaMarina.estilizarCombo(comboMateria);
        comboMateria.setAlignmentX(LEFT_ALIGNMENT);
        contenido.add(comboMateria);
        contenido.add(Box.createVerticalStrut(10));

        // Día / Fecha
        contenido.add(crearEtiqueta("Día y Fecha"));
        contenido.add(Box.createVerticalStrut(4));
        campoFecha = new JTextField("Vie 27 Nov");
        PaletaMarina.estilizarCampo(campoFecha);
        campoFecha.setAlignmentX(LEFT_ALIGNMENT);
        contenido.add(campoFecha);
        contenido.add(Box.createVerticalStrut(10));

        // Horarios
        JPanel filaHoras = new JPanel(new GridLayout(1, 2, 10, 0));
        filaHoras.setBackground(PaletaMarina.NEGRO_FONDO);
        filaHoras.setAlignmentX(LEFT_ALIGNMENT);

        String[] bloques = generarBloquesHorario();

        JPanel colInicio = new JPanel(new BorderLayout(0, 4));
        colInicio.setBackground(PaletaMarina.NEGRO_FONDO);
        colInicio.add(crearEtiqueta("Hora Inicio"), BorderLayout.NORTH);
        comboHoraInicio = new JComboBox<>(bloques);
        comboHoraInicio.setSelectedItem("16:00");
        PaletaMarina.estilizarCombo(comboHoraInicio);
        colInicio.add(comboHoraInicio, BorderLayout.CENTER);

        JPanel colFin = new JPanel(new BorderLayout(0, 4));
        colFin.setBackground(PaletaMarina.NEGRO_FONDO);
        colFin.add(crearEtiqueta("Hora Fin"), BorderLayout.NORTH);
        comboHoraFin = new JComboBox<>(bloques);
        comboHoraFin.setSelectedItem("18:00");
        PaletaMarina.estilizarCombo(comboHoraFin);
        colFin.add(comboHoraFin, BorderLayout.CENTER);

        filaHoras.add(colInicio);
        filaHoras.add(colFin);
        contenido.add(filaHoras);
        contenido.add(Box.createVerticalStrut(10));

        // Capítulo y Tema en dos campos separados
        JPanel filaCapTema = new JPanel(new BorderLayout(8, 0));
        filaCapTema.setBackground(PaletaMarina.NEGRO_FONDO);
        filaCapTema.setAlignmentX(LEFT_ALIGNMENT);

        JPanel colCap = new JPanel(new BorderLayout(0, 4));
        colCap.setBackground(PaletaMarina.NEGRO_FONDO);
        colCap.add(crearEtiqueta("Capítulo"), BorderLayout.NORTH);
        campoCapitulo = new JTextField("Cap. 1");
        PaletaMarina.estilizarCampo(campoCapitulo);
        colCap.add(campoCapitulo, BorderLayout.CENTER);

        JPanel colTema = new JPanel(new BorderLayout(0, 4));
        colTema.setBackground(PaletaMarina.NEGRO_FONDO);
        colTema.add(crearEtiqueta("Tema Principal"), BorderLayout.NORTH);
        campoTema = new JTextField();
        PaletaMarina.estilizarCampo(campoTema);
        colTema.add(campoTema, BorderLayout.CENTER);

        filaCapTema.add(colCap, BorderLayout.WEST);
        filaCapTema.add(colTema, BorderLayout.CENTER);
        contenido.add(filaCapTema);
        contenido.add(Box.createVerticalStrut(10));

        // Guía / Ejercicios
        contenido.add(crearEtiqueta("Guía / Ejercicios (separa varios con ;)"));
        contenido.add(Box.createVerticalStrut(4));
        campoEjercicios = new JTextField();
        PaletaMarina.estilizarCampo(campoEjercicios);
        campoEjercicios.setAlignmentX(LEFT_ALIGNMENT);
        contenido.add(campoEjercicios);
        contenido.add(Box.createVerticalStrut(10));

        // Alumnos (máximo 10)
        etiquetaCupos = crearEtiqueta("Estudiantes (0 / 10 cupos)");
        contenido.add(etiquetaCupos);
        contenido.add(Box.createVerticalStrut(4));

        JPanel listaChecks = new JPanel();
        listaChecks.setLayout(new BoxLayout(listaChecks, BoxLayout.Y_AXIS));
        listaChecks.setBackground(PaletaMarina.AZUL_MAR_TARJETA);
        listaChecks.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        for (Estudiante est : estudiantesDisponibles) {
            JCheckBox check = new JCheckBox(est.toString());
            check.setBackground(PaletaMarina.AZUL_MAR_TARJETA);
            check.setForeground(PaletaMarina.TEXTO_BLANCO);
            check.setFont(PaletaMarina.FUENTE_CUERPO);
            check.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (contarSeleccionados() > ControladorTutorias.MAXIMO_ESTUDIANTES_POR_CLASE) {
                        check.setSelected(false);
                        JOptionPane.showMessageDialog(
                                DialogoNuevaClase.this,
                                "Máximo 10 estudiantes por clase.");
                    }
                    recalcularEnVivo();
                }
            });
            checksEstudiantes.add(check);
            listaChecks.add(check);
        }

        JScrollPane scrollEstudiantes = new JScrollPane(listaChecks);
        scrollEstudiantes.setBorder(BorderFactory.createLineBorder(PaletaMarina.BORDE_MARINO));
        scrollEstudiantes.setAlignmentX(LEFT_ALIGNMENT);
        contenido.add(scrollEstudiantes);
        contenido.add(Box.createVerticalStrut(10));

        // Precio por hora y modalidad
        JPanel filaPrecio = new JPanel(new GridLayout(1, 2, 10, 0));
        filaPrecio.setBackground(PaletaMarina.NEGRO_FONDO);
        filaPrecio.setAlignmentX(LEFT_ALIGNMENT);

        JPanel colTarifa = new JPanel(new BorderLayout(0, 4));
        colTarifa.setBackground(PaletaMarina.NEGRO_FONDO);
        colTarifa.add(crearEtiqueta("Precio / hora ($)"), BorderLayout.NORTH);
        campoTarifa = new JTextField("5.00");
        PaletaMarina.estilizarCampo(campoTarifa);
        colTarifa.add(campoTarifa, BorderLayout.CENTER);

        checkCobroPorAlumno = new JCheckBox("Cobrar por alumno");
        checkCobroPorAlumno.setBackground(PaletaMarina.NEGRO_FONDO);
        checkCobroPorAlumno.setForeground(PaletaMarina.CELESTE_SUAVE);
        checkCobroPorAlumno.setFont(PaletaMarina.FUENTE_NEGRITA);

        filaPrecio.add(colTarifa);
        filaPrecio.add(checkCobroPorAlumno);
        contenido.add(filaPrecio);

        ActionListener refrescar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalcularEnVivo();
            }
        };
        comboHoraInicio.addActionListener(refrescar);
        comboHoraFin.addActionListener(refrescar);
        checkCobroPorAlumno.addActionListener(refrescar);
        campoTarifa.addActionListener(refrescar);

        JScrollPane scrollPrincipal = new JScrollPane(contenido);
        PaletaMarina.estilizarScrollMovil(scrollPrincipal);
        add(scrollPrincipal, BorderLayout.CENTER);

        JPanel barraInferior = new JPanel(new BorderLayout());
        barraInferior.setBackground(PaletaMarina.AZUL_MAR_OSCURO);
        barraInferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, PaletaMarina.BORDE_MARINO),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        etiquetaTotalVivo = new JLabel("2.0h • $10.00");
        etiquetaTotalVivo.setForeground(PaletaMarina.TEXTO_BLANCO);
        etiquetaTotalVivo.setFont(PaletaMarina.FUENTE_SUBTITULO);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setBackground(PaletaMarina.AZUL_MAR_OSCURO);

        BotonModerno btnCancelar = new BotonModerno(
                "Cerrar",
                PaletaMarina.AZUL_MAR_TARJETA,
                PaletaMarina.TEXTO_BLANCO,
                18);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        BotonModerno btnGuardar = new BotonModerno(
                "Agendar",
                PaletaMarina.TEXTO_BLANCO,
                PaletaMarina.CELESTE_BRILLANTE,
                PaletaMarina.NEGRO_FONDO,
                18);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarClase();
            }
        });

        botones.add(btnCancelar);
        botones.add(btnGuardar);

        barraInferior.add(etiquetaTotalVivo, BorderLayout.WEST);
        barraInferior.add(botones, BorderLayout.EAST);

        add(barraInferior, BorderLayout.SOUTH);
    }

    private void guardarClase() {
        double tarifa;
        try {
            tarifa = Double.parseDouble(campoTarifa.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un precio válido (ej. 5.00).");
            return;
        }

        List<Estudiante> seleccionados = new ArrayList<>();
        for (int i = 0; i < checksEstudiantes.size(); i++) {
            if (checksEstudiantes.get(i).isSelected()) {
                seleccionados.add(estudiantesDisponibles.get(i));
            }
        }

        String cap = campoCapitulo.getText() == null ? "" : campoCapitulo.getText().trim();
        String tem = campoTema.getText() == null ? "" : campoTema.getText().trim();
        String temaCombinado = tem.isEmpty() ? "" : (cap.isEmpty() ? tem : (cap + ": " + tem));

        String res = controlador.agendarNuevaClase(
                campoFecha.getText(),
                (String) comboHoraInicio.getSelectedItem(),
                (String) comboHoraFin.getSelectedItem(),
                (String) comboMateria.getSelectedItem(),
                temaCombinado,
                campoEjercicios.getText(),
                seleccionados,
                tarifa,
                checkCobroPorAlumno.isSelected());

        if (res.equals("OK")) {
            claseGuardada = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, res);
        }
    }

    private void recalcularEnVivo() {
        int sel = contarSeleccionados();
        etiquetaCupos.setText("Estudiantes (" + sel + " / 10 cupos)");

        try {
            String ini = (String) comboHoraInicio.getSelectedItem();
            String fin = (String) comboHoraFin.getSelectedItem();
            String[] pI = ini.split(":");
            String[] pF = fin.split(":");
            int mI = Integer.parseInt(pI[0]) * 60 + Integer.parseInt(pI[1]);
            int mF = Integer.parseInt(pF[0]) * 60 + Integer.parseInt(pF[1]);
            double horas = (mF > mI) ? (mF - mI) / 60.0 : 0.0;

            double tarifa = Double.parseDouble(campoTarifa.getText().trim());
            int mult = (checkCobroPorAlumno.isSelected() && sel > 0) ? sel : 1;
            double total = horas * tarifa * mult;

            etiquetaTotalVivo.setText(String.format("%.1fh  •  $%.2f", horas, total));
        } catch (Exception e) {
            etiquetaTotalVivo.setText("Calculando...");
        }
    }

    private int contarSeleccionados() {
        int c = 0;
        for (JCheckBox check : checksEstudiantes) {
            if (check.isSelected()) {
                c++;
            }
        }
        return c;
    }

    private String[] generarBloquesHorario() {
        List<String> lista = new ArrayList<>();
        for (int h = 7; h <= 21; h++) {
            lista.add(String.format("%02d:00", h));
            lista.add(String.format("%02d:30", h));
        }
        return lista.toArray(new String[0]);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(PaletaMarina.CELESTE_SUAVE);
        lbl.setFont(PaletaMarina.FUENTE_NEGRITA);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    public boolean isClaseGuardada() {
        return claseGuardada;
    }
}
