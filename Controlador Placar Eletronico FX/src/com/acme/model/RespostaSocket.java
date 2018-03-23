package com.acme.model;

public enum RespostaSocket {

    // Respostas de conexao
    CONEXAO_ACEITA_USUARIO_PRINCIPAL(), CONEXAO_ACEITA_USUARIO_PROPAGANDA(), CONEXAO_RECUSADA(),
    // Respostas de escolha de esporte
    ESPORTE_ACEITO_BASQUETE(), ESPORTE_ACEITO_FUTSAL(), ESPORTE_RECUSADO(),
    // Respostas verificação de usuario principal
    USUARIO_PRINCIPAL_CONECTADO(), USUARIO_PRINCIPAL_NAO_CONECTADO(),
    // Respostas comandos
    COMANDO_ACEITO(), COMANDO_RECUSADO();

    // Construtor
    private RespostaSocket() {
    }
}
