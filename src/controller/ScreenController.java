/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Xuyen
 */
import gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/*Tham khảo cách chuyển màn hình*/
public class ScreenController {
    private JPanel jpbView;
    private String kindSelected = "";
    List<Catalogue> listDanhMuc = null;

    public ScreenController(JPanel jpbView) {
        this.jpbView = jpbView;
    }

    public void setDashboard(JPanel jpnItem, JLabel jlbItem) throws IOException {
        JPanel node = new jPnChat();
        jpbView.removeAll();
        jpbView.setLayout(new BorderLayout());
        jpbView.add(node);
        jpbView.validate();
        jpbView.repaint();
    }
     public void setDashboardQL(JPanel jpnItem, JLabel jlbItem) throws IOException {
//        jpnItem.setBackground(new Color(175, 177, 179));
//        jlbItem.setBackground(new Color(175, 177, 179));
        JPanel node = new JPnQL();
        jpbView.removeAll();
        jpbView.setLayout(new BorderLayout());
        jpbView.add(node);
        jpbView.validate();
        jpbView.repaint();
    }

    public void setEvent(java.util.List<Catalogue> listDanhMuc) {
        this.listDanhMuc = listDanhMuc;
        for (Catalogue item : listDanhMuc) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;

        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "Chat":
                {
                    try {
                        node = new jPnChat();
                    } catch (IOException ex) {
                        Logger.getLogger(ScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "QL":
                    node = new JPnQL();
                    break;
                default:
                    break;
            }
            jpbView.removeAll();
            jpbView.setLayout(new BorderLayout());
            jpbView.add(node);
            jpbView.validate();
            jpbView.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
//            jpnItem.setBackground(new Color(175, 177, 179));
//            jlbItem.setBackground(new Color(175, 177, 179));
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            jpnItem.setBackground(new Color(175, 177, 179));
//            jlbItem.setBackground(new Color(175, 177, 179));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(238, 238, 238));
                jlbItem.setBackground(new Color(238, 238, 238));
            }
        }

    }
}

