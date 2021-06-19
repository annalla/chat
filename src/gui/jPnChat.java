/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import chat.Chat;
import commoms.enums.Action;
import commoms.enums.Statuscode;
import commoms.request.GetIDRequest;
import commoms.request.GroupMessageRequest;
import commoms.request.InformationRequest;
import commoms.request.MessageRequest;
//import commoms.request.MessageRequest;
import commoms.request.Request;
import commoms.request.setUsernameRequest;
import commoms.response.CloseServerResponse;
import commoms.response.GetIDResponse;
import commoms.response.MessageResponse;
//import commoms.response.MessageResponse;
import commoms.response.UserOnlineResponse;
import controller.serverInfor;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.Closeable;
import org.apache.commons.lang3.ObjectUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Xuyen
 */
public class jPnChat extends javax.swing.JPanel implements Runnable {

    /**
     * Creates new form jPnChat
     */
    public jPnChat() throws IOException {
        initComponents();
        nameUser = Client.nameString;
        choice = Action.SETUP;
        tabChat1.setVisible(false);
        tabChat2.setVisible(false);
        jTFGroup.setEnabled(false);
        btnClear.setEnabled(false);
        Thread t = new Thread(this);
        t.start();
    }

    public void setUp() throws IOException {
        String ip = "localhost";
        serverInfor s = Chat.listServer.get(Client.vtServer);
        int port = s.getPort();
        clientSocket = new Socket(ip, port);
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        setusername();
        getid();
        ResponseProcess res = new ResponseProcess();
        res.start();
        userOnlines = new ArrayList<>();
        idOnlines = new ArrayList<>();
        jframe = Client.jframeClient;

    }

    public void setusername() throws IOException {
        setUsernameRequest dmRequest = new setUsernameRequest(Action.SET_USERNAME, nameUser);
        sendRequest(dmRequest);
    }

    public void getid() throws IOException {
        sendRequest(GetIDRequest.builder().action(Action.GET_ID).build());
    }
    public boolean equalCustom(List<String> a, List<String> b){
        if(a.size()!=b.size()){
            return false;
        }
        for(String aaString:a){
            if(b.indexOf(aaString)==-1){
                return false;
            }
        }
        return true;
    }
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    List<String> userOnlines;
    List<String> idOnlines;
    private Action choice = Action.SETUP;
    private JFrame jframe;
    private String nameUser;
    private String idUser;
    private boolean tab1 = false;
    private boolean tab2 = false;
    private List<String> idtab1 = new ArrayList<>();
    private List<String> usertab1;
    private List<String> usertab2;
    private List<String> idtab2 = new ArrayList<>();
    private int max = -1;
    private int tabchoose = -1;
    private boolean enterIsSend = true;
    String message;
    private List<List<String>> listGroupId = new ArrayList<>();
    private List<String> listGroupName = new ArrayList<>();

    private void sendRequest(Request req) throws IOException {
        if (req == null) {
            return;
        }
        this.out.writeObject(req);
        this.out.flush();
    }

    private void close() throws IOException {
        if (this.in != null) {
            this.in.close();
        }
        if (this.out != null) {
            this.out.close();
        }
        if (this.clientSocket != null) {
            this.clientSocket.close();
        }
    }

    private void getUserOnlines() throws IOException {
        sendRequest(InformationRequest.builder().action(Action.GET_USERS_ONLINE).build());
    }

    public void setUseronline() {
        int n = userOnlines.size();
        if (n == 0) {
            jListUserforG.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = new String[0];

                public int getSize() {
                    return strings.length;
                }

                public String getElementAt(int i) {
                    return strings[i];
                }
            });
            jListUser.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = new String[0];

                public int getSize() {
                    return strings.length;
                }

