package com.acme.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Jogador extends RecursiveTreeObject<Jogador> {

    // Atributos 
    private StringProperty posicao;
    private StringProperty numero;
    private StringProperty nome;

    // Construtor
    public Jogador(String posicao, String numero, String nome) {
        this.posicao = new SimpleStringProperty(posicao);
        this.numero = new SimpleStringProperty(numero);
        this.nome = new SimpleStringProperty(nome);
    }

    // Métodos
    public StringProperty getPosicao() {
        return posicao;
    }

    public void setPosicao(StringProperty posicao) {
        this.posicao = posicao;
    }

    public StringProperty getNumero() {
        return numero;
    }

    public void setNumero(StringProperty numero) {
        this.numero = numero;
    }

    public StringProperty getNome() {
        return nome;
    }

    public void setNome(StringProperty nome) {
        this.nome = nome;
    }
}
