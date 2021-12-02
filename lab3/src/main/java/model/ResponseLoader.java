package model;

import model.gettingobjects.APIKeys;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.util.Locale;

@Slf4j
public class ResponseLoader {

    private static final int MIN_RADIUS_VALUE = 250;
    private static final int MAX_RADIUS_VALUE = 1000;
    private static final int LIMIT_VALUE = 20;
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private final APIKeys apiKeys;


    public ResponseLoader() {
        apiKeys = new APIKeys();
    }

    public Request loadVariants(String inputAddress) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://graphhopper.com/api/1/geocode?q=").
                append(inputAddress.toLowerCase(Locale.ROOT)).
                append("&locale=").append("en").append("&debug=true&key=")
                .append(apiKeys.getGraphhopperKey());
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Request loadPlaceList(String language, int inputRadius,
                                 GeoPosition geoPosition) {
        int radius = radiusCheck(inputRadius);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.opentripmap.com/0.1/").append(language).
                append("/places/radius?").append("&radius=").append(radius).append(".0&lon=").
                append(geoPosition.getLon()).append("&lat=").append(geoPosition.getLat()).
                append("&format=json&limit=").append(LIMIT_VALUE).append("&apikey=").
                append(apiKeys.getOpenTripMapKey());
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Request loadPlaceDescription(String language, String XID) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.opentripmap.com/0.1/").append(language).
                append("/places/xid/").append(XID).append("?apikey=").
                append(apiKeys.getOpenTripMapKey());
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Request loadPlaceWeather(String language, GeoPosition geoPosition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.openweathermap.org/data/2.5/weather?lat=").
                append(geoPosition.getLat()).append("&lon=").append(geoPosition.getLon()).
                append("&units=metric&lang=").append(language).append("&appid=")
                .append(apiKeys.getOpenWeatherMapKey());
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Response requestReleaser(Request request) {
        Call call = CLIENT.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            log.error("No response for your request");
        }
        return response;
    }

    private int radiusCheck(int radius) {
        return Math.min(Math.max(radius, MIN_RADIUS_VALUE), MAX_RADIUS_VALUE);
    }
}
