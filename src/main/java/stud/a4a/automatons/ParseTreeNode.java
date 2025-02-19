package stud.a4a.automatons;

import java.util.*;

public class ParseTreeNode {
    String symbol;
    List<ParseTreeNode> children;

    public ParseTreeNode(String symbol) {
        this.symbol = symbol;
        this.children = new ArrayList<>();
    }

    public void addChild(ParseTreeNode child) {
        children.add(child);
    }
}
