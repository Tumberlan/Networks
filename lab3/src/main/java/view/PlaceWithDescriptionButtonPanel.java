package view;

import model.gettingobjects.XidPlace;
import view.utils.MyTextPane;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
@Setter
public class PlaceWithDescriptionButtonPanel {

    private static final int PANEL_HEIGHT = 100;
    private static final int PANEL_WIDTH_VISIBLE_OFFSET_FIX = -25;

    private int width;

    private JPanel panel;
    private JLabel labelForButton;
    private XidPlace place;
    private MyTextPane textPane;
    private JButton descriptionButton;
    private GridBagConstraints constraints = new GridBagConstraints();
    private boolean isEmpty = false;

    public PlaceWithDescriptionButtonPanel(int parentWidth, Object object) {
        panel = new JPanel();
        calculateDimensions(parentWidth);
        if (object.getClass() == XidPlace.class) {
            place = (XidPlace) object;
        } else {
            isEmpty = true;
        }
        //place = newPlace;

        panel.setPreferredSize(new Dimension(width, PANEL_HEIGHT));
        panel.setForeground(Color.YELLOW);
        initComponents();
    }

    private void initComponents() {
        textPane = new MyTextPane();
        textPane.setStyles();
        if (!isEmpty) {
            textPane.setText(new String[][]{{textPane.makeStringFormat(nameCheck(place.getName()),
                    true), "heading"}});
        } else {
            textPane.setText(new String[][]{{"NO MAPPED PLACES FOUND", "heading"}});
        }
        panel.add(textPane.getTextPane());
        if (!isEmpty) {
            descriptionButton = new JButton("Info");
            panel.add(descriptionButton);
        }
    }

    private String nameCheck(String name) {
        if (null == name || "".equals(name)) {
            return "NO NAME";
        } else {
            return name;
        }
    }

    public void addActionListenerToDescriptionButton(ActionListener actionListener) {
        descriptionButton.addActionListener(actionListener);
    }

    private void calculateDimensions(int parentWidth) {
        width = parentWidth + PANEL_WIDTH_VISIBLE_OFFSET_FIX;
    }
}
