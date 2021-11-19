package Controller;

import Model.AppLogic;
import Model.GeoPosition;
import Model.GettingObjects.*;
import View.FirstFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class SearchButtonController {

    private final FirstFrame firstFrame;
    private final AppLogic appLogic;

    public SearchButtonController(FirstFrame firstFrame, AppLogic appLogic) {
        this.firstFrame = firstFrame;
        this.appLogic = appLogic;
        this.firstFrame.addActionListenerToQuestionPanel(new SearchButtonListener());
    }

    class SearchButtonListener implements ActionListener {

        private String language;

        @Override
        public void actionPerformed(ActionEvent e) {
            String address = firstFrame.getAddressFromQuestionPanelTextField();
            CompletableFuture.supplyAsync(() -> {
                analyzeLanguage(address);
                return appLogic.listOfAddressResponse(address);
            }).thenAcceptAsync(listOfPlaces -> {
                firstFrame.setListOfPlacesToChoosingPanel(listOfPlaces);
                listOfPlaces.getPlaceList().forEach(x -> {
                    GeoPosition tmpGeoPos = x.getPosition();
                    firstFrame.addActionListenersToChoosingPanel(new DescriptionInfoButtonListener(language,
                            "15", tmpGeoPos), tmpGeoPos);
                    firstFrame.addActionListenersToChoosingPanel(new WeatherInfoButtonListener(language,
                            x), tmpGeoPos);
                });
                firstFrame.refreshFrame();
                if(0 == listOfPlaces.getPlaceList().size()){
                    JOptionPane.showMessageDialog(firstFrame.getFrame(),
                            "No places found with that name");
                }
            });
        }

        private void analyzeLanguage(String address) {
            language = "en";
        }
    }

    class DescriptionInfoButtonListener implements ActionListener {

        private final GeoPosition geoPosition;
        private final String language;
        private final String radius;

        public DescriptionInfoButtonListener(String language, String radius,
                                             GeoPosition placeGeoPosition) {
            geoPosition = placeGeoPosition;
            this.language = language;
            this.radius = radius;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CompletableFuture.supplyAsync(() -> appLogic.parsePlace(language, radius, geoPosition))
                    .thenAcceptAsync(tmpListPlace -> {
                        CompletableFuture.supplyAsync(() -> appLogic.takeDescription(language,
                                tmpListPlace)).thenAcceptAsync(x -> {
                            firstFrame.setDescriptionsOnInfoPanel(x);
                            firstFrame.refreshFrame();
                        });
                    });
        }
    }

    class WeatherInfoButtonListener implements ActionListener {

        private final String language;
        private final Place place;

        public WeatherInfoButtonListener(String language, Place place) {
            this.language = language;
            this.place = place;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CompletableFuture.supplyAsync(() -> appLogic.takeWeather(language, place))
                    .thenAcceptAsync(x -> {
                        firstFrame.setWeatherOnWeatherPanel(x);
                        firstFrame.refreshFrame();
                    });
        }
    }
}
