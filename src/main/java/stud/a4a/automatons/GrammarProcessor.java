package stud.a4a.automatons;

import java.util.*;

public class GrammarProcessor {
    private List<Rule> rules = new ArrayList<>(); // Список правил

    public GrammarProcessor() {}

    // Конструктор для инициализации с уже заданными правилами
    public GrammarProcessor(List<Rule> rules) {
        this.rules = rules != null ? rules : new ArrayList<>();
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules;
    }

    // Метод для проверки вывода заданной цепочки
    public boolean derive(String startSymbol, String targetString) {
        return deriveHelper(startSymbol, targetString, "");
    }

    /**
     * Генерирует все возможные пути вывода для заданной цепочки.
     */
    public List<List<String>> generateDerivationPaths(String startSymbol, String targetString) {
        List<List<String>> result = new ArrayList<>();
        deriveHelper(startSymbol, targetString, new ArrayList<>(), result);
        return result;
    }

    private void deriveHelper(String currentString, String targetString, List<String> path, List<List<String>> results) {
        path.add(currentString); // Добавляем текущее состояние вывода

        if (currentString.equals(targetString)) {
            results.add(new ArrayList<>(path)); // Нашли решение, сохраняем
            return;
        }

        // Ищем все возможные пути для каждого нетерминала в строке
        for (Rule rule : rules) {
            String nonTerminal = rule.getNonTerminal();
            List<String> productions = rule.getProductions();

            int index = currentString.indexOf(nonTerminal);
            if (index == -1) continue; // Нетерминал не найден, пропускаем

            for (String production : productions) {
                String newString = currentString.substring(0, index) + production + currentString.substring(index + 1);
                if (newString.length() > targetString.length()) continue; // Ограничиваем длину вывода

                deriveHelper(newString, targetString, new ArrayList<>(path), results);
            }
        }
    }

    private boolean deriveHelper(String currentSymbol, String targetString, String derivedString) {
        // Если текущий символ - терминал, проверяем, соответствует ли он целевой строке
        for (Rule rule : rules) {
            if (rule.getNonTerminal().equals(currentSymbol)) {
                return derivedString.equals(targetString);
            }
        }

        // Пробуем все правила для текущего символа
        for (Rule rule : rules) {
            if (rule.getNonTerminal().equals(currentSymbol)) {
                for (String production : rule.getProductions()) {
                    if (deriveHelper(production, targetString, derivedString + production)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Метод для генерации языка на основе грамматики
    public Set<String> generateLanguage(String startSymbol, int maxLength) {
        Set<String> language = new HashSet<>();
        generateHelper(startSymbol, "", maxLength, language);
        return language;
    }

    private void generateHelper(String currentSymbol, String currentString, int maxLength, Set<String> language) {
        // Если текущий символ - терминал, добавляем его в язык
        if (isTerminal(currentSymbol)) {
            if (currentString.length() <= maxLength) {
                language.add(currentString);
            }
            return;
        }

        // Пробуем все правила для текущего символа
        for (Rule rule : rules) {
            if (rule.getNonTerminal().equals(currentSymbol)) {
                for (String production : rule.getProductions()) {
                    generateHelper(production, currentString + production, maxLength, language);
                }
            }
        }
    }

    private boolean isTerminal(String symbol) {
        // Определяем, является ли символ терминалом (например, это не ключ грамматики)
        for (Rule rule : rules) {
            if (rule.getNonTerminal().equals(symbol)) {
                return false;
            }
        }
        return true;
    }

    // Метод для добавления правила
    public void addRule(Rule rule) {
        if (rule == null || rule.getNonTerminal() == null || rule.getProductions() == null) {
            throw new IllegalArgumentException("Правило не может быть пустым.");
        }

        rules.add(rule);
    }
}
