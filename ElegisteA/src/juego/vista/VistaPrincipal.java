package juego.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaPrincipal extends JFrame {

    public static final String VISTA_INICIO = "INICIO";
    public static final String VISTA_JUEGO = "JUEGO";
    public static final String VISTA_ESTADISTICAS = "ESTADISTICAS";

    private final CardLayout cardLayout;
    private final JPanel contenedorPrincipal;

    // Componentes Vista Inicio
    private JTextField txtNombreJugador;
    private JButton btnIniciarPartida;
    private JButton btnSalir;

    // Componentes Vista Juego
    private JLabel lblEstado;
    private JButton btnEstadisticas;
    private JButton btnNuevaPartida;
    private PanelTablero panelTablero;
    private JComboBox<String> comboPreguntas;
    private JButton btnPreguntar;
    private JLabel lblRespuesta;
    private JToggleButton btnModoArriesgar;
    private JButton btnConfirmarIdentidad;

    // Componentes Vista Estadísticas
    private PanelEstadisticas panelEstadisticas;
    private JButton btnVolverJuego;

    public VistaPrincipal() {
        setTitle("Adivina Quién - Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 740);
        setMinimumSize(new Dimension(960, 620));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedorPrincipal = new JPanel(cardLayout);

        contenedorPrincipal.add(crearPanelInicio(), VISTA_INICIO);
        contenedorPrincipal.add(crearPanelJuego(), VISTA_JUEGO);
        contenedorPrincipal.add(crearPanelEstadisticas(), VISTA_ESTADISTICAS);

        add(contenedorPrincipal);
        mostrarVista(VISTA_INICIO);
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(238, 238, 238));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 20, 8, 20);

        JLabel lblTitulo = new JLabel("ADIVINA QUIÉN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblTitulo.setForeground(new Color(34, 34, 34));
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 14, 20);
        panel.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("¡Ingresa tu nombre para comenzar!", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblSubtitulo.setForeground(new Color(51, 51, 51));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 26, 20);
        panel.add(lblSubtitulo, gbc);

        JPanel panelCampo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelCampo.setOpaque(false);
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setForeground(new Color(40, 40, 40));

        txtNombreJugador = new JTextField(22);
        txtNombreJugador.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtNombreJugador.setPreferredSize(new Dimension(260, 32));
        panelCampo.add(lblNombre);
        panelCampo.add(txtNombreJugador);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 24, 20);
        panel.add(panelCampo, gbc);

        btnIniciarPartida = new JButton("Iniciar Partida");
        btnIniciarPartida.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnIniciarPartida.setPreferredSize(new Dimension(380, 48));
        btnIniciarPartida.setFocusPainted(false);
        gbc.gridy = 3;
        gbc.insets = new Insets(4, 20, 28, 20);
        panel.add(btnIniciarPartida, gbc);

        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelSalir.setOpaque(false);
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSalir.setPreferredSize(new Dimension(95, 32));
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> System.exit(0));
        panelSalir.add(btnSalir);

        gbc.gridy = 4;
        gbc.insets = new Insets(10, 20, 10, 20);
        panel.add(panelSalir, gbc);

        return panel;
    }

    private JPanel crearPanelJuego() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(224, 229, 236));

        // Barra superior oscura
        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(new Color(27, 38, 54));
        barraSuperior.setBorder(new EmptyBorder(10, 16, 10, 16));

        lblEstado = new JLabel("Jugador: Invitado | Turno 1: Haz una pregunta o descarta cartas");
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblEstado.setForeground(Color.WHITE);
        barraSuperior.add(lblEstado, BorderLayout.WEST);

        JPanel botonesTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botonesTop.setOpaque(false);

        btnEstadisticas = new JButton("Estadísticas");
        btnEstadisticas.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnEstadisticas.setPreferredSize(new Dimension(120, 32));
        btnEstadisticas.setFocusPainted(false);

        btnNuevaPartida = new JButton("Nueva Partida");
        btnNuevaPartida.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnNuevaPartida.setPreferredSize(new Dimension(130, 32));
        btnNuevaPartida.setFocusPainted(false);

        botonesTop.add(btnEstadisticas);
        botonesTop.add(btnNuevaPartida);
        barraSuperior.add(botonesTop, BorderLayout.EAST);

        panel.add(barraSuperior, BorderLayout.NORTH);

        // Tablero central con scroll
        panelTablero = new PanelTablero();
        JScrollPane scrollPane = new JScrollPane(panelTablero);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(150, 165, 185)));
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Barra inferior clásica
        JPanel barraInferior = new JPanel(new BorderLayout(10, 0));
        barraInferior.setBackground(new Color(235, 238, 242));
        barraInferior.setBorder(new EmptyBorder(10, 14, 10, 14));

        JPanel zonaIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        zonaIzquierda.setOpaque(false);

        JLabel lblPregunta = new JLabel("Pregunta:");
        lblPregunta.setFont(new Font("SansSerif", Font.BOLD, 13));

        comboPreguntas = new JComboBox<>();
        comboPreguntas.setFont(new Font("SansSerif", Font.PLAIN, 13));
        comboPreguntas.setPreferredSize(new Dimension(280, 30));

        btnPreguntar = new JButton("Preguntar");
        btnPreguntar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnPreguntar.setPreferredSize(new Dimension(105, 30));
        btnPreguntar.setFocusPainted(false);

        lblRespuesta = new JLabel(" ");
        lblRespuesta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblRespuesta.setForeground(new Color(30, 58, 95));

        zonaIzquierda.add(lblPregunta);
        zonaIzquierda.add(comboPreguntas);
        zonaIzquierda.add(btnPreguntar);
        zonaIzquierda.add(lblRespuesta);

        JPanel zonaDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        zonaDerecha.setOpaque(false);

        btnModoArriesgar = new JToggleButton("Modo Seleccionar Identidad");
        btnModoArriesgar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnModoArriesgar.setPreferredSize(new Dimension(205, 30));
        btnModoArriesgar.setFocusPainted(false);

        btnConfirmarIdentidad = new JButton("¿Es este personaje?");
        btnConfirmarIdentidad.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnConfirmarIdentidad.setPreferredSize(new Dimension(165, 30));
        btnConfirmarIdentidad.setEnabled(false);
        btnConfirmarIdentidad.setFocusPainted(false);

        zonaDerecha.add(btnModoArriesgar);
        zonaDerecha.add(btnConfirmarIdentidad);

        barraInferior.add(zonaIzquierda, BorderLayout.WEST);
        barraInferior.add(zonaDerecha, BorderLayout.EAST);

        panel.add(barraInferior, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(238, 238, 238));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(27, 38, 54));
        header.setBorder(new EmptyBorder(10, 16, 10, 16));

        JLabel titulo = new JLabel("Estadísticas de Partidas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);

        btnVolverJuego = new JButton("Volver al Tablero");
        btnVolverJuego.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnVolverJuego.setFocusPainted(false);
        header.add(btnVolverJuego, BorderLayout.EAST);

        panel.add(header, BorderLayout.NORTH);

        panelEstadisticas = new PanelEstadisticas();
        panel.add(panelEstadisticas, BorderLayout.CENTER);
        return panel;
    }

    public void mostrarVista(String nombreVista) {
        cardLayout.show(contenedorPrincipal, nombreVista);
        if (VISTA_INICIO.equals(nombreVista)) {
            setTitle("Adivina Quién - Inicio");
        } else if (VISTA_JUEGO.equals(nombreVista)) {
            setTitle("Adivina Quién - Tablero");
        } else if (VISTA_ESTADISTICAS.equals(nombreVista)) {
            setTitle("Adivina Quién - Estadísticas");
        }
    }

    public PanelTablero getPanelTablero() {
        return panelTablero;
    }

    public PanelEstadisticas getPanelEstadisticas() {
        return panelEstadisticas;
    }

    public void cargarPreguntas(List<String> preguntas) {
        comboPreguntas.removeAllItems();
        for (String p : preguntas) {
            comboPreguntas.addItem(p);
        }
    }

    public String getPreguntaSeleccionada() {
        Object sel = comboPreguntas.getSelectedItem();
        return sel != null ? sel.toString() : "";
    }

    public String getNombreJugadorIngresado() {
        return txtNombreJugador.getText().trim();
    }

    public void setTextoEstado(String texto) {
        lblEstado.setText(texto);
    }

    public void setTextoRespuesta(String texto) {
        lblRespuesta.setText(texto);
    }

    public boolean isModoArriesgarActivo() {
        return btnModoArriesgar.isSelected();
    }

    public void setModoArriesgarActivo(boolean activo) {
        btnModoArriesgar.setSelected(activo);
    }

    public void setConfirmarIdentidadHabilitado(boolean habilitado) {
        btnConfirmarIdentidad.setEnabled(habilitado);
    }

    public void mostrarMensaje(String titulo, String mensaje, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public boolean confirmarAccion(String titulo, String mensaje) {
        int res = JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.YES_NO_OPTION);
        return res == JOptionPane.YES_OPTION;
    }

    public void agregarIniciarPartidaListener(ActionListener l) {
        btnIniciarPartida.addActionListener(l);
        txtNombreJugador.addActionListener(l);
    }

    public void agregarAbrirEstadisticasListener(ActionListener l) {
        btnEstadisticas.addActionListener(l);
    }

    public void agregarVolverEstadisticasListener(ActionListener l) {
        btnVolverJuego.addActionListener(l);
    }

    public void agregarTarjetasListener(ActionListener l) {
        panelTablero.setTarjetasListener(l);
    }

    public void agregarModoArriesgarListener(ActionListener l) {
        btnModoArriesgar.addActionListener(l);
    }

    public void agregarConfirmarIdentidadListener(ActionListener l) {
        btnConfirmarIdentidad.addActionListener(l);
    }

    public void agregarHacerPreguntaListener(ActionListener l) {
        btnPreguntar.addActionListener(l);
    }

    public void agregarNuevaPartidaListener(ActionListener l) {
        btnNuevaPartida.addActionListener(l);
    }
}