package interfaz;

import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaTelefono appMovil = new VentanaTelefono();
                appMovil.setVisible(true);
            }
        });
    }
}
