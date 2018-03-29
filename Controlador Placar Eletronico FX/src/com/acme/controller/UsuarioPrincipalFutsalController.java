package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Classe Referente ao controlador da cena de futsal.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class UsuarioPrincipalFutsalController implements Initializable {

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXTextField jfxtfNomeTimeLocal;

    @FXML
    private JFXButton jfxbAlterarNomeTimeLocal;

    @FXML
    private JFXButton jfxbRestaurarNomeTimeLocal;

    @FXML
    private JFXTextField jfxtfNomeTimeVisitante;

    @FXML
    private JFXButton jfxbAlterarNomeTimeVisitantejfxbAlterarNomeTimeVisitante;

    @FXML
    private JFXButton jfxbRestaurarImagemDireita;

    @FXML
    private JFXTextField jfxtfCronometro;

    @FXML
    private JFXButton jfxbIniciarCronometro;

    @FXML
    private JFXButton jfxbPausarCronometro;

    @FXML
    private JFXButton jfxbRestaurarCronometro;

    @FXML
    private JFXTextField jfxtfPeriodo;

    @FXML
    private JFXButton jfxbAumentarPeriodo;

    @FXML
    private JFXButton jfxbDiminuirPeriodo;

    @FXML
    private JFXButton jfxbRestaurarPeriodo;

    @FXML
    private Label lGolsTimeLocal;

    @FXML
    private JFXButton jfxbAumentarGolsTimeLocal;

    @FXML
    private JFXButton jfxbDiminuirGolsTimeLocal;

    @FXML
    private Label lGolsTimeVisitante;

    @FXML
    private JFXButton jfxbAumentarGolsTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuirGolsTimeVisitante;

    @FXML
    private JFXButton jfxbZerarGols;

    @FXML
    private Label lFaltasTimeLocal;

    @FXML
    private JFXButton jfxbAumentarFaltasTimeLocal;

    @FXML
    private JFXButton jfxbDiminuirFaltasTimeLocal;

    @FXML
    private Label lFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbAumentarFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuirFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbZerarFaltas;

    @FXML
    private JFXButton jfxbRestaurarTudo;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private RespostaSocket respostaComando;
    
    private int somador;

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        // Pedir confirmação
        MainApp.trocarCena(Cena.ESPORTE);
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
    void jfxbAlterarNomeTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAlterarNomeTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarFaltasTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarFaltasTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarGolsTimeLocalOnAction(ActionEvent event) {
        try {
        
            respostaComando = PlacarClient.enviarComando(Comando.SOMAR,"mais", "1");
            somar("1");
            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
            } else {

            }
        } catch (IOException ex) {
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentarGolsTimeVisitante(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirFaltasTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirFaltasTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirGolsTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirGolsTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbIniciarCronometroOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbPausarCronometroOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarCronometroOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarImagemDireitaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarNomeTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarTudoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbZerarFaltasOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbZerarGolsOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void somar(String texto){
        Integer x = Integer.valueOf(texto);
        somador += x;
        String teste = Integer.toString(somador);
        
        Timeline pao = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            lGolsTimeLocal.setText(teste);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        pao.play();
        
    }
}
