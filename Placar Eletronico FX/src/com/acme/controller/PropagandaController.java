package com.acme.controller;

import com.acme.PlacarServer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

public class PropagandaController implements Initializable {

    @FXML
    private VBox vbPropaganda;

    @FXML
    private MediaView mvVideo;

    @FXML
    private Label lTituloTimeLocal;

    @FXML
    private Label lTituloTimeVisitante;

    @FXML
    private Label lTimeLocal;

    @FXML
    private Label lTimeVisitante;

    @FXML
    private Label lEscalacaoTimeLocal;

    @FXML
    private Label lEscalacaoTimeVisitante;

    public void exibirPropagandaImagem(File file) {
        Platform.runLater(() -> {
            Image img = new Image(file.toURI().toString());
            BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, false);
            BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            vbPropaganda.setBackground(background);
        });
    }

    public void exibirPropagandaVideo(File file) {
        Platform.runLater(() -> {
            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mvVideo.setFitHeight(vbPropaganda.getHeight());
            mvVideo.setFitWidth(vbPropaganda.getWidth());
            mvVideo.setMediaPlayer(mediaPlayer);

            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }

            mediaPlayer.play();
        });
    }

    public void pararPropagandaVideo() {
        Platform.runLater(() -> mvVideo.getMediaPlayer().stop());
    }

    public void exibirEscalacoes(String escalacao) {
        String[] escalacoes = escalacao.split("§");
        String[] jogadoresTimeLocal = escalacoes[0].split(">");
        String[] jogadoresTimeVisitante = escalacoes[1].split(">");
        String escalacaoTimeLocal = "";
        String escalacaoTimeVisitante = "";

        for (int i = 0; i < jogadoresTimeLocal.length; i++) {
            jogadoresTimeLocal[i] = jogadoresTimeLocal[i].replace("-", "    -    ");
            escalacaoTimeLocal += jogadoresTimeLocal[i] + "\n\n";
        }

        for (int i = 0; i < jogadoresTimeVisitante.length; i++) {
            jogadoresTimeVisitante[i] = jogadoresTimeVisitante[i].replace("-", "    -    ");
            escalacaoTimeVisitante += jogadoresTimeVisitante[i] + "\n\n";
        }

        final String ESCALACAO_TIME_LOCAL = escalacaoTimeLocal;
        final String ESCALACAO_TIME_VISITANTE = escalacaoTimeVisitante;

        Platform.runLater(() -> {
            lTimeLocal.setText("Escalação Time Local");
            lTituloTimeLocal.setText("Posição     -     Número     -     Jogador");
            lEscalacaoTimeLocal.setText(ESCALACAO_TIME_LOCAL);

            lTimeVisitante.setText("Escalação Time Visitante");
            lTituloTimeVisitante.setText("Posição     -     Número     -     Jogador");
            lEscalacaoTimeVisitante.setText(ESCALACAO_TIME_VISITANTE);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlacarServer.instanciaPropagandaController(this);
    }
}
