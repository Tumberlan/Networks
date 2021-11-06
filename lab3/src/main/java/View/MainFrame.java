package View;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final int START_FRAME_LOCATION_X = 0;
    private static final int START_FRAME_LOCATION_Y = 0;
    private static final int TEXT_FIELD_LOCATION_X = 100;
    private static final int TEXT_FIELD_LOCATION_Y = 400;
    private static final int TEXT_FIELD_HEIGHT = 50;
    private static final int TEXT_FIELD_WIDTH = 300;
    private JPanel panel = new JPanel(new GridBagLayout());

    public MainFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(START_FRAME_LOCATION_X, START_FRAME_LOCATION_Y);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setPreferredSize(screenSize);
        this.setSize(screenSize);
        this.setTitle("my app");
//this.setResizable(false);

        this.getContentPane().add(panel, BorderLayout.SOUTH);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10,10,10,5);
        JLabel inputText = new JLabel("Keyword or phrase");
        JTextField textField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(inputText, constraints);
        constraints.gridx = 4;
        panel.add(textField, constraints);
//textField.setFont(textField.getFont().deriveFont(50f));

        //panel.add(textField);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}