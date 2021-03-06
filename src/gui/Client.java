/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import chat.Chat;
import controller.Catalogue;
import controller.ScreenController;
import controller.serverInfor;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Xuyen
 */
public class Client extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
    public static String nameString = "";
    public static int vtServer = -1;
    //private ScreenController controller;
    public static JFrame jframeClient;

    public Client(String username) {
        initComponents();
        setUser();
        jLbServer.setVisible(false);
        jLbinforServer.setVisible(false);

        //List<Catalogue> listDanhMuc = new ArrayList<>();
        //listDanhMuc.add(new Catalogue("Chat", jPnBack, jLbBack));
        //listDanhMuc.add(new Catalogue("QL", jPnmanage, jLbmanage));
        //controller = new ScreenController(jPnView);
        //controller.setDashboard(jPnAccount, jLbAccount);
        //controller.setEvent(listDanhMuc);
        jframeClient = this;
        nameString = username;
        jLbName.setText(nameString);
    }

    public void setDashboard(JPanel jpbView) throws IOException {
        JPanel node = new jPnChat();
        jpbView.removeAll();
        jpbView.setLayout(new BorderLayout());
        jpbView.add(node);
        jpbView.validate();
        jpbView.repaint();
//        Thread t=new Thread (node);
//        t.start(); 
    }
     public void setDashboard2(JPanel jpbView){
        JPanel node = new JPnQL();
        jpbView.removeAll();
        jpbView.setLayout(new BorderLayout());
        jpbView.add(node);
        jpbView.validate();
        jpbView.repaint();
//        Thread t=new Thread (node);
//        t.start(); 
    }

    public void setUser() {

        String[] serverlist = new String[Chat.listServer.size()];
        String servernameString = "";
        int i = 0;
        if (Chat.listServer.size() != 0) {
            for (serverInfor s : Chat.listServer) {
                servernameString = "";
                servernameString = s.getName() + ":" + String.valueOf(s.getPort());
                serverlist[i] = servernameString;
                i++;
            }
        }

        CBServer.setModel(new javax.swing.DefaultComboBoxModel<>(serverlist));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLbName = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        CBServer = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnConnect = new javax.swing.JButton();
        jLbServer = new javax.swing.JLabel();
        jLbinforServer = new javax.swing.JLabel();
        jPnView = new javax.swing.JPanel();
        jLbNoServer = new javax.swing.JLabel();
        btnManage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLbName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbName.setText("jLabel1");

        btnLogout.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/logout.png"))); // NOI18N
        btnLogout.setText("Exit");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        CBServer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CBServer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Server:");

        btnConnect.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        jLbServer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbServer.setText("Th??ng tin Server:");

        jLbinforServer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbinforServer.setText("jLabel4");

        javax.swing.GroupLayout jPnViewLayout = new javax.swing.GroupLayout(jPnView);
        jPnView.setLayout(jPnViewLayout);
        jPnViewLayout.setHorizontalGroup(
            jPnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPnViewLayout.setVerticalGroup(
            jPnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
        );

        jLbNoServer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbNoServer.setText("NO SERVER");

        btnManage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnManage.setText("Manage Server List");
        btnManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CBServer, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(btnConnect)))
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLbNoServer)
                    .addComponent(jLbServer))
                .addGap(18, 18, 18)
                .addComponent(jLbinforServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 318, Short.MAX_VALUE)
                .addComponent(btnManage, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLbName)
                .addGap(108, 108, 108)
                .addComponent(btnLogout)
                .addGap(34, 34, 34))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLbNoServer, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                            .addComponent(btnManage, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLbName)
                            .addComponent(btnLogout)
                            .addComponent(CBServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLbServer)
                            .addComponent(jLbinforServer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConnect)
                        .addGap(31, 31, 31)))
                .addComponent(jPnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        //TODO add your handling code here:
        connectServer();
    }//GEN-LAST:event_btnConnectActionPerformed
    public void connectServer() {
        btnManage.setEnabled(false);
        vtServer = CBServer.getSelectedIndex();
        jLbServer.setVisible(true);
        jLbinforServer.setVisible(true);
        int vtS = CBServer.getSelectedIndex();
        serverInfor infor = Chat.listServer.get(vtS);
        jLbinforServer.setText(infor.getName() + " " + "hostname:" + infor.getHostname() + "  port:" + String.valueOf(infor.getPort()));
        jLbNoServer.setVisible(false);
        try {
            setDashboard(jPnView);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnConnect.setEnabled(false);
        CBServer.setEditable(false);
        btnLogout.setEnabled(false);
    }
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed

        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageActionPerformed
        // TODO add your handling code here:
        setDashboard2(jPnView);
    }//GEN-LAST:event_btnManageActionPerformed

    /**
     * @param args the command line arguments //
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                nw Client().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBServer;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnManage;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLbName;
    private javax.swing.JLabel jLbNoServer;
    private javax.swing.JLabel jLbServer;
    private javax.swing.JLabel jLbinforServer;
    private javax.swing.JPanel jPnView;
    // End of variables declaration//GEN-END:variables
}
