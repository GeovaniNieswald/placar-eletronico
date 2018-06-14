package com.acme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;

public class Utils {

    public static boolean stringParaBoolean(String str) {
        return str.equalsIgnoreCase("S") || str.equalsIgnoreCase("T") || str.equalsIgnoreCase("TRUE") || str.equalsIgnoreCase("V");
    }

    public static void concat(String listaStr, String separador, String novoValor) {
        if (listaStr.isEmpty()) {
            listaStr += novoValor;
        } else {
            listaStr += separador + novoValor;
        }
    }

    public static File decodificar(String tipo, String fileCodificado) throws IOException {
        File file = null;

        switch (tipo) {
            case "imagem":
                file = new File("imagem.png");
                break;
            case "video":
                file = new File("video.mp4");
                break;
            default:
                MeuLogger.logMensagem(Level.WARNING, "Tipo de arquivo informado não está presente entre as opções do switch.");
        }

        byte[] bytes = Base64.getDecoder().decode(fileCodificado);

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);

        return file;
    }

    public static String stringToMd5(String txt) throws NoSuchAlgorithmException {
        if (txt != null) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(txt.getBytes());
            return new BigInteger(1, md.digest()).toString(16).toUpperCase();
        } else {
            return "";
        }
    }
}
