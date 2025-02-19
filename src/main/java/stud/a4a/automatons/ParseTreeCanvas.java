package stud.a4a.automatons;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ParseTreeCanvas extends Canvas {
    private ParseTreeNode root;

    public ParseTreeCanvas(double width, double height) {
        super(width, height);
    }

    public void setTree(ParseTreeNode root) {
        this.root = root;
        drawTree();
    }

    private void drawTree() {
        if (root == null) return;
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight()); // Очищаем перед отрисовкой
        gc.setFont(new Font(14));
        drawNode(gc, root, getWidth() / 2, 50, getWidth() / 4);
    }

    private void drawNode(GraphicsContext gc, ParseTreeNode node, double x, double y, double offset) {
        gc.setFill(Color.BLACK);
        gc.fillText(node.symbol, x - 10, y);

        double childY = y + 50; // Расстояние между уровнями
        int numChildren = node.children.size();
        double startX = x - (offset * (numChildren - 1)) / 2;

        for (int i = 0; i < numChildren; i++) {
            double childX = startX + i * offset;
            gc.setStroke(Color.BLACK);
            gc.strokeLine(x, y + 5, childX, childY - 5); // Линия к потомку
            drawNode(gc, node.children.get(i), childX, childY, offset / 2);
        }
    }
}

