package transport.client.gui;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import transport.model.ReservationManager;
import transport.services.ITransportServices;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;

    private FadeTransition fadeOut;
    private MainController mainController;
    private ReservationManager loggedUser;
    private ITransportServices server;
    //private Service service;
    private Stage stage;
    private Parent mainParent;

    public void setServer(ITransportServices server) {
        this.server = server;
    }
    public void setParent(Parent parent){
        mainParent = parent;
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        loggedUser = new ReservationManager(username, password);

        try {
            server.login(loggedUser, mainController);
            System.out.println("User succesfully logged in " + username);
            Stage stage = new Stage();
            stage.setTitle("Transport Window for " + loggedUser.getName());
            stage.setScene(new Scene(mainParent));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    mainController.logout();
                    System.exit(0);
                }
            });

            stage.show();
            mainController.setLoggedUser(loggedUser);
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            showErrorAlert(e.getMessage(), "error-label");
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
            mainController.setServer(server);
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
