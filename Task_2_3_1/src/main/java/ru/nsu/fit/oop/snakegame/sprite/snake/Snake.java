package ru.nsu.fit.oop.snakegame.sprite.snake;

import ru.nsu.fit.oop.snakegame.sprite.board.Board;
import ru.nsu.fit.oop.snakegame.sprite.fruit.Fruit;
import ru.nsu.fit.oop.snakegame.cell.Cell;
import ru.nsu.fit.oop.snakegame.sprite.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is a snake model from the snake game.
 */
public abstract class Snake implements Sprite {
    private final int INITIAL_LENGTH = 3;
    private Direction direction;
    private Cell head;
    private final List<Cell> body;

    /**
     * Class constructor.
     *
     * @param width  - snake cell's width.
     * @param height - snake cell's height.
     */
    public Snake(double width, double height) {
        this.body = Stream.generate(() -> new Cell(width, height)).limit(INITIAL_LENGTH).collect(Collectors.toCollection(ArrayList::new));
        this.head = this.body.get(0);
    }

    /**
     * Returns current snake direction.
     *
     * @return current snake direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Returns current snake length.
     *
     * @return current snake length.
     */
    public int getLength() {
        return body.size();
    }

    /**
     * Changes snake' direction.
     *
     * @param direction new snake's direction.
     */
    public void setDirection(Direction direction) {
        if (this.direction.opposite(direction)) {
            return;
        }
        this.direction = direction;
    }

    /**
     * Initialize snake.
     *
     * @param headPositionX - initial snake's head x coordinate.
     * @param headPositionY - initial snake's head x coordinate.
     */
    public void start(double headPositionX, double headPositionY) {
        direction = Direction.RIGHT;
        head.setPosition(headPositionX, headPositionY);
        for (int i = 1; i < INITIAL_LENGTH; ++i) {
            body.get(i).setPosition(body.get(i - 1).getX() - head.getWidth(), headPositionY);
        }
    }

    /**
     * Increases the size of the snake by one.
     */
    public void grow() {
        Cell flake = new Cell(head.getWidth(), head.getHeight());
        body.add(flake);
    }

    /**
     * Returns sprite's boundary.
     *
     * @return sprite's boundary.
     */
    @Override
    public List<Cell> getBoundary() {
        return Collections.unmodifiableList(body);
    }

    /**
     * Determine if the sprite intersects with another sprite.
     *
     * @param sprite - another sprite.
     * @return true if the sprite intersects with another sprite.
     */
    @Override
    public boolean intersects(Sprite sprite) {
        if (sprite == this) {
            return body.stream().anyMatch((cell -> cell != head && cell.intersects(head)));
        }
        if (sprite instanceof Board) {
            return head.intersects((Cell) sprite.getBoundary());
        }
        if (sprite instanceof Fruit) {
            return body.stream().anyMatch((cell -> cell.intersects((Cell) sprite.getBoundary())));
        }
        return false;
    }

    /**
     * Changes sprite's coordinates.
     *
     * @param x -  new x coordinate.
     * @param y - new y coordinate.
     */
    @Override
    public void update(double x, double y) {
        Cell head = new Cell(this.head.getWidth(), this.head.getHeight());
        switch (direction) {
            case RIGHT -> head.setPosition(this.head.getX() + x, this.head.getY());
            case LEFT -> head.setPosition(this.head.getX() - x, this.head.getY());
            case UP -> head.setPosition(this.head.getX(), this.head.getY() - y);
            case DOWN -> head.setPosition(this.head.getX(), this.head.getY() + y);
        }
        this.head = head;
        body.add(0, head);
        body.remove(getLength() - 1);

    }

    /**
     * Renders the sprite.
     *
     * @param object - rendered sprite.
     */
    @Override
    public abstract void render(Object object);
}
