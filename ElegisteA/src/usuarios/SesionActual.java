package usuarios;

public class SesionActual {

    private static String idUsuario;
    private static String nombreUsuario;

    // Evita crear objetos de esta clase
    private SesionActual() {
    }

    // Inicia la sesión del usuario
    public static synchronized void iniciarSesion(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "No se puede iniciar una sesión sin usuario."
            );
        }

        idUsuario = usuario.getIdUsuario();
        nombreUsuario = usuario.getNombreUsuario();
    }

    // Comprueba si existe una sesión activa
    public static synchronized boolean haySesionActiva() {
        return idUsuario != null;
    }

    // Obtiene el ID del usuario conectado
    public static synchronized String obtenerIdUsuario() {
        return idUsuario;
    }

    // Obtiene el nombre del usuario conectado
    public static synchronized String obtenerNombreUsuario() {
        return nombreUsuario;
    }

    // Cierra la sesión actual
    public static synchronized void cerrarSesion() {
        idUsuario = null;
        nombreUsuario = null;
    }
}