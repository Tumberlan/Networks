import handlers.AcceptHandler;
import handlers.Handler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class Proxy {
    private final int port;

    public void start() {
        Selector selector;
        ServerSocketChannel serverSocket;
        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));
            serverSocket.register(selector, SelectionKey.OP_ACCEPT, new AcceptHandler(selector));
        } catch (IOException e) {
            log.error("CAN'T START PROXY", e);
            return;
        }

        while (true) {
            try {
                if (selector.select() <= 0) {
                    continue;
                }
            } catch (IOException e) {
                log.error("SELECTOR PROBLEMS", e);
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                var key = iterator.next();
                if (key.isValid() && key.isAcceptable()) {
                    if (key.attachment() == null) {
                        log.warn("KEY WITH NO ATTACHMENTS");
                        continue;
                    }
                    ((Handler) key.attachment()).acceptMode(key.channel());
                }
                if (key.isValid() && key.isReadable()) {
                    if (key.attachment() == null) {
                        log.warn("KEY WITH NO ATTACHMENTS");
                        continue;
                    }
                    ((Handler) key.attachment()).readMode(key.channel());
                }
                if (key.isValid() && key.isWritable()) {
                    if (key.attachment() == null) {
                        log.warn("KEY WITH NO ATTACHMENTS");
                        continue;
                    }
                    ((Handler) key.attachment()).writeMode(key.channel());
                }
                if (key.isValid() && key.isConnectable()) {
                    if (key.attachment() == null) {
                        log.warn("KEY WITH NO ATTACHMENTS");
                        continue;
                    }
                    ((Handler) key.attachment()).connectMode(key.channel());
                }
                iterator.remove();
            }
        }
    }
}
