package View;

import Model.GettingObjects.ListOfPlaces;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class TestFrame {

    JFrame frame = new JFrame();

    public TestFrame(ListOfPlaces listOfPlaces){
        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation(0,0);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setTitle("my app");
        MainPanel mainPanel = new MainPanel(listOfPlaces);
        frame.add(mainPanel.getPanel());

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
