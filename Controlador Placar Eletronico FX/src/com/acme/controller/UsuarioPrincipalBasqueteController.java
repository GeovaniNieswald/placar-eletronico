package com.acme.controller;

import com.acme.MainApp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class UsuarioPrincipalBasqueteController implements Initializable {

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
    private JFXButton jfxbRestaurarNomeTimeVisitante;

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
    private JFXRadioButton jfxrbPosseLocal;

    @FXML
    private ToggleGroup tgPosse;

    @FXML
    private JFXRadioButton jfxrbPosseVisitante;

    @FXML
    private JFXCheckBox jfxcbBonusLocal;

    @FXML
    private JFXCheckBox jfxcbBonusVisitante;

    @FXML
    private Label lPontosTimeLocal;

    @FXML
    private JFXButton jfxbAumentar1PontoTimeLocal;

    @FXML
    private JFXButton jfxbAumentar2PontoTimeLocal;

    @FXML
    private JFXButton jfxbAumentar3PontoTimeLocal;

    @FXML
    private JFXButton jfxbDiminuir1PontoTimeLocal;

    @FXML
    private JFXButton jfxbDiminuir2PontoTimeLocal;

    @FXML
    private JFXButton jfxbDiminuir3PontoTimeLocal;

    @FXML
    private Label lPontosTimeVisitante;

    @FXML
    private JFXButton jfxbAumentar1PontoTimeVisitante;

    @FXML
    private JFXButton jfxbAumentar2PontoTimeVisitante;

    @FXML
    private JFXButton jfxbAumentar3PontoTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuir1PontoTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuir2PontoTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuir3PontoTimeVisitante;

    @FXML
    private JFXButton jfxbZerarPontos;

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
    private JFXTextField jfxtfJogador;

    @FXML
    private JFXTextField jfxtfFaltas;

    @FXML
    private JFXButton jfxbAlterarUltimoJogador;

    @FXML
    private JFXButton jfxbRestaurarUltimoJogador;

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
    void jfxbAlterarUltimoJogadorOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentar1PontoTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentar1PontoTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentar2PontoTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentar2PontoTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentar3PontoTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentar3PontoTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarFaltasTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarFaltasTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentarPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuir1PontoTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuir1PontoTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuir2PontoTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuir2PontoTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuir3PontoTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuir3PontoTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirFaltasTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirFaltasTimeVisitanteOnAction(ActionEvent event) {

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
    void jfxbRestaurarNomeTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarNomeTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarTudoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarUltimoJogadorOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbZerarFaltasOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbZerarPontosOnAction(ActionEvent event) {

    }

    @FXML
    void jfxcbBonusLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxcbBonusVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxrbPosseLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxrbPosseVisitanteOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
