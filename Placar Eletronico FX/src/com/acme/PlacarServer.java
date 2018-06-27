package com.acme;

import com.acme.controller.PlacarController;
import com.acme.controller.PropagandaController;
import com.acme.model.Cena;
import com.acme.model.DadosXML;
import com.acme.model.ListaUsuarios;
import com.acme.model.ResultadoLogin;
import com.acme.model.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.xml.bind.JAXBException;

public class PlacarServer extends Thread {

    private final Socket SOCKET;
    private Usuario usuarioAtual;

    private static boolean usuarioPlacarOn;
    private static boolean usuarioPropagandaOn;
    private static boolean usuarioAdmOn;

    private static PlacarController placarController;
    private static PropagandaController propagandaController;

    public PlacarServer(Socket socket) {
        this.SOCKET = socket;
    }

    public static void instanciaPlacarController(PlacarController pc) {
        placarController = pc;
    }

    public static void instanciaPropagandaController(PropagandaController pc) {
        propagandaController = pc;
    }

    // Método para iniciar o servidor
    public static void iniciar() {
        Thread t = new Thread(() -> {
            try (ServerSocket listener = new ServerSocket(9898)) {
                while (true) {
                    new PlacarServer(listener.accept()).start();
                }
            } catch (IOException ex) {
                MeuLogger.logException(Level.SEVERE, "Erro ao iniciar servidor.", ex);
            }
        });
        t.start();
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));
            PrintWriter out = new PrintWriter(SOCKET.getOutputStream(), true);

            String login = in.readLine();
            String senha = in.readLine();
            usuarioAtual = new Usuario();

            ResultadoLogin resultado = validaLogin(login, senha, usuarioAtual);

            if (resultado != ResultadoLogin.USUARIO_INVALIDO) {
                switch (resultado) {
                    case USUARIO_VALIDO:
                        if (usuarioAtual.isAdmin()) {
                            out.println("#conexao;ok;usuario-administrador");
                        } else if (usuarioAtual.isPlacar()) {
                            out.println("#conexao;ok;usuario-placar");
                        } else {
                            out.println("#conexao;ok;usuario-propaganda");
                        }
                        break;
                    case USUARIO_ADM_CONECTADO:
                        out.println("#conexao;not-ok;usuario-administrador");
                        break;
                    case USUARIO_PROPAGANDA_CONECTADO:
                        out.println("#conexao;not-ok;usuario-propaganda");
                        break;
                    case USUARIO_PLACAR_CONECTADO:
                        out.println("#conexao;not-ok;usuario-placar");
                        break;
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
                out.println("#conexao;not-ok;usuario-invalido");
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.SEVERE, "Erro de conexão.", ex);
        } finally {
            try {
                SOCKET.close();
            } catch (IOException ex) {
                MeuLogger.logException(Level.WARNING, "Não foi possível fechar a conexão.", ex);
            }
        }
    }

    private ResultadoLogin validaLogin(String login, String senha, Usuario usuario) {
        try {
            if (login == null || senha == null) {
                return ResultadoLogin.USUARIO_INVALIDO;
            } else {
                ListaUsuarios users = (ListaUsuarios) DadosXML.select("ListaUsuarios");

                for (Usuario u : users.getUsuarios()) {
                    if (login.equals(u.getUsuario()) && Utils.stringToMd5(senha).equals(u.getSenha())) {
                        usuario.setUsuario(u.getUsuario());
                        usuario.setSenha(u.getSenha());
                        usuario.setAdmin(u.isAdmin());
                        usuario.setPlacar(u.isPlacar());
                        usuario.setPropaganda(u.isPropaganda());

                        if (usuarioAdmOn && usuario.isAdmin()) {
                            return ResultadoLogin.USUARIO_ADM_CONECTADO;
                        } else if (usuarioPlacarOn && usuario.isPlacar()) {
                            return ResultadoLogin.USUARIO_PLACAR_CONECTADO;
                        } else if (usuarioPropagandaOn && usuario.isPropaganda()) {
                            return ResultadoLogin.USUARIO_PROPAGANDA_CONECTADO;
                        }

                        if (usuario.isAdmin()) {
                            usuarioAdmOn = true;
                        } else if (usuario.isPropaganda()) {
                            usuarioPropagandaOn = true;
                        }

                        return ResultadoLogin.USUARIO_VALIDO;
                    }
                }
            }
        } catch (JAXBException | IOException ex) {
            MeuLogger.logException(Level.SEVERE, "Erro no XML.", ex);
            return ResultadoLogin.USUARIO_INVALIDO;
        } catch (NoSuchAlgorithmException ex) {
            MeuLogger.logException(Level.SEVERE, "Erro na geração do MD5.", ex);
            return ResultadoLogin.USUARIO_INVALIDO;
        }

        return ResultadoLogin.USUARIO_INVALIDO;
    }

    public String processarComando(String comando) {
        String[] params = comando.split(";");
        if (params != null) {
            switch (params[0]) {
                case "#cadastro-usuario":
                    return comandoCadastroUsuario(params);
                case "#esporte":
                    return comandoEsporte(params);
                case "#usuario-placar":
                    return comandoUsuarioPlacar(params);
                case "#nome-time":
                    return comandoNomeTime(params);
                case "#cronometro":
                    return comandoCronometro(params);
                case "#periodo":
                    return comandoPeriodo(params);
                case "#posse":
                    return comandoPosse(params);
                case "#bonus":
                    return comandoBonus(params);
                case "#pontos":
                    return comandoPontos(params);
                case "#faltas":
                    return comandoFaltas(params);
                case "#texto-inferior":
                    return comandoTextoInferior(params);
                case "#imagens":
                    return comandoImagens(params);
                case "#propaganda":
                    return comandoPropaganda(params);
                case "#desconectar":
                    return comandoDesconectar();
                default:
                    return "#comando;not-ok";
            }
        } else {
            return "#comando;not-ok";
        }
    }

    private static void trocarCena(Cena c) {
        Platform.runLater(() -> MainApp.trocarCena(c));
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
                } catch (JAXBException | IOException ex) {
                    MeuLogger.logException(Level.SEVERE, "Erro no XML.", ex);
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
                } catch (JAXBException | IOException ex) {
                    MeuLogger.logException(Level.SEVERE, "Erro no XML.", ex);
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

                    listaAtualizada.getUsuarios().stream()
                            .filter((u) -> !u.getUsuario().equals(params[2]))
                            .forEach((u) -> listaPermanece.add(u));

                    listaAtualizada.setUsuarios(listaPermanece);
                    DadosXML.insert("ListaUsuarios", listaAtualizada);

                    return "#cadastro-usuario;ok";
                } catch (JAXBException | IOException ex) {
                    MeuLogger.logException(Level.SEVERE, "Erro no XML.", ex);
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

                    listaAtualizada.getUsuarios().stream()
                            .filter((u) -> u.getUsuario().equals(params[2]))
                            .forEach((u) -> u.setSenha(params[3]));

                    DadosXML.insert("ListaUsuarios", listaAtualizada);

                    return "#cadastro-usuario;ok";
                } catch (JAXBException | IOException ex) {
                    MeuLogger.logException(Level.SEVERE, "Erro no XML.", ex);
                    return "#cadastro-usuario;not-ok";
                }
            default:
                return "#cadastro-usuario;not-ok";
        }
    }

    public static String comandoEsporte(String[] params) {
        switch (params[1]) {
            case "basquete":
                if (usuarioPlacarOn) {
                    return "#esporte;not-ok";
                } else {
                    trocarCena(Cena.PLACAR_BASQUETE);
                    usuarioPlacarOn = true;
                    return "#esporte;basquete-ok";
                }
            case "futsal":
                if (usuarioPlacarOn) {
                    return "#esporte;not-ok";
                } else {
                    trocarCena(Cena.PLACAR_FUTSAL);
                    usuarioPlacarOn = true;
                    return "#esporte;futsal-ok";
                }
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
            case "visitante":
                switch (params[2]) {
                    case "alterar":
                        placarController.alterarNomeTime(params[1], params[3]);
                        return "#nome-time;ok";
                    case "restaurar":
                        placarController.alterarNomeTime(params[1], "VISITANTE");
                        return "#nome-time;ok";
                    default:
                        return "#nome-time;not-ok";
                }
            default:
                return "#nome-time;not-ok";
        }
    }

    public static String comandoCronometro(String[] params) {
        switch (params[1]) {
            case "definir":
                placarController.definirCronometro(params[2], params[3]);
                return "#cronometro;ok";
            case "iniciar":
                placarController.iniciarCronometro(params[2], params[3]);
                return "#cronometro;ok";
            case "pausar":
                placarController.pausarCronometro();
                return "#cronometro;ok";
            case "zerar":
                placarController.zerarCronometro(params[2]);
                return "#cronometro;ok";
            default:
                return "#cronometro;not-ok";
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

    public static String comandoPosse(String[] params) {
        switch (params[1]) {
            case "local":
            case "visitante":
                switch (params[2]) {
                    case "trocar":
                        placarController.trocarPosse(params[1]);
                        return "#posse;ok";
                    default:
                        return "#posse;not-ok";
                }
            default:
                return "#posse;not-ok";
        }
    }

    public static String comandoBonus(String[] params) {
        switch (params[1]) {
            case "local":
            case "visitante":
                switch (params[2]) {
                    case "definir":
                        placarController.definirBonus(params[1]);
                        return "#bonus;ok";
                    case "remover":
                        placarController.removerBonus(params[1]);
                        return "#bonus;ok";
                    default:
                        return "#bonus;not-ok";
                }
            default:
                return "#bonus;not-ok";
        }
    }

    public static String comandoPontos(String[] params) {
        switch (params[1]) {
            case "local":
            case "visitante":
                switch (params[2]) {
                    case "mais":
                        placarController.aumentarPontos(params[1], Integer.parseInt(params[3]));
                        return "#pontos;ok";
                    case "menos":
                        placarController.diminuirPontos(params[1], Integer.parseInt(params[3]));
                        return "#pontos;ok";
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
            case "visitante":
                switch (params[2]) {
                    case "mais":
                        placarController.aumentarFaltas(params[1], Integer.parseInt(params[3]));
                        return "#faltas;ok";
                    case "menos":
                        placarController.diminuirFaltas(params[1], Integer.parseInt(params[3]));
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

    public static String comandoTextoInferior(String[] params) {
        switch (params[1]) {
            case "alterar":
                placarController.alterarTextoInferior(params[2]);
                return "#texto-inferior;ok";
            case "restaurar":
                placarController.alterarTextoInferior("PLACAR ELETRONICO FX");
                return "#texto-inferior;ok";
            default:
                return "#texto-inferior;not-ok";
        }
    }

    public static String comandoImagens(String[] params) {
        try {
            switch (params[1]) {
                case "direita":
                case "esquerda":
                    switch (params[2]) {
                        case "alterar":
                            placarController.alterarImagem(params[1], Utils.decodificar("imagem", params[3]));
                            return "#imagens;ok";
                        case "restaurar":
                            placarController.restaurarImagem(params[1]);
                            return "#imagens;ok";
                        default:
                            return "#imagens;not-ok";
                    }
                default:
                    return "#imagens;not-ok";
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.WARNING, "Não foi possível decodificar a imagem.", ex);
            return "#imagens;not-ok";
        }
    }

    public static String comandoPropaganda(String[] params) {
        try {
            switch (params[1]) {
                case "imagem":
                    switch (params[2]) {
                        case "exibir":
                            trocarCena(Cena.PROPAGANDA);
                            sleep(1000);
                            propagandaController.exibirPropagandaImagem(Utils.decodificar("imagem", params[3]));
                            return "#propaganda;ok";
                        case "parar":
                            trocarCena(Cena.ATUAL);
                            return "#propaganda;ok";
                        default:
                            return "#propaganda;not-ok";
                    }
                case "video":
                    switch (params[2]) {
                        case "exibir":
                            trocarCena(Cena.PROPAGANDA);
                            sleep(1000);
                            propagandaController.exibirPropagandaVideo(Utils.decodificar("video", params[3]));
                            return "#propaganda;ok";
                        case "parar":
                            propagandaController.pararPropagandaVideo();
                            sleep(1000);
                            trocarCena(Cena.ATUAL);
                            return "#propaganda;ok";
                        default:
                            return "#propaganda;not-ok";
                    }
                case "escalacao":
                    switch (params[2]) {
                        case "exibir":
                            trocarCena(Cena.PROPAGANDA);
                            sleep(1000);
                            propagandaController.exibirEscalacoes(params[3]);
                            return "#propaganda;ok";
                        case "parar":
                            trocarCena(Cena.ATUAL);
                            return "#propaganda;ok";
                        default:
                            return "#propaganda;not-ok";
                    }
                default:
                    return "#propaganda;not-ok";
            }
        } catch (IOException ex) {
            MeuLogger.logException(Level.WARNING, "Não foi possível decodificar a imagem/vídeo.", ex);
            return "#propaganda;not-ok";
        } catch (InterruptedException ex) {
            MeuLogger.logException(Level.INFO, "Thread interrompida.", ex);
            return "#propaganda;not-ok";
        }
    }

    public String comandoDesconectar() {
        if (usuarioAtual.isAdmin()) {
            usuarioAdmOn = false;
        } else if (usuarioAtual.isPlacar()) {
            usuarioPlacarOn = false;
        } else if (usuarioAtual.isPropaganda()) {
            usuarioPropagandaOn = false;
        }

        try {
            SOCKET.close();
        } catch (IOException ex) {
            MeuLogger.logException(Level.WARNING, "Não foi possível fechar a conexão.", ex);
        }

        return "";
    }
}
