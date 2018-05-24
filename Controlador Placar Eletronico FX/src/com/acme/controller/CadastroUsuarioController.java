package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.MeuLogger;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

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

    @FXML
    void gpOnMousePressed(MouseEvent event) {
        posicaoInicialX = event.getSceneX();
        posicaoInicialY = event.getSceneY();
    }

    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - posicaoInicialX, event.getScreenY() - posicaoInicialY);
    }

    @FXML
    void jfxbSalvarOnAction(ActionEvent event) {
        if (jfxtfUsuario.getText().trim().isEmpty() || jfxpfSenha.getText().trim().isEmpty()) {
            Utils.telaMensagem("Ops", "", "Usuário e senha devem estar preenchidos!", Alert.AlertType.WARNING);
        } else {
            if (jfxtfUsuario.getText().contains(",") || jfxtfUsuario.getText().contains(";")) {
                Utils.telaMensagem("Ops", "", "Nome de usuário não pode conter vírgula nem ponto-e-vírgula!", Alert.AlertType.WARNING);
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
                            Utils.telaMensagem("Ok", "", "Usuário salvo!", Alert.AlertType.INFORMATION);
                            jfxtfUsuario.setText("");
                            jfxpfSenha.setText("");
                            jfxcbAdministrador.setSelected(true);
                            break;
                        case USUARIO_JA_EXISTE:
                            Utils.telaMensagem("Ops", "", "Usuário já existe!", Alert.AlertType.WARNING);
                            break;
                        default:
                            Utils.telaMensagem("Ops", "", "Não foi possível cadastrar o usuário!", Alert.AlertType.WARNING);
                    }
                } catch (IOException ex) {
                    MeuLogger.logException(Level.SEVERE, "Erro de conexão.", ex);
                    Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
