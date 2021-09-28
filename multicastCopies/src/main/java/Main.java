import java.net.UnknownHostException;

public class Main {

    private static final int FEW_ARGUMENTS_EXCEPTION = 1;

    public static void main(String[] args) throws UnknownHostException {
        if (args.length != 1){
            System.out.println("Too few arguments");
            System.exit(FEW_ARGUMENTS_EXCEPTION);
        }
        App app = new App(args[0]);
        app.run();
    }
}
