/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import chat.Chat;
import controller.serverInfor;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Xuyen
 */
public class JPnQL extends javax.swing.JPanel {

    /**
     * Creates new form JPnQL
     */
    private List<serverInfor> infor;
    private List<server> listServer;

    public JPnQL() {
        initComponents();
        setServerList();
    }

    public void setServerList() {
        infor = Chat.listServer;
        listServer = Chat.Serverl;
        String[] str = new String[infor.size()];
        int i = 0;
        for (serverInfor in : infor) {
            String x = "";
            x += in.getName();
            x += ":";
            x += String.valueOf(in.getPort());
            str[i] = x;
            i++;
        }
        jListServer.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = str;

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        btnDelete.setEnabled(false);
        jTFport.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jListServer = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jTFport = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        jListServer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jListServer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListServerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jListServer);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Server List");

        jTFport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTFport.setToolTipText("port between 3000 and 9000");
        jTFport.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFportKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Port");

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTFport, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)))
                .addContainerGap(523, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTFport, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnDelete))))
                .addContainerGap(204, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            // TODO add your handling code here:
            deleteServer();
            setServerList();
        } catch (IOException ex) {
            Logger.getLogger(JPnQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jListServerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListServerMouseClicked
        // TODO add your handling code here:
        btnDelete.setEnabled(true);
    }//GEN-LAST:event_jListServerMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            // TODO add your handling code here:
            addServer();
        } catch (IOException ex) {
            Logger.getLogger(JPnQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void jTFportKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFportKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            try {
                addServer();
            } catch (IOException ex) {
                Logger.getLogger(JPnQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTFportKeyReleased
    public void deleteServer() throws IOException {
        int index = jListServer.getSelectedIndex();
        server s = this.listServer.get(index);
        serverInfor siInfor=this.infor.get(index);
        Chat.Serverl.remove(s);
        Chat.listServer.remove(siInfor);
        if (s.serverSocket != null) {
            s.serverSocket.close();
        }
        s.dispose();
    }

    public void addServer() throws IOException {
        String port = jTFport.getText();
        if (port.length() == 0) {
            JOptionPane.showMessageDialog(null, "Port is empty!");
            return;
        }
        boolean isNumeric = (port != null && port.matches("[0-9]+"));
        if (!isNumeric) {
            JOptionPane.showMessageDialog(null, "Port is numberic!");
            return;
        }
        int pp = Integer.parseInt(port);
        if (pp < 3000 || pp > 9000) {
            JOptionPane.showMessageDialog(null, "Port between 3000 and 9000!");
            return;
        }
        for (serverInfor s : Chat.listServer) {
            if (pp == s.getPort()) {
                JOptionPane.showMessageDialog(null, "Server with this port exists!");
                return;
            }
        }
        jTFport.setText("");
        server s = new server(pp);
        s.setVisible(true);
        setServerList();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jListServer;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTFport;
    // End of variables declaration//GEN-END:variables
}
