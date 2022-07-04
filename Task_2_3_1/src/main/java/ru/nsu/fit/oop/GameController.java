/* (C) 2022 */
package ru.nsu.fit.oop;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Pair;

/** Class, which responsible for the entire logic of the game*/
public class GameController implements Initializable {
  /** List of food */
  private static List<Pair<Food, File>> foodList;
  /** defines current score */
  private static int score = 0;
  /** stage of the game if game is ended */
  private static boolean gameOver;

  @FXML public Label scoreLabel;

  /**
   * This method should be used on game start to create game field and generate food
   *
   * @param foodCount count of food to generate
   * @throws IOException if you don't have folder with pictures of food
   */
  public static void onGameStart(int foodCount) throws IOException {
    createGameField();
    GameController.generateAllFood(foodCount);
  }

  /**
   * This method should be used while game run to check gameOver and which food was eaten by snake
   */
  public void onGameRun() {
    gameOver();
    eatFood();
  }
  /**
   * Create new list of food and fill it if n-th food
   *
   * @param count - count of food
   */
  public static void generateAllFood(int count) {
    foodList = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      Food food = Food.generateFood();
      Field.getTile(food.getX(), food.getY()).setTaken(true);
      foodList.add(new Pair<>(food, getRandomFoodImg()));
    }
  }

  /** restart game if it's ended */
  public static void restart() {
    SnakeGame.snake = new Snake(Field.getRows());
    GameController.generateAllFood(5);
    score = 0;
    gameOver = false;
  }

  /** Check if snake eat food then remove food from food list and add new one, increase score */
  private static void eatFood() {
    int food = Snake.eatFood(foodList);
    if (food > -1) {
      Food foodObj = foodList.get(food).getKey();
      Field.getTile(foodObj.getX(), foodObj.getY()).setTaken(true);
      foodList.remove(food);
      foodList.add(new Pair<>(Food.generateFood(), getRandomFoodImg()));
      score += 5;
    }
  }

  /** Check if snake die */
  private static void gameOver() {
    if (Snake.getSnakeHead().x < 0
        || Snake.getSnakeHead().y < 0
        || Snake.getSnakeHead().x * SnakeGame.getSquareSize() >= SnakeGame.getWidth()
        || Snake.getSnakeHead().y * SnakeGame.getSquareSize() >= SnakeGame.getHeight()) {
      gameOver = true;
    } else if (Snake.die()) {
      gameOver = true;
    } else if (Field.getTile((int) Snake.getSnakeHead().getX(), (int) Snake.getSnakeHead().getY())
        .isWall()) {
      gameOver = true;
    }
  }

  /**
   * create game field by sample from file
   *
   * @throws IOException if not file map.txt in folder gameMap
   */
  private static void createGameField() throws IOException {
    String file = new String("src/main/resources/ru/nsu/fit/oop/gameMap/map.txt");
    String map = new String(Files.readAllBytes(Paths.get(file)));
    new Field(map);
  }

  /**
   * @return food list
   */
  public static List<Pair<Food, File>> getFoodList() {
    return foodList;
  }

  /**
   * @return score of game
   */
  private static int getScore() {
    return score;
  }

  /**
   * @return random food image from file
   */
  private static File getRandomFoodImg() {
    File folder = new File("src/main/resources/ru/nsu/fit/oop/images");
    return folder.listFiles()[(int) (Math.random() * folder.listFiles().length)];
  }

  /**
   * @return game over or not
   */
  public static boolean getGameOver() {
    return gameOver;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    scoreLabel.textProperty().bind(Bindings.format("Score: %d", getScore()));
  }
}
