package stud.a4a.automatons;

import javafx.scene.layout.AnchorPane;

public abstract class TabController {
    protected AnchorPane root;

    public TabController(AnchorPane root) {
        this.root = root;
        initializeUI();
    }

    protected abstract void initializeUI();

}
