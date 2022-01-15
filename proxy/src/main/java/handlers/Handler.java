package handlers;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

@Slf4j
public class Handler {

    public void acceptMode(SelectableChannel channel) {

    }

    public void readMode(SelectableChannel channel) {

    }

    public void writeMode(SelectableChannel channel) {

    }

    public void connectMode(SelectableChannel channel) {
    }

    private void closeChannel(SelectableChannel channel, Selector selector) {
        if (channel == null || selector == null) {
            return;
        }
        try {
            SelectionKey key = channel.keyFor(selector);
            if (key == null) {
                return;
            }
            channel.keyFor(selector).cancel();
            channel.close();
        } catch (IOException e) {
            log.error("error in channel", e);
        }
    }

    protected void closeChannels(Selector selector, SelectableChannel... channels) {
        for (var channel : channels) {
            closeChannel(channel, selector);
        }
    }

    protected boolean isExpectedByte(String errorMessage, byte inputByte, byte... expectedBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(errorMessage).
                append(String.format("; got 0x%02X , expected: ", inputByte));
        for (byte expectedByte : expectedBytes) {
            if (inputByte == expectedByte) {
                return true;
            }
            stringBuilder.append(String.format(" 0x%02X ,", expectedByte));
        }
        log.error(stringBuilder.toString());
        return false;
    }
}
