package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.RespostaSocket;
import com.acme.model.Tela;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class EsperaController implements Initializable {

    @FXML
    private Label lPontos;

    private int controlePontos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlePontos = -1;

        Timeline aguardando = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            controlePontos++;

            switch (controlePontos) {
                case 0:
                    lPontos.setText("");
                    break;
                case 1:
                    lPontos.setText(".");
                    break;
                case 2:
                    lPontos.setText("..");
                    break;
                case 3:
                    lPontos.setText("...");
                    controlePontos = -1;
                    break;
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        aguardando.setCycleCount(Animation.INDEFINITE);
        aguardando.play();

        Timeline esperandoUsuarioPrincipal = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                RespostaSocket respostaUsuarioPrincipal = PlacarClient.verificarSeUsuarioPrincipalEstaConectado();

                if (respostaUsuarioPrincipal == RespostaSocket.USUARIO_PRINCIPAL_CONECTADO) {
                    MainApp.trocarCena(Tela.USUARIO_PROPAGANDA);
                }
            } catch (IOException ex) {
                Logger.getLogger(EsperaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }),
                new KeyFrame(Duration.seconds(5))
        );
        esperandoUsuarioPrincipal.setCycleCount(Animation.INDEFINITE);
        esperandoUsuarioPrincipal.play();
    }
}
