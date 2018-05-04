package com.acme.model;

import com.acme.controller.PlacarController;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cronos implements Runnable {

    private PlacarController tela;

    private int segundos = 59;
    private int minutos = 19;
//    private int horas;

    public Cronos(PlacarController tc) {
        this.tela = tc;
    }

    @Override
    public void run() {
        System.out.println("Executando");
        while (tela.isExecutando()) {

           
            if (segundos == 0) {
                minutos--;
                segundos = 59;
            } else {
                segundos--;
            }

            tela.alterarCronometro(segundos, minutos);
            if (minutos == 0 && segundos == 0) {    
                break;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cronos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

//           if (minutos == 0 && segundos == 0) {
//                break;
//            } else {
//                tela.alterarCronometro(segundos, minutos);
//                segundos--;
//            }


//package com.acme.model;
//
//import com.acme.controller.PlacarController;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Cronos implements Runnable {
//
//    private PlacarController tela;
//
//    private int segundos;
//    private int minutos;
//    private int horas;
//
//    public Cronos(PlacarController tc) {
//        this.tela = tc;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("Executando");
//        while (tela.isExecutando()) {
//            segundos++;
//
//            if (segundos == 59) {
//                minutos++;
//            }
//
//            if (minutos == 99) {
//                horas++;
//            }
//
////            if (horas == 23 && minutos == 59 && segundos == 59) {
////                tela.setExecutando(false);
//////                tela.teste();
////            } 
//            else {
//                if (segundos == 59) {
//                    segundos = 0;
//                }
//
//                if (minutos == 99) {
//                    minutos = 0;
//                }
//            }
//
//            tela.alterarCronometro(segundos, minutos, horas);
//
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Cronos.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//}
