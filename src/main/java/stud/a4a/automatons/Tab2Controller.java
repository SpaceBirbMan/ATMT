package stud.a4a.automatons;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab2Controller extends TabController {
    private RulesTextArea rulesField;
    private ResultTextArea resultTextArea;
    private ParseTreeCanvas canvas;
    private Button processButton;

    public Tab2Controller(AnchorPane root) {
        super(root);
        initializeUI();
    }

    @Override
    protected void initializeUI() {
        rulesField = new RulesTextArea();
        resultTextArea = new ResultTextArea();
        canvas = new ParseTreeCanvas(800, 600);
        processButton = new Button("Обработать");

        resultTextArea.setLayoutX(10);
        resultTextArea.setLayoutY(180);
        resultTextArea.setPrefSize(380, 250);

        rulesField.setLayoutX(10);
        rulesField.setLayoutY(10);
        rulesField.setPrefSize(380, 100);

        canvas.setLayoutX(10);
        canvas.setLayoutY(180);

        processButton.setLayoutX(400);
        processButton.setLayoutY(120);

        // Обработчик для кнопки
        processButton.setOnAction(event -> processData());

        root.getChildren().addAll(rulesField, canvas, processButton, resultTextArea);
    }

    private void processData() {
        // Собираем данные
        GrammarProcessor grammarProcessor = rulesField.parseGrammar();

        // Определяем тип грамматики и описание языка
        String grammarType = grammarProcessor.determineGrammarType();
        String languageDescription = grammarProcessor.describeLanguage();

        // Выводим результат
        resultTextArea.setText("Тип грамматики: " + grammarType + "\n\n" + languageDescription);
    }

    private void openTreeWindow(ParseTreeNode rootNode) {
        // Создаем окно для дерева
        TreeWindow treeWindow = new TreeWindow(rootNode);
        treeWindow.show();
    }

}
