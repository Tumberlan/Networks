package handlers;

import lombok.extern.slf4j.Slf4j;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Message;
import org.xbill.DNS.ResolverConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.List;

@Slf4j
public class DomainHandler extends Handler {
    private static final int BUFFER_SIZE = 4096;
    private static final int CLIENT_AFTER_HOST_PRIORITY_CODE = 0;

    private final Selector selector;

    private SocketChannel clientSocket;
    private SocketChannel hostSocket;
    private DatagramChannel dns;
    private InetSocketAddress hostAddress;
    private int port;

    public DomainHandler(Selector selector, SocketChannel clientSocket, int port) throws IOException {
        this.clientSocket = clientSocket;
        this.selector = selector;
        this.port = port;
        List<InetSocketAddress> dnsServers = ResolverConfig.getCurrentConfig().servers();
        dns = DatagramChannel.open();
        dns.configureBlocking(false);
        dns.connect(dnsServers.get(0));
        dns.register(selector, SelectionKey.OP_WRITE, this);
        clientSocket.register(selector, CLIENT_AFTER_HOST_PRIORITY_CODE, this);
    }

    @Override
    public void readMode(SelectableChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try {
            dns.read(buffer);
            Message msg = new Message(buffer.array());
            List<org.xbill.DNS.Record> recs = msg.getSection(1);
            for (org.xbill.DNS.Record rec : recs) {
                if (rec instanceof ARecord) {
                    ARecord arec = (ARecord) rec;
                    InetAddress adr = arec.getAddress();
                    hostAddress = new InetSocketAddress(adr, port);
                    hostSocket = SocketChannel.open();
                    hostSocket.configureBlocking(false);
                    hostSocket.connect(this.hostAddress);
                    hostSocket.register(selector, SelectionKey.OP_CONNECT, this);
                    closeChannels(selector, dns);
                    return;
                }
            }
        } catch (IOException e) {
            closeChannels(selector, dns, clientSocket, hostSocket);
            log.error("READ ERROR", e);
        }
    }
}
