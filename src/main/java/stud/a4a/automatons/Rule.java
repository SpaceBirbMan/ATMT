package stud.a4a.automatons;

import java.util.List;

public class Rule {
    private String nonTerminal; // Левый символ (например, "A")
    private List<String> productions; // Правая часть (например, ["a+c", "b"])

    public Rule() {}

    public Rule(String nonTerminal, List<String> productions) {
        if (nonTerminal == null || productions == null || productions.isEmpty()) {
            throw new IllegalArgumentException("Нетерминал и его правила не могут быть пустыми.");
        }
        this.nonTerminal = nonTerminal;
        this.productions = productions;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public List<String> getProductions() {
        return productions;
    }

    @Override
    public String toString() {
        return nonTerminal + " = " + String.join(" | ", productions);
    }
}
