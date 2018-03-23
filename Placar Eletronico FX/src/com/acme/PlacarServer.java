package com.acme;

import com.acme.controller.PlacarController;
import com.acme.model.Tela;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PlacarServer extends Thread {

    private final Socket socket;
    private static boolean usuarioPrincipalOn;
    private static PlacarController pc;

    public PlacarServer(Socket socket) {
        this.socket = socket;
    }

    public static void teste(PlacarController pc1) {
        pc = pc1;
    }

    // Método para iniciar o servidor
    public static void iniciar() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket listener = new ServerSocket(9898)) {
                    while (true) {
                        new PlacarServer(listener.accept()).start();
                    }
                } catch (IOException ex) {
                    // IMPLEMENTAR LOG
                }
            }
        });
        t.start();
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String login = in.readLine();
            String senha = in.readLine();

            // Fazer a validação de usuario e senha
            if ((login.equals("geo") || login.equals("pedro")) && senha.equals("geo")) {
                if (login.equals("geo")) { // verificar quais as permições do usuario
                    out.println("#conexao;ok;usuario-principal");
                } else {
                    out.println("#conexao;ok;usuario-propaganda");
                }

                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    } else {
                        out.println(processarComando(input));
                    }
                }
            } else {
                out.println("#conexao;not-ok");
            }
        } catch (IOException ex) {
            // IMPLEMENTAR LOG
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                // IMPLEMENTAR LOG
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
        if (params != null) {
            switch (params[0]) {
                case "#esporte":
                    return comandoEsporte(params);
                case "#usuario-principal":
                    return comandoUsuarioPrincipal(params);
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
                case "#texto-inferior-visor":
                    return comandoTextoInferiorVisor(params);
                default:
                    return "#comando;not-ok";
            }
        } else {
            return "#comando;not-ok";
        }
    }

    //Comandos abaixo hoje printam somente um retorno
    //No futuro, eles irão também manipular o valor dos atributos que representam os dados do placar
    public static String comandoEsporte(String[] params) {
        switch (params[1]) {
            case "basquete":
                Timeline telaBasquete = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    usuarioPrincipalOn = true;

                    MainApp.trocarCena(Tela.PLACAR_BASQUETE);
                }),
                        new KeyFrame(Duration.seconds(1))
                );
                telaBasquete.play();

                return "#esporte;ok;basquete";
            case "futsal":
                Timeline telaFutsal = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    usuarioPrincipalOn = true;

                    MainApp.trocarCena(Tela.PLACAR_FUTSAL);
                }),
                        new KeyFrame(Duration.seconds(1))
                );
                telaFutsal.play();

                return "#esporte;ok;futsal";
            default:
                return "#esporte;not-ok";
        }
    }

    public static String comandoUsuarioPrincipal(String[] params) {
        if (usuarioPrincipalOn) {
            return "#usuario-principal;ok";
        } else {
            return "#usuario-principal;not-ok";
        }
    }

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

    public static String comandoTextoInferiorVisor(String[] params) {
        switch (params[1]) {
            case "set":
                pc.setTextoInferiorVisor(params[2]);

                return "#texto-inferior-visor;ok";
            case "reset":
                return "Set texto do visor: " + params[2];
            default:
                return "#texto-inferior-visor;not-ok";
        }
    }
}
