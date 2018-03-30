package com.acme;

import com.acme.model.Comando;
import com.acme.model.RespostaSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
            if (respostaConexao[2].equals("usuario-principal")) {
                return RespostaSocket.CONEXAO_ACEITA_USUARIO_PRINCIPAL;
            } else {
                return RespostaSocket.CONEXAO_ACEITA_USUARIO_PROPAGANDA;
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
            case ALTERAR_NOME_TIME_LOCAL:
                 out.println("#alterar-nome-time-local;alterar");

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                }

                return RespostaSocket.COMANDO_RECUSADO;
                
                
            case ESCOLHER_ESPORTE_BASQUETE:
                out.println("#esporte;basquete");

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.ESPORTE_ACEITO_BASQUETE;
                }

                return RespostaSocket.ESPORTE_RECUSADO;

            case ESCOLHER_ESPORTE_FUTSAL:
                out.println("#esporte;futsal");

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.ESPORTE_ACEITO_FUTSAL;
                }

                return RespostaSocket.ESPORTE_RECUSADO;

            case VERIFICAR_USUARIO_PRINCIPAL:
                out.println("#usuario-principal");

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.USUARIO_PRINCIPAL_CONECTADO;
                } else {
                    return RespostaSocket.USUARIO_PRINCIPAL_NAO_CONECTADO;
                }

            case ALTERAR_TEXTO_INFERIOR:
                out.println("#texto-inferior;alterar;" + valores[0]);

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }

            case RESTAURAR_TEXTO_INFERIOR:
                out.println("#texto-inferior;restaurar");

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }

            case CRIAR_USUARIO:
                out.println("#cadastrouser;" + valores[0] + ";" + valores[1] + ";" + valores[2] + ";" + valores[3] + ";" + valores[4] + ";" + valores[5]);

                respostaComando = in.readLine().split(";");

                if (respostaComando[1].equals("ok")) {
                    return RespostaSocket.COMANDO_ACEITO;
                } else {
                    return RespostaSocket.COMANDO_RECUSADO;
                }
                
            case SOMAR:
                out.println("#pontos;"+valores[0]+";"+valores[1]);

            // Aqui tem que ir adicionando os case, e no enum adicionar o nome 
            // do comando, acima tem os exemplos garai. 
            default:
                return RespostaSocket.COMANDO_RECUSADO;
            // IMPLEMENTAR LOG
        }
    }

    // Vai ser trocado por uma mensagem na própria tela
    public static void alert(String msg, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

            }
        });
    }
}
