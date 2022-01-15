package common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProtocolMessage {
    NEW_PLAYER(0),
    NEW_MOVE(1),
    WIN(2),
    LOSE(3),
    DISCONNECTED(4),
    ANNOUNCEMENT(5);

    private final int code;

    public ProtocolMessage decode(int code) {
        switch (code) {
            case 0 -> {
                return NEW_PLAYER;
            }
            case 1 -> {
                return NEW_MOVE;
            }
            case 2 -> {
                return WIN;
            }
            case 3 -> {
                return LOSE;
            }
            case 4 -> {
                return DISCONNECTED;
            }
            case 5 -> {
                return ANNOUNCEMENT;
            }
        }
        return null;
    }
}
