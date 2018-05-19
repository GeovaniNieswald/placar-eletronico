package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.Cronos;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

/**
 * Classe Referente ao controlador das cenas do placar.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class PlacarController implements Initializable {

    @FXML
    private JFXTextField jfxtfNomeTimeLocal;

    @FXML
    private JFXTextField jfxtfNomeTimeVisitante;

    @FXML
    private JFXTextField jfxtfMinutos;

    @FXML
    private JFXButton jfxbIniciarCronometro;

    @FXML
    private JFXButton jfxbPausarCronometro;

    @FXML
    private JFXButton jfxbRestaurarCronometro;

    @FXML
    private JFXTextField jfxtfPeriodo;

    @FXML
    private Label lPontosTimeLocal;

    @FXML
    private Label lPontosTimeVisitante;

    @FXML
    private Label lFaltasTimeLocal;

    @FXML
    private Label lFaltasTimeVisitante;

    @FXML
    private JFXTextField jfxtfNomeTimeLocalL;

    @FXML
    private JFXTextField jfxtfNomeTimeVisitanteL;

    @FXML
    private JFXTextField jfxtfCronometroL;

    @FXML
    private JFXTextField jfxtfPeriodoL;

    @FXML
    private JFXTextField jfxtfSegundos;

    @FXML
    private JFXButton jfxbDefinirCronometro;

    @FXML
    private ToggleGroup tgPosse;

    @FXML
    private JFXCheckBox jfxcbBonusLocal;

    @FXML
    private JFXCheckBox jfxcbBonusVisitante;

    @FXML
    private JFXTextField jfxtfJogador;

    @FXML
    private JFXTextField jfxtfFaltas;

    @FXML
    private JFXTextField jfxtfJogadorL;

    @FXML
    private JFXTextField jfxtfFaltasL;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private RespostaSocket respostaComando;

    private boolean basquete;

    private int pontosTimeLocal;
    private int pontosTimeVisitante;

    private int faltasTimeLocal;
    private int faltasTimeVisitante;

    private int periodo = 1;

    private int minutos;
    private int segundos;

    private boolean primeiraVez = true;
    private boolean executando;
    private Thread t;

    /**
     * Método para trocar a cor dos campos TextField.
     *
     * @param cor String - Hexadecimal da cor quando o campo não está com foco e
     * quando está.
     * @param componentes JFXTextField - Varargs que contém os campos.
     */
    private void trocarCorJFXTextField(String cor, JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setUnFocusColor(Paint.valueOf(cor));
            comp.setFocusColor(Paint.valueOf(cor));
        }
    }

    /**
     * Método para trocar a cor dos labels.
     *
     * @param cor String - Hexadecimal da cor.
     * @param componente Label.
     */
    private void trocarCorLabel(String cor, Label componente) {
        componente.setTextFill(Paint.valueOf(cor));
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        // Pedir confirmação
        System.exit(0);
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        // Pedir confirmação
        MainApp.trocarCena(Cena.ESPORTE);
    }

    /**
     * Método acionado quando o mouse é arrastado, ele pega a posição atual
     * horizontal e vertical da cena, faz a subtração pela posição inicial
     * horizontal e vertical separadamente, e chama o método que move a tela,
     * passando os valores resultantes dessas subtrações.
     *
     * @param event MouseEvent.
     */
    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - posicaoInicialX, event.getScreenY() - posicaoInicialY);
    }

    /**
     * Método acionado quando o algum botão do mouse é pressionado, ele pega a
     * posição atual horizontal e vertical da cena.
     *
     * @param event MouseEvent.
     */
    @FXML
    void gpOnMousePressed(MouseEvent event) {
        posicaoInicialX = event.getSceneX();
        posicaoInicialY = event.getSceneY();
    }

    @FXML
    void jfxbAlterarNomeTimeLocalOnAction(ActionEvent event) {
        if (jfxtfNomeTimeLocal.getText().trim().isEmpty() || jfxtfNomeTimeLocal.getText().length() > 15) {
            trocarCorJFXTextField("red", jfxtfNomeTimeLocalL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.NOME_TIME, "local", "alterar", jfxtfNomeTimeLocal.getText());

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", jfxtfNomeTimeLocalL);
                } else {
                    trocarCorJFXTextField("red", jfxtfNomeTimeLocalL);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", jfxtfNomeTimeLocalL);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbAlterarNomeTimeVisitanteOnAction(ActionEvent event) {
        if (jfxtfNomeTimeVisitante.getText().trim().isEmpty() || jfxtfNomeTimeVisitante.getText().length() > 15) {
            trocarCorJFXTextField("red", jfxtfNomeTimeVisitanteL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.NOME_TIME, "visitante", "alterar", jfxtfNomeTimeVisitante.getText());

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", jfxtfNomeTimeVisitanteL);
                } else {
                    trocarCorJFXTextField("red", jfxtfNomeTimeVisitanteL);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", jfxtfNomeTimeVisitanteL);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbAlterarUltimoJogadorOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbAumentar1PontoTimeLocalOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "mais", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeLocal++;

                if (pontosTimeLocal > 9) {
                    lPontosTimeLocal.setText(pontosTimeLocal + "");
                } else {
                    lPontosTimeLocal.setText("0" + pontosTimeLocal);
                }

                trocarCorLabel("white", lPontosTimeLocal);
            } else {
                trocarCorLabel("red", lPontosTimeLocal);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lPontosTimeLocal);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentar1PontoTimeVisitanteOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "mais", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeVisitante++;

                if (pontosTimeVisitante > 9) {
                    lPontosTimeVisitante.setText(pontosTimeVisitante + "");
                } else {
                    lPontosTimeVisitante.setText("0" + pontosTimeVisitante);
                }

                trocarCorLabel("white", lPontosTimeVisitante);
            } else {
                trocarCorLabel("red", lPontosTimeVisitante);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lPontosTimeVisitante);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentar2PontoTimeLocalOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "mais", "2");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeLocal += 2;

                if (pontosTimeLocal > 9) {
                    lPontosTimeLocal.setText(pontosTimeLocal + "");
                } else {
                    lPontosTimeLocal.setText("0" + pontosTimeLocal);
                }

                trocarCorLabel("white", lPontosTimeLocal);
            } else {
                trocarCorLabel("red", lPontosTimeLocal);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lPontosTimeLocal);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentar2PontoTimeVisitanteOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "mais", "2");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeVisitante += 2;

                if (pontosTimeVisitante > 9) {
                    lPontosTimeVisitante.setText(pontosTimeVisitante + "");
                } else {
                    lPontosTimeVisitante.setText("0" + pontosTimeVisitante);
                }

                trocarCorLabel("white", lPontosTimeVisitante);
            } else {
                trocarCorLabel("red", lPontosTimeVisitante);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lPontosTimeVisitante);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentar3PontoTimeLocalOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "mais", "3");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeLocal += 3;

                if (pontosTimeLocal > 9) {
                    lPontosTimeLocal.setText(pontosTimeLocal + "");
                } else {
                    lPontosTimeLocal.setText("0" + pontosTimeLocal);
                }

                trocarCorLabel("white", lPontosTimeLocal);
            } else {
                trocarCorLabel("red", lPontosTimeLocal);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lPontosTimeLocal);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentar3PontoTimeVisitanteOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "mais", "3");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeVisitante += 3;

                if (pontosTimeVisitante > 9) {
                    lPontosTimeVisitante.setText(pontosTimeVisitante + "");
                } else {
                    lPontosTimeVisitante.setText("0" + pontosTimeVisitante);
                }

                trocarCorLabel("white", lPontosTimeVisitante);
            } else {
                trocarCorLabel("red", lPontosTimeVisitante);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lPontosTimeVisitante);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }

    }

    @FXML
    void jfxbAumentarFaltasTimeLocalOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.FALTAS, "local", "mais", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                faltasTimeLocal++;

                if (faltasTimeLocal > 9) {
                    lFaltasTimeLocal.setText(faltasTimeLocal + "");
                } else {
                    lFaltasTimeLocal.setText("0" + faltasTimeLocal);
                }

                trocarCorLabel("white", lFaltasTimeLocal);
            } else {
                trocarCorLabel("red", lFaltasTimeLocal);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lFaltasTimeLocal);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentarFaltasTimeVisitanteOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.FALTAS, "visitante", "mais", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                faltasTimeVisitante++;

                if (faltasTimeVisitante > 9) {
                    lFaltasTimeVisitante.setText(faltasTimeVisitante + "");
                } else {
                    lFaltasTimeVisitante.setText("0" + faltasTimeVisitante);
                }

                trocarCorLabel("white", lFaltasTimeVisitante);
            } else {
                trocarCorLabel("red", lFaltasTimeVisitante);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lFaltasTimeVisitante);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentarPeriodoOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PERIODO, "mais", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                periodo++;

                if (periodo > 9) {
                    jfxtfPeriodo.setText(periodo + "");
                } else {
                    jfxtfPeriodo.setText("0" + periodo);
                }

                trocarCorJFXTextField("#09a104", jfxtfPeriodoL);
            } else {
                trocarCorJFXTextField("red", jfxtfPeriodoL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", jfxtfPeriodoL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }

    }

    @FXML
    void jfxbDiminuirPeriodoOnAction(ActionEvent event) {
        if (periodo > 1) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PERIODO, "menos", "1");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    periodo--;

                    if (periodo > 9) {
                        jfxtfPeriodo.setText(periodo + "");
                    } else {
                        jfxtfPeriodo.setText("0" + periodo);
                    }

                    trocarCorJFXTextField("#09a104", jfxtfPeriodoL);
                } else {
                    trocarCorJFXTextField("red", jfxtfPeriodoL);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", jfxtfPeriodoL);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbDiminuir1PontoTimeLocalOnAction(ActionEvent event) {
        if (pontosTimeLocal > 0) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "menos", "1");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    pontosTimeLocal--;

                    if (pontosTimeLocal > 9) {
                        lPontosTimeLocal.setText(pontosTimeLocal + "");
                    } else {
                        lPontosTimeLocal.setText("0" + pontosTimeLocal);
                    }

                    trocarCorLabel("white", lPontosTimeLocal);
                } else {
                    trocarCorLabel("red", lPontosTimeLocal);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lPontosTimeLocal);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbDiminuir1PontoTimeVisitanteOnAction(ActionEvent event) {
        if (pontosTimeVisitante > 0) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "menos", "1");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    pontosTimeVisitante--;

                    if (pontosTimeVisitante > 9) {
                        lPontosTimeVisitante.setText(pontosTimeVisitante + "");
                    } else {
                        lPontosTimeVisitante.setText("0" + pontosTimeVisitante);
                    }

                    trocarCorLabel("white", lPontosTimeVisitante);
                } else {
                    trocarCorLabel("red", lPontosTimeVisitante);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lPontosTimeVisitante);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbDiminuir2PontoTimeLocalOnAction(ActionEvent event) {
        if (pontosTimeLocal > 1) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "menos", "2");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    pontosTimeLocal -= 2;

                    if (pontosTimeLocal > 9) {
                        lPontosTimeLocal.setText(pontosTimeLocal + "");
                    } else {
                        lPontosTimeLocal.setText("0" + pontosTimeLocal);
                    }

                    trocarCorLabel("white", lPontosTimeLocal);
                } else {
                    trocarCorLabel("red", lPontosTimeLocal);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lPontosTimeLocal);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbDiminuir2PontoTimeVisitanteOnAction(ActionEvent event) {
        if (pontosTimeVisitante > 1) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "menos", "2");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    pontosTimeVisitante -= 2;

                    if (pontosTimeVisitante > 9) {
                        lPontosTimeVisitante.setText(pontosTimeVisitante + "");
                    } else {
                        lPontosTimeVisitante.setText("0" + pontosTimeVisitante);
                    }

                    trocarCorLabel("white", lPontosTimeVisitante);
                } else {
                    trocarCorLabel("red", lPontosTimeVisitante);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lPontosTimeVisitante);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbDiminuir3PontoTimeLocalOnAction(ActionEvent event) {
        if (pontosTimeLocal > 2) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "menos", "3");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    pontosTimeLocal -= 3;

                    if (pontosTimeLocal > 9) {
                        lPontosTimeLocal.setText(pontosTimeLocal + "");
                    } else {
                        lPontosTimeLocal.setText("0" + pontosTimeLocal);
                    }

                    trocarCorLabel("white", lPontosTimeLocal);
                } else {
                    trocarCorLabel("red", lPontosTimeLocal);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lPontosTimeLocal);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }

    }

    @FXML
    void jfxbDiminuir3PontoTimeVisitanteOnAction(ActionEvent event) {
        if (pontosTimeVisitante > 2) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "menos", "3");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    pontosTimeVisitante -= 3;

                    if (pontosTimeVisitante > 9) {
                        lPontosTimeVisitante.setText(pontosTimeVisitante + "");
                    } else {
                        lPontosTimeVisitante.setText("0" + pontosTimeVisitante);
                    }

                    trocarCorLabel("white", lPontosTimeVisitante);
                } else {
                    trocarCorLabel("red", lPontosTimeVisitante);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lPontosTimeVisitante);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbDiminuirFaltasTimeLocalOnAction(ActionEvent event) {
        if (faltasTimeLocal > 0) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.FALTAS, "local", "menos", "1");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    faltasTimeLocal--;

                    if (faltasTimeLocal > 9) {
                        lFaltasTimeLocal.setText(faltasTimeLocal + "");
                    } else {
                        lFaltasTimeLocal.setText("0" + faltasTimeLocal);
                    }

                    trocarCorLabel("white", lFaltasTimeLocal);
                } else {
                    trocarCorLabel("red", lFaltasTimeLocal);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lFaltasTimeLocal);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbDiminuirFaltasTimeVisitanteOnAction(ActionEvent event) {
        if (faltasTimeVisitante > 0) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.FALTAS, "visitante", "menos", "1");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    faltasTimeVisitante--;

                    if (faltasTimeVisitante > 9) {
                        lFaltasTimeVisitante.setText(faltasTimeVisitante + "");
                    } else {
                        lFaltasTimeVisitante.setText("0" + faltasTimeVisitante);
                    }

                    trocarCorLabel("white", lFaltasTimeVisitante);
                } else {
                    trocarCorLabel("red", lFaltasTimeVisitante);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lFaltasTimeVisitante);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    public boolean isExecutando() {
        return executando;
    }

    public void setExecutando(boolean executando) {
        this.executando = executando;
    }

    public void alterarCronometro(int segundos, int minutos) {
        if (segundos < 10) {
            jfxtfSegundos.setText("0" + segundos);
        } else {
            jfxtfSegundos.setText("" + segundos);
        }
        if (minutos < 10) {
            jfxtfMinutos.setText("0" + minutos);
        } else {
            jfxtfMinutos.setText("" + minutos);
        }
    }

    private void iniciarCronometro(String minutos, String segundos) {
        this.executando = true;

        if (primeiraVez) {
            t = new Thread(new Cronos(this, minutos, segundos));
            primeiraVez = false;
            t.start();
        } else {
            t.resume();
        }
    }

    private void pausarCronometro() {
        this.executando = false;
        t.suspend();
    }

    private void zerarCronometro() {
        this.executando = false;
        this.primeiraVez = true;
        t.stop();
    }

    public void pararCronometro() {
        this.executando = false;
        this.primeiraVez = true;

        jfxbDefinirCronometro.setDisable(false);
        jfxbIniciarCronometro.setDisable(true);
        jfxbPausarCronometro.setDisable(true);
        jfxbRestaurarCronometro.setDisable(true);
        jfxtfMinutos.setEditable(true);
        jfxtfSegundos.setEditable(true);

        t.stop();
    }

    private void iniciarCronometroPlacar() {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.CRONOMETRO, "iniciar", "" + minutos, "" + segundos);

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                iniciarCronometro(minutos + "", segundos + "");

                trocarCorJFXTextField("#09a104", jfxtfCronometroL);

                jfxbDefinirCronometro.setDisable(true);
                jfxbIniciarCronometro.setDisable(true);
                jfxbPausarCronometro.setDisable(false);
                jfxbRestaurarCronometro.setDisable(true);

                jfxtfMinutos.setEditable(false);
                jfxtfSegundos.setEditable(false);
            } else {
                trocarCorJFXTextField("red", jfxtfCronometroL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", jfxtfCronometroL);
            //IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbDefinirCronometroOnAction(ActionEvent event) {
        boolean erro = false;

        if (jfxtfMinutos.getText().trim().isEmpty()) {
            trocarCorJFXTextField("red", jfxtfMinutos);
            erro = true;
        } else {
            try {
                int minutosLocal = Integer.parseInt(jfxtfMinutos.getText());

                if (minutosLocal < 0 || minutosLocal > 999) {
                    trocarCorJFXTextField("red", jfxtfMinutos);
                    erro = true;
                } else {
                    trocarCorJFXTextField("white", jfxtfMinutos);
                }
            } catch (NumberFormatException ex) {
                trocarCorJFXTextField("red", jfxtfMinutos);
                jfxbIniciarCronometro.setDisable(true);
                erro = true;
            }
        }

        if (jfxtfSegundos.getText().trim().isEmpty()) {
            trocarCorJFXTextField("red", jfxtfSegundos);
            erro = true;
        } else {
            try {
                int segundosLocal = Integer.parseInt(jfxtfSegundos.getText());
                int minutosLocal = Integer.parseInt(jfxtfMinutos.getText());

                if (segundosLocal < 0 || (minutosLocal == 0 && segundosLocal == 0) || segundosLocal > 59) {
                    trocarCorJFXTextField("red", jfxtfSegundos);
                    erro = true;
                } else {
                    trocarCorJFXTextField("white", jfxtfSegundos);
                }
            } catch (NumberFormatException ex) {
                trocarCorJFXTextField("red", jfxtfSegundos);
                jfxbIniciarCronometro.setDisable(true);
                erro = true;
            }
        }

        if (!erro) {
            minutos = Integer.parseInt(jfxtfMinutos.getText());
            segundos = Integer.parseInt(jfxtfSegundos.getText());

            try {
                respostaComando = PlacarClient.enviarComando(Comando.CRONOMETRO, "definir", "" + minutos, "" + segundos);

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    if (minutos < 10) {
                        jfxtfMinutos.setText("0" + minutos);
                    }
                    if (segundos < 10) {
                        jfxtfSegundos.setText("0" + segundos);
                    }

                    trocarCorJFXTextField("#09a104", jfxtfMinutos, jfxtfSegundos);
                    jfxbIniciarCronometro.setDisable(false);
                } else {
                    trocarCorJFXTextField("red", jfxtfMinutos, jfxtfSegundos);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", jfxtfMinutos, jfxtfSegundos);
                //IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbIniciarCronometroOnAction(ActionEvent event) {
        if (primeiraVez) {
            try {
                if (minutos == Integer.parseInt(jfxtfMinutos.getText()) && segundos == Integer.parseInt(jfxtfSegundos.getText())) {
                    iniciarCronometroPlacar();
                } else {
                    trocarCorJFXTextField("white", jfxtfMinutos, jfxtfSegundos);
                    jfxbIniciarCronometro.setDisable(true);
                }
            } catch (NumberFormatException ex) {
                trocarCorJFXTextField("white", jfxtfMinutos, jfxtfSegundos);
                jfxbIniciarCronometro.setDisable(true);
            }
        } else {
            iniciarCronometroPlacar();
        }
    }

    @FXML
    void jfxbPausarCronometroOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.CRONOMETRO, "pausar", "", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pausarCronometro();

                trocarCorJFXTextField("#09a104", jfxtfCronometroL);

                jfxbIniciarCronometro.setDisable(false);
                jfxbPausarCronometro.setDisable(true);
                jfxbRestaurarCronometro.setDisable(false);
            } else {
                trocarCorJFXTextField("red", jfxtfCronometroL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", jfxtfCronometroL);
            // Implementar log
        }
    }

    @FXML
    void jfxbRestaurarCronometroOnAction(ActionEvent event) {
        try {
            if (basquete) {
                respostaComando = PlacarClient.enviarComando(Comando.CRONOMETRO, "zerar", "basquete", "");
            } else {
                respostaComando = PlacarClient.enviarComando(Comando.CRONOMETRO, "zerar", "futsal", "");
            }

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                zerarCronometro();

                jfxbDefinirCronometro.setDisable(false);
                jfxbIniciarCronometro.setDisable(true);
                jfxbPausarCronometro.setDisable(true);
                jfxbRestaurarCronometro.setDisable(true);

                jfxtfMinutos.setEditable(true);
                jfxtfSegundos.setEditable(true);

                if (basquete) {
                    jfxtfMinutos.setText("10");
                } else {
                    jfxtfMinutos.setText("20");
                }

                jfxtfSegundos.setText("00");

                trocarCorJFXTextField("white", jfxtfMinutos, jfxtfSegundos, jfxtfCronometroL);
            } else {
                trocarCorJFXTextField("red", jfxtfCronometroL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", jfxtfCronometroL);
            // Implementar log
        }
    }

    @FXML
    void jfxbRestaurarNomeTimeLocalOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.NOME_TIME, "local", "restaurar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", jfxtfNomeTimeLocalL);
                jfxtfNomeTimeLocal.setText("");
            } else {
                trocarCorJFXTextField("red", jfxtfNomeTimeLocalL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", jfxtfNomeTimeLocalL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbRestaurarNomeTimeVisitanteOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.NOME_TIME, "visitante", "restaurar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", jfxtfNomeTimeVisitanteL);
                jfxtfNomeTimeVisitante.setText("");
            } else {
                trocarCorJFXTextField("red", jfxtfNomeTimeVisitanteL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", jfxtfNomeTimeVisitanteL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbZerarFaltasOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.FALTAS, "zerar", "", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                faltasTimeLocal = 0;
                faltasTimeVisitante = 0;

                lFaltasTimeLocal.setText("00");
                lFaltasTimeVisitante.setText("00");

                trocarCorLabel("white", lFaltasTimeLocal);
                trocarCorLabel("white", lFaltasTimeVisitante);
            } else {
                trocarCorLabel("red", lFaltasTimeLocal);
                trocarCorLabel("red", lFaltasTimeVisitante);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lFaltasTimeLocal);
            trocarCorLabel("red", lFaltasTimeVisitante);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbZerarPontosOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "zerar", "", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeLocal = 0;
                pontosTimeVisitante = 0;

                lPontosTimeLocal.setText("00");
                lPontosTimeVisitante.setText("00");

                trocarCorLabel("white", lPontosTimeLocal);
                trocarCorLabel("white", lPontosTimeVisitante);
            } else {
                trocarCorLabel("red", lPontosTimeLocal);
                trocarCorLabel("red", lPontosTimeVisitante);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lPontosTimeLocal);
            trocarCorLabel("red", lPontosTimeVisitante);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbRestaurarPeriodoOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PERIODO, "zerar", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                periodo = 1;

                jfxtfPeriodo.setText("0" + periodo);

                trocarCorJFXTextField("white", jfxtfPeriodoL);
            } else {
                trocarCorJFXTextField("red", jfxtfPeriodoL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", jfxtfPeriodoL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbRestaurarTudoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarUltimoJogadorOnAction(ActionEvent event) {

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
    public void initialize(URL location, ResourceBundle resources) {
        if (jfxtfMinutos.getText().equals("10")) {
            basquete = true;
        } else {
            basquete = false;
        }
    }
}
