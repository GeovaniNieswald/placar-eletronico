package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.acme.model.Cena;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Classe Referente ao controlador da cena de espera.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class EsperaController implements Initializable {

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private FontAwesomeIconView faivCadastroUsuario;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private Timeline esperandoUsuarioPrincipal = new Timeline();

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
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        PlacarClient.desconectar();
        MainApp.trocarCena(Cena.CONEXAO);
        esperandoUsuarioPrincipal.stop();
    }

    // Fazer a verificação
    @FXML
    void faivCadastroUsuarioOnMouseClicked(MouseEvent event) {
        MainApp.trocarCena(Cena.GERENCIAR_USUARIOS);
    }

    /**
     * Método acionado quando a cena associada a este controlador é iniciada,
     * ele fica em um loop esperando a conexão de um usuário principal, quando
     * este se conecta, é exibida a tela de controle de propaganda.
     *
     * @param url URL.
     * @param rb ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        esperandoUsuarioPrincipal = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                RespostaSocket respostaUsuarioPrincipal = PlacarClient.enviarComando(Comando.VERIFICAR_USUARIO_PRINCIPAL);

                if (respostaUsuarioPrincipal == RespostaSocket.USUARIO_PRINCIPAL_CONECTADO) {
                    MainApp.trocarCena(Cena.USUARIO_PROPAGANDA);
                    esperandoUsuarioPrincipal.stop();
                }
            } catch (IOException ex) {
                // Informar erro de conexão
                // IMPLEMENTAR LOG
            }
        }),
                new KeyFrame(Duration.seconds(5))
        );
        esperandoUsuarioPrincipal.setCycleCount(Animation.INDEFINITE);
        esperandoUsuarioPrincipal.play();
    }
}
