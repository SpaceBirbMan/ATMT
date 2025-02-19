package stud.a4a.automatons;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainController {
    @FXML private AnchorPane tab1;
    @FXML private AnchorPane tab2;
    @FXML private AnchorPane tab3;
    @FXML private AnchorPane tab5;
    @FXML private AnchorPane tab6;
    @FXML private AnchorPane tab7;
    @FXML private AnchorPane tab9a;
    @FXML private AnchorPane tab11;
    @FXML private AnchorPane tab12;

    @FXML
    public void initialize() {
        new Tab1Controller(tab1);
    }
}
