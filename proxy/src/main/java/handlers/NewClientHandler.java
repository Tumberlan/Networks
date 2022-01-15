package handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import socks.Socks;
import socks.SocksLength;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

@AllArgsConstructor
@Slf4j
public class NewClientHandler extends Handler {
    private final Selector selector;

    @Override
    public void readMode(SelectableChannel channel) {
        SocketChannel clientChannel = (SocketChannel) channel;
        try {
            ByteBuffer buf = ByteBuffer.allocate(SocksLength.NEW_CLIENT);
            int readBytes = clientChannel.read(buf);
            if (readBytes <= 0) {
                return;
            }

            buf.flip();
            byte protocolVersion = buf.get();
            if (!isExpectedByte("WRONG SOCKS VERSION", protocolVersion, Socks.VERSION)) {
                closeChannels(selector, clientChannel);
                return;
            }

            byte nAuth = buf.get();
            if (nAuth < Socks.MIN_AUTHENTIFICATION) {
                log.info("AUTHENTIFICATION IS NOT AVAILABLE");
                closeChannels(selector, clientChannel);
                return;
            }

            boolean expectedMethodSupported = false;
            for (int i = 0; i < Byte.toUnsignedInt(nAuth); i++) {
                if (isExpectedByte("UNAVAILABLE AUTHENTIFICATION METHOD", buf.get(),
                        Socks.NO_AUTHENTIFICATION)) {
                    expectedMethodSupported = true;
                    break;
                }
            }
            if (!expectedMethodSupported) {
                log.error("NO AUTHENTIFICATION IS NOT AVAILABLE");
                closeChannels(selector, clientChannel);
                return;
            }

            clientChannel.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
        } catch (IOException e) {
            closeChannels(selector, clientChannel);
            log.error("READ ERROR", e);
        }
    }

    @Override
    public void writeMode(SelectableChannel channel) {
        SocketChannel clientChannel = (SocketChannel) channel;
        try {
            ByteBuffer buf = ByteBuffer.allocate(SocksLength.NEW_CLIENT);
            buf.put(Socks.VERSION);
            buf.put(Socks.NO_AUTHENTIFICATION);
            buf.flip();
            clientChannel.write(buf);
            clientChannel.register(selector, SelectionKey.OP_READ, new ConnectionRequestHandler(selector));
        } catch (IOException e) {
            closeChannels(selector, clientChannel);
            log.error("WRITE ERROR", e);
        }
    }
}
