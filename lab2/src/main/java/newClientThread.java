import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

@AllArgsConstructor
public class newClientThread implements Runnable {
    private final Socket socket;
    private final String clientID;

    @SneakyThrows
    @Override
    public void run() {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        MyProtocol newProtocol = new MyProtocol(dataInputStream, dataOutputStream);
        System.out.println("CLIENT " + clientID + " ENDED TRANSFER");
    }
}
