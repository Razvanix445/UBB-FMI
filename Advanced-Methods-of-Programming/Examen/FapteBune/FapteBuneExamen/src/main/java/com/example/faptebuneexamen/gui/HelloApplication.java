package com.example.faptebuneexamen.gui;

import com.example.faptebuneexamen.domain.Nevoie;
import com.example.faptebuneexamen.domain.Persoana;
import com.example.faptebuneexamen.repository.Repository;
import com.example.faptebuneexamen.repository.RepositoryDBNevoie;
import com.example.faptebuneexamen.repository.RepositoryDBPersoana;
import com.example.faptebuneexamen.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Service service;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            String url = "jdbc:postgresql://localhost:1234/faptebune";
            String username = "postgres";
            String password = "2003razvi";

            Repository<Long, Persoana> repoTables = new RepositoryDBPersoana(url, username, password);
            Repository<Long, Nevoie> repoMenuItems = new RepositoryDBNevoie(url, username, password);
            service = new Service(repoTables, repoMenuItems);

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

        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("/com/example/faptebuneexamen/login-view.fxml"));
        AnchorPane loginLayout = loginLoader.load();
        primaryStage.setScene(new Scene(loginLayout));

        LoginController loginController = loginLoader.getController();
        loginController.setService(service);
        loginController.setStage(primaryStage);
    }
}