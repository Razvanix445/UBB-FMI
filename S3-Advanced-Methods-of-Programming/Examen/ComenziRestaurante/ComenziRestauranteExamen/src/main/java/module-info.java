module com.example.comenzirestauranteexamen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.comenzirestauranteexamen.domain to javafx.base;
    opens com.example.comenzirestauranteexamen to javafx.fxml;
    exports com.example.comenzirestauranteexamen;
    exports com.example.comenzirestauranteexamen.gui;
    opens com.example.comenzirestauranteexamen.gui to javafx.fxml;
}