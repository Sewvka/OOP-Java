/* (C) 2022 */
package ru.nsu.fit.oop;

/**
 * Food.java
 *
 * <p>Multiton class, which define food, which can be eaten by snake
 */
public class Food {
  /** x coordinate of food */
  private int x;

  /** y coordinate of food */
  private int y;

  /** array of food images which can be shown on the screen */

  /**
   * Generate new food and check if there is an overlay with other food or snake body
   *
   * @return Food - coords
   */
  public static Food generateFood() {
    Food food = new Food();
    Tile[] tilesList = Field.getTilesList();
    int cur;
    while (true) {
      cur = (int) Math.floor(Math.random() * (tilesList.length));
      if (!(tilesList[cur] == null)) {
        if (!(Field.getTile(tilesList[cur].getX(), tilesList[cur].getY()).isWall()
            || Field.getTile(tilesList[cur].getX(), tilesList[cur].getY()).isTaken())) {
          food.x = tilesList[cur].getX();
          food.y = tilesList[cur].getY();
          return food;
        }
      }
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
}
