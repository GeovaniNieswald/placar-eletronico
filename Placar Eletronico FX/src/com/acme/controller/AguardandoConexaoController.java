package com.acme.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AguardandoConexaoController implements Initializable {

    @FXML
    private Button bFechar;

    @FXML
    private Label lData;

    @FXML
    private Label lHora;

    private DateFormat sdfData;
    private DateFormat sdfHora;
    private Calendar cal;

    @FXML
    void bFecharAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sdfData = new SimpleDateFormat("dd/MM/yyyy");
        sdfHora = new SimpleDateFormat("HH:mm:ss");

        // Relógio em tempo real
        Timeline relogio = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            cal = GregorianCalendar.getInstance();
            lData.setText(sdfData.format(cal.getTime()));
            lHora.setText(sdfHora.format(cal.getTime()));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        relogio.setCycleCount(Animation.INDEFINITE);
        relogio.play();
    }
}
