package server.netlogic;

import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class NewClientTask implements Runnable{

    private final Socket socket;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public NewClientTask(Socket socket){
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

    }
}
