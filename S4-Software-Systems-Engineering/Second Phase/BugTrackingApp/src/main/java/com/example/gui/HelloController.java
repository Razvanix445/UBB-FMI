package com.example.gui;

import com.example.repository.database.BugDBRepository;
import com.example.repository.database.ProgrammerDBRepository;
import com.example.repository.database.TesterDBRepository;
import com.example.repository.interfaces.IBugRepository;
import com.example.repository.interfaces.IProgrammerRepository;
import com.example.repository.interfaces.ITesterRepository;
import com.example.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController extends Application {

    private Service service;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            String url = "jdbc:postgresql://localhost:1234/BugTracking";
            String username = "postgres";
            String password = "2003razvi";

            ITesterRepository testerRepository = new TesterDBRepository(url, username, password);
            IProgrammerRepository programmerRepository = new ProgrammerDBRepository(url, username, password);
            IBugRepository bugRepository = new BugDBRepository(url, username, password);
            service = new Service(testerRepository, programmerRepository, bugRepository);

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
        loginLoader.setLocation(getClass().getResource("/com/example/gui/views/login_view.fxml"));
        AnchorPane loginLayout = loginLoader.load();
        primaryStage.setScene(new Scene(loginLayout));

        LoginController loginController = loginLoader.getController();
        loginController.setService(service);
        loginController.setStage(primaryStage);
    }
}
