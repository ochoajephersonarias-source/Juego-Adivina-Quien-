package vista;

import idioma.GestorIdioma;
import usuarios.GestorUsuarios;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.Arrays;

public class VentanaRegistro extends JFrame {

    private final GestorUsuarios gestor;

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JPasswordField campoConfirmar;

    private JLabel titulo;
    private JLabel subtitulo;
    private JLabel etiquetaUsuario;
    private JLabel etiquetaContrasena;
    private JLabel etiquetaConfirmar;

    private JButton botonRegistrar;
    private JButton botonCancelar;

    public VentanaRegistro(GestorUsuarios gestor) {

        this.gestor = gestor;

        setSize(440, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        campoConfirmar = new JPasswordField();

        etiquetaUsuario = new JLabel();
        etiquetaContrasena = new JLabel();
        etiquetaConfirmar = new JLabel();

        JPanel formulario = new JPanel(new GridLayout(6, 1, 8, 8));

        formulario.add(etiquetaUsuario);
        formulario.add(campoUsuario);

        formulario.add(etiquetaContrasena);
        formulario.add(campoContrasena);

        formulario.add(etiquetaConfirmar);
        formulario.add(campoConfirmar);

        botonRegistrar = new JButton();
        botonRegistrar.addActionListener(e -> registrarCuenta());

        botonCancelar = new JButton();
        botonCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new GridLayout(1, 2, 10, 0));
        botones.add(botonRegistrar);
        botones.add(botonCancelar);

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
                        + GestorIdioma.obtenerTexto("registro.titulo")
        );

        titulo.setText(
                GestorIdioma.obtenerTexto("registro.titulo").toUpperCase()
        );

        subtitulo.setText(
                GestorIdioma.obtenerTexto("registro.subtitulo")
        );

        etiquetaUsuario.setText(
                GestorIdioma.obtenerTexto("registro.usuario")
        );

        etiquetaContrasena.setText(
                GestorIdioma.obtenerTexto("registro.contrasena")
        );

        etiquetaConfirmar.setText(
                GestorIdioma.obtenerTexto("registro.confirmar")
        );

        botonRegistrar.setText(
                GestorIdioma.obtenerTexto("registro.boton")
        );

        botonCancelar.setText(
                GestorIdioma.obtenerTexto("general.cancelar")
        );
    }

    private void registrarCuenta() {

        String nombre = campoUsuario.getText().trim();

        char[] contrasena = campoContrasena.getPassword();
        char[] confirmar = campoConfirmar.getPassword();

        try {

            if (nombre.isEmpty()) {

                mostrarMensaje(
                        GestorIdioma.obtenerTexto("registro.usuario_vacio"),
                        GestorIdioma.obtenerTexto("general.error"),
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (!nombre.matches("[a-zA-Z0-9_]{3,20}")) {

                mostrarMensaje(
                        GestorIdioma.obtenerTexto("registro.usuario_invalido"),
                        GestorIdioma.obtenerTexto("general.error"),
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (contrasena.length < 8) {

                mostrarMensaje(
                        GestorIdioma.obtenerTexto("registro.contrasena_corta"),
                        GestorIdioma.obtenerTexto("general.error"),
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (!Arrays.equals(contrasena, confirmar)) {

                mostrarMensaje(
                        GestorIdioma.obtenerTexto("registro.no_coinciden"),
                        GestorIdioma.obtenerTexto("general.error"),
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (gestor.existeUsuario(nombre)) {

                mostrarMensaje(
                        GestorIdioma.obtenerTexto("registro.duplicado"),
                        GestorIdioma.obtenerTexto("registro.error_titulo"),
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            gestor.registrarUsuario(nombre, contrasena);

            mostrarMensaje(
                    GestorIdioma.obtenerTexto("registro.exito_detalle"),
                    GestorIdioma.obtenerTexto("registro.exito_titulo"),
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (IllegalArgumentException | IllegalStateException ex) {

            mostrarMensaje(
                    GestorIdioma.obtenerTexto("registro.error_generico"),
                    GestorIdioma.obtenerTexto("registro.error_titulo"),
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IOException | GeneralSecurityException ex) {

            String mensaje = MessageFormat.format(
                    GestorIdioma.obtenerTexto("registro.error_guardar"),
                    ex.getMessage()
            );

            mostrarMensaje(
                    mensaje,
                    GestorIdioma.obtenerTexto("general.error"),
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            Arrays.fill(contrasena, '\0');
            Arrays.fill(confirmar, '\0');

            campoContrasena.setText("");
            campoConfirmar.setText("");
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
}