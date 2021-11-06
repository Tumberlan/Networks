import Model.PossibleVariantsLoader;
import View.MainFrame;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        //MainFrame mainFrame = new MainFrame();
        PossibleVariantsLoader possibleVariantsLoader = new PossibleVariantsLoader();
        possibleVariantsLoader.printAll();
    }
}
