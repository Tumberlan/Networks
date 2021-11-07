package Model;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

@Slf4j
public class PossibleVariantsLoader {

    private static final int MIN_RADIUS_VALUE = 250;
    private static final int MAX_RADIUS_VALUE = 1000;
    private static final int LIMIT_VALUE = 20;
    private static final Properties properties = new Properties();


    private static final OkHttpClient client = new OkHttpClient();

    public Request loadVariants(String inputAddress) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://graphhopper.com/api/1/geocode?q=").
                append(inputAddress.toLowerCase(Locale.ROOT)).
                append("&locale=de&debug=true&key=api_key");
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Request loadPlaceList(String language, int inputRadius,
                                 GeoPosition geoPosition) {
        int radius = radiusCheck(inputRadius);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.opentripmap.com/0.1/").append(language).
                append("/places/radius?radius=").append(radius).append("&lon=").
                append(geoPosition.getLon()).append("&lat=").append(geoPosition.getLat()).
                append("&format=json&limit=").append(LIMIT_VALUE);
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Request loadPlaceDescription(String language, String XID) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.opentripmap.com/0.1/").append(language).
                append("/places/xid/").append(XID);
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Request loadPlaceWeather(GeoPosition geoPosition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.openweathermap.org/data/2.5/weather?lat=").
                append(geoPosition.getLat()).append("&lon=").append(geoPosition.getLon()).
                append("&appid=").append(properties.getProperty("OWP_API_KEY"));
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public void printAll(boolean isRussian) throws IOException {

        String language;
        if (isRussian) {
            language = "ru";
        } else {
            language = "en";
        }
        GeoPosition geoPosition = new GeoPosition();

        Request variantsRequest = loadVariants("Berlin");
        Request placeListRequest = loadPlaceList(language, 50, geoPosition);
        Request placeDescriptorRequest = loadPlaceDescription(language, "berlin");
        Request placeWeatherRequest = loadPlaceWeather(geoPosition);
        System.out.println(variantsRequest);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(placeListRequest);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(placeDescriptorRequest);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(placeWeatherRequest);
    }

    public Response requestReleaser(Request request){
        Call call = client.newCall(request);
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