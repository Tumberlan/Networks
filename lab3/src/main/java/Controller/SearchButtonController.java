package Controller;

import Model.AppLogic;
import Model.GeoPosition;
import Model.GettingObjects.ListOfPlaces;
import Model.GettingObjects.XidPlace;
import View.New.FirstFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.List;

public class SearchButtonController{

    private FirstFrame firstFrame;
    private AppLogic appLogic;

    public SearchButtonController(FirstFrame firstFrame, AppLogic appLogic){
        this.firstFrame = firstFrame;
        this.appLogic = appLogic;
        this.firstFrame.addActionListenerToQuestionPanel(new SearchButtonListener());
    }

    class SearchButtonListener implements ActionListener{

        private List<XidPlace> xidList;
        private String language;

        @Override
        public void actionPerformed(ActionEvent e) {
            String address =  firstFrame.getAddressFromQuestionPanelTextField();
            analyzeLanguage(address);
            ListOfPlaces tmpListOfPlaces = appLogic.listOfAddressResponse(address);
            firstFrame.setListOfPlacesToChoosingPanel(tmpListOfPlaces);
            tmpListOfPlaces.getPlaceList().forEach(x->{
                GeoPosition tmpGeoPos = x.getPosition();
                firstFrame.addActionListenersToChoosingPanel(new InfoButtonListener(language,
                        "15", tmpGeoPos), tmpGeoPos);
            });
            firstFrame.refreshFrame();
        }

        private void analyzeLanguage(String address){
            language = "en";
        }
    }

    class InfoButtonListener implements ActionListener{

        private GeoPosition geoPosition;
        private String language;
        private String radius;

        public InfoButtonListener(String language, String radius, GeoPosition placeGeoPosition){
                geoPosition = placeGeoPosition;
                this.language = language;
                this.radius = radius;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //JOptionPane.showMessageDialog(firstFrame.getFrame(), "OHH BABY IT'S TRIPLE");
            //firstFrame.setDescriptionOnInfoPanel(appLogic.takeDescription(language, ));
            firstFrame.setXidListOnInfoPanel(appLogic.parsePlace(language, radius,geoPosition));

        }
    }
}
