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

public class EscalacaoController implements Initializable {

    @FXML
    private Label lTimeLocal;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorLocal;

    @FXML
    private JFXTextField jfxtfNumeroJogadorLocal;

    @FXML
    private JFXTextField jfxtfNomeJogadorLocal;

    @FXML
    private JFXTreeTableView<Jogador> jfxttvTabelaEscalacaoTimeLocal;

    @FXML
    private Label lTimeVisitante;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorVisitante;

    @FXML
    private JFXTextField jfxtfNumeroJogadorVisitante;

    @FXML
    private JFXTextField jfxtfNomeJogadorVisitante;

    @FXML
    private JFXTreeTableView<Jogador> jfxttvTabelaEscalacaoTimeVisitante;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorLocalL;

    @FXML
    private JFXTextField jfxtfNumeroJogadorLocalL;

    @FXML
    private JFXTextField jfxtfNomeJogadorLocalL;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorVisitanteL;

    @FXML
    private JFXTextField jfxtfNumeroJogadorVisitanteL;

    @FXML
    private JFXTextField jfxtfNomeJogadorVisitanteL;

    private static PropagandaController propagandaController;

    // Variáveis para controlar o deslocamento
    private double posicaoInicialX = 0;
    private double posicaoInicialY = 0;

    private ArrayList<ArrayList<Jogador>> escalacao;

    private ObservableList<Jogador> jogadoresTimeLocal;
    private ObservableList<Jogador> jogadoresTimeVisitante;

    public static void instanciaPropagandaController(PropagandaController pc) {
        propagandaController = pc;
    }

    private void trocarCorJFXTextField(String cor, JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setUnFocusColor(Paint.valueOf(cor));
            comp.setFocusColor(Paint.valueOf(cor));
        }
    }

    private boolean validarCampos(JFXTextField campoPosicao, JFXTextField campoNumero, JFXTextField campoNome, JFXTextField labelPosicao, JFXTextField labelNumero, JFXTextField labelNome) {
        boolean camposValidos = true;

        if (campoPosicao.getText().trim().isEmpty() || !campoPosicao.getText().matches("[A-zÀ-ú ç]*")) {
            trocarCorJFXTextField("red", labelPosicao);
            camposValidos = false;
        } else {
            trocarCorJFXTextField("white", labelPosicao);
        }

        if (campoNumero.getText().trim().isEmpty() || !campoNumero.getText().matches("[0-9]*")) {
            trocarCorJFXTextField("red", labelNumero);
            camposValidos = false;
        } else {
            trocarCorJFXTextField("white", labelNumero);
        }

        if (campoNome.getText().trim().isEmpty() || !campoNome.getText().matches("[A-zÀ-ú ç]*")) {
            trocarCorJFXTextField("red", labelNome);
            camposValidos = false;
        } else {
            trocarCorJFXTextField("white", labelNome);
        }

        return camposValidos;
    }

    private void limparCampos(JFXTextField... componentes) {
        for (JFXTextField comp : componentes) {
            comp.setText("");
        }
    }

    private boolean validarEscalacoes(ObservableList<Jogador> jogadoresTimeLocal, ObservableList<Jogador> jogadoresTimeVisitante) {
        boolean escalacoesValidas = true;

        if (jogadoresTimeLocal.isEmpty()) {
            lTimeLocal.setTextFill(Paint.valueOf("red"));
            escalacoesValidas = false;
        } else {
            lTimeLocal.setTextFill(Paint.valueOf("white"));
        }

        if (jogadoresTimeVisitante.isEmpty()) {
            lTimeVisitante.setTextFill(Paint.valueOf("red"));
            escalacoesValidas = false;
        } else {
            lTimeVisitante.setTextFill(Paint.valueOf("white"));
        }

        return escalacoesValidas;
    }

    @FXML
    void faivVoltarOnMouseCliked(MouseEvent event) {
        MainApp.trocarCena(Cena.PROPAGANDA_ATUAL);
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
    void jfxbAdicionarJogadorLocalOnAction(ActionEvent event) {
        if (validarCampos(jfxtfPosicaoJogadorLocal, jfxtfNumeroJogadorLocal, jfxtfNomeJogadorLocal, jfxtfPosicaoJogadorLocalL, jfxtfNumeroJogadorLocalL, jfxtfNomeJogadorLocalL)) {
            jogadoresTimeLocal.add(new Jogador(jfxtfPosicaoJogadorLocal.getText(), jfxtfNumeroJogadorLocal.getText(), jfxtfNomeJogadorLocal.getText()));

            limparCampos(jfxtfPosicaoJogadorLocal, jfxtfNumeroJogadorLocal, jfxtfNomeJogadorLocal);

            lTimeLocal.setTextFill(Paint.valueOf("white"));
        }
    }

    @FXML
    void jfxbAdicionarJogadorVisitanteOnAction(ActionEvent event) {
        if (validarCampos(jfxtfPosicaoJogadorVisitante, jfxtfNumeroJogadorVisitante, jfxtfNomeJogadorVisitante, jfxtfPosicaoJogadorVisitanteL, jfxtfNumeroJogadorVisitanteL, jfxtfNomeJogadorVisitanteL)) {
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
        if (validarEscalacoes(jogadoresTimeLocal, jogadoresTimeVisitante)) {
            ArrayList<Jogador> escalacaoLocal = new ArrayList<>();

            jogadoresTimeLocal.forEach((j) -> {
                escalacaoLocal.add(j);
            });

            ArrayList<Jogador> escalacaoVisitante = new ArrayList<>();

            jogadoresTimeVisitante.forEach((j) -> {
                escalacaoVisitante.add(j);
            });

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
