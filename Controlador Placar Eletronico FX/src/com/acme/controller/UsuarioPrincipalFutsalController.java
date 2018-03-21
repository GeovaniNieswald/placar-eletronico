package com.acme.controller;

import com.acme.MainApp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class UsuarioPrincipalFutsalController implements Initializable {

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXTextField jfxtfNomeTimeLocal;

    @FXML
    private JFXButton jfxbAlterarNomeTimeLocal;

    @FXML
    private JFXButton jfxbRestaurarNomeTimeLocal;

    @FXML
    private JFXTextField jfxtfNomeTimeVisitante;

    @FXML
    private JFXButton jfxbAlterarNomeTimeVisitantejfxbAlterarNomeTimeVisitante;

    @FXML
    private JFXButton jfxbRestaurarImagemDireita;

    @FXML
    private JFXTextField jfxtfCronometro;

    @FXML
    private JFXButton jfxbIniciarCronometro;

    @FXML
    private JFXButton jfxbPausarCronometro;

    @FXML
    private JFXButton jfxbRestaurarCronometro;

    @FXML
    private JFXTextField jfxtfPeriodo;

    @FXML
    private JFXButton jfxbAumentarPeriodo;

    @FXML
    private JFXButton jfxbDiminuirPeriodo;

    @FXML
    private JFXButton jfxbRestaurarPeriodo;

    @FXML
    private Label lGolsTimeLocal;

    @FXML
    private JFXButton jfxbAumentarGolsTimeLocal;

    @FXML
    private JFXButton jfxbDiminuirGolsTimeLocal;

    @FXML
    private Label lGolsTimeVisitante;

    @FXML
    private JFXButton jfxbAumentarGolsTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuirGolsTimeVisitante;

    @FXML
    private JFXButton jfxbZerarGols;

    @FXML
    private Label lFaltasTimeLocal;

    @FXML
    private JFXButton jfxbAumentarFaltasTimeLocal;

    @FXML
    private JFXButton jfxbDiminuirFaltasTimeLocal;

    @FXML
    private Label lFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbAumentarFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuirFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbZerarFaltas;

    @FXML
    private JFXButton jfxbRestaurarTudo;

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
    void jfxbAlterarNomeTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAlterarNomeTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarFaltasTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarFaltasTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarGolsTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarGolsTimeVisitante(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirFaltasTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirFaltasTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirGolsTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirGolsTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbIniciarCronometroOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbPausarCronometroOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarCronometroOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarImagemDireitaOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarNomeTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarTudoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbZerarFaltasOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbZerarGolsOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
