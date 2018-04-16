package com.acme.controller;

import com.acme.PlacarServer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PropagandaController implements Initializable {

    @FXML
    private VBox vbPropaganda;

    private void linhaDoTempoVBox(File file) {
        Image image = new Image(file.toURI().toString());
        BackgroundSize backgroundSize = new BackgroundSize(vbPropaganda.getWidth(), vbPropaganda.getHeight(), false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Timeline tlVBox = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            vbPropaganda.setBackground(background);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tlVBox.play();
    }

    public void exibirPropagandaImagem(File file) {
        linhaDoTempoVBox(file);
    }

    public void exibirPropagandaVideo(File file) {
        linhaDoTempoVBox(file);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlacarServer.instanciaPropagandaController(this);
    }
}
