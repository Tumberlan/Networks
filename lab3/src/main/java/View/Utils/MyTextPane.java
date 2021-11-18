package View.Utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;

@Slf4j
@Getter
@Setter
public class MyTextPane {

    private final String STYLE_heading = "heading";
    private final String STYLE_normal = "normal";
    private final String STYLE_new_paragraph = "new_paragraph";
    private final String FONT_style = "Times New Roman";
    private Style heading = null;
    private Style normal = null;
    private Style new_paragraph = null;

    private JTextPane textPane;

    public MyTextPane() {
        textPane = new JTextPane();
        textPane.setEditable(false);
    }

    public void setStyles() {
        createStyles();
    }

    public void setSize(Dimension dimension) {
        textPane.setSize(dimension);
    }

    public void setText(String[][] text) {
        loadText(text);
    }

    private void createStyles() {
        normal = textPane.addStyle(STYLE_normal, null);
        StyleConstants.setFontFamily(normal, FONT_style);
        StyleConstants.setFontSize(normal, 12);

        new_paragraph = textPane.addStyle(STYLE_new_paragraph, normal);
        StyleConstants.setFontSize(new_paragraph, 16);
        StyleConstants.setItalic(new_paragraph, true);

        heading = textPane.addStyle(STYLE_heading, normal);
        StyleConstants.setFontSize(heading, 18);
        StyleConstants.setBold(heading, true);
    }

    private void loadText(String[][] text) {
        for (String[] strings : text) {
            Style style = getStyle(strings[1]);
            try {
                Document doc = textPane.getDocument();
                doc.insertString(doc.getLength(), strings[0], style);
            } catch (Exception e) {
                log.error("can't load text on textPane");
            }
        }
    }

    private Style getStyle(String styleStr) {
        switch (styleStr) {
            case STYLE_heading -> {
                return heading;
            }
            case STYLE_new_paragraph -> {
                return new_paragraph;
            }
            case STYLE_normal -> {
                return normal;
            }
        }
        return normal;
    }
}
