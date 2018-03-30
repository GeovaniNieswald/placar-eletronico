package com.acme.controller;

import com.acme.PlacarServer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class PlacarController implements Initializable {

    @FXML
    private Button bFechar;

    @FXML
    private Label lTextoInferior;
    
    @FXML
    private Label jfxlNomeTimeLocal;
    
    public void setAlterarNomeTimeLocal(String texto){
        jfxlNomeTimeLocal.setText(texto);
    }

    public void setTextoInferior(String texto) {
        Timeline textoInferior = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            lTextoInferior.setText(texto);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        textoInferior.play();
    }
    public void somar (String texto){
      //codigo Celmer  
    }

    @FXML
    void bFecharAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlacarServer.instanciaPlacarController(this);
    }

}
