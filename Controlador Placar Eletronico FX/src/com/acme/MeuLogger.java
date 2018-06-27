package com.acme;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MeuLogger {

    private static final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static {
        try {
            Handler fh = new FileHandler(System.getProperty("user.home") + "/Controlador-Placar-Eletronico/Log/log.txt", true);

            fh.setFormatter(new SimpleFormatter());

            LOG.setUseParentHandlers(false);
            LOG.addHandler(fh);
            LOG.setLevel(Level.INFO);
        } catch (IOException | SecurityException ex) {
            ex.printStackTrace();
        }
    }

    public static void logException(Level logLevel, String msg, Exception ex) {
        LOG.log(logLevel, msg, ex);
    }

    public static void logMensagem(Level logLevel, String msg) {
        LOG.log(logLevel, msg);
    }
}
