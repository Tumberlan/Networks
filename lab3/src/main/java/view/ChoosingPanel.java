package view;

import model.GeoPosition;
import model.gettingobjects.ListOfPlaces;
import model.gettingobjects.Place;
import view.utils.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class ChoosingPanel {

    private int height;
    private int width;
    private int xCoord;
    private int yCoord;

    private JPanel panel;
    private ListOfPlaces listOfPlaces;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private List<OneVariantPanel> oneVariantPanelList;

    public ChoosingPanel(int parentHeight, int parentWidth) {
        panel = new JPanel();
        calculateDimensions(parentWidth, parentHeight);
        panel.setBounds(xCoord, yCoord, width, height);
        panel.setLayout(new BorderLayout());
        initComponents();
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private void initComponents() {
        scrollPanel = new JPanel(new VerticalLayout());
        scrollPanel.setSize(new Dimension(width, height));
        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void setListOfPlaces(ListOfPlaces listOfPlaces) {
        SwingUtilities.invokeLater(() -> {
            panel.remove(scrollPane);
            oneVariantPanelList = new LinkedList<OneVariantPanel>();
            initComponents();
            this.listOfPlaces = listOfPlaces;
            processListOfPlaces();
            panel.add(scrollPane, BorderLayout.CENTER);
        });
    }

    private void addPlaceOnPanel(Place place) {
        OneVariantPanel tmpNewOneVariantPanel = new OneVariantPanel(width, place);
        oneVariantPanelList.add(tmpNewOneVariantPanel);
    }

    private void addPlacesFromListToPanel() {
        oneVariantPanelList.forEach(x -> {
            scrollPanel.add(x.getPanel());
            scrollPanel.revalidate();
        });
    }

    private void processListOfPlaces() {
        if (this.listOfPlaces != null) {
            this.listOfPlaces.getPlaceList().forEach(this::addPlaceOnPanel);
        }
        addPlacesFromListToPanel();
    }

    public void addActionListenerToInfoOnOneVariant(ActionListener actionListener,
                                                    GeoPosition geoPosition) {

        SwingUtilities.invokeLater(() -> {
            oneVariantPanelList.forEach(x -> {
                if (x.getPlace().getPosition().equals(geoPosition)) {
                    x.addActionListenerToInfoButton(actionListener);
                }
            });

            panel.revalidate();
        });
    }

    /*
    SwingUtilities.InvokeLater(()->{
        --my code--
    })
     */
    private void calculateDimensions(int parentWidth, int parentHeight) {
        width = parentWidth / 2;
        height = parentHeight * 3 / 4;
        xCoord = 0;
        yCoord = parentHeight / 4;
    }
}
