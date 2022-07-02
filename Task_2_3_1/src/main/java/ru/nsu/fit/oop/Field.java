/* (C) 2022 */
package ru.nsu.fit.oop;

import java.awt.*;
import java.util.List;

/** Class of field, should be created once per game, have two lists of tiles to get fast */
public class Field {
  /** property to have access for 0(1) to any tile of game */
  private static Tile[][] twoDimensionField;

  /** property to fast get random tile to generate food */
  private static Tile[] oneDimensionField;

  /** count of rows on field */
  private static int rows;

  /** count of columns of field */
  private static int columns;

  /**
   * @param fieldDescription string with 0 and x symbols, where x is wall and 0 is field tile
   */
  public Field(String fieldDescription) {
    int tilesCnt = 0;
    int curX = 0;
    int curY = 0;
    rows = 0;
    columns = 1;
    oneDimensionField = new Tile[fieldDescription.length()];
    for (char tileChar : fieldDescription.toCharArray()) {
      if (tileChar == 48) {
        oneDimensionField[tilesCnt] = new Tile(curX, curY, false);
      } else if (tileChar == 120) {
        oneDimensionField[tilesCnt] = new Tile(curX, curY, true);
      }
      if (tileChar == 10) {
        curY++;
        columns++;
        curX = 0;
      } else {
        tilesCnt++;
        curX++;
        if (curX > rows) {
          rows = curX;
        }
      }
    }
    twoDimensionField = new Tile[rows][columns];
    int counter = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        twoDimensionField[i][j] = oneDimensionField[counter];
        counter++;
      }
    }
  }

  /**
   * @return count of rows
   */
  public static int getRows() {
    return rows;
  }

  /**
   * @return count of columns
   */
  public static int getColumns() {
    return columns;
  }

  /**
   * @param xCoord x coord of tile
   * @param yCoord y coord of tile
   * @return Tile object
   */
  public static Tile getTile(int xCoord, int yCoord) {
    return twoDimensionField[xCoord][yCoord];
  }

  /**
   * change tiles under snake to taken (food can not spawn on it)
   *
   * @param body snake body
   */
  public static void changeTilesTaken(List<Point> body) {
    for (Point point : body) {
      if (point.getX() != -1) {
        getTile((int) point.getX(), (int) point.getY()).setTaken(true);
      }
    }
    if (body.get(body.size() - 1).getX() != -1) {
      getTile((int) body.get(body.size() - 1).getX(), (int) body.get(body.size() - 1).getY())
          .setTaken(false);
    }
  }

  /**
   * @return list of tiles
   */
  public static Tile[] getTilesList() {
    return oneDimensionField;
  }
}
