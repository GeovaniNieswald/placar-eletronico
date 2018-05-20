package com.acme;

import com.acme.controller.PlacarController;
import com.acme.controller.PropagandaController;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javax.xml.bind.JAXBException;

public class PlacarServer extends Thread {

    private final Socket socket;
    private static boolean usuarioPlacarOn;
    private static PlacarController placarController;
    private static PropagandaController propagandaController;

    public PlacarServer(Socket socket) {
        this.socket = socket;
    }

    public static void instanciaPlacarController(PlacarController pc) {
        placarController = pc;
    }

    public static void instanciaPropagandaController(PropagandaController pc) {
        propagandaController = pc;
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

            if (validaLogin(login, senha, u)) {
                if (u.isAdmin()) {
                    out.println("#conexao;ok;usuario-administrador");
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

    public static String processarComando(String comando) {
        String[] params = comando.split(";");
        if (params != null) {
            switch (params[0]) {
                case "#esporte":
                    return comandoEsporte(params);
                case "#usuario-placar":
                    return comandoUsuarioPlacar(params);
                case "#nome-time":
                    return comandoNomeTime(params);
                case "#texto-inferior":
                    return comandoTextoInferior(params);
                case "#pontos":
                    return comandoPontos(params);
                case "#faltas":
                    return comandoFaltas(params);
                case "#imagens":
                    return comandoImagens(params);
                case "#cronometro":
                    return comandoCronometro(params);
                case "#propaganda":
                    return comandoPropaganda(params);
                case "#tempos":
                    return comandoTempos(params);
                case "#cadastro-usuario":
                    return comandoCadastroUsuario(params);
                case "#periodo":
                    return comandoPeriodo(params);
                case "#reset":
                    return comandoReset(params);
                default:
                    return "#comando;not-ok";
            }
        } else {
            return "#comando;not-ok";
        }
    }

    private static void linhaDoTempoTrocarCena(Cena c) {
        Timeline cena = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            MainApp.trocarCena(c);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        cena.play();
    }

    public static String comandoEsporte(String[] params) {
        switch (params[1]) {
            case "basquete":
                linhaDoTempoTrocarCena(Cena.PLACAR_BASQUETE);
                usuarioPlacarOn = true;
                return "#esporte;basquete-ok";
            case "futsal":
                linhaDoTempoTrocarCena(Cena.PLACAR_FUTSAL);
                usuarioPlacarOn = true;
                return "#esporte;futsal-ok";
            default:
                return "#esporte;not-ok";
        }
    }

    public static String comandoUsuarioPlacar(String[] params) {
        if (usuarioPlacarOn) {
            return "#usuario-placar;ok";
        } else {
            return "#usuario-placar;not-ok";
        }
    }

    public static String comandoNomeTime(String[] params) {
        switch (params[1]) {
            case "local":
                switch (params[2]) {
                    case "alterar":
                        placarController.setNomeTimeLocal(params[3]);
                        return "#nome-time;ok";
                    case "restaurar":
                        placarController.setNomeTimeLocal("LOCAL");
                        return "#nome-time;ok";
                    default:
                        return "#nome-time;not-ok";
                }
            case "visitante":
                switch (params[2]) {
                    case "alterar":
                        placarController.setNomeTimeVisitante(params[3]);
                        return "#nome-time;ok";
                    case "restaurar":
                        placarController.setNomeTimeVisitante("VISITANTE");
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
                placarController.setTextoInferior(params[2]);
                return "#texto-inferior;ok";
            case "restaurar":
                placarController.setTextoInferior("PLACAR ELETRONICO FX");
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
                        placarController.aumentarPontosTimeLocal(Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "menos":
                        placarController.diminuirPontosTimeLocal(Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "set":
                        break;
                    default:
                        return "#pontos;not-ok";
                }
            case "visitante":
                switch (params[2]) {
                    case "mais":
                        placarController.aumentarPontosTimeVisitante(Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "menos":
                        placarController.diminuirPontosTimeVisitante(Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "set":
                        break;
                    default:
                        return "#pontos;not-ok";
                }
            case "zerar":
                placarController.zerarPontos();
                return "#pontos;ok";
            default:
                return "#pontos;not-ok";
        }
    }

    public static String comandoFaltas(String[] params) {
        switch (params[1]) {
            case "local":
                switch (params[2]) {
                    case "mais":
                        placarController.aumentarFaltasTimeLocal(Integer.parseInt(params[3]));
                        return "#faltas;ok";
                    case "menos":
                        placarController.diminuirFaltasTimeLocal(Integer.parseInt(params[3]));
                        return "#faltas;ok";
                    default:
                        return "#faltas;not-ok";
                }
            case "visitante":
                switch (params[2]) {
                    case "mais":
                        placarController.aumentarFaltasTimeVisitante(Integer.parseInt(params[3]));
                        return "#faltas;ok";
                    case "menos":
                        placarController.diminuirFaltasTimeVisitante(Integer.parseInt(params[3]));
                        return "#faltas;ok";
                    default:
                        return "#faltas;not-ok";
                }
            case "zerar":
                placarController.zerarFaltas();
                return "#faltas;ok";
            default:
                return "#faltas;not-ok";
        }
    }

    public static String comandoPeriodo(String[] params) {
        switch (params[1]) {
            case "mais":
                placarController.aumentarPeriodo(Integer.parseInt(params[2]));
                return "#periodo;ok";
            case "menos":
                placarController.diminuirPeriodo(Integer.parseInt(params[2]));
                return "#periodo;ok";
            case "zerar":
                placarController.restaurarPeriodo();
                return "#periodo;ok";
            default:
                return "#periodo;not-ok";
        }
    }

    public static String comandoImagens(String[] params) {
        try {
            switch (params[1]) {
                case "direita":
                    switch (params[2]) {
                        case "alterar":
                            placarController.alterarImagemDireita(Utils.decodificar("imagem", params[3]));
                            return "#imagens;ok";
                        case "restaurar":
                            placarController.restaurarImagemDireita();
                            return "#imagens;ok";
                        default:
                            return "#imagens;not-ok";
                    }
                case "esquerda":
                    switch (params[2]) {
                        case "alterar":
                            placarController.alterarImagemEsquerda(Utils.decodificar("imagem", params[3]));
                            return "#imagens;ok";
                        case "restaurar":
                            placarController.restaurarImagemEsquerda();
                            return "#imagens;ok";
                        default:
                            return "#imagens;not-ok";
                    }
                default:
                    return "#imagens;not-ok";
            }
        } catch (IOException ex) {
            return "#imagens;not-ok";
        }
    }

    public static String comandoCronometro(String[] params) {
        switch (params[1]) {
            case "definir":
                placarController.definir(params[2], params[3]);
                return "#cronometro;ok";
            case "iniciar":
                placarController.iniciar(params[2], params[3]);
                return "#cronometro;ok";
            case "pausar":
                placarController.pausar();
                return "#cronometro;ok";
            case "zerar":
                placarController.zerar(params[2]);
                return "#cronometro;ok";
            default:
                return "#cronometro;not-ok";
        }
    }

    public static String comandoPropaganda(String[] params) {
        try {
            switch (params[1]) {
                case "imagem":
                    switch (params[2]) {
                        case "exibir":
                            linhaDoTempoTrocarCena(Cena.PROPAGANDA);
                            sleep(1000);
                            propagandaController.exibirPropagandaImagem(Utils.decodificar("imagem", params[3]));
                            return "#propaganda;ok";
                        case "parar":
                            linhaDoTempoTrocarCena(Cena.ATUAL);
                            return "#propaganda;ok";
                        default:
                            return "#propaganda;not-ok";
                    }
                case "video":
                    switch (params[2]) {
                        case "exibir":
                            linhaDoTempoTrocarCena(Cena.PROPAGANDA);
                            sleep(1000);
                            propagandaController.exibirPropagandaVideo(Utils.decodificar("video", params[3]));
                            return "#propaganda;ok";
                        case "parar":
                            propagandaController.pararPropagandaVideo();
                            sleep(1000);
                            linhaDoTempoTrocarCena(Cena.ATUAL);
                            return "#propaganda;ok";
                        default:
                            return "#propaganda;not-ok";
                    }
                case "escalacao":
                    switch (params[2]) {
                        case "exibir":
                            linhaDoTempoTrocarCena(Cena.PROPAGANDA);
                            sleep(1000);
                            propagandaController.exibirEscalacao(params[3]);
                            return "#propaganda;ok";
                        case "parar":
                            propagandaController.pararEscalacao();
                            sleep(1000);
                            linhaDoTempoTrocarCena(Cena.ATUAL);
                            return "#propaganda;ok";
                        default:
                            return "#propaganda;not-ok";
                    }
                default:
                    return "#propaganda;not-ok";
            }
        } catch (IOException | InterruptedException ex) {
            return "#propaganda;not-ok";
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

    public static String comandoCadastroUsuario(String[] params) {
        switch (params[1]) {
            case "add":
                try {
                    Usuario novoUser = new Usuario();
                    novoUser.setUsuario(params[2]);
                    novoUser.setSenha(params[3]);
                    novoUser.setAdmin(Utils.stringParaBoolean(params[4]));
                    novoUser.setPlacar(Utils.stringParaBoolean(params[5]));
                    novoUser.setPropaganda(Utils.stringParaBoolean(params[6]));

                    //carrega users já existentes, caso existam
                    ListaUsuarios listaExistente = new ListaUsuarios();
                    if (!DadosXML.isEmpty("ListaUsuarios")) {
                        listaExistente = (ListaUsuarios) DadosXML.select("ListaUsuarios");

                        for (Usuario u : listaExistente.getUsuarios()) {
                            if (u.getUsuario().equals(novoUser.getUsuario())) {
                                return "#cadastro-usuario;usuario-existe";
                            }
                        }
                    }

                    //passa a lista antiga para o objeto novo, e inclui o registro novo
                    ListaUsuarios listaNova = new ListaUsuarios();
                    listaNova.setUsuarios(listaExistente.getUsuarios());
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
                            if (params[2].equalsIgnoreCase("all")) {
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
                        return "#cadastro-usuario;not-ok";
                    }
                } catch (JAXBException ex) {
                    return "#cadastro-usuario;not-ok";
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

    public static String comandoReset(String[] params) {
        switch (params[2]) {
            case "all":
                if (placarController.isExecutando()) {
                    return "#reset;not-ok";
                } else {
                    placarController.resetAll("true".equals(params[3]));
                    return "#reset;ok";
                }
            default:
                return "#reset;not-ok";
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
