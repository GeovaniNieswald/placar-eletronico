package com.acme.controller;

import com.acme.MainApp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class UsuarioPropagandaController implements Initializable {

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXTextField jfxtfTextoInferior;

    @FXML
    private JFXButton jfxbAlterarTextoInferior;

    @FXML
    private JFXButton jfxbRestaurarTextoInferior;

    @FXML
    private JFXTextField jfxtfImagemDireita;

    @FXML
    private JFXButton jfxbAlterarImagemDireita;

    @FXML
    private JFXButton jfxbRestaurarImagemDireita;

    @FXML
    private JFXTextField jfxtfImagemEsquerda;

    @FXML
    private JFXButton jfxbAlterarImagemEsquerda;

    @FXML
    private JFXButton jfxbRestaurarImagemEsquerda;

    @FXML
    private JFXTextField jfxtfImagemVideo;

    @FXML
    private JFXButton jfxbExibirImagemVideo;

    @FXML
    private JFXButton jfxbPararImagemVideo;

    @FXML
    private JFXTextField jfxtfEscalacoes;

    @FXML
    private JFXButton jfxbExibirEscalacoes;

    @FXML
    private JFXButton jfxbPararEscalacoes;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - xOffset, event.getScreenY() - yOffset);
    }

    @FXML
    void gpOnMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void jfxbAlterarImagemDireitaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAlterarImagemEsquerdaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAlterarTextoInferiorOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbExibirEscalacoesOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbExibirImagemVideoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbPararEscalacoesOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbPararImagemVideoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarImagemDireitaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarImagemEsquerdaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarTextoInferiorOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfEscalacoesOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfImagemDireitaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfImagemEsquerdaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfImagemVideoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxtfTextoInferiorOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
