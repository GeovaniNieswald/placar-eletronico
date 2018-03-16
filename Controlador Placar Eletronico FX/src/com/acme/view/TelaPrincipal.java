/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.view;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Geovani Nieswald
 */
public class TelaPrincipal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/acme/model/TelaPrincipal.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Controlador Placar Eletrônico");

        // Icons made by Smashicons https://www.flaticon.com/authors/smashicons, from Flaticon https://www.flaticon.com/, is licensed by Creative Commons BY 3.0 http://creativecommons.org/licenses/by/3.0/
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/acme/resources/controls(64).png")));

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
