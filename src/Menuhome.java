import java.awt.CardLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Menuhome extends JScrollPane implements MouseListener {
 

    String[] yesNo_options = {"Oui","Non"};
    static JFrame f = Fenetre.f;

    static final int WIDTH = Contenu.WIDTH /9;
    static CardLayout cardLayout = Fenetre.cardLayout;

    BoutonMenu quitter = new BoutonMenu("Quitter");
    BoutonMenu effectif = new BoutonMenu("Effectif");
    BoutonMenu infosClub = new BoutonMenu("Infos Club");
    BoutonMenu detection = new BoutonMenu("Detection");

    static final int HEIGHT_BOUTON = 50; 
    int size = HEIGHT_BOUTON;
    Menuhome(){
        this.setBounds(0, 0, WIDTH, Contenu.HEIGHT);
        this.setVisible(true);
        this.setBackground(Contenu.bg);
        this.setLayout(null);

        this.add(quitter);
        this.add(infosClub); 
        this.add(effectif);
        this.add(detection);

        quitter.setBounds(0, 0, WIDTH, HEIGHT_BOUTON);
        infosClub.setBounds(0, size, WIDTH, HEIGHT_BOUTON);
        size += HEIGHT_BOUTON;
        effectif.setBounds(0, size, WIDTH, HEIGHT_BOUTON);
        size += HEIGHT_BOUTON;
        detection.setBounds(0, size, WIDTH, HEIGHT_BOUTON);
        

        quitter.addMouseListener(this);
        effectif.addMouseListener(this);
        detection.addMouseListener(this);
        infosClub.addMouseListener(this);
        


    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getSource() == quitter ) {
            if (confirmLeave()) {

                f.dispose();
            }
        }

        if (e.getSource() == infosClub) {
            cardLayout.show(Fenetre.container,"InfosClub");
        }

        if (e.getSource() == effectif) {
            cardLayout.show(Fenetre.container,"Effectif");
        }



        if (e.getSource() == detection) {
            cardLayout.show(Fenetre.container, "Detection");
        }
        f.repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }

    private boolean confirmLeave() {
        int x =  JOptionPane.showOptionDialog(f,"Etes vous sûr de vouloir quitter ? ","Fermer l'appli",0,0,null,yesNo_options,yesNo_options[0]);
        if (x==0) { return true;}
        else {return false;}
    }


}
