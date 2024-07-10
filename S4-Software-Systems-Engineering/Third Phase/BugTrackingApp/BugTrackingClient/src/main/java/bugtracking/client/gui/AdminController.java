package bugtracking.client.gui;

import bugtracking.model.*;
import bugtracking.services.BugTrackingException;
import bugtracking.services.IBugTrackingServices;
import bugtracking.services.validator.UserValidator;
import bugtracking.services.validator.Validator;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController {

    @FXML
    private Text errorMessageLabel;

    @FXML
    private TextField userUsernameField;
    @FXML
    private TextField userPasswordField;
    @FXML
    private TextField userEmailField;
    @FXML
    private TextField userNameField;
    @FXML
    private CheckBox testerCheckBox;
    @FXML
    private CheckBox programmerCheckBox;

    ObservableList<UserWithTypeDTO> userModel = FXCollections.observableArrayList();
    @FXML
    TableView<UserWithTypeDTO> userTableView;
    @FXML
    TableColumn<UserWithTypeDTO, String> userTableColumnUsername;
    @FXML
    TableColumn<UserWithTypeDTO, String> userTableColumnName;
    @FXML
    TableColumn<UserWithTypeDTO, String> userTableColumnEmail;
    @FXML
    TableColumn<UserWithTypeDTO, String> userTableColumnPassword;
    @FXML
    TableColumn<UserWithTypeDTO, String> userTableColumnUserType;

    private IBugTrackingServices server;
    private FadeTransition fadeOut;
    private Stage stage;
    private Validator validator = new UserValidator();

    public void setServer(IBugTrackingServices server) {
        this.server = server;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        initModel();
    }

    @FXML
    public void initialize() {
        userTableColumnUsername.setCellValueFactory(new PropertyValueFactory<UserWithTypeDTO, String>("username"));
        userTableColumnName.setCellValueFactory(new PropertyValueFactory<UserWithTypeDTO, String>("name"));
        userTableColumnEmail.setCellValueFactory(new PropertyValueFactory<UserWithTypeDTO, String>("email"));
        userTableColumnPassword.setCellValueFactory(new PropertyValueFactory<UserWithTypeDTO, String>("password"));
        userTableColumnUserType.setCellValueFactory(new PropertyValueFactory<UserWithTypeDTO, String>("type"));

        userTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userTableView.setItems(userModel);

        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                UserWithTypeDTO selectedUser = userTableView.getSelectionModel().getSelectedItem();
                userUsernameField.setText(selectedUser.getUsername());
                userPasswordField.setText(selectedUser.getPassword());
                userEmailField.setText(selectedUser.getEmail());
                userNameField.setText(selectedUser.getName());
                testerCheckBox.setSelected("Tester".equals(selectedUser.getType()));
                programmerCheckBox.setSelected("Programmer".equals(selectedUser.getType()));
            }
        });
    }

    private void initModel() {
        List<UserWithTypeDTO> users = null;
        try {
            users = server.getAllUsers();
        } catch (BugTrackingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(users);
        ObservableList<UserWithTypeDTO> observableDataUsers = FXCollections.observableList(users);
        userTableView.setItems(observableDataUsers);
    }

    @FXML
    private void handleAddUser() {
        String username = userUsernameField.getText();
        String password = userPasswordField.getText();
        String email = userEmailField.getText();
        String name = userNameField.getText();
        try {
            if (testerCheckBox.isSelected() && !programmerCheckBox.isSelected()) {
                Tester tester = new Tester(username, password, email, name);
                validator.validate(tester);
                server.addTester(tester);
                initModel();
            }
            if (programmerCheckBox.isSelected() && !testerCheckBox.isSelected()) {
                Programmer programmer = new Programmer(username, password, email, name);
                validator.validate(programmer);
                server.addProgrammer(programmer);
                initModel();
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    private void handleDeleteUser() {
        UserWithTypeDTO selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showErrorAlert("No user selected!");
            return;
        }
        try {
            if (userTableColumnUserType.getCellData(selectedUser).equals("Tester")) {
                Tester tester = server.searchTesterByUsername(selectedUser.getUsername());
                server.deleteTester(tester);
            }
            else if (userTableColumnUserType.getCellData(selectedUser).equals("Programmer")) {
                Programmer programmer = server.searchProgrammerByUsername(selectedUser.getUsername());
                server.deleteProgrammer(programmer);
            }
            initModel();
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    private void handleUpdateUser() {
        UserWithTypeDTO selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showErrorAlert("No user selected!");
            return;
        }
        try {
            String username = userUsernameField.getText();
            String password = userPasswordField.getText();
            String email = userEmailField.getText();
            String name = userNameField.getText();
            if (userTableColumnUserType.getCellData(selectedUser).equals("Tester")) {
                Tester tester = server.searchTesterByUsername(selectedUser.getUsername());
                tester.setUsername(username);
                tester.setPassword(password);
                tester.setEmail(email);
                tester.setName(name);
                validator.validate(tester);
                server.updateTester(tester);
            }
            else if (userTableColumnUserType.getCellData(selectedUser).equals("Programmer")) {
                Programmer programmer = server.searchProgrammerByUsername(selectedUser.getUsername());
                programmer.setUsername(username);
                programmer.setPassword(password);
                programmer.setEmail(email);
                programmer.setName(name);
                validator.validate(programmer);
                server.updateProgrammer(programmer);
            }
            initModel();
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
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
        this.stage.close();
    }
}
