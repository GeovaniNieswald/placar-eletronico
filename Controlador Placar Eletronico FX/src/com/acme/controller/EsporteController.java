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
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class EsporteController implements Initializable {

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private void escolherEsporte(String esporte) {
        try {
            RespostaSocket respostaEsporte = PlacarClient.enviarComando(Comando.ESCOLHER_ESPORTE, esporte);

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
    void ovMinimizarOnMouseClicked(MouseEvent event) {
        MainApp.minimizar();
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        PlacarClient.desconectar();
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

    @FXML
    void ivBasqueteOnMouseClicked(MouseEvent event) {
        escolherEsporte("basquete");
    }

    @FXML
    void ivFutsalOnMouseClicked(MouseEvent event) {
        escolherEsporte("futsal");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
