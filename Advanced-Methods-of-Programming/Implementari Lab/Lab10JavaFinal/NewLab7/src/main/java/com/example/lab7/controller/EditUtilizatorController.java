package com.example.lab7.controller;

import com.example.lab7.domain.Utilizator;
import com.example.lab7.service.Service;
import com.example.lab7.validators.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUtilizatorController {

    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldNume;
    @FXML
    private TextField textFieldPrenume;

    private Service service;
    Stage dialogStage;
    Utilizator utilizator;

    @FXML
    private void initialize() {
    }

    public void setService(Service service, Stage stage, Utilizator utilizator) {
        this.service = service;
        this.dialogStage = dialogStage;
        this.utilizator = utilizator;
        if (null != utilizator) {
            setFields(utilizator);
            textFieldId.setEditable(false);
        }
    }

//    @FXML
//    public void handleSave() {
//        String id = textFieldId.getText();
//        String nume = textFieldNume.getText();
//        String prenume = textFieldPrenume.getText();
//        Utilizator utilizator = new Utilizator(nume, prenume);
//        if (null == this.utilizator)
//            saveUtilizator(utilizator);
//        else
//            updateUtilizator(utilizator);
//    }

    private void updateUtilizator(Utilizator utilizator) {
        try {
            Utilizator newUtilizator = this.service.modificaUtilizator(utilizator);
            if (newUtilizator == null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Modificare utilizator", "Utilizatorul a fost modificat cu succes!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
        dialogStage.close();
    }

    private void saveUtilizator(Utilizator utilizator) {
        try {
            Utilizator newUtilizator = this.service.adaugaUtilizator(utilizator);
            if (newUtilizator == null)
                dialogStage.close();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Salvare utilizator", "Utilizatorul a fost salvat cu succes!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
        dialogStage.close();
    }

    private void clearFields() {
        textFieldId.setText("");
        textFieldNume.setText("");
        textFieldPrenume.setText("");
    }

    private void setFields(Utilizator utilizator) {
        textFieldId.setText(utilizator.getId().toString());
        textFieldNume.setText(utilizator.getNume());
        textFieldPrenume.setText(utilizator.getPrenume());
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }
}
