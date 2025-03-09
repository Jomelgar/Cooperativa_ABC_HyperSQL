
package FrontEnd;
import Backend.*;

public class MainWindow extends javax.swing.JFrame {

    private boolean active = false;
    
    public MainWindow(javax.swing.JFrame frame) {
        initComponents();
        this.setSize(960,560);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        showpass.setOpaque(false);
        showpass.setContentAreaFilled(false);
        showpass.setBorderPainted(false);
        this.setLocationRelativeTo(frame);
        
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Username = new javax.swing.JTextField();
        showpass = new javax.swing.JButton();
        password = new javax.swing.JPasswordField();
        button = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(980, 528));
        setSize(new java.awt.Dimension(960, 540));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Username.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        Username.setBorder(null);
        getContentPane().add(Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 510, 40));

        showpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpassActionPerformed(evt);
            }
        });
        getContentPane().add(showpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 340, 70, 60));

        password.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        password.setBorder(null);
        getContentPane().add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 510, 40));

        button.setBackground(new java.awt.Color(37, 47, 56));
        button.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        button.setForeground(new java.awt.Color(255, 255, 255));
        button.setText("INICIAR SESIÓN");
        button.setBorderPainted(false);
        button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button.setOpaque(true);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        getContentPane().add(button, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 450, 200, 50));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sources/main_menu.png"))); // NOI18N
        Background.setPreferredSize(new java.awt.Dimension(959, 528));
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpassActionPerformed
            active = !active;
            
            if(active)
            {
                password.setEchoChar((char)0);
            }else
            {
                password.setEchoChar('\u2022');
            }
    }//GEN-LAST:event_showpassActionPerformed

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        BackEnd.User user = new BackEnd.User(Username.getText(),password.getText());
        if(BackEnd.Main.db.SearchUser(user))
        {
            BackEnd.Main.logged = user;
            StartupMenu main = new StartupMenu(this);
            main.show();
            this.dispose();
        }
    }//GEN-LAST:event_buttonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JTextField Username;
    private javax.swing.JButton button;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton showpass;
    // End of variables declaration//GEN-END:variables
}
