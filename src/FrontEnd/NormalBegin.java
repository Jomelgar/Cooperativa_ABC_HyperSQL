/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package FrontEnd;

/**
 *
 * @author jomel
 */
public class NormalBegin extends javax.swing.JPanel {

    /**
     * Creates new form NormalBegin
     */
    public NormalBegin() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0,0,0,0));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(740, 440));

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(0,0,0,0)
        );
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("En Coorporativa ABC, nos especializamos en ofrecer soluciones estratégicas e innovadoras para [sector o industria].\n Desde nuestros inicios, hemos trabajado con un firme compromiso hacia la excelencia, la innovación y la satisfacción \nde  nuestros clientes. Nuestra misión es transformar desafíos en oportunidades,  proporcionando productos y servicios \nque optimizan procesos, potencian negocios y generan valor sostenible. Contamos con un equipo altamente \ncapacitado, apasionado por la tecnología, la eficiencia y el desarrollo empresarial. En Coorporativa ABC, creemos en la\ntransparencia, la colaboración y la mejora continua. Estos valores nos han permitido consolidarnos como un referente\n en el  mercado, estableciendo relaciones de confianza con nuestros clientes y socios estratégicos. Estamos aquí para \nayudarte a alcanzar el éxito. ¡Descubre cómo podemos impulsar tu negocio!");
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("Sitka Text", 0, 36)); // NOI18N
        jLabel1.setText("SOBRE NOSOTROS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(48, 48, 48)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
