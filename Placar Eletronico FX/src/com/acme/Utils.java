package com.acme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

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
        File file;

        switch (tipo) {
            case "imagem":
                file = new File("imagem.png");
                break;
            case "video":
                file = new File("video.mp4");
                break;
            default:
                file = new File("imagem.png");
        }

        byte[] bytes = Base64.getDecoder().decode(fileCodificado);

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);

        return file;
    }
}
