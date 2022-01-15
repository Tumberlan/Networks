package server.gamelogic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Player {
    private final int id;
    @Setter
    private int snakeLength;
}
