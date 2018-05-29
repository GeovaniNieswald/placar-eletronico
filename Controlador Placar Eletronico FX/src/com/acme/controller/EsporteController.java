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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXRadioButton;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EsporteController implements Initializable {

    @FXML
    private JFXRadioButton jfxrbBasquete;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private void escolherEsporte() {
        try {
            RespostaSocket respostaEsporte;

            if (jfxrbBasquete.isSelected()) {
                respostaEsporte = PlacarClient.enviarComando(Comando.ESCOLHER_ESPORTE, "basquete");
            } else {
                respostaEsporte = PlacarClient.enviarComando(Comando.ESCOLHER_ESPORTE, "futsal");
            }

            switch (respostaEsporte) {
                case ESPORTE_ACEITO_BASQUETE:
                    MainApp.trocarCena(Cena.PLACAR_BASQUETE);
                    break;
                case ESPORTE_ACEITO_FUTSAL:
                    MainApp.trocarCena(Cena.PLACAR_FUTSAL);
                    break;
                default:
                    MeuLogger.logMensagem(Level.WARNING, "RespostaSocket informada não está presente entre as opções do switch.");
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.SEVERE, "Erro de conexão.", ex);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução e reinicie o programa!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        PlacarClient.desconectar();
        MainApp.trocarCena(Cena.CONEXAO);
    }

    @FXML
    void gpOnMousePressed(MouseEvent event) {
        posicaoInicialX = event.getSceneX();
        posicaoInicialY = event.getSceneY();
    }

    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - posicaoInicialX, event.getScreenY() - posicaoInicialY);
    }

    @FXML
    void jfxbOkOnAction(ActionEvent event) {
        escolherEsporte();
    }

    @FXML
    void jfxbOkOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            escolherEsporte();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
