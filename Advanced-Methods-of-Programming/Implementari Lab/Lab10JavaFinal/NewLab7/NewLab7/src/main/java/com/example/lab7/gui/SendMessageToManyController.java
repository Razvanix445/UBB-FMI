package com.example.lab7.gui;

import com.example.lab7.Session;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SendMessageToManyController {

    private Service service;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    private List<Utilizator> selectedUsers = new ArrayList<>();

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator, String> tableColumnUsername;
    @FXML
    TableColumn<Utilizator, String> tableColumnEmail;

    @FXML
    private TextField messageTextField;

    public void setService(Service service) {
        this.service = service;
        initModel();
    }

    @FXML
    public void initialize() {
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("username"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("email"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(model);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Utilizator>) c -> {
            selectedUsers.clear();
            selectedUsers.addAll(c.getList());
        });
    }

    @FXML
    private void handleSendMessageToUsers(ActionEvent event) {
        String messageText = messageTextField.getText().trim();
        if (!messageText.isEmpty() && !selectedUsers.isEmpty()) {
            service.sendMessage(Session.getLoggedUser(), selectedUsers, messageText);
            selectedUsers.clear();
            messageTextField.clear();
        }
    }

    private void initModel() {
        Iterable<Utilizator> users = service.getAllUtilizatori();
        List<Utilizator> usersList = new ArrayList<>();
        for (Utilizator user: users)
            usersList.add(user);
        model.setAll(usersList);
    }
}
