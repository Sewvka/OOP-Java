package ru.nsu.fit.oop.snakegame.game;

import ru.nsu.fit.oop.application.configuration.Configuration;
import ru.nsu.fit.oop.snakegame.sprite.Sprite;
import ru.nsu.fit.oop.snakegame.sprite.board.Board;
import ru.nsu.fit.oop.snakegame.sprite.fruit.Fruit;
import ru.nsu.fit.oop.snakegame.sprite.snake.Direction;
import ru.nsu.fit.oop.snakegame.sprite.snake.Snake;

import java.util.List;

/**
 * The model for the snake game.
 */
public class Game {
    private final Configuration configuration;
    private final Board board;
    private final Snake snake;
    private final List<Fruit> food;
    private GameState gameState;

    /**
     * Class constructor.
     * @param configuration - the current configuration of the game.
     * @param board - the model of the board for the game.
     * @param snake - the model of the snake for the game.
     * @param food - the model of the fruits for the game.
     */
    public Game(Configuration configuration, Board board, Snake snake, List<Fruit> food) {
        this.configuration = configuration;
        this.board = board;
        this.snake = snake;
        this.food = food;
        this.gameState = GameState.PLAY;
    }
    /**
     * Returns current score.
     * @return current score.
     */
    public int getScore() {
        return snake.getLength() - 3;
    }

    /**
     * Returns current snake game state.
     * @return current snake game state.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Changes snake direction.
     * @param direction - new snake direction.
     */
    public void setSnakeDirection(Direction direction) {
        snake.setDirection(direction);
    }

    private void updateGameState() {
        if (!snake.intersects(board) || snake.intersects(snake)) {
            gameState = GameState.DEFEAT;
        } else if (getScore() == configuration.maximumScore()) {
            gameState = GameState.VICTORY;
        } else {
            gameState = GameState.PLAY;
        }
    }

    private void updateSprite(Sprite sprite) {
        do {
            sprite.update(configuration.rowsNumber(), configuration.columnsNumber());
        } while (snake.intersects(sprite) ||
                food.stream().anyMatch(other -> other != sprite && other.intersects(sprite)));
    }

    private void eatFood() {
        for (Fruit fruit : food) {
            if (snake.intersects(fruit)) {
                snake.grow();
                updateSprite(fruit);
            }
        }
    }

    /**
     * Initialize new snake game.
     */
    public void start() {
        snake.start(snake.getLength() * configuration.squareSize(), (configuration.columnsNumber() >> 1) * configuration.squareSize());
        food.forEach(this::updateSprite);
    }

    /**
     * Update all components of the game.
     */
    public void update() {
        if (gameState == GameState.PLAY) {
            eatFood();
            snake.update(configuration.squareSize(), configuration.squareSize());
            updateGameState();
        }
    }
}
