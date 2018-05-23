package com.acme.model;

import com.acme.controller.PlacarController;

public class Cronos implements Runnable {

    private PlacarController pc;

    private int minutos;
    private int segundos;

    public Cronos(PlacarController pc, String minutos, String segundos) {
        this.pc = pc;
        this.minutos = Integer.parseInt(minutos);
        this.segundos = Integer.parseInt(segundos);
    }

    @Override
    public void run() {
        while (pc.isExecutando()) {
            if (segundos == 0) {
                minutos--;
                segundos = 59;
            } else {
                segundos--;
            }

            pc.alterarCronometro(segundos, minutos);

            if (minutos == 0 && segundos == 0) {
                pc.pararCronometro();
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
