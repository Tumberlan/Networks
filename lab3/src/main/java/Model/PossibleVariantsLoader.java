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
import java.util.Scanner;

@Slf4j
public class PossibleVariantsLoader {

    private static final int MIN_RADIUS_VALUE = 250;
    private static final int MAX_RADIUS_VALUE = 1000;


    private static final OkHttpClient client = new OkHttpClient();

    public Request loadVariants(String inputAddress){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://graphhopper.com/api/1/geocode?q=").append(inputAddress.toLowerCase(Locale.ROOT)).
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

    public Request loadPlaceList(String inputAddress, boolean isRussian, int inputRadius, GeoPosition geoPosition){
        String language;
        if(isRussian){
            language = "ru";
        }else {
            language = "en";
        }
        int radius = radiusCheck(inputRadius);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.opentripmap.com/0.1/").append(language).
                append("/places/radius?radius=").append(radius).append("&lon=").append(geoPosition.getLon()).
                append("&lat=").append(geoPosition.getLat());
        return new Request.Builder().url(stringBuilder.toString()).get().build();
    }

    public void printAll() throws IOException {

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

        Request variantsRequest = loadVariants("Berlin");
        System.out.println(variantsRequest);
    }

    private int radiusCheck(int radius){
        return Math.min(Math.max(radius,MIN_RADIUS_VALUE),MAX_RADIUS_VALUE);
    }
}
