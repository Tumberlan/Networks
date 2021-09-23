import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.UUID;

public class App {
    private static final int MULTICAST_PORT = 8001;
    private static final UUID OWN_UUID = UUID.randomUUID();
    private static final byte TTL = 120;
    private static final long TIME_FOR_WORK = 100000;
    private static final int TIME_TO_RECEIVE_MESSAGE = 200;
    private static final long TIME_TO_BECOME_UNAVAILABLE = 5000;
    private static final long TIME_TO_SEND_MESSAGE = 4000;
    private static final int UUID_BYTE_LENGTH = 16;
    private final InetAddress groupAddress;
    private Long startTime;
    private int numberOfCopies = 0;
    private long timeOfLastSending = 0;
    private HashMap<String, Long> connectionsList = new HashMap<String, Long>();


    public App(String address) throws UnknownHostException {
        groupAddress = InetAddress.getByName(address);
    }

    public void run() {
        String decodedReceivedCode = null;


        startTime = System.currentTimeMillis();
        MulticastSocket multicastSocket = null;
        try {
            multicastSocket = new MulticastSocket(MULTICAST_PORT);
            multicastSocket.joinGroup(groupAddress);
            multicastSocket.setSoTimeout(TIME_TO_RECEIVE_MESSAGE);
        }catch (IOException ignored){}

        byte[] code;

        while (isTimeNotRunOutStill()) {
            code = codeUUID(OWN_UUID);
            if ((System.currentTimeMillis() - timeOfLastSending) > TIME_TO_SEND_MESSAGE) {
                DatagramPacket sendingMulticastDatagram = new DatagramPacket(code, code.length, groupAddress, MULTICAST_PORT);
                try {
                    multicastSocket.send(sendingMulticastDatagram, TTL);
                } catch (IOException ignored) {}
                timeOfLastSending = System.currentTimeMillis();
            }
            try {
                DatagramPacket receivedMulticastDatagram = new DatagramPacket(new byte[UUID_BYTE_LENGTH], UUID_BYTE_LENGTH);
                multicastSocket.receive(receivedMulticastDatagram);
                byte[] receivedCode = receivedMulticastDatagram.getData();

                decodedReceivedCode = decodeUUID(receivedCode);

            } catch (IOException invalid) {
            }

            String uniqueUUID = null;
            if (decodedReceivedCode != null) {
                uniqueUUID = decodedReceivedCode;
            }
            cleanConnections();
            connectionsList.put(uniqueUUID, System.currentTimeMillis());
            int newNumberOfCopies = connectionsList.size();
            if (newNumberOfCopies != numberOfCopies) {
                numberOfCopies = newNumberOfCopies;
                printCopiesList();
            }

        }


    }

    private boolean isTimeNotRunOutStill() {
        return TIME_FOR_WORK >= (System.currentTimeMillis() - startTime);
    }

    private void cleanConnections() {
        this.connectionsList.entrySet().removeIf(next -> System.currentTimeMillis()
                - next.getValue() > TIME_TO_BECOME_UNAVAILABLE);
    }

    public String decodeUUID(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        UUID newUUID = new UUID(firstLong, secondLong);
        return newUUID.toString();
    }

    public byte[] codeUUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[UUID_BYTE_LENGTH]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    private void printCopiesList() {
        System.out.println("Copies: ");
        int counter = 0;
        for (var entry : this.connectionsList.entrySet()) {
            if (!entry.getKey().equals(OWN_UUID.toString())) {
                System.out.println(entry.getKey());
                counter++;
            }
        }
        System.out.println("All in all: " + counter + " alive copies");
    }
}