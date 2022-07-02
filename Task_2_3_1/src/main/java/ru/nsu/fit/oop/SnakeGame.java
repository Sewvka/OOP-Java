/* (C) 2022 */
package ru.nsu.fit.oop;

import java.io.File;
import java.io.IOException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

/** Class of whole game, which start it and defines all game stages */
public class SnakeGame extends Application {
  /** width of game screen */
  private static final int WIDTH = 800;

  /** height of game screen */
  private static final int HEIGHT = 800;

  /** square size on screen */
  private static int SQUARE_SIZE;

  /** direction of snake if it moves to right */
  private static final int RIGHT = 0;

  /** direction of snake if it moves to left */
  private static final int LEFT = 1;

  /** direction of snake if it moves up */
  private static final int UP = 2;

  /** direction of snake if it moves down */
  private static final int DOWN = 3;

  public Text score_cnt;
  /** defines shape of the snake */
  private int SHAPE;

  /** snake in current game */
  public static Snake snake;

  /** Graphics Context */
  private GraphicsContext gc;

  /** define current direction of snake */
  private int currentDirection;

  /**
   * @param args args from command line on start app
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Function that start game, create scene, generate food list and create snake
   *
   * @param primaryStage current stage of game
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    //    fxml вызыв, что мы можем опис что нет
    SHAPE = 1;
    GameController.onGameStart(5);
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
            if (GameController.getGameOver()) {
              GameController.restart();
            } else {
              SHAPE = SHAPE == 1 ? 2 : 1;
            }
          }
        });
    SQUARE_SIZE = HEIGHT / Field.getRows();
    snake = new Snake(Field.getRows());
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  /**
   * Draw all textures on screen and redraw it every 130 millisecond, also check snake current
   * direction and move it, check game stage
   *
   * @param gc Graphics context used to issue draw calls to a Canvas using a buffer
   */
  private void run(GraphicsContext gc) {
    if (GameController.getGameOver()) {
      gc.setFill(Color.RED);
      gc.setFont(new Font("Digital-7", 70));
      gc.fillText("Game Over", (float) WIDTH / 3.5, (float) HEIGHT / 2);
      return;
    }
    drawBackground(gc);
    drawFood(gc);
    drawSnake(gc);
    drawScore();
    Field.changeTilesTaken(Snake.getSnakeBody());
    Snake.move();
    switch (currentDirection) {
      case RIGHT:
        Snake.moveRight();
        break;
      case LEFT:
        Snake.moveLeft();
        break;
      case UP:
        Snake.moveUp();
        break;
      case DOWN:
        Snake.moveDown();
        break;
    }
    GameController.onGameRun();
  }

  /**
   * Draw background with tartan texture or walls
   *
   * @param gc Graphics context
   */
  private void drawBackground(GraphicsContext gc) {
    for (int i = 0; i < Field.getColumns(); i++) {
      for (int j = 0; j < Field.getRows(); j++) {
        if (!Field.getTile(i, j).isWall()) {
          if ((i + j) % 2 == 0) {
            gc.setFill(Color.web("AAD751"));
          } else {
            gc.setFill(Color.web("A2D149"));
          }
        } else {
          gc.setFill(Color.web("A9A9A9"));
        }
        gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
      }
    }
  }

  /**
   * Draw food from food list on game screen
   *
   * @param gc Graphics context
   */
  private void drawFood(GraphicsContext gc) {
    for (Pair<Food, File> value : GameController.getFoodList()) {
      gc.drawImage(
          new Image("ru/nsu/fit/oop/images/" + value.getValue().getName()),
          value.getKey().getX() * SQUARE_SIZE,
          value.getKey().getY() * SQUARE_SIZE,
          SQUARE_SIZE,
          SQUARE_SIZE);
    }
  }

  /**
   * Draws a snake depending on shape
   *
   * @param gc Graphics context
   */
  private void drawSnake(GraphicsContext gc) {
    int arc = 35;
    Color color = Color.web("4674E9");
    switch (SHAPE) {
      case 1:
        arc = 20;
        color = Color.web("CC00CC");
        break;
      case 2:
        color = Color.web("4674E9");
        arc = 35;
        break;
    }
    gc.setFill(color);
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

  /** draw score on game screen */
  private void drawScore() {
    gc.setFill(Color.WHITE);
    gc.setFont(new Font("Digital-7", 35));
    gc.fillText("Score: " + GameController.getScore(), 10, 35);
  }

  /**
   * @return width of screen
   */
  public static int getWidth() {
    return WIDTH;
  }
  /**
   * @return height of screen
   */
  public static int getHeight() {
    return HEIGHT;
  }
  /**
   * @return square size
   */
  public static int getSquareSize() {
    return SQUARE_SIZE;
  }
}
