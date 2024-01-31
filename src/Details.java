import java.awt.BasicStroke;

import java.awt.Graphics2D;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Details extends JPanel {

    static final int WIDTH = Contenu.WIDTH_WITH_MENUHOME;
    int largeur;
    int hauteur;
    static JFrame f = Fenetre.f;
    Bouton details = new Bouton("Voir details");

    Details(InfosJoueurs j) {

        this.setVisible(true);
        this.setBackground(Contenu.bg);
        this.setLayout(null);
        this.add(new Menuhome());
        placer(j);
        this.add(details);
        details.setBounds((WIDTH - Contenu.BOUTON_WIDTH)/2, Contenu.HEIGHT - Contenu.BOUTON_HEIGHT*2, Contenu.BOUTON_WIDTH, Contenu.BOUTON_HEIGHT);

        f.add(this);

    }

    @Override
    protected void paintComponent(java.awt.Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        BasicStroke line = new BasicStroke(4.0f);
        g.setStroke(line);
        g.setColor(Contenu.yellow);
        g.drawLine(WIDTH/2, Contenu.HEIGHT/2, WIDTH/2, Contenu.HEIGHT*15/20);
        g.setStroke(new BasicStroke(1.0f));
    }

    private void placer(InfosJoueurs j) {

        int largeur_bis =0;
        JLabel posteTxt = new LabelTxt("Poste de Prédilection : ");
        JLabel piedTxt = new LabelTxt("Pied Fort : ");

        JLabel match = new LabelTxt("Match joués : ",14);
        JLabel buts = new LabelTxt("Buts : ",14);
        JLabel passeD = new LabelTxt("Passes décisives : ",14);
        JLabel cartonJ = new LabelTxt("Cartons jaunes : ",14);

        JLabel maillot = new LabelTxt("Numero maillot : "+ j.maillot.getText(),14);
        JLabel hommeMatch = new LabelTxt("Homme du match : ",14);
        JLabel equipeSemaine = new LabelTxt("Equipe de la semaine : ",14);
        JLabel cartonR = new LabelTxt("Cartons rouges : ",14);



        this.add(match);
        this.add(buts);
        this.add(passeD);
        this.add(cartonJ);

        this.add(maillot);
        this.add(hommeMatch);
        this.add(equipeSemaine);
        this.add(cartonR);

        //On recree des label afin de ne pas modifier le positionnement des labels correspondant aux descriptions des joueurs de la page ou il y a l'effectif 
        JLabel nom = new LabelTxt(j.nom.getText());
        JLabel prenom = new LabelTxt(j.prenom.getText());
        JLabel age = new LabelTxt(j.age.getText());
        JLabel pied = new LabelTxt(j.pied.getText(),40);
        JLabel poste = new LabelTxt(j.poste.getText(),40);


        this.add(InfosJoueurs.detailsImg);
        this.add(nom);
        this.add(prenom);
        this.add(age);
        this.add(pied);
        this.add(poste);



        this.add(posteTxt);
        this.add(piedTxt);


        largeur = InfosJoueurs.detailsImg.getIcon().getIconWidth() + Menuhome.WIDTH;
        hauteur = InfosJoueurs.detailsImg.getIcon().getIconHeight();
        int size = nom.getFont().getSize()*2;
        
        InfosJoueurs.detailsImg.setBounds(Menuhome.WIDTH, 0, largeur, hauteur);
        largeur += 10;
        hauteur = 0;
        nom.setBounds(largeur,  hauteur,150 ,size);
        hauteur += 2*size;
        prenom.setBounds(largeur, hauteur, 150, size);
        hauteur += 2*size;
        age.setBounds(largeur , hauteur, 150, size);
        
        hauteur += 2*size*2;
        largeur = Menuhome.WIDTH;
        posteTxt.setBounds(largeur, hauteur, 275, size);
        largeur += posteTxt.getWidth() +100;
        piedTxt.setBounds(largeur, hauteur, 150, size);
        hauteur += 2*size;
        pied.setBounds(largeur, hauteur, 150, size);
        largeur = Menuhome.WIDTH;
        poste.setBounds(largeur, hauteur, 150, size);


        largeur = WIDTH*3/10;
        hauteur += size*3;

        match.setBounds(largeur, hauteur, 175, size);
        largeur_bis = largeur + (WIDTH/2-match.getWidth())*2/3;
        maillot.setBounds(largeur_bis, hauteur, 205, size);

        hauteur += 2*size;
        buts.setBounds(largeur, hauteur, 175, size);
        hommeMatch.setBounds(largeur_bis, hauteur, 205, size);

        hauteur += 2*size;
        passeD.setBounds(largeur, hauteur, 175, size);
        equipeSemaine.setBounds(largeur_bis, hauteur, 205, size);

        hauteur += 2*size;
        cartonJ.setBounds(largeur, hauteur, 175, size);
        cartonR.setBounds(largeur_bis, hauteur, 205, size);
    }
    
}
