package ru.nsu.fit.oop.snakegamefx.game;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.nsu.fit.oop.application.configuration.Configuration;
import ru.nsu.fit.oop.application.snakegame.SnakeGame;
import ru.nsu.fit.oop.application.snakegame.SnakeGameController;
import ru.nsu.fit.oop.snakegame.sprite.snake.Direction;

import static javafx.animation.Animation.Status.PAUSED;
import static javafx.animation.Animation.Status.RUNNING;
import static ru.nsu.fit.oop.snakegame.game.GameState.*;

/**
 * This class interacts with the components of the MVC model for the snake game
 */
public class GameController {
    private final SnakeGameController controller;
    private final GameFX snakeGame;
    private final Timeline timeline;
    private final Stage stage;
    /**
     * Class constructor.
     *
     * @param controller - controller for the snake game application.
     * @param snakeGame  - the model of the snake game.
     * @param timeline   - the instance of the class responsible for changing the frames of the snake game.
     */
    public GameController(SnakeGameController controller, GameFX snakeGame, Timeline timeline, Stage stage) {
        this.controller = controller;
        this.snakeGame = snakeGame;
        this.timeline = timeline;
        this.stage = stage;
    }

    /**
     * Handles key event.
     *
     * @param event - user pressing one of the keyboard buttons.
     */
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        if (timeline.getStatus() == PAUSED) {
            timeline.play();
            return;
        }
        switch (code) {
            case RIGHT -> {
                snakeGame.setSnakeDirection(Direction.RIGHT);
                timeline.play();
            }
            case LEFT -> {
                snakeGame.setSnakeDirection(Direction.LEFT);
                timeline.play();
            }
            case UP -> {
                snakeGame.setSnakeDirection(Direction.UP);
                timeline.play();
            }
            case DOWN -> {
                snakeGame.setSnakeDirection(Direction.DOWN);
                timeline.play();
            }
            case ESCAPE -> timeline.stop();
        }
    }

    /**
     * Runs snake game.
     *
     * @param frame - frame on which snake game should be rendered.
     */
    public void run(Group frame) {
        if (timeline.getStatus() == RUNNING && (snakeGame.getGameState() == DEFEAT || snakeGame.getGameState() == VICTORY)) {
            timeline.stop();
            SnakeGame game = new SnakeGame(new Configuration(36, 20, 20, 13, 0, 10, 100));
            game.setStage(stage);
        }
        snakeGame.update();
        controller.updateScore();
        snakeGame.render(frame);
    }
}
