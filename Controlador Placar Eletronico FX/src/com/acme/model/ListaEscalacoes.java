package com.acme.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listaEscalacoes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaEscalacoes {

    @XmlElement(name = "escalacao")
    private List<Escalacao> escalacoes = new ArrayList<>();

    public List<Escalacao> getEscalacoes() {
        return escalacoes;
    }

    public void setEscalacoes(ArrayList<Escalacao> escalacoes) {
        this.escalacoes = escalacoes;
    }
}
