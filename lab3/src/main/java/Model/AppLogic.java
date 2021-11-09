package Model;

import Model.GettingObjects.ListOfPlaces;
import Model.GettingObjects.Place;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Address;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class AppLogic {

    private static final PossibleVariantsLoader POSSIBLE_VARIANTS_LOADER = new PossibleVariantsLoader();
    private static final JsonParser JSON_PARSER = new JsonParser();
    public ListOfPlaces listOfAddressResponse(String address, String language){
        Response response = POSSIBLE_VARIANTS_LOADER.requestReleaser(
                POSSIBLE_VARIANTS_LOADER.loadVariants(address));
        String responseText = null;
        try {
            responseText = response.body().string();
        } catch (IOException e) {
            log.error("can't take response message");
        }
        System.out.println(response);
        System.out.println(responseText);
        ListOfPlaces newListOfPlaces = JSON_PARSER.parse(responseText, ListOfPlaces.class);
        System.out.println(newListOfPlaces);
        newListOfPlaces.getPlaceList().forEach(x->{
            System.out.println(x.getName() + "\n" +
                    x.getCountry() + "\n" +
                    x.getCity() + "\n" +
                    x.getState() + "\n" +
                    x.getStreet() + "\n" +
                    x.getHouseNumber() + "\n" +
                    x.getCountryCode() + "\n" +
                    x.getPosition().getStringLat() + "\n" +
                    x.getPosition().getStringLon() + "\n" +
                    x.getPosition().getLat() + "\n" +
                    x.getPosition().getLon() + "\n");
        });
        return newListOfPlaces;
    }

    public void placeListLoadResponse(String language, String radius, ListOfPlaces listOfPlaces){
        System.out.println("!1!");
        listOfPlaces.getPlaceList().forEach(x->{
            parsePlace(language, radius, x.getPosition());
        });
        System.out.println("!4!");
    }

    public void parsePlace(String language, String radius, GeoPosition geoPosition){
        System.out.println("!2!");
        Response response = POSSIBLE_VARIANTS_LOADER.requestReleaser(POSSIBLE_VARIANTS_LOADER
                .loadPlaceList(language, Integer.parseInt(radius), geoPosition));
        String responseText = null;
        try {
            responseText = response.body().string();
        } catch (IOException e) {
            log.error("can't take response message");
        }
        System.out.println("!3!");
        System.out.println(responseText);
    }
}
