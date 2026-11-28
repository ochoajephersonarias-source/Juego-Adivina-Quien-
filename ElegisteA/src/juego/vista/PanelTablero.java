package juego.vista;

import modelo.Personaje;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelTablero extends JPanel {

    private final List<PanelTarjetaPersonaje> tarjetas;
    private ActionListener tarjetasListener;

    public PanelTablero() {
        this.tarjetas = new ArrayList<>();
        setLayout(new GridLayout(0, 4, 14, 14));
        setBackground(new Color(224, 229, 236));
        setBorder(new EmptyBorder(14, 16, 14, 16));
    }

    public void inicializarTablero(List<Personaje> personajes) {
        removeAll();
        tarjetas.clear();

        for (Personaje p : personajes) {
            PanelTarjetaPersonaje tarjeta = new PanelTarjetaPersonaje(p);
            if (tarjetasListener != null) {
                tarjeta.addActionListener(tarjetasListener);
            }
            tarjetas.add(tarjeta);
            add(tarjeta);
        }

        revalidate();
        repaint();
    }

    public void setTarjetasListener(ActionListener listener) {
        this.tarjetasListener = listener;
        for (PanelTarjetaPersonaje t : tarjetas) {
            t.addActionListener(listener);
        }
    }

    public List<PanelTarjetaPersonaje> getTarjetas() {
        return tarjetas;
    }

    public void actualizarEstadoDescarte(int idPersonaje, boolean descartado) {
        for (PanelTarjetaPersonaje t : tarjetas) {
            if (t.getIdPersonaje() == idPersonaje) {
                t.setDescartado(descartado);
                break;
            }
        }
    }

    public void marcarTarjetaSeleccionada(int idPersonaje) {
        for (PanelTarjetaPersonaje t : tarjetas) {
            t.setSeleccionado(t.getIdPersonaje() == idPersonaje);
        }
    }

    public void reiniciarVisualizacion() {
        for (PanelTarjetaPersonaje t : tarjetas) {
            t.setDescartado(false);
            t.setSeleccionado(false);
        }
    }
}