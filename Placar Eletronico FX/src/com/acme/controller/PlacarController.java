package com.acme.controller;

import com.acme.PlacarServer;
import com.acme.model.Cronos;
import de.jensd.fx.glyphs.octicons.OctIconView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class PlacarController implements Initializable {

    @FXML
    private Label lCronometro;

    @FXML
    private Label lNomeTimeLocal;

    @FXML
    private Label lPontosTimeLocal;

    @FXML
    private Label lNomeTimeVisitante;

    @FXML
    private Label lPontosTimeVisitante;

    @FXML
    private Label lFaltasTimeLocal;

    @FXML
    private Label lFaltasTimeVisitante;

    @FXML
    private Label lPeriodo;

    @FXML
    private Label lTextoInferior;

    @FXML
    private OctIconView ovBonusTimeLocal;

    @FXML
    private OctIconView ovBonusTimeVisitante;

    @FXML
    private OctIconView ovPosseTimeVisitante;

    @FXML
    private OctIconView ovPosseTimeLocal;

    @FXML
    private VBox vbImagenEsquerda;

    @FXML
    private VBox vbImagenDireita;

    @FXML
    private Label lNumJogador;

    @FXML
    private Label lFaltasJogador;

    private int pontosTimeLocal;
    private int pontosTimeVisitante;

    private int faltasTimeLocal;
    private int faltasTimeVisitante;

    private boolean primeiraVez = true;
    private boolean executando;
    private Thread t;

    private void linhaDoTempoLabel(Label label, String texto) {
        Timeline tlLabel = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            label.setText(texto);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tlLabel.play();
    }

    private void linhaDoTempoVBox(VBox vBox, File file) {
        Background background;

        if (file == null) {
            BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("black"), CornerRadii.EMPTY, Insets.EMPTY);
            background = new Background(backgroundFill);
        } else {
            Image image = new Image(file.toURI().toString());
            BackgroundSize backgroundSize = new BackgroundSize(vBox.getWidth(), vBox.getHeight(), false, false, false, false);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            background = new Background(backgroundImage);
        }

        Timeline tlVBox = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            vBox.setBackground(background);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tlVBox.play();
    }

    public void setNomeTimeLocal(String texto) {
        linhaDoTempoLabel(lNomeTimeLocal, texto);
    }

    public void setNomeTimeVisitante(String texto) {
        linhaDoTempoLabel(lNomeTimeVisitante, texto);
    }

    public void setTextoInferior(String texto) {
        linhaDoTempoLabel(lTextoInferior, texto);
    }

    public void aumentarPontosTimeLocal(int pontos) {
        pontosTimeLocal += pontos;

        if (pontosTimeLocal > 9) {
            linhaDoTempoLabel(lPontosTimeLocal, pontosTimeLocal + "");
        } else {
            linhaDoTempoLabel(lPontosTimeLocal, "0" + pontosTimeLocal);
        }
    }

    public void diminuirPontosTimeLocal(int pontos) {
        pontosTimeLocal -= pontos;

        if (pontosTimeLocal > 9) {
            linhaDoTempoLabel(lPontosTimeLocal, pontosTimeLocal + "");
        } else {
            linhaDoTempoLabel(lPontosTimeLocal, "0" + pontosTimeLocal);
        }
    }

    public void aumentarPontosTimeVisitante(int pontos) {
        pontosTimeVisitante += pontos;

        if (pontosTimeVisitante > 9) {
            linhaDoTempoLabel(lPontosTimeVisitante, pontosTimeVisitante + "");
        } else {
            linhaDoTempoLabel(lPontosTimeVisitante, "0" + pontosTimeVisitante);
        }
    }

    public void diminuirPontosTimeVisitante(int pontos) {
        pontosTimeVisitante -= pontos;

        if (pontosTimeVisitante > 9) {
            linhaDoTempoLabel(lPontosTimeVisitante, pontosTimeVisitante + "");
        } else {
            linhaDoTempoLabel(lPontosTimeVisitante, "0" + pontosTimeVisitante);
        }
    }

    public void zerarPontos() {
        pontosTimeLocal = 0;
        pontosTimeVisitante = 0;

        linhaDoTempoLabel(lPontosTimeLocal, "00");
        linhaDoTempoLabel(lPontosTimeVisitante, "00");
    }

    public void aumentarFaltasTimeLocal(int faltas) {
        faltasTimeLocal += faltas;

        if (faltasTimeLocal > 9) {
            linhaDoTempoLabel(lFaltasTimeLocal, faltasTimeLocal + "");
        } else {
            linhaDoTempoLabel(lFaltasTimeLocal, "0" + faltasTimeLocal);
        }
    }

    public void diminuirFaltasTimeLocal(int faltas) {
        faltasTimeLocal -= faltas;

        if (faltasTimeLocal > 9) {
            linhaDoTempoLabel(lFaltasTimeLocal, faltasTimeLocal + "");
        } else {
            linhaDoTempoLabel(lFaltasTimeLocal, "0" + faltasTimeLocal);
        }
    }

    public void aumentarFaltasTimeVisitante(int faltas) {
        faltasTimeVisitante += faltas;

        if (faltasTimeVisitante > 9) {
            linhaDoTempoLabel(lFaltasTimeVisitante, faltasTimeVisitante + "");
        } else {
            linhaDoTempoLabel(lFaltasTimeVisitante, "0" + faltasTimeVisitante);
        }
    }

    public void diminuirFaltasTimeVisitante(int faltas) {
        faltasTimeVisitante -= faltas;

        if (faltasTimeVisitante > 9) {
            linhaDoTempoLabel(lFaltasTimeVisitante, faltasTimeVisitante + "");
        } else {
            linhaDoTempoLabel(lFaltasTimeVisitante, "0" + faltasTimeVisitante);
        }
    }

    public void zerarFaltas() {
        faltasTimeLocal = 0;
        faltasTimeVisitante = 0;

        linhaDoTempoLabel(lFaltasTimeLocal, "00");
        linhaDoTempoLabel(lFaltasTimeVisitante, "00");
    }

    public void alterarImagemDireita(File file) {
        linhaDoTempoVBox(vbImagenDireita, file);
    }

    public void alterarImagemEsquerda(File file) {
        linhaDoTempoVBox(vbImagenEsquerda, file);
    }

    public void restaurarImagemDireita() {
        linhaDoTempoVBox(vbImagenDireita, null);
    }

    public void restaurarImagemEsquerda() {
        linhaDoTempoVBox(vbImagenEsquerda, null);
    }

    public boolean isExecutando() {
        return executando;
    }

    public void setExecutando(boolean executando) {
        this.executando = executando;
    }

    public void alterarCronometro(int segundos, int minutos) {
        Timeline tlCronometro = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            String[] cronometro = lCronometro.getText().split(":");

            if (segundos < 10) {
                lCronometro.setText(cronometro[0] + ":0" + segundos);
            } else {
                lCronometro.setText(cronometro[0] + ":" + segundos);
            }

            cronometro = lCronometro.getText().split(":");

            if (minutos < 10) {
                lCronometro.setText("0" + minutos + ":" + cronometro[1]);
            } else {
                lCronometro.setText(minutos + ":" + cronometro[1]);
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tlCronometro.play();
    }

    public void definir(String minutos, String segundos) {
        if (minutos.length() == 1) {
            minutos = "0" + minutos;
        }

        if (segundos.length() == 1) {
            segundos = "0" + segundos;
        }

        linhaDoTempoLabel(lCronometro, minutos + ":" + segundos);
    }

    public void iniciar(String minutos, String segundos) {
        this.executando = true;

        if (primeiraVez) {
            t = new Thread(new Cronos(this, minutos, segundos));
            primeiraVez = false;
            t.start();
        } else {
            t.resume();
        }
    }

    public void pausar() {
        this.executando = false;
        t.suspend();
    }

    public void zerar() {
        this.executando = false;
        this.primeiraVez = true;

        Timeline tlCronometro = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            lCronometro.setText("20:00");
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tlCronometro.play();
        t.stop();
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
