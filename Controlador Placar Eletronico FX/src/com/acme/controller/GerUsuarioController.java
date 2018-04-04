package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

public class GerUsuarioController implements Initializable {

    @FXML
    private JFXListView<?> jfxList;

    @FXML
    private Label jfxlStatus;

    @FXML
    private JFXButton jfxbTrocaSenha;

    @FXML
    private JFXButton jfxbNovoUser;

    @FXML
    private JFXButton jfxbExcluirUser;

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        MainApp.trocarCena(Cena.ESPORTE);
    }

    @FXML
    void jfxbNovoUserOnClick(MouseEvent event) {
        MainApp.trocarCena(Cena.CAD_USUARIO);
    }

    @FXML
    void jfxbExcluirUserOnClick(MouseEvent event) {
        try {
            String user = (String) jfxList.getSelectionModel().getSelectedItem();
            boolean conf = Utils.confirm("Exclusão de usuário", "Exclusão de usuário", "Deseja excluir o usuario " + user + "?\n"
                    + "Esta operação não pode ser desfeita.", Alert.AlertType.WARNING);
            if (conf) {
                RespostaSocket resp = PlacarClient.enviarComando(Comando.CADASTRO_USUARIO, "delete", user);
                if (resp == RespostaSocket.COMANDO_ACEITO) {
                    updateList("Usuário " + user + " foi excluído.");
                } else {
                    jfxlStatus.setText("Ocorreu um erro na exclusão.");
                }
            }
        } catch (IOException ex) {
            jfxlStatus.setText("Erro: " + ex.getMessage());
        }
    }

    @FXML
    void jfxbTrocaSenhaOnClick(MouseEvent event) {
        try {
            String user = (String) jfxList.getSelectionModel().getSelectedItem();
            Optional<String> senha = Utils.prompt("Trocar senha", "Trocar senha do usuario " + user, "Nova senha:");
            RespostaSocket resp = PlacarClient.enviarComando(Comando.CADASTRO_USUARIO, "update", user, senha.get());
            if (resp == RespostaSocket.COMANDO_ACEITO) {
                updateList("Usuário " + user + " teve a senha atualizada.");
            } else {
                jfxlStatus.setText("Ocorreu um erro na troca de senha.");
            }
        } catch (IOException ex) {
            jfxlStatus.setText("Erro: " + ex.getMessage());
        }
    }

    private void updateList(String msg) {
        try {
            Date d = new Date();
            String resp = PlacarClient.enviarComandoResp(Comando.CADASTRO_USUARIO, "get", "all");
            String[] users = resp.split(",");
            ObservableList data = FXCollections.observableArrayList();
            for (String s : users) {
                data.add(s);
            }
            jfxList.setItems(data);

            if (msg == null) {
                jfxlStatus.setText("Lista atualizada: " + d);
            } else {
                jfxlStatus.setText(msg);
            }

        } catch (IOException ex) {
            jfxlStatus.setText("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateList(null);
    }

}
