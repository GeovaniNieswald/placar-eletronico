package com.acme;

import com.acme.model.Tela;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage stage;

    private static Scene telaAguardandoConexao;
    private static Scene telaPlacarBasquete;
    private static Scene telaPlacarFutsal;
    private static Scene telaPropaganda;

    @Override
    public void start(Stage primaryStage) throws IOException {
        PlacarServer.iniciar();
        
        stage = primaryStage;
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/com/acme/resources/icones/icone.png")));

        Font.loadFont(MainApp.class.getResource("/com/acme/resources/fontes/DS-DIGI.TTF").toExternalForm(), 23.8);

        trocarCena(Tela.AGUARDANDO_CONEXAO);

        stage.show();
    }

    public static void trocarCena(Tela t) {
        try {
            switch (t) {
                case AGUARDANDO_CONEXAO:
                    Parent fxmlConexao = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaAguardandoConexao.fxml"));
                    telaAguardandoConexao = new Scene(fxmlConexao);

                    configurarCena(telaAguardandoConexao, "Aguardando Conexão - Placar Eletrônico FX");
                    break;
                case PLACAR_BASQUETE:
                    Parent fxmlPlacarBasquete = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPlacarBasquete.fxml"));
                    telaPlacarBasquete = new Scene(fxmlPlacarBasquete);

                    configurarCena(telaPlacarBasquete, "Basquete - Placar Eletrônico FX");
                    break;
                case PLACAR_FUTSAL:
                    Parent fxmlPlacarFutsal = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPlacarFutsal.fxml"));
                    telaPlacarFutsal = new Scene(fxmlPlacarFutsal);

                    configurarCena(telaPlacarFutsal, "Futsal - Placar Eletrônico FX");
                    break;
                case PROPAGANDA:
                    Parent fxmlPropaganda = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPropaganda.fxml"));
                    telaPropaganda = new Scene(fxmlPropaganda);

                    configurarCena(telaPropaganda, "Propaganda - Placar Eletrônico FX");
            }
        } catch (IOException ex) {
            // IMPLEMENTAR LOG
        }
    }

    private static void configurarCena(Scene cena, String titulo) {
        stage.setScene(cena);
        stage.setTitle(titulo);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
