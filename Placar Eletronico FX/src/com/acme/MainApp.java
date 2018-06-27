package com.acme;

import com.acme.model.Cena;
import com.acme.model.DadosXML;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;

public class MainApp extends Application {

    private static Stage stage;
    private static Scene cenaAtual;

    private static Scene cenaAguardandoConexao;
    private static Scene cenaPlacarBasquete;
    private static Scene cenaPlacarFutsal;
    private static Scene cenaPropaganda;

    private static final KeyCodeCombination COMBINACAO_FECHAR = new KeyCodeCombination(KeyCode.F, KeyCodeCombination.ALT_ANY);

    @Override
    public void start(Stage primaryStage) {
        PlacarServer.iniciar();

        stage = primaryStage;
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/acme/resources/icones/icone.png")));

        Font.loadFont(this.getClass().getResource("/com/acme/resources/fontes/DS-DIGI.TTF").toExternalForm(), 23.8);
        Font.loadFont(this.getClass().getResource("/com/acme/resources/fontes/Comfortaa.ttf").toExternalForm(), 57.8);

        criarPastas();

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
                    cenaAtual = cenaPlacarBasquete;
                    break;
                case PLACAR_FUTSAL:
                    Parent fxmlPlacarFutsal = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPlacarFutsal.fxml"));
                    cenaPlacarFutsal = new Scene(fxmlPlacarFutsal);
                    configurarCena(cenaPlacarFutsal, "Futsal - Placar Eletrônico FX");
                    cenaAtual = cenaPlacarFutsal;
                    break;
                case PROPAGANDA:
                    Parent fxmlPropaganda = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPropaganda.fxml"));
                    cenaPropaganda = new Scene(fxmlPropaganda);
                    configurarCena(cenaPropaganda, "Propaganda - Placar Eletrônico FX");
                    break;
                case ATUAL:
                    configurarCena(cenaAtual, "Placar Eletrônico FX");
                    break;
                default:
                    MeuLogger.logMensagem(Level.WARNING, "Cena informada não está presente entre as opções do switch.");
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.WARNING, "Aqruivo FXML não está presente.", ex);
        }
    }

    private static void configurarCena(Scene cena, String titulo) {
        cena.setCursor(Cursor.NONE);

        stage.setScene(cena);

        stage.setTitle(titulo);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        cena.setOnKeyReleased((KeyEvent event) -> {
            if (COMBINACAO_FECHAR.match(event)) {
                System.exit(0);
            }
        });
    }

    private void criarPastas() {
        String padrao = System.getProperty("user.home") + "/Placar-Eletronico/";

        File file = new File(padrao + "Log");
        file.mkdirs();

        file = new File(padrao + "Temp");
        file.mkdirs();

        file = new File(padrao + "Users");
        file.mkdirs();

        if (!new File(padrao + "Users/usuarios.xml").exists()) {
            try {
                DadosXML.adicionarUsuariosPadrao();
            } catch (IOException | JAXBException ex) {
                MeuLogger.logException(Level.SEVERE, "Não foi possível criar XML padrão.", ex);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
