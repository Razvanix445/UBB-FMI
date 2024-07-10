package bugtracking.client.gui;

import bugtracking.model.Bug;
import bugtracking.model.BugStatus;
import bugtracking.model.Programmer;
import bugtracking.model.User;
import bugtracking.services.BugTrackingException;
import bugtracking.services.IBugTrackingObserver;
import bugtracking.services.IBugTrackingServices;
import bugtracking.services.validator.ValidationException;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProgrammerController implements Initializable, IBugTrackingObserver {

    private IBugTrackingServices server;
    private Stage stage;
    ObservableList<Bug> bugModel = FXCollections.observableArrayList();
    ObservableList<Bug> assignedBugModel = FXCollections.observableArrayList();
    private FadeTransition fadeOut;
    private Programmer loggedUser;

    @FXML
    private Text errorMessageLabel;

    @FXML
    private ComboBox<BugStatus> bugStatusField;

    @FXML
    TableView<Bug> assignedBugTableView;
    @FXML
    TableColumn<Bug, String> assignedBugTableColumnName;
    @FXML
    TableColumn<Bug, String> assignedBugTableColumnDescription;
    @FXML
    TableColumn<Bug, BugStatus> assignedBugTableColumnStatus;
    @FXML
    TableColumn<Bug, LocalDateTime> assignedBugTableColumnTimestamp;

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

    public void setLoggedUser(Programmer loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        assignedBugTableColumnName.setCellValueFactory(new PropertyValueFactory<Bug, String>("name"));
        assignedBugTableColumnDescription.setCellValueFactory(new PropertyValueFactory<Bug, String>("description"));
        assignedBugTableColumnStatus.setCellValueFactory(new PropertyValueFactory<Bug, BugStatus>("status"));
        assignedBugTableColumnTimestamp.setCellValueFactory(new PropertyValueFactory<Bug, LocalDateTime>("timestamp"));

        assignedBugTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        assignedBugTableView.setItems(assignedBugModel);

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

        List<Bug> bugList = StreamSupport.stream(bugs.spliterator(), false)
                .filter(bug -> bug.getAssignedTo() != null && bug.getAssignedTo().equals(loggedUser))
                .collect(Collectors.toList());
        assignedBugModel.setAll(bugList);

        ObservableList<BugStatus> bugStatuses = FXCollections.observableArrayList(BugStatus.values());
        bugStatusField.setItems(bugStatuses);
    }

    @FXML
    private void handleOpenProfile() {
        try {
            FXMLLoader profileLoader = new FXMLLoader();
            profileLoader.setLocation(getClass().getResource("/views/profile_view.fxml"));
            AnchorPane profileLayout = profileLoader.load();

            ProfileController profileController = profileLoader.getController();
            profileController.setServer(server);
            System.out.println("Logged user: " + loggedUser);
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
    private void handleEliminateBug() {
        Bug mainSelectedBug = bugTableView.getSelectionModel().getSelectedItem();
        Bug assignedSelectedBug = assignedBugTableView.getSelectionModel().getSelectedItem();
        Bug bug = new Bug();
        if (mainSelectedBug == null && assignedSelectedBug == null) {
            showErrorAlert("Please select a bug!");
            return;
        }
        else if (mainSelectedBug == null)
            bug = assignedSelectedBug;
        else if (assignedSelectedBug == null)
            bug = mainSelectedBug;
        try {
            Bug bugForDeletion = server.searchBugByNameAndDescription(bug.getName(), bug.getDescription());
            server.deleteBug(bugForDeletion);
            initModel();
        } catch (BugTrackingException e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    private void handleUpdateBug() {
        Bug mainSelectedBug = bugTableView.getSelectionModel().getSelectedItem();
        Bug assignedSelectedBug = assignedBugTableView.getSelectionModel().getSelectedItem();
        Bug bug = new Bug();
        if (mainSelectedBug == null && assignedSelectedBug == null) {
            showErrorAlert("Please select a bug!");
            return;
        }
        else if (mainSelectedBug == null)
            bug = assignedSelectedBug;
        else if (assignedSelectedBug == null)
            bug = mainSelectedBug;
        try {
            BugStatus status = bugStatusField.getValue();
            Bug bugForUpdate = server.searchBugByNameAndDescription(bug.getName(), bug.getDescription());
            bugForUpdate.setStatus(status);
            server.updateBug(bugForUpdate);
            initModel();
        } catch (BugTrackingException | ValidationException e) {
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
