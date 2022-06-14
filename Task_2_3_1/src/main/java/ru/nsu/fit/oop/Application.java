package ru.nsu.fit.oop;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.nsu.fit.oop.application.configuration.Configuration;
import ru.nsu.fit.oop.application.snakegame.SnakeGame;

/**
 * The main class for launching the application. At start opens menu and sets default configuration of the game.
 */
public class Application extends javafx.application.Application {
    private final Configuration DEFAULT_CONFIGURATION = new Configuration(36, 20, 20, 13, 0, 10, 100);
    private final Image ICON = new Image(String.valueOf(getClass().getResource("/ru/nsu/fit/oop/images/icon.png").toExternalForm()));

    /**
     * Starts snake game application.
     *
     * @param primaryStage - the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(ICON);
        SnakeGame game = new SnakeGame(DEFAULT_CONFIGURATION);
        game.setStage(primaryStage);
        primaryStage.show();
    }

    /**
     * Launches a snake game application.
     *
     * @param args - arguments for the application launching.
     */
    public static void main(String[] args) {
        launch();
    }
}
