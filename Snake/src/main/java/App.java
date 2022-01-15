import view.MainWindow;

public class App {
    public static void main(String[] args){
        MainWindow mainWindow = new MainWindow();
        mainWindow.createGameField(10,10);
        mainWindow.setVisible(true);
    }

}
