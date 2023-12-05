package com.example.lab7.gui;

import com.example.lab7.Session;
import com.example.lab7.controller.MessageAlert;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.exceptions.UtilizatorInexistentException;
import com.example.lab7.service.Service;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorController {
    Service service;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    private FadeTransition fadeOut;
    private Utilizator loggedUser;

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,String> tableColumnNume;
    @FXML
    TableColumn<Utilizator,String> tableColumnPrenume;
    @FXML
    TableColumn<Utilizator,String> tableColumnUsername;
    @FXML
    TableColumn<Utilizator,String> tableColumnEmail;
    @FXML
    private TextField dataForSearch;
    @FXML
    private Label errorMessageLabel;


    public void setService(Service service) {
        this.service = service;
        initModel();
    }

    @FXML
    public void initialize() {
        this.loggedUser = Session.getLoggedUser();

        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("nume"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("prenume"));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("username"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("email"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<Utilizator> users = service.getAllUtilizatori();
        List<Utilizator> list = StreamSupport.stream(users.spliterator(), false)
                .filter(user -> !user.equals(loggedUser))
                .collect(Collectors.toList());
        model.setAll(list);
    }

    private void initFriendsModel() {
        List<Utilizator> friends = service.getPrieteniUtilizator(loggedUser.getId());
        List<Utilizator> list = new ArrayList<>(friends);
        model.setAll(list);
    }

    private void initCommunityModel() {
        List<Utilizator> community = service.getCommunityOfLoggedUser();
        model.setAll(community);
    }

    @FXML
    public void handleDeleteUser(ActionEvent actionEvent) {
        try {
            Utilizator selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Utilizator deleted = service.stergeUtilizator(selected);
                if (deleted != null) {
                    showErrorAlert("Utilizatorul a fost sters cu succes!", "success-label");
                    initModel();
                }
            } else {
                showErrorAlert("Nu a fost selectat niciun utilizator!", "error-label");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFindUser(ActionEvent actionEvent) {
        String data = dataForSearch.getText().trim();
        if (data.isEmpty()) {
            showErrorAlert("Introduceti text in casuta de cautare!", "error-label");
            return;
        }
        clearAddFields();
        List<Utilizator> searchResults = service.cautaUtilizatoriDupaCeva(data);
        if (searchResults.isEmpty()) {
            showErrorAlert("Nu s-au gasit utilizatori!", "error-label");
        } else {
            model.setAll(searchResults);
        }
    }

    @FXML
    public void handleAddFriend(ActionEvent actionEvent) {
        try {
            Utilizator selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                service.adaugaPrietenie(selected.getId(), loggedUser.getId());
                showErrorAlert("Prietenul a fost adaugat cu succes!", "success-label");
                initFriendsModel();
            } else {
                showErrorAlert("Nu a fost selectat niciun utilizator!", "error-label");
            }
        } catch (Exception e) {
            showErrorAlert("Eroare la adaugarea prietenului!", "error-label");
        }
    }

    @FXML
    public void handleDeleteFriend(ActionEvent actionEvent) {
        try {
            Utilizator selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                service.stergePrietenie(selected.getId(), loggedUser.getId());
                showErrorAlert("Prietenul a fost sters cu succes!", "success-label");
                initFriendsModel();
            } else {
                showErrorAlert("Nu a fost selectat niciun utilizator!", "error-label");
            }
        } catch (Exception e) {
            showErrorAlert("Eroare la eliminarea prietenului!", "error-label");
        }
    }

    @FXML
    public void handleUserProfileDetails(ActionEvent actionEvent) {
        Utilizator loggedUser = service.getLoggedUser();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab7/gui/views/profile-view.fxml"));
            AnchorPane userProfilePane = loader.load();

            UserProfileController userProfileController = loader.getController();
            userProfileController.setService(service);
            userProfileController.init(loggedUser);

            Stage userProfileStage = new Stage();
            userProfileStage.setTitle("User Profile");
            userProfileStage.initModality(Modality.WINDOW_MODAL);
            userProfileStage.initOwner(tableView.getScene().getWindow());

            Scene userProfileScene = new Scene(userProfilePane);
            userProfileStage.setScene(userProfileScene);

            userProfileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCommunityOfLoggedUser(ActionEvent actionEvent) {
        try {
            initCommunityModel();
        } catch (Exception e) {
            showErrorAlert("Eroare la redarea comunitatii!", "error-label");
        }
    }

    @FXML
    public void handleRefreshTable(ActionEvent actionEvent) {
        initModel();
    }

    @FXML
    public void handleRefreshFriendsTable(ActionEvent actionEvent) {
        initFriendsModel();
    }

    private void showErrorAlert(String message, String styleClass) {
        errorMessageLabel.setText(message);
        errorMessageLabel.getStyleClass().setAll(styleClass);
        errorMessageLabel.setVisible(true);

        fadeOut = new FadeTransition(Duration.seconds(2), errorMessageLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }

    private void clearAddFields() {
        dataForSearch.clear();
    }

    @FXML
    private void handleExit() {
        Stage currentStage = (Stage) dataForSearch.getScene().getWindow();
        currentStage.close();
    }
}