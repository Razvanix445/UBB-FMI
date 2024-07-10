package com.example.gui;

import com.example.domain.*;
import com.example.service.Service;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TesterController {

    private Service service;
    private Stage stage;
    ObservableList<Bug> bugModel = FXCollections.observableArrayList();
    private User loggedUser;

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoggedUser(Tester loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    public void initialize() {
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
    }

    @FXML
    private void handleExit() {
        this.stage.close();
    }
}
