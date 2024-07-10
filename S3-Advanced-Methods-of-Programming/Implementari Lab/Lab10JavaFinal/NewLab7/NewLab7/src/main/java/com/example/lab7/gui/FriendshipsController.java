package com.example.lab7.gui;

import com.example.lab7.domain.FriendRequest;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.repository.paging.Page;
import com.example.lab7.repository.paging.Pageable;
import com.example.lab7.repository.paging.PageableImplementation;
import com.example.lab7.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendshipsController {

    ObservableList<Utilizator> modelPendings = FXCollections.observableArrayList();

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

    private Page<FriendRequest> currentPageRequests;

    @FXML
    private TextField currentPageNumberField;
    @FXML
    private TextField numberOfUsersField;
    @FXML
    private Text maximumNumberOfPagesText;


    private Service service;

    public void setService(Service service) {
        this.service = service;
        initializeTables();
        initModel();
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

    private void initModel() {
        this.currentPageRequests = service.getCurrentPageFriendRequests();
        List<Utilizator> content = service.getAllUsersFromRequests(currentPageRequests.getContent().collect(Collectors.toList()));
        int numberOfUsers = service.getAllUtilizatoriByStatus("pending").size();
        int division = numberOfUsersField.getText().isEmpty() ? 1 :
                (numberOfUsers % Integer.parseInt(numberOfUsersField.getText()) == 0) ?
                        numberOfUsers / Integer.parseInt(numberOfUsersField.getText()) :
                        numberOfUsers / Integer.parseInt(numberOfUsersField.getText()) + 1;
        maximumNumberOfPagesText.setText(String.valueOf(division));
        currentPageNumberField.setText(String.valueOf(currentPageRequests.getPageable().getPageNumber()));
        modelPendings.setAll(content);
        pendingTable.setItems(modelPendings);
    }

    private void loadTableData(TableView<Utilizator> table, List<Utilizator> data) {
        ObservableList<Utilizator> observableData = FXCollections.observableList(data);
        table.setItems(observableData);
    }

    @FXML
    private void handlePreviousPage(ActionEvent event) {
        System.out.println("Handling Previous Page...");
        this.currentPageRequests = service.getPreviousPageFriendRequests();
        List<Utilizator> users = service.getAllUsersFromRequests(currentPageRequests.getContent().collect(Collectors.toList()));
        System.out.println("Loaded users: " + users.size());
        currentPageNumberField.setText(String.valueOf(currentPageRequests.getPageable().getPageNumber()));
        loadTableData(pendingTable, users);
    }

    @FXML
    private void handleNextPage(ActionEvent event) {
        System.out.println("Handling Next Page...");
        this.currentPageRequests = service.getNextPageFriendRequests();
        List<Utilizator> users = service.getAllUsersFromRequests(currentPageRequests.getContent().collect(Collectors.toList()));
        System.out.println("Loaded users: " + users.size());
        currentPageNumberField.setText(String.valueOf(currentPageRequests.getPageable().getPageNumber()));
        loadTableData(pendingTable, users);
    }

    @FXML
    private void handlePageNumberChange(ActionEvent event) {
        try {
            int pageNumber = Integer.parseInt(currentPageNumberField.getText());
            Pageable pageable = new PageableImplementation(pageNumber, Integer.parseInt(numberOfUsersField.getText()));
            this.currentPageRequests = service.getAllPendingRequestsPaginat(pageable);
            initModel();
        } catch (NumberFormatException e) {
            showAlert("Invalid page number!", "Invalid page number!");
        }
    }

    @FXML
    private void handleNumberOfUsersChange(ActionEvent event) {
        try {
            int numberOfUsers = Integer.parseInt(numberOfUsersField.getText());
            Pageable pageable = new PageableImplementation(1, numberOfUsers);
            this.currentPageRequests = service.getAllPendingRequestsPaginat(pageable);
            initModel();
        } catch (NumberFormatException e) {
            showAlert("Invalid number of users!", "Invalid number of users!");
        }
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