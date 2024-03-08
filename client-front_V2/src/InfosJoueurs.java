import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class InfosJoueurs extends JScrollPane {

    Date contrat,naissance;
   public String prenom,nom,nationalite,postion,pied,calculed_age;
    int age,salaire,taille,numero,poids;
    private String [] infosJoueur;


    Box box = new Box(BoxLayout.X_AXIS);
    Box imageJoueur = new Box(BoxLayout.Y_AXIS);
    Box identite = new Box(BoxLayout.Y_AXIS);
    Box ageBox = new Box(BoxLayout.Y_AXIS);
    Box nationaliteBox = new Box(BoxLayout.Y_AXIS);
    Box contratBox = new Box(BoxLayout.Y_AXIS);
    Box salaireBox = new Box(BoxLayout.Y_AXIS);
    Box positionBox = new Box(BoxLayout.Y_AXIS);
    Box tailleBox = new Box(BoxLayout.Y_AXIS);
    Box numeroBox = new Box(BoxLayout.Y_AXIS);
    Box poidsBox = new Box(BoxLayout.Y_AXIS); 

    public static final int NbAttributs = 10;
    public static final int WidthBox_Y = (Page.WIDTH / NbAttributs) -1; 
    public static final int HeightBox_Y = 75;
    InfosJoueurs() {
        addInfosBox("Inserer", "Photo", imageJoueur, box);
        addInfosBox("Nom", "Prenom", identite, box);
        addInfosBox("XX ans", null, ageBox, box);
        addInfosBox("FR", null, nationaliteBox, box);
        addInfosBox("30 Decembre ", "2026", contratBox, box);
        addInfosBox("4,00Mio.€", null, salaireBox, box);
        addInfosBox("MG", null, positionBox, box);
        addInfosBox("175", null, tailleBox, box);
        addInfosBox("12", null, numeroBox, box);
        addInfosBox("75", null, poidsBox, box);
        setViewportView(box);
        getViewport().setBackground(Page.bg);
        //box.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
    }

    InfosJoueurs(String prenom,String nom,Date naissance,String nat,Date contrat,int salaire, String pos, int taille, int numero, int poids,String pied){

        this.prenom = prenom;
        this.naissance = naissance;
        this.nationalite = nat;
        this.nom = nom.toUpperCase();
        this.contrat = contrat;
        this.taille = taille;
        this.numero = numero;
        this.poids = poids;
        this.salaire = salaire;
        this.postion = pos;
        this.pied = pied;
        this.calculed_age = " "+ChronoUnit.YEARS.between(naissance.toLocalDate(),LocalDate.now());

        String[] infos = {prenom+" "+nom, this.calculed_age, this.nationalite,this.contrat+"",this.salaire+"",this.postion,this.taille+"",this.numero+"",this.poids+""};
        this.infosJoueur = infos;
        java.util.Date dateUtil = new java.util.Date(contrat.getTime());
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy",Locale.FRENCH);
        String str = format.format(dateUtil);
        LocalDate localD = LocalDate.now();
        long ageLong = ChronoUnit.YEARS.between(naissance.toLocalDate(),localD);
        int age = (int)ageLong;
        this.age = age;
        int tailleContrat = str.length();
        int startAnnee = tailleContrat - 4;
        addInfosBox("Inserer", "Photo", imageJoueur, box);
        addInfosBox(nom.toUpperCase(), prenom, identite, box);
        addInfosBox(Integer.toString(age) + " ans", null, ageBox, box);
        addInfosBox(nat, null, nationaliteBox, box);
        addInfosBox(str.substring(0, startAnnee), str.substring(startAnnee), contratBox, box);
        addInfosBox(Integer.toString(salaire) +" €", null, salaireBox, box);
        addInfosBox(pos, null, positionBox, box);
        addInfosBox(Integer.toString(taille), null, tailleBox, box);
        addInfosBox(Integer.toString(numero), null, numeroBox, box);
        addInfosBox(Integer.toString(poids), null, poidsBox, box);
        setViewportView(box);
        getViewport().setBackground(Page.bg);
    }


    public static void addInfosBox(String txt,String txt2,Box box_Y,Box box_X ) {
        box_Y.add(Box.createGlue());
        box_Y.add(new LabelTxt(txt,13));
        box_Y.add(new LabelTxt(txt2,13));
        box_Y.add(Box.createGlue());
        box_Y.setPreferredSize(new Dimension(WidthBox_Y, HeightBox_Y));
        box_X.add(box_Y);
        box_Y.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        box_X.add(Box.createGlue());
    }

    public String toString(){
        return("Le joueur " + nom + " " + prenom + " de " + age + " ans de nationalite " + nationalite + " evoluant au poste " + postion + " et ayant un contrat allant jusque le " + contrat + " avec un salaire de " + salaire + "euros mesure " + taille + " cm et fait " + poids + " Kg et porte le numero " + numero );
    }

    public String[] getTabInfo(){
        return this.infosJoueur;
    }

}
