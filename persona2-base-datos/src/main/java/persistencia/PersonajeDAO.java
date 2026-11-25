package persistencia;

import modelo.Personaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Acceso a personajes; la lógica del juego no necesita escribir SQL. */
public class PersonajeDAO {
    private static final String SELECT_PERSONAJES = """
            SELECT p.id_personaje, p.nombre, p.color_ojos, p.tipo_cabello,
                   p.color_cabello, p.color_piel, p.usa_lentes, p.tiene_sonrisa,
                   p.tiene_barba, p.ruta_imagen,
                   COALESCE((SELECT group_concat(nombre, ', ')
                             FROM (SELECT a.nombre AS nombre
                                   FROM personaje_accesorio pa
                                   JOIN accesorios a ON a.id_accesorio = pa.id_accesorio
                                   WHERE pa.id_personaje = p.id_personaje
                                   ORDER BY a.nombre COLLATE NOCASE)), '') AS accesorios
            FROM personajes p
            """;

    public List<Personaje> obtenerTodos() throws SQLException {
        String sql = SELECT_PERSONAJES + " ORDER BY p.id_personaje";
        try (Connection c = ConexionSQLite.abrir();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Personaje> resultado = new ArrayList<>();
            while (rs.next()) resultado.add(mapear(rs));
            return resultado;
        }
    }

    /** Devuelve hasta 24 personajes distintos, elegidos aleatoriamente. */
    public List<Personaje> obtener24Aleatorios() throws SQLException {
        String sql = SELECT_PERSONAJES + " ORDER BY random() LIMIT 24";
        try (Connection c = ConexionSQLite.abrir();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Personaje> resultado = new ArrayList<>();
            while (rs.next()) resultado.add(mapear(rs));
            return resultado;
        }
    }

    public Personaje buscarPorId(int id) throws SQLException {
        try (Connection c = ConexionSQLite.abrir();
             PreparedStatement ps = c.prepareStatement(SELECT_PERSONAJES + " WHERE p.id_personaje = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /** Las características normalizadas (incluidos todos los accesorios) vienen en el Personaje. */
    public Personaje consultarCaracteristicas(int id) throws SQLException {
        return buscarPorId(id);
    }

    public int insertar(Personaje personaje) throws SQLException {
        String sql = """
                INSERT INTO personajes
                (nombre, color_ojos, tipo_cabello, color_cabello, color_piel,
                 usa_lentes, tiene_sonrisa, tiene_barba, ruta_imagen)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection c = ConexionSQLite.abrir()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    bind(ps, personaje);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) throw new SQLException("SQLite no devolvió el ID del personaje insertado");
                        id = keys.getInt(1);
                    }
                }
                guardarAccesorios(c, id, personaje.getAccesorios());
                c.commit();
                return id;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public boolean actualizar(Personaje personaje) throws SQLException {
        String sql = """
                UPDATE personajes SET nombre=?, color_ojos=?, tipo_cabello=?, color_cabello=?,
                color_piel=?, usa_lentes=?, tiene_sonrisa=?, tiene_barba=?, ruta_imagen=?
                WHERE id_personaje=?
                """;
        try (Connection c = ConexionSQLite.abrir()) {
            c.setAutoCommit(false);
            try {
                int filas;
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    bind(ps, personaje);
                    ps.setInt(10, personaje.getIdPersonaje());
                    filas = ps.executeUpdate();
                }
                if (filas == 0) {
                    c.rollback();
                    return false;
                }
                try (PreparedStatement ps = c.prepareStatement("DELETE FROM personaje_accesorio WHERE id_personaje=?")) {
                    ps.setInt(1, personaje.getIdPersonaje());
                    ps.executeUpdate();
                }
                guardarAccesorios(c, personaje.getIdPersonaje(), personaje.getAccesorios());
                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public boolean eliminar(int id) throws SQLException {
        try (Connection c = ConexionSQLite.abrir();
             PreparedStatement ps = c.prepareStatement("DELETE FROM personajes WHERE id_personaje=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private static void bind(PreparedStatement ps, Personaje p) throws SQLException {
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getColorOjos());
        ps.setString(3, p.getTipoCabello());
        ps.setString(4, p.getColorCabello());
        ps.setString(5, p.getColorPiel());
        ps.setInt(6, p.isUsaLentes() ? 1 : 0);
        ps.setInt(7, p.isTieneSonrisa() ? 1 : 0);
        ps.setInt(8, p.isTieneBarba() ? 1 : 0);
        ps.setString(9, p.getRutaImagen());
    }

    private static void guardarAccesorios(Connection c, int id, String accesorios) throws SQLException {
        if (accesorios == null || accesorios.isBlank()) return;
        String buscar = "INSERT OR IGNORE INTO accesorios(nombre) VALUES (?)";
        String relacion = "INSERT INTO personaje_accesorio(id_personaje, id_accesorio) "
                + "SELECT ?, id_accesorio FROM accesorios WHERE nombre = ? COLLATE NOCASE";
        for (String valor : accesorios.split("[,;]")) {
            String nombre = valor.trim();
            if (nombre.isEmpty()) continue;
            try (PreparedStatement ps = c.prepareStatement(buscar)) {
                ps.setString(1, nombre);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = c.prepareStatement(relacion)) {
                ps.setInt(1, id);
                ps.setString(2, nombre);
                ps.executeUpdate();
            }
        }
    }

    private static Personaje mapear(ResultSet rs) throws SQLException {
        return new Personaje(rs.getInt("id_personaje"), rs.getString("nombre"),
                rs.getString("color_ojos"), rs.getString("tipo_cabello"),
                rs.getString("color_cabello"), rs.getString("accesorios"),
                rs.getInt("usa_lentes") == 1, rs.getInt("tiene_sonrisa") == 1,
                rs.getInt("tiene_barba") == 1, rs.getString("ruta_imagen"),
                rs.getString("color_piel"));
    }
}
