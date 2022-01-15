package handlers;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

@Getter
public class ChannelWrapper {
    private static final int BUF_SIZE = 4096;
    private final ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
    @Setter
    private boolean outputShutdown = false;
    @Setter
    private boolean finishRead = false;
    public final SocketChannel channel;

    public ChannelWrapper(SocketChannel channel) {
        this.channel = channel;
    }
}
