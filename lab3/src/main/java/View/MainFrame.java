package View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final int START_FRAME_LOCATION_X = 0;
    private static final int START_FRAME_LOCATION_Y = 0;
    private static final int TEXT_FIELD_LOCATION_X = 100;
    private static final int TEXT_FIELD_LOCATION_Y = 400;
    private static final int TEXT_FIELD_HEIGHT = 50;
    private static final int TEXT_FIELD_WIDTH = 300;
    private static final JPanel panel = new JPanel(new GridBagLayout());
    private static final JTextField textField = new JTextField();

    public MainFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(START_FRAME_LOCATION_X, START_FRAME_LOCATION_Y);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
        this.setSize(screenSize);
        this.setTitle("my app");
        //this.setResizable(false);

        this.getContentPane().add(panel);
        GridBagConstraints constraints = new GridBagConstraints();


        //textField.setBounds(TEXT_FIELD_LOCATION_X, TEXT_FIELD_LOCATION_Y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        //textField.setBounds(0, 0, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        textField.setText("Put here your text");
        //textField.setFont(textField.getFont().deriveFont(50f));

        panel.add(textField);
        panel.setPreferredSize(screenSize);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}