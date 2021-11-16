package View.New;

import Model.GeoPosition;
import Model.GettingObjects.ListOfPlaces;
import Model.GettingObjects.Place;
import View.OneVariantPanel;
import View.VerticalLayout;
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

    private JPanel panel;
    private ListOfPlaces listOfPlaces;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private List<OneVariantPanel> oneVariantPanelList;

    public ChoosingPanel(int parentHeight, int parentWidth){
        panel = new JPanel();
        height = parentHeight*3/4;
        width = parentWidth/2;
        panel.setBounds(0, parentHeight/4, width, height);
        panel.setBackground(Color.CYAN);
        panel.setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents(){
        scrollPanel = new JPanel(new VerticalLayout());
        scrollPanel.setSize(new Dimension(width, height));
        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void setListOfPlaces(ListOfPlaces listOfPlaces){
        panel.remove(scrollPane);
        oneVariantPanelList = new LinkedList<OneVariantPanel>();
        initComponents();
        this.listOfPlaces = listOfPlaces;
        processListOfPlaces();
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private void addPlaceOnPanel(Place place) {
        OneVariantPanel tmpNewOneVariantPanel = new OneVariantPanel(width,place);
        oneVariantPanelList.add(tmpNewOneVariantPanel);
    }

    private void addPlacesFromListToPanel(){
        oneVariantPanelList.forEach(x->{
            scrollPanel.add(x.getPanel());
            scrollPanel.revalidate();
        });
    }

    private void processListOfPlaces(){
        if (this.listOfPlaces != null) {
            this.listOfPlaces.getPlaceList().forEach(this::addPlaceOnPanel);
        }
        addPlacesFromListToPanel();
    }

    public void refreshPanel(){
        scrollPane.revalidate();
        scrollPanel.revalidate();
        panel.revalidate();
    }

    public void addActionListenerToInfoOnOneVariant(ActionListener actionListener, GeoPosition geoPosition){
        oneVariantPanelList.forEach(x->{
            if(x.getPlace().getPosition().equals(geoPosition)) {
                x.addActionListenerToInfoButton(actionListener);
            }
        });
        panel.revalidate();
    }

}
