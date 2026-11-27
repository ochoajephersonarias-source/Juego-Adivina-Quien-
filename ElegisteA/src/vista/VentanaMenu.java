
package vista;

import usuarios.GestorUsuarios;
import usuarios.SesionActual;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {

    private final GestorUsuarios gestor;

    public VentanaMenu(GestorUsuarios gestor) {

        this.gestor = gestor;

        setTitle("Adivina Quién - Menú principal");
        setSize(450, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new BorderLayout(15, 20));
        principal.setBorder(
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        );

        String nombre = SesionActual.obtenerNombreUsuario();

        JLabel titulo = new JLabel(
                "ADIVINA QUIÉN",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel bienvenida = new JLabel(
                "¡Bienvenido, " + nombre + "!",
                SwingConstants.CENTER
        );

        bienvenida.setFont(new Font("Arial", Font.PLAIN, 17));

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 10));
        encabezado.add(titulo);
        encabezado.add(bienvenida);

        JButton botonJugar = new JButton("Jugar");
        JButton botonPerfil = new JButton("Mi perfil");
        JButton botonIdioma = new JButton("Cambiar idioma");
        JButton botonCerrar = new JButton("Cerrar sesión");

        botonJugar.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "La conexión con la partida se realizará "
                            + "al integrar el módulo de juego."
            );

        });

        botonPerfil.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Nombre de usuario: "
                            + SesionActual.obtenerNombreUsuario()
                            + "\nID de usuario: "
                            + SesionActual.obtenerIdUsuario(),
                    "Mi perfil",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        botonIdioma.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "La selección de español e inglés "
                            + "se implementará en el módulo de idiomas."
            );

        });

        botonCerrar.addActionListener(e -> {

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas cerrar sesión?",
                    "Cerrar sesión",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {

                SesionActual.cerrarSesion();

                dispose();

                VentanaLogin login = new VentanaLogin(gestor);
                login.setVisible(true);
            }

        });

        JPanel botones = new JPanel(new GridLayout(4, 1, 10, 12));

        botones.add(botonJugar);
        botones.add(botonPerfil);
        botones.add(botonIdioma);
        botones.add(botonCerrar);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(botones, BorderLayout.CENTER);

        add(principal);
    }
}
