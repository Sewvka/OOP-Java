/* (C) 2022 */
package ru.nsu.fit.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SnakeGameTest {
  @Test
  public void createFieldTest() {
    Field field = new Field("00000\n00000\n00000\n00000\n00000");
    assertEquals(5 + 5, field.getColumns() + field.getRows());
  }

  @Test
  public void wallFieldTest() {
    Field field = new Field("xx000\n00000\n000x0\n00000\n0x000");
    int walls = 0;
    for (int i = 0; i < field.getRows(); i++) {
      for (int j = 0; j < field.getColumns(); j++) {
        if (field.getTile(i, j).isWall()) {
          walls++;
        }
      }
    }
    assertEquals(4, walls);
  }
}
