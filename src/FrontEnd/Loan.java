package FrontEnd;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;


public class Loan extends javax.swing.JPanel {
    private String user;
    
    public Loan() {
        initComponents();
        user = BackEnd.Main.logged.getUsername();
        if(BackEnd.Main.db.countLoans(user) > 0) {setData(user);}
        SpinnerModel model = new SpinnerNumberModel(0,0,BackEnd.Main.db.getMoneyApo(user),0.1);
        monto.setModel(model);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        type = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        num_loan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        amount_paid = new javax.swing.JTextField();
        paybtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        monto = new javax.swing.JSpinner();
        refreshbtn = new javax.swing.JButton();
        creation_date = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        periods = new javax.swing.JSpinner();
        loanbtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(0,0,0,0));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(740, 440));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Automatico", "Fiduciario" }));
        type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeActionPerformed(evt);
            }
        });
        add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 130, -1));

        jLabel3.setText("Tipo de Prestamo:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 110, 20));

        num_loan.setEditable(false);
        add(num_loan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 160, -1));

        jLabel5.setText("Saldo:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        amount_paid.setEditable(false);
        add(amount_paid, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 160, -1));

        paybtn.setBackground(new java.awt.Color(57, 45, 82));
        paybtn.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        paybtn.setForeground(new java.awt.Color(255, 255, 255));
        paybtn.setText("REALIZAR PAGO");
        paybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paybtnActionPerformed(evt);
            }
        });
        add(paybtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 260, 40));

        jLabel6.setText("Numero de Prestamo:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel7.setText("Monto:");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, -1, -1));

        monto.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, null, 0.1d));
        add(monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 160, -1));

        refreshbtn.setBackground(new java.awt.Color(57, 45, 82));
        refreshbtn.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        refreshbtn.setForeground(new java.awt.Color(255, 255, 255));
        refreshbtn.setText("Refrescar");
        refreshbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshbtnActionPerformed(evt);
            }
        });
        add(refreshbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 120, 40));

        creation_date.setEditable(false);
        add(creation_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 160, -1));

        jLabel8.setText("Fecha de Creacion:");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel9.setText("Periodos:");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        periods.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 12.0d, 1.0d));
        add(periods, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 160, -1));

        loanbtn.setBackground(new java.awt.Color(57, 45, 82));
        loanbtn.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        loanbtn.setForeground(new java.awt.Color(255, 255, 255));
        loanbtn.setText("CREAR PRESTAMO");
        loanbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanbtnActionPerformed(evt);
            }
        });
        add(loanbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 260, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void paybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paybtnActionPerformed
        if(BackEnd.Main.db.countLoans(user) != 0)
        {
            BackEnd.Main.db.setPay(BackEnd.Main.db.getLoan(user));
            setData(user);
        }
    }//GEN-LAST:event_paybtnActionPerformed

    private void typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeActionPerformed
        
        if(BackEnd.Main.db.countLoans(user) == 0)
        {
            if(type.getSelectedIndex() == 0)
            {
                SpinnerModel model = new SpinnerNumberModel(0,0,BackEnd.Main.db.getMoneyApo(user),0.1);
                monto.setModel(model);
            }else
            {
                monto.setValue(1);
                SpinnerModel model = new SpinnerNumberModel(1,1,10000,0.1);
                monto.setModel(model);
            }
        }
    }//GEN-LAST:event_typeActionPerformed

    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbtnActionPerformed
        setData(user);
    }//GEN-LAST:event_refreshbtnActionPerformed

    private void loanbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanbtnActionPerformed
        if(BackEnd.Main.db.countLoans(user) == 0)
        {
            String tipo = (type.getSelectedIndex() == 0)? "Automatico":"Fiduciario";
            BackEnd.Main.db.setLoan((double)monto.getValue(),(int)((double)periods.getValue()),tipo,user);
            setData(user);
        }
    }//GEN-LAST:event_loanbtnActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amount_paid;
    private javax.swing.JTextField creation_date;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton loanbtn;
    private javax.swing.JSpinner monto;
    private javax.swing.JTextField num_loan;
    private javax.swing.JButton paybtn;
    private javax.swing.JSpinner periods;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JComboBox<String> type;
    // End of variables declaration//GEN-END:variables

    private void setData(String user) {
        BackEnd.Loan l = BackEnd.Main.db.getLoan(user);
        if(l == null)
        {
            num_loan.setText("");
            periods.setEnabled(true);
            monto.setEnabled(true);
            periods.setValue(0);
            type.setSelectedIndex(0);
            creation_date.setText("");
            amount_paid.setText("");
            monto.setValue(0);
        }else
        {
            periods.setValue(l.getPeriods());
            periods.setEnabled(false);
            monto.setEnabled(false);
            amount_paid.setText(Double.toString(l.getSaldo()));
            creation_date.setText(l.getDate());
            num_loan.setText(l.getNumber());
            if(l.getTipo().equals("Automatico"))
            {
                type.setSelectedIndex(0);
            }else
            {
                type.setSelectedIndex(1);
            }
            monto.setValue(l.getMonto());
            type.setEnabled(false);
        }
    }
}
