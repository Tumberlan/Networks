import Model.AppLogic;
import Model.GettingObjects.ListOfPlaces;
import Model.PossibleVariantsLoader;
import View.MainFrame;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        AppLogic appLogic = new AppLogic();
        ListOfPlaces listOfPlaces = appLogic.listOfAddressResponse("Novosibirsk theatre",
                "en");
        appLogic.placeListLoadResponse("ru", "14", listOfPlaces);
    }
}
