import Model.PossibleVariantsLoader;
import View.MainFrame;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        //MainFrame mainFrame = new MainFrame();
        PossibleVariantsLoader possibleVariantsLoader = new PossibleVariantsLoader();
        possibleVariantsLoader.printAll();
    }
}
