package com.acme.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlacarServer extends Thread {

    private final Socket socket;
    private final int clientNumber;

    // Chama o metodo na primeira tela do projeto Placar Eletronico FX que eh o server
    public static void iniciar() throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("O servidor do placar foi iniciado.");
                int clientNumber = 0;
                try (ServerSocket listener = new ServerSocket(9898)) {
                    while (true) {
                        new PlacarServer(listener.accept(), clientNumber++).start();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PlacarServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();

    }

    public PlacarServer(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        System.out.println("Nova conexão com operador de placar " + clientNumber + " em " + socket.getLocalAddress());
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Olá! Você é o operador " + clientNumber); // aqui faz a checagem se eh root, e responde com as permisões
            while (true) {
                String input = in.readLine();
                if (input == null || input.equals(".")) {
                    break;
                } else {
                    out.println(processarComando(input));
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro no operador " + clientNumber + ": " + ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Erro no operador " + clientNumber + ": " + ex.getMessage());
            }
        }
    }

    /*
    Protocolo de comunição: #comando;tipo;valor
    Exemplos:
    
        Somar/subtrair/resetar placar
        Mesma lógica nos comando: #faltaset, #tempos
        #pontos;mais;1
        #pontos;menos;1
        #pontos;set;0
    
        Mudar tempo no cronômetro
        #cronometro;set;10:00
        #cronometro;play
        #cronometro;pause
    
        Primeira propaganda da lista de imagens que estão na pasta no servidor
        #propaganda;set;1
    
        Fazer propagandas mudarem automaticamente a cada X segundos
        #propaganda;autoplay;10
    
        Mudar texto que aparece no visor
        #visor;set;TEXTO TESTE
    
        Nenhum comando precisa de get, pois a informação nunca precisará ser retornada ao operador
        A única coisa que é retornada ao operador é uma confirmação de que o comando foi executado
        Qualquer texto informado que não estiver neste formato deve ser rejeitado pelo servidor
     */
    public static String processarComando(String comando) {
        String[] params = comando.split(";");
        if (params != null && params.length == 3) {
            switch (params[0]) {
                case "#pontos":
                    return comandoPontos(params);
                case "#faltaset":
                    return comandoFaltaSet(params);
                case "#tempos":
                    return comandoTempos(params);
                case "#cronometro":
                    return comandoCronometro(params);
                case "#propagandas":
                    return comandoPropagandas(params);
                case "#visor":
                    return comandoVisor(params);
                default:
                    return "Comando inválido: " + comando;
            }
        } else {
            return "Comando inválido: " + comando;
        }
    }

    //Comandos abaixo hoje printam somente um retorno
    //No futuro, eles irão também manipular o valor dos atributos que representam os dados do placar
    public static String comandoPontos(String[] params) {
        switch (params[1]) {
            case "mais":
                return "Soma pontos: " + params[2];
            case "menos":
                return "Subtrai pontos: " + params[2];
            case "set":
                return "Set pontos: " + params[2];
            default:
                return "Comando inválido! Parâmetro Tipo = " + params[2];
        }
    }

    public static String comandoFaltaSet(String[] params) {
        switch (params[1]) {
            case "mais":
                return "Soma faltas/sets: " + params[2];
            case "menos":
                return "Subtrai faltas/sets: " + params[2];
            case "set":
                return "Set faltas/sets: " + params[2];
            default:
                return "Comando inválido! Parâmetro Tipo = " + params[2];
        }
    }

    public static String comandoTempos(String[] params) {
        switch (params[1]) {
            case "mais":
                return "Soma tempos: " + params[2];
            case "menos":
                return "Subtrai tempos: " + params[2];
            case "set":
                return "Set tempos: " + params[2];
            default:
                return "Comando inválido! Parâmetro Tipo = " + params[2];
        }
    }

    public static String comandoCronometro(String[] params) {
        switch (params[1]) {
            case "set":
                return "Set tempo do cronômetro: " + params[2];
            default:
                return "Comando inválido! Parâmetro Tipo = " + params[2];
        }
    }

    public static String comandoPropagandas(String[] params) {
        switch (params[1]) {
            case "set":
                return "Selecionar imagem de propaganda: " + params[2];
            case "autoplay":
                return "Mudar propaganda a cada " + params[2] + " segundos";
            default:
                return "Comando inválido! Parâmetro Tipo = " + params[2];
        }
    }

    public static String comandoVisor(String[] params) {
        switch (params[1]) {
            case "set":
                return "Set texto do visor: " + params[2];
            default:
                return "Comando inválido! Parâmetro Tipo = " + params[2];
        }
    }
}
