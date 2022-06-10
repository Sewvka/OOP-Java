package ru.nsu.fit.oop;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Snake {
    private final Color snakeColor = Color.GREENYELLOW;

    public Rectangle body = new Rectangle(12, 12, snakeColor); //SNAKE BODY

    private int xpos;
    private int ypos;
    private int oldXpos;
    private int oldYpos;

    Snake(int x, int y) {
        oldXpos = x;
        xpos = x;
        oldYpos = y;
        ypos = y;
    }

    public void setSnakeColor(Color snakeColor) {
        int size = 12;
        body = new Rectangle(size, size, snakeColor);
    }

    public void setPos(int x, int y) {
        oldXpos = xpos;
        oldYpos = ypos;

        xpos = x;
        ypos = y;
    }

    public int getOldXpos() {
        return oldXpos;
    }

    public int getOldYpos() {
        return oldYpos;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

}