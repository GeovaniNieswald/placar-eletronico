package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.RespostaSocket;
import com.acme.model.Tela;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class EsperaController implements Initializable {

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    private FontAwesomeIconView faivSair;

    private double xOffset = 0;
    private double yOffset = 0;

    private Timeline esperandoUsuarioPrincipal = new Timeline();

    @FXML
    void gpOnMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - xOffset, event.getScreenY() - yOffset);
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        PlacarClient.desconectar();
        MainApp.trocarCena(Tela.CONEXAO);
        esperandoUsuarioPrincipal.stop();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        esperandoUsuarioPrincipal = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                RespostaSocket respostaUsuarioPrincipal = PlacarClient.verificarSeUsuarioPrincipalEstaConectado();

                if (respostaUsuarioPrincipal == RespostaSocket.USUARIO_PRINCIPAL_CONECTADO) {
                    MainApp.trocarCena(Tela.USUARIO_PROPAGANDA);
                    esperandoUsuarioPrincipal.stop();
                }
            } catch (IOException ex) {
                // Informar erro de conexão
                // IMPLEMENTAR LOG
            }
        }),
                new KeyFrame(Duration.seconds(5))
        );
        esperandoUsuarioPrincipal.setCycleCount(Animation.INDEFINITE);
        esperandoUsuarioPrincipal.play();
    }
}
