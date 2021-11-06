package Model;

import java.net.URI;
import java.net.URISyntaxException;

public class PossibleVariantsLoader {

    private final URI dataBaseURI;


    public PossibleVariantsLoader() throws URISyntaxException {
        dataBaseURI = new URI("https://docs.graphhopper.com/#operation/getGeocode");
    }


}
