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

    public static File decodificar(String fileCodificado) throws IOException {
        File file = new File("teste.png");

        byte[] bytes = Base64.getDecoder().decode(fileCodificado);

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);

        return file;
    }
}
