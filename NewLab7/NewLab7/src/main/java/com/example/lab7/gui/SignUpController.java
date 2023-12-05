package com.example.lab7.gui;

import com.example.lab7.controller.MessageAlert;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.service.Service;
import com.example.lab7.validators.UtilizatorValidator;
import com.example.lab7.validators.ValidationException;
import com.example.lab7.validators.Validator;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUpController {

    private Service service;
    private Validator<Utilizator> validator = new UtilizatorValidator();
    private Stage stage;
    private FadeTransition fadeOut;

    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private Label errorMessageLabel;

    public void setService(Service service) {
        this.service = service;
        this.validator = new UtilizatorValidator();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleAddUser(ActionEvent actionEvent) {
        String prenume = firstNameField.getText().trim();
        String nume = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();

        if (!isValidCredentials(prenume, nume, username, password, email)) {
            showErrorAlert("Credentiale invalide sau insuficiente. Incearca din nou.", "error-label");
        } else {
            Utilizator utilizatorNou = new Utilizator(prenume, nume, username, password, email);
            Utilizator utilizatorAdaugat = service.adaugaUtilizator(utilizatorNou);

            if (utilizatorAdaugat != null) {
                showErrorAlert("Utilizatorul a fost creat cu succes!", "success-label");
                handleDelayedExit();
            } else {
                showErrorAlert("Utilizatorul nu a putut fi salvat!", "error-label");
            }
        }
    }

    private boolean isValidCredentials(String firstName, String lastName, String username, String password, String email) {
        Utilizator utilizator;
        try {
            utilizator = new Utilizator(firstName, lastName, username, password, email);
            validator.validate(utilizator);
        } catch (ValidationException e) {
            showErrorAlert("Credentiale invalide. Incearca din nou.", "error-label");
            return false;
        }
        return true;
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
        Stage currentStage = (Stage) firstNameField.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void handleDelayedExit() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Stage currentStage = (Stage) firstNameField.getScene().getWindow();
            currentStage.close();
        }));
        timeline.play();
    }
}
