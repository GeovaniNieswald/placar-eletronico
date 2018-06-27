package com.acme.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.FileUtils;

public class DadosXML {

    private static final HashMap<String, String> ARQUIVOS = new HashMap<>();

    //listar aqui todos os arquivos existentes e seus caminhos
    static {
        ARQUIVOS.put("ListaUsuarios", System.getProperty("user.home") + "/Placar-Eletronico/Users/usuarios.xml");
    }

    public static Object select(String keyArquivo) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ListaUsuarios.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(getArquivo(keyArquivo));
    }

    public static void insert(String keyArquivo, Object values) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ListaUsuarios.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(values, getArquivo(keyArquivo));
    }

    public static boolean isEmpty(String keyArquivo) {
        return getArquivo(keyArquivo).length() == 0;
    }

    private static File getArquivo(String keyArquivo) {
        return new File(ARQUIVOS.get(keyArquivo));
    }

    public static void adicionarUsuariosPadrao() throws IOException, JAXBException {
        File arquivo = getArquivo("ListaUsuarios");
        FileUtils.copyInputStreamToFile(DadosXML.class.getResourceAsStream("/xml/usuarios.xml"), arquivo);

        JAXBContext jaxbContext = JAXBContext.newInstance(ListaUsuarios.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Object usuarios = jaxbUnmarshaller.unmarshal(arquivo);

        insert("ListaUsuarios", usuarios);
    }
}
