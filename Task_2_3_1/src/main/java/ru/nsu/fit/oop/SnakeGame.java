/* (C) 2022 */
package ru.nsu.fit.oop;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SnakeGame extends Application {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 800;
  private static final int ROWS = 20;
  private static final int COLUMNS = ROWS;
  private static final int SQUARE_SIZE = WIDTH / ROWS;
  private static final int RIGHT = 0;
  private static final int LEFT = 1;
  private static final int UP = 2;
  private static final int DOWN = 3;
  public Text score_cnt;
  private int SHAPE;
  private static Snake snake;
  private GraphicsContext gc;
  private List<Food> foodList;
  private boolean gameOver;
  private int currentDirection;
  private int score = 0;

  public static void main(String[] args) {
    launch(args);
  }
  // TODO move to fxml создание объектов объяснить зачем так лучше
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Snake");
    Group root = new Group();
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    gc = canvas.getGraphicsContext2D();
    scene.setOnKeyPressed(
        event -> {
          KeyCode code = event.getCode();
          if (code == KeyCode.RIGHT || code == KeyCode.D) {
            if (currentDirection != LEFT) {
              currentDirection = RIGHT;
            }
          } else if (code == KeyCode.LEFT || code == KeyCode.A) {
            if (currentDirection != RIGHT) {
              currentDirection = LEFT;
            }
          } else if (code == KeyCode.UP || code == KeyCode.W) {
            if (currentDirection != DOWN) {
              currentDirection = UP;
            }
          } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            if (currentDirection != UP) {
              currentDirection = DOWN;
            }
          } else if (code == KeyCode.SPACE) {
            if (gameOver) {
              restart();
            }
          }
        });
    snake = new Snake(ROWS);
    generateAllFood(5);
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  private void run(GraphicsContext gc) {
    if (gameOver) {
      gc.setFill(Color.RED);
      gc.setFont(new Font("Digital-7", 70));
      gc.fillText("Game Over", (float) WIDTH / 3.5, (float) HEIGHT / 2);
      return;
    }
    drawBackground(gc);
    drawFood(gc);
    drawSnake(gc);
    drawScore();
    Snake.move();
    switch (currentDirection) {
      case RIGHT:
        snake.moveRight();
        break;
      case LEFT:
        snake.moveLeft();
        break;
      case UP:
        snake.moveUp();
        break;
      case DOWN:
        snake.moveDown();
        break;
    }
    gameOver();
    if (snake.eatFood(foodList, ROWS, COLUMNS)) score += 5;
  }

  /** restart game if it's ended */
  private void restart() {
    snake = new Snake(ROWS);
    generateAllFood(5);
    score = 0;
    gameOver = false;
  }

  private void drawBackground(GraphicsContext gc) {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        if ((i + j) % 2 == 0) {
          gc.setFill(Color.web("AAD751"));
        } else {
          gc.setFill(Color.web("A2D149"));
        }
        gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
      }
    }
  }

  /**
   * @param count - count of food Create new list of food and fill it if n-th food
   */
  public void generateAllFood(int count) {
    foodList = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      Food food = Food.generateFood(ROWS, COLUMNS, Snake.getSnakeBody(), foodList);
      foodList.add(food);
    }
  }

  private void drawFood(GraphicsContext gc) {
    for (Food value : foodList) {
      gc.drawImage(
          value.getImg(),
          value.getX() * SQUARE_SIZE,
          value.getY() * SQUARE_SIZE,
          SQUARE_SIZE,
          SQUARE_SIZE);
    }
  }
  // TODO изменить вид змейки в зависимости от параметра
  private void drawSnake(GraphicsContext gc) {
    SHAPE = 1;
    int arc;
    switch (SHAPE) {
      case 1:
        arc = 20;
        break;
      case 2:
        arc = 35;
        break;
      default:
        arc = 35;
    }
    gc.setFill(Color.web("4674E9"));
    gc.fillRoundRect(
        Snake.getSnakeHead().getX() * SQUARE_SIZE,
        Snake.getSnakeHead().getY() * SQUARE_SIZE,
        SQUARE_SIZE - 1,
        SQUARE_SIZE - 1,
        arc,
        arc);
    for (int i = 1; i < Snake.getSnakeBody().size(); i++) {
      gc.fillRoundRect(
          Snake.getSnakeBody().get(i).getX() * SQUARE_SIZE,
          Snake.getSnakeBody().get(i).getY() * SQUARE_SIZE,
          SQUARE_SIZE - 1,
          SQUARE_SIZE - 1,
          arc - 15,
          arc - 15);
    }
  }

  /** Check if snake die then change game state */
  public void gameOver() {
    if (snake.die(SQUARE_SIZE, WIDTH, HEIGHT)) gameOver = true;
  }

  private void drawScore() {
    gc.setFill(Color.WHITE);
    gc.setFont(new Font("Digital-7", 35));
    gc.fillText("Score: " + score, 10, 35);
  }
}
