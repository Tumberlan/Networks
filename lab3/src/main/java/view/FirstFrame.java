package view;

import model.GeoPosition;
import model.gettingobjects.ListOfPlaces;
import model.gettingobjects.Weather;
import model.gettingobjects.XidPlace;
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
    private static final int FRAME_WIDTH_VISIBLE_OFFSET_FIX = 15;
    private static final int FRAME_HEIGHT_VISIBLE_OFFSET_FIX = 35;
    private static final int FRAME_X_LOCATION = 0;
    private static final int FRAME_Y_LOCATION = 0;

    private JFrame frame;
    private JPanel panel = new JPanel();
    private QuestionPanel questionPanel;
    private ChoosingPanel choosingPanel;
    private InfoPanel infoPanel;
    private WeatherPanel weatherPanel;

    public FirstFrame() {

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame();
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setLocation(FRAME_X_LOCATION, FRAME_Y_LOCATION);
            frame.setPreferredSize(new Dimension(FRAME_WIDTH + FRAME_WIDTH_VISIBLE_OFFSET_FIX,
                    FRAME_HEIGHT + FRAME_HEIGHT_VISIBLE_OFFSET_FIX));
            frame.setTitle("my app");
            frame.setResizable(false);
            initPanels();
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }

    private void initPanels() {
        questionPanel = new QuestionPanel(FRAME_HEIGHT, FRAME_WIDTH);
        JButton tmpButton = questionPanel.getButton();
        questionPanel.setButton(tmpButton);
        choosingPanel = new ChoosingPanel(FRAME_HEIGHT, FRAME_WIDTH);
        infoPanel = new InfoPanel(FRAME_HEIGHT, FRAME_WIDTH);
        weatherPanel = new WeatherPanel(FRAME_HEIGHT, FRAME_WIDTH);

        frame.add(questionPanel.getPanel());
        frame.add(choosingPanel.getPanel());
        frame.add(infoPanel.getPanel());
        frame.add(weatherPanel.getPanel());
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

    public void addActionListenersToInfoPanel(ActionListener actionListener, String xid) {
        infoPanel.addActionListenerToInfoOnOnePlace(actionListener, xid);
    }

    public String getAddressFromQuestionPanelTextField() {
        return questionPanel.getAddressFromTextField();
    }

    public void setXidPlaceListOnInfoPanel(List<XidPlace> xidPlaceList) {
        infoPanel.setXidPlaceList(xidPlaceList);
    }

    public void setWeatherOnWeatherPanel(Weather weather) {
        weatherPanel.setPlaceWeather(weather);
    }

    public void refreshFrame() {
        frame.revalidate();
        frame.repaint();
    }
}
