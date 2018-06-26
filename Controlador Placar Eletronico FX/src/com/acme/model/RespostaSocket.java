package com.acme.model;

public enum RespostaSocket {

    // Respostas de conexao
    CONEXAO_ACEITA_USUARIO_ADM(), CONEXAO_ACEITA_USUARIO_PLACAR(),
    CONEXAO_ACEITA_USUARIO_PROPAGANDA(),
    CONEXAO_RECUSADA_USUARIO_INVALIDO(), CONEXAO_RECUSADA_USUARIO_ADM_ON(),
    CONEXAO_RECUSADA_USUARIO_PLACAR_ON(), CONEXAO_RECUSADA_USUARIO_PROPAGANDA_ON(),
    // Respostas de escolha de esporte
    ESPORTE_ACEITO_BASQUETE(), ESPORTE_ACEITO_FUTSAL(),
    // Respostas verificação de usuario principal
    USUARIO_PLACAR_CONECTADO(), USUARIO_PLACAR_NAO_CONECTADO(),
    //Cadastro de usuario
    USUARIO_JA_EXISTE(),
    // Respostas de comandos
    COMANDO_ACEITO(), COMANDO_RECUSADO();
}
