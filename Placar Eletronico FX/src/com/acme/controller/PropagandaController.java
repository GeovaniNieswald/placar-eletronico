package com.acme.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PropagandaController {

    @FXML
    private Button bFechar;

    @FXML
    void bFecharAction(ActionEvent event) {
        System.exit(0);
    }

}
