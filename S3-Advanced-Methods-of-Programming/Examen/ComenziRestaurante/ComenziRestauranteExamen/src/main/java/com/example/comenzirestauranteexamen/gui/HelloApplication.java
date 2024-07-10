package com.example.comenzirestauranteexamen.gui;

import com.example.comenzirestauranteexamen.domain.MenuItem;
import com.example.comenzirestauranteexamen.domain.Order;
import com.example.comenzirestauranteexamen.domain.Table;
import com.example.comenzirestauranteexamen.repository.Repository;
import com.example.comenzirestauranteexamen.repository.RepositoryDBMenuItems;
import com.example.comenzirestauranteexamen.repository.RepositoryDBOrders;
import com.example.comenzirestauranteexamen.repository.RepositoryDBTables;
import com.example.comenzirestauranteexamen.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Service service;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            String url = "jdbc:postgresql://localhost:1234/comenzirestaurante";
            String username = "postgres";
            String password = "2003razvi";

            Repository<Long, Table> repoTables = new RepositoryDBTables(url, username, password);
            Repository<Long, MenuItem> repoMenuItems = new RepositoryDBMenuItems(url, username, password);
            Repository<Long, Order> repoOrders = new RepositoryDBOrders(url, username, password);
            service = new Service(repoTables, repoMenuItems, repoOrders);

            initView(stage);
            stage.setTitle("Hello!");
            stage.setWidth(800);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    private void initView(Stage primaryStage) throws IOException {

        FXMLLoader employeeLoader = new FXMLLoader();
        employeeLoader.setLocation(getClass().getResource("/com/example/comenzirestauranteexamen/gui/views/employee-view.fxml"));
        AnchorPane employeeLayout = employeeLoader.load();
        primaryStage.setScene(new Scene(employeeLayout));

        EmployeeController employeeController = employeeLoader.getController();
        employeeController.setService(service);
        employeeController.setStage(primaryStage);

        Iterable<Table> tables = service.getAllTables();
        for (Table table: tables) {
            FXMLLoader tableLoader = new FXMLLoader();
            tableLoader.setLocation(getClass().getResource("/com/example/comenzirestauranteexamen/gui/views/table-view.fxml"));
            AnchorPane tableLayout = tableLoader.load();

            Stage tableStage = new Stage();
            tableStage.setTitle("Table " + table.getId());
            tableStage.setScene(new Scene(tableLayout));

            TableController tableController = tableLoader.getController();
            tableController.setService(service);
            tableController.setStage(primaryStage);
            tableController.setTable(table);

            tableStage.show();
        }

    }
}
