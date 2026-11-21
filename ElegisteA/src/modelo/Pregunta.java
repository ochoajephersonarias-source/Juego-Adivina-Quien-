package modelo;

public class Pregunta {
    private String categoria;
    private String valorEsperado;
    private String textoPregunta;

    public Pregunta(String categoria, String valorEsperado, String textoPregunta) {
        this.categoria = categoria;
        this.valorEsperado = valorEsperado;
        this.textoPregunta = textoPregunta;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getValorEsperado() {
        return valorEsperado;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }
}