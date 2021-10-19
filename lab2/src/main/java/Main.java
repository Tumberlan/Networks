import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class Main {

    private static final String TAKING_FILE_PATH = "receivedFiles";
    private static final String CLEANER_COMMAND = "clean";
    private static final int TYPE_OF_PROGRAM_NUMBER_OF_ARGS = 1;
    private static final int SERVER_START_NUMBER_OF_ARGS = 2;
    private static final int SERVER_START_AND_CLEAN_NUMBER_OF_ARGS = 3;
    private static final int SERVER_CLEAN_ARGS_POSITION = 2;
    private static final int CLIENT_START_NUMBER_OF_ARGS = 4;
    private static final int SWITCHER_ARGS_POSITION = 0;
    private static final int CLIENT_IP_ARGS_POSITION = 1;
    private static final int CLIENT_PORT_ARGS_POSITION = 2;
    private static final int CLIENT_PATH_ARGS_POSITION = 3;
    private static final int SERVER_PORT_ARGS_POSITION = 1;


    public static void main(String[] args) throws IOException {
        if (TYPE_OF_PROGRAM_NUMBER_OF_ARGS > args.length) {
            log.error("LESS ARGUMENTS");
            return;
        }
        PreStartChecker preStartChecker = new PreStartChecker(args);
        if (args[SWITCHER_ARGS_POSITION].equals("c")) {
            startClient(args, preStartChecker);
        }
        if (args[SWITCHER_ARGS_POSITION].equals("s")) {
            startServer(args, preStartChecker);
        }
    }

    private static void startClient(String[] args, PreStartChecker preStartChecker) throws UnknownHostException {
        if (CLIENT_START_NUMBER_OF_ARGS != args.length) {
            log.error("LESS ARGUMENTS TO START CLIENT");
            return;
        }
        if (!preStartChecker.canClientStart()) {
            return;
        }
        InetAddress address = InetAddress.getByName(args[CLIENT_IP_ARGS_POSITION]);
        Client client = new Client(address, Integer.parseInt(args[CLIENT_PORT_ARGS_POSITION]),
                args[CLIENT_PATH_ARGS_POSITION]);
        client.run();
    }

    private static void startServer(String[] args, PreStartChecker preStartChecker) throws IOException {
        boolean withClean = false;
        if (SERVER_START_AND_CLEAN_NUMBER_OF_ARGS == args.length) {
            if (CLEANER_COMMAND.equals(args[SERVER_CLEAN_ARGS_POSITION])) {
                Cleaner cleaner = new Cleaner(TAKING_FILE_PATH);
                cleaner.clean();
                withClean = true;
            }

        }

        if (SERVER_START_NUMBER_OF_ARGS != args.length && !withClean) {
            log.error("LESS ARGUMENTS TO START SERVER");
            return;
        }

        if (!preStartChecker.canServerStart()) {
            return;
        }
        Server server = new Server(Integer.parseInt(args[SERVER_PORT_ARGS_POSITION]));
        server.start();
    }
}
