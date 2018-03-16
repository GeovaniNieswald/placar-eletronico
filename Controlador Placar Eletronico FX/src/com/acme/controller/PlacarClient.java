package com.acme.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlacarClient {

    private static BufferedReader in;
    private static PrintWriter out;

    public static String conectar(String serverAddress, String login, String senha) throws IOException {
        Socket socket = new Socket(serverAddress, 9898);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        out.println(login);
        out.println(senha);

        return in.readLine();
    }
}
