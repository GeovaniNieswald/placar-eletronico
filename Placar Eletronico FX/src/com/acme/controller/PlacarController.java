package com.acme.controller;

import com.acme.PlacarServer;
import com.acme.model.Cronos;
import de.jensd.fx.glyphs.octicons.OctIconView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
    private VBox vbImagenEsquerda;

    @FXML
    private VBox vbImagenDireita;

    @FXML
    private OctIconView ovBonusTimeLocal;

    @FXML
    private OctIconView ovBonusTimeVisitante;

    @FXML
    private OctIconView ovPosseTimeVisitante;

    @FXML
    private OctIconView ovPosseTimeLocal;

    private final int VELOCIDADE_TXT = 10000; //em milissegundos

    private int pontosTimeLocal;
    private int pontosTimeVisitante;

    private int faltasTimeLocal;
    private int faltasTimeVisitante;

    private int periodo = 1;

    private Thread threadCronometro;

    private boolean executandoCronometroPrimeiraVez = true;
    private boolean executandoCronometro;

    private void alterarLabel(Label label, String texto) {
        Platform.runLater(() -> label.setText(texto));
    }

    private void alterarVbox(VBox vBox, File file) {
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

        Platform.runLater(() -> vBox.setBackground(background));
    }

    public void alterarNomeTime(String lado, String texto) {
        if (lado.equals("local")) {
            alterarLabel(lNomeTimeLocal, texto);
        } else {
            alterarLabel(lNomeTimeVisitante, texto);
        }
    }

    public boolean isExecutandoCronometro() {
        return executandoCronometro;
    }

    public void setExecutandoCronometro(boolean executando) {
        this.executandoCronometro = executando;
    }

    public void alterarCronometro(int segundos, int minutos) {
        Platform.runLater(() -> {
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
        });
    }

    public void definirCronometro(String minutos, String segundos) {
        if (minutos.length() == 1) {
            minutos = "0" + minutos;
        }

        if (segundos.length() == 1) {
            segundos = "0" + segundos;
        }

        alterarLabel(lCronometro, minutos + ":" + segundos);
    }

    public void iniciarCronometro(String minutos, String segundos) {
        this.executandoCronometro = true;

        if (executandoCronometroPrimeiraVez) {
            threadCronometro = new Thread(new Cronos(this, minutos, segundos));
            executandoCronometroPrimeiraVez = false;
            threadCronometro.start();
        } else {
            threadCronometro.resume();
        }
    }

    public void pausarCronometro() {
        this.executandoCronometro = false;
        threadCronometro.suspend();
    }

    public void zerarCronometro(String esporte) {
        this.executandoCronometro = false;
        this.executandoCronometroPrimeiraVez = true;

        Platform.runLater(() -> {
            if (esporte.equals("basquete")) {
                lCronometro.setText("10:00");
            } else {
                lCronometro.setText("20:00");
            }
        });

        if (threadCronometro != null) {
            threadCronometro.stop();
        }
    }

    public void pararCronometro() {
        this.executandoCronometro = false;
        this.executandoCronometroPrimeiraVez = true;
        threadCronometro.stop();
    }

    public void aumentarPeriodo(int valor) {
        periodo += valor;

        if (periodo > 9) {
            alterarLabel(lPeriodo, periodo + "");
        } else {
            alterarLabel(lPeriodo, "0" + periodo);
        }
    }

    public void diminuirPeriodo(int valor) {
        periodo -= valor;

        if (periodo > 9) {
            alterarLabel(lPeriodo, periodo + "");
        } else {
            alterarLabel(lPeriodo, "0" + periodo);
        }
    }

    public void restaurarPeriodo() {
        periodo = 1;
        alterarLabel(lPeriodo, "0" + periodo);
    }

    public void trocarPosse(String lado) {
        if (lado.equals("local")) {
            ovPosseTimeLocal.setVisible(true);
            ovPosseTimeVisitante.setVisible(false);
        } else {
            ovPosseTimeLocal.setVisible(false);
            ovPosseTimeVisitante.setVisible(true);
        }
    }

    public void definirBonus(String lado) {
        if (lado.equals("local")) {
            ovBonusTimeLocal.setVisible(true);
        } else {
            ovBonusTimeVisitante.setVisible(true);
        }
    }

    public void removerBonus(String lado) {
        if (lado.equals("local")) {
            ovBonusTimeLocal.setVisible(false);
        } else {
            ovBonusTimeVisitante.setVisible(false);
        }
    }

    public void aumentarPontos(String lado, int pontos) {
        if (lado.equals("local")) {
            pontosTimeLocal += pontos;

            if (pontosTimeLocal > 9) {
                alterarLabel(lPontosTimeLocal, pontosTimeLocal + "");
            } else {
                alterarLabel(lPontosTimeLocal, "0" + pontosTimeLocal);
            }
        } else {
            pontosTimeVisitante += pontos;

            if (pontosTimeVisitante > 9) {
                alterarLabel(lPontosTimeVisitante, pontosTimeVisitante + "");
            } else {
                alterarLabel(lPontosTimeVisitante, "0" + pontosTimeVisitante);
            }
        }
    }

    public void diminuirPontos(String lado, int pontos) {
        if (lado.equals("local")) {
            pontosTimeLocal -= pontos;

            if (pontosTimeLocal > 9) {
                alterarLabel(lPontosTimeLocal, pontosTimeLocal + "");
            } else {
                alterarLabel(lPontosTimeLocal, "0" + pontosTimeLocal);
            }
        } else {
            pontosTimeVisitante -= pontos;

            if (pontosTimeVisitante > 9) {
                alterarLabel(lPontosTimeVisitante, pontosTimeVisitante + "");
            } else {
                alterarLabel(lPontosTimeVisitante, "0" + pontosTimeVisitante);
            }
        }
    }

    public void zerarPontos() {
        pontosTimeLocal = 0;
        pontosTimeVisitante = 0;

        alterarLabel(lPontosTimeLocal, "00");
        alterarLabel(lPontosTimeVisitante, "00");
    }

    public void aumentarFaltas(String lado, int faltas) {
        if (lado.equals("local")) {
            faltasTimeLocal += faltas;

            if (faltasTimeLocal > 9) {
                alterarLabel(lFaltasTimeLocal, faltasTimeLocal + "");
            } else {
                alterarLabel(lFaltasTimeLocal, "0" + faltasTimeLocal);
            }
        } else {
            faltasTimeVisitante += faltas;

            if (faltasTimeVisitante > 9) {
                alterarLabel(lFaltasTimeVisitante, faltasTimeVisitante + "");
            } else {
                alterarLabel(lFaltasTimeVisitante, "0" + faltasTimeVisitante);
            }
        }
    }

    public void diminuirFaltas(String lado, int faltas) {
        if (lado.equals("local")) {
            faltasTimeLocal -= faltas;

            if (faltasTimeLocal > 9) {
                alterarLabel(lFaltasTimeLocal, faltasTimeLocal + "");
            } else {
                alterarLabel(lFaltasTimeLocal, "0" + faltasTimeLocal);
            }
        } else {
            faltasTimeVisitante -= faltas;

            if (faltasTimeVisitante > 9) {
                alterarLabel(lFaltasTimeVisitante, faltasTimeVisitante + "");
            } else {
                alterarLabel(lFaltasTimeVisitante, "0" + faltasTimeVisitante);
            }
        }
    }

    public void zerarFaltas() {
        faltasTimeLocal = 0;
        faltasTimeVisitante = 0;

        alterarLabel(lFaltasTimeLocal, "00");
        alterarLabel(lFaltasTimeVisitante, "00");
    }

    public void alterarTextoInferior(String texto) {
        alterarLabel(lTextoInferior, texto);
    }

    public void alterarImagem(String lado, File file) {
        if (lado.equals("direita")) {
            alterarVbox(vbImagenDireita, file);
        } else {
            alterarVbox(vbImagenEsquerda, file);
        }
    }

    public void restaurarImagem(String lado) {
        if (lado.equals("direita")) {
            alterarVbox(vbImagenDireita, null);
        } else {
            alterarVbox(vbImagenEsquerda, null);
        }
    }

    @FXML
    void bFecharAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlacarServer.instanciaPlacarController(this);

        Platform.runLater(() -> {
            int tamanhoTexto = lTextoInferior.getText().length();

            if (tamanhoTexto == 0) {
                tamanhoTexto = 1;
            }

            TranslateTransition tt = new TranslateTransition(Duration.millis(VELOCIDADE_TXT), lTextoInferior);
            tt.setCycleCount(Animation.INDEFINITE);
            tt.setFromX((tamanhoTexto * 60) * -1);
            tt.setToX(1200);
            tt.playFromStart();
        });
    }
}
