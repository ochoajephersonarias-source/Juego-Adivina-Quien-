package logica;

import java.util.List;
import modelo.InteligenciaComputadora;
import modelo.JugadorHumano;
import modelo.Personaje;

public class Partida {
    private int turnoActual;
    private JugadorHumano jugadorHumano;
    private InteligenciaComputadora inteligenciaComputadora;
    private List<Personaje> personajes;

    public Partida(JugadorHumano jugadorHumano, InteligenciaComputadora inteligenciaComputadora,
                   List<Personaje> personajes) {
        this.turnoActual = 1;
        this.jugadorHumano = jugadorHumano;
        this.inteligenciaComputadora = inteligenciaComputadora;
        this.personajes = personajes;
    }

    public void iniciarPartida() {
        this.turnoActual = 1;
    }

    public boolean verificarGanador() {
        return false;
    }

    public int getTurnoActual() {
        return turnoActual;
    }
}
