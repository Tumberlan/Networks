package Model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
public class PossibleVariantsLoader {

    private static final int MIN_RADIUS_VALUE = 250;
    private static final int MAX_RADIUS_VALUE = 1000;
    private static final int LIMIT_VALUE = 20;
    private static final Properties properties = new Properties();


    private static final OkHttpClient client = new OkHttpClient();

    public Request loadVariants(String inputAddress){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://graphhopper.com/api/1/geocode?q=").
                append(inputAddress.toLowerCase(Locale.ROOT)).
                append("&locale=de&debug=true&key=api_key");
        return new Request.Builder().url(stringBuilder.toString()).get().build();

        /*System.out.println(stringBuilder.toString());

        URI dataBaseAddress = null;
        try {
            dataBaseAddress = new URI(stringBuilder.toString());
        } catch (URISyntaxException e) {
            log.error("Can't find that URI");
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(dataBaseAddress);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            log.error("no response");
        }
        return httpResponse;*/
    }

    public Request loadPlaceList(String language, int inputRadius,
                                 GeoPosition geoPosition){
        int radius = radiusCheck(inputRadius);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.opentripmap.com/0.1/").append(language).
                append("/places/radius?radius=").append(radius).append("&lon=").
                append(geoPosition.getLon()).append("&lat=").append(geoPosition.getLat()).
                append("&format=json&limit=").append(LIMIT_VALUE);
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Request loadPlaceDescription(String language, String XID){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.opentripmap.com/0.1/").append(language).
                append("/places/xid/").append(XID);
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public Request loadPlaceWeather(String language, GeoPosition geoPosition){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.openweathermap.org/data/2.5/weather?lat=").
                append(geoPosition.getLat()).append("&lon=").append(geoPosition.getLon()).
                append("&appid=").append(properties.getProperty("OWP_API_KEY"));
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public void printAll(boolean isRussian) throws IOException {

        /*HttpResponse response1 = loadVariants("Berlin");

        Scanner sc = null;
        try {
            sc = new Scanner(response1.getEntity().getContent());
        } catch (IOException e) {
            log.error("Can't invoke scanner");
        }

        //Printing the status line
        System.out.println(response1.getStatusLine());
        while(sc.hasNext()) {
            System.out.println(sc.nextLine());
        }*/

        String language;
        if(isRussian){
            language = "ru";
        }else {
            language = "en";
        }
        GeoPosition geoPosition = new GeoPosition();

        Request variantsRequest = loadVariants("Berlin");
        Request placeListRequest = loadPlaceList(language, 50, geoPosition);
        Request placeDescriptorRequest = loadPlaceDescription(language, "berlin");
        Request placeWeatherRequest = loadPlaceWeather(language, geoPosition);
        System.out.println(variantsRequest);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(placeListRequest);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(placeDescriptorRequest);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(placeWeatherRequest);
    }

    private int radiusCheck(int radius){
        return Math.min(Math.max(radius,MIN_RADIUS_VALUE),MAX_RADIUS_VALUE);
    }
}
