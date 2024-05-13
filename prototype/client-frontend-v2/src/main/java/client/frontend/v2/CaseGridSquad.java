package client.frontend.v2;

import java.awt.*;

import javax.swing.*;

public class CaseGridSquad extends JLabel {


    private JFrame fen;
    //private int WIDTH = fen.getWidth()/12;
    private static final int HEIGHT = 75;

    public CaseGridSquad(String i, JFrame fen){
        super(i);
            this.setOpaque(true);
            this.setPreferredSize(new Dimension(fen.getWidth()/13,75));
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
           this.setBackground(Color.GRAY);
            this.setForeground(Color.white);
            this.setHorizontalAlignment(CENTER);
            this.fen =fen;
        }

        public CaseGridSquad(Image i, JFrame fen){
            super(new ImageIcon(i.getScaledInstance(fen.getWidth()/13, HEIGHT, Image.SCALE_DEFAULT)));
                this.setOpaque(true);
                this.setPreferredSize(new Dimension(fen.getWidth()/13,75));
                this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                this.setBackground(Color.GRAY);
                this.setForeground(Color.white);
                this.setHorizontalAlignment(CENTER);
                this.fen =fen;

        }
        public CaseGridSquad(JButton i, JFrame fen){
            JPanel panel = new JPanel();
            panel.setBackground(Color.GREEN);
            panel.add(i);
            add(panel);
            this.setOpaque(true);
                this.setPreferredSize(new Dimension(fen.getWidth()/13,75));
                this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                this.setBackground(Color.GRAY);
                this.setForeground(Color.white);
                this.setHorizontalAlignment(CENTER);
                this.fen =fen;

        }


}
