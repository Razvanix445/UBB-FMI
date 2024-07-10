module com.example.faptebuneexamen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.faptebuneexamen.service to javafx.fxml;
    opens com.example.faptebuneexamen.repository to javafx.fxml;
    opens com.example.faptebuneexamen.domain to javafx.base;
    opens com.example.faptebuneexamen to javafx.fxml;
    exports com.example.faptebuneexamen.service;
    exports com.example.faptebuneexamen.repository;
    exports com.example.faptebuneexamen;
    exports com.example.faptebuneexamen.gui;
    opens com.example.faptebuneexamen.gui to javafx.fxml;
}