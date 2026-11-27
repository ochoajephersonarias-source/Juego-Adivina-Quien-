package modelo;

public record PartidaHistorial(
        int idPartida,
        String fechaFin,
        int turnos,
        String personajeJugador,
        String personajeComputadora,
        ResultadoPartida resultado
) {
}