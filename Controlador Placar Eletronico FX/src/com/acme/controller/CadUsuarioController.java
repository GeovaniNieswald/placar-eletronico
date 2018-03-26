package com.acme.controller;

import com.acme.MainApp;
import static com.acme.MainApp.trocarCena;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
        trocarCena(Cena.ESPORTE);
    }

    @FXML
    void jfxbSalvarOnAction(ActionEvent event) {
        String nomeuser = jfxtfUsername.getText();
        String senha = jfxtfPassword.getText();
        String admin = String.valueOf(jfxcbAdmin.isSelected());
        String placar = String.valueOf(jfxcbPlacar.isSelected());
        String prop = String.valueOf(jfxcbProp.isSelected());

        if (nomeuser.trim().isEmpty() || senha.trim().isEmpty()) {
            PlacarClient.alert("Usuário e senha devem estar preenchidos!", Alert.AlertType.WARNING);
        } else if (!(jfxcbAdmin.isSelected() || jfxcbPlacar.isSelected() || jfxcbProp.isSelected())) {
            PlacarClient.alert("Usuário precisa ter pelo menos 1 permissão marcada!", Alert.AlertType.WARNING);
        } else {
            try {
                RespostaSocket resp = PlacarClient.enviarComando(Comando.CRIAR_USUARIO, "add", nomeuser, senha, admin, placar, prop);
                switch (resp) {
                    case USUARIO_JA_EXISTE:
                        break;
                    default:
                        PlacarClient.alert("Usuário salvo!", Alert.AlertType.INFORMATION);
                        jfxtfUsername.setText("");
                        jfxtfPassword.setText("");
                        jfxcbAdmin.setSelected(false);
                        jfxcbPlacar.setSelected(false);
                        jfxcbProp.setSelected(false);
                        break;
                }
            } catch (IOException ex) {
                // Mostrar erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    public void initialize(URL url, ResourceBundle rb) {

    }

}
