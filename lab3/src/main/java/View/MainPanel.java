package View;

import Model.GettingObjects.ListOfPlaces;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class MainPanel{

    private JPanel panel;
    private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private JButton btnAddPage;
    private ListOfPlaces listOfPlaces;

    public MainPanel(ListOfPlaces newList){
        this.listOfPlaces = newList;
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 140));
        panel.setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents(){
        scrollPanel = new JPanel(new VerticalLayout());
        scrollPanel.setSize(new Dimension(300, 300));
        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listOfPlaces.getPlaceList().forEach(x->{
            JPanel tmpPanel = new OneVariantPanel(400,x).getPanel();
            addPanel(tmpPanel);
            scrollPanel.revalidate();
        });

        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private class Page extends JPanel
    {
        private static int idx = 1;
        private JLabel pageContent;
        public Page(){
            setPreferredSize(new Dimension(80, 300));
            setBackground(Color.YELLOW);
            pageContent = new JLabel("Page " + (idx++));
            add(pageContent);
        }
    }

    public void addPanel(JPanel pnl){
        scrollPanel.add(pnl);
    }
}
