import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Main {

    private static final String TAKING_FILE_PATH = "receivedFiles";

    public static void main(String[] args) throws IOException {

        if (args[0].equals("c")) {
            startClient(args);
        }
        if (args[0].equals("s")) {
            startServer(args);
        }
    }

    private static void startClient(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(args[1]);
        System.out.println(args[3]);
        Client client = new Client(address, Integer.parseInt(args[2]), args[3]);
        client.run();
    }

    private static void startServer(String[] args) throws IOException {
        Cleaner cleaner = new Cleaner(TAKING_FILE_PATH);
        cleaner.clean();
        Server server = new Server(Integer.parseInt(args[1]));
        server.start();
    }
}
