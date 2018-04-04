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
import java.util.ArrayList;
import java.util.List;
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
                case "#esporte":
                    return comandoEsporte(params);
                case "#usuario-principal":
                    return comandoUsuarioPrincipal(params);
                case "#nome-time":
                    return comandoNomeTime(params);
                case "#texto-inferior":
                    return comandoTextoInferior(params);
                case "#cadastro-usuario":
                    return comandoCadastroUsuario(params);
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
                default:
                    return "#comando;not-ok";
            }
        } else {
            return "#comando;not-ok";
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

                return "#esporte;basquete-ok";
            case "futsal":
                Timeline telaFutsal = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    usuarioPrincipalOn = true;
                    MainApp.trocarCena(Cena.PLACAR_FUTSAL);
                }),
                        new KeyFrame(Duration.seconds(1))
                );
                telaFutsal.play();

                return "#esporte;futsal-ok";
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

    public static String comandoNomeTime(String[] params) {
        switch (params[1]) {
            case "local":
                switch (params[2]) {
                    case "alterar":
                        controller.setNomeTimeLocal(params[3]);
                        return "#nome-time;ok";
                    case "restaurar":
                        controller.setNomeTimeLocal("LOCAL");
                        return "#nome-time;ok";
                    default:
                        return "#nome-time;not-ok";
                }
            case "visitante":
                switch (params[2]) {
                    case "alterar":
                        controller.setNomeTimeVisitante(params[3]);
                        return "#nome-time;ok";
                    case "restaurar":
                        controller.setNomeTimeVisitante("VISITANTE");
                        return "#nome-time;ok";
                    default:
                        return "#nome-time;not-ok";
                }
            default:
                return "#nome-time;not-ok";
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
            case "local":
                switch (params[2]) {
                    case "mais":
                        controller.aumentarPontosTimeLocal(Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "menos":
                        controller.diminuirPontosTimeLocal(Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "set":
                        break;
                    default:
                        return "#pontos;not-ok";
                }
            case "visitante":
                switch (params[2]) {
                    case "mais":
                        controller.aumentarPontosTimeVisitante(Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "menos":
                        controller.diminuirPontosTimeVisitante(Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "set":
                        break;
                    default:
                        return "#pontos;not-ok";
                }
            case "zerar":
                controller.zerarPontos();
                return "#pontos;ok";
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

    public static String comandoCadastroUsuario(String[] params) {
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
                    novoUser.setAdmin(Utils.stringParaBoolean(params[4]));
                    novoUser.setPlacar(Utils.stringParaBoolean(params[5]));
                    novoUser.setPropaganda(Utils.stringParaBoolean(params[6]));
                    listaNova.getUsuarios().add(novoUser);

                    //grava a lista nova
                    DadosXML.insert("ListaUsuarios", listaNova);
                    return "#cadastro-usuario;ok";
                } catch (JAXBException ex) {
                    return "#cadastro-usuario;not-ok";
                }
            case "get":
                try {
                    ListaUsuarios listaExistente;
                    String listaRetorno = "";
                    if (!DadosXML.isEmpty("ListaUsuarios")) {
                        listaExistente = (ListaUsuarios) DadosXML.select("ListaUsuarios");
                        for (Usuario u : listaExistente.getUsuarios()) {
                            if ("all".equalsIgnoreCase(params[2])) {
                                if (listaRetorno.isEmpty()) {
                                    listaRetorno += u.getUsuario();
                                } else {
                                    listaRetorno += "," + u.getUsuario();
                                }
                            } else {
                                if (u.getUsuario().equalsIgnoreCase(params[2])) {
                                    if (listaRetorno.isEmpty()) {
                                        listaRetorno += u.getUsuario();
                                    } else {
                                        listaRetorno += "," + u.getUsuario();
                                    }
                                }
                            }
                        }
                        return "#cadastro-usuario;ok;" + listaRetorno;
                    } else {
                        return "#cadastro-usuario;not-ok;vazio";
                    }
                } catch (JAXBException ex) {
                    return "#cadastro-usuario;not-ok;erro";
                }
            case "delete":
                try {
                    ListaUsuarios listaExistente = new ListaUsuarios();
                    ArrayList<Usuario> listaPermanece = new ArrayList<>();
                    if (!DadosXML.isEmpty("ListaUsuarios")) {
                        listaExistente = (ListaUsuarios) DadosXML.select("ListaUsuarios");
                    }
                    ListaUsuarios listaAtualizada = new ListaUsuarios();
                    listaAtualizada.setUsuarios(listaExistente.getUsuarios());
                    for (Usuario u : listaAtualizada.getUsuarios()) {
                        if (!u.getUsuario().equals(params[2])) {
                            listaPermanece.add(u);
                        }
                    }
                    listaAtualizada.setUsuarios(listaPermanece);
                    DadosXML.insert("ListaUsuarios", listaAtualizada);
                    return "#cadastro-usuario;ok";
                } catch (JAXBException ex) {
                    return "#cadastro-usuario;not-ok";
                }
            case "update":
                try {
                    ListaUsuarios listaExistente = new ListaUsuarios();
                    if (!DadosXML.isEmpty("ListaUsuarios")) {
                        listaExistente = (ListaUsuarios) DadosXML.select("ListaUsuarios");
                    }
                    ListaUsuarios listaAtualizada = new ListaUsuarios();
                    listaAtualizada.setUsuarios(listaExistente.getUsuarios());
                    for (Usuario u : listaAtualizada.getUsuarios()) {
                        if (u.getUsuario().equals(params[2])) {
                            u.setSenha(params[3]);
                            break;
                        }
                    }
                    DadosXML.insert("ListaUsuarios", listaAtualizada);
                    return "#cadastro-usuario;ok";
                } catch (JAXBException ex) {
                    return "#cadastro-usuario;not-ok";
                }
            default:
                return "#cadastro-usuario;not-ok";
        }
    }

    private static boolean validaLogin(String login, String senha, Usuario usuario) {
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
            // IMPLEMENTAR LOGIN
            return false;
        }

        return false;
    }
}
