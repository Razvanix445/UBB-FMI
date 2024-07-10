package com.example.lab7.gui;

import com.example.lab7.Session;
import com.example.lab7.controller.MessageAlert;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.service.Service;
import com.example.lab7.validators.ValidationException;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserProfileController {

    private Service service;
    private Utilizator loggedUser;
    private FadeTransition fadeOut;

    @FXML
    private TextField prenumeField;
    @FXML
    private TextField numeField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;

    @FXML
    private Label errorMessageLabel;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    public void init(Utilizator utilizator) {
        this.loggedUser = Session.getLoggedUser();
        prenumeField.setText(utilizator.getPrenume());
        numeField.setText(utilizator.getNume());
        usernameField.setText(utilizator.getUsername());
        passwordField.setText(utilizator.getPassword());
        emailField.setText(utilizator.getEmail());
    }

    @FXML
    public void handleDeleteUser(ActionEvent actionEvent) {
        try {
            Utilizator deleted = service.stergeUtilizator(loggedUser);
            if (deleted != null) {
                showErrorAlert("Utilizatorul a fost sters cu succes!", "success-label");
                handleDelayedExit();
                Stage currentStage = (Stage) emailField.getScene().getWindow();
                FXMLLoader loginLoader = new FXMLLoader();
                loginLoader.setLocation(getClass().getResource("/com/example/lab7/gui/views/login-view.fxml"));
                AnchorPane loginLayout = loginLoader.load();
                currentStage.setScene(new Scene(loginLayout));

                LoginController loginController = loginLoader.getController();
                loginController.setService(service);
                loginController.setStage(currentStage);
            }
            else showErrorAlert("Nu a fost selectat niciun utilizator!", "error-label");
        } catch (Exception e) {
            showErrorAlert("Error deleting account!", "error-label");
        }
    }

    @FXML
    public void handleUpdateUser(ActionEvent actionEvent) {
        String prenume = prenumeField.getText().trim();
        String nume = numeField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();

        Utilizator updatedUser;
        Utilizator modifiedUser = null;
        try {
            updatedUser = new Utilizator(prenume, nume, username, password, email);
            updatedUser.setId(loggedUser.getId());
            modifiedUser = service.modificaUtilizator(updatedUser);
        } catch (ValidationException e) {
            showErrorAlert("Date incorecte!", "error-label");
        }

        if (modifiedUser != null) {
            loggedUser = modifiedUser;
            Session.setLoggedUser(loggedUser);
            init(loggedUser);
            showErrorAlert("Datele au fost actualizate cu succes!", "success-label");
        } else {
            showErrorAlert("Eroare la actualizarea datelor!", "error-label");
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
    private void handleDelayedExit() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Platform.exit();
        }));
        timeline.play();
    }

    @FXML
    private void handleExit() {
        Stage currentStage = (Stage) prenumeField.getScene().getWindow();
        currentStage.close();
    }
}
