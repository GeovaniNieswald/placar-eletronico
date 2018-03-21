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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;

public class EsporteController implements Initializable {

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXCheckBox jfxcbBasquete;

    @FXML
    private JFXCheckBox jfxcbFutsal;

    @FXML
    private JFXButton jfxbOk;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        try {
            MainApp.trocarCena(Tela.CONEXAO);
        } catch (IOException ex) {
            Logger.getLogger(EsperaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - xOffset, event.getScreenY() - yOffset);
    }

    @FXML
    void gpOnMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void jfxbOkOnAction(ActionEvent event) {
        try {
            RespostaSocket respostaComando;

            if (jfxcbBasquete.isSelected()) {
                respostaComando = PlacarClient.escolherEsporte("#esporte;basquete;1");
            } else {
                respostaComando = PlacarClient.escolherEsporte("#esporte;futsal;1");
            }

            switch (respostaComando) {
                case COMANDO_ACEITO_BASQUETE:
                    MainApp.trocarCena(Tela.USUARIO_PRINCIPAL);
                    break;
                case COMANDO_ACEITO_FUTSAL:
                    MainApp.trocarCena(Tela.USUARIO_PRINCIPAL); // Verificar se precisam ser telas diferentes
                    break;
                default:
                    System.out.println("erro"); // Tratar 
                }
        } catch (IOException ex) {
            Logger.getLogger(EsporteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void jfxcbBasqueteOnAction(ActionEvent event) {
        jfxcbBasquete.setSelected(true);
        jfxcbFutsal.setSelected(false);
    }

    @FXML
    void jfxcbFutsalOnAction(ActionEvent event) {
        jfxcbFutsal.setSelected(true);
        jfxcbBasquete.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
