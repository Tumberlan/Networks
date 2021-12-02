package model.gettingobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Weather {
    private @JsonProperty("weather")
    List<JustWeatherInfo> justWeatherInfo;
    private @JsonProperty("main")
    MainWeather mainWeather;
    private int visibility;
    private Wind wind;
    private Clouds clouds;

    @Getter
    @Setter
    public static final class JustWeatherInfo {
        private String main;
        private String description;
    }

    @Getter
    @Setter
    public static final class MainWeather {
        private @JsonProperty("temp")
        double temperature;
        private @JsonProperty("feels_like")
        double feelsLike;
        private @JsonProperty("temp_min")
        double minTemperature;
        private @JsonProperty("temp_max")
        double maxTemperature;
        private int pressure;
        private int humidity;
        private @JsonProperty("sea_level")
        int seaLevel;
        private @JsonProperty("grnd_level")
        int groundLevel;
    }

    @Getter
    @Setter
    public static final class Wind {
        private int speed;
        private @JsonProperty("deg")
        int degree;
        private double gust;

        public String calculateWindDirection() {
            int windIdx = (degree % 360) / 45;
            switch (windIdx) {
                case 0 -> {
                    return WindDirection.NORTH.convertToString();
                }
                case 1 -> {
                    return WindDirection.NORTH_EAST.convertToString();
                }
                case 2 -> {
                    return WindDirection.EAST.convertToString();
                }
                case 3 -> {
                    return WindDirection.SOUTH_EAST.convertToString();
                }
                case 4 -> {
                    return WindDirection.SOUTH.convertToString();
                }
                case 5 -> {
                    return WindDirection.SOUTH_WEST.convertToString();
                }
                case 6 -> {
                    return WindDirection.WEST.convertToString();
                }
                case 7 -> {
                    return WindDirection.NORTH_WEST.convertToString();
                }
            }
            return null;
        }
    }

    @Getter
    @Setter
    public static final class Clouds {
        private @JsonProperty("all")
        int cloudsAmount;
    }
}
