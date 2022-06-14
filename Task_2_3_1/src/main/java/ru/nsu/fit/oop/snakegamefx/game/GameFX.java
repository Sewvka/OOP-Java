package ru.nsu.fit.oop.snakegamefx.game;

import javafx.scene.Group;
import ru.nsu.fit.oop.application.configuration.Configuration;
import ru.nsu.fit.oop.snakegame.sprite.board.Board;
import ru.nsu.fit.oop.snakegame.sprite.fruit.Fruit;
import ru.nsu.fit.oop.snakegame.sprite.snake.Snake;
import ru.nsu.fit.oop.snakegame.game.Game;
import ru.nsu.fit.oop.snakegame.game.GameState;

import java.util.List;

/**
 * This class represents a View in the MVC model for the snake game.
 * Contains the method {@link #render(Group)} to render all game components on a frame.
 */
public class GameFX extends Game {
    private final Board board;
    private final Snake snake;
    private final List<Fruit> food;

    /**
     * Class constructor. Allows creating a game model based on sprites.
     *
     * @param configuration -  a current configuration of the snake game.
     * @param board         - a model of the board for the game.
     * @param snake         - a model of the snake for the game.
     * @param food          - a list of fruits' models to be placed on the board.
     */
    public GameFX(Configuration configuration, Board board, Snake snake, List<Fruit> food) {
        super(configuration, board, snake, food);
        this.board = board;
        this.snake = snake;
        this.food = food;
    }

    /**
     * Renders all components of the snake game on the specified frame if the game is not over.
     *
     * @param frame - a frame on which snake game should be rendered.
     */
    public void render(Group frame) {
        if (getGameState() == GameState.PLAY) {
            frame.getChildren().clear();
            board.render(frame);
            food.forEach(fruit -> fruit.render(frame));
            snake.render(frame);
        }
    }
}
