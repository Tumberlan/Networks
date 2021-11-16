package View;

import Model.GettingObjects.Place;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Slf4j
@Getter
@Setter
public class OneVariantPanel {

    private static final int PANEL_HEIGHT = 100;
    private final String STYLE_heading = "heading";
    private final String STYLE_normal = "normal";
    private final String FONT_style = "Times New Roman";
    private Style heading = null;
    private Style normal = null;

    private JPanel panel;
    private JLabel labelForButton;
    private int parentWidth;
    private  int width;
    private Place place;
    private JTextPane textPane;
    private JButton infoButton;
    GridBagConstraints constraints = new GridBagConstraints();


    public OneVariantPanel(int parentWidth, Place initPlace) {
        panel = new JPanel();
        this.parentWidth = parentWidth;
        width = parentWidth - 25;
        this.place = initPlace;
        panel.setPreferredSize(new Dimension(width, PANEL_HEIGHT));
        panel.setForeground(Color.YELLOW);
        initComponents();
    }

    private void initComponents(){
        textPane = new JTextPane();
        createStyles();
        loadText(makeDescriptionString());
        textPane.setEditable(false);
        panel.add(textPane);

        infoButton = new JButton("Info");
        panel.add(infoButton);
    }

    private void createStyles() {
        normal = textPane.addStyle(STYLE_normal, null);
        StyleConstants.setFontFamily(normal, FONT_style);
        StyleConstants.setFontSize(normal, 12);

        heading = textPane.addStyle(STYLE_heading, normal);
        StyleConstants.setFontSize(heading, 18);
        StyleConstants.setBold(heading, true);
    }

    private void loadText(String[][] text) {
        for (String[] strings : text) {
            Style style = (strings[1].equals(STYLE_heading)) ?
                    heading : normal;
            try {
                Document doc = textPane.getDocument();
                doc.insertString(doc.getLength(), strings[0], style);
            } catch (Exception e) {
                log.error("can't load text on textPane");
            }
        }
    }

    private String[][] makeDescriptionString() {
        return new String[][]{
                {this.place.getName() + "\r\n", "heading"},
                {"State: " + notNullAddToStringBuilder(this.place.getState()), "normal"},
                {" , Country: " + notNullAddToStringBuilder(this.place.getCountry()), "normal"},
                {" (" + notNullAddToStringBuilder(this.place.getCountryCode()) + ")\r\n", "normal"},
                {"City: " + notNullAddToStringBuilder(this.place.getCity()), "normal"},
                {" , Street: " + notNullAddToStringBuilder(this.place.getStreet()), "normal"},
                {" , House: " + notNullAddToStringBuilder(this.place.getHouseNumber()), "normal"}
        };
    }

    private String notNullAddToStringBuilder(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return ("-");
        }
    }

    public void addActionListenerToInfoButton(ActionListener actionListener){
        infoButton.addActionListener(actionListener);
    }

}
