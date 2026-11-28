import idioma.GestorIdioma;
import usuarios.GestorUsuarios;
import vista.VentanaLogin;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            String[] idiomas = {
                    "Español",
                    "English"
            };

            int seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Selecciona tu idioma / Select your language:",
                    "Adivina Quién / Guess Who",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    idiomas,
                    idiomas[0]
            );

            if (seleccion == JOptionPane.CLOSED_OPTION) {
                return;
            }

            String idioma = seleccion == 0 ? "es" : "en";

            GestorIdioma.cambiarIdioma(idioma);

            GestorUsuarios gestor = new GestorUsuarios();

            VentanaLogin ventana = new VentanaLogin(gestor);

            ventana.setVisible(true);
        });
    }
}