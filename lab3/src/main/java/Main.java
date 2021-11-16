import java.io.IOException;
import java.net.URISyntaxException;


public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        /*AppLogic appLogic = new AppLogic();
        ListOfPlaces listOfPlaces = appLogic.listOfAddressResponse("en","Москва");
        List<XidPlace> xidList = appLogic.placeListLoadResponse("ru", "14", listOfPlaces);
        xidList.forEach(x->{
            System.out.println(x.getName());
            System.out.println(x.getXid());
        });
        appLogic.takeDescription("ru", xidList);
        appLogic.takeWeather("ru", listOfPlaces);*/

        //AppLogic appLogic = new AppLogic();
        //FirstFrame firstFrame = new FirstFrame(appLogic);
        //MainFrame mainFrame = new MainFrame(appLogic);
        //ListOfPlaces listOfPlaces = appLogic.listOfAddressResponse("moscow");

        App app = new App();
        app.start();

        //TestFrame testFrame = new TestFrame(listOfPlaces);
    }
}