                public String getElementAt(int i) {
                    return strings[i];
                }
            });
        }
        String[] stringUser = new String[n];
        int i = 0;
        for (String s : userOnlines) {
            stringUser[i] = s;
            i++;
        }
        jListUser.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = stringUser;

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        jListUserforG.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = stringUser;

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
    }

    @Override
    public void run() {
        try {

            switch (choice) {
                case SETUP:
                    setUp();
                    return;
                case DISCONNECT: {
                    sendRequest(InformationRequest.builder().action(Action.DISCONNECT).build());
                    close();
                    this.removeAll();
                    this.updateUI();
                    jframe.dispose();
                    new Client(nameUser).setVisible(true);
                    return;
                }
                case CHAT_ALL: {
                    try {
                        if (tabchoose == 1) {
                            List<String> idtab = new ArrayList<>(this.idtab1);
                            idtab.add(idUser);
                            sendRequest(GroupMessageRequest.builder()
                                    .action(Action.CHAT_ALL)
                                    .message(message)
                                    .uids(this.idtab1)
                                    .groupList(idtab)
                                    .build());
                        } else {
                            List<String> idtab = new ArrayList<>(this.idtab2);
                            idtab.add(idUser);
                            sendRequest(GroupMessageRequest.builder()
                                    .action(Action.CHAT_ALL)
                                    .message(message)
                                    .uids(this.idtab2)
                                    .groupList(idtab)
                                    .build());
                        }
                        return;

                    } catch (IOException ex) {
                        Logger.getLogger(jPnTabChat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
//Tham khảo va Custom

    private class ResponseProcess extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    Object object = in.readObject();
                    if (ObjectUtils.isEmpty(object)) {
                        continue;
                    }
                    if (object instanceof GetIDResponse) {
                        GetIDResponse iDResponse = (GetIDResponse) object;
                        idUser = iDResponse.getMyid();
                    }
                    if (object instanceof MessageResponse) {
                        MessageResponse messageResponse = (MessageResponse) object;
                        int vt = idOnlines.indexOf(messageResponse.getSenderId());
                        String nameSender = userOnlines.get(vt);
                        setMessagetoHistory(messageResponse.getMessage(), messageResponse.getSenderId(), messageResponse.getGroup());

                    }
                    if (object instanceof UserOnlineResponse) {
                        UserOnlineResponse userOnlineResponse = (UserOnlineResponse) object;
                        if (Statuscode.OK.equals(userOnlineResponse.getStatusCode())) {
                            userOnlines = userOnlineResponse.getUsernames();
                            idOnlines = userOnlineResponse.getIdsList();
                            setUseronline();
                        } else {
                            System.out.println("Request failed!!!");
                        }
                    }

                    if (object instanceof CloseServerResponse) {
                        CloseServerResponse messageResponse = (CloseServerResponse) object;
                        CloseAll();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setMessagetoHistory(String mess, String senderId, List<String> group) {
        group.remove(idUser);
        if (group.size() > 1) {
            if (listGroupId.indexOf(group) == -1) {
                listGroupId.add(new ArrayList<>(group));
                String nameGroup = "";
                for (String s : group) {
                    nameGroup += userOnlines.get(idOnlines.indexOf(s));
                    nameGroup += ",";
                }
                nameGroup = nameGroup.substring(0, nameGroup.length() - 1);
                listGroupName.add(nameGroup);
                setGrouptoList();
            }
        }
        String nameSender = userOnlines.get(idOnlines.indexOf(senderId));
        if (tab1 && equalCustom(group,idtab1)) {
            jTAhistory1.append(nameSender + " : " + mess + "");
            return;
        }
        if (tab2 && equalCustom(group,idtab2)) {
            jTAhistory2.append(nameSender + " : " + mess + "");
            return;
        }
        List<String> name = new ArrayList<>();
        for (String id : group) {
            int vt = idOnlines.indexOf(id);
            name.add(userOnlines.get(vt));
        }
        opentabChat(group, name);
        if (max == 1) {
            jTAhistory1.append(nameSender + " : " + mess + "");
            
            return;
        }
        jTAhistory2.append(nameSender + " : " + mess + "");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        btnDisconnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListUser = new javax.swing.JList<>();
        tabChat2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        camera2 = new javax.swing.JLabel();
        jPnhistory2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTAhistory2 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTAchat2 = new javax.swing.JTextArea();
        send2 = new javax.swing.JLabel();
        mic2 = new javax.swing.JLabel();
        neutral1 = new javax.swing.JLabel();
        sad2 = new javax.swing.JLabel();
        kiss2 = new javax.swing.JLabel();
        smile2 = new javax.swing.JLabel();
        happy2 = new javax.swing.JLabel();
        attach1 = new javax.swing.JLabel();
        jLbName2 = new javax.swing.JLabel();
        tabChat1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTAchat1 = new javax.swing.JTextArea();
        send1 = new javax.swing.JLabel();
        mic1 = new javax.swing.JLabel();
        attach2 = new javax.swing.JLabel();
        happy1 = new javax.swing.JLabel();
        kiss1 = new javax.swing.JLabel();
        smile1 = new javax.swing.JLabel();
        neutral2 = new javax.swing.JLabel();
        sad1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        camera1 = new javax.swing.JLabel();
        jPnhistory1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTAhistory1 = new javax.swing.JTextArea();
        jLbName1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListGroupChat = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListUserforG = new javax.swing.JList<>();
        btnCreateGroup = new javax.swing.JButton();
        jTFGroup = new javax.swing.JTextField();
        otpEnter = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        btnDisconnect.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectActionPerformed(evt);
            }
        });

        jListUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jListUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jListUser);

        tabChat2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabChat2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabChat2.setPreferredSize(new java.awt.Dimension(432, 481));

        camera2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/video.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(camera2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(camera2))
        );

        jPnhistory2.setPreferredSize(new java.awt.Dimension(4, 0));

        jTAhistory2.setColumns(20);
        jTAhistory2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTAhistory2.setRows(5);
        jScrollPane7.setViewportView(jTAhistory2);

        javax.swing.GroupLayout jPnhistory2Layout = new javax.swing.GroupLayout(jPnhistory2);
        jPnhistory2.setLayout(jPnhistory2Layout);
        jPnhistory2Layout.setHorizontalGroup(
            jPnhistory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
        );
        jPnhistory2Layout.setVerticalGroup(
            jPnhistory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
        );

        jTAchat2.setColumns(20);
        jTAchat2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTAchat2.setRows(5);
        jTAchat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTAchat2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAchat2KeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(jTAchat2);

        send2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/send.png"))); // NOI18N
        send2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                send2MouseClicked(evt);
            }
        });

        mic2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/microphone.png"))); // NOI18N

        neutral1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-neutral-outline.png"))); // NOI18N

        sad2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-sad-outline.png"))); // NOI18N

        kiss2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-kiss-outline.png"))); // NOI18N

        smile2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-happy-outline.png"))); // NOI18N

        happy2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-excited-outline.png"))); // NOI18N

        attach1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/attachment.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(happy2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kiss2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(attach1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mic2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(smile2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(neutral1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sad2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(send2)
                        .addGap(27, 27, 27))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(attach1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(happy2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mic2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(send2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(neutral1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sad2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(smile2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(kiss2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jLbName2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbName2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbName2.setText("jLabel3");

        javax.swing.GroupLayout tabChat2Layout = new javax.swing.GroupLayout(tabChat2);
        tabChat2.setLayout(tabChat2Layout);
        tabChat2Layout.setHorizontalGroup(
            tabChat2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabChat2Layout.createSequentialGroup()
                .addComponent(jLbName2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPnhistory2, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
            .addGroup(tabChat2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        tabChat2Layout.setVerticalGroup(
            tabChat2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabChat2Layout.createSequentialGroup()
                .addGroup(tabChat2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbName2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPnhistory2, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabChat1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabChat1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabChat1.setPreferredSize(new java.awt.Dimension(432, 434));

        jPanel4.setPreferredSize(new java.awt.Dimension(428, 64));

        jTAchat1.setColumns(20);
        jTAchat1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTAchat1.setRows(5);
        jTAchat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTAchat1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAchat1KeyTyped(evt);
            }
        });
        jScrollPane5.setViewportView(jTAchat1);

        send1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/send.png"))); // NOI18N
        send1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                send1MouseClicked(evt);
            }
        });

        mic1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/microphone.png"))); // NOI18N

        attach2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/attachment.png"))); // NOI18N

        happy1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-excited-outline.png"))); // NOI18N

        kiss1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-kiss-outline.png"))); // NOI18N

        smile1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-happy-outline.png"))); // NOI18N

        neutral2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-neutral-outline.png"))); // NOI18N

        sad1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/emoticon-sad-outline.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(attach2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mic1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(send1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(happy1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kiss1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(smile1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(neutral2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sad1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mic1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(send1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(attach2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(happy1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(kiss1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(smile1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(neutral2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sad1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        camera1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/video.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(camera1)
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(camera1))
        );

        jTAhistory1.setColumns(20);
        jTAhistory1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTAhistory1.setRows(5);
        jScrollPane6.setViewportView(jTAhistory1);

        javax.swing.GroupLayout jPnhistory1Layout = new javax.swing.GroupLayout(jPnhistory1);
        jPnhistory1.setLayout(jPnhistory1Layout);
        jPnhistory1Layout.setHorizontalGroup(
            jPnhistory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
        );
        jPnhistory1Layout.setVerticalGroup(
            jPnhistory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
        );

        jLbName1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbName1.setText("jLabel3");

        javax.swing.GroupLayout tabChat1Layout = new javax.swing.GroupLayout(tabChat1);
        tabChat1.setLayout(tabChat1Layout);
        tabChat1Layout.setHorizontalGroup(
            tabChat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tabChat1Layout.createSequentialGroup()
                .addComponent(jLbName1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPnhistory1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabChat1Layout.setVerticalGroup(
            tabChat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabChat1Layout.createSequentialGroup()
                .addGroup(tabChat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPnhistory1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Online User:");

        jListGroupChat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jListGroupChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListGroupChatMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jListGroupChat);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Group List");

        jListUserforG.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jListUserforG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListUserforGMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jListUserforG);

        btnCreateGroup.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCreateGroup.setText("Create Group");
        btnCreateGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateGroupActionPerformed(evt);
            }
        });

        jTFGroup.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTFGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFGroupActionPerformed(evt);
            }
        });

        otpEnter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        otpEnter.setText("Option Enter");
        otpEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otpEnterActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnDisconnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(otpEnter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(159, 159, 159)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addComponent(tabChat1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTFGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCreateGroup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tabChat2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDisconnect)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(otpEnter))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTFGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCreateGroup)
                                .addComponent(btnClear)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabChat1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                    .addComponent(tabChat2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTFGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFGroupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFGroupActionPerformed

    private void jListUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListUserMouseClicked
        int vt = jListUser.getSelectedIndex();
        List<String> idChatList = new ArrayList<>();
        idChatList.add(idOnlines.get(vt));
        List<String> nameChatList = new ArrayList<>();
        nameChatList.add(userOnlines.get(vt));
        opentabChat(idChatList, nameChatList);
    }//GEN-LAST:event_jListUserMouseClicked
    public void opentabChat(List<String> idChatList, List<String> nameChatList) {
        if (equalCustom(idChatList,idtab1) || equalCustom(idChatList,idtab2)) {
            return;
        }
        if (!tab1 || max == 2) {
            max = 1;
            tab1 = true;
            idtab1 = idChatList;
            usertab1 = nameChatList;
            setNameJlb(jLbName1, usertab1);
            jTAchat1.setText("");
            tabChat1.setVisible(tab1);
            return;
        }
        max = 2;
        tab2 = true;
        idtab2 = idChatList;
        usertab2 = nameChatList;
        setNameJlb(jLbName2, usertab2);
        jTAchat2.setText("");
        tabChat2.setVisible(tab2);
    }

    public void setNameJlb(JLabel jLbName, List<String> name) {
        if (name.size() == 1) {
            for (String s : name) {
                jLbName.setText(s);
            }
        } else {
            String nameSender = "";
            for (String s : name) {
                nameSender += s;
                nameSender += ",";
            }
            nameSender = nameSender.substring(0, nameSender.length() - 1);
            jLbName.setText(nameSender);
        }
    }

    public void choiceEnter() {
        int n = JOptionPane.showConfirmDialog(
                null, "ENTER IS SEND IF YES, ENTER IS MORE LINES IF NO?",
                "ENTER OPTION",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            enterIsSend = true;
        } else if (n == JOptionPane.NO_OPTION) {
            enterIsSend = false;
        }
    }
    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconnectActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            CloseAll();
        } catch (IOException ex) {
            Logger.getLogger(jPnChat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnDisconnectActionPerformed

    private void jTAchat1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAchat1KeyReleased
        // TODO add your handling code here:
        if (enterIsSend) {
            if (evt.getKeyCode() == evt.VK_ENTER) {
                tabchoose = 1;
                sendMessage(tabchoose, jTAchat1, jTAhistory1);
            }
        }

    }//GEN-LAST:event_jTAchat1KeyReleased

    private void jTAchat1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAchat1KeyTyped
        // TODO add your handling code here:


    }//GEN-LAST:event_jTAchat1KeyTyped

    private void jTAchat2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAchat2KeyReleased
        // TODO add your handling code here:
        if (enterIsSend) {
            if (evt.getKeyCode() == evt.VK_ENTER) {
                tabchoose = 2;
                sendMessage(tabchoose, jTAchat2, jTAhistory2);
            }
        }

    }//GEN-LAST:event_jTAchat2KeyReleased

    private void send1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_send1MouseClicked
        // TODO add your handling code here:
        tabchoose = 1;
        sendMessage(tabchoose, jTAchat1, jTAhistory1);
    }//GEN-LAST:event_send1MouseClicked

    private void send2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_send2MouseClicked
        // TODO add your handling code here:
        tabchoose = 2;
        sendMessage(tabchoose, jTAchat2, jTAhistory2);
    }//GEN-LAST:event_send2MouseClicked

    private void jTAchat2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAchat2KeyTyped
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER && evt.getKeyCode() == evt.VK_SHIFT) {
            System.out.println("ok");

        }
    }//GEN-LAST:event_jTAchat2KeyTyped

    private void otpEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otpEnterActionPerformed
        // TODO add your handling code here:
        choiceEnter();
    }//GEN-LAST:event_otpEnterActionPerformed

    private void btnCreateGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateGroupActionPerformed
        // TODO add your handling code here:
        createGroup();
    }//GEN-LAST:event_btnCreateGroupActionPerformed

    private void jListUserforGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListUserforGMouseClicked
        // TODO add your handling code here:
        setGroup();
        btnClear.setEnabled(true);
    }//GEN-LAST:event_jListUserforGMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        jTFGroup.setText("");
        idfor1group.clear();
        idfor1group = new ArrayList<>();
    }//GEN-LAST:event_btnClearActionPerformed

    private void jListGroupChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListGroupChatMouseClicked
        // TODO add your handling code here:
        openGroupChatNew();
    }//GEN-LAST:event_jListGroupChatMouseClicked
    public void openGroupChatNew() {
        int index = jListGroupChat.getSelectedIndex();
        String nameString = jListGroupChat.getSelectedValue();
        List<String> nList = new ArrayList<>();
        String[] ssStrings = nameString.split(",");
        for (String name : ssStrings) {
            nList.add(name);
        }
        opentabChat(this.listGroupId.get(index), nList);
    }

    public void createGroup() {
        String nameString = jTFGroup.getText();
        if (nameString.length() == 0) {
            JOptionPane.showMessageDialog(null, "Group Member is empty!");
            return;
        }
        if (idfor1group.size() == 1) {
            JOptionPane.showMessageDialog(null, "At least 2 members!");
            return;
        }
        nameString = nameString.substring(0, nameString.length() - 1);
        listGroupId.add(new ArrayList<>(idfor1group));
        listGroupName.add(nameString);
        setGrouptoList();
        jTFGroup.setText("");
        List<String> nList = new ArrayList<>();
        String[] ssStrings = nameString.split(",");
        for (String name : ssStrings) {
            nList.add(name);
        }
        opentabChat(new ArrayList<String>(idfor1group), nList);
        idfor1group.clear();
        idfor1group = new ArrayList<>();
    }

    public void setGrouptoList() {
        String[] stringUser = new String[listGroupName.size()];
        int i = 0;
        for (String s : listGroupName) {
            stringUser[i] = s;
            i++;
        }
        jListGroupChat.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = stringUser;

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
    }
    private List<String> idfor1group = new ArrayList<>();

    public void setGroup() {
        if (jTFGroup.getText().length() == 0) {
            idfor1group.clear();
            idfor1group = new ArrayList<>();
        }
        int index = jListUserforG.getSelectedIndex();
        if (idfor1group.size() != 0 && idfor1group.indexOf(idOnlines.get(index)) != -1) {
            return;
        }
        idfor1group.add(idOnlines.get(index));
        String name = jListUserforG.getSelectedValue();
        jTFGroup.setText(jTFGroup.getText() + name + ",");
    }

    public void sendMessage(int tabchoose, JTextArea ta, JTextArea history) {
        message = ta.getText();
        if (message.length() == 0) {
            JOptionPane.showMessageDialog(null, "The message is empty!");
            return;
        }
        history.append("me : " + message + "");
        ta.setText("");
        history.requestFocus();
        ta.requestFocus();
        choice = Action.CHAT_ALL;
        Thread t = new Thread(this);
        t.start();
    }

    public void CloseAll() throws IOException {
        choice = Action.DISCONNECT;
        Thread t = new Thread(this);
        t.start();

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attach1;
    private javax.swing.JLabel attach2;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreateGroup;
    private javax.swing.JButton btnDisconnect;
    private javax.swing.JLabel camera1;
    private javax.swing.JLabel camera2;
    private javax.swing.JLabel happy1;
    private javax.swing.JLabel happy2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLbName1;
    private javax.swing.JLabel jLbName2;
    private javax.swing.JList<String> jListGroupChat;
    private javax.swing.JList<String> jListUser;
    private javax.swing.JList<String> jListUserforG;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPnhistory1;
    private javax.swing.JPanel jPnhistory2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextArea jTAchat1;
    private javax.swing.JTextArea jTAchat2;
    private javax.swing.JTextArea jTAhistory1;
    private javax.swing.JTextArea jTAhistory2;
    private javax.swing.JTextField jTFGroup;
    private javax.swing.JLabel kiss1;
    private javax.swing.JLabel kiss2;
    private javax.swing.JLabel mic1;
    private javax.swing.JLabel mic2;
    private javax.swing.JLabel neutral1;
    private javax.swing.JLabel neutral2;
    private javax.swing.JButton otpEnter;
    private javax.swing.JLabel sad1;
    private javax.swing.JLabel sad2;
    private javax.swing.JLabel send1;
    private javax.swing.JLabel send2;
    private javax.swing.JLabel smile1;
    private javax.swing.JLabel smile2;
    private javax.swing.JPanel tabChat1;
    private javax.swing.JPanel tabChat2;
    // End of variables declaration//GEN-END:variables
}
