package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Cena;
import com.acme.model.Comando;
import com.acme.MeuLogger;
import com.acme.model.Escalacao;
import com.acme.model.ListaEscalacoes;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PropagandaController implements Initializable {

    @FXML
    private GridPane gpTelaPropaganda;

    @FXML
    private JFXTextField jfxtfTextoInferior;

    @FXML
    private JFXTextField jfxtfImagemDireita;

    @FXML
    private JFXTextField jfxtfImagemEsquerda;

    @FXML
    private JFXTextField jfxtfPropaganda;

    @FXML
    private JFXTextField jfxtfTextoInferiorL;

    @FXML
    private JFXTextField jfxtfImagemDireitaL;

    @FXML
    private JFXTextField jfxtfImagemEsquerdaL;

    @FXML
    private JFXTextField jfxtfPropagandaL;

    @FXML
    private JFXTextField jfxtfEscalacaoL;

    @FXML
    private JFXTextField jfxtfEscalacaoAtual;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private RespostaSocket respostaComando;

    private String imagemDireita;
    private String imagemEsquerda;
    private String propaganda;

    private boolean propagandaImagem;

    private boolean executandoVideo;

    private String escalacoes;

    public void receberEscalacoes(ListaEscalacoes escalacoes) {
        Escalacao escalacaoLocal = escalacoes.getEscalacoes().get(0);
        Escalacao escalacaoVisitante = escalacoes.getEscalacoes().get(1);

        this.escalacoes = "";

        escalacaoLocal.getJogadores().forEach((j) -> {
            this.escalacoes += j.getPosicao() + "-" + j.getNumero() + "-" + j.getNome() + ">";
        });

        this.escalacoes = this.escalacoes.substring(0, this.escalacoes.length() - 1);

        this.escalacoes += "§";

        escalacaoVisitante.getJogadores().forEach((j) -> {
            this.escalacoes += j.getPosicao() + "-" + j.getNumero() + "-" + j.getNome() + ">";
        });

        this.escalacoes = this.escalacoes.substring(0, this.escalacoes.length() - 1);

        jfxtfEscalacaoAtual.setText("Escalação Adicionada");
    }

    private void erroDeConexao(Exception ex, JFXTextField tf) {
        MeuLogger.logException(Level.SEVERE, "Erro de conexão.", ex);

        trocarCorJFXTextField("red", tf);

        Utils.telaMensagem("Erro de Conexão", "", "Aconteceu algum erro na conexão, verifique se o placar eletrônico está em execução!", Alert.AlertType.ERROR);
    }

    private void trocarCorJFXTextField(String cor, JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setUnFocusColor(Paint.valueOf(cor));
            comp.setFocusColor(Paint.valueOf(cor));
        }
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        if (Utils.telaConfirmacao("Sair", "", "Tem certeza que deseja sair?", Alert.AlertType.CONFIRMATION)) {
            System.exit(0);
        }
    }

    @FXML
    void ovMinimizarOnMouseClicked(MouseEvent event) {
        MainApp.minimizar();
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
    void jfxbAlterarImagemDireitaOnAction(ActionEvent event) {
        if (imagemDireita == null || imagemDireita.trim().isEmpty()) {
            trocarCorJFXTextField("red", jfxtfImagemDireitaL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.IMAGENS, "direita", "alterar", imagemDireita);

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", jfxtfImagemDireitaL);
                } else {
                    trocarCorJFXTextField("red", jfxtfImagemDireitaL);
                }
            } catch (IOException ex) {
                erroDeConexao(ex, jfxtfImagemDireitaL);
            }
        }
    }

    @FXML
    void jfxbAlterarImagemEsquerdaOnAction(ActionEvent event) {
        if (imagemEsquerda == null || imagemEsquerda.trim().isEmpty()) {
            trocarCorJFXTextField("red", jfxtfImagemEsquerdaL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.IMAGENS, "esquerda", "alterar", imagemEsquerda);

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", jfxtfImagemEsquerdaL);
                } else {
                    trocarCorJFXTextField("red", jfxtfImagemEsquerdaL);
                }
            } catch (IOException ex) {
                erroDeConexao(ex, jfxtfImagemEsquerdaL);
            }
        }
    }

    @FXML
    void jfxbAlterarTextoInferiorOnAction(ActionEvent event) {
        String textoInicial = jfxtfTextoInferior.getText();
        String textoAjustado = textoInicial.replaceAll("[^A-Za-z0-9 ]", "");

        if (jfxtfTextoInferior.getText().trim().isEmpty() || !textoInicial.equals(textoAjustado)) {
            trocarCorJFXTextField("red", jfxtfTextoInferiorL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.TEXTO_INFERIOR, "alterar", jfxtfTextoInferior.getText());

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", jfxtfTextoInferiorL);
                } else {
                    trocarCorJFXTextField("red", jfxtfTextoInferiorL);
                }
            } catch (IOException ex) {
                erroDeConexao(ex, jfxtfTextoInferiorL);
            }
        }
    }

    @FXML
    void jfxbExibirEscalacaoOnAction(ActionEvent event) {
        if (escalacoes == null || escalacoes.trim().isEmpty()) {
            trocarCorJFXTextField("red", jfxtfEscalacaoL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "escalacao", "exibir", escalacoes);

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", jfxtfEscalacaoL);
                } else {
                    trocarCorJFXTextField("red", jfxtfEscalacaoL);
                }
            } catch (IOException ex) {
                erroDeConexao(ex, jfxtfEscalacaoL);
            }
        }
    }

    @FXML
    void jfxbExibirPropagandaOnAction(ActionEvent event) {
        if (propaganda == null || propaganda.trim().isEmpty()) {
            trocarCorJFXTextField("red", jfxtfPropagandaL);
        } else {
            try {
                if (propagandaImagem) {
                    respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "imagem", "exibir", propaganda);
                } else {
                    respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "video", "exibir", propaganda);
                }

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", jfxtfPropagandaL);
                    executandoVideo = true;
                } else {
                    trocarCorJFXTextField("red", jfxtfPropagandaL);
                }
            } catch (IOException ex) {
                erroDeConexao(ex, jfxtfPropagandaL);
            }
        }
    }

    @FXML
    void jfxbPararEscalacaoOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "escalacao", "parar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", jfxtfEscalacaoL);
            } else {
                trocarCorJFXTextField("red", jfxtfEscalacaoL);
            }
        } catch (IOException ex) {
            erroDeConexao(ex, jfxtfEscalacaoL);
        }
    }

    @FXML
    void jfxbPararPropagandaOnAction(ActionEvent event) {
        if (executandoVideo) {
            try {
                if (propagandaImagem) {
                    respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "imagem", "parar", "");
                } else {
                    respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "video", "parar", "");
                }

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("white", jfxtfPropagandaL);
                    jfxtfPropaganda.setText("");
                    propaganda = "";
                    executandoVideo = false;
                } else {
                    trocarCorJFXTextField("red", jfxtfPropagandaL);
                }
            } catch (IOException ex) {
                erroDeConexao(ex, jfxtfPropagandaL);
            }
        }
    }

    @FXML
    void jfxbRestaurarImagemDireitaOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.IMAGENS, "direita", "restaurar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", jfxtfImagemDireitaL);
                jfxtfImagemDireita.setText("");
                imagemDireita = "";
            } else {
                trocarCorJFXTextField("red", jfxtfImagemDireitaL);
            }
        } catch (IOException ex) {
            erroDeConexao(ex, jfxtfImagemDireitaL);
        }
    }

    @FXML
    void jfxbRestaurarImagemEsquerdaOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.IMAGENS, "esquerda", "restaurar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", jfxtfImagemEsquerdaL);
                jfxtfImagemEsquerda.setText("");
                imagemEsquerda = "";
            } else {
                trocarCorJFXTextField("red", jfxtfImagemEsquerdaL);
            }
        } catch (IOException ex) {
            erroDeConexao(ex, jfxtfImagemEsquerdaL);
        }
    }

    @FXML
    void jfxbRestaurarTextoInferiorOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.TEXTO_INFERIOR, "restaurar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", jfxtfTextoInferiorL);
                jfxtfTextoInferior.setText("");
            } else {
                trocarCorJFXTextField("red", jfxtfTextoInferiorL);
            }
        } catch (IOException ex) {
            erroDeConexao(ex, jfxtfTextoInferiorL);
        }
    }

    @FXML
    void jfxtfImagemDireitaOnMouseClicked(MouseEvent event) {
        FileChooser fcImagem = new FileChooser();

        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Imagens", "*.bmp", "*.jpeg", "*.jpg", "*.png");

        fcImagem.setTitle("Selecione uma Imagem");
        fcImagem.getExtensionFilters().add(filtro);

        File file = fcImagem.showOpenDialog((Stage) gpTelaPropaganda.getScene().getWindow());

        if (file != null) {
            try {
                imagemDireita = Utils.codificar(file);

                jfxtfImagemDireita.setText(file.getName());

                trocarCorJFXTextField("white", jfxtfImagemDireitaL);
            } catch (IOException ex) {
                MeuLogger.logException(Level.WARNING, "Erro ao codificar arquivo.", ex);
                trocarCorJFXTextField("red", jfxtfImagemDireitaL);
            }
        }
    }

    @FXML
    void jfxtfImagemEsquerdaOnMouseClicked(MouseEvent event) {
        FileChooser fcImagem = new FileChooser();

        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Imagens", "*.bmp", "*.jpeg", "*.jpg", "*.png");

        fcImagem.setTitle("Selecione uma Imagem");
        fcImagem.getExtensionFilters().add(filtro);

        File file = fcImagem.showOpenDialog((Stage) gpTelaPropaganda.getScene().getWindow());

        if (file != null) {
            try {
                imagemEsquerda = Utils.codificar(file);

                jfxtfImagemEsquerda.setText(file.getName());

                trocarCorJFXTextField("white", jfxtfImagemEsquerdaL);
            } catch (IOException ex) {
                MeuLogger.logException(Level.WARNING, "Erro ao codificar arquivo.", ex);
                trocarCorJFXTextField("red", jfxtfImagemEsquerdaL);
            }
        }
    }

    @FXML
    void jfxtfCriarCarregarEscalacaoOnClicked(MouseEvent event) {
        MainApp.trocarCena(Cena.ESCALACAO);
        EscalacaoController.instanciaPropagandaController(this);
    }

    @FXML
    void jfxtfPropagandaOnClicked(MouseEvent event) {
        FileChooser fcImagemVideo = new FileChooser();

        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Imagens/Vídeos", "*.mkv", "*.avi", "*.mpeg", "*.mpg", "*.mp4", "*.wmv", "*.bmp", "*.jpeg", "*.jpg", "*.png");

        fcImagemVideo.setTitle("Selecione uma Imagem ou um Vídeo");
        fcImagemVideo.getExtensionFilters().add(filtro);

        File file = fcImagemVideo.showOpenDialog((Stage) gpTelaPropaganda.getScene().getWindow());

        if (file != null) {
            long fileSizeInBytes = file.length();
            long fileSizeInKB = fileSizeInBytes / 1024;
            long fileSizeInMB = fileSizeInKB / 1024;

            if (fileSizeInMB > 120) {
                trocarCorJFXTextField("red", jfxtfPropagandaL);
            } else {
                String extencao = file.getAbsolutePath();

                if (extencao.endsWith(".mkv") || extencao.endsWith(".avi") || extencao.endsWith(".mpeg") || extencao.endsWith(".mpg") || extencao.endsWith(".mp4") || extencao.endsWith(".wmv") || extencao.endsWith(".bmp") || extencao.endsWith(".jpeg") || extencao.endsWith(".jpg") || extencao.endsWith(".png")) {
                    propagandaImagem = extencao.endsWith(".bmp") || extencao.endsWith(".jpeg") || extencao.endsWith(".jpg") || extencao.endsWith(".png");

                    try {
                        propaganda = Utils.codificar(file);

                        jfxtfPropaganda.setText(file.getName());

                        trocarCorJFXTextField("white", jfxtfPropagandaL);
                    } catch (IOException ex) {
                        MeuLogger.logException(Level.WARNING, "Erro ao codificar arquivo.", ex);
                        trocarCorJFXTextField("red", jfxtfPropagandaL);
                    }
                } else {
                    trocarCorJFXTextField("red", jfxtfPropagandaL);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int maxLength = 30;
        jfxtfTextoInferior.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (jfxtfTextoInferior.getText().length() > maxLength) {
                String s = jfxtfTextoInferior.getText().substring(0, maxLength);
                jfxtfTextoInferior.setText(s);
            }
        });
    }

}
