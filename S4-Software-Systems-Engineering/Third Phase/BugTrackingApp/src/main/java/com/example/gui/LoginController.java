package com.example.gui;

import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.domain.User;
import com.example.service.Service;
import com.example.validator.UserValidator;
import com.example.validator.Validator;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private ImageView icon;

    @FXML
    private Pane targetPane;

    @FXML
    private Text errorMessageLabel;

    @FXML
    private CheckBox testerCheckBox;
    @FXML
    private CheckBox programmerCheckBox;

    private Service service;
    private Stage stage;
    private FadeTransition fadeOut;
    private Validator<User> validator;

    private double initialX;
    private double offsetX;
    private TranslateTransition returnTransition;

    public void setService(Service service) {
        this.service = service;
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
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        User loggedUser = null;
        Tester tester = service.searchTesterByUsername(username);
        Programmer programmer = service.searchProgrammerByUsername(username);

        if (username.equals("admin") && password.equals("admin")) {
            try {
                FXMLLoader adminLoader = new FXMLLoader();
                adminLoader.setLocation(getClass().getResource("/com/example/gui/views/admin_view.fxml"));
                AnchorPane adminLayout = adminLoader.load();

                AdminController adminController = adminLoader.getController();
                adminController.setService(service);

                Stage adminStage = new Stage();
                adminStage.setTitle("Admin's Window");
                adminStage.setScene(new Scene(adminLayout));
                adminStage.setResizable(true);
                adminStage.setMaximized(true);
                adminController.setStage(adminStage);
//                    ((Node)(event.getSource())).getScene().getWindow().hide();
                adminStage.show();

            } catch (IOException e) {
                e.printStackTrace();
//                showErrorAlert("Invalid admin ^_^!");
            }
        }
        else if (tester == null && programmer == null) {
            showErrorAlert("Invalid credentials!");
            return;
        }
        else if (programmer != null && !testerCheckBox.isSelected()) {
            if (programmer.getPassword().equals(password) && programmerCheckBox.isSelected()) {
                loggedUser = programmer;
                try {
                    FXMLLoader programmerLoader = new FXMLLoader();
                    programmerLoader.setLocation(getClass().getResource("/com/example/gui/views/programmer_view.fxml"));
                    AnchorPane programmerLayout = programmerLoader.load();

                    ProgrammerController programmerController = programmerLoader.getController();
                    programmerController.setLoggedUser((Programmer)loggedUser);
                    programmerController.setService(service);

                    Stage programmerStage = new Stage();
                    programmerStage.setTitle(programmer.getName() + "'s Window");
                    programmerStage.setScene(new Scene(programmerLayout));
                    programmerStage.setResizable(true);
                    programmerStage.setMaximized(true);
                    programmerController.setStage(programmerStage);
//                    ((Node)(event.getSource())).getScene().getWindow().hide();
                    programmerStage.show();

                } catch (IOException e) {
                    showErrorAlert("Invalid programmer!");
                }
            }
        }
        else if (tester != null && !programmerCheckBox.isSelected()) {
            if (tester.getPassword().equals(password) && testerCheckBox.isSelected()) {
                loggedUser = tester;
                try {
                    FXMLLoader testerLoader = new FXMLLoader();
                    testerLoader.setLocation(getClass().getResource("/com/example/gui/views/tester_view.fxml"));
                    AnchorPane testerLayout = testerLoader.load();

                    TesterController testerController = testerLoader.getController();
                    testerController.setLoggedUser((Tester)loggedUser);
                    testerController.setService(service);

                    Stage testerStage = new Stage();
                    testerStage.setTitle(tester.getName() + "'s Window");
                    testerStage.setScene(new Scene(testerLayout));
                    testerStage.setResizable(true);
                    testerStage.setMaximized(true);
                    testerController.setStage(testerStage);
//                    ((Node)(event.getSource())).getScene().getWindow().hide();
                    testerStage.show();

                } catch (IOException e) {
                    showErrorAlert("Invalid tester!");
                }
            }
        }
        else {
            showErrorAlert("Invalid credentials!");
            return;
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader registerLoader = new FXMLLoader();
            registerLoader.setLocation(getClass().getResource("/com/example/gui/views/register_view.fxml"));
            AnchorPane registerLayout = registerLoader.load();

            RegisterController registerController = registerLoader.getController();
            registerController.setService(service);

            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setScene(new Scene(registerLayout));
            registerController.setStage(registerStage);

            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMousePressed(MouseEvent event) {
        initialX = icon.getLayoutX();
    }

    private void handleMouseDragged(MouseEvent event) {
        offsetX = event.getSceneX() - initialX;
        if (offsetX > 0) {
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
            handleLogin(null);
        }
    }

    private void showErrorAlert(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setFill(Color.RED);
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
