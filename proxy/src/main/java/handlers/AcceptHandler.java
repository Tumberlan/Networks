package handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.*;

@Slf4j
@AllArgsConstructor
public class AcceptHandler extends Handler {
    private final Selector selector;

    @Override
    public void acceptMode(SelectableChannel channel) {
        SocketChannel clientChannel;
        try {
            ServerSocketChannel serverChannel = (ServerSocketChannel) channel;
            clientChannel = serverChannel.accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ, new NewClientHandler(selector));

        } catch (IOException e) {
            log.error("ACCEPT ERROR");
        }
    }
}
