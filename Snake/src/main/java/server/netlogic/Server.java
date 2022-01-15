package server.netlogic;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Server {

    private static final int BACKLOG = 100;
    private static final int MULTICAST_PORT = 9192;
    private static final boolean SERVER_IS_ON = true;


    private final int port;
    private final InetAddress multicastAddress;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private DataOutputStream multicastOutputStream;

    @SneakyThrows
    public Server(int port){
        this.port = port;
        multicastAddress = InetAddress.getByName("239.192.0.4");
    }

    @SneakyThrows
    public void start(){
        InetAddress address = InetAddress.getByName("localhost");
        ServerSocket serverSocket = new ServerSocket(port, BACKLOG, address);
        Socket multicastSocket = new Socket(multicastAddress, MULTICAST_PORT);
        multicastOutputStream = new DataOutputStream(multicastSocket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (SERVER_IS_ON) {
                    try {
                        multicastOutputStream.writeUTF("");
                        this.wait(1000);
                    }catch (InterruptedException e){
                        log.error("THREAD CAN'T WAIT");
                        break;
                    } catch (IOException e) {
                        log.error("UNABLE TO WRITE TO MULTICAST ADDRESS");
                        break;
                    }
                }
            }
        }).start();

        log.info("SERVER STARTED");
        while(!serverSocket.isClosed()){
            Socket clientSocket = serverSocket.accept();
            //threadPool.submit();
        }
        threadPool.shutdown();
    }
}
