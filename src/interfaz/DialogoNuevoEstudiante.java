package interfaz;

import controladores.ControladorTutorias;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoNuevoEstudiante extends JDialog {
    private ControladorTutorias controlador;
    private boolean guardado;

    private JTextField campoNombre;
    private JComboBox<String> comboUniversidad;
    private JComboBox<String> comboNivel;

    public DialogoNuevoEstudiante(JFrame padre, ControladorTutorias controlador) {
        super(padre, "Nuevo Estudiante", true);
        this.controlador = controlador;
        this.guardado = false;

        setSize(380, 390);
        setLocationRelativeTo(padre);
        getContentPane().setBackground(PaletaMarina.NEGRO_FONDO);
        setLayout(new BorderLayout());

        construirVista();
    }

    private void construirVista() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(PaletaMarina.NEGRO_FONDO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(20, 22, 16, 22));

        JLabel titulo = new JLabel("Registrar Estudiante");
        titulo.setForeground(PaletaMarina.TEXTO_BLANCO);
        titulo.setFont(PaletaMarina.FUENTE_APP_TITULO);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Agrega un alumno para asignarlo a tus clases");
        subtitulo.setForeground(PaletaMarina.TEXTO_GRIS_AZUL);
        subtitulo.setFont(PaletaMarina.FUENTE_PEQUENA);
        subtitulo.setAlignmentX(LEFT_ALIGNMENT);

        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(4));
        contenedor.add(subtitulo);
        contenedor.add(Box.createVerticalStrut(18));

        contenedor.add(crearEtiqueta("Nombre y Apellido"));
        contenedor.add(Box.createVerticalStrut(5));
        campoNombre = new JTextField();
        PaletaMarina.estilizarCampo(campoNombre);
        campoNombre.setAlignmentX(LEFT_ALIGNMENT);
        contenedor.add(campoNombre);
        contenedor.add(Box.createVerticalStrut(14));

        contenedor.add(crearEtiqueta("Universidad"));
        contenedor.add(Box.createVerticalStrut(5));
        String[] universidades = {"EPN", "ESPE", "PUCE", "UDLA", "Otra"};
        comboUniversidad = new JComboBox<>(universidades);
        PaletaMarina.estilizarCombo(comboUniversidad);
        comboUniversidad.setAlignmentX(LEFT_ALIGNMENT);
        contenedor.add(comboUniversidad);
        contenedor.add(Box.createVerticalStrut(14));

        contenedor.add(crearEtiqueta("Nivel o Semestre"));
        contenedor.add(Box.createVerticalStrut(5));
        String[] niveles = {"Nivelación", "1er Semestre", "2do Semestre", "3er Semestre"};
        comboNivel = new JComboBox<>(niveles);
        PaletaMarina.estilizarCombo(comboNivel);
        comboNivel.setAlignmentX(LEFT_ALIGNMENT);
        contenedor.add(comboNivel);

        add(contenedor, BorderLayout.CENTER);

        JPanel barraBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        barraBotones.setBackground(PaletaMarina.AZUL_MAR_OSCURO);
        barraBotones.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, PaletaMarina.BORDE_MARINO));

        BotonModerno btnCancelar = new BotonModerno(
                "Cancelar",
                PaletaMarina.AZUL_MAR_MEDIO,
                PaletaMarina.TEXTO_BLANCO,
                16);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        BotonModerno btnGuardar = new BotonModerno(
                "Guardar",
                PaletaMarina.CELESTE_PROFUNDO,
                PaletaMarina.CELESTE_BRILLANTE,
                Color.BLACK,
                16);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String res = controlador.registrarEstudiante(
                        campoNombre.getText(),
                        (String) comboUniversidad.getSelectedItem(),
                        (String) comboNivel.getSelectedItem());
                if (res.equals("OK")) {
                    guardado = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(DialogoNuevoEstudiante.this, res);
                }
            }
        });

        barraBotones.add(btnCancelar);
        barraBotones.add(btnGuardar);
        add(barraBotones, BorderLayout.SOUTH);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(PaletaMarina.CELESTE_SUAVE);
        lbl.setFont(PaletaMarina.FUENTE_NEGRITA);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    public boolean isGuardado() {
        return guardado;
    }
}
