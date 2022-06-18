package ru.nsu.fit.oop;

import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Food {
    private static final String[] FOODS_IMAGE = new String[]{"/ru/nsu/fit/oop/images/ic_orange.png", "/ru/nsu/fit/oop/images/ic_apple.png", "/ru/nsu/fit/oop/images/ic_cherry.png",
            "/ru/nsu/fit/oop/images/ic_berry.png", "/ru/nsu/fit/oop/images/ic_coconut_.png", "/ru/nsu/fit/oop/images/ic_peach.png", "/ru/nsu/fit/oop/images/ic_watermelon.png", "/ru/nsu/fit/oop/images/ic_orange.png",
            "/ru/nsu/fit/oop/images/ic_pomegranate.png"};
    private Image img;
    private int x;
    private int y;

    public static Food generateFood(int rows, int columns, List<Point> snakeBody, List<Food> foodList) {
        Food food = new Food();
        start:
        while (true) {
            food.x = (int) (Math.random() * rows);
            food.y = (int) (Math.random() * columns);

            for (Point snake : snakeBody) {
                if (snake.getX() == food.x && snake.getY() == food.y) {
                    continue start;
                }
            }
            for (Food f : foodList) {
                if (food.x == f.getX() && food.y == f.getY()) {
                    continue start;
                }
            }
            food.img = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
            return food;
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Image getImg(){
        return img;
    }
}
