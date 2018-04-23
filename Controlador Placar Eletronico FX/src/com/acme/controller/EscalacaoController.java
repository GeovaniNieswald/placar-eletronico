package com.acme.controller;

import com.acme.MainApp;
import com.acme.model.Cena;
import com.acme.model.Jogador;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

/**
 * Classe Referente ao controlador da cena de escalação.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class EscalacaoController implements Initializable {

    @FXML
    private Label lTimeLocal;

    @FXML
    private Label lTimeVisitante;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorLocal;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorLocalL;

    @FXML
    private JFXTextField jfxtfNumeroJogadorLocal;

    @FXML
    private JFXTextField jfxtfNumeroJogadorLocalL;

    @FXML
    private JFXTextField jfxtfNomeJogadorLocal;

    @FXML
    private JFXTextField jfxtfNomeJogadorLocalL;

    @FXML
    private JFXTreeTableView<Jogador> jfxttvTabelaEscalacaoTimeLocal;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorVisitante;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorVisitanteL;

    @FXML
    private JFXTextField jfxtfNumeroJogadorVisitante;

    @FXML
    private JFXTextField jfxtfNumeroJogadorVisitanteL;

    @FXML
    private JFXTextField jfxtfNomeJogadorVisitante;

    @FXML
    private JFXTextField jfxtfNomeJogadorVisitanteL;

    @FXML
    private JFXTreeTableView<Jogador> jfxttvTabelaEscalacaoTimeVisitante;

    private static PropagandaController propagandaController;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private ObservableList<Jogador> jogadoresTimeLocal;
    private ObservableList<Jogador> jogadoresTimeVisitante;

    private ArrayList<ArrayList<Jogador>> escalacao;

    public static void instanciaPropagandaController(PropagandaController pc) {
        propagandaController = pc;
    }

    /**
     * Método para trocar a cor dos campos TextField.
     *
     * @param cor String - Hexadecimal da cor quando o campo não está com foco e
     * quando está com foco.
     * @param corFocus String - Hexadecimal da cor quando o campo está com foco.
     * @param componentes JFXTextField - Varargs que contém os campos.
     */
    private void trocarCorJFXTextField(String cor, JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setUnFocusColor(Paint.valueOf(cor));
            comp.setFocusColor(Paint.valueOf(cor));
        }
    }

    private void trocarCorSeVaziu(JFXTextField campo1, JFXTextField campo2, JFXTextField campo3, JFXTextField label1, JFXTextField label2, JFXTextField label3) {
        if (campo1.getText().trim().isEmpty()) {
            trocarCorJFXTextField("red", label1);
        } else {
            trocarCorJFXTextField("white", label1);
        }

        if (campo2.getText().trim().isEmpty()) {
            trocarCorJFXTextField("red", label2);
        } else {
            trocarCorJFXTextField("white", label2);
        }

        if (campo3.getText().trim().isEmpty()) {
            trocarCorJFXTextField("red", label3);
        } else {
            trocarCorJFXTextField("white", label3);
        }
    }

    private void limparCampos(JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setText("");
        }
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        MainApp.trocarCena(Cena.PROPAGANDA_ATUAL);
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
    void jfxbAdicionarJogadorLocalOnAction(ActionEvent event) {
        trocarCorSeVaziu(jfxtfPosicaoJogadorLocal, jfxtfNumeroJogadorLocal, jfxtfNomeJogadorLocal, jfxtfPosicaoJogadorLocalL, jfxtfNumeroJogadorLocalL, jfxtfNomeJogadorLocalL);

        if (!jfxtfPosicaoJogadorLocal.getText().trim().isEmpty() && !jfxtfNumeroJogadorLocal.getText().trim().isEmpty() && !jfxtfNomeJogadorLocal.getText().trim().isEmpty()) {
            jogadoresTimeLocal.add(new Jogador(jfxtfPosicaoJogadorLocal.getText(), jfxtfNumeroJogadorLocal.getText(), jfxtfNomeJogadorLocal.getText()));

            limparCampos(jfxtfPosicaoJogadorLocal, jfxtfNumeroJogadorLocal, jfxtfNomeJogadorLocal);

            lTimeLocal.setTextFill(Paint.valueOf("white"));
        }
    }

    @FXML
    void jfxbAdicionarJogadorVisitanteOnAction(ActionEvent event) {
        trocarCorSeVaziu(jfxtfPosicaoJogadorVisitante, jfxtfNumeroJogadorVisitante, jfxtfNomeJogadorVisitante, jfxtfPosicaoJogadorVisitanteL, jfxtfNumeroJogadorVisitanteL, jfxtfNomeJogadorVisitanteL);

        if (!jfxtfPosicaoJogadorVisitante.getText().trim().isEmpty() && !jfxtfNumeroJogadorVisitante.getText().trim().isEmpty() && !jfxtfNomeJogadorVisitante.getText().trim().isEmpty()) {
            jogadoresTimeVisitante.add(new Jogador(jfxtfPosicaoJogadorVisitante.getText(), jfxtfNumeroJogadorVisitante.getText(), jfxtfNomeJogadorVisitante.getText()));

            limparCampos(jfxtfPosicaoJogadorVisitante, jfxtfNumeroJogadorVisitante, jfxtfNomeJogadorVisitante);

            lTimeVisitante.setTextFill(Paint.valueOf("white"));
        }
    }

    @FXML
    void jfxbRemoverJogadorLocalOnAction(ActionEvent event) {
        int index = jfxttvTabelaEscalacaoTimeLocal.getSelectionModel().getSelectedIndex();

        if (!jogadoresTimeLocal.isEmpty() && index != -1) {
            jogadoresTimeLocal.remove(index);
            lTimeLocal.setTextFill(Paint.valueOf("white"));
        }
    }

    @FXML
    void jfxbRemoverJogadorVisitanteOnAction(ActionEvent event) {
        int index = jfxttvTabelaEscalacaoTimeVisitante.getSelectionModel().getSelectedIndex();

        if (!jogadoresTimeVisitante.isEmpty() && index != -1) {
            jogadoresTimeVisitante.remove(index);
            lTimeVisitante.setTextFill(Paint.valueOf("white"));
        }
    }

    @FXML
    void jfxbSalvarEscalacaoOnAction(ActionEvent event) {
        if (jogadoresTimeLocal.isEmpty()) {
            lTimeLocal.setTextFill(Paint.valueOf("red"));
        } else {
            lTimeLocal.setTextFill(Paint.valueOf("white"));
        }

        if (jogadoresTimeVisitante.isEmpty()) {
            lTimeVisitante.setTextFill(Paint.valueOf("red"));
        } else {
            lTimeVisitante.setTextFill(Paint.valueOf("white"));
        }

        if (!jogadoresTimeLocal.isEmpty() && !jogadoresTimeVisitante.isEmpty()) {
            ArrayList<Jogador> escalacaoLocal = new ArrayList<>();

            for (Jogador j : jogadoresTimeLocal) {
                escalacaoLocal.add(j);
            }

            ArrayList<Jogador> escalacaoVisitante = new ArrayList<>();

            for (Jogador j : jogadoresTimeVisitante) {
                escalacaoVisitante.add(j);
            }

            escalacao.add(escalacaoLocal);
            escalacao.add(escalacaoVisitante);

            propagandaController.receberEscalacao(escalacao);

            lTimeLocal.setTextFill(Paint.valueOf("#09a104"));
            lTimeVisitante.setTextFill(Paint.valueOf("#09a104"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Colunas tabela time local
        JFXTreeTableColumn<Jogador, String> colunaPosicaoLocal = new JFXTreeTableColumn<>("Posição");
        colunaPosicaoLocal.setPrefWidth(242);
        colunaPosicaoLocal.setCellValueFactory((TreeTableColumn.CellDataFeatures<Jogador, String> param) -> param.getValue().getValue().getPosicao());

        JFXTreeTableColumn<Jogador, String> colunaNumeroLocal = new JFXTreeTableColumn<>("Número");
        colunaNumeroLocal.setPrefWidth(242);
        colunaNumeroLocal.setCellValueFactory((TreeTableColumn.CellDataFeatures<Jogador, String> param) -> param.getValue().getValue().getNumero());

        JFXTreeTableColumn<Jogador, String> colunaNomeLocal = new JFXTreeTableColumn<>("Nome");
        colunaNomeLocal.setPrefWidth(242);
        colunaNomeLocal.setCellValueFactory((TreeTableColumn.CellDataFeatures<Jogador, String> param) -> param.getValue().getValue().getNome());

        // Colunas tabela time visitante
        JFXTreeTableColumn<Jogador, String> colunaPosicaoVisitante = new JFXTreeTableColumn<>("Posição");
        colunaPosicaoVisitante.setPrefWidth(242);
        colunaPosicaoVisitante.setCellValueFactory((TreeTableColumn.CellDataFeatures<Jogador, String> param) -> param.getValue().getValue().getPosicao());

        JFXTreeTableColumn<Jogador, String> colunaNumeroVisitante = new JFXTreeTableColumn<>("Número");
        colunaNumeroVisitante.setPrefWidth(242);
        colunaNumeroVisitante.setCellValueFactory((TreeTableColumn.CellDataFeatures<Jogador, String> param) -> param.getValue().getValue().getNumero());

        JFXTreeTableColumn<Jogador, String> colunaNomeVisitante = new JFXTreeTableColumn<>("Nome");
        colunaNomeVisitante.setPrefWidth(242);
        colunaNomeVisitante.setCellValueFactory((TreeTableColumn.CellDataFeatures<Jogador, String> param) -> param.getValue().getValue().getNome());

        jogadoresTimeLocal = FXCollections.observableArrayList();
        jogadoresTimeVisitante = FXCollections.observableArrayList();

        jfxttvTabelaEscalacaoTimeLocal.getColumns().addAll(colunaPosicaoLocal, colunaNumeroLocal, colunaNomeLocal);
        jfxttvTabelaEscalacaoTimeVisitante.getColumns().addAll(colunaPosicaoVisitante, colunaNumeroVisitante, colunaNomeVisitante);

        TreeItem<Jogador> rootTimeLocal = new RecursiveTreeItem<>(jogadoresTimeLocal, RecursiveTreeObject::getChildren);
        TreeItem<Jogador> rootTimeVisitante = new RecursiveTreeItem<>(jogadoresTimeVisitante, RecursiveTreeObject::getChildren);

        jfxttvTabelaEscalacaoTimeLocal.setRoot(rootTimeLocal);
        jfxttvTabelaEscalacaoTimeLocal.setShowRoot(false);

        jfxttvTabelaEscalacaoTimeVisitante.setRoot(rootTimeVisitante);
        jfxttvTabelaEscalacaoTimeVisitante.setShowRoot(false);

        escalacao = new ArrayList<>();
    }
}
