package com.acme;

import com.acme.model.Cena;
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

    private static Scene cenaAguardandoConexao;
    private static Scene cenaPlacarBasquete;
    private static Scene cenaPlacarFutsal;
    private static Scene cenaPropaganda;

    @Override
    public void start(Stage primaryStage) throws IOException {
        PlacarServer.iniciar();
        
        stage = primaryStage;
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/acme/resources/icones/icone.png")));

        Font.loadFont(this.getClass().getResource("/com/acme/resources/fontes/DS-DIGI.TTF").toExternalForm(), 23.8);

        trocarCena(Cena.AGUARDANDO_CONEXAO);

        stage.show();
    }

    public static void trocarCena(Cena c) {
        try {
            switch (c) {
                case AGUARDANDO_CONEXAO:
                    Parent fxmlAguardandoConexao = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaAguardandoConexao.fxml"));
                    cenaAguardandoConexao = new Scene(fxmlAguardandoConexao);
                    configurarCena(cenaAguardandoConexao, "Aguardando Conexão - Placar Eletrônico FX");
                    break;                    
                case PLACAR_BASQUETE:
                    Parent fxmlPlacarBasquete = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPlacarBasquete.fxml"));
                    cenaPlacarBasquete = new Scene(fxmlPlacarBasquete);
                    configurarCena(cenaPlacarBasquete, "Basquete - Placar Eletrônico FX");
                    break;                    
                case PLACAR_FUTSAL:
                    Parent fxmlPlacarFutsal = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPlacarFutsal.fxml"));
                    cenaPlacarFutsal = new Scene(fxmlPlacarFutsal);
                    configurarCena(cenaPlacarFutsal, "Futsal - Placar Eletrônico FX");
                    break;                    
                case PROPAGANDA:
                    Parent fxmlPropaganda = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPropaganda.fxml"));
                    cenaPropaganda = new Scene(fxmlPropaganda);
                    configurarCena(cenaPropaganda, "Propaganda - Placar Eletrônico FX");
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
