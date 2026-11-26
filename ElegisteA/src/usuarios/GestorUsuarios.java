
package usuarios;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class GestorUsuarios {

    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final int ITERACIONES = 120000;
    private static final int LONGITUD_HASH = 256;
    private static final int LONGITUD_SAL = 16;

    private final Path archivoUsuarios;

    public GestorUsuarios() {
        this(Path.of("data", "usuarios.txt"));
    }

    public GestorUsuarios(Path archivoUsuarios) {
        this.archivoUsuarios = archivoUsuarios;
    }

    // Registra una cuenta nueva
    public synchronized Usuario registrarUsuario(
            String nombreUsuario, char[] contrasena)
            throws IOException, GeneralSecurityException {

        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new IllegalArgumentException("El usuario es obligatorio.");
        }

        String nombre = nombreUsuario.trim();

        if (!nombre.matches("[a-zA-Z0-9_]{3,20}")) {
            throw new IllegalArgumentException(
                    "El usuario debe tener entre 3 y 20 caracteres. "
                            + "Solo letras, números y guion bajo.");
        }

        if (contrasena == null || contrasena.length < 8) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 8 caracteres.");
        }

        if (existeUsuario(nombre)) {
            throw new IllegalStateException(
                    "Ese nombre de usuario ya existe.");
        }

        String hash = generarHash(contrasena);

        Usuario usuario = new Usuario(
                UUID.randomUUID().toString(),
                nombre,
                hash
        );

        guardarUsuario(usuario);

        return usuario;
    }

    // Guarda el usuario en el archivo de texto
    private void guardarUsuario(Usuario usuario) throws IOException {

        Path carpeta = archivoUsuarios.toAbsolutePath().getParent();

        Files.createDirectories(carpeta);

        String registro = usuario.getIdUsuario()
                + "|" + usuario.getNombreUsuario()
                + "|" + usuario.getHashContrasena()
                + System.lineSeparator();

        Files.writeString(
                archivoUsuarios,
                registro,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    // Busca un usuario por su nombre
    public Usuario buscarUsuario(String nombreUsuario) throws IOException {

        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            return null;
        }

        if (!Files.exists(archivoUsuarios)) {
            return null;
        }

        for (String linea : Files.readAllLines(
                archivoUsuarios, StandardCharsets.UTF_8)) {

            String[] datos = linea.split("\\|", 3);

            if (datos.length != 3) {
                throw new IOException("Registro de usuario inválido.");
            }

            if (datos[1].equalsIgnoreCase(nombreUsuario.trim())) {
                return new Usuario(datos[0], datos[1], datos[2]);
            }
        }

        return null;
    }

    // Comprueba si el nombre de usuario ya está registrado
    public boolean existeUsuario(String nombreUsuario) throws IOException {
        return buscarUsuario(nombreUsuario) != null;
    }

    // Comprueba las credenciales para iniciar sesión
    public boolean autenticar(String nombreUsuario, char[] contrasena)
            throws IOException, GeneralSecurityException {

        Usuario usuario = buscarUsuario(nombreUsuario);

        if (usuario == null || contrasena == null) {
            return false;
        }

        return verificarHash(
                contrasena,
                usuario.getHashContrasena()
        );
    }

    // Genera un hash seguro para la contraseña
    private String generarHash(char[] contrasena)
            throws GeneralSecurityException {

        byte[] sal = new byte[LONGITUD_SAL];
        new SecureRandom().nextBytes(sal);

        byte[] hash = derivarHash(contrasena, sal, ITERACIONES);

        return ALGORITMO + "$" + ITERACIONES + "$"
                + Base64.getEncoder().encodeToString(sal) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    // Verifica si la contraseña coincide con el hash guardado
    private boolean verificarHash(char[] contrasena, String hashGuardado)
            throws GeneralSecurityException {

        String[] partes = hashGuardado.split("\\$");

        if (partes.length != 4 || !ALGORITMO.equals(partes[0])) {
            return false;
        }

        try {
            int iteraciones = Integer.parseInt(partes[1]);
            byte[] sal = Base64.getDecoder().decode(partes[2]);
            byte[] hashEsperado = Base64.getDecoder().decode(partes[3]);

            byte[] hashCalculado = derivarHash(
                    contrasena, sal, iteraciones
            );

            return MessageDigest.isEqual(hashEsperado, hashCalculado);

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Realiza el proceso PBKDF2
    private byte[] derivarHash(
            char[] contrasena, byte[] sal, int iteraciones)
            throws GeneralSecurityException {

        PBEKeySpec especificacion = new PBEKeySpec(
                contrasena, sal, iteraciones, LONGITUD_HASH
        );

        try {
            SecretKeyFactory fabrica =
                    SecretKeyFactory.getInstance(ALGORITMO);

            return fabrica.generateSecret(especificacion).getEncoded();

        } finally {
            especificacion.clearPassword();
        }
    }
}
