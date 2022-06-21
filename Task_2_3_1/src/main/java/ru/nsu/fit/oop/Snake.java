/* (C) 2022 */
package ru.nsu.fit.oop;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
  private static List<Point> snakeBody;
  private static Point snakeHead;

  public Snake(int rows) {
    snakeBody = new ArrayList();
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

  /** Change snake body coords of each tile */
  public static void move() {
    for (int i = snakeBody.size() - 1; i >= 1; i--) {
      snakeBody.get(i).x = snakeBody.get(i - 1).x;
      snakeBody.get(i).y = snakeBody.get(i - 1).y;
    }
  }

  /** move snake to the right */
  public void moveRight() {
    snakeHead.x++;
  }

  /** move snake to the left */
  public void moveLeft() {
    snakeHead.x--;
  }

  /** move snake to top */
  public void moveUp() {
    snakeHead.y--;
  }

  /** move snake down */
  public void moveDown() {
    snakeHead.y++;
  }

  /**
   * @param square_size - size of one tile
   * @param width - game screen width
   * @param height - game screen height
   * @return - Boolean that means snake death
   */
  public boolean die(int square_size, int width, int height) {
    if (snakeHead.x < 0
        || snakeHead.y < 0
        || snakeHead.x * square_size >= width
        || snakeHead.y * square_size >= height) {
      return true;
    }

    // destroy itself
    for (int i = 1; i < snakeBody.size(); i++) {
      if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
        return true;
      }
    }
    return false;
  }

  /** Check if snake cross food, then increase snake size, remove this food and create new one */
  public boolean eatFood(List<Food> foodList, int rows, int columns) {
    for (int food = 0; food < foodList.size(); food++) {
      if (snakeHead.getX() == foodList.get(food).getX()
          && snakeHead.getY() == foodList.get(food).getY()) {
        snakeBody.add(new Point(-1, -1));
        foodList.remove(food);
        foodList.add(Food.generateFood(rows, columns, snakeBody, foodList));
        return true;
      }
    }
    return false;
  }
}
