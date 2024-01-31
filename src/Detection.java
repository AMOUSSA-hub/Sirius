import java.awt.BasicStroke;
import java.awt.CardLayout;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Detection extends JPanel {

    static CardLayout cardLayout = Fenetre.cardLayout;
    static JFrame f = Fenetre.f;

    Detection() {

        this.setLayout(null);
        this.setVisible(true);
        this.add(new Menuhome());
        this.setBackground(Contenu.bg);

        
        
        

    }
    @Override
    protected void paintComponent(java.awt.Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        BasicStroke line = new BasicStroke(10.0f);
        g.setStroke(line);
        g.setColor(Contenu.yellow);
        g.drawLine(0, 400, Contenu.WIDTH, 400);
        g.setStroke(new BasicStroke(1.0f));
    }

}
