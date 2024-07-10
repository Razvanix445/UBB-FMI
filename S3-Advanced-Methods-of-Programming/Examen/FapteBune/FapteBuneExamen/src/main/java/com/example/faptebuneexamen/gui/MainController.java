package com.example.faptebuneexamen.gui;

import com.example.faptebuneexamen.domain.Nevoie;
import com.example.faptebuneexamen.domain.Persoana;
import com.example.faptebuneexamen.events.ChangeEventType;
import com.example.faptebuneexamen.events.NevoieChangeEvent;
import com.example.faptebuneexamen.observer.Observer;
import com.example.faptebuneexamen.service.Service;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class MainController implements Observer<NevoieChangeEvent> {

    private Service service;
    private Stage stage;
    private Persoana persoana;

    @FXML
    private TableView<Nevoie> nevoiTableView;

    @FXML
    private TableColumn<Nevoie, String> titluColumn;

    @FXML
    private TableColumn<Nevoie, String> descriereColumn;

    @FXML
    private TableColumn<Nevoie, String> dataColumn;

    @FXML
    private TableColumn<Nevoie, String> statusColumn;

    @FXML
    private TableView<Nevoie> fapteBuneTableView;

    @FXML
    private TableColumn<Nevoie, String> titluFapteBuneColumn;

    @FXML
    private TableColumn<Nevoie, String> descriereFapteBuneColumn;

    @FXML
    private TableColumn<Nevoie, String> dataFapteBuneColumn;

    @FXML
    private TableColumn<Nevoie, String> statusFapteBuneColumn;

    @FXML
    private Text confirmationText;

    @FXML
    private TextField titluNevoieNoua;

    @FXML
    private TextField descriereNevoieNoua;

    @FXML
    private DatePicker deadlineNevoieNoua;

    @FXML
    private Button adaugaNevoieNouaButton;

    @FXML
    private Text confirmationNevoieText;

    public void setService(Service service) {
        this.service = service;
        populateNevoiTableView();
        populateFapteBuneTableView();
        nevoiTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                onNevoieSelected();
            }
        });
        service.addObserver(this);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPersoana(Persoana persoana) {
        this.persoana = persoana;
    }

    private void populateNevoiTableView() {
        titluColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        List<Nevoie> nevoiList = service.getNevoiForCity(persoana.getOras().toString(), persoana);
        nevoiTableView.setItems(FXCollections.observableArrayList(nevoiList));
    }

    private void populateFapteBuneTableView() {
        titluFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriereFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        dataFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        statusFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        List<Nevoie> nevoiList = service.getFapteBune(persoana);
        fapteBuneTableView.setItems(FXCollections.observableArrayList(nevoiList));
    }

    private void onNevoieSelected() {
        Nevoie selectedNevoie = nevoiTableView.getSelectionModel().getSelectedItem();
        if (selectedNevoie != null && selectedNevoie.getOmSalvator() == 0L) {
            claimNevoie(selectedNevoie);
        }
        populateNevoiTableView();
        populateFapteBuneTableView();
    }

    private void claimNevoie(Nevoie nevoie) {
        service.claimNevoie(nevoie, persoana.getId());
        confirmationText.setText("Ai preluat nevoia cu succes!");

        nevoiTableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void onNewNevoiePressedButton() {
        String titlu = titluNevoieNoua.getText();
        String descriere = descriereNevoieNoua.getText();
        LocalDateTime deadline = deadlineNevoieNoua.getValue().atStartOfDay();
        Nevoie nevoie = new Nevoie(titlu, descriere, deadline, persoana.getId(), 0L, "Caut erou!");
        service.adaugaNevoie(nevoie);
        populateNevoiTableView();
        populateFapteBuneTableView();
        confirmationNevoieText.setText("Ai adaugat nevoia cu succes!");
    }

    @Override
    public void update(NevoieChangeEvent event) {
        if (event.getChangeEventType() == ChangeEventType.ADD || event.getChangeEventType() == ChangeEventType.DELETE || event.getChangeEventType() == ChangeEventType.UPDATE) {
            populateNevoiTableView();
            populateFapteBuneTableView();
        }
    }
}
