package view;

import model.gettingobjects.PlaceDescription;
import view.utils.MyTextPane;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class DescriptionOptionPanel {

    private JOptionPane optionPane;
    private PlaceDescription description;
    private MyTextPane textPane;

    public DescriptionOptionPanel(PlaceDescription placeDescription) {
        this.description = placeDescription;
        textPane = new MyTextPane();
        textPane.setStyles();
        textPane.setText(makeDescriptionString(false));
    }

    public JTextPane getContent() {
        return textPane.getTextPane();
    }

    private String[][] makeDescriptionString(boolean isEmpty) {
        if (isEmpty) {
            return new String[][]{
                    {parseDescriptionsAmount(false), "heading"}
            };
        }
        return new String[][]{
                {nameCheck(description.getName()) + "\r\n", "heading"},
                {"Description:\r\n", "new_paragraph"},
                {"Default information: " + textPane.makeStringFormat(parseDescription(), false)
                        + "\r\n", "normal"},
                {"Wikipedia information:\r\n" + textPane.makeStringFormat(parseWikiInfo(), false),
                        "normal"}
        };
    }

    private String parseDescription() {
        if (description.getInfo() != null) {
            return notNullAddToStringBuilder(description.getInfo().getDescr());
        }
        return "-";
    }

    private String nameCheck(String name) {
        if (null == name || "".equals(name)) {
            return "NO NAME";
        } else {
            return name;
        }
    }

    private String parseWikiInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        if (description.getWikiInfo() != null) {
            stringBuilder.append("Title: ").append(notNullAddToStringBuilder(description.
                    getWikiInfo().getTitle())).append("\r\n").append(notNullAddToStringBuilder(
                    description.getWikiInfo().getText())).append("\r\n");
        } else {
            stringBuilder.append("-\r\n");
        }
        return stringBuilder.toString();
    }

    private String notNullAddToStringBuilder(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return ("-");
        }
    }

    private String parseDescriptionsAmount(boolean isEmpty) {
        if (isEmpty) {
            return "THAT POINT HAS NO MAPPED PLACES!";
        } else {
            return "";
        }
    }
}
