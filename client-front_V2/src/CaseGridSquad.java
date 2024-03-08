import java.awt.*;

import javax.swing.*;

public class CaseGridSquad extends JLabel {


     private JFrame fen;


    public CaseGridSquad(String i, JFrame fen){
        super(i);
            this.setOpaque(true);
            this.setPreferredSize(new Dimension(fen.getWidth()/11,50));
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            this.setBackground(Color.GRAY);
            this.setForeground(Color.white);
            this.setHorizontalAlignment(CENTER);
            this.fen =fen;
        }
    

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        this.setPreferredSize(new Dimension(fen.getWidth()/11,50));
        

    }
}
