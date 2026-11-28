package vista;

import idioma.GestorIdioma;
import usuarios.GestorUsuarios;
import usuarios.SesionActual;
import usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.Arrays;

public class VentanaLogin extends JFrame {

    private final GestorUsuarios gestor;

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;

    private JLabel titulo;
    private JLabel subtitulo;
    private JLabel etiquetaUsuario;
    private JLabel etiquetaContrasena;

    private JButton botonEntrar;
    private JButton botonRegistro;

    public VentanaLogin(GestorUsuarios gestor) {

        this.gestor = gestor;

        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        titulo = new JLabel("", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        subtitulo = new JLabel("", SwingConstants.CENTER);

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 5));
        encabezado.add(titulo);
        encabezado.add(subtitulo);

        campoUsuario = new JTextField();
        campoContrasena = new JPasswordField();

        etiquetaUsuario = new JLabel();
        etiquetaContrasena = new JLabel();

        JPanel formulario = new JPanel(new GridLayout(4, 1, 8, 8));

        formulario.add(etiquetaUsuario);
        formulario.add(campoUsuario);
        formulario.add(etiquetaContrasena);
        formulario.add(campoContrasena);

        botonEntrar = new JButton();
        botonEntrar.addActionListener(e -> iniciarSesion());

        campoContrasena.addActionListener(e -> iniciarSesion());

        JPanel botones = new JPanel(new BorderLayout());

        botones.add(botonEntrar, BorderLayout.CENTER);

        botonRegistro = new JButton();

        botonRegistro.addActionListener(e -> {
            VentanaRegistro ventanaRegistro =
                    new VentanaRegistro(gestor);

            ventanaRegistro.setVisible(true);
        });

        botones.add(botonRegistro, BorderLayout.SOUTH);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(formulario, BorderLayout.CENTER);
        principal.add(botones, BorderLayout.SOUTH);

        add(principal);

        actualizarTextos();
    }

    private void actualizarTextos() {

        setTitle(
                GestorIdioma.obtenerTexto("app.titulo")
                        + " - "
                        + GestorIdioma.obtenerTexto("login.titulo")
        );

        titulo.setText(
                GestorIdioma.obtenerTexto("app.titulo").toUpperCase()
        );

        subtitulo.setText(
                GestorIdioma.obtenerTexto("login.subtitulo")
        );

        etiquetaUsuario.setText(
                GestorIdioma.obtenerTexto("login.usuario")
        );

        etiquetaContrasena.setText(
                GestorIdioma.obtenerTexto("login.contrasena")
        );

        botonEntrar.setText(
                GestorIdioma.obtenerTexto("login.boton")
        );

        botonRegistro.setText(
                GestorIdioma.obtenerTexto("login.registro")
        );
    }

    private void iniciarSesion() {

        String nombre = campoUsuario.getText().trim();
        char[] contrasena = campoContrasena.getPassword();

        try {

            if (nombre.isEmpty() || contrasena.length == 0) {

                mostrarMensaje(
                        GestorIdioma.obtenerTexto("login.campos_vacios"),
                        GestorIdioma.obtenerTexto("login.campos_incompletos"),
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean valido = gestor.autenticar(nombre, contrasena);

            if (!valido) {

                mostrarMensaje(
                        GestorIdioma.obtenerTexto("login.error"),
                        GestorIdioma.obtenerTexto("login.error_titulo"),
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            Usuario usuario = gestor.buscarUsuario(nombre);

            if (usuario == null) {

                mostrarMensaje(
                        GestorIdioma.obtenerTexto("login.cuenta_no_recuperada"),
                        GestorIdioma.obtenerTexto("login.error_titulo"),
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            SesionActual.iniciarSesion(usuario);

            String bienvenida = MessageFormat.format(
                    GestorIdioma.obtenerTexto("login.bienvenida_usuario"),
                    usuario.getNombreUsuario()
            );

            mostrarMensaje(
                    bienvenida,
                    GestorIdioma.obtenerTexto("general.exito"),
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            VentanaMenu menu = new VentanaMenu(gestor);
            menu.setVisible(true);

        } catch (IOException | GeneralSecurityException ex) {

            String mensaje = MessageFormat.format(
                    GestorIdioma.obtenerTexto("login.error_detalle"),
                    ex.getMessage()
            );

            mostrarMensaje(
                    mensaje,
                    GestorIdioma.obtenerTexto("general.error"),
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            Arrays.fill(contrasena, '\0');
            campoContrasena.setText("");
        }
    }

    private void mostrarMensaje(
            String mensaje,
            String titulo,
            int tipo
    ) {

        String aceptar = GestorIdioma.obtenerTexto("general.aceptar");

        JOptionPane.showOptionDialog(
                this,
                mensaje,
                titulo,
                JOptionPane.DEFAULT_OPTION,
                tipo,
                null,
                new Object[]{aceptar},
                aceptar
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            GestorUsuarios gestor = new GestorUsuarios();

            VentanaLogin ventana = new VentanaLogin(gestor);

            ventana.setVisible(true);
        });
    }
}