package com.acme.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JogadorTabela extends RecursiveTreeObject<JogadorTabela> {

    private StringProperty posicao;
    private StringProperty numero;
    private StringProperty nome;

    public JogadorTabela(String posicao, String numero, String nome) {
        this.posicao = new SimpleStringProperty(posicao);
        this.numero = new SimpleStringProperty(numero);
        this.nome = new SimpleStringProperty(nome);
    }

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
