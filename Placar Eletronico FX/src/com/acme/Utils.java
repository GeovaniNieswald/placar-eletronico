package com.acme;

import java.util.List;

public class Utils {

    public static boolean stringParaBoolean(String str) {
        if (str.equalsIgnoreCase("S") || str.equalsIgnoreCase("T") || str.equalsIgnoreCase("TRUE") || str.equalsIgnoreCase("V")) {
            return true;
        } else {
            return false;
        }
    }

    public static void concat(String listaStr, String separador, String novoValor) {
        if (listaStr.isEmpty()) {
            listaStr += novoValor;
        } else {
            listaStr += separador + novoValor;
        }
    }

}
