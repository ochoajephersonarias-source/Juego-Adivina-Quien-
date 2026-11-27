
package usuarios;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class PruebaUsuarios {

    public static void main(String[] args) throws Exception {

        // Creamos un archivo temporal para las pruebas
        Path carpeta = Files.createTempDirectory("prueba-usuarios-");
        Path archivo = carpeta.resolve("usuarios.txt");

        GestorUsuarios gestor = new GestorUsuarios(archivo);

        String nombre = "jugador_prueba";
        char[] contrasena = "ClavePrueba123!".toCharArray();

        try {

            // 1. Probar el registro
            Usuario usuario = gestor.registrarUsuario(nombre, contrasena);

            System.out.println("1. Registro: " +
                    (usuario != null ? "CORRECTO" : "ERROR"));

            // 2. Probar la búsqueda
            System.out.println("2. Buscar usuario: " +
                    (gestor.buscarUsuario(nombre) != null
                            ? "CORRECTO" : "ERROR"));

            // 3. Probar inicio de sesión correcto
            System.out.println("3. Login correcto: " +
                    gestor.autenticar(nombre, contrasena));

            // 4. Probar contraseña incorrecta
            char[] incorrecta = "OtraClave123!".toCharArray();

            try {
                System.out.println("4. Rechazar contraseña incorrecta: " +
                        !gestor.autenticar(nombre, incorrecta));
            } finally {
                Arrays.fill(incorrecta, '\0');
            }

            // 5. Probar usuarios duplicados
            boolean duplicadoRechazado = false;

            try {
                gestor.registrarUsuario(nombre, contrasena);
            } catch (IllegalStateException e) {
                duplicadoRechazado = true;
            }

            System.out.println("5. Rechazar usuario duplicado: " +
                    duplicadoRechazado);

        } finally {
            Arrays.fill(contrasena, '\0');
            Files.deleteIfExists(archivo);
            Files.deleteIfExists(carpeta);
        }
    }
}

