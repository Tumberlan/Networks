package Model;

import Model.GettingObjects.*;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class AppLogic {

    private static final ResponseLoader RESPONSE_LOADER = new ResponseLoader();
    private static final JsonParser JSON_PARSER = new JsonParser();

    public ListOfPlaces listOfAddressResponse(String address) {
        Response response = RESPONSE_LOADER.requestReleaser(
                RESPONSE_LOADER.loadVariants(address));
        String responseText = null;
        try {
            responseText = response.body().string();
        } catch (IOException e) {
            log.error("can't take response message");
        }
        ListOfPlaces newListOfPlaces = JSON_PARSER.parse(responseText, ListOfPlaces.class);
        return newListOfPlaces;
    }

    public List<XidPlace> parsePlace(String language, String radius, GeoPosition geoPosition) {
        Response response = RESPONSE_LOADER.requestReleaser(RESPONSE_LOADER
                .loadPlaceList(language, Integer.parseInt(radius), geoPosition));
        String responseText = null;
        try {
            responseText = response.body().string();
        } catch (IOException e) {
            log.error("can't take response message");
        }
        return JSON_PARSER.parse(responseText, new TypeReference<List<XidPlace>>() {
        });
    }

    public List<PlaceDescription> takeDescription(String language, List<XidPlace> responseXidList) {
        List<PlaceDescription> listOfPlacesDescription = new LinkedList<PlaceDescription>();
        responseXidList.forEach(x -> {
            Response response = RESPONSE_LOADER.requestReleaser(RESPONSE_LOADER.
                    loadPlaceDescription(language, x.getXid()));
            String responseText = null;
            try {
                responseText = response.body().string();
            } catch (IOException e) {
                log.error("can't take response message");
            }
            PlaceDescription placeDescription = JSON_PARSER.parse(responseText, PlaceDescription.class);
            listOfPlacesDescription.add(placeDescription);
        });
        return listOfPlacesDescription;
    }

    public Weather takeWeather(String language, Place place) {
        Response response = RESPONSE_LOADER.requestReleaser(RESPONSE_LOADER.loadPlaceWeather(
                language, place.getPosition()));
        String responseText = null;
        try {
            responseText = response.body().string();
        } catch (IOException e) {
            log.error("can't take response message");
        }
        Weather weather = JSON_PARSER.parse(responseText, Weather.class);
        return weather;
    }
}
