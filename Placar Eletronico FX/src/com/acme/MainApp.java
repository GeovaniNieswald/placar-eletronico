package com.acme;

import com.acme.model.Tela;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage stage;

    private static Scene telaAguardandoConexao;
    private static Scene telaPlacarBasquete;
    private static Scene telaPlacarFutsal;
    private static Scene telaPropaganda;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        Parent fxmlConexao = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaAguardandoConexao.fxml"));
        telaAguardandoConexao = new Scene(fxmlConexao);

        Parent fxmlPlacarBasquete = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaPlacarBasquete.fxml"));
        telaPlacarBasquete = new Scene(fxmlPlacarBasquete);

        Parent fxmlPlacarFutsal = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaPlacarFutsal.fxml"));
        telaPlacarFutsal = new Scene(fxmlPlacarFutsal);

        Parent fxmlPropaganda = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaPropaganda.fxml"));
        telaPropaganda = new Scene(fxmlPropaganda);

        configurarTela(telaAguardandoConexao, "Aguardando Conexão - Placar Eletrônico FX");
        stage.show();
    }

    public static void trocarTela(Tela t) {
        switch (t) {
            case AGUARDANDO_CONEXAO:
                configurarTela(telaAguardandoConexao, "Aguardando Conexão - Placar Eletrônico FX");
                break;
            case PLACAR_BASQUETE:
                configurarTela(telaPlacarBasquete, "Basquete - Placar Eletrônico FX");
                break;
            case PLACAR_FUTSAL:
                configurarTela(telaPlacarFutsal, "Futsal - Placar Eletrônico FX");
                break;
            case PROPAGANDA:
                configurarTela(telaPropaganda, "Propaganda - Placar Eletrônico FX");
        }
    }

    private static void configurarTela(Scene tela, String titulo) {
        stage.setScene(tela);
        stage.setTitle(titulo);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        //stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/com/acme/resources/controls(64).png")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
