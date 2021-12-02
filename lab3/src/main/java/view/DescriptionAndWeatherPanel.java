package view;

import model.gettingobjects.Weather;
import view.utils.MyTextPane;
import view.utils.VerticalLayout;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Getter
@Setter
public class DescriptionAndWeatherPanel {

    private static final int PANEL_HEIGHT = 200;
    private static final int PANEL_WIDTH_VISIBLE_OFFSET_FIX = -25;

    private int width;

    private JPanel panel;
    private Weather weather;
    private MyTextPane textPane;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private String placeName;
    private boolean isWeather;
    private boolean isDescriptionEmpty = false;

    public DescriptionAndWeatherPanel(int parentWidth, Weather newWeather) {
        panel = new JPanel(new GridLayout());
        weather = newWeather;

        calculateDimensions(parentWidth);
        panel.setPreferredSize(new Dimension(width, PANEL_HEIGHT));
        panel.setForeground(Color.YELLOW);

        scrollPanel = new JPanel(new VerticalLayout());
        scrollPanel.setSize(new Dimension(width, PANEL_HEIGHT));
        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);

        initComponents();
    }

    private void initComponents() {
        textPane = new MyTextPane();
        textPane.setStyles();
        textPane.setText(makeWeatherString());
        textPane.setSize(new Dimension(width, PANEL_HEIGHT));
        scrollPanel.add(textPane.getTextPane());
    }

    private String[][] makeWeatherString() {
        return new String[][]{
                {"Weather:\r\n", "heading"},
                {parseJustWeatherInfo(), "normal"},
                {parseMainWeather(), "normal"},
                {"Clouds amount: " + weather.getClouds().getCloudsAmount() + "\r\n", "normal"},
                {parseWind(), "normal"}
        };
    }

    private String parseJustWeatherInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        weather.getJustWeatherInfo().forEach(x -> {
            stringBuilder.append("Main: ").append(notNullAddToStringBuilder(x.getMain()))
                    .append(" , Description: ").append(notNullAddToStringBuilder(x.getDescription()))
                    .append("\r\n");
        });
        return stringBuilder.toString();
    }

    private String parseMainWeather() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Temperature: ").append(weather.getMainWeather().getTemperature())
                .append(" , Feels like: ").append(weather.getMainWeather().getFeelsLike())
                .append(" , Minimal Temperature: ").append(weather.getMainWeather().getMinTemperature())
                .append("\r\nMaximal Temperature: ").append(weather.getMainWeather().getMaxTemperature())
                .append(" , Humidity: ").append(weather.getMainWeather().getHumidity())
                .append("\r\nPressure: ").append(weather.getMainWeather().getPressure())
                .append(" , Sea level: ").append(weather.getMainWeather().getSeaLevel())
                .append(" , Ground Level: ").append(weather.getMainWeather().getGroundLevel())
                .append("\r\n");
        return stringBuilder.toString();
    }

    private String parseWind() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wind:\r\nDegree: ").append(weather.getWind().getDegree())
                .append(" , Speed: ").append(weather.getWind().getSpeed())
                .append(" , Gust: ").append(weather.getWind().getGust())
                .append(" , Direction ").append(weather.getWind().calculateWindDirection());
        return stringBuilder.toString();
    }

    private String notNullAddToStringBuilder(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return ("-");
        }
    }

    private void calculateDimensions(int parentWidth) {
        width = parentWidth + PANEL_WIDTH_VISIBLE_OFFSET_FIX;
    }
}
