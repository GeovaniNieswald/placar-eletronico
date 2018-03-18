package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.RespostaSocket;
import com.acme.model.Tela;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class ConexaoController implements Initializable {

    @FXML
    private TextField tfEndereco;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfSenha;

    @FXML
    private Button bConectar;

    @FXML
    void bConectarAction(ActionEvent event) {
        Timeline conexao = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                RespostaSocket respostaConexao = PlacarClient.conectar(tfEndereco.getText(), tfLogin.getText(), tfSenha.getText());

                switch (respostaConexao) {
                    case CONEXAO_ACEITA_USUARIO_PRINCIPAL:
                        MainApp.trocarTela(Tela.ESPORTE);
                        break;
                    case CONEXAO_ACEITA_USUARIO_PROPAGANDA:
                        MainApp.trocarTela(Tela.ESPORTE); // vai ser outra tela
                        break;
                    default:
                        Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                        dialogoErro.setTitle("Ops");
                        dialogoErro.setHeaderText("Login ou Senha inválido");
                        dialogoErro.setContentText("Tente novamente!");
                        dialogoErro.show();
                        tfLogin.setText("");
                        tfSenha.setText("");
                }
            } catch (IOException ex) {
                System.out.println("Aconteceu algum erro na conexão");
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        conexao.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
