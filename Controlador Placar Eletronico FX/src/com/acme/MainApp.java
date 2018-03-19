package com.acme;

import com.acme.model.Tela;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage stage;

    private static Scene telaConexao;
    private static Scene telaCadUsuario;
    private static Scene telaEsporte;
    private static Scene telaEspera;
    private static Scene telaUsuarioPrincipal;
    private static Scene telaUsuarioPropaganda;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        Parent fxmlConexao = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaConexao.fxml"));
        telaConexao = new Scene(fxmlConexao);

        Parent fxmlCadUsuario = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaCadUsuario.fxml"));
        telaCadUsuario = new Scene(fxmlCadUsuario);

        Parent fxmlEsporte = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaEsporte.fxml"));
        telaEsporte = new Scene(fxmlEsporte);

        Parent fxmlEspera = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaEspera.fxml"));
        telaEspera = new Scene(fxmlEspera);

        Parent fxmlUsuarioPrincipal = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaUsuarioPrincipal.fxml"));
        telaUsuarioPrincipal = new Scene(fxmlUsuarioPrincipal);

        Parent fxmlUsuarioPropaganda = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaUsuarioPropaganda.fxml"));
        telaUsuarioPropaganda = new Scene(fxmlUsuarioPropaganda);

        configurarTela(telaConexao, "Conexão - Controlador Placar Eletrônico");
        stage.show();
    }

    public static void trocarTela(Tela t) {
        switch (t) {
            case CONEXAO:
                configurarTela(telaConexao, "Conexão - Controlador Placar Eletrônico");
                break;
            case CAD_USUARIO:
                configurarTela(telaCadUsuario, "Cadastro de Usuário - Controlador Placar Eletrônico");
                break;
            case ESPORTE:
                configurarTela(telaEsporte, "Selecione o Esporte - Controlador Placar Eletrônico");
                break;
            case ESPERA:
                configurarTela(telaEspera, "Esperando - Controlador Placar Eletrônico");
                break;
            case USUARIO_PRINCIPAL:
                configurarTela(telaUsuarioPrincipal, "Controlador Placar Eletrônico");
                break;
            case USUARIO_PROPAGANDA:
                configurarTela(telaUsuarioPropaganda, "Controlador Placar Eletrônico");
        }
    }

    private static void configurarTela(Scene tela, String titulo) {
        stage.setScene(tela);
        stage.setTitle(titulo);
        stage.setResizable(false);
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/com/acme/resources/controls(64).png")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
