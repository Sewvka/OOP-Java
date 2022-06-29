/* (C) 2022 */
package ru.nsu.fit.oop;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Snake.java
 *
 * <p>Singleton class of snake, which define coords of snake body and its head, can check if snake
 * eat itself or food Class of snake with props <b>snakeBody</b>, <b>snakeHead</b>
 */
public class Snake {
  /** List of Points [(x,y), ...] coords of snake body */
  private static List<Point> snakeBody;

  /** Point (x, y) coordinates of snake head */
  private static Point snakeHead;

  /**
   * Class constructor
   *
   * @param rows count of rows in game screen
   */
  public Snake(int rows) {
    snakeBody = new ArrayList();
    for (int i = 0; i < 3; i++) {
      snakeBody.add(new Point(5, rows / 2));
    }
    snakeHead = snakeBody.get(0);
  }

  /**
   * @return List of Points [(x,y), ...] coords of snake body
   */
  public static List<Point> getSnakeBody() {
    return snakeBody;
  }

  /**
   * @return Point (x, y) coordinates of snake head
   */
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

    // destroy itself
    for (int i = 1; i < snakeBody.size(); i++) {
      if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
        return true;
      }
    }
    return false;
  }
  /**
   * Function checks if snake hit the food
   *
   * @param foodList list of food
   * @return index of food in list if snake hit it else -1
   */
  public int eatFood(List<Food> foodList) {
    for (int food = 0; food < foodList.size(); food++) {
      if (snakeHead.getX() == foodList.get(food).getX()
          && snakeHead.getY() == foodList.get(food).getY()) {
        snakeBody.add(new Point(-1, -1));
        return food;
      }
    }
    return -1;
  }
}
