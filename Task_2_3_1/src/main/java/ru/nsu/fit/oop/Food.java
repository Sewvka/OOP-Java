/* (C) 2022 */
package ru.nsu.fit.oop;

import java.awt.*;
import java.util.List;
import javafx.scene.image.Image;

/**
 * Food.java
 *
 * <p>Multiton class, which define food, which can be eaten by snake
 */
public class Food {
  /** img of food */
  private Image img;

  /** x coordinate of food */
  private int x;

  /** y coordinate of food */
  private int y;

  /** array of food images which can be shown on the screen */
  private static final String[] FOODS_IMAGE =
      new String[] {
        "/ru/nsu/fit/oop/images/ic_orange.png",
        "/ru/nsu/fit/oop/images/ic_cherry.png",
        "/ru/nsu/fit/oop/images/ic_coconut_.png",
        "/ru/nsu/fit/oop/images/ic_peach.png",
        "/ru/nsu/fit/oop/images/ic_watermelon.png",
        "/ru/nsu/fit/oop/images/ic_orange.png",
        "/ru/nsu/fit/oop/images/ic_pomegranate.png"
      };

  /**
   * Generate new food and check if there is an overlay with other food or snake body
   *
   * @param rows - Count of screen rows
   * @param columns - Count of screen columns
   * @param snakeBody - List of Points - snake body
   * @param foodList - List of food
   * @return Food - coords with texture
   */
  public static Food generateFood(
      int rows, int columns, List<Point> snakeBody, List<Food> foodList) {
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

  /**
   * @return - x cord of food
   */
  public int getX() {
    return x;
  }

  /**
   * @return - y cord of food
   */
  public int getY() {
    return y;
  }

  /**
   * @return - texture of food
   */
  public Image getImg() {
    return img;
  }
}
