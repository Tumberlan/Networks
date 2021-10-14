import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
@Slf4j
public class Client {

    private final InetAddress IPAddress;
    private final int port;
    private final String pathToFile;
    private final Path path;

    private static final int BUFFER_SIZE = 1024;
    private static final int NO_DATA_TO_SEND = 0;
    private static final int STARTING_BUFFER_POINT = 0;

    private final ProtokolCodes protokolCodes = new ProtokolCodes();


    public Client(InetAddress inetAddress, int port, String pathToFile) {
        this.IPAddress = inetAddress;
        this.port = port;
        this.pathToFile = pathToFile;
        path = Paths.get(this.pathToFile);
    }

    public void run() {
        try {
            log.info("FILE TRANSFER IS STARTED");
            Socket clientSocket = new Socket(this.IPAddress, port);
            FileInputStream fileInputStream = new FileInputStream(pathToFile);
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            boolean goNext = sendFileName(dataInputStream, dataOutputStream);
            if (!goNext) {
                return;
            }
            sendFileSize(dataOutputStream);
            sendFileContent(fileInputStream, dataInputStream, dataOutputStream);
        } catch (IOException e) {
            log.error("SERVER IS NOT ON");
        }
    }


    private boolean sendFileName(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        String fileName = path.getFileName().toString();
        dataOutputStream.writeInt(fileName.length());
        dataOutputStream.writeUTF(fileName);
        dataOutputStream.flush();

        int response = dataInputStream.readInt();
        if (response == protokolCodes.
                wrapCodeToInt(ProtokolCodes.CodeValue.NAME_DOES_NOT_MATCH_WITH_NAME_LENGTH)) {
            return false;
        }
        return true;
    }

    private void sendFileSize(DataOutputStream dataOutputStream) throws IOException {
        long fileSize = Files.size(path);
        dataOutputStream.writeLong(fileSize);
        dataOutputStream.flush();
    }

    private void sendFileContent(FileInputStream inputStream, DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        byte[] sendingBuffer = new byte[BUFFER_SIZE];
        int tmpReadBytes = inputStream.read(sendingBuffer, STARTING_BUFFER_POINT, BUFFER_SIZE);
        while (NO_DATA_TO_SEND < tmpReadBytes) {
            dataOutputStream.write(sendingBuffer, STARTING_BUFFER_POINT, tmpReadBytes);
            dataOutputStream.flush();
            tmpReadBytes = inputStream.read(sendingBuffer, STARTING_BUFFER_POINT, BUFFER_SIZE);
        }

        int response = dataInputStream.readInt();
        if (response == protokolCodes.
                wrapCodeToInt(ProtokolCodes.CodeValue.SIZE_OF_FILE_DOES_NOT_MATCH_WITH_FILE_SIZE)) {
            log.info("SIZE OF FILE DOES NOT MATCH WITH FILE SIZE");
            return;
        } else if (response == protokolCodes.
                wrapCodeToInt(ProtokolCodes.CodeValue.FILE_SUCCESSFULLY_READ)) {
            log.info("FILE SUCCESSFULLY READ");
        }
    }
}
