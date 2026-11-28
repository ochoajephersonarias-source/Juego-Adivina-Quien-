package vista;

import idioma.GestorIdioma;
import usuarios.GestorUsuarios;
import usuarios.SesionActual;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {

    private final GestorUsuarios gestor;

    private JLabel titulo;
    private JLabel bienvenida;

    private JButton botonJugar;
    private JButton botonPerfil;
    private JButton botonIdioma;
    private JButton botonCerrar;

    public VentanaMenu(GestorUsuarios gestor) {

        this.gestor = gestor;

        setSize(450, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new BorderLayout(15, 20));
        principal.setBorder(
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        );

        titulo = new JLabel("", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 27));

        bienvenida = new JLabel("", SwingConstants.CENTER);
        bienvenida.setFont(new Font("Arial", Font.PLAIN, 17));

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 10));
        encabezado.add(titulo);
        encabezado.add(bienvenida);

        botonJugar = new JButton();
        botonPerfil = new JButton();
        botonIdioma = new JButton();
        botonCerrar = new JButton();

        botonJugar.addActionListener(e -> {

            mostrarInformacion(
                    GestorIdioma.obtenerTexto("juego.pendiente"),
                    GestorIdioma.obtenerTexto("menu.jugar")
            );

        });

        botonPerfil.addActionListener(e -> {

            String mensaje =
                    GestorIdioma.obtenerTexto("perfil.usuario")
                            + ": " + SesionActual.obtenerNombreUsuario()
                            + "\n"
                            + GestorIdioma.obtenerTexto("perfil.id")
                            + ": " + SesionActual.obtenerIdUsuario();

            mostrarInformacion(
                    mensaje,
                    GestorIdioma.obtenerTexto("perfil.titulo")
            );

        });

        botonIdioma.addActionListener(e -> cambiarIdioma());

        botonCerrar.addActionListener(e -> cerrarSesion());

        JPanel botones = new JPanel(new GridLayout(4, 1, 10, 12));

        botones.add(botonJugar);
        botones.add(botonPerfil);
        botones.add(botonIdioma);
        botones.add(botonCerrar);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(botones, BorderLayout.CENTER);

        add(principal);

        actualizarTextos();
    }

    private void actualizarTextos() {

        String nombre = SesionActual.obtenerNombreUsuario();

        setTitle(
                GestorIdioma.obtenerTexto("app.titulo")
                        + " - "
                        + GestorIdioma.obtenerTexto("menu.titulo")
        );

        titulo.setText(
                GestorIdioma.obtenerTexto("app.titulo").toUpperCase()
        );

        String signoInicial =
                GestorIdioma.obtenerIdioma().equals("es") ? "¡" : "";

        bienvenida.setText(
                signoInicial
                        + GestorIdioma.obtenerTexto("menu.bienvenida")
                        + ", " + nombre + "!"
        );

        botonJugar.setText(
                GestorIdioma.obtenerTexto("menu.jugar")
        );

        botonPerfil.setText(
                GestorIdioma.obtenerTexto("menu.perfil")
        );

        botonIdioma.setText(
                GestorIdioma.obtenerTexto("menu.idioma")
        );

        botonCerrar.setText(
                GestorIdioma.obtenerTexto("menu.cerrar")
        );
    }

    private void cambiarIdioma() {

        String[] opciones = {
                GestorIdioma.obtenerTexto("idioma.espanol"),
                GestorIdioma.obtenerTexto("idioma.ingles")
        };

        JComboBox<String> selector = new JComboBox<>(opciones);

        selector.setSelectedIndex(
                GestorIdioma.obtenerIdioma().equals("es") ? 0 : 1
        );

        String[] botones = {
                GestorIdioma.obtenerTexto("general.aceptar"),
                GestorIdioma.obtenerTexto("general.cancelar")
        };

        int respuesta = JOptionPane.showOptionDialog(
                this,
                selector,
                GestorIdioma.obtenerTexto("idioma.titulo"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                botones,
                botones[0]
        );

        if (respuesta == 0) {

            String idioma = selector.getSelectedIndex() == 0
                    ? "es" : "en";

            GestorIdioma.cambiarIdioma(idioma);

            actualizarTextos();
        }
    }

    private void cerrarSesion() {

        String[] opciones = {
                GestorIdioma.obtenerTexto("general.si"),
                GestorIdioma.obtenerTexto("general.no")
        };

        int respuesta = JOptionPane.showOptionDialog(
                this,
                GestorIdioma.obtenerTexto("menu.confirmar_cierre"),
                GestorIdioma.obtenerTexto("menu.cerrar"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]
        );

        if (respuesta == 0) {

            SesionActual.cerrarSesion();

            dispose();

            VentanaLogin login = new VentanaLogin(gestor);
            login.setVisible(true);
        }
    }

    private void mostrarInformacion(String mensaje, String titulo) {

        String aceptar = GestorIdioma.obtenerTexto("general.aceptar");

        JOptionPane.showOptionDialog(
                this,
                mensaje,
                titulo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{aceptar},
                aceptar
        );
    }
}