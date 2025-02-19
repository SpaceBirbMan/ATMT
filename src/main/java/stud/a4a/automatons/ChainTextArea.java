package stud.a4a.automatons;

import javafx.application.Platform;

public class ChainTextArea extends RuledTextArea {

    public ChainTextArea() {
        setPromptText("Введите целевую цепочку (например, a+c)");

        textProperty().addListener((observable, oldValue, newValue) -> {
            // Если строка заканчивается на новой строке, начинаем парсить цепочку
            if (newValue.endsWith("\n")) {
                parseChain(newValue.trim());
                Platform.runLater(() -> setText("")); // Очистка поля после парсинга
            }
        });
    }

    private void parseChain(String input) {
        // Здесь можно выполнить дополнительную обработку цепочки, если требуется.
        // Например, добавить цепочку в Bus или сделать что-то с ней.

        // Добавим цепочку в Bus
        Bus.addChain(input);
    }
}
