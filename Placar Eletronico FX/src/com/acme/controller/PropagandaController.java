package com.acme.controller;

import com.acme.PlacarServer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

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

    private void linhaDoTempo(String tipo, File file, String jogTimeLocal, String jogTimeVisitante) {
        Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            switch (tipo) {
                case "imagem":
                    Image img = new Image(file.toURI().toString());
                    BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, false);
                    BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                    Background background = new Background(backgroundImage);
                    vbPropaganda.setBackground(background);

                    break;
                case "video":
                    if (file == null) {
                        mvVideo.getMediaPlayer().stop();
                    } else {
                        Media media = new Media(file.toURI().toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        mvVideo.setFitHeight(vbPropaganda.getHeight());
                        mvVideo.setFitWidth(vbPropaganda.getWidth());
                        mvVideo.setMediaPlayer(mediaPlayer);

                        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                            mediaPlayer.stop();
                        }

                        mediaPlayer.play();
                    }

                    break;
                case "escalacao-exibir":
                    lTimeLocal.setText("Escalação Time Local");
                    lTituloTimeLocal.setText("Posição     -     Número     -     Jogador");
                    lEscalacaoTimeLocal.setText(jogTimeLocal);

                    lTimeVisitante.setText("Escalação Time Visitante");
                    lTituloTimeVisitante.setText("Posição     -     Número     -     Jogador");
                    lEscalacaoTimeVisitante.setText(jogTimeVisitante);

                    break;
                case "escalacao-parar":
                    lTituloTimeLocal.setText("");
                    lTituloTimeVisitante.setText("");
                    lTimeLocal.setText("");
                    lTimeVisitante.setText("");
                    lEscalacaoTimeLocal.setText("");
                    lEscalacaoTimeVisitante.setText("");
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tl.play();
    }

    public void exibirPropagandaImagem(File file) {
        linhaDoTempo("imagem", file, "", "");
    }

    public void exibirPropagandaVideo(File file) {
        linhaDoTempo("video", file, "", "");
    }

    public void pararPropagandaVideo() {
        linhaDoTempo("video", null, "", "");
    }

    public void exibirEscalacao(String escalacao) {
        String[] times = escalacao.split("§");
        String[] jogadoresTimeLocal = times[0].split(">");
        String[] jogadoresTimeVisitante = times[1].split(">");
        String jogTimeLocal = "";
        String jogTimeVisitante = "";

        for (int i = 0; i < jogadoresTimeLocal.length; i++) {
            jogadoresTimeLocal[i] = jogadoresTimeLocal[i].replace("-", "    -    ");
            jogTimeLocal += jogadoresTimeLocal[i] + "\n\n";
        }

        for (int i = 0; i < jogadoresTimeVisitante.length; i++) {
            jogadoresTimeVisitante[i] = jogadoresTimeVisitante[i].replace("-", "    -    ");
            jogTimeVisitante += jogadoresTimeVisitante[i] + "\n\n";
        }

        linhaDoTempo("escalacao-exibir", null, jogTimeLocal, jogTimeVisitante);
    }

    public void pararEscalacao() {
        linhaDoTempo("escalacao-parar", null, "", "");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlacarServer.instanciaPropagandaController(this);
    }
}
