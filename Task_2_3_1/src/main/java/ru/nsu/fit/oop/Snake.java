package ru.nsu.fit.oop;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static List<Point> snakeBody = new ArrayList();
    private static Point snakeHead;

    public Snake(int rows) {
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, rows / 2));
        }
        snakeHead = snakeBody.get(0);
    }

    public static List<Point> getSnakeBody() {
        return snakeBody;
    }

    public static Point getSnakeHead() {
        return snakeHead;
    }

    public static void move() {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
    }

    public void moveRight() {
        snakeHead.x++;
    }

    public void moveLeft() {
        snakeHead.x--;
    }

    public void moveUp() {
        snakeHead.y--;
    }

    public void moveDown() {
        snakeHead.y++;
    }

    public boolean die(int square_size, int width, int height) {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * square_size >= width || snakeHead.y * square_size >= height) {
            return true;
        }

        //destroy itself
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                return true;
            }
        }
        return false;
    }
}
