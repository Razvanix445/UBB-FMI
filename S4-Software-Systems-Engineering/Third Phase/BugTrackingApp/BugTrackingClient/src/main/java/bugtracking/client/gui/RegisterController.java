package bugtracking.client.gui;

import bugtracking.services.IBugTrackingServices;
import bugtracking.model.Programmer;
import bugtracking.model.Tester;
import bugtracking.model.User;
import bugtracking.services.IBugTrackingServices;
import bugtracking.services.validator.UserValidator;
import bugtracking.services.validator.Validator;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class RegisterController {

    @FXML
    private Text errorMessageLabel;

    @FXML
    private ImageView icon;

    @FXML
    private Pane targetPane;

    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;

    @FXML
    private CheckBox testerCheckBox;
    @FXML
    private CheckBox programmerCheckBox;

    private IBugTrackingServices server;
    private Stage stage;
    private FadeTransition fadeOut;
    private Validator<User> validator;

    private double initialX;
    private double offsetX;
    private TranslateTransition returnTransition;

    public void setServer(IBugTrackingServices server) {
        this.server = server;
        this.validator = new UserValidator();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        icon.setOnMousePressed(this::handleMousePressed);
        icon.setOnMouseDragged(this::handleMouseDragged);
        icon.setOnMouseReleased(this::handleMouseReleased);
        returnTransition = new TranslateTransition(Duration.seconds(0.5), icon);
        icon.toFront();
    }

    @FXML
    public void handleAddUser() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();

        try {
            if (testerCheckBox.isSelected() && !programmerCheckBox.isSelected()) {
                Tester tester = new Tester(username, password, email, name);
                validator.validate(tester);
                server.addTester(tester);
            }
            else if (!testerCheckBox.isSelected() && programmerCheckBox.isSelected()) {
                Programmer programmer = new Programmer(username, password, email, name);
                validator.validate(programmer);
                server.addProgrammer(programmer);
            }
            else {
                showErrorAlert("Please select only one role!", RED);
                return;
            }
        } catch (Exception e) {
            showErrorAlert("Invalid credentials!", RED);
            return;
        }
        showErrorAlert("User added successfully!", GREEN);
        handleDelayedExit();
    }

    private void handleMousePressed(MouseEvent event) {
        initialX = icon.getLayoutX();
    }

    private void handleMouseDragged(MouseEvent event) {
        offsetX = event.getSceneX() - initialX;
        if (offsetX < 0) {
            TranslateTransition dragTransition = new TranslateTransition(Duration.seconds(0.01), icon);
            dragTransition.setToX(offsetX);
            dragTransition.play();
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        returnTransition.setFromX(icon.getTranslateX());
        returnTransition.setToX(0);
        returnTransition.play();

        double imageViewFinalX = icon.getLayoutX() + icon.getTranslateX();
        double imageViewFinalY = icon.getLayoutY() + icon.getTranslateY();
        double threshold = 25;

        if (imageViewFinalX >= targetPane.getLayoutX() - threshold &&
                imageViewFinalX <= targetPane.getLayoutX() + targetPane.getPrefWidth() + threshold &&
                imageViewFinalY >= targetPane.getLayoutY() - threshold &&
                imageViewFinalY <= targetPane.getLayoutY() + targetPane.getPrefHeight() + threshold) {
            handleAddUser();
        }
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
        this.stage.close();
    }
}
