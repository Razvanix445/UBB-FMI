package com.example.lab7.gui;

import com.example.lab7.domain.Utilizator;
import com.example.lab7.repository.*;
import com.example.lab7.repository.paging.Page;
import com.example.lab7.repository.paging.Pageable;
import com.example.lab7.repository.paging.PageableImplementation;
import com.example.lab7.repository.paging.PagingRepository;
import com.example.lab7.service.Service;
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
            String url = "jdbc:postgresql://localhost:1234/socialnetwork";
            String username = "postgres";
            String password = "2003razvi";
            //Repository<Long, Utilizator> utilizatorDBRepository = new UserDBRepository(url, username, password);
            //UserDBRepository utilizatorDBRepository = new UserDBRepository(url, username, password);
            PagingRepository<Long, Utilizator> userDBPagingRepository = new UserDBPagingRepository(url, username, password);

            //Repository<Long, Prietenie> prietenieDBRepository = new FriendshipDBRepository(url, username, password);
            FriendshipDBRepository prietenieDBRepository = new FriendshipDBRepository(url, username, password);
            MessageDBRepository messageDBRepository = new MessageDBRepository(url, username, password);
            FriendRequestDBRepository friendRequestDBRepository = new FriendRequestDBRepository(url, username, password);

            service = new Service(userDBPagingRepository, prietenieDBRepository, messageDBRepository, friendRequestDBRepository);

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
        loginLoader.setLocation(getClass().getResource("/com/example/lab7/gui/views/login-view.fxml"));
        AnchorPane loginLayout = loginLoader.load();
        primaryStage.setScene(new Scene(loginLayout));

        LoginController loginController = loginLoader.getController();
        loginController.setService(service);
        loginController.setStage(primaryStage);
    }
}