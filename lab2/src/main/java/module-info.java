module org.openjfx.lab2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.lab2 to javafx.fxml;
    opens org.openjfx.lab2.controller to javafx.fxml;

    exports org.openjfx.lab2;
    exports org.openjfx.lab2.controller;
}