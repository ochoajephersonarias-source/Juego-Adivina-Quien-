package juego.vista;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


 // visualización de estadísticas históricas del jugador mediante JFreeChart


public class PanelEstadisticas extends JPanel {

    private final DefaultCategoryDataset dataset;
    private final JButton btnVolver;

    public PanelEstadisticas() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(248, 250, 252));

        dataset = new DefaultCategoryDataset();
        actualizarDatosGrafico(0, 0);

        JFreeChart graficoBarras = ChartFactory.createBarChart(
                "Historial de Partidas - Adivina Quién",
                "Resultado",
                "Cantidad de Partidas",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        ChartPanel panelGrafico = new ChartPanel(graficoBarras);
        panelGrafico.setMouseWheelEnabled(true);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setOpaque(false);

        btnVolver = new JButton("Volver al Juego");
        btnVolver.setActionCommand("VOLVER_DE_ESTADISTICAS");
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelInferior.add(btnVolver);

        add(panelGrafico, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }


     // controlador de partidas

    public void actualizarDatosGrafico(int partidasGanadas, int partidasPerdidas) {
        dataset.clear();
        dataset.addValue(Math.max(0, partidasGanadas), "Partidas", "Ganadas");
        dataset.addValue(Math.max(0, partidasPerdidas), "Partidas", "Perdidas");
    }

    public void agregarVolverListener(ActionListener listener) {
        btnVolver.addActionListener(listener);
    }
}