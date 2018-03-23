package com.acme;

import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlacarClient {

    private static Socket socket;

    public static RespostaSocket conectar(String serverAddress, String login, String senha) throws IOException {
        socket = new Socket(serverAddress, 9898);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(login);
        out.println(senha);

        String[] respostaConexao = in.readLine().split(";");

        if (respostaConexao[1].equals("ok")) {
            if (respostaConexao[2].equals("usuario-principal")) {
                return RespostaSocket.CONEXAO_ACEITA_USUARIO_PRINCIPAL;
            } else {
                return RespostaSocket.CONEXAO_ACEITA_USUARIO_PROPAGANDA;
            }
        }

        return RespostaSocket.CONEXAO_RECUSADA;
    }

    public static void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            // IMPLEMENTAR LOG
        }
    }

    public static RespostaSocket enviarComando(Comando comando, String... valores) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        switch (comando) {
            case ESCOLHER_ESPORTE:
                out.println("#esporte;" + valores[0]);

                String[] respostaEsporte = in.readLine().split(";");

                if (respostaEsporte[1].equals("ok")) {
                    if (respostaEsporte[2].equals("basquete")) {
                        return RespostaSocket.ESPORTE_ACEITO_BASQUETE;
                    } else {
                        return RespostaSocket.ESPORTE_ACEITO_FUTSAL;
                    }
                }

                return RespostaSocket.ESPORTE_RECUSADO;

            case VERIFICAR_USUARIO_PRINCIPAL:
                out.println("#usuario-principal");

                String respostaUsuarioPrincipal[] = in.readLine().split(";");

                if (respostaUsuarioPrincipal[1].equals("ok")) {
                    return RespostaSocket.USUARIO_PRINCIPAL_CONECTADO;
                } else {
                    return RespostaSocket.USUARIO_PRINCIPAL_NAO_CONECTADO;
                }

            case SET_TEXTO_INFERIOR_VISOR:
                if (valores[0].equals("set")) {
                    out.println("#texto-inferior-visor;set;" + valores[1]);
                } else {

                }
        }

        return RespostaSocket.COMANDO_RECUSADO;
    }
}
