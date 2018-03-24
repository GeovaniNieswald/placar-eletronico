package com.acme;

import com.acme.model.Cena;
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

    private static Scene cenaConexao;
    private static Scene cenaCadUsuario;
    private static Scene cenaEsporte;
    private static Scene cenaEspera;
    private static Scene cenaUsuarioPrincipalBasquete;
    private static Scene cenaUsuarioPrincipalFutsal;
    private static Scene cenaUsuarioPropaganda;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/acme/resources/icones/icone.png")));

        trocarCena(Cena.CONEXAO);

        stage.show();
    }

    public static void moverTela(double x, double y) {
        stage.setX(x);
        stage.setY(y);
    }

    public static void trocarCena(Cena c) {
        try {
            switch (c) {
                case CONEXAO:
                    Parent fxmlConexao = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaConexao.fxml"));
                    cenaConexao = new Scene(fxmlConexao);
                    configurarCena(cenaConexao, "Conexão - Controlador Placar Eletrônico");
                    break;
                case CAD_USUARIO:
                    Parent fxmlCadUsuario = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaCadUsuario.fxml"));
                    cenaCadUsuario = new Scene(fxmlCadUsuario);
                    configurarCena(cenaCadUsuario, "Cadastro de usuário - Controlador Placar Eletrônico");
                    break;
                case ESPORTE:
                    Parent fxmlEsporte = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaEsporte.fxml"));
                    cenaEsporte = new Scene(fxmlEsporte);
                    configurarCena(cenaEsporte, "Seleção do esporte - Controlador Placar Eletrônico");
                    break;
                case ESPERA:
                    Parent fxmlEspera = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaEspera.fxml"));
                    cenaEspera = new Scene(fxmlEspera);
                    configurarCena(cenaEspera, "Esperando - Controlador Placar Eletrônico");
                    break;
                case USUARIO_PRINCIPAL_BASQUETE:
                    Parent fxmlUsuarioPrincipalBasquete = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaUsuarioPrincipalBasquete.fxml"));
                    cenaUsuarioPrincipalBasquete = new Scene(fxmlUsuarioPrincipalBasquete);
                    configurarCena(cenaUsuarioPrincipalBasquete, "Basquete - Controlador Placar Eletrônico");
                    break;
                case USUARIO_PRINCIPAL_FUTSAL:
                    Parent fxmlUsuarioPrincipalFutsal = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaUsuarioPrincipalFutsal.fxml"));
                    cenaUsuarioPrincipalFutsal = new Scene(fxmlUsuarioPrincipalFutsal);
                    configurarCena(cenaUsuarioPrincipalFutsal, "Futsal - Controlador Placar Eletrônico");
                    break;
                case USUARIO_PROPAGANDA:
                    Parent fxmlUsuarioPropaganda = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaUsuarioPropaganda.fxml"));
                    cenaUsuarioPropaganda = new Scene(fxmlUsuarioPropaganda);
                    configurarCena(cenaUsuarioPropaganda, "Propaganda - Controlador Placar Eletrônico");
                default:
                // Implementar log
            }
        } catch (IOException ex) {
            // IMPLEMENTAR LOG
        }
    }

    private static void configurarCena(Scene cena, String titulo) {
        stage.setScene(cena);
        stage.setTitle(titulo);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
