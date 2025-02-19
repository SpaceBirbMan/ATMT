package stud.a4a.automatons;

import java.util.ArrayList;
import java.util.List;

public class Bus {
    private static List<Rule> rules = new ArrayList<>(); // Список всех правил
    private static List<String> chains = new ArrayList<>(); // Список цепочек

    // Метод для добавления нового правила
    public static void addRule(Rule rule) {
        rules.add(rule);
    }

    // Метод для получения всех правил
    public static List<Rule> getRules() {
        return rules;
    }

    // Метод для очистки всех правил
    public static void clear() {
        rules.clear();
        chains.clear();
    }

    // Метод для добавления цепочки
    public static void addChain(String chain) {
        chains.add(chain);
    }

    // Метод для получения всех цепочек
    public static List<String> getChains() {
        return chains;
    }

    // Метод для вывода всех правил
    public static void printRules() {
        for (Rule rule : rules) {
            System.out.println(rule);
        }
    }

    // Метод для вывода всех цепочек
    public static void printChains() {
        for (String chain : chains) {
            System.out.println(chain);
        }
    }
}
