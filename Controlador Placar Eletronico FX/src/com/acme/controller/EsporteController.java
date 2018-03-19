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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class EsporteController implements Initializable {

    @FXML
    private RadioButton rbBasquete;

    @FXML
    private ToggleGroup esportes;

    @FXML
    private RadioButton rbFutsal;

    @FXML
    private Button bOk;

    @FXML
    void bOkAction(ActionEvent event) {
        try {
            RespostaSocket respostaComando;

            if (rbBasquete.isSelected()) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
