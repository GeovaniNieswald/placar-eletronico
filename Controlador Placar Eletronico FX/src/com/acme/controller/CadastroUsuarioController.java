package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class CadastroUsuarioController implements Initializable {

    @FXML
    private JFXTextField jfxtfUsuario;

    @FXML
    private JFXPasswordField jfxpfSenha;

    @FXML
    private JFXCheckBox jfxcbAdministrador;

    @FXML
    private JFXCheckBox jfxcbPlacar;

    @FXML
    private JFXCheckBox jfxcbPropaganda;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

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
        if (jfxtfUsuario.getText().trim().isEmpty() || jfxpfSenha.getText().trim().isEmpty()) {
            Utils.telaMensagem("Usuário e senha devem estar preenchidos!", Alert.AlertType.WARNING);
        } else {
            if (jfxtfUsuario.getText().contains(",") || jfxtfUsuario.getText().contains(";")) {
                Utils.telaMensagem("Nome de usuário não pode conter vírgula nem ponto-e-vírgula", Alert.AlertType.WARNING);
            } else {
                String usuario = jfxtfUsuario.getText();
                String senha = jfxpfSenha.getText();
                String usuarioAdministrador = String.valueOf(jfxcbAdministrador.isSelected());
                String usuarioPlacar = String.valueOf(jfxcbPlacar.isSelected());
                String usuarioPropaganda = String.valueOf(jfxcbPropaganda.isSelected());

                try {
                    RespostaSocket resposta = PlacarClient.enviarComando(Comando.CADASTRO_USUARIO, "add", usuario, senha, usuarioAdministrador, usuarioPlacar, usuarioPropaganda);

                    switch (resposta) {
                        case COMANDO_ACEITO:
                            Utils.telaMensagem("Usuário salvo!", Alert.AlertType.INFORMATION);
                            jfxtfUsuario.setText("");
                            jfxpfSenha.setText("");
                            jfxcbAdministrador.setSelected(true);
                            break;
                        case USUARIO_JA_EXISTE:
                            Utils.telaMensagem("Usuário já existe!", Alert.AlertType.WARNING);
                            break;
                        default:
                            Utils.telaMensagem("Não foi possível cadastrar o usuário!", Alert.AlertType.WARNING);
                    }
                } catch (IOException ex) {
                    // Mostrar erro de conexão
                    // IMPLEMENTAR LOG
                }
            }
        }
    }

    @FXML
    void jfxcbAdministradorOnAction(ActionEvent event) {
        jfxcbPlacar.setSelected(false);
        jfxcbPropaganda.setSelected(false);
    }

    @FXML
    void jfxcbPlacarOnAction(ActionEvent event) {
        jfxcbAdministrador.setSelected(false);
        jfxcbPropaganda.setSelected(false);
    }

    @FXML
    void jfxcbPropagandaOnAction(ActionEvent event) {
        jfxcbAdministrador.setSelected(false);
        jfxcbPlacar.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
