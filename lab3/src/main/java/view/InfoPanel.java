package view;

import model.gettingobjects.PlaceDescription;
import model.gettingobjects.XidPlace;
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
    private List<PlaceWithDescriptionButtonPanel> placeWithDescriptionButtonPanelList;
    private List<XidPlace> xidPlaceList;
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

    public void setXidPlaceList(List<XidPlace> xidList) {
        SwingUtilities.invokeLater(() -> {
            panel.remove(scrollPane);
            initComponents();
            if (xidList != null) {
                placeWithDescriptionButtonPanelList = new LinkedList<PlaceWithDescriptionButtonPanel>();
            }
            xidPlaceList = xidList;
            processListInfo();
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.revalidate();
            panel.revalidate();
        });
    }

    private void processListInfo() {
        if (xidPlaceList != null) {
            xidPlaceList.forEach(this::addPlaceOnPanel);
            if (xidPlaceList.size() == 0) {
                addNoPlacesOnPanel();
            } else {
                addPlacesFromListToPanel();
            }
        }

    }

    private void addNoPlacesOnPanel() {
        PlaceWithDescriptionButtonPanel newPlaceWithDescriptionButtonPanel =
                new PlaceWithDescriptionButtonPanel(width, "");
        scrollPanel.add(newPlaceWithDescriptionButtonPanel.getPanel());
        scrollPanel.revalidate();
    }

    private void addPlaceOnPanel(XidPlace place) {
        PlaceWithDescriptionButtonPanel newPlaceWithDescriptionButtonPanel =
                new PlaceWithDescriptionButtonPanel(width, place);
        placeWithDescriptionButtonPanelList.add(newPlaceWithDescriptionButtonPanel);
    }

    private void addPlacesFromListToPanel() {
        placeWithDescriptionButtonPanelList.forEach(x -> {
            scrollPanel.add(x.getPanel());
            scrollPanel.revalidate();
        });
    }

    public void addActionListenerToInfoOnOnePlace(ActionListener actionListener,
                                                  String xid) {

        SwingUtilities.invokeLater(() -> {
            placeWithDescriptionButtonPanelList.forEach(x -> {
                if (x.getPlace().getXid().equals(xid)) {
                    x.addActionListenerToDescriptionButton(actionListener);
                }
            });

            panel.revalidate();
        });
    }

    private void calculateDimensions(int parentWidth, int parentHeight) {
        width = parentWidth / 2;
        height = parentHeight / 2;
        xCoord = parentWidth / 2;
        yCoord = parentHeight / 2;
    }
}
