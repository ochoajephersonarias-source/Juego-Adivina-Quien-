package juego.vista;

import modelo.Personaje;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PanelTarjetaPersonaje extends JPanel {

    private final Personaje personaje;
    private boolean descartado = false;
    private boolean seleccionado = false;
    private final List<ActionListener> listeners = new ArrayList<>();

    private final Border bordeNormal;
    private final Border bordeSeleccionado;
    private final Border bordeDescartado;

    public PanelTarjetaPersonaje(Personaje personaje) {
        this.personaje = personaje;

        bordeNormal = new CompoundBorder(
                new LineBorder(new Color(122, 146, 178), 1),
                new CompoundBorder(
                        new LineBorder(Color.WHITE, 1),
                        new EmptyBorder(6, 6, 8, 6)
                )
        );
        bordeSeleccionado = new CompoundBorder(
                new LineBorder(new Color(29, 78, 216), 3),
                new EmptyBorder(5, 5, 7, 5)
        );
        bordeDescartado = new CompoundBorder(
                new LineBorder(new Color(148, 163, 184), 1),
                new EmptyBorder(7, 7, 9, 7)
        );

        setLayout(new BorderLayout(0, 4));
        setBorder(bordeNormal);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(250, 208));

        // Foto centrada
        JLabel lblImagen = new JLabel("", SwingConstants.CENTER);
        ImageIcon icono = cargarImagenEscalada(personaje.getRutaImagen(), 74, 74);
        if (icono != null) {
            lblImagen.setIcon(icono);
        } else {
            lblImagen.setText("?");
            lblImagen.setFont(new Font("SansSerif", Font.BOLD, 28));
            lblImagen.setForeground(new Color(100, 116, 139));
        }
        add(lblImagen, BorderLayout.NORTH);

        // Nombre + etiquetas de atributos con colores
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false);

        JLabel lblNombre = new JLabel(personaje.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNombre.setForeground(new Color(15, 23, 42));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(lblNombre);
        panelCentro.add(Box.createVerticalStrut(3));

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 1));
        fila1.setOpaque(false);
        fila1.add(crearEtiqueta("Ojos: " + personaje.getColorOjos(), new Color(219, 234, 254), new Color(30, 64, 175)));
        fila1.add(crearEtiqueta(personaje.getTipoCabello() + " " + personaje.getColorCabello(), new Color(243, 232, 255), new Color(107, 33, 168)));
        fila1.add(crearEtiqueta("Piel: " + personaje.getColorPiel(), new Color(254, 243, 199), new Color(146, 64, 14)));
        fila1.add(crearEtiqueta(personaje.getGenero(), new Color(254, 226, 226), new Color(153, 27, 27)));

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 1));
        fila2.setOpaque(false);
        fila2.add(crearEtiqueta(
                (personaje.isUsaLentes() ? "✓ " : "✗ ") + "Lentes",
                personaje.isUsaLentes() ? new Color(220, 252, 231) : new Color(241, 245, 249),
                personaje.isUsaLentes() ? new Color(22, 101, 52) : new Color(100, 116, 139)
        ));
        fila2.add(crearEtiqueta(
                (personaje.isTieneSonrisa() ? "✓ " : "✗ ") + "Sonrisa",
                personaje.isTieneSonrisa() ? new Color(220, 252, 231) : new Color(241, 245, 249),
                personaje.isTieneSonrisa() ? new Color(22, 101, 52) : new Color(100, 116, 139)
        ));
        fila2.add(crearEtiqueta(
                (personaje.isTieneBarba() ? "✓ " : "✗ ") + "Barba",
                personaje.isTieneBarba() ? new Color(220, 252, 231) : new Color(241, 245, 249),
                personaje.isTieneBarba() ? new Color(22, 101, 52) : new Color(100, 116, 139)
        ));
        fila2.add(crearEtiqueta("Acc: " + personaje.getAccesorios(), new Color(237, 233, 254), new Color(91, 33, 182)));

        panelCentro.add(fila1);
        panelCentro.add(fila2);
        add(panelCentro, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notificarClic();
            }
        });
    }

    private JLabel crearEtiqueta(String texto, Color fondo, Color colorTexto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 9));
        lbl.setForeground(colorTexto);
        lbl.setOpaque(true);
        lbl.setBackground(fondo);
        lbl.setBorder(new CompoundBorder(
                new LineBorder(colorTexto.brighter(), 1),
                new EmptyBorder(1, 3, 1, 3)
        ));
        return lbl;
    }

    private ImageIcon cargarImagenEscalada(String ruta, int ancho, int alto) {
        try {
            String rutaLimpia = ruta.startsWith("/") ? ruta : "/" + ruta;
            URL url = getClass().getResource(rutaLimpia);
            if (url == null) return null;
            Image img = new ImageIcon(url).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        Color arriba = descartado ? new Color(203, 213, 225) : new Color(252, 254, 255);
        Color abajo = descartado ? new Color(180, 190, 204) : new Color(213, 226, 242);
        GradientPaint gradiente = new GradientPaint(0, 0, arriba, 0, getHeight(), abajo);
        g2.setPaint(gradiente);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (descartado) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(100, 116, 139, 135));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(new Color(185, 28, 28, 190));
            g2.setStroke(new BasicStroke(3f));
            g2.drawLine(14, 14, getWidth() - 14, getHeight() - 14);
            g2.drawLine(getWidth() - 14, 14, 14, getHeight() - 14);
            g2.dispose();
        }
    }

    public int getIdPersonaje() {
        return personaje.getIdPersonaje();
    }

    public String getNombrePersonaje() {
        return personaje.getNombre();
    }

    public boolean isDescartado() {
        return descartado;
    }

    public void setDescartado(boolean descartado) {
        this.descartado = descartado;
        actualizarBorde();
        repaint();
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
        actualizarBorde();
        repaint();
    }

    private void actualizarBorde() {
        if (seleccionado) {
            setBorder(bordeSeleccionado);
        } else if (descartado) {
            setBorder(bordeDescartado);
        } else {
            setBorder(bordeNormal);
        }
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    private void notificarClic() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PERSONAJE_" + personaje.getIdPersonaje());
        for (ActionListener l : listeners) {
            l.actionPerformed(event);
        }
    }
}