package com.example.lab7.gui;

import com.example.lab7.domain.Utilizator;
import com.example.lab7.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class FriendshipsController {

    @FXML
    private TableView<Utilizator> pendingTable;
    @FXML
    private TableColumn<Utilizator, String> pendingUsernameColumn;
    @FXML
    private TableColumn<Utilizator, String> pendingEmailColumn;

    @FXML
    private TableView<Utilizator> approvedTable;
    @FXML
    private TableColumn<Utilizator, String> approvedUsernameColumn;
    @FXML
    private TableColumn<Utilizator, String> approvedEmailColumn;

    @FXML
    private TableView<Utilizator> declinedTable;
    @FXML
    private TableColumn<Utilizator, String> declinedUsernameColumn;
    @FXML
    private TableColumn<Utilizator, String> declinedEmailColumn;

    private Service service;

    public void setService(Service service) {
        this.service = service;
        initializeTables();
    }

    private void initializeTables() {
        pendingUsernameColumn.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("username"));
        pendingEmailColumn.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("email"));

        approvedUsernameColumn.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("username"));
        approvedEmailColumn.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("email"));

        declinedUsernameColumn.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("username"));
        declinedEmailColumn.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("email"));

        loadTableData(pendingTable, service.getAllUtilizatoriByStatus("pending"));
        loadTableData(approvedTable, service.getAllUtilizatoriByStatus("approved"));
        loadTableData(declinedTable, service.getAllUtilizatoriByStatus("declined"));
    }

    private void loadTableData(TableView<Utilizator> table, List<Utilizator> data) {
        ObservableList<Utilizator> observableData = FXCollections.observableList(data);
        table.setItems(observableData);
    }

    @FXML
    private void handleAcceptRequest() {
        handleFriendRequestAction(pendingTable, "Accept");
    }

    @FXML
    private void handleDeclineRequest() {
        handleFriendRequestAction(pendingTable, "Decline");
    }

    @FXML
    private void handleDeleteRequest() {
        handleFriendRequestAction(declinedTable, "Delete");
    }

    private void handleFriendRequestAction(TableView<Utilizator> table, String action) {
        Utilizator selectedUser = table.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            if ("Accept".equals(action))
                service.acceptFriendRequest(selectedUser);
            else if ("Decline".equals(action))
                service.declineFriendRequest(selectedUser);
            else if ("Delete".equals(action))
                service.deleteFriendRequest(selectedUser);
            loadTableData(pendingTable, service.getAllUtilizatoriByStatus("pending"));
            loadTableData(approvedTable, service.getAllUtilizatoriByStatus("approved"));
            loadTableData(declinedTable, service.getAllUtilizatoriByStatus("declined"));
        } else {
            showAlert("Select an user", "Please select an user from the table!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        Stage currentStage = (Stage) approvedTable.getScene().getWindow();
        currentStage.close();
    }
}