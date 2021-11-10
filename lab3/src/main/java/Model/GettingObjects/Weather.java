package Model.GettingObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Weather {
    private @JsonProperty("weather") List<JustWeatherInfo> justWeatherInfo;
    private @JsonProperty("main") MainWeather mainWeather;
    private int visibility;
    private Wind wind;
    private Clouds clouds;


    public static final class JustWeatherInfo{
        private String main;
        private String description;
    }

    public static final class MainWeather{
        private @JsonProperty("temp") double temperature;
        private @JsonProperty("feels_like") double feelsLike;
        private @JsonProperty("temp_min") double minTemperature;
        private @JsonProperty("temp_max") double maxTemperature;
        private int pressure;
        private int humidity;
        private @JsonProperty("sea_level") int seaLevel;
        private @JsonProperty("grnd_level") int groundLevel;
    }

    public static final class Wind{
        private int speed;
        private @JsonProperty("deg") int degree;
        private double gust;

        public WindDirection calculateWindDirection(){
            int windIdx = (degree % 360) /45;
            switch (windIdx){
                case 0 -> {
                    return WindDirection.NORTH;
                }
                case 1 -> {
                    return WindDirection.NORTH_EAST;
                }
                case 2 -> {
                    return WindDirection.EAST;
                }
                case 3 -> {
                    return WindDirection.SOUTH_EAST;
                }
                case 4 -> {
                    return WindDirection.SOUTH;
                }
                case 5 -> {
                    return WindDirection.SOUTH_WEST;
                }
                case 6 -> {
                    return WindDirection.WEST;
                }
                case 7 -> {
                    return WindDirection.NORTH_WEST;
                }
            }
            return null;
        }
    }

    public static final class Clouds{
        private @JsonProperty("all") int cloudsAmount;
    }
}
