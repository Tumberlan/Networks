package View.New;

import Model.GeoPosition;
import Model.GettingObjects.ListOfPlaces;
import Model.GettingObjects.PlaceDescription;
import Model.GettingObjects.Weather;
import Model.GettingObjects.XidPlace;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

@Getter
@Setter
public class FirstFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    private JFrame frame;
    private JPanel panel = new JPanel();
    private QuestionPanel questionPanel;
    private ChoosingPanel choosingPanel;
    private InfoPanel infoPanel;

    public FirstFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH + 15, FRAME_HEIGHT + 35));
        frame.setTitle("my app");

        initPanels();

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void initPanels() {
        questionPanel = new QuestionPanel(FRAME_HEIGHT, FRAME_WIDTH);
        JButton tmpButton = questionPanel.getButton();
        questionPanel.setButton(tmpButton);
        choosingPanel = new ChoosingPanel(FRAME_HEIGHT, FRAME_WIDTH);
        infoPanel = new InfoPanel(FRAME_HEIGHT, FRAME_WIDTH);

        frame.add(questionPanel.getPanel());
        frame.add(choosingPanel.getPanel());
        frame.add(infoPanel.getPanel());
    }

    public void addActionListenerToQuestionPanel(ActionListener searchButtonListener) {
        questionPanel.addActionListenerToSearchButton(searchButtonListener);
    }

    public void addActionListenersToChoosingPanel(ActionListener actionListener,
                                                  GeoPosition geoPosition) {
        choosingPanel.addActionListenerToInfoOnOneVariant(actionListener, geoPosition);
    }

    public void setListOfPlacesToChoosingPanel(ListOfPlaces listOfPlaces) {
        choosingPanel.setListOfPlaces(listOfPlaces);
    }

    public String getAddressFromQuestionPanelTextField() {
        return questionPanel.getAddressFromTextField();
    }

    public void refreshQuestionPanel() {
        questionPanel.refreshPanel();
    }

    public void refreshChoosingPanel() {
        choosingPanel.refreshPanel();
    }

    public void refreshQInfoPanel() {
        infoPanel.refreshPanel();
    }

    public void setXidListOnInfoPanel(List<XidPlace> xidList) {
        infoPanel.resetXidList(xidList);
    }

    public void setDescriptionAndWeatherOnInfoPanel(PlaceDescription description, Weather weather){
        infoPanel.setDescriptionAndWeather(description, weather);
    }

    public void refreshFrame() {
        frame.revalidate();
        frame.repaint();
    }
}
