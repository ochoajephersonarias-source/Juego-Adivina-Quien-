package modelo;

import java.util.ArrayList;
import java.util.List;

public class InteligenciaComputadora extends Participante {
    private List<Personaje> candidatosRestantes;

    public InteligenciaComputadora(int idParticipante, String nombre, List<Personaje> personajesIniciales) {
        super(idParticipante, nombre);
        this.candidatosRestantes = new ArrayList<>(personajesIniciales);
    }

    @Override
    public void seleccionarPersonaje(Personaje personaje) {
        asignarPersonajeSecreto(personaje);
    }

    @Override
    public boolean responderPregunta(Pregunta pregunta) {
        // Evaluar según el personaje secreto asignado y la pregunta recibida
        return false;
    }

    public Pregunta formularPregunta() {
        // Seleccionar la siguiente pregunta en función de candidatosRestantes
        return null;
    }

    public boolean actualizarCandidatos(Pregunta pregunta, boolean respuesta) {
        if (pregunta == null) {
            return false;
        }
        // Filtrar candidatosRestantes según pregunta y respuesta
        return !candidatosRestantes.isEmpty();
    }

    public List<Personaje> getCandidatosRestantes() {
        return new ArrayList<>(candidatosRestantes);
    }
}