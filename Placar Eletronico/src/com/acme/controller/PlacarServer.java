package com.acme.controller;

import java.net.ServerSocket;

public class PlacarServer {

    public static void start() throws Exception {
        System.out.println("O servidor do placar foi iniciado.");
        int clientNumber = 0;
        try (ServerSocket listener = new ServerSocket(9898)) {
            while (true) {
                new ControladorPlacar(listener.accept(), clientNumber++).start();
            }
        }
    }

}
