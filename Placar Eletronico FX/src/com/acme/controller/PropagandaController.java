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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class PropagandaController implements Initializable {

    @FXML
    private VBox vbPropaganda;

    @FXML
    private MediaView mvVideo;

    private void linhaDoTempoVBox(String tipo, File file) {
        if (tipo.equals("imagem")) {
            Timeline tlVBox = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                Image image = new Image(file.toURI().toString());
                BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, false);
                BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                Background background = new Background(backgroundImage);

                vbPropaganda.setBackground(background);
            }),
                    new KeyFrame(Duration.seconds(1))
            );
            tlVBox.play();
        } else {
            Timeline tlMediaView = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                Media media = new Media(file.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mvVideo.setFitHeight(vbPropaganda.getHeight());
                mvVideo.setFitWidth(vbPropaganda.getWidth());
                mvVideo.setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
                mediaPlayer.play();
            }),
                    new KeyFrame(Duration.seconds(1))
            );
            tlMediaView.play();
        }
    }

    public void exibirPropagandaImagem(File file) {
        linhaDoTempoVBox("imagem", file);
    }

    public void exibirPropagandaVideo(File file) {
        linhaDoTempoVBox("video", file);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlacarServer.instanciaPropagandaController(this);
    }
}
