package com.acme.model;

import com.acme.controller.PlacarController;

public class Cronos implements Runnable {

    private PlacarController tela;

    private int minutos;
    private int segundos;

    public Cronos(PlacarController pc, String minutos, String segundos) {
        this.tela = pc;
        this.minutos = Integer.parseInt(minutos);
        this.segundos = Integer.parseInt(segundos);
    }

    @Override
    public void run() {
        while (tela.isExecutando()) {
            if (segundos == 0) {
                minutos--;
                segundos = 59;
            } else {
                segundos--;
            }

            tela.alterarCronometro(segundos, minutos);

            if (minutos == 0 && segundos == 0) {
                tela.bloqueiaBotoes();
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                // Implementar log
            }
        }
    }
}
