module stud.a4a.automatons {
    requires javafx.controls;
    requires javafx.fxml;


    opens stud.a4a.automatons to javafx.fxml;
    exports stud.a4a.automatons;
}