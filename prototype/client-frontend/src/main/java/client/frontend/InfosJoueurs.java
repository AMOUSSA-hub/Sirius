package client.frontend;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import edu.ezip.ing1.pds.business.dto.Player;
import edu.ezip.ing1.pds.client.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@JsonRootName(value = "student")
public class InfosJoueurs extends JScrollPane implements ActionListener {

    Date contrat,naissance;
    String prenom,nom,nationalite,position,pied;
    int age,salaire,taille,numero,poids,id;
    Bouton btn = new Bouton(20,20,"Modif");

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
    public InfosJoueurs() {
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


    

    public InfosJoueurs(String prenom,String nom,Date naissance,String nat,Date contrat,int salaire, String pos, int taille, int numero, int poids,String pied,int id){
        this.id = id;
        this.prenom = prenom;
        this.naissance = naissance;
        this.nationalite = nat;
        this.nom = nom.toUpperCase();
        this.contrat = contrat;
        this.taille = taille;
        this.numero = numero;
        this.poids = poids;
        this.salaire = salaire;
        this.position = pos;
        this.pied = pied;
        java.util.Date dateUtil = new java.util.Date(contrat.getTime());
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy",Locale.FRENCH);
        String str = format.format(dateUtil);
        LocalDate localD = LocalDate.now();
        long ageLong = ChronoUnit.YEARS.between(naissance.toLocalDate(),localD);
        int age = (int)ageLong;
        this.age = age;
        int tailleContrat = str.length();
        int startAnnee = tailleContrat - 4;
        imageJoueur.add(btn);
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
        btn.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
 /*            try {
                Player player = this.toPlayer();
                MainInsertClient.updatePlayer(player);
            }catch(Exception ex) {
                System.err.println(ex);
            } */
        }
    }


    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public Date getNaissance() {
        return naissance;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public String getPosition() {
        return position;
    }
    
    public String getPied() {
        return pied;
    }
    
    public int getTaille() {
        return taille;
    }
    
    public int getPoids() {
        return poids;
    }
    
    public String getNationalite(){
        return nationalite;
    }

    @JsonProperty("nom")
    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonProperty("prenom")
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @JsonProperty("naissance")
    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }

    @JsonProperty("numero")
    public void setNumero(int numero) {
        this.numero = numero;
    }

    @JsonProperty("position")
    public void setPoste(String position) {
        this.position = position;
    }

    @JsonProperty("pied")
    public void setPied(String pied) {
        this.pied = pied;
    }

    @JsonProperty("taille")
    public void setTaille(int taille) {
        this.taille = taille;
    }

    @JsonProperty("poids")
    public void setPoids(int poids) {
        this.poids = poids;
    }

    @JsonProperty("nationalite")
    public void setNationalite(String nationalite){
        this.nationalite = nationalite;
    }



    public String toString(){
        return("Le joueur " + nom + " " + prenom + " de " + age + " ans de nationalite " + nationalite + " evoluant au poste " + position + " et ayant un contrat allant jusque le " + contrat + " avec un salaire de " + salaire + "euros mesure " + taille + " cm et fait " + poids + " Kg et porte le numero " + numero );
    }

    public static InfosJoueurs playerToInfosJoueurs(Player p) {
        InfosJoueurs j = new InfosJoueurs(p.nom,p.prenom,p.naissance,p.nationalite,p.contrat,p.salaire,p.position,p.taille,p.numero,p.poids,p.pied,p.id);
        return j;
    }

    public Player toPlayer(){
        return (new Player(nom,prenom,naissance,nationalite,contrat,salaire,position,taille,numero,poids,pied,id)); 
    }
}
