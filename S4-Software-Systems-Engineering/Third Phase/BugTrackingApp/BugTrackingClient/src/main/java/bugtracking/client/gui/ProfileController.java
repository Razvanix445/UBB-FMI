package bugtracking.client.gui;

import bugtracking.model.Programmer;
import bugtracking.model.Tester;
import bugtracking.model.User;
import bugtracking.services.IBugTrackingServices;
import bugtracking.services.validator.UserValidator;
import bugtracking.services.validator.Validator;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class ProfileController {

    @FXML
    private Text errorMessageLabel;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    private IBugTrackingServices server;
    private Stage stage;
    private FadeTransition fadeOut;
    private Validator<User> validator;
    private User loggedUser;

    public void setServer(IBugTrackingServices server) {
        this.server = server;
        this.validator = new UserValidator();
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
        initModel();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initModel() {
        nameField.setText(loggedUser.getName());
        usernameField.setText(loggedUser.getUsername());
        passwordField.setText(loggedUser.getPassword());
        emailField.setText(loggedUser.getEmail());
    }

    @FXML
    public void handleUpdateAccount() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();

        try {
            User user = new User(username, password, email, name);
            validator.validate(user);
            if (loggedUser instanceof Tester) {
                Tester tester = new Tester(username, password, email, name);
                tester.setId(loggedUser.getId());
                server.updateTester(tester);
            }
            else if (loggedUser instanceof Programmer) {
                Programmer programmer = new Programmer(username, password, email, name);
                programmer.setId(loggedUser.getId());
                server.updateProgrammer(programmer);
            }
            else {
                showErrorAlert("Please select only one role!", RED);
                return;
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage(), RED);
            return;
        }
        showErrorAlert("User updated successfully!", GREEN);
        handleDelayedExit();
    }

    private void showErrorAlert(String message, Color color) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setFill(color);
        errorMessageLabel.setVisible(true);

        fadeOut = new FadeTransition(Duration.seconds(2), errorMessageLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }

    @FXML
    private void handleDelayedExit() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            this.stage.close();
        }));
        timeline.play();
    }

    @FXML
    private void handleExit() {
        stage.close();
    }
}
