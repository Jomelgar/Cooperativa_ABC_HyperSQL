package FrontEnd;






public class AccountNormal extends javax.swing.JPanel {
    private BackEnd.Account acc;
    private String user;
    
    public AccountNormal() {
        initComponents();
        user = BackEnd.Main.logged.getUsername();
        setData(user);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        type = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        num_acc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        money = new javax.swing.JTextField();
        liqbtn = new javax.swing.JButton();
        abonobtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Desc = new javax.swing.JTextArea();
        monto = new javax.swing.JSpinner();
        refreshbtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(0,0,0,0));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(740, 440));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aportaciones", "Retirable" }));
        type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeActionPerformed(evt);
            }
        });
        add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, -1));

        jLabel3.setText("Tipo de Cuenta:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 70, 110, 20));

        jLabel4.setText("Comentario:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));

        num_acc.setEditable(false);
        add(num_acc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 160, -1));

        jLabel5.setText("Saldo:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        money.setEditable(false);
        add(money, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 160, -1));

        liqbtn.setBackground(new java.awt.Color(57, 45, 82));
        liqbtn.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        liqbtn.setForeground(new java.awt.Color(255, 255, 255));
        liqbtn.setText("REALIZAR LIQUIDACION");
        liqbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liqbtnActionPerformed(evt);
            }
        });
        add(liqbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 260, 40));

        abonobtn.setBackground(new java.awt.Color(57, 45, 82));
        abonobtn.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        abonobtn.setForeground(new java.awt.Color(255, 255, 255));
        abonobtn.setText("REALIZAR ABONO");
        abonobtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abonobtnActionPerformed(evt);
            }
        });
        add(abonobtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 260, 40));

        jLabel6.setText("Numero de Cuenta:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel7.setText("Monto:");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, -1, -1));

        Desc.setColumns(4);
        Desc.setRows(5);
        Desc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                DescKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(Desc);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 220, -1));

        monto.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.1d));
        add(monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 160, -1));

        refreshbtn.setBackground(new java.awt.Color(57, 45, 82));
        refreshbtn.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        refreshbtn.setForeground(new java.awt.Color(255, 255, 255));
        refreshbtn.setText("Refrescar");
        refreshbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshbtnActionPerformed(evt);
            }
        });
        add(refreshbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 120, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void DescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DescKeyTyped
        if(Desc.getText().length() > 256)
        {
            evt.consume();
        }
    }//GEN-LAST:event_DescKeyTyped

    private void abonobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abonobtnActionPerformed
        BackEnd.Main.db.setPayment(num_acc.getText(), Desc.getText(), (Double)monto.getValue());
        setData(user);
    }//GEN-LAST:event_abonobtnActionPerformed

    private void typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeActionPerformed
        setData(user);
    }//GEN-LAST:event_typeActionPerformed

    private void liqbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liqbtnActionPerformed
       liquidation liq = new liquidation(null,true,(double)monto.getValue(),user, BackEnd.Main.logged.getUsername());
       liq.setVisible(true);
    }//GEN-LAST:event_liqbtnActionPerformed

    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbtnActionPerformed
        // TODO add your handling code here:
        setData(user);
    }//GEN-LAST:event_refreshbtnActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Desc;
    private javax.swing.JButton abonobtn;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton liqbtn;
    private javax.swing.JTextField money;
    private javax.swing.JSpinner monto;
    private javax.swing.JTextField num_acc;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JComboBox<String> type;
    // End of variables declaration//GEN-END:variables

    private void setData(String user) {
        acc = BackEnd.Main.db.getAccount(BackEnd.Main.db.getCodeEmp(user));
        int selected = type.getSelectedIndex();
        if(selected == 0)
        {
            num_acc.setText(acc.numero_Apo());
            money.setText(Double.toString(acc.saldo_Apo()));
        } else
        {
            num_acc.setText(acc.numero_Afi());
            money.setText(Double.toString(acc.saldo_Afi()));
            
        }
    }
}
