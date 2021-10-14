import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

@AllArgsConstructor
@Slf4j
public class FileDownloadHandlerThread implements Runnable {
    private final Socket socket;
    private final String clientID;

    @SneakyThrows
    @Override
    public void run() {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        MyProtocol newProtocol = new MyProtocol(dataInputStream, dataOutputStream);
        log.info("CLIENT " + clientID + " ENDED TRANSFER");
    }
}

