package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GerenciarUsuariosController implements Initializable {

    @FXML
    private Label lStatus;

    @FXML
    private JFXListView<?> jfxlvLista;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

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

    @FXML
    void jfxbExcluirUsuarioOnClick(MouseEvent event) {
        try {
            String usuario = (String) jfxlvLista.getSelectionModel().getSelectedItem();
            boolean confirmacao = Utils.telaConfirmacao("Exclusão de usuário", "Exclusão de usuário", "Deseja excluir o usuario " + usuario + "?\n"
                    + "Esta operação não pode ser desfeita.", Alert.AlertType.WARNING);

            if (confirmacao) {
                RespostaSocket resposta = PlacarClient.enviarComando(Comando.CADASTRO_USUARIO, "delete", usuario);

                if (resposta == RespostaSocket.COMANDO_ACEITO) {
                    updateList("Usuário " + usuario + " foi excluído.");
                } else {
                    lStatus.setText("Ocorreu um erro na exclusão.");
                }
            }
        } catch (IOException ex) {
            // implementar log
            lStatus.setText("Erro: " + ex.getMessage());
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
                updateList("Usuário " + usuario + " teve a senha atualizada.");
            } else {
                lStatus.setText("Ocorreu um erro na troca de senha.");
            }
        } catch (IOException ex) {
            // implementar log
            lStatus.setText("Erro: " + ex.getMessage());
        }
    }

    private void updateList(String mensagem) {
        try {
            Date d = new Date();
            String resposta = PlacarClient.enviarComandoResp(Comando.CADASTRO_USUARIO, "get", "all");
            String[] usuarios = resposta.split(",");
            ObservableList dados = FXCollections.observableArrayList();

            for (String s : usuarios) {
                dados.add(s);
            }

            jfxlvLista.setItems(dados);

            if (usuarios.length != 0) {
                jfxlvLista.getSelectionModel().select(0);
            }

            if (mensagem == null) {
                lStatus.setText("Lista atualizada: " + d);
            } else {
                lStatus.setText(mensagem);
            }
        } catch (IOException ex) {
            // implementar log
            lStatus.setText("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateList(null);
    }
}
