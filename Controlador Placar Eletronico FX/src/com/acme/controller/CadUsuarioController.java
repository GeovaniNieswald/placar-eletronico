package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

/**
 * Classe Referente ao controlador da cena de cadastro de usuário.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class CadUsuarioController {

    @FXML
    private JFXTextField jfxtfUsername;

    @FXML
    private JFXPasswordField jfxtfPassword;

    @FXML
    private JFXCheckBox jfxcbAdmin;

    @FXML
    private JFXCheckBox jfxcbPlacar;

    @FXML
    private JFXCheckBox jfxcbProp;

    @FXML
    private JFXButton jfxbSalvar;

    @FXML
    private JFXButton jfxbCancelar;

    @FXML
    private JFXTextField jfxtfUsernameL;

    @FXML
    private JFXTextField jfxtfPasswordL;

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    private FontAwesomeIconView faivSair;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        MainApp.trocarCena(Cena.GERENCIAR_USUARIOS);
    }

    @FXML
    void jfxbCancelarOnAction(ActionEvent event) {
        MainApp.trocarCena(Cena.GERENCIAR_USUARIOS);
    }

    /**
     * Método acionado quando o algum botão do mouse é pressionado, ele pega a
     * posição atual horizontal e vertical da cena.
     *
     * @param event MouseEvent.
     */
    @FXML
    void gpOnMousePressed(MouseEvent event) {
        posicaoInicialX = event.getSceneX();
        posicaoInicialY = event.getSceneY();
    }

    /**
     * Método acionado quando o mouse é arrastado, ele pega a posição atual
     * horizontal e vertical da cena, faz a subtração pela posição inicial
     * horizontal e vertical separadamente, e chama o método que move a tela,
     * passando os valores resultantes dessas subtrações.
     *
     * @param event MouseEvent.
     */
    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - posicaoInicialX, event.getScreenY() - posicaoInicialY);
    }

    @FXML
    void jfxbSalvarOnAction(ActionEvent event) {
        String nomeuser = jfxtfUsername.getText();
        String senha = jfxtfPassword.getText();
        String admin = String.valueOf(jfxcbAdmin.isSelected());
        String placar = String.valueOf(jfxcbPlacar.isSelected());
        String prop = String.valueOf(jfxcbProp.isSelected());

        if (nomeuser.trim().isEmpty() || senha.trim().isEmpty()) {
            Utils.alert("Usuário e senha devem estar preenchidos!", Alert.AlertType.WARNING);
        } else if (nomeuser.contains(",") || nomeuser.contains(";")) {
            Utils.alert("Nome de usuário não pode conter vírgula nem ponto-e-vírgula", Alert.AlertType.WARNING);
        } else if (!(jfxcbAdmin.isSelected() || jfxcbPlacar.isSelected() || jfxcbProp.isSelected())) {
            Utils.alert("Usuário precisa ter pelo menos 1 permissão marcada!", Alert.AlertType.WARNING);
        } else {
            try {
                RespostaSocket resp = PlacarClient.enviarComando(Comando.CADASTRO_USUARIO, "add", nomeuser, senha, admin, placar, prop);
                switch (resp) {
                    case USUARIO_JA_EXISTE:
                        break;
                    default:
                        Utils.alert("Usuário salvo!", Alert.AlertType.INFORMATION);
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

    @FXML
    void jfxcbAdminOnAction(ActionEvent event) {
        jfxcbPlacar.setSelected(false);
        jfxcbProp.setSelected(false);
    }

    @FXML
    void jfxcbPlacarOnAction(ActionEvent event) {
        jfxcbAdmin.setSelected(false);
        jfxcbProp.setSelected(false);
    }

    @FXML
    void jfxcbPropOnAction(ActionEvent event) {
        jfxcbAdmin.setSelected(false);
        jfxcbPlacar.setSelected(false);
    }

    public void initialize(URL url, ResourceBundle rb) {
    }
}
