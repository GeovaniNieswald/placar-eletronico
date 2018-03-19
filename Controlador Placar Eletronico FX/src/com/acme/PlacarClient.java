package com.acme;

import com.acme.model.RespostaSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlacarClient {

    private static BufferedReader in;
    private static PrintWriter out;
    private static Socket socket;

    public static RespostaSocket conectar(String serverAddress, String login, String senha) throws IOException {
        socket = new Socket(serverAddress, 9898);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        out.println(login);
        out.println(senha);

        String[] respostaConexao = in.readLine().split(";");

        if (respostaConexao[0].equals("#conexao")) {
            if (respostaConexao[1].equals("ok")) {
                if (respostaConexao[2].equals("usuario-principal")) {
                    return RespostaSocket.CONEXAO_ACEITA_USUARIO_PRINCIPAL;
                } else {
                    return RespostaSocket.CONEXAO_ACEITA_USUARIO_PROPAGANDA;
                }
            }
        }

        return RespostaSocket.CONEXAO_RECUSADA;
    }

    public static RespostaSocket escolherEsporte(String comando) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        out.println(comando);

        String[] respostaComando = in.readLine().split(";");

        if (respostaComando[0].equals("#esporte")) {
            if (respostaComando[1].equals("ok")) {
                if (respostaComando[2].equals("basquete")) {
                    return RespostaSocket.COMANDO_ACEITO_BASQUETE;
                } else {
                    return RespostaSocket.COMANDO_ACEITO_FUTSAL;
                }
            }
        }

        return RespostaSocket.COMANDO_RECUSADO;
    }

    public static RespostaSocket verificarSeUsuarioPrincipalEstaConectado() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        out.println("#usuario-principal;0;0");

        String resposta[] = in.readLine().split(";");

        if (resposta[0].equals("#usuario-principal") && resposta[1].equals("ok")) {
            return RespostaSocket.USUARIO_PRINCIPAL_CONECTADO;
        } else {
            return RespostaSocket.USUARIO_PRINCIPAL_NAO_CONECTADO;
        }
    }
}
