package stud.a4a.automatons;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab1Controller extends TabController {
    private RulesTextArea rulesField;
    private ChainTextArea chainField;
    private ResultTextArea resultTextArea;
    private ParseTreeCanvas canvas;
    private Button processButton;

    public Tab1Controller(AnchorPane root) {
        super(root);
        initializeUI();
    }

    @Override
    protected void initializeUI() {
        rulesField = new RulesTextArea();
        chainField = new ChainTextArea();
        resultTextArea = new ResultTextArea();
        canvas = new ParseTreeCanvas(800, 600);
        processButton = new Button("Обработать");

        resultTextArea.setLayoutX(10);
        resultTextArea.setLayoutY(180);
        resultTextArea.setPrefSize(380, 250);

        rulesField.setLayoutX(10);
        rulesField.setLayoutY(10);
        rulesField.setPrefSize(380, 100);

        chainField.setLayoutX(10);
        chainField.setLayoutY(120);
        chainField.setPrefSize(380, 40);

        canvas.setLayoutX(10);
        canvas.setLayoutY(180);

        processButton.setLayoutX(400);
        processButton.setLayoutY(120);

        // Обработчик для кнопки
        processButton.setOnAction(event -> processData());

        root.getChildren().addAll(rulesField, chainField, canvas, processButton, resultTextArea);
    }

    private void processData() {
        // Собираем данные
        GrammarProcessor grammarProcessor = rulesField.parseGrammar();

        // Преобразуем список правил в карту
        Map<String, List<String>> rulesMap = new HashMap<>();
        for (Rule rule : grammarProcessor.getRules()) {
            rulesMap.put(rule.getNonTerminal(), rule.getProductions());
        }

        // Обновлено: создаем правила из парсинга и добавляем их в Bus
        for (Rule rule : grammarProcessor.getRules()) {
            for (String production : rule.getProductions()) {
                // Оборачиваем production в список
                Bus.addRule(new Rule(rule.getNonTerminal(), Collections.singletonList(production))); // Добавляем правила в Bus
            }
        }

        // Создаем ParseTreeBuilder с картой правил
        ParseTreeBuilder treeBuilder = new ParseTreeBuilder(rulesMap);
        ParseTreeNode root = treeBuilder.buildTree("S", chainField.getText());

        // Проверка на возможность получения цепочки
        List<List<String>> derivationPaths = grammarProcessor.generateDerivationPaths("S", chainField.getText());

        boolean error = false;

        if (derivationPaths.isEmpty()) {
            error = true;
            resultTextArea.setText("Ошибка: Цепочка недостижима с данной грамматикой."); // мало ошибок, надо catch
        } else {
            resultTextArea.setText("Вывод цепочки:\n");
            for (List<String> path : derivationPaths) {
                resultTextArea.appendText(String.join(" -> ", path) + "\n");
            }
        }

        // Добавляем цепочку в Bus
        Bus.addChain(chainField.getText());

        // Создаем окно для отображения дерева
        if (!error) {
            openTreeWindow(root); // Передаем ParseTreeNode
        }
    }

    private void openTreeWindow(ParseTreeNode rootNode) {
        // Создаем окно для дерева
        TreeWindow treeWindow = new TreeWindow(rootNode);
        treeWindow.show();
    }

}
