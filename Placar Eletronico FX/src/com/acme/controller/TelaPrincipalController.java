package com.acme.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TelaPrincipalController implements Initializable {

    @FXML
    private Button bFechar;

    @FXML
    private Label lAguardandoConexao;

    @FXML
    private Label lRelogio;

    private int controlePontos;

    @FXML
    void bFecharAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controlePontos = -1;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy    -    HH:mm:ss");

        // Relógio em tempo real
        Timeline relogio = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Calendar cal = GregorianCalendar.getInstance();
            lRelogio.setText(df.format(cal.getTime()));

        }),
                new KeyFrame(Duration.seconds(1))
        );
        relogio.setCycleCount(Animation.INDEFINITE);
        relogio.play();

        // Mensagem de aguardo
        Timeline aguardando = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            controlePontos++;

            switch (controlePontos) {
                case 0:
                    lAguardandoConexao.setText("Aguardando Conexão");
                    break;
                case 1:
                    lAguardandoConexao.setText("Aguardando Conexão.");
                    break;
                case 2:
                    lAguardandoConexao.setText("Aguardando Conexão..");
                    break;
                case 3:
                    lAguardandoConexao.setText("Aguardando Conexão...");
                    break;
                case 4:
                    lAguardandoConexao.setText("Aguardando Conexão..");
                    break;
                case 5:
                    lAguardandoConexao.setText("Aguardando Conexão.");
                    controlePontos = -1;
                    break;
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        aguardando.setCycleCount(Animation.INDEFINITE);
        aguardando.play();
    }
}