
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JProgressBar;




public class Fenetre extends JFrame  {

    
    static CardLayout cardLayout = new CardLayout();
    static final int MAXIMUM = 100;
    static Container container;
    JProgressBar chargement = new JProgressBar(0, MAXIMUM);
    
    public static Boolean isHome = false;
    
    Contenu c = new Contenu();
    Players players = new Players();
    Detection detection = new Detection();
    InfosClub infosClub = new InfosClub();


    static JFrame f = new JFrame();
    Fenetre() {
        
        
        //chargement.setStringPainted(true);
        f.add(c);
        f.pack();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);

        f.setLocationRelativeTo(null);
        
        container= f.getContentPane();
        container.setLayout(cardLayout);

        container.add("Home",c);
        container.add("Detection",detection);
        container.add("Effectif", players);
        container.add("InfosClub", infosClub);

        f.setTitle("Club Manager");
        
        c.chargeBarre(); //On charge la barre
        c.enter(); //A la fin du chargement de la barre, on fait apparaitre le bouton entrer pour entrer dans l'appli 
        

        f.repaint();
        
    }


    
}

