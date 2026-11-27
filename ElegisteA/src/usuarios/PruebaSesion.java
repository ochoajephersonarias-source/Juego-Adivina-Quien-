
package usuarios;

public class PruebaSesion {

    public static void main(String[] args) {

        Usuario jugador = new Usuario(
                "id-prueba-001",
                "jugador_prueba",
                "hash-ficticio"
        );

        // 1. Comprobar que no hay sesión activa
        System.out.println("1. Sesion inicialmente cerrada: "
                + !SesionActual.haySesionActiva());

        // 2. Iniciar sesión
        SesionActual.iniciarSesion(jugador);

        System.out.println("2. Sesion iniciada: "
                + SesionActual.haySesionActiva());

        // 3. Comprobar el ID
        System.out.println("3. ID correcto: "
                + "id-prueba-001".equals(
                SesionActual.obtenerIdUsuario()));

        // 4. Comprobar el nombre
        System.out.println("4. Nombre correcto: "
                + "jugador_prueba".equals(
                SesionActual.obtenerNombreUsuario()));

        // 5. Cerrar sesión
        SesionActual.cerrarSesion();
        System.out.println("5. Sesion cerrada: "
                + !SesionActual.haySesionActiva());
    }
}
