
package vista;

import usuarios.GestorUsuarios;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class VentanaRegistro extends JFrame {

    private final GestorUsuarios gestor;

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JPasswordField campoConfirmar;

    public VentanaRegistro(GestorUsuarios gestor) {

        this.gestor = gestor;

        setTitle("Adivina Quién - Crear cuenta");
        setSize(440, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        JLabel titulo = new JLabel(
                "CREAR CUENTA",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel subtitulo = new JLabel(
                "Regístrate para comenzar a jugar",
                SwingConstants.CENTER
        );

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 5));
        encabezado.add(titulo);
        encabezado.add(subtitulo);

        campoUsuario = new JTextField();
        campoContrasena = new JPasswordField();
        campoConfirmar = new JPasswordField();

        JPanel formulario = new JPanel(new GridLayout(6, 1, 8, 8));

        formulario.add(new JLabel("Nombre de usuario:"));
        formulario.add(campoUsuario);

        formulario.add(new JLabel("Contraseña:"));
        formulario.add(campoContrasena);

        formulario.add(new JLabel("Confirmar contraseña:"));
        formulario.add(campoConfirmar);

        JButton botonRegistrar = new JButton("Crear cuenta");
        botonRegistrar.addActionListener(e -> registrarCuenta());

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new GridLayout(1, 2, 10, 0));
        botones.add(botonRegistrar);
        botones.add(botonCancelar);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(formulario, BorderLayout.CENTER);
        principal.add(botones, BorderLayout.SOUTH);

        add(principal);
    }

    private void registrarCuenta() {

        String nombre = campoUsuario.getText().trim();

        char[] contrasena = campoContrasena.getPassword();
        char[] confirmar = campoConfirmar.getPassword();

        try {

            if (nombre.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debes escribir un nombre de usuario."
                );

                return;
            }

            if (contrasena.length < 8) {

                JOptionPane.showMessageDialog(
                        this,
                        "La contraseña debe tener al menos 8 caracteres."
                );

                return;
            }

            if (!Arrays.equals(contrasena, confirmar)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Las contraseñas no coinciden."
                );

                return;
            }

            gestor.registrarUsuario(nombre, contrasena);

            JOptionPane.showMessageDialog(
                    this,
                    "¡Cuenta creada correctamente! Ya puedes iniciar sesión.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (IllegalArgumentException | IllegalStateException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "No se pudo registrar",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IOException | GeneralSecurityException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar la cuenta: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            Arrays.fill(contrasena, '\0');
            Arrays.fill(confirmar, '\0');

            campoContrasena.setText("");
            campoConfirmar.setText("");
        }
    }
}
