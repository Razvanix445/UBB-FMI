package com.example.gui;

import com.example.domain.Bug;
import com.example.domain.BugStatus;
import com.example.domain.Programmer;
import com.example.domain.User;
import com.example.service.Service;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProgrammerController {

    private Service service;
    ObservableList<Bug> bugModel = FXCollections.observableArrayList();
    ObservableList<Bug> assignedBugModel = FXCollections.observableArrayList();
    private User loggedUser;

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

    public void setService(Service service) {
        this.service = service;
        initModel();
    }

    public void setLoggedUser(Programmer loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    public void initialize() {
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
        Iterable<Bug> bugs = service.getAllBugs();

        ObservableList<Bug> observableDataBugs = FXCollections.observableList(StreamSupport.stream(bugs.spliterator(), false)
                .collect(Collectors.toList()));
        bugModel.setAll(observableDataBugs);

        List<Bug> bugList = StreamSupport.stream(bugs.spliterator(), false)
                .filter(bug -> bug.getAssignedTo().equals(loggedUser))
                .collect(Collectors.toList());
        assignedBugModel.setAll(bugList);
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
