package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.RespostaSocket;
import com.acme.model.Cena;
import com.acme.MeuLogger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
    private JFXTextField jfxtfEndereco;

    @FXML
    private JFXTextField jfxtfLogin;

    @FXML
    private JFXPasswordField jfxpfSenha;

    @FXML
    private Label lInfos;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

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

        trocarCorJFXTextField("white", "#890e01", jfxtfLogin, jfxtfEndereco);
        trocarCorJFXPasswordField("white", "#890e01", jfxpfSenha);

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
                    case CONEXAO_ACEITA_USUARIO_ADMINISTRADOR:
                        MainApp.trocarCena(Cena.GERENCIAR_USUARIOS);
                        break;
                    case CONEXAO_ACEITA_USUARIO_PLACAR:
                        MainApp.trocarCena(Cena.ESPORTE);
                        break;
                    case CONEXAO_ACEITA_USUARIO_PROPAGANDA:
                        MainApp.trocarCena(Cena.ESPERA);
                        break;
                    default:
                        PlacarClient.desconectar();

                        lInfos.setText("Login ou Senha inválidos!");
                        trocarCorJFXTextField("red", "red", jfxtfLogin);
                        trocarCorJFXPasswordField("red", "red", jfxpfSenha);
                        jfxpfSenha.setText("");
                }
            } catch (IOException ex) {
                MeuLogger.logException(Level.WARNING, "Erro de conexão.", ex);
                lInfos.setText("Aconteceu algum erro na conexão!");
                trocarCorJFXTextField("red", "red", jfxtfEndereco);
            }
        }
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
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
    void jfxbConectarAction(ActionEvent event) {
        conectar();
    }

    @FXML
    void jfxbConectarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            conectar();
        }
    }

    @FXML
    void jfxpfSenhaOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            conectar();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
