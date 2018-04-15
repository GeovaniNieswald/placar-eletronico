package com.acme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 * Classe que contém métodos úteis para o programa.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class Utils {

    /**
     * Método que exibe uma tela com uma mensagem.
     *
     * @param msg String - Mensagem que será exibida.
     * @param tipo Alert.AlertType - Tipo de tela.
     */
    public static void alert(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            }
        });
    }

    /**
     * Método que exibe uma tela de confirmação.
     *
     * @param titulo String - Título da tela de confirmação.
     * @param cabecalho String - Cabeçalho da tela de confirmação.
     * @param msg String - Mensagem que será exibida.
     * @param tipo Alert.AlertType - Tipo de tela.
     * @return boolean - True ou False dependendo do botão clicado.
     */
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

    /**
     * Método que exibe uma tela para digitar uma informação.
     *
     * @param titulo String - Título da tela.
     * @param cabecalho String - Cabeçalho da tela.
     * @param msg String - Mensagem que será exibida.
     * @return Optional<String> - Informação digitada.
     */
    public static Optional<String> prompt(String titulo, String cabecalho, String msg) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(cabecalho);
        dialog.setContentText(msg);

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * Método que codifica um File para uma String Base64.
     *
     * @param file File - File que será codificado.
     * @return String - String que contêm um File codificado.
     * @throws java.io.IOException
     */
    public static String codificar(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);

        String fileCodificado = new String(Base64.getEncoder().encode(bytes), "UTF-8");

        return fileCodificado;
    }
}
