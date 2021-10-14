import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@AllArgsConstructor
@Log4j
public class PreStartChecker {

    private String[] args;
    private static final int CLIENT_SERVER_ADDRESS_ARGS_POSITION = 1;
    private static final int CLIENT_PORT_ARGS_POSITION = 2;
    private static final int SERVER_PORT_ARGS_POSITION = 1;
    private static final int LOWEST_PORT_NUMBER = 1024;
    private static final int HIGHEST_PORT_NUMBER = 65535;


    public boolean canServerStart(){
        int port = Integer.parseInt(args[SERVER_PORT_ARGS_POSITION]);
        if(LOWEST_PORT_NUMBER > port || HIGHEST_PORT_NUMBER < port){
            log.error("YOUR PORT NUMBER IS WRONG");
            return false;
        }
        return true;
    }

    public boolean canClientStart(){
        try {
            InetAddress address = InetAddress.getByName(args[CLIENT_SERVER_ADDRESS_ARGS_POSITION]);
        } catch (UnknownHostException e) {
            log.error("YOUR ADDRESS GOT WRONG TYPE");
            return false;
        }
        int port = Integer.parseInt(args[CLIENT_PORT_ARGS_POSITION]);
        if(LOWEST_PORT_NUMBER > port || HIGHEST_PORT_NUMBER < port){
            log.error("YOUR PORT NUMBER IS WRONG");
            return false;
        }
        return true;
    }
}
