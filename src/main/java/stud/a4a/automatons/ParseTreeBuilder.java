package stud.a4a.automatons;

import java.util.*;

public class ParseTreeBuilder {
    private Map<String, List<String>> rules;

    public ParseTreeBuilder(Map<String, List<String>> rules) {
        this.rules = rules;
    }

    public ParseTreeNode buildTree(String startSymbol, String targetString) {
        return constructTree(startSymbol, targetString, new HashSet<>());
    }

    private ParseTreeNode constructTree(String current, String target, Set<String> visited) {
        if (visited.contains(current)) return null;
        visited.add(current);

        ParseTreeNode node = new ParseTreeNode(current);
        if (current.equals(target)) return node;

        if (rules != null) {
            for (Map.Entry<String, List<String>> entry : rules.entrySet()) {
                String nonTerminal = entry.getKey();
                List<String> productions = entry.getValue();

                int index = current.indexOf(nonTerminal);
                if (index == -1) continue;

                for (String production : productions) {
                    String newString = current.substring(0, index) + production + current.substring(index + 1);
                    if (newString.length() > target.length()) continue;

                    ParseTreeNode child = constructTree(newString, target, new HashSet<>(visited));
                    if (child != null) node.addChild(child);
                }

                return node;
            }



        }
        return null;
    }
}
