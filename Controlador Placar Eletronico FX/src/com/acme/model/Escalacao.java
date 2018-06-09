package com.acme.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "escalacao")
@XmlAccessorType(XmlAccessType.FIELD)
public class Escalacao {

    private boolean timeLocal;
    @XmlElement(name = "jogador")
    private List<Jogador> jogadores;

    public Escalacao(boolean timeLocal, ArrayList<Jogador> jogadores) {
        this.timeLocal = timeLocal;
        this.jogadores = jogadores;
    }

    public Escalacao() {
    }

    public boolean isTimeLocal() {
        return timeLocal;
    }

    public void setTimeLocal(boolean timeLocal) {
        this.timeLocal = timeLocal;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
