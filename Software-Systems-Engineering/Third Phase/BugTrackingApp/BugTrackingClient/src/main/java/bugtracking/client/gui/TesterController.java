package bugtracking.client.gui;

import bugtracking.model.*;
import bugtracking.services.BugTrackingException;
import bugtracking.services.IBugTrackingObserver;
import bugtracking.services.IBugTrackingServices;
import bugtracking.services.validator.BugValidator;
import bugtracking.services.validator.Validator;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TesterController implements Initializable, IBugTrackingObserver {

    private IBugTrackingServices server;
    private Stage stage;
    private FadeTransition fadeOut;
    ObservableList<Bug> bugModel = FXCollections.observableArrayList();
    private Tester loggedUser;
    private Validator<Bug> validator = new BugValidator();

    @FXML
    private Text errorMessageLabel;

    @FXML
    private TextField bugNameField;
    @FXML
    private TextField bugDescriptionField;
    @FXML
    private TextField bugDeadlineField;
    @FXML
    private TextField bugStatusField;
    @FXML
    private ComboBox<String> bugProgrammerComboBox;

    @FXML
    TableView<Bug> bugTableView;
    @FXML
    TableColumn<Bug, String> bugTableColumnName;
    @FXML
    TableColumn<Bug, String> bugTableColumnDescription;
    @FXML
    TableColumn<Bug, BugStatus> bugTableColumnStatus;
    @FXML
    TableColumn<Bug, LocalDateTime> bugTableColumnTimestamp;

    public void setServer(IBugTrackingServices server) throws BugTrackingException {
        this.server = server;
        initModel();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoggedUser(Tester loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        bugTableColumnName.setCellValueFactory(new PropertyValueFactory<Bug, String>("name"));
        bugTableColumnDescription.setCellValueFactory(new PropertyValueFactory<Bug, String>("description"));
        bugTableColumnStatus.setCellValueFactory(new PropertyValueFactory<Bug, BugStatus>("status"));
        bugTableColumnTimestamp.setCellValueFactory(new PropertyValueFactory<Bug, LocalDateTime>("timestamp"));

        bugTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        bugTableView.setItems(bugModel);
    }

    private void initModel() {
        Iterable<Bug> bugs = null;
        try {
            bugs = server.getAllBugs();
        } catch (BugTrackingException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Bug> observableDataBugs = FXCollections.observableList(StreamSupport.stream(bugs.spliterator(), false)
                .collect(Collectors.toList()));
        bugModel.setAll(observableDataBugs);

        try {
            List<Programmer> programmers = server.getAllProgrammers();
            ObservableList<String> observableDataProgrammers = FXCollections.observableList(StreamSupport.stream(programmers.spliterator(), false)
                    .map(User::getUsername)
                    .filter(username -> !username.equals("admin"))
                    .collect(Collectors.toList()));
            bugProgrammerComboBox.setItems(observableDataProgrammers);
        } catch (BugTrackingException e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    private void handleOpenProfile() {
        try {
            FXMLLoader profileLoader = new FXMLLoader();
            profileLoader.setLocation(getClass().getResource("/views/profile_view.fxml"));
            AnchorPane profileLayout = profileLoader.load();

            ProfileController profileController = profileLoader.getController();
            profileController.setServer(server);
            profileController.setLoggedUser(loggedUser);

            Stage profileStage = new Stage();
            profileStage.setTitle(loggedUser.getName() + "'s Window");
            profileStage.setScene(new Scene(profileLayout));
            profileStage.setResizable(true);
            profileController.setStage(profileStage);
            profileStage.show();
        } catch (IOException e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    private void handleReport(ActionEvent event) throws BugTrackingException {
        String name = bugNameField.getText();
        String description = bugDescriptionField.getText();
        String programmerName = bugProgrammerComboBox.getValue();
        Programmer programmer = server.searchProgrammerByUsername(programmerName);

        try {
            Bug bug = new Bug(name, description, loggedUser, programmer);
            validator.validate(bug);
            server.addBug(bug);
        } catch (BugTrackingException e) {
            showErrorAlert(e.getMessage());
        }
    }

    public void bugAdded(Bug bug) throws BugTrackingException {
        Platform.runLater(() -> {
            System.out.println("Bug added: " + bug);
            initModel();
        });
    }

    public void bugRemoved(Bug bug) throws BugTrackingException {
        Platform.runLater(() -> {
            System.out.println("Bug removed: " + bug);
            initModel();
        });
    }

    public void bugUpdated(Bug bug) throws BugTrackingException {
        Platform.runLater(() -> {
            System.out.println("Bug updated: " + bug);
            initModel();
        });
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
