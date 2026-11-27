package persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Abre la base SQLite y la inicializa con el esquema y los datos base la primera vez. */
public final class ConexionSQLite {
    private static final String DEFAULT_PATH = "data/adivina-quien.db";
    private static volatile boolean inicializada;

    private ConexionSQLite() { }

    public static Connection abrir() throws SQLException {
        String path = System.getProperty("adivina.db.path", DEFAULT_PATH);
        Path archivo = Path.of(path).toAbsolutePath();
        try {
            Path directorio = archivo.getParent();
            if (directorio != null) Files.createDirectories(directorio);
        } catch (IOException e) {
            throw new SQLException("No se pudo crear el directorio de la base de datos: " + archivo, e);
        }

        Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + archivo);
        try (Statement st = conexion.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
            st.execute("PRAGMA busy_timeout = 5000");
        }
        inicializarUnaVez(conexion);
        return conexion;
    }

    private static void inicializarUnaVez(Connection conexion) throws SQLException {
        if (inicializada) return;
        synchronized (ConexionSQLite.class) {
            if (inicializada) return;
            ejecutarRecurso(conexion, "/db/schema.sql");
            ejecutarRecurso(conexion, "/db/seed.sql");
            inicializada = true;
        }
    }

    private static void ejecutarRecurso(Connection conexion, String recurso) throws SQLException {
        try (InputStream in = ConexionSQLite.class.getResourceAsStream(recurso)) {
            if (in == null) throw new SQLException("No se encontró el recurso " + recurso);
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            try (Statement st = conexion.createStatement()) {
                for (String sentencia : sql.split(";")) {
                    if (!sentencia.isBlank()) st.execute(sentencia);
                }
            }
        } catch (IOException e) {
            throw new SQLException("No se pudo leer el recurso " + recurso, e);
        }
    }
}
