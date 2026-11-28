package idioma;

public class PruebaIdioma {

    public static void main(String[] args) {

        System.out.println("IDIOMA ESPAÑOL:");
        System.out.println(GestorIdioma.obtenerTexto("menu.titulo"));

        GestorIdioma.cambiarIdioma("en");

        System.out.println("\nIDIOMA INGLES:");
        System.out.println(GestorIdioma.obtenerTexto("menu.titulo"));

        GestorIdioma.cambiarIdioma("es");

        System.out.println("\nREGRESAMOS A ESPAÑOL:");
        System.out.println(GestorIdioma.obtenerTexto("menu.titulo"));
    }
}