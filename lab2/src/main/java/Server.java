import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@AllArgsConstructor
@Slf4j
public class Server {
    private final int port;
    private static final int BACKLOG = 100;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    public void start() throws IOException {
        InetAddress address = InetAddress.getByName("localhost");
        ServerSocket serverSocket = new ServerSocket(port, BACKLOG, address);
        log.info("SERVER IS STARTED");
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(clientSocket.getInetAddress().toString()).append("-").append(clientSocket.getPort());
                String newId = stringBuilder.toString();
                log.info("CLIENT " + newId + " STARTED TRANSFER");
                threadPool.submit(new FileDownloadHandlerThread(clientSocket, newId));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
