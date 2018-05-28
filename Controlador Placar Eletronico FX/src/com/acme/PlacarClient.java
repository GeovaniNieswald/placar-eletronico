package com.acme;

import com.acme.model.Comando;
import com.acme.model.MeuLogger;
import com.acme.model.RespostaSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

public class PlacarClient {

    // Variável referente a conexão atual da aplicação.
    private static Socket socket;

    private static RespostaSocket converterResposta(String[] respostaComando, BufferedReader in, PrintWriter out) throws IOException {
        if (respostaComando[1].equals("ok")) {
            return RespostaSocket.COMANDO_ACEITO;
        } else {
            return RespostaSocket.COMANDO_RECUSADO;
        }
    }

    public static RespostaSocket conectar(String serverAddress, String login, String senha) throws IOException {
        socket = new Socket(serverAddress, 9898);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(login);
        out.println(senha);

        String[] respostaConexao = in.readLine().split(";");

        if (respostaConexao[1].equals("ok")) {
            switch (respostaConexao[2]) {
                case "usuario-administrador":
                    return RespostaSocket.CONEXAO_ACEITA_USUARIO_ADMINISTRADOR;
                case "usuario-placar":
                    return RespostaSocket.CONEXAO_ACEITA_USUARIO_PLACAR;
                case "usuario-propaganda":
                    return RespostaSocket.CONEXAO_ACEITA_USUARIO_PROPAGANDA;
                default:
                    return RespostaSocket.CONEXAO_RECUSADA;
            }
        }

        return RespostaSocket.CONEXAO_RECUSADA;
    }

    public static void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            MeuLogger.logException(Level.WARNING, "Não foi possível fechar a conexão.", ex);
        }
    }

    public static RespostaSocket enviarComando(Comando comando, String... valores) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String[] respostaComando;

        switch (comando) {
            case ESCOLHER_ESPORTE:
                out.println("#esporte;" + valores[0]);
                respostaComando = in.readLine().split(";");

                switch (respostaComando[1]) {
                    case "basquete-ok":
                        return RespostaSocket.ESPORTE_ACEITO_BASQUETE;
                    case "futsal-ok":
                        return RespostaSocket.ESPORTE_ACEITO_FUTSAL;
                    default:
                        return RespostaSocket.COMANDO_RECUSADO;
                }

            case VERIFICAR_USUARIO_PLACAR:
                out.println("#usuario-placar");
                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.USUARIO_PLACAR_CONECTADO;
                } else {
                    return RespostaSocket.USUARIO_PLACAR_NAO_CONECTADO;
                }

            case CADASTRO_USUARIO:
                switch (valores[0]) {
                    case "add":
                        out.println("#cadastro-usuario;" + valores[0] + ";" + valores[1] + ";" + valores[2] + ";" + valores[3] + ";" + valores[4] + ";" + valores[5]);
                        break;
                    case "delete":
                        out.println("#cadastro-usuario;" + valores[0] + ";" + valores[1]);
                        break;
                    case "update":
                        out.println("#cadastro-usuario;" + valores[0] + ";" + valores[1] + ";" + valores[2]);
                        break;
                    default:
                        return RespostaSocket.COMANDO_RECUSADO;
                }

                respostaComando = in.readLine().split(";");

                switch (respostaComando[1]) {
                    case "ok":
                        return RespostaSocket.COMANDO_ACEITO;
                    case "usuario-existe":
                        return RespostaSocket.USUARIO_JA_EXISTE;
                    default:
                        return RespostaSocket.COMANDO_RECUSADO;
                }

            case NOME_TIME:
                out.println("#nome-time;" + valores[0] + ";" + valores[1] + ";" + valores[2]);
                return converterResposta(in.readLine().split(";"), in, out);

            case TEXTO_INFERIOR:
                out.println("#texto-inferior;" + valores[0] + ";" + valores[1]);
                return converterResposta(in.readLine().split(";"), in, out);

            case PONTOS:
                out.println("#pontos;" + valores[0] + ";" + valores[1] + ";" + valores[2]);
                return converterResposta(in.readLine().split(";"), in, out);

            case FALTAS:
                out.println("#faltas;" + valores[0] + ";" + valores[1] + ";" + valores[2]);
                return converterResposta(in.readLine().split(";"), in, out);

            case PERIODO:
                out.println("#periodo;" + valores[0] + ";" + valores[1]);
                return converterResposta(in.readLine().split(";"), in, out);

            case IMAGENS:
                out.println("#imagens;" + valores[0] + ";" + valores[1] + ";" + valores[2]);
                return converterResposta(in.readLine().split(";"), in, out);

            case PROPAGANDA:
                out.println("#propaganda;" + valores[0] + ";" + valores[1] + ";" + valores[2]);
                return converterResposta(in.readLine().split(";"), in, out);

            case CRONOMETRO:
                out.println("#cronometro;" + valores[0] + ";" + valores[1] + ";" + valores[2]);
                return converterResposta(in.readLine().split(";"), in, out);
                
            case BONUS:
                out.println("#bonus;" + valores[0] + ";" + valores[1]);
                return converterResposta(in.readLine().split(";"), in, out);

            default:
                MeuLogger.logMensagem(Level.WARNING, "Comando informado não está presente entre as opções do switch.");
                return RespostaSocket.COMANDO_RECUSADO;
        }
    }

    public static String retornarUsuarios() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("#cadastro-usuario;get;all");

        String[] respostaComando = in.readLine().split(";");

        if (respostaComando[1].equalsIgnoreCase("not-ok")) {
            return "#cadastro-usuario;not-ok";
        } else {
            return respostaComando[2];
        }
    }
}
