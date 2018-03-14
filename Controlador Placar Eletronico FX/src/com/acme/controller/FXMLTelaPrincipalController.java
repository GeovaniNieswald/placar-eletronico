package com.acme.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

;

/**
 *
 * @author Geovani Nieswald
 */
public class FXMLTelaPrincipalController implements Initializable {

    @FXML
    private Label lCronometro;

    @FXML
    private Button bIniciarCronometro;

    @FXML
    private Button bPausarCronometro;

    @FXML
    private Button bZerarCronometro;

    @FXML
    private Label lPeriodo;

    @FXML
    private Button bAumentarPeriodo;

    @FXML
    private Button bDiminuirPeriodo;

    @FXML
    private Button bZerarPeriodo;

    @FXML
    private Label lPontosTimeA;

    @FXML
    private Button bAumentarPontosTimeA;

    @FXML
    private Button bDiminuirPontosTimeA;

    @FXML
    private Button bZerarPontos;

    @FXML
    private Button bDiminuirPontosTimeB;

    @FXML
    private Button bAumentarPontosTimeB;

    @FXML
    private Label lPontosTimeB;

    @FXML
    private Label lFaltasSetsTimeA;

    @FXML
    private Button bAumentarFaltasSetsTimeA;

    @FXML
    private Button bDiminuirFaltasSetsTimeA;

    @FXML
    private Button bZerarFaltasSets;

    @FXML
    private Button bDiminuirFaltasSetsTimeB;

    @FXML
    private Button bAumentarFaltasSetsTimeB;

    @FXML
    private Label lFaltasSetsTimeB;

    @FXML
    private TextField tfPropaganda;

    @FXML
    private Button bAlterarPropaganda;

    @FXML
    private Button bResetarPropaganda;

    @FXML
    private Button bZerarTudo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
