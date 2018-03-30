package com.acme.controller;

import com.acme.PlacarServer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class PlacarController implements Initializable {

    @FXML
    private Button bFechar;

    @FXML
    private Label lTextoInferior;

    @FXML
    private Label lNomeTimeLocal;

    @FXML
    private Label lNomeTimeVisitante;

    @FXML
    private Label lPontosTimeLocal;

    @FXML
    private Label lPontosTimeVisitante;

    private int pontosTimeLocal;
    private int pontosTimeVisitante;

    private void linhaDoTempo(Label label, String texto) {
        Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            label.setText(texto);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tl.play();
    }

    public void setNomeTimeLocal(String texto) {
        linhaDoTempo(lNomeTimeLocal, texto);
    }

    public void setNomeTimeVisitante(String texto) {
        linhaDoTempo(lNomeTimeVisitante, texto);
    }

    public void setTextoInferior(String texto) {
        linhaDoTempo(lTextoInferior, texto);
    }

    public void aumentarPontosTimeLocal(int pontos) {
        pontosTimeLocal += pontos;

        if (pontosTimeLocal > 9) {
            linhaDoTempo(lPontosTimeLocal, pontosTimeLocal + "");
        } else {
            linhaDoTempo(lPontosTimeLocal, "0" + pontosTimeLocal);
        }
    }

    public void diminuirPontosTimeLocal(int pontos) {
        pontosTimeLocal -= pontos;

        if (pontosTimeLocal > 9) {
            linhaDoTempo(lPontosTimeLocal, pontosTimeLocal + "");
        } else {
            linhaDoTempo(lPontosTimeLocal, "0" + pontosTimeLocal);
        }
    }

    public void aumentarPontosTimeVisitante(int pontos) {
        pontosTimeVisitante += pontos;

        if (pontosTimeVisitante > 9) {
            linhaDoTempo(lPontosTimeVisitante, pontosTimeVisitante + "");
        } else {
            linhaDoTempo(lPontosTimeVisitante, "0" + pontosTimeVisitante);
        }
    }

    public void diminuirPontosTimeVisitante(int pontos) {
        pontosTimeVisitante -= pontos;

        if (pontosTimeVisitante > 9) {
            linhaDoTempo(lPontosTimeVisitante, pontosTimeVisitante + "");
        } else {
            linhaDoTempo(lPontosTimeVisitante, "0" + pontosTimeVisitante);
        }
    }

    public void zerarPontos() {
        pontosTimeLocal = 0;
        pontosTimeVisitante = 0;

        linhaDoTempo(lPontosTimeLocal, "00");
        linhaDoTempo(lPontosTimeVisitante, "00");
    }

    @FXML
    void bFecharAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlacarServer.instanciaPlacarController(this);
    }

}
