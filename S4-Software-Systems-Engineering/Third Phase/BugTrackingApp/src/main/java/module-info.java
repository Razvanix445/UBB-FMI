module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.gui to javafx.fxml;
    exports com.example.gui;

    opens com.example.domain to javafx.base;
    exports com.example.domain;
//    exports controller;
//    opens controller to javafx.fxml;
}