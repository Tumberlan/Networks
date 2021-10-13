import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@AllArgsConstructor
public class Server {
    private final int port;
    private static final int BACKLOG = 100;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    public void start() throws IOException {
        InetAddress address = InetAddress.getByName("localhost");
        ServerSocket serverSocket = new ServerSocket(port, BACKLOG, address);
        System.out.println("SERVER IS STARTED");
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(clientSocket.getInetAddress()).append("-").append(clientSocket.getPort());
                String newId = stringBuilder.toString();
                System.out.println("CLIENT " + newId + " STARTED TRANSFER");
                threadPool.submit(new newClientThread(clientSocket, newId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
