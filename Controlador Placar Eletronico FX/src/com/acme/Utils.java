package com.acme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class Utils {

    public static void telaMensagem(String titulo, String cabecalho, String msg, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(msg);

        alerta.showAndWait();
    }

    public static boolean telaConfirmacao(String titulo, String cabecalho, String msg, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(msg);

        ButtonType botaoSim = new ButtonType("Sim", ButtonData.YES);
        ButtonType botaoNao = new ButtonType("Não", ButtonData.NO);
        ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

        alerta.getButtonTypes().setAll(botaoSim, botaoNao, botaoCancelar);

        Optional<ButtonType> resultado = alerta.showAndWait();

        return resultado.get() == botaoSim;
    }

    public static Optional<String> telaPrompt(String titulo, String cabecalho, String msg) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(cabecalho);
        dialog.setContentText(msg);

        Optional<String> resultado = dialog.showAndWait();

        if (resultado.isPresent()) {
            return resultado;
        } else {
            return null;
        }
    }

    public static String codificar(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);

        return new String(Base64.getEncoder().encode(bytes), "UTF-8");
    }

    public static String stringToMd5(String txt) throws NoSuchAlgorithmException {
        if (txt != null) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(txt.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } else {
            return "";
        }
    }
}
