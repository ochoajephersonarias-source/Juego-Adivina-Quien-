import juego.vista.PanelTarjetaPersonaje;
import juego.vista.VistaPrincipal;
import modelo.Personaje;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static int idSeleccionado = -1;

    public static void main(String[] args) {
        try {
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            VistaPrincipal vista = new VistaPrincipal();

            List<Personaje> personajes = crearPersonajesDePrueba();
            vista.getPanelTablero().inicializarTablero(personajes);

            vista.cargarPreguntas(Arrays.asList(
                    "¿Es mujer?",
                    "¿Es hombre?",
                    "¿Tiene los ojos azules?",
                    "¿Tiene los ojos café?",
                    "¿Tiene los ojos verdes?",
                    "¿Tiene los ojos grises?",
                    "¿Tiene el cabello lacio?",
                    "¿Tiene el cabello ondulado?",
                    "¿Tiene el cabello rizado?",
                    "¿Es calvo/a?",
                    "¿Tiene el cabello rubio?",
                    "¿Tiene el cabello negro?",
                    "¿Tiene el cabello castaño?",
                    "¿Tiene el cabello pelirrojo?",
                    "¿Tiene el cabello canoso?",
                    "¿Tiene piel clara?",
                    "¿Tiene piel media?",
                    "¿Tiene piel morena?",
                    "¿Tiene piel oscura?",
                    "¿Usa lentes?",
                    "¿Tiene sonrisa?",
                    "¿Tiene barba?",
                    "¿Usa algún accesorio?",
                    "¿Usa aretes?",
                    "¿Usa sombrero?",
                    "¿Usa collar?",
                    "¿Usa corbata?",
                    "¿Usa diadema?",
                    "¿Usa pañuelo?",
                    "¿Usa bufanda?",
                    "¿Usa hebilla?",
                    "¿Usa pendientes?",
                    "¿Usa gorra?",
                    "¿Usa lazo?"
            ));
            vista.getPanelEstadisticas().actualizarDatosGrafico(5, 2);

            vista.agregarIniciarPartidaListener(e -> {
                String nombre = vista.getNombreJugadorIngresado();
                if (nombre.isEmpty()) {
                    vista.mostrarMensaje("Validación", "Ingresa tu nombre para iniciar.", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                vista.setTextoEstado("Jugador: " + nombre + " | Turno 1: Haz una pregunta o descarta cartas");
                vista.mostrarVista(VistaPrincipal.VISTA_JUEGO);
            });

            vista.agregarAbrirEstadisticasListener(e -> vista.mostrarVista(VistaPrincipal.VISTA_ESTADISTICAS));
            vista.agregarVolverEstadisticasListener(e -> vista.mostrarVista(VistaPrincipal.VISTA_JUEGO));

            vista.agregarTarjetasListener(e -> {
                int id = Integer.parseInt(e.getActionCommand().replace("PERSONAJE_", ""));
                PanelTarjetaPersonaje tarjeta = null;
                for (PanelTarjetaPersonaje t : vista.getPanelTablero().getTarjetas()) {
                    if (t.getIdPersonaje() == id) {
                        tarjeta = t;
                        break;
                    }
                }
                if (tarjeta == null) return;

                if (vista.isModoArriesgarActivo()) {
                    if (!tarjeta.isDescartado()) {
                        idSeleccionado = id;
                        vista.getPanelTablero().marcarTarjetaSeleccionada(id);
                        vista.setConfirmarIdentidadHabilitado(true);
                        vista.setTextoRespuesta("Seleccionado para arriesgar: " + tarjeta.getNombrePersonaje());
                    }
                } else {
                    vista.getPanelTablero().actualizarEstadoDescarte(id, !tarjeta.isDescartado());
                }
            });

            vista.agregarModoArriesgarListener(e -> {
                if (!vista.isModoArriesgarActivo()) {
                    idSeleccionado = -1;
                    vista.getPanelTablero().marcarTarjetaSeleccionada(-1);
                    vista.setConfirmarIdentidadHabilitado(false);
                    vista.setTextoRespuesta(" ");
                } else {
                    vista.setTextoRespuesta("Haz clic sobre el personaje que crees que es el secreto.");
                }
            });

            vista.agregarConfirmarIdentidadListener(e -> {
                if (idSeleccionado != -1) {
                    for (PanelTarjetaPersonaje t : vista.getPanelTablero().getTarjetas()) {
                        if (t.getIdPersonaje() == idSeleccionado) {
                            if (vista.confirmarAccion("Confirmar Identidad", "¿Declarar a " + t.getNombrePersonaje() + "?")) {
                                vista.mostrarMensaje("Prueba completada", "Declaraste a: " + t.getNombrePersonaje(), JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        }
                    }
                }
            });

            vista.agregarHacerPreguntaListener(e ->
                    vista.setTextoRespuesta("Pregunta: \"" + vista.getPreguntaSeleccionada() + "\" -> Respuesta de prueba: Sí"));

            vista.agregarNuevaPartidaListener(e -> {
                vista.getPanelTablero().reiniciarVisualizacion();
                vista.setModoArriesgarActivo(false);
                vista.setConfirmarIdentidadHabilitado(false);
                idSeleccionado = -1;
                vista.setTextoRespuesta("Tablero reiniciado.");
            });

            vista.setVisible(true);
        });
    }

    private static List<Personaje> crearPersonajesDePrueba() {
        List<Personaje> lista = new ArrayList<>();
        String carpeta = "imagenes/";

        lista.add(new Personaje(1, "Abril", "Azul", "Ondulado", "Rubio", "Aretes", true, true, false, carpeta + "Abril01.jpg", "Clara", "Mujer"));
        lista.add(new Personaje(2, "Bruno", "Café", "Rizado", "Negro", "Sombrero", false, false, true, carpeta + "Bruno02.jpg", "Morena", "Hombre"));
        lista.add(new Personaje(3, "Camila", "Verde", "Lacio", "Castaño", "Collar", true, true, false, carpeta + "camila03.jpg", "Media", "Mujer"));
        lista.add(new Personaje(4, "Darío", "Gris", "Calvo", "Ninguno", "Corbata", false, false, true, carpeta + "Dario04.jpg", "Oscura", "Hombre"));
        lista.add(new Personaje(5, "Emilia", "Azul", "Rizado", "Pelirrojo", "Diadema", true, true, false, carpeta + "Emilia05.jpg", "Clara", "Mujer"));
        lista.add(new Personaje(6, "Fabio", "Café", "Lacio", "Negro", "Ninguno", true, false, false, carpeta + "Fabio06.jpg", "Oscura", "Hombre"));
        lista.add(new Personaje(7, "Gabriela", "Verde", "Ondulado", "Rubio", "Pañuelo", false, true, false, carpeta + "Gabriela07.jpg", "Media", "Mujer"));
        lista.add(new Personaje(8, "Héctor", "Gris", "Calvo", "Ninguno", "Bufanda", true, false, true, carpeta + "Hector08.jpg", "Morena", "Hombre"));
        lista.add(new Personaje(9, "Inés", "Azul", "Lacio", "Negro", "Hebilla", true, true, false, carpeta + "Ines09.jpg", "Clara", "Mujer"));
        lista.add(new Personaje(10, "Joaquín", "Café", "Ondulado", "Canoso", "Ninguno", false, false, false, carpeta + "Joaquin10.jpg", "Media", "Hombre"));
        lista.add(new Personaje(11, "Kiara", "Verde", "Rizado", "Castaño", "Pendientes", true, true, false, carpeta + "Kiara11.jpg", "Morena", "Mujer"));
        lista.add(new Personaje(12, "Leandro", "Gris", "Calvo", "Ninguno", "Gorra", false, false, true, carpeta + "Leandro12.jpg", "Oscura", "Hombre"));
        lista.add(new Personaje(13, "Mónica", "Azul", "Ondulado", "Pelirrojo", "Aretes", true, false, false, carpeta + "Monica13.jpg", "Media", "Mujer"));
        lista.add(new Personaje(14, "Nicolás", "Café", "Lacio", "Rubio", "Ninguno", false, true, false, carpeta + "Nicolas14.jpg", "Clara", "Hombre"));
        lista.add(new Personaje(15, "Óscar", "Verde", "Calvo", "Ninguno", "Ninguno", false, false, false, carpeta + "Oscar15.jpg", "Oscura", "Hombre"));
        lista.add(new Personaje(16, "Pablo", "Gris", "Rizado", "Negro", "Bufanda y corbata", true, true, true, carpeta + "Pablo16.jpg", "Morena", "Hombre"));
        lista.add(new Personaje(17, "Renata", "Azul", "Lacio", "Castaño", "Sombrero", false, false, false, carpeta + "Renata17.jpg", "Oscura", "Mujer"));
        lista.add(new Personaje(18, "Samuel", "Café", "Rizado", "Canoso", "Ninguno", true, true, false, carpeta + "Samuel18.jpg", "Media", "Hombre"));
        lista.add(new Personaje(19, "Teresa", "Verde", "Ondulado", "Pelirrojo", "Lazo", false, false, false, carpeta + "Teresa19.jpg", "Clara", "Mujer"));
        lista.add(new Personaje(20, "Ulises", "Gris", "Lacio", "Canoso", "Corbata", false, true, false, carpeta + "Ulises20.jpg", "Morena", "Hombre"));
        lista.add(new Personaje(21, "Valeria", "Azul", "Ondulado", "Rubio", "Ninguno", true, false, false, carpeta + "Valeria21.jpg", "Oscura", "Mujer"));
        lista.add(new Personaje(22, "William", "Café", "Calvo", "Ninguno", "Gorra", false, true, true, carpeta + "William21.jpg", "Media", "Hombre"));
        lista.add(new Personaje(23, "Ximena", "Verde", "Rizado", "Castaño", "Collar", true, false, false, carpeta + "Ximena23.jpg", "Clara", "Mujer"));
        lista.add(new Personaje(24, "Yadiel", "Gris", "Calvo", "Ninguno", "Ninguno", false, true, false, carpeta + "Yadiel24.jpg", "Morena", "Hombre"));

        return lista;
    }
}