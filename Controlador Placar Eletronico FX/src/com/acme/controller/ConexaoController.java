package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.RespostaSocket;
import com.acme.model.Tela;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class ConexaoController implements Initializable {

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXTextField jfxtfEndereco;

    @FXML
    private JFXTextField jfxtfLogin;

    @FXML
    private JFXPasswordField jfxpfSenha;

    @FXML
    private JFXButton jfxbConectar;

    @FXML
    private Label lInfos;

    private double xOffset = 0;
    private double yOffset = 0;

    private void trocarCorJFXTextField(String corUnFocus, String corFocus, JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setUnFocusColor(Paint.valueOf(corUnFocus));
            comp.setFocusColor(Paint.valueOf(corFocus));
        }
    }

    private void trocarCorJFXPasswordField(String corUnFocus, String corFocus, JFXPasswordField componente) {
        componente.setUnFocusColor(Paint.valueOf(corUnFocus));
        componente.setFocusColor(Paint.valueOf(corFocus));
    }

    private void conectar() {
        lInfos.setText("");

        trocarCorJFXTextField("white", "#7d217c", jfxtfLogin, jfxtfEndereco);
        trocarCorJFXPasswordField("white", "#7d217c", jfxpfSenha);

        if (jfxtfEndereco.getText().trim().isEmpty() || jfxtfLogin.getText().trim().isEmpty() || jfxpfSenha.getText().trim().isEmpty()) {
            lInfos.setText("Você não preencheu algum campo!");

            if (jfxtfEndereco.getText().trim().isEmpty()) {
                jfxtfEndereco.setText("");
                trocarCorJFXTextField("red", "red", jfxtfEndereco);
            }
            if (jfxtfLogin.getText().trim().isEmpty()) {
                jfxtfLogin.setText("");
                trocarCorJFXTextField("red", "red", jfxtfLogin);
            }
            if (jfxpfSenha.getText().trim().isEmpty()) {
                jfxpfSenha.setText("");
                trocarCorJFXPasswordField("red", "red", jfxpfSenha);
            }
        } else {
            try {
                RespostaSocket respostaConexao = PlacarClient.conectar(jfxtfEndereco.getText(), jfxtfLogin.getText(), jfxpfSenha.getText());

                switch (respostaConexao) {
                    case CONEXAO_ACEITA_USUARIO_PRINCIPAL:
                        MainApp.trocarCena(Tela.ESPORTE);
                        break;
                    case CONEXAO_ACEITA_USUARIO_PROPAGANDA:
                        MainApp.trocarCena(Tela.ESPERA);
                        break;
                    default:
                        PlacarClient.desconectar();

                        lInfos.setText("Login ou Senha errados!");
                        trocarCorJFXTextField("red", "red", jfxtfLogin);
                        trocarCorJFXPasswordField("red", "red", jfxpfSenha);
                        jfxpfSenha.setText("");
                }
            } catch (IOException ex) {
                lInfos.setText("Aconteceu algum erro na conexão!");
                trocarCorJFXTextField("red", "red", jfxtfEndereco);
                // IMPLEMENTAR LOG
            }
        }
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
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void jfxpfSenhaOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            conectar();
        }
    }

    @FXML
    void jfxbConectarAction(ActionEvent event) {
        conectar();
    }

    @FXML
    void jfxbConectarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            conectar();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
