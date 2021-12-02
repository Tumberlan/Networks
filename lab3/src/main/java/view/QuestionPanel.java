package view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
@Setter
public class QuestionPanel {

    private int height;
    private int width;
    private int xCoord;
    private int yCoord;
    private String address = null;

    private JPanel panel;
    private JLabel label;
    private JTextField addressTextField;
    private JButton button;
    private GridBagConstraints constraints = new GridBagConstraints();

    public QuestionPanel(int parentHeight, int parentWidth) {
        panel = new JPanel();
        calculateDimensions(parentWidth, parentHeight);
        panel.setBounds(xCoord, yCoord, width, height);
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel.setLayout(new GridBagLayout());
        initComponents();
    }

    private void initComponents() {
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = 0;
        constraints.gridx = 0;
        label = new JLabel("Please, put here place that you want found");
        panel.add(label, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 250;
        addressTextField = new JTextField();
        panel.add(addressTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        button = new JButton("Search");
        panel.add(button, constraints);
    }

    public void addActionListenerToSearchButton(ActionListener searchButtonListener) {
        button.addActionListener(searchButtonListener);
    }

    public String getAddressFromTextField() {
        return addressTextField.getText();
    }

    private void calculateDimensions(int parentWidth, int parentHeight) {
        width = parentWidth;
        height = parentHeight / 4;
        xCoord = 0;
        yCoord = 0;
    }
}
