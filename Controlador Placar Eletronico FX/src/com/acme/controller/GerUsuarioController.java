package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private JFXButton jfxbExcluirUser;

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        MainApp.trocarCena(Cena.ESPORTE);
    }

    @FXML
    void jfxbExcluirUserOnClick(MouseEvent event) {
        //detecta qual é o registro selecionado
        //pede confirmação
        //envia comando de exclusão
        //no retorno atualiza o label
        //atualiza a grid
    }

    @FXML
    void jfxbTrocaSenhaOnClick(MouseEvent event) {
        //detecta qual é o registro selecionado
        //mostra prompt para informar a nova senha (não pede a antiga, pois está na mão do admin)
        //envia a senha nova
        //no retorno atualiza o label
        //atualiza a grid
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String resp = PlacarClient.enviarComandoResp(Comando.CADASTRO_USUARIO, "get", "all");
            String[] users = resp.split(",");
            ObservableList data = FXCollections.observableArrayList();
            for (String s : users) {
                data.add(s);
            }
            jfxList.setItems(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
