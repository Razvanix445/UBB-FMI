package com.example.faptebuneexamen.gui;

import com.example.faptebuneexamen.domain.Oras;
import com.example.faptebuneexamen.domain.Persoana;
import com.example.faptebuneexamen.service.Service;
import com.example.faptebuneexamen.validators.LoggingValidator;
import com.example.faptebuneexamen.validators.ValidationException;
import com.example.faptebuneexamen.validators.Validator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {

    private Service service;
    private Validator<Persoana> validator = new LoggingValidator();
    private Stage stage;

    @FXML
    private ListView<String> registerUsernameListView;

    @FXML
    private TextField loginUsernameField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField registerNumeField;

    @FXML
    private TextField registerPrenumeField;

    @FXML
    private TextField registerUsernameField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private ComboBox<Oras> registerCityComboBox;

    @FXML
    private TextField registerStreetField;

    @FXML
    private TextField registerStreetNumberField;

    @FXML
    private TextField registerPhoneNumberField;

    @FXML
    private Button registerButton;

    @FXML
    private Text status;

    public void setService(Service service) {
        this.service = service;
        this.validator = new LoggingValidator();
        registerCityComboBox.setItems(FXCollections.observableArrayList(Oras.values()));
        loadExistingUsernames();
        registerUsernameListView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    onUsernameSelected();
                }
        });
    }

    private void onUsernameSelected() {
        String selectedUsername = registerUsernameListView.getSelectionModel().getSelectedItem();
        if (selectedUsername != null) {
            Persoana persoana = service.cautaPersoanaDupaUsername(selectedUsername);
            openMainWindow(persoana);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void onLoginButtonClick() {
        String username = registerUsernameListView.getSelectionModel().getSelectedItem();
        Persoana persoana = service.cautaPersoanaDupaUsername(username);
        openMainWindow(persoana);
    }

    public void openMainWindow(Persoana persoana) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/faptebuneexamen/main-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Hello!");
            stage.setWidth(800);
            stage.show();
            MainController mainController = loader.getController();
            mainController.setPersoana(persoana);
            mainController.setService(service);
            mainController.setStage(stage);
            //this.stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onRegisterButtonClick() {
        String nume = registerNumeField.getText();
        String prenume = registerPrenumeField.getText();
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        Oras oras = registerCityComboBox.getValue();
        String strada = registerStreetField.getText();
        String numarStrada = registerStreetNumberField.getText();
        String telefon = registerPhoneNumberField.getText();
        Persoana persoana = new Persoana(nume, prenume, username, password, oras, strada, numarStrada, telefon);
        if (!isValidRegistration(persoana)) {
            status.setText("Try again!");
        }
        else {
            service.adaugaPersoana(persoana);
            status.setText("Welcome!");
        }
        openMainWindow(persoana);
    }

    private boolean isValidRegistration(Persoana persoana) {
        try {
            validator.validate(persoana);
        } catch (ValidationException e) {
            return false;
        }
        return true;
    }

    private void loadExistingUsernames() {
        List<String> existingUsernames = service.getExistingUsernames();
        registerUsernameListView.setItems(FXCollections.observableArrayList(existingUsernames));
    }
}