package com.acme.controller;

import com.acme.MainApp;
import com.acme.PlacarClient;
import com.acme.Utils;
import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Classe Referente ao controlador da cena de propaganda.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
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
    private JFXTextField jfxtfEscalacoes;

    @FXML
    private JFXTextField jfxtfTextoInferiorL;

    @FXML
    private JFXTextField jfxtfImagemDireitaL;

    @FXML
    private JFXTextField jfxtfImagemEsquerdaL;

    @FXML
    private JFXTextField jfxtfPropagandaL;

    @FXML
    private JFXTextField jfxtfEscalacoesL;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private RespostaSocket respostaComando;

    private String imagemDireita;
    private String imagemEsquerda;
    private String propaganda;

    private boolean propagandaImagem;

    /**
     * Método para trocar a cor dos campos TextField.
     *
     * @param corUnFocus String - Hexadecimal da cor quando o campo não está com
     * foco.
     * @param corFocus String - Hexadecimal da cor quando o campo está com foco.
     * @param componentes JFXTextField - Varargs que contém os campos.
     */
    private void trocarCorJFXTextField(String corUnFocus, String corFocus, JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setUnFocusColor(Paint.valueOf(corUnFocus));
            comp.setFocusColor(Paint.valueOf(corFocus));
        }
    }

    @FXML
    void faivSairOnMouseCliked(MouseEvent event) {
        // Pedir confirmação
        System.exit(0);
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
    void jfxbAlterarImagemDireitaOnAction(ActionEvent event) {
        if (imagemDireita == null || imagemDireita.trim().isEmpty()) {
            trocarCorJFXTextField("red", "red", jfxtfImagemDireitaL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.IMAGENS, "direita", "alterar", imagemDireita);

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", "#09a104", jfxtfImagemDireitaL);
                } else {
                    trocarCorJFXTextField("red", "red", jfxtfImagemDireitaL);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", "red", jfxtfImagemDireitaL);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbAlterarImagemEsquerdaOnAction(ActionEvent event) {
        if (imagemEsquerda == null || imagemEsquerda.trim().isEmpty()) {
            trocarCorJFXTextField("red", "red", jfxtfImagemEsquerdaL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.IMAGENS, "esquerda", "alterar", imagemEsquerda);

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", "#09a104", jfxtfImagemEsquerdaL);
                } else {
                    trocarCorJFXTextField("red", "red", jfxtfImagemEsquerdaL);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", "red", jfxtfImagemEsquerdaL);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    /**
     * Método que altera o texto inferior do placar eletrônico, fazendo as
     * verificações de acentuação e etc.
     *
     * @param event MouseEvent.
     */
    @FXML
    void jfxbAlterarTextoInferiorOnAction(ActionEvent event) {
        String textoInicial = jfxtfTextoInferior.getText();
        String textoAjustado = textoInicial.replaceAll("[^A-Za-z0-9 ]", "");

        if (jfxtfTextoInferior.getText().trim().isEmpty() || !textoInicial.equals(textoAjustado)) {
            trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
        } else {
            try {
                respostaComando = PlacarClient.enviarComando(Comando.TEXTO_INFERIOR, "alterar", jfxtfTextoInferior.getText());

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", "#09a104", jfxtfTextoInferiorL);
                } else {
                    trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbExibirEscalacoesOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbExibirPropagandaOnAction(ActionEvent event) {
        if (propaganda == null || propaganda.trim().isEmpty()) {
            trocarCorJFXTextField("red", "red", jfxtfPropagandaL);
        } else {
            try {
                if (propagandaImagem) {
                    respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "imagem", "exibir", propaganda);
                } else {
                    respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "video", "exibir", propaganda);
                }

                if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                    trocarCorJFXTextField("#09a104", "#09a104", jfxtfPropagandaL);
                } else {
                    trocarCorJFXTextField("red", "red", jfxtfPropagandaL);
                }
            } catch (IOException ex) {
                trocarCorJFXTextField("red", "red", jfxtfPropagandaL);
                // Mostrar msg de erro de conexão
                // IMPLEMENTAR LOG
            }
        }
    }

    @FXML
    void jfxbPararEscalacoesOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbPararPropagandaOnAction(ActionEvent event) {
        try {
            if (propagandaImagem) {
                respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "imagem", "parar", "");
            } else {
                respostaComando = PlacarClient.enviarComando(Comando.PROPAGANDA, "video", "parar", "");
            }

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", "white", jfxtfPropagandaL);
                jfxtfPropaganda.setText("");
                propaganda = "";
            } else {
                trocarCorJFXTextField("red", "red", jfxtfPropagandaL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", "red", jfxtfPropagandaL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbRestaurarImagemDireitaOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.IMAGENS, "direita", "restaurar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", "white", jfxtfImagemDireitaL);
                jfxtfImagemDireita.setText("");
                imagemDireita = "";
            } else {
                trocarCorJFXTextField("red", "red", jfxtfImagemDireitaL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", "red", jfxtfImagemDireitaL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxbRestaurarImagemEsquerdaOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.IMAGENS, "esquerda", "restaurar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", "white", jfxtfImagemEsquerdaL);
                jfxtfImagemEsquerda.setText("");
                imagemEsquerda = "";
            } else {
                trocarCorJFXTextField("red", "red", jfxtfImagemEsquerdaL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", "red", jfxtfImagemEsquerdaL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    /**
     * Método que restaura o texto inferior do placar eletrônico.
     *
     * @param event MouseEvent.
     */
    @FXML
    void jfxbRestaurarTextoInferiorOnAction(ActionEvent event) {
        try {
            respostaComando = PlacarClient.enviarComando(Comando.TEXTO_INFERIOR, "restaurar", "");

            if (respostaComando == RespostaSocket.COMANDO_ACEITO) {
                trocarCorJFXTextField("white", "white", jfxtfTextoInferiorL);
                jfxtfTextoInferior.setText("");
            } else {
                trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
            }
        } catch (IOException ex) {
            trocarCorJFXTextField("red", "red", jfxtfTextoInferiorL);
            // Mostrar msg de erro de conexão
            // IMPLEMENTAR LOG
        }
    }

    @FXML
    void jfxtfEscalacoesOnClicked(MouseEvent event) {

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

                trocarCorJFXTextField("white", "white", jfxtfImagemDireitaL);
            } catch (IOException ex) {
                trocarCorJFXTextField("red", "red", jfxtfImagemDireitaL);
                // Implementar log
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

                trocarCorJFXTextField("white", "white", jfxtfImagemEsquerdaL);
            } catch (IOException ex) {
                trocarCorJFXTextField("red", "red", jfxtfImagemEsquerdaL);
                // Implementar log
            }
        }
    }

    @FXML
    void jfxtfPropagandaOnClicked(MouseEvent event) {
        FileChooser fcImagemVideo = new FileChooser();

        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Imagens/Vídeos", "*.mkv", "*.avi", "*.mpeg", "*.mpg", "*.mp4", "*.wmv", "*.bmp", "*.jpeg", "*.jpg", "*.png");

        fcImagemVideo.setTitle("Selecione uma Imagem ou um Vídeo");
        fcImagemVideo.getExtensionFilters().add(filtro);

        File file = fcImagemVideo.showOpenDialog((Stage) gpTelaPropaganda.getScene().getWindow());

        if (file != null) {
            String extencao = file.getAbsolutePath();

            if (extencao.endsWith(".mkv") || extencao.endsWith(".avi") || extencao.endsWith(".mpeg") || extencao.endsWith(".mpg") || extencao.endsWith(".mp4") || extencao.endsWith(".wmv") || extencao.endsWith(".bmp") || extencao.endsWith(".jpeg") || extencao.endsWith(".jpg") || extencao.endsWith(".png")) {
                propagandaImagem = extencao.endsWith(".bmp") || extencao.endsWith(".jpeg") || extencao.endsWith(".jpg") || extencao.endsWith(".png");

                try {
                    propaganda = Utils.codificar(file);

                    jfxtfPropaganda.setText(file.getName());

                    trocarCorJFXTextField("white", "white", jfxtfPropagandaL);
                } catch (IOException ex) {
                    trocarCorJFXTextField("red", "red", jfxtfPropagandaL);
                    // Implementar log
                }
            } else {
                trocarCorJFXTextField("red", "red", jfxtfPropagandaL);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
