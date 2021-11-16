package View.New;

import Model.GettingObjects.PlaceDescription;
import Model.GettingObjects.Weather;
import View.MainPanel;
import View.VerticalLayout;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

@Slf4j
@Getter
@Setter
public class DescriptionAndWeatherPanel {

    private static final int PANEL_HEIGHT = 200;
    private final String STYLE_heading = "heading";
    private final String STYLE_normal = "normal";
    private final String STYLE_new_paragraph = "new_paragraph";
    private final String FONT_style = "Times New Roman";
    private Style heading = null;
    private Style normal = null;
    private Style new_paragraph = null;

    private JPanel panel;
    private int width;
    private PlaceDescription description;
    private Weather weather;
    private JTextPane textPane;

    public DescriptionAndWeatherPanel(int parentWidth, PlaceDescription placeDescription,
                                      Weather weather){
        panel = new JPanel(new VerticalLayout());
        this.width = parentWidth;
        this.description = placeDescription;
        this.weather = weather;

        initComponents();

    }

    private void initComponents(){
        createStyles(textPane);
        loadText(textPane, makeDescriptionString());
        textPane.setEditable(false);
        panel.add(textPane);
    }

    private void createStyles(JTextPane textPane) {
        normal = textPane.addStyle(STYLE_normal, null);
        StyleConstants.setFontFamily(normal, FONT_style);
        StyleConstants.setFontSize(normal, 12);

        new_paragraph = textPane.addStyle(STYLE_new_paragraph, normal);
        StyleConstants.setFontSize(new_paragraph, 16);
        StyleConstants.setItalic(new_paragraph, true);

        heading = textPane.addStyle(STYLE_heading, normal);
        StyleConstants.setFontSize(heading, 18);
        StyleConstants.setBold(heading, true);
    }

    private void loadText(JTextPane textPane, String[][] text) {
        for (String[] strings : text) {
            Style style = (strings[1].equals(STYLE_heading)) ?
                    heading : normal;
            try {
                Document doc = textPane.getDocument();
                doc.insertString(doc.getLength(), strings[0], style);
            } catch (Exception e) {
                log.error("can't load text on textPane");
            }
        }
    }

    private String[][] makeDescriptionString() {
        return new String[][]{
                {description.getName() + "\r\n", "heading"},
                {"Description:\r\n" , "new_paragraph"},
                {"Default information: " + notNullAddToStringBuilder(description.getInfo()) +"\r\n",
                        "normal"},
                {"Wikipedia information:\r\nTitle: " + notNullAddToStringBuilder(description.
                        getWikiInfo().getTitle()) + "\r\n", "normal"},
                {description.getWikiInfo().getText() + "\r\n", "normal"},
                {"Weather:\r\n", "new_paragraph"},
                {parseJustWeatherInfo(), "normal"},
                {parseMainWeather(), "normal"},
                {"Clouds amount: " + weather.getClouds().getCloudsAmount() + "\r\n", "normal"},
                {parseWind(), "normal"}
        };
    }

    private String parseJustWeatherInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        weather.getJustWeatherInfo().forEach(x->{
            stringBuilder.append("Main: ").append(notNullAddToStringBuilder(x.getMain()))
                    .append(" , Description: ").append(notNullAddToStringBuilder(x.getDescription()))
                    .append("\r\n");
        });
        return stringBuilder.toString();
    }

    private String parseMainWeather(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Temperature: ").append(weather.getMainWeather().getTemperature())
                .append(" , Feels like: ").append(weather.getMainWeather().getFeelsLike())
                .append(" , Minimal Temperature: ").append(weather.getMainWeather().getMinTemperature())
                .append(" , Maximal Temperature: ").append(weather.getMainWeather().getMaxTemperature())
                .append("\r\n , Humidity: ").append(weather.getMainWeather().getHumidity())
                .append(" , Pressure: ").append(weather.getMainWeather().getPressure())
                .append(" , Sea level: ").append(weather.getMainWeather().getSeaLevel())
                .append(" , Ground Level: ").append(weather.getMainWeather().getGroundLevel())
                .append("\r\n");
        return stringBuilder.toString();
    }

    private String parseWind(){
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



/*
    private String[][] makeStringsForDescription() {
        return new String[][]{
                {this.description.getName() + "\r\n", "heading"},
                {"State: " + notNullAddToStringBuilder(this.place.getState()), "normal"},
                {" , Country: " + notNullAddToStringBuilder(this.place.getCountry()), "normal"},
                {" (" + notNullAddToStringBuilder(this.place.getCountryCode()) + ")\r\n", "normal"},
                {"City: " + notNullAddToStringBuilder(this.place.getCity()), "normal"},
                {" , Street: " + notNullAddToStringBuilder(this.place.getStreet()), "normal"},
                {" , House: " + notNullAddToStringBuilder(this.place.getHouseNumber()), "normal"}
        };
    }

    private String[][] makeStringsForWeather() {
        return new String[][]{
                {this.place.getName() + "\r\n", "heading"},
                {"State: " + notNullAddToStringBuilder(this.place.getState()), "normal"},
                {" , Country: " + notNullAddToStringBuilder(this.place.getCountry()), "normal"},
                {" (" + notNullAddToStringBuilder(this.place.getCountryCode()) + ")\r\n", "normal"},
                {"City: " + notNullAddToStringBuilder(this.place.getCity()), "normal"},
                {" , Street: " + notNullAddToStringBuilder(this.place.getStreet()), "normal"},
                {" , House: " + notNullAddToStringBuilder(this.place.getHouseNumber()), "normal"}
        };
    }
*/

}
