package modelo;

import java.util.ArrayList;
import java.util.List;

public class JugadorHumano extends Participante {

    public JugadorHumano(int idParticipante, String nombre) {
        super(idParticipante, nombre);
    }

    @Override
    public void seleccionarPersonaje(Personaje personaje) {
        asignarPersonajeSecreto(personaje);
    }

    @Override
    public boolean responderPregunta(Pregunta pregunta) {
        // Evaluar según el personaje secreto asignado y la categoría/valorEsperado de la pregunta
        return false;
    }

    public boolean declararIdentidad(Personaje objetivo) {
        if (objetivo == null || getPersonajeSecreto() == null) {
            return false;
        }
        return getPersonajeSecreto().esCandidatoValido(objetivo);
    }
}