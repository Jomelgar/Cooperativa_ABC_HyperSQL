package FrontEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import java.util.Map;


public class AdminUser extends javax.swing.JPanel {

    DefaultTableModel tableModel;
    private DefaultListModel<String> listModel;
    
    public AdminUser() {
        initComponents();
        String[] columnNames = {"Referencia", "Ciudad", "Avenida", "Casa", "Departamento","Calle"};
        tableModel = new DefaultTableModel(columnNames, 1);
        direcciontable.setModel(tableModel);
        listModel = new DefaultListModel<>();
        this.telehonelist.setModel(listModel);
        clearData();
        setData(BackEnd.Main.logged.getUsername());
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addbtn = new javax.swing.JButton();
        delbtn = new javax.swing.JButton();
        searchbtn = new javax.swing.JButton();
        slastfield = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        telehonelist = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        direcciontable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        modbtn = new javax.swing.JButton();
        searchfield = new javax.swing.JTextField();
        codeempfield = new javax.swing.JTextField();
        userfield = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        rolebtn = new javax.swing.JCheckBox();
        passwordfield = new javax.swing.JTextField();
        fnamefield = new javax.swing.JTextField();
        snamefield = new javax.swing.JTextField();
        flastfield = new javax.swing.JTextField();
        smailfield = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        fmailfield = new javax.swing.JTextField();
        Cleanbtn = new javax.swing.JButton();
        removelistbtn = new javax.swing.JButton();
        addlistbtn = new javax.swing.JButton();
        phonefield = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0,0,0,0));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(740, 440));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addbtn.setBackground(new java.awt.Color(57, 45, 82));
        addbtn.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        addbtn.setForeground(new java.awt.Color(255, 255, 255));
        addbtn.setText("AÑADIR USUARIO");
        addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbtnActionPerformed(evt);
            }
        });
        add(addbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 160, 30));

        delbtn.setBackground(new java.awt.Color(57, 45, 82));
        delbtn.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        delbtn.setForeground(new java.awt.Color(255, 255, 255));
        delbtn.setText("ELIMINAR USUARIO");
        add(delbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 370, -1, 30));

        searchbtn.setBackground(new java.awt.Color(51, 0, 51));
        searchbtn.setForeground(new java.awt.Color(255, 255, 255));
        searchbtn.setText("SEARCH");
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });
        add(searchbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));
        add(slastfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 150, -1));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 380, 20));

        telehonelist.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(telehonelist);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, 170, 100));

        direcciontable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Referencia", "Ciudad", "Avenida", "Casa", "Departamento", "Calle"
            }
        ));
        jScrollPane2.setViewportView(direcciontable);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 370, 110));

        jLabel2.setText("Segundo Apellido:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, -1, -1));

        jLabel3.setText("Codigo de Empleado:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel4.setText("Telefonos:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, -1, -1));

        jLabel5.setText("Rol:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, -1));

        jLabel6.setText("Segundo Nombre:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, -1, -1));

        jLabel7.setText("Primer Apellido:");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jLabel8.setText("Primer Nombre:");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel9.setText("Nombre de Usuario:");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, -1, -1));

        jLabel10.setText("Dirección:");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, -1, -1));

        modbtn.setBackground(new java.awt.Color(57, 45, 82));
        modbtn.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        modbtn.setForeground(new java.awt.Color(255, 255, 255));
        modbtn.setText("MODIFICAR USUARIO");
        modbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modbtnActionPerformed(evt);
            }
        });
        add(modbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, 190, 30));
        add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 380, -1));

        codeempfield.setEditable(false);
        add(codeempfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 130, -1));
        add(userfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 130, -1));

        jLabel11.setText("Contraseña:");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        rolebtn.setText("¿Es Admin?");
        add(rolebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, -1, -1));

        passwordfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordfieldActionPerformed(evt);
            }
        });
        add(passwordfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 220, -1));
        add(fnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 130, -1));
        add(snamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 150, -1));
        add(flastfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 150, -1));
        add(smailfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 150, -1));

        jLabel12.setText("Correo Secundario:");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, -1, -1));

        jLabel13.setText("Correo Primario:");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));
        add(fmailfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 150, -1));

        Cleanbtn.setBackground(new java.awt.Color(57, 45, 82));
        Cleanbtn.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        Cleanbtn.setForeground(new java.awt.Color(255, 255, 255));
        Cleanbtn.setText("LIMPIAR");
        Cleanbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CleanbtnActionPerformed(evt);
            }
        });
        add(Cleanbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 100, 30));

        removelistbtn.setBackground(new java.awt.Color(0, 51, 51));
        removelistbtn.setForeground(new java.awt.Color(255, 255, 255));
        removelistbtn.setText("REMOVE NUMBER");
        removelistbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removelistbtnActionPerformed(evt);
            }
        });
        add(removelistbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 290, -1, -1));

        addlistbtn.setBackground(new java.awt.Color(0, 51, 51));
        addlistbtn.setForeground(new java.awt.Color(255, 255, 255));
        addlistbtn.setText("ADD NUMBER");
        addlistbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addlistbtnActionPerformed(evt);
            }
        });
        add(addlistbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 260, -1, -1));
        add(phonefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 180, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void passwordfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordfieldActionPerformed
        
    }//GEN-LAST:event_passwordfieldActionPerformed

    private void modbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modbtnActionPerformed
        LinkedHashMap<String, Object> map = getData();
        map.put("usuario_modificador", BackEnd.Main.db.getCodeEmp(BackEnd.Main.logged.getUsername()));
        BackEnd.Main.db.modifiedUser(map,rolebtn.isSelected());
        this.removeAll();
        this.repaint();
    }//GEN-LAST:event_modbtnActionPerformed

    private void addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbtnActionPerformed
        LinkedHashMap<String, Object> map = getData();
        map.put("usuario_creador", BackEnd.Main.db.getCodeEmp(BackEnd.Main.logged.getUsername()));
        ArrayList<String> phones = new ArrayList<>();
        for (Object obj: listModel.toArray()) {
            phones.add((String)obj);
        }
        
        BackEnd.Main.db.addUser(map,rolebtn.isSelected());
        clearData();
    }//GEN-LAST:event_addbtnActionPerformed

    private void CleanbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CleanbtnActionPerformed
        // TODO add your handling code here:
        clearData();
    }//GEN-LAST:event_CleanbtnActionPerformed

    private void removelistbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removelistbtnActionPerformed
        // TODO add your handling code here:
        listModel.remove(listModel.size() - 1);
    }//GEN-LAST:event_removelistbtnActionPerformed

    private void addlistbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addlistbtnActionPerformed
        // TODO add your handling code here:
        if(phonefield.getText().trim() != ""){
            listModel.addElement(phonefield.getText());
            telehonelist.setModel(listModel);
        }
    }//GEN-LAST:event_addlistbtnActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        // TODO add your handling code here:
        String user = searchfield.getText();
        if(BackEnd.Main.db.getCodeEmp(user) != null){
            setData(user);
        }
    }//GEN-LAST:event_searchbtnActionPerformed
    
    private void addList(ArrayList<String> data) {
        for (String item : data) {
            listModel.addElement(item);
        }
    }
    
    private LinkedHashMap<String, Object> getData()
    {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        
        map.put("id_usuario", (userfield.getText().trim() == "")? null: userfield.getText());
        map.put("contrasena", (passwordfield.getText().trim() == "")? null: passwordfield.getText());
        map.put("primer_nombre", (fnamefield.getText().trim() == "")? null: fnamefield.getText());
        map.put("segundo_nombre", (snamefield.getText().trim() == "")? null: snamefield.getText());
        map.put("primer_apellido", (fnamefield.getText().trim() == "")? null: flastfield.getText());
        map.put("segundo_apellido", (snamefield.getText().trim() == "")? null: slastfield.getText());
        
        String[] row = new String [tableModel.getColumnCount()];
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if(direcciontable.getValueAt(0, i) != null){
            row[i] = direcciontable.getValueAt(0, i).toString();
            }
            else
            {
                row[i] = null;
            }
        }
        
        map.put("referencia",row[0]);
        map.put("ciudad",  row[1]);
        map.put("avenida",row[2]);
        map.put("casa", row[3]);
        map.put("departamento", row[4]);
        map.put("calle", row[5]);
        
        map.put("correo_primario", (fmailfield.getText().trim() == "")? null: fmailfield.getText());
        map.put("correo_secundario", (smailfield.getText().trim() == "")? null: smailfield.getText());
        
        return map;
    }
    
    private void setData(String user)
    {
        LinkedHashMap<String, Object> map = BackEnd.Main.db.getUserById(user);
        codeempfield.setText(map.get("CODIGO_EMPLEADO") != null ? map.get("CODIGO_EMPLEADO").toString() : "");
        userfield.setText(map.get("ID_USUARIO") != null ? map.get("ID_USUARIO").toString() : "");
        passwordfield.setText(map.get("CONTRASENA") != null ? map.get("CONTRASENA").toString() : "");
        rolebtn.setSelected(map.get("ROL") != null ? (boolean) map.get("ROL") : false);
        fnamefield.setText(map.get("PRIMER_NOMBRE") != null ? map.get("PRIMER_NOMBRE").toString() : "");
        snamefield.setText(map.get("SEGUNDO_NOMBRE") != null ? map.get("SEGUNDO_NOMBRE").toString() : "");
        flastfield.setText(map.get("PRIMER_APELLIDO") != null ? map.get("PRIMER_APELLIDO").toString() : "");
        slastfield.setText(map.get("SEGUNDO_APELLIDO") != null ? map.get("SEGUNDO_APELLIDO").toString() : "");
        fmailfield.setText(map.get("CORREO_PRIMARIO") != null ? map.get("CORREO_PRIMARIO").toString() : "");
        smailfield.setText(map.get("CORREO_SECUNDARIO") != null ? map.get("CORREO_SECUNDARIO").toString() : "");
        tableModel.removeRow(0);
        tableModel.addRow(new Object[]{
            map.get("REFERENCIA") != null ? map.get("REFERENCIA").toString() : "",
            map.get("CIUDAD") != null ? map.get("CIUDAD").toString() : "",
            map.get("AVENIDA") != null ? map.get("AVENIDA").toString() : "",
            map.get("CASA") != null ? map.get("CASA").toString() : "",
            map.get("DEPARTAMENTO") != null ? map.get("DEPARTAMENTO").toString() : "",
            map.get("CALLE") != null ? map.get("CALLE").toString() : ""
        });
    }
    
    private void clearData() 
    {
        this.codeempfield.setText("");
        this.userfield.setText("");
        this.passwordfield.setText("");
        this.rolebtn.setSelected(false);
        this.fnamefield.setText("");
        this.snamefield.setText("");
        this.flastfield.setText("");
        this.slastfield.setText("");
        this.fmailfield.setText("");
        this.smailfield.setText("");
        listModel.clear();
        tableModel.removeRow(0);
        tableModel.addRow(new Object[]{"","","","","",""});
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cleanbtn;
    private javax.swing.JButton addbtn;
    private javax.swing.JButton addlistbtn;
    private javax.swing.JTextField codeempfield;
    private javax.swing.JButton delbtn;
    private javax.swing.JTable direcciontable;
    private javax.swing.JTextField flastfield;
    private javax.swing.JTextField fmailfield;
    private javax.swing.JTextField fnamefield;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton modbtn;
    private javax.swing.JTextField passwordfield;
    private javax.swing.JTextField phonefield;
    private javax.swing.JButton removelistbtn;
    private javax.swing.JCheckBox rolebtn;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextField searchfield;
    private javax.swing.JTextField slastfield;
    private javax.swing.JTextField smailfield;
    private javax.swing.JTextField snamefield;
    private javax.swing.JList<String> telehonelist;
    private javax.swing.JTextField userfield;
    // End of variables declaration//GEN-END:variables
}
