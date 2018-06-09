package com.acme.controller;

import com.acme.MainApp;
import com.acme.model.Cena;
import com.acme.model.DadosXML;
import com.acme.model.Escalacao;
import com.acme.model.Jogador;
import com.acme.model.JogadorTabela;
import com.acme.model.ListaEscalacoes;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.bind.JAXBException;

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
    private JFXTreeTableView<JogadorTabela> jfxttvTabelaEscalacaoTimeLocal;

    @FXML
    private Label lTimeVisitante;

    @FXML
    private JFXTextField jfxtfPosicaoJogadorVisitante;

    @FXML
    private JFXTextField jfxtfNumeroJogadorVisitante;

    @FXML
    private JFXTextField jfxtfNomeJogadorVisitante;

    @FXML
    private JFXTreeTableView<JogadorTabela> jfxttvTabelaEscalacaoTimeVisitante;

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

    private ObservableList<JogadorTabela> jogadoresTimeLocal;
    private ObservableList<JogadorTabela> jogadoresTimeVisitante;

    private ListaEscalacoes escalacoes;

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

    private boolean validarEscalacoes(ObservableList<JogadorTabela> jogadoresTimeLocal, ObservableList<JogadorTabela> jogadoresTimeVisitante) {
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
            jogadoresTimeLocal.add(new JogadorTabela(jfxtfPosicaoJogadorLocal.getText(), jfxtfNumeroJogadorLocal.getText(), jfxtfNomeJogadorLocal.getText()));

            limparCampos(jfxtfPosicaoJogadorLocal, jfxtfNumeroJogadorLocal, jfxtfNomeJogadorLocal);

            lTimeLocal.setTextFill(Paint.valueOf("white"));
        }
    }

    @FXML
    void jfxbAdicionarJogadorVisitanteOnAction(ActionEvent event) {
        if (validarCampos(jfxtfPosicaoJogadorVisitante, jfxtfNumeroJogadorVisitante, jfxtfNomeJogadorVisitante, jfxtfPosicaoJogadorVisitanteL, jfxtfNumeroJogadorVisitanteL, jfxtfNomeJogadorVisitanteL)) {
            jogadoresTimeVisitante.add(new JogadorTabela(jfxtfPosicaoJogadorVisitante.getText(), jfxtfNumeroJogadorVisitante.getText(), jfxtfNomeJogadorVisitante.getText()));

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
    void jfxbAdicionarEscalacaoOnAction(ActionEvent event) {
        if (validarEscalacoes(jogadoresTimeLocal, jogadoresTimeVisitante)) {
            ArrayList<Jogador> jogadoresLocal = new ArrayList<>();
            ArrayList<Jogador> jogadoresVisitante = new ArrayList<>();

            jogadoresTimeLocal.forEach((j) -> {
                Jogador jAux = new Jogador(j.getPosicao().get(), j.getNumero().get(), j.getNome().get());
                jogadoresLocal.add(jAux);
            });

            jogadoresTimeVisitante.forEach((j) -> {
                Jogador jAux = new Jogador(j.getPosicao().get(), j.getNumero().get(), j.getNome().get());
                jogadoresVisitante.add(jAux);
            });

            Escalacao timeLocal = new Escalacao(true, jogadoresLocal);
            Escalacao timeVisitante = new Escalacao(false, jogadoresVisitante);

            ArrayList<Escalacao> listaEscalacoes = new ArrayList<>();
            listaEscalacoes.add(timeLocal);
            listaEscalacoes.add(timeVisitante);

            escalacoes.setEscalacoes(listaEscalacoes);

            propagandaController.receberEscalacoes(escalacoes);

            lTimeLocal.setTextFill(Paint.valueOf("#09a104"));
            lTimeVisitante.setTextFill(Paint.valueOf("#09a104"));
        }
    }

    @FXML
    void jfxbCarregarXMLOnAction(ActionEvent event) {

    }

    @FXML
    void jfxbSalvarXMLOnAction(ActionEvent event) {
        try {
            DadosXML.salvarEscalacoes("C:\\Users\\Geovani Nieswald\\Controlador-Placar-Eletronico\\escalacoes.xml", escalacoes);
        } catch (JAXBException ex) {
            Logger.getLogger(EscalacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Colunas tabela time local
        JFXTreeTableColumn<JogadorTabela, String> colunaPosicaoLocal = new JFXTreeTableColumn<>("Posição");
        colunaPosicaoLocal.setPrefWidth(242);
        colunaPosicaoLocal.setCellValueFactory((TreeTableColumn.CellDataFeatures<JogadorTabela, String> param) -> param.getValue().getValue().getPosicao());

        JFXTreeTableColumn<JogadorTabela, String> colunaNumeroLocal = new JFXTreeTableColumn<>("Número");
        colunaNumeroLocal.setPrefWidth(242);
        colunaNumeroLocal.setCellValueFactory((TreeTableColumn.CellDataFeatures<JogadorTabela, String> param) -> param.getValue().getValue().getNumero());

        JFXTreeTableColumn<JogadorTabela, String> colunaNomeLocal = new JFXTreeTableColumn<>("Nome");
        colunaNomeLocal.setPrefWidth(242);
        colunaNomeLocal.setCellValueFactory((TreeTableColumn.CellDataFeatures<JogadorTabela, String> param) -> param.getValue().getValue().getNome());

        // Colunas tabela time visitante
        JFXTreeTableColumn<JogadorTabela, String> colunaPosicaoVisitante = new JFXTreeTableColumn<>("Posição");
        colunaPosicaoVisitante.setPrefWidth(242);
        colunaPosicaoVisitante.setCellValueFactory((TreeTableColumn.CellDataFeatures<JogadorTabela, String> param) -> param.getValue().getValue().getPosicao());

        JFXTreeTableColumn<JogadorTabela, String> colunaNumeroVisitante = new JFXTreeTableColumn<>("Número");
        colunaNumeroVisitante.setPrefWidth(242);
        colunaNumeroVisitante.setCellValueFactory((TreeTableColumn.CellDataFeatures<JogadorTabela, String> param) -> param.getValue().getValue().getNumero());

        JFXTreeTableColumn<JogadorTabela, String> colunaNomeVisitante = new JFXTreeTableColumn<>("Nome");
        colunaNomeVisitante.setPrefWidth(242);
        colunaNomeVisitante.setCellValueFactory((TreeTableColumn.CellDataFeatures<JogadorTabela, String> param) -> param.getValue().getValue().getNome());

        jogadoresTimeLocal = FXCollections.observableArrayList();
        jogadoresTimeVisitante = FXCollections.observableArrayList();

        jfxttvTabelaEscalacaoTimeLocal.getColumns().addAll(colunaPosicaoLocal, colunaNumeroLocal, colunaNomeLocal);
        jfxttvTabelaEscalacaoTimeVisitante.getColumns().addAll(colunaPosicaoVisitante, colunaNumeroVisitante, colunaNomeVisitante);

        TreeItem<JogadorTabela> rootTimeLocal = new RecursiveTreeItem<>(jogadoresTimeLocal, RecursiveTreeObject::getChildren);
        TreeItem<JogadorTabela> rootTimeVisitante = new RecursiveTreeItem<>(jogadoresTimeVisitante, RecursiveTreeObject::getChildren);

        jfxttvTabelaEscalacaoTimeLocal.setRoot(rootTimeLocal);
        jfxttvTabelaEscalacaoTimeLocal.setShowRoot(false);

        jfxttvTabelaEscalacaoTimeVisitante.setRoot(rootTimeVisitante);
        jfxttvTabelaEscalacaoTimeVisitante.setShowRoot(false);

        escalacoes = new ListaEscalacoes();
    }
}
