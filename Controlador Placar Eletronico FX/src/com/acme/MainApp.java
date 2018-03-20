package com.acme;

import com.acme.model.Tela;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private static Stage stage;
//
    private static Scene telaConexao;
    private static Scene telaCadUsuario;
    private static Scene telaEsporte;
    private static Scene telaEspera;
    private static Scene telaUsuarioPrincipal;
    private static Scene telaUsuarioPropaganda;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        trocarCena(Tela.CONEXAO);

        stage.show();
    }

    public static void trocarCena(Tela t) throws IOException {
        switch (t) {
            case CONEXAO:
                Parent fxmlConexao = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaConexao.fxml"));
                telaConexao = new Scene(fxmlConexao);
                
                configurarCena(telaConexao, "Conexão - Controlador Placar Eletrônico");
                break;
            case CAD_USUARIO:
                Parent fxmlCadUsuario = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaCadUsuario.fxml"));
                telaCadUsuario = new Scene(fxmlCadUsuario);

                configurarCena(telaCadUsuario, "Cadastro de Usuário - Controlador Placar Eletrônico");
                break;
            case ESPORTE:
                Parent fxmlEsporte = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaEsporte.fxml"));
                telaEsporte = new Scene(fxmlEsporte);

                configurarCena(telaEsporte, "Selecione o Esporte - Controlador Placar Eletrônico");
                break;
            case ESPERA:
                Parent fxmlEspera = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaEspera.fxml"));
                telaEspera = new Scene(fxmlEspera);

                configurarCena(telaEspera, "Esperando - Controlador Placar Eletrônico");
                break;
            case USUARIO_PRINCIPAL:
                Parent fxmlUsuarioPrincipal = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaUsuarioPrincipal.fxml"));
                telaUsuarioPrincipal = new Scene(fxmlUsuarioPrincipal);

                configurarCena(telaUsuarioPrincipal, "Controlador Placar Eletrônico");
                break;
            case USUARIO_PROPAGANDA:
                Parent fxmlUsuarioPropaganda = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaUsuarioPropaganda.fxml"));
                telaUsuarioPropaganda = new Scene(fxmlUsuarioPropaganda);

                configurarCena(telaUsuarioPropaganda, "Controlador Placar Eletrônico");
        }
    }

    private static void configurarCena(Scene cena, String titulo) {
        stage.setScene(cena);
        stage.setTitle(titulo);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/com/acme/resources/icones/controls(64).png")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
