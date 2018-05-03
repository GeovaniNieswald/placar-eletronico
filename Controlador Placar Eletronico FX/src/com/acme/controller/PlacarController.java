package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private JFXTextField jfxtfCronometro;

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

    @FXML
    private JFXButton jfxIniciar;

    @FXML
    private JFXButton jfxPausar;

    @FXML
    private JFXButton jfxRestaurar;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private RespostaSocket respostaComando;

    private int pontosTimeLocal;
    private int pontosTimeVisitante;

    private int faltasTimeLocal;
    private int faltasTimeVisitante;

    /**
     * Método para trocar a cor dos campos TextField.
     *
     * @param cor String - Hexadecimal da cor.
     * @param componente JFXTextField - Campo.
     */
    private void trocarCorJFXTextField(String cor, JFXTextField componente) {
        componente.setUnFocusColor(Paint.valueOf(cor));
        componente.setFocusColor(Paint.valueOf(cor));
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

    @FXML
    void jfxbDiminuirPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbIniciarCronometroOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.CRONOS, "start");
            jfxIniciar.setDisable(true);
            jfxPausar.setDisable(false);
            jfxRestaurar.setDisable(true);
        } catch (IOException ex) {
            Logger.getLogger(PlacarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void jfxbPausarCronometroOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.CRONOS, "pause");
            jfxIniciar.setDisable(false);
            jfxPausar.setDisable(true);
            jfxRestaurar.setDisable(false);
        } catch (IOException ex) {
            Logger.getLogger(PlacarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void jfxbRestaurarCronometroOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.CRONOS, "reset");
            jfxIniciar.setDisable(false);
            jfxPausar.setDisable(false);
            jfxRestaurar.setDisable(true);
        } catch (IOException ex) {
            Logger.getLogger(PlacarController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    void jfxbAumentar1PontoTimeVisitante(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
