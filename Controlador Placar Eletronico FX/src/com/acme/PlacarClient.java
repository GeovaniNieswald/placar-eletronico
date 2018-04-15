package com.acme;

import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe Referente a comunicação socket.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public class PlacarClient {

    // Variável referente a conexão atual da aplicação.
    private static Socket socket;

    /**
     * Método para conectar ao servidor (placar eletrônico).
     *
     * @param serverAddress String - Endereço do servidor onde está o placar
     * eletrõnico.
     * @param login String - Login do usuário que está tentando conectar.
     * @param senha String - Senha do usuário que está tentando conectar.
     * @return RespostaSocket - Enum que pode representar uma conexão aceita
     * para o usuário principal ou usuário propaganda, ou pode representar uma
     * conexão recusada.
     * @throws java.io.IOException Caso ocorra algum erro na comunicação por
     * socket.
     */
    public static RespostaSocket conectar(String serverAddress, String login, String senha) throws IOException {
        socket = new Socket(serverAddress, 9898);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(login);
        out.println(senha);

        String[] respostaConexao = in.readLine().split(";");

        if (respostaConexao[1].equals("ok")) {
            switch (respostaConexao[2]) {
                case "usuario-principal":
                    return RespostaSocket.CONEXAO_ACEITA_USUARIO_PRINCIPAL;
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

    /**
     * Método para desconectar do servidor (placar eletrônico).
     *
     */
    public static void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            // IMPLEMENTAR LOG
        }
    }

    /**
     * Método para enviar algum comando para o servidor (placar eletrônico).
     *
     * @param comando Comando - Enum referente ao tipo de comando desejado.
     * @param valores String - Varargs com valores que esses comando possam
     * precisar.
     * @return RespostaSocket - Enum que representa se o comando foi aceito,
     * recusado, podendo passar alguma informação a mais dependendo do que foi
     * definido no enum.
     * @throws java.io.IOException Caso ocorra algum erro na comunicação por
     * socket.
     */
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

            case NOME_TIME:
                out.println("#nome-time;" + valores[0] + ";" + valores[1] + ";" + valores[2]);

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }

            case TEXTO_INFERIOR:
                out.println("#texto-inferior;" + valores[0] + ";" + valores[1]);

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }

            case CADASTRO_USUARIO:
                if ("get".equalsIgnoreCase(valores[0])) {
                    return RespostaSocket.COMANDO_RECUSADO;
                } else if ("add".equalsIgnoreCase(valores[0])) {
                    out.println("#cadastro-usuario;" + valores[0] + ";" + valores[1] + ";" + valores[2] + ";" + valores[3] + ";" + valores[4] + ";" + valores[5]);
                } else if ("delete".equalsIgnoreCase(valores[0])) {
                    out.println("#cadastro-usuario;" + valores[0] + ";" + valores[1]);
                } else if ("update".equalsIgnoreCase(valores[0])) {
                    out.println("#cadastro-usuario;" + valores[0] + ";" + valores[1] + ";" + valores[2]);
                }

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }

            case PONTOS:
                out.println("#pontos;" + valores[0] + ";" + valores[1] + ";" + valores[2]);

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }

            case FALTAS:
                out.println("#faltas;" + valores[0] + ";" + valores[1] + ";" + valores[2]);

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }

            case IMAGENS:
                out.println("#imagens;" + valores[0] + ";" + valores[1] + ";" + valores[2]);

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }

            default:
                return RespostaSocket.COMANDO_RECUSADO;
            // IMPLEMENTAR LOG
        }
    }

    public static String enviarComandoResp(Comando comando, String... valores) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String[] respostaComando;

        switch (comando) {
            case CADASTRO_USUARIO:
                if ("get".equalsIgnoreCase(valores[0])) {
                    out.println("#cadastro-usuario;" + valores[0] + ";" + valores[1]);
                } else if ("add".equalsIgnoreCase(valores[0])) {
                    out.println("#cadastro-usuario;" + valores[0] + ";" + valores[1] + ";" + valores[2] + ";" + valores[3] + ";" + valores[4] + ";" + valores[5]);
                }

                respostaComando = in.readLine().split(";");

                if ("get".equalsIgnoreCase(valores[0])) {
                    return respostaComando[2];
                } else {
                    if (respostaComando[1].equals("ok")) {
                        return "ok";
                    } else {
                        return "not-ok";
                    }
                }
            default:
                return "not-ok";
        }
    }
}
