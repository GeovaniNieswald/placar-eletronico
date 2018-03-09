/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.view;

/**
 *
 * @author Geovani Nieswald
 */
public class JFTelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form JFTelaPrincipal
     */
    public JFTelaPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPainelPrincipal = new javax.swing.JPanel();
        jpCronometro = new javax.swing.JPanel();
        jtfCronometro = new javax.swing.JTextField();
        jbIniciarCronometro = new javax.swing.JButton();
        jbPausarCronometro = new javax.swing.JButton();
        jbZerarCronometro = new javax.swing.JButton();
        jpPeriodo = new javax.swing.JPanel();
        jtfPeriodo = new javax.swing.JTextField();
        jbAumentarPeriodo = new javax.swing.JButton();
        jbDiminuirPeriodo = new javax.swing.JButton();
        jbZerarPeriodo = new javax.swing.JButton();
        jpPontos = new javax.swing.JPanel();
        jlPontosTimeA = new javax.swing.JLabel();
        jlPontosTimeB = new javax.swing.JLabel();
        jtfPontosTimeA = new javax.swing.JTextField();
        jtfPontosTimeB = new javax.swing.JTextField();
        jbAumentarPontosTimeA = new javax.swing.JButton();
        jbDiminuirPontosTimeA = new javax.swing.JButton();
        jbAumentarPontosTimeB = new javax.swing.JButton();
        jbDiminuirPontosTimeB = new javax.swing.JButton();
        jbZerarPontos = new javax.swing.JButton();
        jpFaltasSets = new javax.swing.JPanel();
        jlFaltasSetsTimeA = new javax.swing.JLabel();
        jlFaltasSetsTimeB = new javax.swing.JLabel();
        jtfFaltasSetsTimeA = new javax.swing.JTextField();
        jtfFaltasSetsTimeB = new javax.swing.JTextField();
        jbAumentarFaltasSetsTimeA = new javax.swing.JButton();
        jbDiminuirFaltasSetsTimeA = new javax.swing.JButton();
        jbAumentarFaltasSetsTimeB = new javax.swing.JButton();
        jbDiminuirFaltasSetsTimeB = new javax.swing.JButton();
        jbZerarFaltasSets = new javax.swing.JButton();
        jpTextoPainel = new javax.swing.JPanel();
        jtfTextoPainel = new javax.swing.JTextField();
        jbAlterarTextoPainel = new javax.swing.JButton();
        jbResetarTextoPainel = new javax.swing.JButton();
        jbZerarTudo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controlador Placar Eletrônico");

        jpCronometro.setBorder(javax.swing.BorderFactory.createTitledBorder("Cronômetro"));

        jtfCronometro.setEditable(false);

        jbIniciarCronometro.setText("Iniciar");

        jbPausarCronometro.setText("Pausar");

        jbZerarCronometro.setText("Zerar");

        javax.swing.GroupLayout jpCronometroLayout = new javax.swing.GroupLayout(jpCronometro);
        jpCronometro.setLayout(jpCronometroLayout);
        jpCronometroLayout.setHorizontalGroup(
            jpCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCronometroLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfCronometro)
                    .addGroup(jpCronometroLayout.createSequentialGroup()
                        .addComponent(jbIniciarCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jbPausarCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jbZerarCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        jpCronometroLayout.setVerticalGroup(
            jpCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCronometroLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jtfCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jpCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbIniciarCronometro)
                    .addComponent(jbPausarCronometro)
                    .addComponent(jbZerarCronometro))
                .addGap(20, 20, 20))
        );

        jpPeriodo.setBorder(javax.swing.BorderFactory.createTitledBorder("Período"));

        jtfPeriodo.setEditable(false);

        jbAumentarPeriodo.setText("+");

        jbDiminuirPeriodo.setText("-");

        jbZerarPeriodo.setText("Zerar");

        javax.swing.GroupLayout jpPeriodoLayout = new javax.swing.GroupLayout(jpPeriodo);
        jpPeriodo.setLayout(jpPeriodoLayout);
        jpPeriodoLayout.setHorizontalGroup(
            jpPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPeriodoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jbAumentarPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jpPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfPeriodo)
                    .addComponent(jbDiminuirPeriodo, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbZerarPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jpPeriodoLayout.setVerticalGroup(
            jpPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPeriodoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jtfPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jpPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAumentarPeriodo)
                    .addComponent(jbDiminuirPeriodo)
                    .addComponent(jbZerarPeriodo))
                .addGap(20, 20, 20))
        );

        jpPontos.setBorder(javax.swing.BorderFactory.createTitledBorder("Pontos"));

        jlPontosTimeA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPontosTimeA.setText("Time A");

        jlPontosTimeB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPontosTimeB.setText("Time B");

        jtfPontosTimeA.setEditable(false);

        jtfPontosTimeB.setEditable(false);

        jbAumentarPontosTimeA.setText("+");

        jbDiminuirPontosTimeA.setText("-");

        jbAumentarPontosTimeB.setText("+");

        jbDiminuirPontosTimeB.setText("-");

        jbZerarPontos.setText("Zerar");

        javax.swing.GroupLayout jpPontosLayout = new javax.swing.GroupLayout(jpPontos);
        jpPontos.setLayout(jpPontosLayout);
        jpPontosLayout.setHorizontalGroup(
            jpPontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPontosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpPontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfPontosTimeA)
                    .addComponent(jlPontosTimeA, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jbAumentarPontosTimeA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbDiminuirPontosTimeA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addComponent(jbZerarPontos)
                .addGap(21, 21, 21)
                .addGroup(jpPontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlPontosTimeB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfPontosTimeB, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbAumentarPontosTimeB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jbDiminuirPontosTimeB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jpPontosLayout.setVerticalGroup(
            jpPontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPontosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpPontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPontosTimeA)
                    .addComponent(jlPontosTimeB))
                .addGap(10, 10, 10)
                .addGroup(jpPontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfPontosTimeA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPontosTimeB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jpPontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAumentarPontosTimeA)
                    .addComponent(jbAumentarPontosTimeB))
                .addGap(20, 20, 20)
                .addGroup(jpPontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbDiminuirPontosTimeA)
                    .addComponent(jbDiminuirPontosTimeB)
                    .addComponent(jbZerarPontos))
                .addGap(20, 20, 20))
        );

        jpFaltasSets.setBorder(javax.swing.BorderFactory.createTitledBorder("Faltas / Sets"));

        jlFaltasSetsTimeA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFaltasSetsTimeA.setText("Time A");

        jlFaltasSetsTimeB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFaltasSetsTimeB.setText("Time B");

        jtfFaltasSetsTimeA.setEditable(false);

        jtfFaltasSetsTimeB.setEditable(false);

        jbAumentarFaltasSetsTimeA.setText("+");

        jbDiminuirFaltasSetsTimeA.setText("-");

        jbAumentarFaltasSetsTimeB.setText("+");

        jbDiminuirFaltasSetsTimeB.setText("-");

        jbZerarFaltasSets.setText("Zerar");

        javax.swing.GroupLayout jpFaltasSetsLayout = new javax.swing.GroupLayout(jpFaltasSets);
        jpFaltasSets.setLayout(jpFaltasSetsLayout);
        jpFaltasSetsLayout.setHorizontalGroup(
            jpFaltasSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFaltasSetsLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpFaltasSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfFaltasSetsTimeA)
                    .addComponent(jlFaltasSetsTimeA, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jbAumentarFaltasSetsTimeA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbDiminuirFaltasSetsTimeA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addComponent(jbZerarFaltasSets)
                .addGap(21, 21, 21)
                .addGroup(jpFaltasSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlFaltasSetsTimeB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfFaltasSetsTimeB, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbAumentarFaltasSetsTimeB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jbDiminuirFaltasSetsTimeB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jpFaltasSetsLayout.setVerticalGroup(
            jpFaltasSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFaltasSetsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpFaltasSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlFaltasSetsTimeA)
                    .addComponent(jlFaltasSetsTimeB))
                .addGap(10, 10, 10)
                .addGroup(jpFaltasSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfFaltasSetsTimeA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfFaltasSetsTimeB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jpFaltasSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAumentarFaltasSetsTimeA)
                    .addComponent(jbAumentarFaltasSetsTimeB))
                .addGap(20, 20, 20)
                .addGroup(jpFaltasSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbDiminuirFaltasSetsTimeA)
                    .addComponent(jbDiminuirFaltasSetsTimeB)
                    .addComponent(jbZerarFaltasSets))
                .addGap(20, 20, 20))
        );

        jpTextoPainel.setBorder(javax.swing.BorderFactory.createTitledBorder("Texto do Painel"));

        jbAlterarTextoPainel.setText("Alterar");

        jbResetarTextoPainel.setText("Resetar");

        javax.swing.GroupLayout jpTextoPainelLayout = new javax.swing.GroupLayout(jpTextoPainel);
        jpTextoPainel.setLayout(jpTextoPainelLayout);
        jpTextoPainelLayout.setHorizontalGroup(
            jpTextoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTextoPainelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jtfTextoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbAlterarTextoPainel)
                .addGap(30, 30, 30)
                .addComponent(jbResetarTextoPainel)
                .addGap(20, 20, 20))
        );
        jpTextoPainelLayout.setVerticalGroup(
            jpTextoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTextoPainelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jpTextoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTextoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAlterarTextoPainel)
                    .addComponent(jbResetarTextoPainel))
                .addGap(18, 18, 18))
        );

        jbZerarTudo.setText("Zerar Tudo");

        javax.swing.GroupLayout jpPainelPrincipalLayout = new javax.swing.GroupLayout(jpPainelPrincipal);
        jpPainelPrincipal.setLayout(jpPainelPrincipalLayout);
        jpPainelPrincipalLayout.setHorizontalGroup(
            jpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPainelPrincipalLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbZerarTudo)
                    .addGroup(jpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jpTextoPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpPainelPrincipalLayout.createSequentialGroup()
                            .addGroup(jpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jpCronometro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jpPontos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addGroup(jpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jpPeriodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jpFaltasSets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jpPainelPrincipalLayout.setVerticalGroup(
            jpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPainelPrincipalLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpCronometro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpFaltasSets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpPontos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jpTextoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jbZerarTudo)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPainelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPainelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName()) || "Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFTelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFTelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAlterarTextoPainel;
    private javax.swing.JButton jbAumentarFaltasSetsTimeA;
    private javax.swing.JButton jbAumentarFaltasSetsTimeB;
    private javax.swing.JButton jbAumentarPeriodo;
    private javax.swing.JButton jbAumentarPontosTimeA;
    private javax.swing.JButton jbAumentarPontosTimeB;
    private javax.swing.JButton jbDiminuirFaltasSetsTimeA;
    private javax.swing.JButton jbDiminuirFaltasSetsTimeB;
    private javax.swing.JButton jbDiminuirPeriodo;
    private javax.swing.JButton jbDiminuirPontosTimeA;
    private javax.swing.JButton jbDiminuirPontosTimeB;
    private javax.swing.JButton jbIniciarCronometro;
    private javax.swing.JButton jbPausarCronometro;
    private javax.swing.JButton jbResetarTextoPainel;
    private javax.swing.JButton jbZerarCronometro;
    private javax.swing.JButton jbZerarFaltasSets;
    private javax.swing.JButton jbZerarPeriodo;
    private javax.swing.JButton jbZerarPontos;
    private javax.swing.JButton jbZerarTudo;
    private javax.swing.JLabel jlFaltasSetsTimeA;
    private javax.swing.JLabel jlFaltasSetsTimeB;
    private javax.swing.JLabel jlPontosTimeA;
    private javax.swing.JLabel jlPontosTimeB;
    private javax.swing.JPanel jpCronometro;
    private javax.swing.JPanel jpFaltasSets;
    private javax.swing.JPanel jpPainelPrincipal;
    private javax.swing.JPanel jpPeriodo;
    private javax.swing.JPanel jpPontos;
    private javax.swing.JPanel jpTextoPainel;
    private javax.swing.JTextField jtfCronometro;
    private javax.swing.JTextField jtfFaltasSetsTimeA;
    private javax.swing.JTextField jtfFaltasSetsTimeB;
    private javax.swing.JTextField jtfPeriodo;
    private javax.swing.JTextField jtfPontosTimeA;
    private javax.swing.JTextField jtfPontosTimeB;
    private javax.swing.JTextField jtfTextoPainel;
    // End of variables declaration//GEN-END:variables
}
