package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

/**
 * Classe Referente ao controlador da cena de futsal.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class UsuarioPrincipalFutsalController implements Initializable {

    @FXML
    private FontAwesomeIconView faivVoltar;

    @FXML
    private FontAwesomeIconView faivSair;

    @FXML
    private JFXTextField jfxtfNomeTimeLocalL;

    @FXML
    private JFXTextField jfxtfNomeTimeLocal;

    @FXML
    private JFXButton jfxbAlterarNomeTimeLocal;

    @FXML
    private JFXButton jfxbRestaurarNomeTimeLocal;

    @FXML
    private JFXTextField jfxtfNomeTimeVisitante;

    @FXML
    private JFXTextField jfxtfNomeTimeVisitanteL;

    @FXML
    private JFXButton jfxbAlterarNomeTimeVisitante;

    @FXML
    private JFXButton jfxbRestaurarNomeTimeVisitante;

    @FXML
    private JFXTextField jfxtfCronometro;

    @FXML
    private JFXButton jfxbIniciarCronometro;

    @FXML
    private JFXButton jfxbPausarCronometro;

    @FXML
    private JFXButton jfxbRestaurarCronometro;

    @FXML
    private JFXTextField jfxtfPeriodo;

    @FXML
    private JFXButton jfxbAumentarPeriodo;

    @FXML
    private JFXButton jfxbDiminuirPeriodo;

    @FXML
    private JFXButton jfxbRestaurarPeriodo;

    @FXML
    private Label lGolsTimeLocal;

    @FXML
    private JFXButton jfxbAumentarGolsTimeLocal;

    @FXML
    private JFXButton jfxbDiminuirGolsTimeLocal;

    @FXML
    private Label lGolsTimeVisitante;

    @FXML
    private JFXButton jfxbAumentarGolsTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuirGolsTimeVisitante;

    @FXML
    private JFXButton jfxbZerarGols;

    @FXML
    private Label lFaltasTimeLocal;

    @FXML
    private JFXButton jfxbAumentarFaltasTimeLocal;

    @FXML
    private JFXButton jfxbDiminuirFaltasTimeLocal;

    @FXML
    private Label lFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbAumentarFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbDiminuirFaltasTimeVisitante;

    @FXML
    private JFXButton jfxbZerarFaltas;

    @FXML
    private JFXButton jfxbRestaurarTudo;

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
    void jfxbAumentarFaltasTimeLocalOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "somaFalta", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                faltasTimeLocal++;

                if (pontosTimeLocal > 9) {
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
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "somaFalta", "1");

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
    void jfxbAumentarGolsTimeLocalOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "mais", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeLocal++;

                if (pontosTimeLocal > 9) {
                    lGolsTimeLocal.setText(pontosTimeLocal + "");
                } else {
                    lGolsTimeLocal.setText("0" + pontosTimeLocal);
                }

                trocarCorLabel("white", lGolsTimeLocal);
            } else {
                trocarCorLabel("red", lGolsTimeLocal);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lGolsTimeLocal);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentarGolsTimeVisitante(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "mais", "1");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeVisitante++;

                if (pontosTimeVisitante > 9) {
                    lGolsTimeVisitante.setText(pontosTimeVisitante + "");
                } else {
                    lGolsTimeVisitante.setText("0" + pontosTimeVisitante);
                }

                trocarCorLabel("white", lGolsTimeVisitante);
            } else {
                trocarCorLabel("red", lGolsTimeVisitante);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lGolsTimeVisitante);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbAumentarPeriodoOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirFaltasTimeLocalOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirFaltasTimeVisitanteOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbDiminuirGolsTimeLocalOnAction(ActionEvent event) {
        if (pontosTimeLocal > 0) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "local", "menos", "1");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    pontosTimeLocal--;

                    if (pontosTimeLocal > 9) {
                        lGolsTimeLocal.setText(pontosTimeLocal + "");
                    } else {
                        lGolsTimeLocal.setText("0" + pontosTimeLocal);
                    }

                    trocarCorLabel("white", lGolsTimeLocal);
                } else {
                    trocarCorLabel("red", lGolsTimeLocal);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lGolsTimeLocal);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbDiminuirGolsTimeVisitanteOnAction(ActionEvent event) {
        if (pontosTimeVisitante > 0) {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "visitante", "menos", "1");

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    pontosTimeVisitante--;

                    if (pontosTimeVisitante > 9) {
                        lGolsTimeVisitante.setText(pontosTimeVisitante + "");
                    } else {
                        lGolsTimeVisitante.setText("0" + pontosTimeVisitante);
                    }

                    trocarCorLabel("white", lGolsTimeVisitante);
                } else {
                    trocarCorLabel("red", lGolsTimeVisitante);
                }
            } catch (IOException ex) {
                trocarCorLabel("red", lGolsTimeVisitante);
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

    }

    @FXML
    void jfxbPausarCronometroOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbRestaurarCronometroOnAction(ActionEvent event) {

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
    void jfxbZerarFaltasOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbZerarGolsOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PONTOS, "zerar", "", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                pontosTimeLocal = 0;
                pontosTimeVisitante = 0;

                lGolsTimeLocal.setText("00");
                lGolsTimeVisitante.setText("00");

                trocarCorLabel("white", lGolsTimeLocal);
                trocarCorLabel("white", lGolsTimeVisitante);
            } else {
                trocarCorLabel("red", lGolsTimeLocal);
                trocarCorLabel("red", lGolsTimeVisitante);
            }
        } catch (IOException ex) {
            trocarCorLabel("red", lGolsTimeLocal);
            trocarCorLabel("red", lGolsTimeVisitante);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
