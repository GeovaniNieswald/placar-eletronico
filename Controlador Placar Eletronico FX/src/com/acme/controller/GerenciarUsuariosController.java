package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.MeuLogger;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class GerenciarUsuariosController implements Initializable {

    @FXML
    private JFXListView<?> jfxlvLista;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private void atualizarLista(String mensagem) {
        try {
            String resposta = PlacarClient.retornarUsuarios();

            if (resposta.contains("not-ok")) {
                Utils.telaMensagem("Ops", "", "Não foi possível atualziar a lista!", Alert.AlertType.WARNING);
            } else {
                String[] usuarios = resposta.split(",");
                ObservableList dados = FXCollections.observableArrayList();

                dados.addAll(Arrays.asList(usuarios));

                jfxlvLista.setItems(dados);

                if (usuarios.length != 0) {
                    jfxlvLista.getSelectionModel().select(0);
                }

                if (mensagem != null) {
                    Utils.telaMensagem("Ok", "", mensagem, Alert.AlertType.INFORMATION);
                }
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.SEVERE, "Erro de conexão.", ex);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução e reinicie o programa!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void ovMinimizarOnMouseClicked(MouseEvent event) {
        MainApp.minimizar();
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        PlacarClient.desconectar();
        MainApp.trocarCena(Cena.CONEXAO);
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
    void jfxbExcluirUsuarioOnClick(MouseEvent event) {
        try {
            String usuario = (String) jfxlvLista.getSelectionModel().getSelectedItem();

            boolean confirmacao = Utils.telaConfirmacao("Exclusão de usuário", "Exclusão de usuário", "Deseja excluir o usuario " + usuario + "?\nEsta operação não pode ser desfeita.", Alert.AlertType.WARNING);

            if (confirmacao) {
                RespostaSocket resposta = PlacarClient.enviarComando(Comando.CADASTRO_USUARIO, "delete", usuario);

                if (resposta == RespostaSocket.COMANDO_ACEITO) {
                    atualizarLista("Usuário " + usuario + " foi excluído.");
                } else {
                    Utils.telaMensagem("Ops", "", "Ocorreu um erro na exclusão!", Alert.AlertType.WARNING);
                }
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.SEVERE, "Erro de conexão.", ex);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void jfxbNovoUsuarioOnClick(MouseEvent event) {
        MainApp.trocarCena(Cena.CADASTRO_USUARIO);
    }

    @FXML
    void jfxbTrocarSenhaUsuarioOnClick(MouseEvent event) {
        try {
            String usuario = (String) jfxlvLista.getSelectionModel().getSelectedItem();
            Optional<String> senha = Utils.telaPrompt("Trocar senha", "Trocar senha do usuario " + usuario, "Nova senha:");
            RespostaSocket resposta = PlacarClient.enviarComando(Comando.CADASTRO_USUARIO, "update", usuario, senha.get());

            if (resposta == RespostaSocket.COMANDO_ACEITO) {
                atualizarLista("Usuário " + usuario + " teve a senha atualizada.");
            } else {
                Utils.telaMensagem("Ops", "", "Ocorreu um erro na troca de senha!", Alert.AlertType.WARNING);
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.SEVERE, "Erro de conexão.", ex);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução e reinicie o programa!", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarLista(null);
    }
}
