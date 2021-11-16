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

    public ListOfPlaces listOfAddressResponse(String address){
        Response response = RESPONSE_LOADER.requestReleaser(
                RESPONSE_LOADER.loadVariants(address));
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
                    x.getPosition().getLat() + "\n" +
                    x.getPosition().getLon() + "\n" +
                    x.getPosition().getLat() + "\n" +
                    x.getPosition().getLon() + "\n");
        });
        return newListOfPlaces;
    }

    public List<XidPlace> placeListLoadResponse(String language, String radius,
                                                  ListOfPlaces listOfPlaces){
        List<XidPlace> allXidPlacesList = new LinkedList<XidPlace>();
        listOfPlaces.getPlaceList().forEach(x->{
            List<XidPlace> tmpList = parsePlace(language, radius, x.getPosition());
            allXidPlacesList.addAll(tmpList);
        });
        return allXidPlacesList;
    }

    public List<XidPlace> parsePlace(String language, String radius, GeoPosition geoPosition){
        Response response = RESPONSE_LOADER.requestReleaser(RESPONSE_LOADER
                .loadPlaceList(language, Integer.parseInt(radius), geoPosition));
        String responseText = null;
        try {
            responseText = response.body().string();
        } catch (IOException e) {
            log.error("can't take response message");
        }
        System.out.println(responseText);
        return JSON_PARSER.parse(responseText, new TypeReference<List<XidPlace>>() {});
    }

    public void takeDescription(String language,List<XidPlace> responseXidList){
        responseXidList.forEach(x->{
            Response response = RESPONSE_LOADER.requestReleaser(RESPONSE_LOADER.
                    loadPlaceDescription(language, x.getXid()));
            String responseText = null;
            try {
                responseText = response.body().string();
            } catch (IOException e) {
                log.error("can't take response message");
            }
            System.out.println(response);
            System.out.println(responseText);
            PlaceDescription placeDescription = JSON_PARSER.parse(responseText, PlaceDescription.class);
            System.out.println(placeDescription.getXid() + "\n" +
                    placeDescription.getName() + "\n");
            if(placeDescription.getInfo() != null){
                if(placeDescription.getInfo().getDescr() != null){
                    System.out.println(placeDescription.getInfo().getDescr());
                }
            }
            if(placeDescription.getWikiInfo() != null) {
                if (placeDescription.getWikiInfo().getTitle() != null) {
                    System.out.println(placeDescription.getWikiInfo().getTitle());
                }
                if (placeDescription.getWikiInfo().getText() != null) {
                    System.out.println(placeDescription.getWikiInfo().getText());
                }
            }
        });
    }
    
    public void takeWeather(String language, ListOfPlaces listOfPlaces){
        listOfPlaces.getPlaceList().forEach(x->{
            Response response = RESPONSE_LOADER.requestReleaser(RESPONSE_LOADER.loadPlaceWeather(
                    language, x.getPosition()));
            String responseText = null;
            try {
                responseText = response.body().string();
            } catch (IOException e) {
                log.error("can't take response message");
            }
            System.out.println(response);
            System.out.println(responseText);
            Weather weather = JSON_PARSER.parse(responseText, Weather.class);
            weather.getJustWeatherInfo().forEach(y->{
                System.out.println(y.getMain() + "\n" + y.getDescription());
            });
            System.out.println(weather.getMainWeather().getTemperature() + "\n" +
                    weather.getMainWeather().getFeelsLike() + "\n" +
                    weather.getMainWeather().getMinTemperature() + "\n" +
                    weather.getMainWeather().getMaxTemperature() + "\n" +
                    weather.getMainWeather().getHumidity() + "\n" +
                    weather.getMainWeather().getPressure() + "\n" +
                    weather.getMainWeather().getSeaLevel() + "\n" +
                    weather.getMainWeather().getGroundLevel());
            System.out.println(weather.getClouds().getCloudsAmount());
            System.out.println(weather.getVisibility());
            System.out.println(weather.getWind().getDegree() + "\n" +
                    weather.getWind().getSpeed() + "\n" +
                    weather.getWind().getGust() + "\n" +
                    weather.getWind().calculateWindDirection().convertToString());
        });
    }
}
