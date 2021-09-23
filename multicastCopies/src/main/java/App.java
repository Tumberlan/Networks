import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;

public class App{
    private final int multicastPort = 8001;
    static private final UUID uuid = UUID.randomUUID();
    private final byte ttl = 120;
    private final long timeForWork = 200000;
    private final int timeToReceiveMessage = 200;
    private final long timeToBecomeUnavailable = 5000;
    private final long timeToSendMessage = 4000;
    private Long startTime;
    private int numberOfCopies = 0;
    private long timeOfLastSending = 0;
    private HashMap<String,Long> connectionsList = new HashMap<String, Long>();

    public void run() {
        String decodedReceivedCode = null;

        try {
            startTime = System.currentTimeMillis();
            InetAddress groupAddress = InetAddress.getByName("224.1.1.1");
            MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
            multicastSocket.joinGroup(groupAddress);

            multicastSocket.setSoTimeout(timeToReceiveMessage);
            byte[] code = new byte[16];

            while (workTime()){
                boolean isReadyToSend = codeUUID(code);
                if(isReadyToSend && (System.currentTimeMillis() - timeOfLastSending) > timeToSendMessage ){
                    DatagramPacket sendingMulticastDatagram = new DatagramPacket(code, code.length, groupAddress, multicastPort);
                    multicastSocket.send(sendingMulticastDatagram, ttl);
                    timeOfLastSending = System.currentTimeMillis();
                }
                try{
                    DatagramPacket receivedMulticastDatagram = new DatagramPacket(new byte[16], 16);
                    multicastSocket.receive(receivedMulticastDatagram);
                    byte[] receivedCode = receivedMulticastDatagram.getData();

                    decodedReceivedCode = decodeUUID(receivedCode);
                    if (!isUUID(decodedReceivedCode)) {
                        throw new InvalidObjectException("");
                    }
                }catch (IOException invalid){
                }

                String uniqueUUID = null;
                if(!(decodedReceivedCode == null)){
                    uniqueUUID = decodedReceivedCode;
                }
                connectionsCleaner(System.currentTimeMillis());
                connectionsList.put(uniqueUUID, System.currentTimeMillis());
                int newNumberOfCopies = connectionsList.size();
                if (newNumberOfCopies != numberOfCopies) {
                    numberOfCopies = newNumberOfCopies;
                    printCopiesList();
                }

            }

        } catch (IOException ignored) {}

    }



    private boolean workTime(){
        return timeForWork >= (System.currentTimeMillis()-startTime);
    }

    private void connectionsCleaner(Long workingTime){
        this.connectionsList.entrySet().removeIf(next -> workingTime - next.getValue() > this.timeToBecomeUnavailable);
    }

    private boolean isUUID(String receivedStringUUID){
        return (receivedStringUUID != null);
    }

    private void printCopiesList(){
        System.out.println("Copies: ");
        int counter = 0;
        for (var entry : this.connectionsList.entrySet()) {
            if(!entry.getKey().equals(uuid.toString())){
                System.out.println(entry.getKey());
                counter++;
            }
        }
        System.out.println("All in all: " + counter + " alive copies");
    }



    private void codeSymbol(String symbol, byte[] code, byte code_idx){
        code[code_idx/2] <<= 4;
        byte num = Byte.parseByte(symbol,16);
        code[code_idx/2] = (byte) (code[code_idx/2] | num);
    }


    public boolean codeUUID(byte[] code){
        String str = uuid.toString();
        byte code_idx = 0;
        String[] sub_str = str.split("-");
        if(sub_str.length < 4){
            return false;
        }
        for(int i = 0; i < 5; i++) {
            String[] sub_str_lvl2 = sub_str[i].split("");
            int number;
            if(i == 0){
                number = 8;
            }else if (i == 4){
                number = 12;
            }else{
                number = 4;
            }
            for(int j = 0; j < number; j++){
                codeSymbol(sub_str_lvl2[j], code, code_idx);
                code_idx++;
            }
        }
        return true;
    }

    public String reform(byte number){
        if(number < 10){
            return Integer.toString(number);
        }else{
            switch (number){
                case 10: return "a";
                case 11: return "b";
                case 12: return "c";
                case 13: return "d";
                case 14: return "e";
                case 15: return "f";
            }
        }
        return null;
    }


    public String decodeUUID(byte[] new_code){
        byte[] code = new_code.clone();
        StringBuilder stringBuilder = new StringBuilder();
        int mask = 15;
        int code_idx = 0;
        if(new_code.length != 16){
            return null;
        }
        for(int i = 0; i < 32; i++){
            if(i == 8 || i == 12 || i == 16 || i == 20){
                stringBuilder.append("-");
            }
            byte part = (byte)((code[code_idx/2] >> 4) & mask);
            stringBuilder.append(reform(part));
            code[code_idx/2] <<= 4;
            code_idx++;
        }

        return stringBuilder.toString();
    }
}