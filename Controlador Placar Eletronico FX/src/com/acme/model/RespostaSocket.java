package com.acme.model;

public enum RespostaSocket {

    // Respostas de conexao
    CONEXAO_ACEITA_USUARIO_PRINCIPAL(), CONEXAO_ACEITA_USUARIO_PROPAGANDA(), CONEXAO_RECUSADA(),
    // Respostas de comando
    COMANDO_ACEITO_BASQUETE(), COMANDO_ACEITO_FUTSAL(), COMANDO_RECUSADO(),
    // Respostas verificação de usuario principal
    USUARIO_PRINCIPAL_CONECTADO(), USUARIO_PRINCIPAL_NAO_CONECTADO();

    // Construtor
    private RespostaSocket() {
    }
}
