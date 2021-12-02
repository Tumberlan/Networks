package view;

import model.gettingobjects.Weather;
import view.utils.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class WeatherPanel {

    private int height;
    private int width;
    private int xCoord;
    private int yCoord;

    private JPanel panel;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private Weather weather;

    public WeatherPanel(int parentHeight, int parentWidth) {
        panel = new JPanel();
        calculateDimensions(parentWidth, parentHeight);
        panel.setBounds(xCoord, yCoord, width, height);
        panel.setLayout(new BorderLayout());
        initComponents();
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    public void initComponents() {
        scrollPanel = new JPanel(new VerticalLayout());
        scrollPanel.setSize(new Dimension(width, height));
        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void setPlaceWeather(Weather weather) {
        SwingUtilities.invokeLater(() -> {
            panel.remove(scrollPane);
            initComponents();
            this.weather = weather;
            processWeather();
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.revalidate();
        });
    }

    private void processWeather() {
        if (weather != null) {
            addWeatherOnPanel();
        }
    }

    private void addWeatherOnPanel() {
        DescriptionAndWeatherPanel newDescriptionAndWeatherPanel = new DescriptionAndWeatherPanel(
                width, weather);
        scrollPanel.add(newDescriptionAndWeatherPanel.getPanel());
        scrollPanel.revalidate();
    }

    private void calculateDimensions(int parentWidth, int parentHeight) {
        width = parentWidth / 2;
        height = parentHeight / 4;
        xCoord = parentWidth / 2;
        yCoord = parentHeight / 4;
    }
}
