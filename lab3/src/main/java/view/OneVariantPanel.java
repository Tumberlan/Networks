package view;

import model.gettingobjects.Place;
import view.utils.MyTextPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Slf4j
@Getter
@Setter
public class OneVariantPanel {

    private static final int PANEL_HEIGHT = 100;
    private static final int PANEL_WIDTH_VISIBLE_OFFSET_FIX = -25;

    private int width;

    private JPanel panel;
    private JLabel labelForButton;
    private Place place;
    private MyTextPane textPane;
    private JButton infoButton;
    private GridBagConstraints constraints = new GridBagConstraints();

    public OneVariantPanel(int parentWidth, Place initPlace) {
        panel = new JPanel();
        calculateDimensions(parentWidth);
        place = initPlace;
        panel.setPreferredSize(new Dimension(width, PANEL_HEIGHT));
        panel.setForeground(Color.YELLOW);
        initComponents();
    }

    private void initComponents() {
        textPane = new MyTextPane();
        textPane.setStyles();
        textPane.setText(makeDescriptionString());
        panel.add(textPane.getTextPane());
        infoButton = new JButton("Info");
        panel.add(infoButton);
    }

    private String[][] makeDescriptionString() {
        return new String[][]{
                {textPane.makeStringFormat(nameCheck(this.place.getName()), true) + "\r\n",
                        "heading"},
                {"State: " + notNullAddToStringBuilder(this.place.getState()), "normal"},
                {" , Country: " + notNullAddToStringBuilder(this.place.getCountry()), "normal"},
                {" (" + notNullAddToStringBuilder(this.place.getCountryCode()) + ")\r\n", "normal"},
                {"City: " + notNullAddToStringBuilder(this.place.getCity()), "normal"},
                {" , Street: " + notNullAddToStringBuilder(this.place.getStreet()), "normal"},
                {" , House: " + notNullAddToStringBuilder(this.place.getHouseNumber()), "normal"}
        };
    }

    private String nameCheck(String name) {
        if (null == name || "".equals(name)) {
            return "NO NAME";
        } else {
            return name;
        }
    }

    private String notNullAddToStringBuilder(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return ("-");
        }
    }

    public void addActionListenerToInfoButton(ActionListener actionListener) {
        infoButton.addActionListener(actionListener);
    }

    private void calculateDimensions(int parentWidth) {
        width = parentWidth + PANEL_WIDTH_VISIBLE_OFFSET_FIX;
    }
}
