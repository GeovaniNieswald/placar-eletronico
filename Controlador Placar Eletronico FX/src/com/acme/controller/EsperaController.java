package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.acme.model.Cena;
import com.acme.MeuLogger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class EsperaController implements Initializable {

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private Timeline esperandoUsuarioPlacar = new Timeline();

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        PlacarClient.desconectar();
        System.exit(0);
    }

    @FXML
    void ovMinimizarOnMouseClicked(MouseEvent event) {
        MainApp.minimizar();
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        PlacarClient.desconectar();
        esperandoUsuarioPlacar.stop();
        MainApp.trocarCena(Cena.CONEXAO);
    }

    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - posicaoInicialX, event.getScreenY() - posicaoInicialY);
    }

    @FXML
    void gpOnMousePressed(MouseEvent event) {
        posicaoInicialX = event.getSceneX();
        posicaoInicialY = event.getSceneY();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        esperandoUsuarioPlacar = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                RespostaSocket respostaUsuarioPlacar = PlacarClient.enviarComando(Comando.VERIFICAR_USUARIO_PLACAR);

                if (respostaUsuarioPlacar == RespostaSocket.USUARIO_PLACAR_CONECTADO) {
                    MainApp.trocarCena(Cena.PROPAGANDA);
                    esperandoUsuarioPlacar.stop();
                }
            } catch (IOException ex) {
                MeuLogger.logException(Level.SEVERE, "Erro de conexão.", ex);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução e reinicie o programa!", Alert.AlertType.ERROR);
                esperandoUsuarioPlacar.stop();
            }
        }),
                new KeyFrame(Duration.seconds(5))
        );
        esperandoUsuarioPlacar.setCycleCount(Animation.INDEFINITE);
        esperandoUsuarioPlacar.play();
    }
}
