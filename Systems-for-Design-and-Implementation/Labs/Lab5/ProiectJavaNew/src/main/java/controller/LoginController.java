package controller;

import domain.ReservationManager;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.Service;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;

    private FadeTransition fadeOut;
    private Service service;
    private Stage stage;

    public void setService(Service service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        ReservationManager loggedUser = service.searchReservationManagerByName(username);

        if (loggedUser != null && password.equals(loggedUser.getPassword())) {
            openMainCrudWindow(loggedUser);
            showErrorAlert("E ok.", "success-label");
        } else {
            showErrorAlert("Credentiale invalide. Incearca din nou.", "error-label");
        }
    }

    private void openMainCrudWindow(ReservationManager loggedUser) {
        try {
            FXMLLoader userLoader = new FXMLLoader();
            userLoader.setLocation(getClass().getResource("/views/main-view.fxml"));
            AnchorPane userLayout = userLoader.load();

            Scene scene = new Scene(userLayout);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.show();

            MainController mainController = userLoader.getController();
            mainController.setLoggedUser(loggedUser);
            mainController.setService(service);
            mainController.setStage(stage);

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
