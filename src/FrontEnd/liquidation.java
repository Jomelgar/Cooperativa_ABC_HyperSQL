package FrontEnd;

import javax.swing.JOptionPane;

public class liquidation extends javax.swing.JDialog {

    private double monto;
    private String user;
    private String modified;
    
    public liquidation(java.awt.Frame parent, boolean modal, double monto, String user,String modified) {
        super(parent, modal);
        initComponents();
        this.monto = monto;
        Monto.setText("Monto: Lps. " + monto);
        this.user = user;
        this.setLocationRelativeTo(null);
        this.modified = modified;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parcialbtn = new javax.swing.JButton();
        fullbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Monto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        parcialbtn.setBackground(new java.awt.Color(57, 45, 82));
        parcialbtn.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        parcialbtn.setForeground(new java.awt.Color(255, 255, 255));
        parcialbtn.setText("LIQUIDACION PARCIAL");
        parcialbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcialbtnActionPerformed(evt);
            }
        });

        fullbtn.setBackground(new java.awt.Color(57, 45, 82));
        fullbtn.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        fullbtn.setForeground(new java.awt.Color(255, 255, 255));
        fullbtn.setText("LIQUIDACION COMPLETA");
        fullbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullbtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        jLabel1.setText("¿Qué tipo de Liquidacion desea Realizar?");

        Monto.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        Monto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Monto.setText("Monto Parcial: XXXX.XXlps");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(Monto))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullbtn)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(parcialbtn)))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(Monto)
                .addGap(18, 18, 18)
                .addComponent(parcialbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(fullbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void parcialbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcialbtnActionPerformed
         BackEnd.Main.db.setLiquidation(BackEnd.Main.db.getCodeEmp(modified),monto);
    }//GEN-LAST:event_parcialbtnActionPerformed

    private void fullbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullbtnActionPerformed
        // TODO add your handling code here:
        double saldo = BackEnd.Main.db.getTotalLiquidation(user,modified);
        JOptionPane.showMessageDialog(null, "Usted acaba de retirar: " + saldo, "Liquidación Completa", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_fullbtnActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Monto;
    private javax.swing.JButton fullbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton parcialbtn;
    // End of variables declaration//GEN-END:variables
}
