/* (C) 2022 */
package ru.nsu.fit.oop;

/** Class defines each tile on screen, which can be taken by food or snake or it can be wall */
public class Tile {
  private boolean isWall;
  private int x;
  private int y;
  private boolean isTaken;

  /**
   * @param xCoord x coord if tile
   * @param yCoord y coord of tile
   * @param wall is it wall or not
   */
  public Tile(int xCoord, int yCoord, boolean wall) {
    isTaken = false;
    x = xCoord;
    y = yCoord;
    isWall = wall;
  }

  /**
   * @return is it wall
   */
  public boolean isWall() {
    return isWall;
  }

  /**
   * @return x coord of tile
   */
  public int getX() {
    return x;
  }

  /**
   * @return y coord of tile
   */
  public int getY() {
    return y;
  }

  /**
   * @return is it taken by food or snake
   */
  public boolean isTaken() {
    return isTaken;
  }

  /**
   * @param status change status of tile to given
   */
  public void setTaken(boolean status) {
    isTaken = status;
  }
}
