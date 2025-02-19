package stud.a4a.automatons;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TreeWindow {
    private Stage stage;
    private ParseTreeCanvas canvas;

    public TreeWindow(ParseTreeNode rootNode) {
        stage = new Stage();
        StackPane root = new StackPane();
        canvas = new ParseTreeCanvas(800, 600);

        // Отображаем дерево на канвасе
        canvas.setTree(rootNode);

        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        stage.setTitle("Дерево вывода");
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}
