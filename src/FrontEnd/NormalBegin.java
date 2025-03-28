/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package FrontEnd;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jomel
 */
public class NormalBegin extends javax.swing.JPanel {
    
    private BackEnd.Account acc;
    private String user;
    
    public NormalBegin() {
        initComponents();
        type.setEnabled(false);
        user = BackEnd.Main.logged.getUsername();
        acc = BackEnd.Main.db.getAccount(BackEnd.Main.db.getCodeEmp(user));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        afibtn = new javax.swing.JButton();
        divbtn = new javax.swing.JButton();
        cuentabtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        yearsp = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        type = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0,0,0,0));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(740, 440));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        afibtn.setBackground(new java.awt.Color(57, 45, 82));
        afibtn.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        afibtn.setForeground(new java.awt.Color(255, 255, 255));
        afibtn.setText("VER NUEVOS AFILIADOS");
        afibtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afibtnActionPerformed(evt);
            }
        });
        add(afibtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, 180, 40));

        divbtn.setBackground(new java.awt.Color(57, 45, 82));
        divbtn.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        divbtn.setForeground(new java.awt.Color(255, 255, 255));
        divbtn.setText("VER DIVIDENDOS");
        divbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divbtnActionPerformed(evt);
            }
        });
        add(divbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 160, 40));

        cuentabtn.setBackground(new java.awt.Color(57, 45, 82));
        cuentabtn.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        cuentabtn.setForeground(new java.awt.Color(255, 255, 255));
        cuentabtn.setText("VER ESTADO DE CUENTAS");
        cuentabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuentabtnActionPerformed(evt);
            }
        });
        add(cuentabtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 190, 40));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setEnabled(false);
        jScrollPane1.setViewportView(table);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 680, 200));

        yearsp.setModel(new javax.swing.SpinnerNumberModel(2025, 2025, null, 1));
        add(yearsp, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 130, -1));

        jLabel1.setText("Año:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, -1));

        type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cuenta" }));
        type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeActionPerformed(evt);
            }
        });
        add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 130, -1));

        jLabel3.setText("Cuenta:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, 20));
    }// </editor-fold>//GEN-END:initComponents

    private void cuentabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuentabtnActionPerformed
        // TODO add your handling code here:
        String col[] = {"Codigo Transaccion","Monto","Fecha","Comentario"};
        int anio = (Integer) yearsp.getValue();
        type.removeAll();
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        modelo.addElement(acc.numero_Apo());
        modelo.addElement(acc.numero_Afi());
        type.setModel(modelo);
        DefaultTableModel model = new DefaultTableModel(BackEnd.Main.db.getEstadoCuenta(anio,acc.numero_Apo()), col);
        table.setModel(model);
        type.setEnabled(true);
        
    }//GEN-LAST:event_cuentabtnActionPerformed

    private void divbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divbtnActionPerformed
        String col[] = {"Codigo Empleado","Fecha","Nombre","Saldo Aportaciones","Porcentaje", "Ganancias"};
        int anio = (Integer) yearsp.getValue();
        DefaultTableModel modelo = new DefaultTableModel(BackEnd.Main.db.getDivs(anio),col);
        table.setModel(modelo);
        type.setEnabled(false);
    }//GEN-LAST:event_divbtnActionPerformed

    private void afibtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afibtnActionPerformed
        String col[] = {"Codigo Empleado","Nombre","Fecha de Contratación","Inversión","Ahorro","Total"};
        int anio = (Integer) yearsp.getValue();
        DefaultTableModel modelo = new DefaultTableModel(BackEnd.Main.db.getNuevasAfiliaciones(anio), col);
        table.setModel(modelo);
        type.setEnabled(false);
    }//GEN-LAST:event_afibtnActionPerformed

    private void typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeActionPerformed
        if(type.isEnabled())
        {
            int anio = (Integer) yearsp.getValue();
            String col[] = {"Codigo Empleado","Fecha","Nombre","Saldo Aportaciones","Porcentaje", "Ganancias"};
            DefaultTableModel model = new DefaultTableModel(BackEnd.Main.db.getEstadoCuenta
            (anio,(type.getSelectedIndex() == 0)? acc.numero_Apo(): acc.numero_Afi()),col);
            table.setModel(model);
        }
    }//GEN-LAST:event_typeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton afibtn;
    private javax.swing.JButton cuentabtn;
    private javax.swing.JButton divbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JComboBox<String> type;
    private javax.swing.JSpinner yearsp;
    // End of variables declaration//GEN-END:variables
}
