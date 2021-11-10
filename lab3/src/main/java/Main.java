import Model.AppLogic;
import Model.GettingObjects.ListOfPlaces;
import Model.GettingObjects.XidPlace;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        AppLogic appLogic = new AppLogic();
        ListOfPlaces listOfPlaces = appLogic.listOfAddressResponse("en","Москва");
        List<XidPlace> xidList = appLogic.placeListLoadResponse("ru", "14", listOfPlaces);
        xidList.forEach(x->{
            System.out.println(x.getName());
            System.out.println(x.getXid());
        });
        appLogic.takeDescription("ru", xidList);
        appLogic.takeWeather("ru", listOfPlaces);
    }
}
