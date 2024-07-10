package com.example.gui;

import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.domain.User;
import com.example.service.Service;
import com.example.validator.UserValidator;
import com.example.validator.ValidationException;
import com.example.validator.Validator;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Service service;
    private Stage stage;
    private Validator<User> validator;

    public void setService(Service service) {
        this.service = service;
        this.validator = new UserValidator();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        User loggedUser = null;
        Tester tester = service.searchTesterByUsername(username);
        Programmer programmer = service.searchProgrammerByUsername(username);

        if (tester == null && programmer == null) {
            System.out.println("User not found.");
            return;
        }
        else if (tester == null) {
            if (programmer.getPassword().equals(password)) {
                loggedUser = programmer;
                try {
                    FXMLLoader programmerLoader = new FXMLLoader();
                    programmerLoader.setLocation(getClass().getResource("/com/example/gui/views/programmer_view.fxml"));
                    AnchorPane programmerLayout = programmerLoader.load();

                    ProgrammerController programmerController = programmerLoader.getController();
                    programmerController.setLoggedUser((Programmer)loggedUser);
                    programmerController.setService(service);

                    Scene scene = new Scene(programmerLayout);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.setMaximized(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (programmer == null) {
            if (tester.getPassword().equals(password)) {
                loggedUser = tester;
                try {
                    FXMLLoader testerLoader = new FXMLLoader();
                    testerLoader.setLocation(getClass().getResource("/com/example/gui/views/tester_view.fxml"));
                    AnchorPane testerLayout = testerLoader.load();

                    TesterController testerController = testerLoader.getController();
                    testerController.setLoggedUser((Tester)loggedUser);
                    testerController.setService(service);

                    Scene scene = new Scene(testerLayout);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.setMaximized(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("User not found.");
            return;
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
