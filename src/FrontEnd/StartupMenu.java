package FrontEnd;
import BackEnd.Main;
import java.awt.CardLayout;

public class StartupMenu extends javax.swing.JFrame {
    
    public StartupMenu(javax.swing.JFrame frame) {
        initComponents();
        exitbtn.setOpaque(false);
        exitbtn.setContentAreaFilled(false);
        exitbtn.setBorderPainted(false);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(960,560);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        DisplayStart();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accountbtn = new javax.swing.JButton();
        exitbtn = new javax.swing.JButton();
        userbtn = new javax.swing.JButton();
        startbtn = new javax.swing.JButton();
        loansbtn = new javax.swing.JButton();
        display = new javax.swing.JPanel();
        BackGround = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(960, 550));
        setPreferredSize(new java.awt.Dimension(960, 550));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accountbtn.setBorderPainted(false);
        accountbtn.setContentAreaFilled(false);
        accountbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountbtnActionPerformed(evt);
            }
        });
        getContentPane().add(accountbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 110, 80));

        exitbtn.setBackground(new java.awt.Color(0,0,0,0));
        exitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtnActionPerformed(evt);
            }
        });
        getContentPane().add(exitbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 450, 110, 80));

        userbtn.setBorderPainted(false);
        userbtn.setContentAreaFilled(false);
        userbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userbtnActionPerformed(evt);
            }
        });
        getContentPane().add(userbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 110, 70));

        startbtn.setBorderPainted(false);
        startbtn.setContentAreaFilled(false);
        startbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startbtnActionPerformed(evt);
            }
        });
        getContentPane().add(startbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 90, 110));

        loansbtn.setBorderPainted(false);
        loansbtn.setContentAreaFilled(false);
        loansbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loansbtnActionPerformed(evt);
            }
        });
        getContentPane().add(loansbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 100, 70));

        display.setBackground(new java.awt.Color(0,0,0,0)
        );
        getContentPane().add(display, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 740, 420));

        BackGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sources/startup_menu.png"))); // NOI18N
        BackGround.setText("jLabel1");
        getContentPane().add(BackGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
        MainWindow main = new MainWindow(this);
            main.show();
            this.dispose();
    }//GEN-LAST:event_exitbtnActionPerformed

    private void userbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userbtnActionPerformed
        display.removeAll();
        if(Main.logged.isAdmin())
        {
            display.add(new AdminUser());
        }else
        {
            display.add(new NormalUser());
        }
        display.revalidate();
        display.repaint();
         this.revalidate();
        this.repaint();
    }//GEN-LAST:event_userbtnActionPerformed

    private void startbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startbtnActionPerformed
        // TODO add your handling code here:
        display.removeAll();
        if(Main.logged.isAdmin())
        {
            display.add(new AdminBegin());
        }else
        {
            display.add(new NormalBegin());
        }
        display.revalidate();
        display.repaint();
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_startbtnActionPerformed

    private void accountbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountbtnActionPerformed
        display.removeAll();
        if(Main.logged.isAdmin())
        {
            display.add(new AccountAdmin());
        }else
        {
            display.add(new AccountNormal());
        }
        display.revalidate();
        display.repaint();
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_accountbtnActionPerformed

    private void loansbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loansbtnActionPerformed
        // TODO add your handling code here:
        display.removeAll();
        display.add(new Loan());
        display.revalidate();
        display.repaint();
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_loansbtnActionPerformed

    public void DisplayStart()
    {
        display.removeAll();
        if(Main.logged.isAdmin())
        {
            display.add(new AdminBegin());
        }else
        {
            display.add(new NormalBegin());
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackGround;
    private javax.swing.JButton accountbtn;
    private javax.swing.JPanel display;
    private javax.swing.JButton exitbtn;
    private javax.swing.JButton loansbtn;
    private javax.swing.JButton startbtn;
    private javax.swing.JButton userbtn;
    // End of variables declaration//GEN-END:variables
}
