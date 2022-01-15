package view;

import javax.swing.*;

public enum GameImage {
    EMPTY("photos\\empty.png"),
    APPLE("photos\\apple.png"),
    SNAKE_BODY_HORIZONTAL("photos\\snake_body_horizontal.png"),
    SNAKE_BODY_VERTICAL("photos\\snake_body_vertical.png"),
    SNAKE_HEAD_RIGHT("photos\\snake_head_right.png"),
    SNAKE_HEAD_DOWN("photos\\snake_head_down.png"),
    SNAKE_HEAD_LEFT("photos\\snake_head_left.png"),
    SNAKE_HEAD_UP("photos\\snake_head_up.png"),
    SNAKE_SLIDE_DOWN_RIGHT("photos\\snake_slide_down_right.png"),
    SNAKE_SLIDE_LEFT_DOWN("photos\\snake_slide_left_down.png"),
    SNAKE_SLIDE_LEFT_UP("photos\\snake_slide_left_up.png"),
    SNAKE_SLIDE_UP_RIGHT("photos\\snake_slide_up_right.png"),
    SNAKE_TAIL_DOWN("photos\\snake_tail_down.png"),
    SNAKE_TAIL_LEFT("photos\\snake_tail_left.png"),
    SNAKE_TAIL_RIGHT("photos\\snake_tail_right.png"),
    SNAKE_TAIL_UP("photos\\snake_tail_up.png");

    private final String fileName;
    private ImageIcon imageIcon;

    GameImage(String fileName) {
        this.fileName = fileName;
    }

    public synchronized ImageIcon getImageIcon() {
        if (imageIcon == null) {
            imageIcon = new ImageIcon(ClassLoader.getSystemResource(fileName));
        }

        return imageIcon;
    }
}