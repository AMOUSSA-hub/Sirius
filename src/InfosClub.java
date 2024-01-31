import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfosClub extends JPanel {


    static ImageIcon moissy = new ImageIcon("./images/Moissy.png");
    JLabel lab = new JLabel();
    
    JLabel equipe = new LabelTxt("EQUIPE",20);
    //JLabel effectif = new LabelTxt("Effectif",20);
    JLabel moissyFc = new LabelTxt("Moissy FC",20);

    InfosClub() {


        this.setVisible(true);
        this.setLayout(null);

        this.add(new Menuhome());
        this.setBackground(Contenu.bg);

        lab.setLayout(null);
        equipe.setLayout(null);
        this.add(lab);
        this.add(equipe);
        //this.add(effectif);
        this.add(moissyFc);
        //this.add(addPlayer);
        lab.setVisible(true);
        lab.setIcon(moissy);


        equipe.setBounds(Menuhome.WIDTH*2, 0, 250, equipe.getFont().getSize()*2);
        
        lab.setBounds(Menuhome.WIDTH*2 - moissy.getIconWidth()/4, equipe.getFont().getSize()*3, moissy.getIconWidth(),moissy.getIconHeight());
        moissyFc.setBounds(Menuhome.WIDTH*19/10 - 5, lab.getHeight() + equipe.getHeight()*3, 250, moissyFc.getFont().getSize()*2);
        equipeStats();

    }



    private void equipeStats() {

        int hauteur_bis = Contenu.HEIGHT*2/5;
        JLabel matchs = new LabelTxt("Matchs joués : ");
        JLabel record = new LabelTxt("V/N/D : ");
        JLabel pourcentWin = new LabelTxt("% de Victoires : ");
        JLabel classement = new LabelTxt("Classement Division : ");


        this.add(matchs);
        this.add(record);
        this.add(pourcentWin);
        this.add(classement);

        int size = matchs.getFont().getSize()*2;

        hauteur_bis += size;
        matchs.setBounds(Menuhome.WIDTH + 5, hauteur_bis, 300, size);

        hauteur_bis += size;
        record.setBounds(Menuhome.WIDTH + 5, hauteur_bis, 300, size);

        hauteur_bis += size;
        pourcentWin.setBounds(Menuhome.WIDTH + 5, hauteur_bis, 300, size);

        hauteur_bis += size;
        classement.setBounds(Menuhome.WIDTH + 5, hauteur_bis, 300, size);


    }




}
