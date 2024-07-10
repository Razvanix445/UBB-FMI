package games.client.gui;

import games.model.Player;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import games.services.IServices;

public class LoginController {

    @FXML
    private TextField aliasField;

    @FXML
    private Label errorMessageLabel;

    private FadeTransition fadeOut;
    private MainController mainController;
    private Player loggedUser;
    private IServices server;
    private Stage stage;
    private Parent mainParent;

    public void setServer(IServices server) {
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
        String alias = aliasField.getText().trim();
        loggedUser = new Player(alias);

        try {
            server.login(loggedUser, mainController);
            System.out.println("User succesfully logged in " + alias);
            Stage stage = new Stage();
            stage.setTitle("Window for " + loggedUser.getAlias());
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
            System.out.println("Error logging in " + e);
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
