package ru.nsu.fit.oop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {

    GridPane gameGrid = new GridPane();
    GridPane textGrid = new GridPane();
    //GAME GRID - 40x40
    int gridSizeSquared = 40;
    Label gameOver = new Label("");
    Label pause = new Label("Нажми любую кнопку, чтобы начать.");
    Label controlU = new Label("[W/Up]");
    Label controlD = new Label("[S/Down]");
    Label controlL = new Label("[A/Left] ");
    Label controlR = new Label(" [D/Right]");
    Label close = new Label("Нажми [Escape], чтобы закрыть");
    //ArrayList for the Snake and its body
    //Index 0 of ArrayList is treated as the Head
    ArrayList<Snake> snakeP = new ArrayList<>(0);
    ArrayList<Snake> snakePC = new ArrayList<>(0);
    //This will be used to keep the game running
    Timeline loop;
    //LOOP SPEED
    //1/10 seconds == 10FPS
    double loopSpeed = 1 / 10.0;
    //movementX and movementY, these indicate the direction of the snake head
    //Initial movement is 0
    int deltaX = 0;
    int deltaY = 0;
    //Snakes Head's Initial Position
    int posX = new Random().nextInt(gridSizeSquared);
    int posY = new Random().nextInt(gridSizeSquared);
    int pcPosX = new Random().nextInt(gridSizeSquared);
    int pcPosY = new Random().nextInt(gridSizeSquared);
    //Snake Food, same size as snake (Snake.java)
    Rectangle food = new Rectangle(12, 12, Color.YELLOW);
    int foodPosX = new Random().nextInt(gridSizeSquared);
    int foodPosY = new Random().nextInt(gridSizeSquared);
    //True = Game is Running
    //False = Game is Paused
    boolean start = false;
    boolean dead = false;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        //Fills Grid with Gray Squares
        fillGrid();

        //Constructing Snake's Heads
        snakeP.add(new Snake(posX, posY));
        snakePC.add(new Snake(pcPosX, pcPosY));

        textGrid.setVgap(1.5);
        textGrid.setHgap(2);

        //Setting Grid Positions
        gameGrid.setAlignment(Pos.CENTER);
        textGrid.setAlignment(Pos.CENTER);

        //Initial positions
        gameGrid.add(food, foodPosX, foodPosY);
        gameGrid.add(snakeP.get(0).body, posX, posY);
        gameGrid.add(snakePC.get(0).body, pcPosX, pcPosY);

        //Adding Text to Grid
        textGrid.add(gameOver, 1, 1, 3, 2);
        textGrid.add(pause, 1, 3, 3, 1);
        textGrid.add(controlU, 2, 4, 1, 1);
        textGrid.add(controlL, 1, 5, 1, 1);
        textGrid.add(controlD, 2, 5, 1, 1);
        textGrid.add(controlR, 3, 5, 1, 1);

        textGrid.add(close, 1, 6, 3, 1);

        //Allows to use both grids in the same screen
        FlowPane screen = new FlowPane(Orientation.VERTICAL, gameGrid, textGrid);

        //Creating Game Scene with a black background
        Scene game = new Scene(screen);

        //Detects a Key Being Pressed
        game.setOnKeyPressed(this::keyPressedProcess);

        //Generates Window
        primaryStage.setTitle("Snake");
        primaryStage.setScene(game);
        primaryStage.show();

        //Initializing Loop as timeline.
        loop = new Timeline(new KeyFrame(Duration.seconds(loopSpeed),
                event -> {

                    //Moves Snake
                    moveUserSnake();
                    movePCSnake();
                }));
        // Loop will run endlessly
        loop.setCycleCount(Timeline.INDEFINITE);
    }

    public void moveUserSnake() {
        //WALL CRASH
        if (deltaX == -1 && (posX == 0)) {
            die();
            deltaX = 0;
        } else if (deltaY == -1 && (posY == 0)) {
            die();
            deltaY = 0;
        } else if (deltaX == 1 && (posX == gridSizeSquared - 1)) {
            die();
            deltaX = 0;
        } else if (deltaY == 1 && (posY == gridSizeSquared - 1)) {
            die();
            deltaY = 0;
        } else {

            //Updates head position
            gameGrid.getChildren().remove(snakeP.get(0).body);

            posX += deltaX;
            posY += deltaY;
            gameGrid.add(snakeP.get(0).body, posX, posY);
            snakeP.get(0).setPos(posX, posY);

            //Update for rest of body
            if (snakeP.size() > 1) {
                for (int x = 1; x < snakeP.size(); x++) {
                    gameGrid.getChildren().remove(snakeP.get(x).body);
                    gameGrid.add(snakeP.get(x).body, snakeP.get(x - 1).getOldXpos(), snakeP.get(x - 1).getOldYpos());
                    snakeP.get(x).setPos(snakeP.get(x - 1).getOldXpos(), snakeP.get(x - 1).getOldYpos());
                }

            }

            //FOOD WAS FOUND
            if (posX == foodPosX && posY == foodPosY) {
                //Grows Snake duh
                grow(snakeP);
            }

            //BODY CRASH
            for (int x = 1; x < snakeP.size(); x++) {
                if (posX == snakeP.get(x).getXpos() && posY == snakeP.get(x).getYpos()) {
                    die();
                }
            }
        }
    }


    public void movePCSnake() {

        //Updates head position
        gameGrid.getChildren().remove(snakePC.get(0).body);

        //Food Distance
        int xRout = foodPosX - pcPosX;
        int yRout = foodPosY - pcPosY;

        if (xRout > 0) {
            pcPosX += 1;
        } else if (xRout < 0) {
            pcPosX -= 1;
        } else if (yRout > 0) {
            pcPosY += 1;
        } else if (yRout < 0) {
            pcPosY -= 1;
        }

        gameGrid.add(snakePC.get(0).body, pcPosX, pcPosY);
        snakePC.get(0).setPos(pcPosX, pcPosY);

        //Update for rest of body
        if (snakePC.size() > 1) {
            for (int x = 1; x < snakePC.size(); x++) {
                gameGrid.getChildren().remove(snakePC.get(x).body);
                gameGrid.add(snakePC.get(x).body, snakePC.get(x - 1).getOldXpos(), snakePC.get(x - 1).getOldYpos());
                snakePC.get(x).setPos(snakePC.get(x - 1).getOldXpos(), snakePC.get(x - 1).getOldYpos());
            }

        }

        //FOOD WAS FOUND
        if (pcPosX == foodPosX && pcPosY == foodPosY) {
            //Grows Snake duh
            grow(snakePC);
        }

        //BODY CRASH
        for (int x = 1; x < snakePC.size(); x++) {
            if (pcPosX == snakePC.get(x).getXpos() && pcPosY == snakePC.get(x).getYpos()) {
                die();
            }
        }
    }

    //Detects Key Presses
    public void keyPressedProcess(KeyEvent event) {
        //If you GameOver and Restart
        if (!start && dead && event.getCode() == KeyCode.ENTER) {
            pause.setText("Нажмите [Enter], чтобы прервать");
            gameOver.setText("");
            loop.play();
            start = true;
            dead = false;
        } else if (!start && !dead) {
            pause.setText("Нажмите [Enter], чтобы прервать");
            loop.play();
            start = true;
        }

        //If Enter is pressed, game will pause
        if (event.getCode() == KeyCode.ENTER) {
            pause.setText("Нажмите любую кнопку, чтобы продолжить");
            loop.stop();
            start = false;
        }

        //Changes direction to UP when up/W is pressed
        if (deltaY == 0 && (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)) {
            deltaX = 0;
            deltaY = -1;
        } else if (deltaY == 0 && (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)) {
            deltaX = 0;
            deltaY = 1;
        } else if (deltaX == 0 && (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)) {
            deltaX = -1;
            deltaY = 0;
        } else if (deltaX == 0 && (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)) {
            deltaX = 1;
            deltaY = 0;
        }

        //Closes program when escape is pressed
        if (event.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
    }

    //Fills Grid with rectangles
    public void fillGrid() {
        for (int x = 0; x < gridSizeSquared; x++) {
            gameGrid.addColumn(x, new Rectangle(12, 12, Color.GRAY));

            for (int y = 1; y < gridSizeSquared; y++) {
                gameGrid.addRow(y, new Rectangle(12, 12, Color.GRAY));
            }
        }
    }

    //Changes randomly Food's position
    public void placeFood() {
        Random rPos = new Random();

        int newPosX = rPos.nextInt(gridSizeSquared);
        int newPosY = rPos.nextInt(gridSizeSquared);

        foodPosX = newPosX;
        foodPosY = newPosY;

        gameGrid.getChildren().remove(food);
        gameGrid.add(food, newPosX, newPosY);
    }

    //Grows Snake's Body
    public void grow(ArrayList<Snake> snake) {
        //Adds new Tail where last Tail's position was
        snake.add(new Snake(snake.get(snake.size() - 1).getOldXpos(),
                snake.get(snake.size() - 1).getOldYpos()));

        gameGrid.add(snake.get(snake.size() - 1).body,
                snake.get(snake.size() - 1).getOldXpos(),
                snake.get(snake.size() - 1).getOldYpos());

        placeFood();

    }

    //Game Over
    public void die() {

        int size = snakeP.size();

        //First Removes all but the head from the grid
        for (int x = size - 1; x > 0; x--) {
            gameGrid.getChildren().remove(snakeP.get(x).body);
        }

        //Removes all but the head from the arrayList
        if (size > 1) {
            snakeP.subList(1, size).clear();
        }

        //Pauses Game
        start = false;
        dead = true;
        loop.stop();

        gameOver.setText("Игра окончена");
        pause.setText("Нажмите Enter, чтобы перезапустить игру.");

        //Generates new Position for the snake
        posX = new Random().nextInt(gridSizeSquared);
        posY = new Random().nextInt(gridSizeSquared);

        //Places Snake in new Position
        gameGrid.getChildren().remove(snakeP.get(0).body);
        gameGrid.add(snakeP.get(0).body, posX, posY);
        snakeP.get(0).setPos(posX, posY);

    }

}