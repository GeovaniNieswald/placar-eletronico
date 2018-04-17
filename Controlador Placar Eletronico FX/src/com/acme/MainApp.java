package com.acme;

import com.acme.model.Cena;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Classe Principal da aplicação.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
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
    private static Scene cenaGerenciarUsuarios;

    /**
     * Método que é chamado quando a aplicação é executada. São definidos
     * aspectos visuais (redimensionamento, bordas e icone), e a cena conexao é
     * adicionada a tela, por fim a tela é exibida.
     *
     * @param primaryStage Stage - Primeira tela.
     */
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

    /**
     * Método para mover a tela. Define onde a tela deve ficar localizada, este
     * método é necessário por que quando tiramos a borda da aplicação, não é
     * possível mover a tela.
     *
     * @param x double - Localização horizontal que a tela deve ficar.
     * @param y double - Localização vertical que a tela deve ficar.
     */
    public static void moverTela(double x, double y) {
        stage.setX(x);
        stage.setY(y);
    }

    /**
     * Método que troca a cena da tela. Com base num enum (Cena) passado por
     * parametro é trocada a cena, carrega-se seu fxml então é criada a cena em
     * sí, e é chamado o método de configuração da tela.
     *
     * @param c Cena - Cena que será adicionada a tela.
     */
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
                case PLACAR_PROPAGANDA:
                    Parent fxmlUsuarioPropaganda = FXMLLoader.load(MainApp.class.getResource("/com/acme/view/TelaPropaganda.fxml"));
                    cenaPropaganda = new Scene(fxmlUsuarioPropaganda);
                    configurarTela(cenaPropaganda, "Propaganda - Controlador Placar Eletrônico");
                    break;
                default:
                // Implementar log
            }
        } catch (IOException ex) {
            // IMPLEMENTAR LOG
        }
    }

    /**
     * Método que configura a tela. Define a cena da tela, juntamente com o
     * título.
     *
     * @param cena Scene - Cena que será colocada na tela.
     * @param titulo String - Título da tela.
     */
    private static void configurarTela(Scene cena, String titulo) {
        stage.setScene(cena);
        stage.setTitle(titulo);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
