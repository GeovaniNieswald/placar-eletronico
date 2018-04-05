package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.acme.model.Cena;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXRadioButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Classe Referente ao controlador da cena de escolha de esporte.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class EsporteController implements Initializable {

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXRadioButton jfxrbBasquete;

    @FXML
    private ToggleGroup tgEsportes;

    @FXML
    private JFXRadioButton jfxrbFutsal;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    /**
     * Método para escolher o esporte do placar eletrônico.
     *
     */
    private void escolher() {
        try {
            RespostaSocket respostaEsporte;

            if (jfxrbBasquete.isSelected()) {
                respostaEsporte = PlacarClient.enviarComando(Comando.ESCOLHER_ESPORTE, "basquete");
            } else {
                respostaEsporte = PlacarClient.enviarComando(Comando.ESCOLHER_ESPORTE, "futsal");
            }

            switch (respostaEsporte) {
                case ESPORTE_ACEITO_BASQUETE:
                    MainApp.trocarCena(Cena.USUARIO_PRINCIPAL_BASQUETE);
                    break;
                case ESPORTE_ACEITO_FUTSAL:
                    MainApp.trocarCena(Cena.USUARIO_PRINCIPAL_FUTSAL);
                    break;
                default:
                // Falar que não foi possível escolher esporte
                // IMPLEMENTAR LOG
            }
        } catch (IOException ex) {
            // Mostrar erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        PlacarClient.desconectar();
        MainApp.trocarCena(Cena.CONEXAO);
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
    void jfxbOkOnAction(ActionEvent event) {
        escolher();
    }

    @FXML
    void jfxbOkOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            escolher();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
