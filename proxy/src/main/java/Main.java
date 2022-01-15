import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            log.error("Not enough arguments");
            return;
        }
        Proxy proxy = new Proxy(Integer.parseInt(args[0]));
        proxy.start();
    }
}
