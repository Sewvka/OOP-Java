/* (C) 2022 */
package ru.nsu.fit.oop;

import org.junit.jupiter.api.Test;

public class SnakeGameTest {
  @Test
  public void checkSnakeDeath() {
    int width = 800;
    int height = 800;
    int rows = 20;
    int square_size = width / rows;
    Snake snake = new Snake(rows);
    while (!snake.die(square_size, width, height)) {
      Snake.move();
    }
  }
}
