package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.RespostaSocket;
import com.acme.model.Cena;
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

/**
 * Classe Referente ao controlador da cena de conexão.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
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

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    /**
     * Método para trocar a cor dos campos TextField.
     *
     * @param corUnFocus String - Hexadecimal da cor quando o campo não está com
     * foco.
     * @param corFocus String - Hexadecimal da cor quando o campo está com foco.
     * @param componentes JFXTextField - Varargs que contém os campos.
     */
    private void trocarCorJFXTextField(String corUnFocus, String corFocus, JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setUnFocusColor(Paint.valueOf(corUnFocus));
            comp.setFocusColor(Paint.valueOf(corFocus));
        }
    }

    /**
     * Método para trocar a cor do campo PasswordField.
     *
     * @param corUnFocus String - Hexadecimal da cor quando o campo não está com
     * foco.
     * @param corFocus String - Hexadecimal da cor quando o campo está com foco.
     * @param componente JFXPasswordField - Campo.
     */
    private void trocarCorJFXPasswordField(String corUnFocus, String corFocus, JFXPasswordField componente) {
        componente.setUnFocusColor(Paint.valueOf(corUnFocus));
        componente.setFocusColor(Paint.valueOf(corFocus));
    }

    /**
     * Método para conectar ao servidor (placar eletrônico).
     *
     */
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
                        MainApp.trocarCena(Cena.ESPORTE);
                        break;
                    case CONEXAO_ACEITA_USUARIO_PROPAGANDA:
                        MainApp.trocarCena(Cena.ESPERA);
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

    /**
     * Método acionado quando o algum botão do mouse é pressionado, ele pega a posição atual
     * horizontal e vertical da cena.
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
