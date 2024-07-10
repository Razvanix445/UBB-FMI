package com.example.lab7.gui;

import com.example.lab7.Session;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.service.Service;
import com.example.lab7.validators.UtilizatorValidator;
import com.example.lab7.validators.ValidationException;
import com.example.lab7.validators.Validator;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    @FXML
    private Label errorMessageLabel;

    private Service service;
    private Validator<Utilizator> validator;
    private Stage stage;
    private FadeTransition fadeOut;

    public void setService(Service service) {
        this.service = service;
        this.validator = new UtilizatorValidator();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        Utilizator loggedUser = service.login(username, password);

        if (loggedUser != null && isValidCredentials(username, password)) {
            Session.setLoggedUser(loggedUser);
            openMainCrudWindow();
        } else {
            showErrorAlert("Credentiale invalide. Incearca din nou.", "error-label");
        }
    }

    @FXML
    private void handleSignUp() {
        openSignUpWindow();
    }

    private void openSignUpWindow() {
        try {
            FXMLLoader signUpLoader = new FXMLLoader();
            signUpLoader.setLocation(getClass().getResource("/com/example/lab7/gui/views/signup-view.fxml"));
            AnchorPane signUpLayout = signUpLoader.load();

            SignUpController signUpController = signUpLoader.getController();
            signUpController.setService(service);

            Stage signUpStage = new Stage();
            signUpStage.setTitle("Sign Up");
            signUpStage.initModality(Modality.WINDOW_MODAL);
            signUpStage.setScene(new Scene(signUpLayout));

            signUpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidCredentials(String username, String password) {
        try {
            Utilizator existingUser = service.cautaUtilizatorDupaUsername(username);
            if (existingUser == null || password.isEmpty()) {
                showErrorAlert("Datele introduse sunt incorecte!", "error-label");
                return false;
            }
        } catch (ValidationException e) {
            showErrorAlert(e.getMessage(), "error-label");
            return false;
        }
        return true;
    }

    private void openMainCrudWindow() {
        try {
            FXMLLoader userLoader = new FXMLLoader();
            userLoader.setLocation(getClass().getResource("/com/example/lab7/gui/views/user-view2.fxml"));
            AnchorPane userLayout = userLoader.load();

            UtilizatorController utilizatorController = userLoader.getController();
            utilizatorController.setService(service);

            Scene scene = new Scene(userLayout);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(true);

        } catch (IOException e) {
            showErrorAlert(e.getMessage(), "error-label");
        }
    }

    private void showErrorAlert(String message, String styleClass) {
        errorMessageLabel.setText(message);
        errorMessageLabel.getStyleClass().setAll(styleClass);
        errorMessageLabel.setVisible(true);

        fadeOut = new FadeTransition(Duration.seconds(2), errorMessageLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
