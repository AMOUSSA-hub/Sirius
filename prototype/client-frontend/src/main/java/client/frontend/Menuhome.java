import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Menuhome extends JPanel implements ActionListener  {

    public static final int WIDTH_Bouton = 255;
    public static final int HEIGHT_Bouton = 100;
    int x = 0;
    Bouton accueil = new Bouton(WIDTH_Bouton, HEIGHT_Bouton, "Accueil");
    Bouton effectif = new Bouton(WIDTH_Bouton, HEIGHT_Bouton, "Effectif");
    Bouton formation = new Bouton(WIDTH_Bouton, HEIGHT_Bouton, "Formation");
    Bouton upcomingMatch = new Bouton(WIDTH_Bouton, HEIGHT_Bouton, "Match a venir");
    Bouton stats = new Bouton(WIDTH_Bouton, HEIGHT_Bouton, "Stats");
    Bouton calendrier = new Bouton(WIDTH_Bouton, HEIGHT_Bouton, "Calendrier");
    
    Menuhome() {
        this.setBounds(0, 0, Page.WIDTH,HEIGHT_Bouton);
        this.setVisible(true);
        this.setBackground(Page.bg);
        this.setLayout(null);
        x = addBouton(accueil);
        x = addBouton(effectif);
        x = addBouton(formation);
        x = addBouton(upcomingMatch);
        x = addBouton(stats);
        x = addBouton(calendrier);
    }
    

    private int addBouton(Bouton b){
        add(b).setBounds(x,0, b.width, HEIGHT_Bouton);
        return (x+b.width);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == effectif) {
            Fenetre.cardLayout.show(Fenetre.container, "Effectif");
        }
    }
    
    
}
