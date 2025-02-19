package stud.a4a.automatons;

import java.util.List;

public class RulesTextArea extends RuledTextArea {

    public RulesTextArea() {
        setPromptText("Введите правила грамматики (например, A = a+c | b)");
    }

    public GrammarProcessor parseGrammar() {
        GrammarProcessor grammarProcessor = new GrammarProcessor();
        // Пример простого парсинга
        String[] lines = getText().split("\n");

        for (String line : lines) {
            // Убираем все пробелы перед и после строки
            line = line.replaceAll("\\s+", ""); // Убираем все пробелы в строке

            if (line.contains("=")) {
                String[] parts = line.split("=");
                String leftSide = parts[0].trim(); // Левое side (например, T, S)
                String[] rightSide = parts[1].split("\\|"); // Разделяем альтернативы

                // Создаем правило с левой частью (нетерминал) и правыми частями (список продукций)
                Rule rule = new Rule(leftSide, List.of(rightSide)); // Используем List.of для каждой альтернативы
                grammarProcessor.addRule(rule);
            }
        }

        return grammarProcessor;
    }
}
