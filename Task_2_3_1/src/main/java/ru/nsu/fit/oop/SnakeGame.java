package ru.nsu.fit.oop;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class SnakeGame extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;


    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private GraphicsContext gc;
    private List<Food> foodList;

    private boolean gameOver;
    private int currentDirection;
    private int score = 0;
    private static Snake snake;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
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
            gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
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
        eatFood();
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

    private void generateAllFood(int count) {
        foodList = new ArrayList<Food>();
        for (int i = 0; i < count; i++){
            Food food = Food.generateFood(ROWS, COLUMNS, snake.getSnakeBody(), foodList);
            foodList.add(food);
        }
    }

    private void drawFood(GraphicsContext gc) {
        for (int food = 0; food < foodList.size(); food++){
            gc.drawImage(foodList.get(food).getImg(), foodList.get(food).getX() * SQUARE_SIZE, foodList.get(food).getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }

    }

    private void drawSnake(GraphicsContext gc) {
        gc.setFill(Color.web("4674E9"));
        gc.fillRoundRect(Snake.getSnakeHead().getX() * SQUARE_SIZE, snake.getSnakeHead().getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

        for (int i = 1; i < Snake.getSnakeBody().size(); i++) {
            gc.fillRoundRect(Snake.getSnakeBody().get(i).getX() * SQUARE_SIZE, Snake.getSnakeBody().get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                    SQUARE_SIZE - 1, 20, 20);
        }
    }


    public void gameOver() {
        if (snake.die(SQUARE_SIZE, WIDTH, HEIGHT)) gameOver = true;
    }

    private void eatFood() {
        for (int food = 0; food < foodList.size(); food++){
            if (snake.getSnakeHead().getX() == foodList.get(food).getX() && snake.getSnakeHead().getY() == foodList.get(food).getY()) {
                snake.getSnakeBody().add(new Point(-1, -1));
                foodList.remove(food);
                foodList.add(Food.generateFood(ROWS, COLUMNS, Snake.getSnakeBody(), foodList));
                score += 5;
            }
        }
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    public static void main(String[] args) {
        launch(args);
    }
}