import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class MyProtocol {
    private static final int SEND_TIMING_CYCLE_TIME = 3;
    private static final int BUFFER_SIZE = 1024;
    private static final int RIGHT_TIME_COEF = 1000;
    private static final String FILES_PATH = "receivedFiles";

    private String fileName;
    private final long fileSize;
    private File file;
    private long startTime;
    private long tmpTime = 0;
    private long previousTime;
    private long bytesRead;
    private long bytesReadAtLastIteration;
    private boolean cycleNotEnd;
    private ProtokolCodes protokolCodes = new ProtokolCodes();

    public MyProtocol(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException, InterruptedException {
        int nameLength = takeNameLength(dataInputStream);
        fileName = takeName(dataInputStream, dataOutputStream, nameLength);
        fileSize = takeFileSize(dataInputStream);
        file = createFile();
        takeFile(file, dataInputStream, dataOutputStream, fileSize);
    }

    private int takeNameLength(DataInputStream dataInputStream) throws IOException {
        int nameLength = dataInputStream.readInt();
        return nameLength;
    }

    private String takeName(DataInputStream dataInputStream, DataOutputStream dataOutputStream, int nameLength) throws IOException {
        String name = dataInputStream.readUTF();
        if (nameLength != name.length()) {
            dataOutputStream.writeInt(protokolCodes.
                    wrapCodeToInt(ProtokolCodes.CodeValue.NAME_DOES_NOT_MATCH_WITH_NAME_LENGTH));
        } else {
            dataOutputStream.writeInt(protokolCodes.
                    wrapCodeToInt(ProtokolCodes.CodeValue.NAME_SUCCESSFULLY_READ));
        }
        return name;
    }

    private long takeFileSize(DataInputStream dataInputStream) throws IOException {
        long size = dataInputStream.readLong();
        return size;
    }

    private void takeFile(File file, DataInputStream dataInputStream, DataOutputStream dataOutputStream, long fileSize) throws IOException, InterruptedException {
        byte[] buffer = new byte[BUFFER_SIZE];
        bytesRead = 0;
        bytesReadAtLastIteration = 0;
        startTime = System.currentTimeMillis();
        previousTime = startTime;
        cycleNotEnd = true;
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new SpeedSendThread(), 2, SEND_TIMING_CYCLE_TIME, TimeUnit.SECONDS);
        while (bytesRead < fileSize) {
            int tmpBytesRead = dataInputStream.read(buffer, 0, BUFFER_SIZE);
            if (0 < tmpBytesRead) {
                fileOutputStream.write(buffer, 0, tmpBytesRead);
            }
            bytesRead += tmpBytesRead;
            if (bytesRead >= fileSize) {
                cycleNotEnd = false;
            }
        }

        if (fileSize != bytesRead) {
            dataOutputStream.writeInt(protokolCodes.
                    wrapCodeToInt(ProtokolCodes.CodeValue.SIZE_OF_FILE_DOES_NOT_MATCH_WITH_FILE_SIZE));
        } else {
            dataOutputStream.writeInt(protokolCodes.
                    wrapCodeToInt(ProtokolCodes.CodeValue.FILE_SUCCESSFULLY_READ));
        }
        scheduledExecutorService.awaitTermination(SEND_TIMING_CYCLE_TIME, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

    private File createFile() throws IOException {
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

    private String makeUniqueName(String fileName) {
        String[] subString = fileName.split("\\.");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(subString[0]).append("_").append(UUID.randomUUID()).append(".").append(subString[1]);
        return stringBuilder.toString();
    }


    private class SpeedSendThread implements Runnable {
        @Override
        public void run() {
            tmpTime = System.currentTimeMillis();
            updateTime();
        }
    }

    private void updateTime() {
        log.info("---------------TIME CYCLE INFORMATION---------------");
        long speed;
        long averageSpeed;
        if (tmpTime == previousTime) {
            speed = (bytesRead - bytesReadAtLastIteration);
            averageSpeed = bytesRead;
        } else {
            speed = (bytesRead - bytesReadAtLastIteration) * RIGHT_TIME_COEF / (tmpTime - previousTime);
            averageSpeed = bytesRead * RIGHT_TIME_COEF / (tmpTime - startTime);
        }
        log.info("SPEED: " + speed + " BIT IN 3 SEC");
        log.info("AVERAGE SPEED: " + averageSpeed + " BIT IN " + (tmpTime - startTime) / RIGHT_TIME_COEF + " SEC");
        previousTime = tmpTime;
        bytesReadAtLastIteration = bytesRead;
    }
}
