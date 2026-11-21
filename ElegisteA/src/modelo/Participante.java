package modelo;

public abstract class Participante {
    private int idParticipante;
    private String nombre;
    private Personaje personajeSecreto;

    public Participante(int idParticipante, String nombre) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
    }

    public abstract void seleccionarPersonaje(Personaje personaje);

    public abstract boolean responderPregunta(Pregunta pregunta);

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public Personaje getPersonajeSecreto() {
        return personajeSecreto;
    }

    protected void asignarPersonajeSecreto(Personaje personajeSecreto) {
        this.personajeSecreto = personajeSecreto;
    }
}