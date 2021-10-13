import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyProtocol {

    private String fileName;
    private final long fileSize;
    private File file;

    private static final int NAME_SUCCESSFULLY_READ = 2;
    private static final int FILE_SUCCESSFULLY_READ = 4;
    private static final int NAME_DOES_NOT_MATCH_WITH_NAME_LENGTH = 11;
    private static final int SIZE_OF_FILE_DOES_NOT_MATCH_WITH_FILE_SIZE = 12;

    private static final int SEND_TIMING_CYCLE_TIME = 3000;

    private long startTime;
    private long tmpTime = 0;
    private long previousTime;

    long bytesRead;
    long bytesReadAtLastIteration;
    private static final String FILES_PATH = "receivedFiles";

    private static final int BUFFER_SIZE = 1024;

    public MyProtocol(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {

        int nameLength = takeNameLength(dataInputStream);
        fileName = takeName(dataInputStream, dataOutputStream, nameLength);
        fileSize = takeFileSize(dataInputStream);
        file = createFile();
        takeFile(file, dataInputStream, dataOutputStream, fileSize);
    }

    public int takeNameLength(DataInputStream dataInputStream) throws IOException {
        int nameLength = dataInputStream.readInt();
        return nameLength;
    }

    public String takeName(DataInputStream dataInputStream, DataOutputStream dataOutputStream, int nameLength) throws IOException {
        String name = dataInputStream.readUTF();
        if (nameLength != name.length()) {
            dataOutputStream.writeInt(NAME_DOES_NOT_MATCH_WITH_NAME_LENGTH);
        } else {
            dataOutputStream.writeInt(NAME_SUCCESSFULLY_READ);
        }
        return name;
    }

    public long takeFileSize(DataInputStream dataInputStream) throws IOException {
        long size = dataInputStream.readLong();
        return size;
    }

    public void takeFile(File file, DataInputStream dataInputStream, DataOutputStream dataOutputStream, long fileSize) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        bytesRead = 0;
        bytesReadAtLastIteration = 0;
        startTime = System.currentTimeMillis();
        previousTime = startTime;

        boolean cycleNotEnd = true;
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        while (bytesRead < fileSize) {
            int tmpBytesRead = dataInputStream.read(buffer, 0, BUFFER_SIZE);
            if (0 < tmpBytesRead) {
                fileOutputStream.write(buffer, 0, tmpBytesRead);
            }
            bytesRead += tmpBytesRead;

            tmpTime = System.currentTimeMillis();
            if (SEND_TIMING_CYCLE_TIME < (tmpTime - previousTime)) {
                cycleNotEnd = updateTime();
            }
        }

        if (cycleNotEnd) {
            long speed = bytesRead / (System.currentTimeMillis() - startTime);
            System.out.println("SPEED: " + speed + " BIT IN "+ (System.currentTimeMillis() - startTime) + " SEC");
        }

        if (fileSize != bytesRead) {
            dataOutputStream.writeInt(SIZE_OF_FILE_DOES_NOT_MATCH_WITH_FILE_SIZE);
        } else {
            dataOutputStream.writeInt(FILE_SUCCESSFULLY_READ);
        }
    }

    public File createFile() throws IOException {
        Path path = Paths.get(FILES_PATH);
        List<Path> paths = FileLister.listFiles(path);
        AtomicBoolean isNameTaken = new AtomicBoolean(false);
        paths.forEach(x -> {
            if (fileName.equals(x.getFileName().toString())) {
                isNameTaken.set(true);
            }
        });
        if (isNameTaken.get()) {
            fileName = makeUniqueName(fileName);
        }
        return new File(FILES_PATH, fileName);
    }

    public String makeUniqueName(String fileName) {
        String[] subString = fileName.split("\\.");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(subString[0]).append("_").append(UUID.randomUUID()).append(".").append(subString[1]);
        return stringBuilder.toString();
    }

    public boolean updateTime() {
        System.out.println("---------------TIME CYCLE INFORMATION---------------");
        long speed = (bytesRead - bytesReadAtLastIteration) / (tmpTime - previousTime);
        System.out.println("SPEED: " + speed + " BIT IN 3 SEC");
        long averageSpeed = bytesRead / (tmpTime - startTime);
        System.out.println("AVERAGE SPEED: " + averageSpeed + " BIT IN " + (tmpTime - startTime) + " SEC");
        previousTime = tmpTime;
        bytesReadAtLastIteration = bytesRead;
        return false;
    }


}
