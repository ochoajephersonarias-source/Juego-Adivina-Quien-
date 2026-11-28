package idioma;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class GestorIdioma {

    private static String idiomaActual = "es";

    private static ResourceBundle textos =
            ResourceBundle.getBundle("idioma.mensajes", new Locale("es"));

    private GestorIdioma() {
    }

    public static void cambiarIdioma(String idioma) {

        if (idioma == null ||
                (!idioma.equals("es") && !idioma.equals("en"))) {
            throw new IllegalArgumentException("Idioma no válido.");
        }

        Locale locale = new Locale(idioma);

        textos = ResourceBundle.getBundle("idioma.mensajes", locale);
        idiomaActual = idioma;
    }

    public static String obtenerTexto(String clave) {

        try {
            return textos.getString(clave);
        } catch (MissingResourceException e) {
            return "!" + clave + "!";
        }
    }

    public static String obtenerIdioma() {
        return idiomaActual;
    }
}