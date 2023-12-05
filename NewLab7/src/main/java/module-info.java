module com.example.lab7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.lab7 to javafx.fxml;
    exports com.example.lab7;
    opens com.example.lab7.gui to javafx.fxml;
    exports com.example.lab7.gui;

    opens com.example.lab7.domain to javafx.base;
    exports com.example.lab7.domain;
    exports com.example.lab7.controller;
    opens com.example.lab7.controller to javafx.fxml;
}