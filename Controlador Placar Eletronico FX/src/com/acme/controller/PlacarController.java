package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

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

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private RespostaSocket respostaComando;

    private boolean esporteBasquete;

    private int pontosTimeLocal;
    private int pontosTimeVisitante;

    private int faltasTimeLocal;
    private int faltasTimeVisitante;

    private int periodo = 1;

    private Thread threadCronometro;

    private int minutosCronometro;
    private int segundosCronometro;

    private boolean executandoCronometoPrimeiraVez = true;
    private boolean executandoCronometro;

    private void trocarCorJFXTextField(String cor, JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setUnFocusColor(Paint.valueOf(cor));
            comp.setFocusColor(Paint.valueOf(cor));
        }
    }

    private void trocarCorLabel(String cor, Label componente) {
        componente.setTextFill(Paint.valueOf(cor));
    }

    public boolean isExecutandoCronometro() {
        return executandoCronometro;
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
        this.executandoCronometro = true;

        if (executandoCronometoPrimeiraVez) {
            threadCronometro = new Thread(new Cronos(this, minutos, segundos));
            executandoCronometoPrimeiraVez = false;
            threadCronometro.start();
        } else {
            threadCronometro.resume();
        }
    }

    private void pausarCronometro() {
        this.executandoCronometro = false;
        threadCronometro.suspend();
    }

    public void pararCronometro() {
        this.executandoCronometro = false;
        this.executandoCronometoPrimeiraVez = true;

        jfxbDefinirCronometro.setDisable(false);
        jfxbIniciarCronometro.setDisable(true);
        jfxbPausarCronometro.setDisable(true);
        jfxbRestaurarCronometro.setDisable(true);
        jfxtfMinutos.setEditable(true);
        jfxtfSegundos.setEditable(true);

        threadCronometro.stop();
    }

    private void zerarCronometro() {
        this.executandoCronometro = false;
        this.executandoCronometoPrimeiraVez = true;
        threadCronometro.stop();
    }

    private void iniciarCronometroPlacar() {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.CRONOMETRO, "iniciar", "" + minutosCronometro, "" + segundosCronometro);

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                iniciarCronometro(minutosCronometro + "", segundosCronometro + "");

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
            //IMPLEMENTAR LOG
            trocarCorJFXTextField("red", jfxtfCronometroL);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        if (Utils.telaConfirmacao("Sair", "", "Tem certeza que deseja sair?", Alert.AlertType.CONFIRMATION)) {
            System.exit(0);
        }
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        if (Utils.telaConfirmacao("Voltar", "", "Tem certeza que deseja voltar para a tela de seleção de esporte?", Alert.AlertType.CONFIRMATION)) {
            MainApp.trocarCena(Cena.ESPORTE);
        }
    }

    @FXML
    void gpOnMouseDragged(MouseEvent event) {
        MainApp.moverTela(event.getScreenX() - posicaoInicialX, event.getScreenY() - posicaoInicialY);
    }

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
                // IMPLEMENTAR LOG
                trocarCorJFXTextField("red", jfxtfNomeTimeLocalL);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorJFXTextField("red", jfxtfNomeTimeVisitanteL);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
            }
        }
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lPontosTimeLocal);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lPontosTimeVisitante);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lPontosTimeLocal);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lPontosTimeVisitante);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lPontosTimeLocal);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lPontosTimeVisitante);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lFaltasTimeLocal);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lFaltasTimeVisitante);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorJFXTextField("red", jfxtfPeriodoL);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorJFXTextField("red", jfxtfPeriodoL);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorLabel("red", lPontosTimeLocal);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorLabel("red", lPontosTimeVisitante);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorLabel("red", lPontosTimeLocal);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorLabel("red", lPontosTimeVisitante);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorLabel("red", lPontosTimeLocal);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorLabel("red", lPontosTimeVisitante);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorLabel("red", lFaltasTimeLocal);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
                // IMPLEMENTAR LOG
                trocarCorLabel("red", lFaltasTimeVisitante);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
            }
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
            minutosCronometro = Integer.parseInt(jfxtfMinutos.getText());
            segundosCronometro = Integer.parseInt(jfxtfSegundos.getText());

            try {
                respostaComando = PlacarClient.enviarComando(Comando.CRONOMETRO, "definir", "" + minutosCronometro, "" + segundosCronometro);

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    if (minutosCronometro < 10) {
                        jfxtfMinutos.setText("0" + minutosCronometro);
                    }
                    if (segundosCronometro < 10) {
                        jfxtfSegundos.setText("0" + segundosCronometro);
                    }

                    trocarCorJFXTextField("#09a104", jfxtfMinutos, jfxtfSegundos);
                    jfxbIniciarCronometro.setDisable(false);
                } else {
                    trocarCorJFXTextField("red", jfxtfMinutos, jfxtfSegundos);
                }
            } catch (IOException ex) {
                // IMPLEMENTAR LOG
                trocarCorJFXTextField("red", jfxtfMinutos, jfxtfSegundos);
                Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void jfxbIniciarCronometroOnAction(ActionEvent event) {
        if (executandoCronometoPrimeiraVez) {
            try {
                if (minutosCronometro == Integer.parseInt(jfxtfMinutos.getText()) && segundosCronometro == Integer.parseInt(jfxtfSegundos.getText())) {
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
            // IMPLEMENTAR LOG
            trocarCorJFXTextField("red", jfxtfCronometroL);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void jfxbRestaurarCronometroOnAction(ActionEvent event) {
        try {
            if (esporteBasquete) {
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

                if (esporteBasquete) {
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
            // IMPLEMENTAR LOG
            trocarCorJFXTextField("red", jfxtfCronometroL);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorJFXTextField("red", jfxtfNomeTimeLocalL);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorJFXTextField("red", jfxtfNomeTimeVisitanteL);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lFaltasTimeLocal);
            trocarCorLabel("red", lFaltasTimeVisitante);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorLabel("red", lPontosTimeLocal);
            trocarCorLabel("red", lPontosTimeVisitante);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
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
            // IMPLEMENTAR LOG
            trocarCorJFXTextField("red", jfxtfPeriodoL);
            Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void jfxbRestaurarTudoOnAction(ActionEvent event) {
        if (executandoCronometro) {
            Utils.telaMensagem("", "", "Não é possível restaurar tudo, pois o cronômetro está em execução!", Alert.AlertType.ERROR);
        } else {
            if (Utils.telaConfirmacao("Restaurar Tudo", "", "Deseja mesmo restaurar tudo?", Alert.AlertType.CONFIRMATION)) {
                if (!executandoCronometoPrimeiraVez) {
                    jfxbRestaurarCronometroOnAction(event);
                }
                jfxbRestaurarNomeTimeLocalOnAction(event);
                jfxbRestaurarNomeTimeVisitanteOnAction(event);
                jfxbRestaurarPeriodoOnAction(event);
                jfxbZerarFaltasOnAction(event);
                jfxbZerarPontosOnAction(event);
            }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        esporteBasquete = jfxtfMinutos.getText().equals("10");
    }
}
