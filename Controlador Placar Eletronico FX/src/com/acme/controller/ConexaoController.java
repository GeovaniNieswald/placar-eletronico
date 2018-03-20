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
    void jfxbConectarAction(ActionEvent event) {
        lInfos.setText("");
        jfxtfLogin.setUnFocusColor(Paint.valueOf("white"));
        jfxtfLogin.setFocusColor(Paint.valueOf("#7d217c"));
        jfxpfSenha.setUnFocusColor(Paint.valueOf("white"));
        jfxpfSenha.setFocusColor(Paint.valueOf("#7d217c"));
        jfxtfEndereco.setUnFocusColor(Paint.valueOf("white"));
        jfxtfEndereco.setFocusColor(Paint.valueOf("#7d217c"));

        if (jfxtfEndereco.getText().trim().isEmpty() || jfxtfLogin.getText().trim().isEmpty() || jfxpfSenha.getText().trim().isEmpty()) {
            lInfos.setText("Você não preencheu algum campo!");

            if (jfxtfEndereco.getText().trim().isEmpty()) {
                jfxtfEndereco.setText("");
                jfxtfEndereco.setUnFocusColor(Paint.valueOf("red"));
                jfxtfEndereco.setFocusColor(Paint.valueOf("red"));
            }
            if (jfxtfLogin.getText().trim().isEmpty()) {
                jfxtfLogin.setText("");
                jfxtfLogin.setUnFocusColor(Paint.valueOf("red"));
                jfxtfLogin.setFocusColor(Paint.valueOf("red"));
            }
            if (jfxpfSenha.getText().trim().isEmpty()) {
                jfxpfSenha.setText("");
                jfxpfSenha.setUnFocusColor(Paint.valueOf("red"));
                jfxpfSenha.setFocusColor(Paint.valueOf("red"));
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
                        lInfos.setText("Login ou Senha errados!");
                        jfxtfLogin.setUnFocusColor(Paint.valueOf("red"));
                        jfxtfLogin.setFocusColor(Paint.valueOf("red"));
                        jfxpfSenha.setUnFocusColor(Paint.valueOf("red"));
                        jfxpfSenha.setFocusColor(Paint.valueOf("red"));
                        jfxpfSenha.setText("");
                }
            } catch (IOException ex) {
                lInfos.setText("Aconteceu algum erro na conexão!");
                jfxtfEndereco.setUnFocusColor(Paint.valueOf("red"));
                jfxtfEndereco.setFocusColor(Paint.valueOf("red"));
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
