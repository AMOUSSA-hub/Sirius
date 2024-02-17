import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;


public class Fenetre extends JFrame {


    static JFrame f = new JFrame();
    Page p = new Page();
    Effectif effectif = new Effectif();
    static CardLayout cardLayout = new CardLayout();
    static Container container;

    Fenetre() {
        
        
        //chargement.setStringPainted(true);
        f.add(p);
        f.pack();
        f.setTitle("Club Manager");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);

        f.setLocationRelativeTo(null);
        container= f.getContentPane();
        container.setLayout(cardLayout);
        container.add("Page",p);
        container.add("Effectif",effectif);
        cardLayout.show(container, "Page");
        //p.chargeBarre();
        p.enter();
    }
}