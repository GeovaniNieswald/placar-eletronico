package com.acme.model;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class DadosXML {

    public static void salvarEscalacoes(String caminho, ListaEscalacoes escalacoes) throws JAXBException {
        File file = new File(caminho);
        JAXBContext jaxbContext = JAXBContext.newInstance(ListaEscalacoes.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(escalacoes, file);
    }

    public static ListaEscalacoes carregarEscalações(String caminho) throws JAXBException {
        File file = new File(caminho);
        JAXBContext jaxbContext = JAXBContext.newInstance(ListaEscalacoes.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (ListaEscalacoes) jaxbUnmarshaller.unmarshal(file);
    }
}
