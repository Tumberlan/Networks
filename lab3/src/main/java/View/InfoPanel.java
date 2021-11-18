package View;

import Model.GettingObjects.PlaceDescription;
import Model.GettingObjects.XidPlace;
import View.Utils.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class InfoPanel {

    private int height;
    private int width;
    private int xCoord;
    private int yCoord;

    private JPanel panel;
    private List<XidPlace> xidList;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private PlaceDescription description;
    private List<DescriptionAndWeatherPanel> descriptionAndWeatherPanelList;
    private List<PlaceDescription> placeDescriptionList;
    private boolean ifNoPlaces;

    public InfoPanel(int parentHeight, int parentWidth) {
        panel = new JPanel();
        calculateDimensions(parentWidth, parentHeight);
        panel.setBounds(xCoord, yCoord, width, height);
        panel.setLayout(new BorderLayout());
        initComponents();
        panel.add(scrollPane, BorderLayout.CENTER);
    }


    public void initComponents() {
        scrollPanel = new JPanel(new VerticalLayout());
        scrollPanel.setSize(new Dimension(width, height));
        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void setPlaceDescriptionList(List<PlaceDescription> placeDescriptionList) {
        panel.remove(scrollPane);
        initComponents();
        descriptionAndWeatherPanelList = new LinkedList<DescriptionAndWeatherPanel>();
        this.placeDescriptionList = placeDescriptionList;
        processListInfo();
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.revalidate();
        panel.revalidate();
    }

    private void processListInfo() {
        if (placeDescriptionList != null) {
            placeDescriptionList.forEach(this::addDescriptionOnPanel);
            if (0 == placeDescriptionList.size()) {
                addNoDescriptionsOnPanel();
            } else {
                addDescriptionFromListToPanel();
            }
        }

    }

    private void addNoDescriptionsOnPanel() {
        DescriptionAndWeatherPanel newDescriptionAndWeatherPanel = new DescriptionAndWeatherPanel(
                width, "");
        scrollPanel.add(newDescriptionAndWeatherPanel.getPanel());
        scrollPanel.revalidate();
    }

    private void addDescriptionOnPanel(PlaceDescription description) {
        DescriptionAndWeatherPanel newDescriptionAndWeatherPanel = new DescriptionAndWeatherPanel(
                width, description);
        descriptionAndWeatherPanelList.add(newDescriptionAndWeatherPanel);
    }

    private void addDescriptionFromListToPanel() {
        descriptionAndWeatherPanelList.forEach(x -> {
            scrollPanel.add(x.getPanel());
            scrollPanel.revalidate();
        });
    }

    private void calculateDimensions(int parentWidth, int parentHeight) {
        width = parentWidth / 2;
        height = parentHeight / 2;
        xCoord = parentWidth / 2;
        yCoord = parentHeight / 2;
    }
}
