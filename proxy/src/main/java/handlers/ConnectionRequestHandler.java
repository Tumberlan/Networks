package handlers;

import lombok.extern.slf4j.Slf4j;
import socks.Socks;
import socks.SocksAvailableProtocols;
import socks.SocksLength;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

@Slf4j
public class ConnectionRequestHandler extends Handler {
    private static final int ADDRESS_LENGTH = 4;
    private static final int CLIENT_AFTER_HOST_PRIORITY_CODE = 0;

    private final Selector selector;
    private SocketChannel clientSocket;
    private SocketChannel hostSocket;
    private ByteBuffer buffer;

    public ConnectionRequestHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void readMode(SelectableChannel channel) {
        clientSocket = (SocketChannel) channel;
        try {
            buffer = ByteBuffer.allocate(SocksLength.CONNECTION_REQUEST);
            int inputBytes = clientSocket.read(buffer);
            if (inputBytes < 0) {
                return;
            }

            buffer.flip();
            byte protocolInfo = buffer.get();
            if (!isExpectedByte("UNAVAILABLE SOCKS", protocolInfo, Socks.VERSION)) {
                closeChannels(selector, clientSocket);
                return;
            }

            byte connectionCode = buffer.get();
            if (!isExpectedByte("UNAVAILABLE CONNECTION TYPE", connectionCode,
                    SocksAvailableProtocols.TCP)) {
                closeChannels(selector, clientSocket);
                return;
            }

            byte socksType = buffer.get();
            if (!isExpectedByte("UNAVAILABLE ADDRESS TYPE", socksType,
                    Socks.IPV4, Socks.DOMAIN_NAME)) {
                closeChannels(selector, clientSocket);
                return;
            }

            switch (socksType) {
                case Socks.IPV4 -> {
                    byte[] address = new byte[ADDRESS_LENGTH];
                    buffer.get(address);
                    short port = buffer.getShort();
                    Inet4Address inet4Address = (Inet4Address) Inet4Address.getByAddress(address);

                    hostSocket = SocketChannel.open();
                    hostSocket.configureBlocking(false);
                    hostSocket.connect(new InetSocketAddress(inet4Address, port));

                    clientSocket.keyFor(selector).interestOps(CLIENT_AFTER_HOST_PRIORITY_CODE);
                    hostSocket.register(selector, SelectionKey.OP_CONNECT, this);
                }

                case Socks.DOMAIN_NAME -> {
                    byte domainNameLength = buffer.get();
                    byte[] address = new byte[Byte.toUnsignedInt(domainNameLength)];
                    buffer.get(address);
                    short port = buffer.getShort();
                    clientSocket.register(selector, CLIENT_AFTER_HOST_PRIORITY_CODE,
                            new DomainHandler(selector, clientSocket, port));
                }
            }
        } catch (IOException e) {
            closeChannels(selector, clientSocket, hostSocket);
            log.error("READ ERROR", e);
        }
    }

    @Override
    public void writeMode(SelectableChannel channel) {
        try {
            buffer.flip();
            buffer.put(1, Socks.REQUEST);
            clientSocket.write(buffer);
            ResendHandler handler = new ResendHandler(selector, clientSocket, hostSocket);
            clientSocket.register(selector, SelectionKey.OP_READ, handler);
            hostSocket.register(selector, SelectionKey.OP_READ, handler);
        } catch (IOException e) {
            closeChannels(selector, clientSocket, hostSocket);
            log.error("WRITE ERROR", e);
        }
    }

    public void connectMode(SelectableChannel channel) {
        try {
            hostSocket.finishConnect();
            clientSocket.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
            hostSocket.keyFor(selector).interestOps(CLIENT_AFTER_HOST_PRIORITY_CODE);
        } catch (IOException e) {
            closeChannels(selector, clientSocket, hostSocket);
            log.error("CONNECTION ERROR", e);
        }
    }

}
