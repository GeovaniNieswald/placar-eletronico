package com.acme.model;

/**
 * Enum referente aos comandos da comunicação socket.
 *
 * @author Alex Jung Celmer
 * @author Daniel Frey
 * @author Gabriel Cavalheiro Ullmann
 * @author Geovani Alex Nieswald
 */
public enum Comando {

    // Comandos
    ESCOLHER_ESPORTE(), VERIFICAR_USUARIO_PLACAR(), TEXTO_INFERIOR(),
    CADASTRO_USUARIO(), NOME_TIME(), PONTOS();

    // Construtor
    private Comando() {
    }
}
