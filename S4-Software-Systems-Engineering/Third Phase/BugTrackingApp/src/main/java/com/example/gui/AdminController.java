package com.example.gui;

import com.example.service.Service;
import javafx.stage.Stage;

public class AdminController {

    private Service service;
    private Stage stage;

    public void setService(Service service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
