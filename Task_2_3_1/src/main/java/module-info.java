module ru.nsu {
    requires javafx.controls;
    requires javafx.fxml;

    exports ru.nsu.fit.oop;
    opens ru.nsu.fit.oop to javafx.fxml;
    exports ru.nsu.fit.oop.snakegame.sprite;
    opens ru.nsu.fit.oop.snakegame.sprite to javafx.fxml;
    exports ru.nsu.fit.oop.snakegame.sprite.board;
    opens ru.nsu.fit.oop.snakegame.sprite.board to javafx.fxml;
    exports ru.nsu.fit.oop.snakegame.sprite.snake;
    opens ru.nsu.fit.oop.snakegame.sprite.snake to javafx.fxml;
    exports ru.nsu.fit.oop.snakegame.sprite.fruit;
    opens ru.nsu.fit.oop.snakegame.sprite.fruit to javafx.fxml;
    exports ru.nsu.fit.oop.snakegame.game;
    opens ru.nsu.fit.oop.snakegame.game to javafx.fxml;
    exports ru.nsu.fit.oop.snakegame.cell;
    opens ru.nsu.fit.oop.snakegame.cell to javafx.fxml;
    exports ru.nsu.fit.oop.snakegamefx.skin;
    opens ru.nsu.fit.oop.snakegamefx.skin to javafx.fxml;
    exports ru.nsu.fit.oop.snakegamefx.sprite;
    opens ru.nsu.fit.oop.snakegamefx.sprite to javafx.fxml;
    exports ru.nsu.fit.oop.snakegamefx.game;
    opens ru.nsu.fit.oop.snakegamefx.game to javafx.fxml;
    exports ru.nsu.fit.oop.application.configuration;
    opens ru.nsu.fit.oop.application.configuration to javafx.fxml;
    exports ru.nsu.fit.oop.application.snakegame;
    opens ru.nsu.fit.oop.application.snakegame to javafx.fxml;
}