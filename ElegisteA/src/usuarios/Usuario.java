package usuarios;

public class Usuario {

    private final String idUsuario;
    private final String nombreUsuario;
    private final String hashContrasena;

    public Usuario(String idUsuario, String nombreUsuario, String hashContrasena) {

        if (idUsuario == null || idUsuario.isBlank()) {
            throw new IllegalArgumentException("El ID es obligatorio");
        }

        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        if (hashContrasena == null || hashContrasena.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario.trim();
        this.hashContrasena = hashContrasena;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getHashContrasena() {
        return hashContrasena;
    }
}