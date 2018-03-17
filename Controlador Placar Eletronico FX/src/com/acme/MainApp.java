package com.acme;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/acme/view/TelaUsuarioPrincipal.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Conexão - Controlador Placar Eletrônico");

        // Icons made by Smashicons https://www.flaticon.com/authors/smashicons, from Flaticon https://www.flaticon.com/, is licensed by Creative Commons BY 3.0 http://creativecommons.org/licenses/by/3.0/
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/acme/resources/controls(64).png")));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
