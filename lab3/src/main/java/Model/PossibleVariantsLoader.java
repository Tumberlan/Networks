package Model;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

@Slf4j
public class PossibleVariantsLoader {

    private final URI dataBaseAddress;
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public PossibleVariantsLoader() throws URISyntaxException {
        dataBaseAddress = new URI("https://docs.graphhopper.com/#operation/getGeocode");
    }
    public HttpResponse loadVariants(){
        HttpGet httpGet = new HttpGet(dataBaseAddress);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            log.error("no response");
        }
        return httpResponse;
    }

    public void printAll(){

        HttpResponse response1 = loadVariants();

        Scanner sc = null;
        try {
            sc = new Scanner(response1.getEntity().getContent());
        } catch (IOException e) {
            log.error("Can't invoke scanner");
        }

        //Printing the status line
        System.out.println(response1.getStatusLine());
        while(sc.hasNext()) {
            System.out.println(sc.nextLine());
        }
    }
}
