
package vista;

import usuarios.GestorUsuarios;
import usuarios.SesionActual;
import usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class VentanaLogin extends JFrame {

    private final GestorUsuarios gestor;

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;

    public VentanaLogin(GestorUsuarios gestor) {

        this.gestor = gestor;

        setTitle("Adivina Quién - Iniciar sesión");
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel titulo = new JLabel("ADIVINA QUIÉN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel subtitulo = new JLabel("Inicia sesión para jugar", SwingConstants.CENTER);

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 5));
        encabezado.add(titulo);
        encabezado.add(subtitulo);

        campoUsuario = new JTextField();
        campoContrasena = new JPasswordField();

        JPanel formulario = new JPanel(new GridLayout(4, 1, 8, 8));

        formulario.add(new JLabel("Nombre de usuario:"));
        formulario.add(campoUsuario);
        formulario.add(new JLabel("Contraseña:"));
        formulario.add(campoContrasena);

        JButton botonEntrar = new JButton("Iniciar sesión");
        botonEntrar.addActionListener(e -> iniciarSesion());

        campoContrasena.addActionListener(e -> iniciarSesion());

        JPanel botones = new JPanel(new BorderLayout());
        botones.add(botonEntrar, BorderLayout.CENTER);
        JButton botonRegistro = new JButton("Crear cuenta");

        botonRegistro.addActionListener(e -> {
            VentanaRegistro ventanaRegistro = new VentanaRegistro(gestor);
            ventanaRegistro.setVisible(true);
        });

        botones.add(botonRegistro, BorderLayout.SOUTH);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(formulario, BorderLayout.CENTER);
        principal.add(botones, BorderLayout.SOUTH);

        add(principal);
    }

    private void iniciarSesion() {

        String nombre = campoUsuario.getText().trim();
        char[] contrasena = campoContrasena.getPassword();

        try {

            if (nombre.isEmpty() || contrasena.length == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Completa todos los campos.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean valido = gestor.autenticar(nombre, contrasena);

            if (!valido) {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario o contraseña incorrectos.",
                        "Error de acceso",
                        JOptionPane.ERROR_MESSAGE
                );

                campoContrasena.setText("");
                return;
            }

            Usuario usuario = gestor.buscarUsuario(nombre);

            if (usuario == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo recuperar la cuenta."
                );
                return;
            }

            SesionActual.iniciarSesion(usuario);

            JOptionPane.showMessageDialog(
                    this,
                    "¡Bienvenido, " + usuario.getNombreUsuario() + "!"
            );

            dispose();

        } catch (IOException | GeneralSecurityException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al iniciar sesión: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {
            Arrays.fill(contrasena, '\0');
            campoContrasena.setText("");
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            GestorUsuarios gestor = new GestorUsuarios();
            VentanaLogin ventana = new VentanaLogin(gestor);
            ventana.setVisible(true);
        });
    }
}
