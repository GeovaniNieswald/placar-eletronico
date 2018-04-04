package com.acme;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class Utils {

    // Vai ser trocado por uma mensagem na própria tela
    public static void alert(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

            }
        });
    }

    public static boolean confirm(String titulo, String cabecalho, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(msg);

        ButtonType buttonTypeOne = new ButtonType("Sim");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            return true;
        } else if (result.get() == buttonTypeCancel) {
            return false;
        } else {
            return false;
        }
    }

    public static Optional<String> prompt(String titulo, String cabecalho, String label) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(cabecalho);
        dialog.setContentText(label);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result;
        } else {
            return null;
        }
    }
}
