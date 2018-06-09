package com.acme.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "jogador")
@XmlAccessorType(XmlAccessType.FIELD)
public class Jogador {

    // Atributos 
    private String posicao;
    private String numero;
    private String nome;

    // Construtor
    public Jogador(String posicao, String numero, String nome) {
        this.posicao = posicao;
        this.numero = numero;
        this.nome = nome;
    }
    public Jogador() {
    }

    // Métodos
    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
