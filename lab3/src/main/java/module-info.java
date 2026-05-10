module org.openjfx.lab3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.lab3 to javafx.fxml;
    opens org.openjfx.lab3.controller to javafx.fxml;

    exports org.openjfx.lab3;
    exports org.openjfx.lab3.controller;
    exports org.openjfx.lab3.threads;
}
