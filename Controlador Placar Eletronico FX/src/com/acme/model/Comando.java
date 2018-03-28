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
    ESCOLHER_ESPORTE_BASQUETE(), ESCOLHER_ESPORTE_FUTSAL(), VERIFICAR_USUARIO_PRINCIPAL(), ALTERAR_TEXTO_INFERIOR(),
    RESTAURAR_TEXTO_INFERIOR(), CRIAR_USUARIO();

    // Construtor
    private Comando() {
    }
}
