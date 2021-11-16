package View.New;

import Model.GettingObjects.PlaceDescription;
import Model.GettingObjects.Weather;
import Model.GettingObjects.XidPlace;
import View.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Getter
@Setter
public class InfoPanel {

    private int height;
    private int width;

    private JPanel panel;
    private JTextField textField;
    private List<XidPlace> xidList;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private PlaceDescription description;
    private Weather weather;

    public InfoPanel(int parentHeight, int parentWidth){
        panel = new JPanel(new VerticalLayout());
        height = parentHeight*3/4;
        width = parentWidth/2;
        panel.setBounds(parentWidth/2, parentHeight/4, width, height);

        initComponents();
    }


    public void initComponents(){
        scrollPanel = new JPanel(new VerticalLayout());
        scrollPanel.setSize(new Dimension(width, height));
        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void addNewInfoOnPanel(){
        DescriptionAndWeatherPanel newDescriptionAndWeatherPanel = new DescriptionAndWeatherPanel(
                width, description, weather);
        panel.add(newDescriptionAndWeatherPanel.getPanel());
        panel.revalidate();
    }

    public void resetXidList(List<XidPlace> newXidList){
        xidList = newXidList;
        StringBuilder stringBuilder = new StringBuilder();
        xidList.forEach(x->{
            stringBuilder.append(x.getName()).append("\n");
        });
        textField.setText(stringBuilder.toString());
        panel.revalidate();
    }

    public void setDescriptionAndWeather(PlaceDescription description, Weather weather){
        this.description = description;
        this.weather = weather;
        addNewInfoOnPanel();
    }

    public void refreshPanel(){
        panel.revalidate();
    }
}
