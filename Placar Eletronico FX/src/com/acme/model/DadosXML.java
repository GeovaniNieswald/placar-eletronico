package com.acme.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        ARQUIVOS.put("ListaUsuarios", "/xml/usuarios.xml");
    }

    public static Object select(String arquivo) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ListaUsuarios.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(getArquivo(arquivo));
    }

    public static void insert(String arquivo, Object values) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ListaUsuarios.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(values, getArquivo(arquivo));
    }

    public static boolean isEmpty(String arquivo) throws IOException {
        File arq = getArquivo(arquivo);
        return arq.length() == 0;
    }

    private static File getArquivo(String arquivo) throws IOException {
        InputStream is = DadosXML.class.getResourceAsStream(ARQUIVOS.get(arquivo));

        File tempFile = File.createTempFile("placar", ".tmp");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(is, tempFile);

        is.close();

        return tempFile;
    }
}
