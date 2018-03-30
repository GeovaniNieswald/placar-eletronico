package com.acme;

import com.acme.controller.PlacarController;
import com.acme.model.Cena;
import com.acme.model.DadosXML;
import com.acme.model.ListaUsuarios;
import com.acme.model.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javax.xml.bind.JAXBException;

public class PlacarServer extends Thread {

    private final Socket socket;
    private static boolean usuarioPrincipalOn;
    private static PlacarController controller;

    public PlacarServer(Socket socket) {
        this.socket = socket;
    }

    public static void instanciaPlacarController(PlacarController pc) {
        controller = pc;
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
            Usuario u = new Usuario();

            // Fazer a validação de usuario e senha
            if (validaLogin(login, senha, u)) {
                if (u.isAdmin()) { // verificar quais as permissões do usuario
                    out.println("#conexao;ok;usuario-principal");
                } else if (u.isPlacar()) {
                    out.println("#conexao;ok;usuario-placar");
                } else if (u.isPropaganda()) {
                    out.println("#conexao;ok;usuario-propaganda");
                } else {
                    out.println("#conexao;not-ok");
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
     */
    public static String processarComando(String comando) {
        String[] params = comando.split(";");
        if (params != null) {
            switch (params[0]) {
                case "#alterar-nome-time-local":
                    return comandoAlterarNomeTimeLocal(params);
                case "#esporte":
                    return comandoEsporte(params);
                case "#usuario-principal":
                    return comandoUsuarioPrincipal(params);
                case "#texto-inferior":
                    return comandoTextoInferior(params);
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
                case "#cadastrouser":
                    return comandoCadastroUser(params);
                default:
                    return "#comando;not-ok";
            }
        } else {
            return "#comando;not-ok";
        }
    }
    
public static String comandoAlterarNomeTimeLocal(String[] params) {
        switch (params[1]) {
            case "alterar":
                controller.setAlterarNomeTimeLocal(params[2]);

                return "#alterar-nome-time-local;ok";
            default:
                return "#alterar-nome-time-local;not-ok";
        }
    }

    public static String comandoEsporte(String[] params) {
        switch (params[1]) {
            case "basquete":
                Timeline telaBasquete = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    usuarioPrincipalOn = true;

                    MainApp.trocarCena(Cena.PLACAR_BASQUETE);
                }),
                        new KeyFrame(Duration.seconds(1))
                );
                telaBasquete.play();

                return "#esporte;ok";
            case "futsal":
                Timeline telaFutsal = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    usuarioPrincipalOn = true;

                    MainApp.trocarCena(Cena.PLACAR_FUTSAL);
                }),
                        new KeyFrame(Duration.seconds(1))
                );
                telaFutsal.play();

                return "#esporte;ok";
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

    public static String comandoTextoInferior(String[] params) {
        switch (params[1]) {
            case "alterar":
                controller.setTextoInferior(params[2]);
                return "#texto-inferior;ok";
            case "restaurar":
                controller.setTextoInferior("PLACAR ELETRONICO FX");
                return "#texto-inferior;ok";
            default:
                return "#texto-inferior;not-ok";
        }
    }

    public static String comandoPontos(String[] params) {
        switch (params[1]) {
            case "mais":
                controller.somar(params[2]);
                return "#pontos;ok";
            case "menos":
                return "Subtrai pontos: " + params[2];
            case "set":
                return "Set pontos: " + params[2];
            default:
                return "#pontos;not-ok";
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

    public static String comandoCadastroUser(String[] params) {
        switch (params[1]) {
            case "add":
                try {
                    //carrega users já existentes, caso existam
                    ListaUsuarios listaExistente = new ListaUsuarios();
                    if (!DadosXML.isEmpty("ListaUsuarios")) {
                        listaExistente = (ListaUsuarios) DadosXML.select("ListaUsuarios");
                    }

                    //passa a lista antiga para o objeto novo, e inclui o registro novo
                    ListaUsuarios listaNova = new ListaUsuarios();
                    listaNova.setUsuarios(listaExistente.getUsuarios());
                    Usuario novoUser = new Usuario();
                    novoUser.setUsuario(params[2]);
                    novoUser.setSenha(params[3]);
                    novoUser.setAdmin(strToBoolean(params[4]));
                    novoUser.setPlacar(strToBoolean(params[5]));
                    novoUser.setPropaganda(strToBoolean(params[6]));
                    listaNova.getUsuarios().add(novoUser);

                    //grava a lista nova
                    DadosXML.insert("ListaUsuarios", listaNova);
                    return "Usuário salvo!;ok";
                } catch (JAXBException ex) {
                    return "Erro: " + ex.getMessage();
                }
            default:
                return "Comando inválido! Parâmetro Tipo = " + params[1];
        }
    }

    public static boolean strToBoolean(String str) {
        if ("S".equalsIgnoreCase(str) || "T".equalsIgnoreCase(str)
                || "TRUE".equalsIgnoreCase(str) || "V".equalsIgnoreCase(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validaLogin(String login, String senha, Usuario usuario) {
        try {
            if (login == null || senha == null) {
                return false;
            } else {
                ListaUsuarios users = (ListaUsuarios) DadosXML.select("ListaUsuarios");
                for (Usuario u : users.getUsuarios()) {
                    if (login.equals(u.getUsuario()) && senha.equals(u.getSenha())) {
                        usuario.setUsuario(u.getUsuario());
                        usuario.setSenha(u.getSenha());
                        usuario.setAdmin(u.isAdmin());
                        usuario.setPlacar(u.isPlacar());
                        usuario.setPropaganda(u.isPropaganda());
                        return true;
                    }
                }
            }
        } catch (JAXBException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

}
