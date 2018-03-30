package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

/**
 * Classe Referente ao controlador da cena de propaganda.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class UsuarioPropagandaController implements Initializable {

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXTextField jfxtfTextoInferiorL;

    @FXML
    private JFXTextField jfxtfTextoInferior;

    @FXML
    private JFXButton jfxbAlterarTextoInferior;

    @FXML
    private JFXButton jfxbRestaurarTextoInferior;

    @FXML
    private JFXTextField jfxtfImagemDireita;

    @FXML
    private JFXButton jfxbAlterarImagemDireita;

    @FXML
    private JFXButton jfxbRestaurarImagemDireita;

    @FXML
    private JFXTextField jfxtfImagemEsquerda;

    @FXML
    private JFXButton jfxbAlterarImagemEsquerda;

    @FXML
    private JFXButton jfxbRestaurarImagemEsquerda;

    @FXML
    private JFXTextField jfxtfImagemVideo;

    @FXML
    private JFXButton jfxbExibirImagemVideo;

    @FXML
    private JFXButton jfxbPararImagemVideo;

    @FXML
    private JFXTextField jfxtfEscalacoes;

    @FXML
    private JFXButton jfxbExibirEscalacoes;

    @FXML
    private JFXButton jfxbPararEscalacoes;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private RespostaSocket respostaComando;

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

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        // Pedir confirmação
        System.exit(0);
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
    void jfxbAlterarImagemDireitaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAlterarImagemEsquerdaOnAction(ActionEvent event) {

    }

    /**
     * Método que altera o texto inferior do placar eletrônico, fazendo as
     * verificações de acentuação e etc.
     *
     * @param event MouseEvent.
     */
    @FXML
    void jfxbAlterarTextoInferiorOnAction(ActionEvent event) {
        String textoInicial = jfxtfTextoInferior.getText();
        String textoAjustado = textoInicial.replaceAll("[^A-Za-z0-9 ]", "");

        if (jfxtfTextoInferior.getText().trim().isEmpty() || !textoInicial.equals(textoAjustado)) {
            trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.TEXTO_INFERIOR, "alterar", jfxtfTextoInferior.getText());

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", "#09a104", jfxtfTextoInferiorL);
                } else {
                    trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbExibirEscalacoesOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbExibirImagemVideoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbPararEscalacoesOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbPararImagemVideoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarImagemDireitaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarImagemEsquerdaOnAction(ActionEvent event) {

    }

    /**
     * Método que restaura o texto inferior do placar eletrônico.
     *
     * @param event MouseEvent.
     */
    @FXML
    void jfxbRestaurarTextoInferiorOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.TEXTO_INFERIOR, "restaurar");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", "white", jfxtfTextoInferiorL);
                jfxtfTextoInferior.setText("");
            } else {
                trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxtfEscalacoesOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfImagemDireitaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfImagemEsquerdaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfImagemVideoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfTextoInferiorOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
