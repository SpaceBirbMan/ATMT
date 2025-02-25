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

    // Метод для определения типа грамматики
    public String determineGrammarType() {
        if (rules.isEmpty()) {
            return "Грамматика не содержит правил.";
        }

        boolean isRegular = true;
        boolean isContextFree = true;
        boolean isContextSensitive = true;

        for (Rule rule : rules) {
            String leftSide = rule.getNonTerminal();
            List<String> rightSides = rule.getProductions();

            // Проверка на регулярную грамматику (тип 3)
            for (String rightSide : rightSides) {
                if (rightSide.isEmpty()) {
                    // Пустая строка допустима в регулярной грамматике (например, S -> ε)
                    continue;
                }
                if (rightSide.length() > 2 || (rightSide.length() == 2 && !isTerminal(String.valueOf(rightSide.charAt(1))))) {
                    isRegular = false;
                }
            }

            // Проверка на контекстно-свободную грамматику (тип 2)
            if (leftSide.length() != 1 || !Character.isUpperCase(leftSide.charAt(0))) {
                isContextFree = false;
            }

            // Проверка на контекстно-зависимую грамматику (тип 1)
            for (String rightSide : rightSides) {
                if (rightSide.isEmpty()) {
                    // Пустая строка допустима только если левая часть состоит из одного символа
                    if (leftSide.length() != 1) {
                        isContextSensitive = false;
                    }
                } else if (leftSide.length() > rightSide.length()) {
                    isContextSensitive = false;
                }
            }
        }

        if (isRegular) {
            return "Регулярная грамматика (тип 3)";
        } else if (isContextFree) {
            return "Контекстно-свободная грамматика (тип 2)";
        } else if (isContextSensitive) {
            return "Контекстно-зависимая грамматика (тип 1)";
        } else {
            return "Грамматика общего вида (тип 0)";
        }
    }

    // Метод для описания языка, порождаемого грамматикой
    public String describeLanguage() {
        String grammarType = determineGrammarType();
        StringBuilder description = new StringBuilder();

        description.append("Тип грамматики: ").append(grammarType).append("\n");

        // Пример описания языка
        if (grammarType.contains("Регулярная")) {
            description.append("Язык состоит из цепочек, которые могут быть распознаны конечным автоматом.\n");
        } else if (grammarType.contains("Контекстно-свободная")) {
            description.append("Язык состоит из цепочек, которые могут быть распознаны стековым автоматом.\n");
        } else if (grammarType.contains("Контекстно-зависимая")) {
            description.append("Язык состоит из цепочек, которые могут быть распознаны линейно-ограниченным автоматом.\n");
        } else {
            description.append("Язык состоит из цепочек, которые могут быть распознаны машиной Тьюринга.\n");
        }

        return description.toString();
    }
}