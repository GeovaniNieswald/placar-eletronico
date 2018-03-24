package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CadUsuarioController {

    @FXML
    private JFXTextField jfxtfUsername;

    @FXML
    private JFXTextField jfxtfPassword;

    @FXML
    private JFXCheckBox jfxcbAdmin;

    @FXML
    private JFXCheckBox jfxcbProp;

    @FXML
    private JFXCheckBox jfxcbPlacar;

    @FXML
    private JFXButton jfxbSalvar;

    @FXML
    private JFXButton jfxbCancelar;

    @FXML
    void jfxbCancelarOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbSalvarOnAction(ActionEvent event) {
        String nomeuser = jfxtfUsername.getText();
        String senha = jfxtfPassword.getText();

        try {
            RespostaSocket resp = PlacarClient.enviarComando(Comando.CRIAR_USUARIO, nomeuser, senha);
            switch (resp) {
                case USUARIO_JA_EXISTE:
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            // Mostrar erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    public void initialize(URL url, ResourceBundle rb) {

    }

}
