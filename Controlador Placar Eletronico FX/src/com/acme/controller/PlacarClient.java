package com.acme.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlacarClient {

    private static BufferedReader in;
    private static PrintWriter out;

    public void connectToServer(String serverAddress) throws IOException {
        Socket socket = new Socket(serverAddress, 9898);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(in.readLine()); // rsposta da conexao

        boolean repete = true;
        while (repete) { // da pra remover
            System.out.println("Informe um comando a ser enviado:");
            Scanner sc = new Scanner(System.in);
            out.println(sc.next()); // envia pro servidor, (envia se eh ou não root)
            String response;
            try {
                response = in.readLine(); // recebe quais as permisões
                if (response == null || response.equals("")) {
                    System.exit(0); // olhar
                }
            } catch (IOException ex) {
                response = "Erro: " + ex;
            }
            System.out.println(response);

            // tbm tirar
            System.out.println("Deseja informar mais um comando? (S/N):");
            String input = sc.next();
            if ("N".equalsIgnoreCase(input)) {
                repete = false;
            }
        }
    }

    public static void start() throws Exception {
        // Chamar na primeira tela do controlador

        //TODO: endereço será informado pelo usuário ou ficará salvo em um arquivo de configuração
        //Foi mantido localhost de forma a permitir testar locamente
        PlacarClient client = new PlacarClient();
        client.connectToServer("localhost");

    }
}
