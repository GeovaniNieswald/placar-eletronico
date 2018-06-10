package com.acme;

import com.acme.model.Cena;
import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    // Variável referente a tela principal da aplicação (palco onde as cenas aparecerão).
    private static Stage stage;

    // Cenas da aplicação.
    private static Scene cenaConexao;
    private static Scene cenaCadastroUsuario;
    private static Scene cenaEsporte;
    private static Scene cenaEspera;
    private static Scene cenaPlacarBasquete;
    private static Scene cenaPlacarFutsal;
    private static Scene cenaPropaganda;
    private static Scene cenaEscalacao;
    private static Scene cenaGerenciarUsuarios;
    private static Scene cenaPropagandaAtual;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/acme/resources/icones/icone.png")));

        Font.loadFont(this.getClass().getResource("/com/acme/resources/fontes/Comfortaa.ttf").toExternalForm(), 57.8);

        trocarCena(Cena.CONEXAO);

        stage.show();
    }

    public static void moverTela(double x, double y) {
        stage.setX(x);
        stage.setY(y);
    }
    
    public static void minimizar(){
        stage.setIconified(true);
    }

    public static void trocarCena(Cena c) {
        try {
            switch (c) {
                case CONEXAO:
                    Parent fxmlConexao = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaConexao.fxml"));
                    cenaConexao = new Scene(fxmlConexao);
                    configurarTela(cenaConexao, "Conexão - Controlador Placar Eletrônico");
                    break;
                case GERENCIAR_USUARIOS:
                    Parent fxmlGerUsuario = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaGerenciarUsuarios.fxml"));
                    cenaGerenciarUsuarios = new Scene(fxmlGerUsuario);
                    configurarTela(cenaGerenciarUsuarios, "Usuários - Controlador Placar Eletrônico");
                    break;
                case CADASTRO_USUARIO:
                    Parent fxmlCadUsuario = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaCadastroUsuario.fxml"));
                    cenaCadastroUsuario = new Scene(fxmlCadUsuario);
                    configurarTela(cenaCadastroUsuario, "Cadastro de usuário - Controlador Placar Eletrônico");
                    break;
                case ESPORTE:
                    Parent fxmlEsporte = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaEsporte.fxml"));
                    cenaEsporte = new Scene(fxmlEsporte);
                    configurarTela(cenaEsporte, "Seleção do esporte - Controlador Placar Eletrônico");
                    break;
                case ESPERA:
                    Parent fxmlEspera = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaEspera.fxml"));
                    cenaEspera = new Scene(fxmlEspera);
                    configurarTela(cenaEspera, "Esperando - Controlador Placar Eletrônico");
                    break;
                case PLACAR_BASQUETE:
                    Parent fxmlUsuarioPrincipalBasquete = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPlacarBasquete.fxml"));
                    cenaPlacarBasquete = new Scene(fxmlUsuarioPrincipalBasquete);
                    configurarTela(cenaPlacarBasquete, "Basquete - Controlador Placar Eletrônico");
                    break;
                case PLACAR_FUTSAL:
                    Parent fxmlUsuarioPrincipalFutsal = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPlacarFutsal.fxml"));
                    cenaPlacarFutsal = new Scene(fxmlUsuarioPrincipalFutsal);
                    configurarTela(cenaPlacarFutsal, "Futsal - Controlador Placar Eletrônico");
                    break;
                case PROPAGANDA:
                    Parent fxmlUsuarioPropaganda = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPropaganda.fxml"));
                    cenaPropaganda = new Scene(fxmlUsuarioPropaganda);
                    configurarTela(cenaPropaganda, "Propaganda - Controlador Placar Eletrônico");
                    cenaPropagandaAtual = cenaPropaganda;
                    break;
                case ESCALACAO:
                    Parent fxmlEscalacao = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaEscalacao.fxml"));
                    cenaEscalacao = new Scene(fxmlEscalacao);
                    configurarTela(cenaEscalacao, "Escalação - Propaganda - Controlador Placar Eletrônico");
                    break;
                case PROPAGANDA_ATUAL:
                    configurarTela(cenaPropagandaAtual, "Propaganda - Controlador Placar Eletrônico");
                    break;
                default:
                    MeuLogger.logMensagem(Level.WARNING, "Cena informada não está presente entre as opções do switch.");
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.WARNING, "Aqruivo FXML não está presente.", ex);
        }
    }

    private static void configurarTela(Scene cena, String titulo) {
        stage.setScene(cena);
        stage.setTitle(titulo);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
