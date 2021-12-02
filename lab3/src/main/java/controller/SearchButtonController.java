package controller;

import model.AppLogic;
import model.GeoPosition;
import model.gettingobjects.*;
import view.DescriptionOptionPanel;
import view.FirstFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class SearchButtonController {

    private FirstFrame firstFrame;
    private AppLogic appLogic;

    public SearchButtonController(FirstFrame newFirstFrame, AppLogic newAppLogic) {
        SwingUtilities.invokeLater(() -> {
            firstFrame = newFirstFrame;
            appLogic = newAppLogic;
            firstFrame.addActionListenerToQuestionPanel(new SearchButtonListener());
        });
    }

    class SearchButtonListener implements ActionListener {

        private static final String LANGUAGE = "en";

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                String address = firstFrame.getAddressFromQuestionPanelTextField();
                CompletableFuture.supplyAsync(() -> {
                    return appLogic.listOfAddressResponse(address);
                }).thenAcceptAsync(listOfPlaces -> {
                    if (null == listOfPlaces) {
                        JOptionPane.showMessageDialog(firstFrame.getFrame(),
                                "Error in list of places response load");
                        return;
                    }
                    firstFrame.setListOfPlacesToChoosingPanel(listOfPlaces);
                    listOfPlaces.getPlaceList().forEach(x -> {
                        GeoPosition tmpGeoPos = x.getPosition();
                        firstFrame.addActionListenersToChoosingPanel(new
                                XidPlaceInfoButtonListener(LANGUAGE,
                                "15", tmpGeoPos), tmpGeoPos);
                        firstFrame.addActionListenersToChoosingPanel(new
                                WeatherInfoButtonListener(LANGUAGE, x), tmpGeoPos);
                    });
                    firstFrame.refreshFrame();
                    if (0 == listOfPlaces.getPlaceList().size()) {
                        JOptionPane.showMessageDialog(firstFrame.getFrame(),
                                "No places found with that name");
                    }
                });
            });
        }
    }

    class XidPlaceInfoButtonListener implements ActionListener {

        private GeoPosition geoPosition;
        private String language;
        private String radius;

        public XidPlaceInfoButtonListener(String language, String radius,
                                          GeoPosition placeGeoPosition) {
            SwingUtilities.invokeLater(() -> {
                geoPosition = placeGeoPosition;
                this.language = language;
                this.radius = radius;
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                CompletableFuture.supplyAsync(() -> appLogic.parsePlace(language, radius, geoPosition))
                        .thenAcceptAsync(tmpListPlace -> {
                            firstFrame.setXidPlaceListOnInfoPanel(tmpListPlace);
                            tmpListPlace.forEach(x -> {
                                String tmpXid = x.getXid();
                                firstFrame.addActionListenersToInfoPanel(
                                        new ShowDescriptionButtonListener(language, x), tmpXid);
                            });
                            firstFrame.refreshFrame();
                        });
            });
        }
    }

    class WeatherInfoButtonListener implements ActionListener {

        private String language;
        private Place place;

        public WeatherInfoButtonListener(String newLanguage, Place newPlace) {
            SwingUtilities.invokeLater(() -> {
                language = newLanguage;
                place = newPlace;
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                CompletableFuture.supplyAsync(() -> appLogic.takeWeather(language, place))
                        .thenAcceptAsync(x -> {
                            firstFrame.setWeatherOnWeatherPanel(x);
                            firstFrame.refreshFrame();
                        });
            });
        }
    }

    class ShowDescriptionButtonListener implements ActionListener {
        private String language;
        private XidPlace place;

        public ShowDescriptionButtonListener(String newLanguage, XidPlace xidPlace) {
            language = newLanguage;
            place = xidPlace;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                CompletableFuture.supplyAsync(() -> appLogic.takeDescription(language, place))
                        .thenAcceptAsync(description -> {
                            DescriptionOptionPanel descriptionOptionPanel =
                                    new DescriptionOptionPanel(description);
                            JTextPane tmpTextPane = descriptionOptionPanel.getContent();
                            JOptionPane.showMessageDialog(firstFrame.getFrame(), tmpTextPane,
                                    "Description of " + place.getName(), JOptionPane.PLAIN_MESSAGE);
                        });
            });
        }
    }
}
