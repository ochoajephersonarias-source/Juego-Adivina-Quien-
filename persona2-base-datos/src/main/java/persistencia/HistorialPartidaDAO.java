package persistencia;

import modelo.PartidaHistorial;
import modelo.ResultadoPartida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Registra partidas finalizadas y consulta el historial de un jugador.
 */
public class HistorialPartidaDAO {

    /**
     * Guarda una partida terminada y los dos participantes en una transacción.
     *
     * @return el identificador generado para la partida
     */
    public int registrarPartidaFinalizada(
            String nombreJugador,
            int idPersonajeJugador,
            int idPersonajeComputadora,
            ResultadoPartida resultado,
            int turnos
    ) throws SQLException {

        if (nombreJugador == null || nombreJugador.isBlank()) {
            throw new IllegalArgumentException("El nombre del jugador es obligatorio.");
        }
        if (idPersonajeJugador <= 0 || idPersonajeComputadora <= 0) {
            throw new IllegalArgumentException("Los personajes deben tener IDs válidos.");
        }
        if (resultado == null) {
            throw new IllegalArgumentException("El resultado es obligatorio.");
        }
        if (turnos < 0) {
            throw new IllegalArgumentException("Los turnos no pueden ser negativos.");
        }

        String fecha = Instant.now().toString();

        String insertarPartida = """
                INSERT INTO partidas (fecha_inicio, fecha_fin, estado, turnos)
                VALUES (?, ?, 'FINALIZADA', ?)
                """;

        String insertarParticipante = """
                INSERT INTO participantes_partida
                    (id_partida, rol, nombre_mostrado, id_personaje_secreto, resultado)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionSQLite.abrir()) {
            conexion.setAutoCommit(false);

            try {
                int idPartida;

                try (PreparedStatement ps = conexion.prepareStatement(insertarPartida)) {
                    ps.setString(1, fecha);
                    ps.setString(2, fecha);
                    ps.setInt(3, turnos);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps =
                             conexion.prepareStatement("SELECT last_insert_rowid()")) {
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("No se pudo obtener el ID de la partida.");
                        }
                        idPartida = rs.getInt(1);
                    }
                }

                guardarParticipante(
                        conexion,
                        insertarParticipante,
                        idPartida,
                        "HUMANO",
                        nombreJugador,
                        idPersonajeJugador,
                        resultado
                );

                guardarParticipante(
                        conexion,
                        insertarParticipante,
                        idPartida,
                        "COMPUTADORA",
                        "Computadora",
                        idPersonajeComputadora,
                        resultadoComputadora(resultado)
                );

                conexion.commit();
                return idPartida;

            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }
        }
    }

    /**
     * Recupera las partidas finalizadas de un jugador, de la más reciente a la más antigua.
     */
    public List<PartidaHistorial> obtenerHistorial(String nombreJugador) throws SQLException {
        if (nombreJugador == null || nombreJugador.isBlank()) {
            throw new IllegalArgumentException("El nombre del jugador es obligatorio.");
        }

        String sql = """
                SELECT j.id_partida,
                       j.fecha_fin,
                       j.turnos,
                       pj.nombre AS personaje_jugador,
                       pc.nombre AS personaje_computadora,
                       h.resultado
                FROM partidas j
                JOIN participantes_partida h
                  ON h.id_partida = j.id_partida AND h.rol = 'HUMANO'
                JOIN participantes_partida c
                  ON c.id_partida = j.id_partida AND c.rol = 'COMPUTADORA'
                LEFT JOIN personajes pj
                  ON pj.id_personaje = h.id_personaje_secreto
                LEFT JOIN personajes pc
                  ON pc.id_personaje = c.id_personaje_secreto
                WHERE j.estado = 'FINALIZADA'
                  AND h.nombre_mostrado = ? COLLATE NOCASE
                ORDER BY j.fecha_fin DESC
                """;

        List<PartidaHistorial> historial = new ArrayList<>();

        try (Connection conexion = ConexionSQLite.abrir();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombreJugador);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historial.add(new PartidaHistorial(
                            rs.getInt("id_partida"),
                            rs.getString("fecha_fin"),
                            rs.getInt("turnos"),
                            rs.getString("personaje_jugador"),
                            rs.getString("personaje_computadora"),
                            ResultadoPartida.valueOf(rs.getString("resultado"))
                    ));
                }
            }
        }

        return historial;
    }

    private void guardarParticipante(
            Connection conexion,
            String sql,
            int idPartida,
            String rol,
            String nombre,
            int idPersonaje,
            ResultadoPartida resultado
    ) throws SQLException {

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPartida);
            ps.setString(2, rol);
            ps.setString(3, nombre);
            ps.setInt(4, idPersonaje);
            ps.setString(5, resultado.name());
            ps.executeUpdate();
        }
    }

    private ResultadoPartida resultadoComputadora(ResultadoPartida resultadoJugador) {
        return switch (resultadoJugador) {
            case VICTORIA -> ResultadoPartida.DERROTA;
            case DERROTA -> ResultadoPartida.VICTORIA;
            case EMPATE -> ResultadoPartida.EMPATE;
        };
    }
}