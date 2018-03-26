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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    @FXML
    private JFXButton jfxbOk;

    private double xOffset = 0;
    private double yOffset = 0;

    private void escolher() {
        try {
            RespostaSocket respostaEsporte;

            if (jfxrbBasquete.isSelected()) {
                respostaEsporte = PlacarClient.enviarComando(Comando.ESCOLHER_ESPORTE_BASQUETE);
            } else {
                respostaEsporte = PlacarClient.enviarComando(Comando.ESCOLHER_ESPORTE_FUTSAL);
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
        MainApp.trocarCena(Cena.CONEXAO);
    }

    @FXML
    void gpOnMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - xOffset, event.getScreenY() - yOffset);
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

    @FXML
    void onMouseClickedbtnNovoUsuario(MouseEvent event) {
        MainApp.trocarCena(Cena.CAD_USUARIO);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
