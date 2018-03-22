package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.RespostaSocket;
import com.acme.model.Tela;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EsporteController implements Initializable {

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXRadioButton jfxrbBasquete;

    @FXML
    private ToggleGroup tgEsportes;

    @FXML
    private JFXRadioButton jfxrbFutsal;

    @FXML
    private JFXButton jfxbOk;

    private double xOffset = 0;
    private double yOffset = 0;

    private void escolher() {
        try {
            RespostaSocket respostaComando;

            if (jfxrbBasquete.isSelected()) {
                respostaComando = PlacarClient.escolherEsporte("#esporte;basquete;1");
            } else {
                respostaComando = PlacarClient.escolherEsporte("#esporte;futsal;1");
            }

            switch (respostaComando) {
                case COMANDO_ACEITO_BASQUETE:
                    MainApp.trocarCena(Tela.USUARIO_PRINCIPAL_BASQUETE);
                    break;
                case COMANDO_ACEITO_FUTSAL:
                    MainApp.trocarCena(Tela.USUARIO_PRINCIPAL_FUTSAL);
            }
        } catch (IOException ex) {
            // Mostrar erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        MainApp.trocarCena(Tela.CONEXAO);
    }

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
    void jfxbOkOnAction(ActionEvent event) {
        escolher();
    }

    @FXML
    void jfxbOkOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            escolher();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
