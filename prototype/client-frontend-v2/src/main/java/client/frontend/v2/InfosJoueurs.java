package client.frontend.v2;

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

import javax.swing.JOptionPane;
import java.util.*;


@JsonRootName(value = "student")
public class InfosJoueurs extends JScrollPane implements ActionListener {

    Date contrat,naissance;
    String prenom,nom,nationalite,position,pied,calculed_age;
    int age,salaire,taille,numero,poids,id;
    private String [] infos;

    Bouton btn = new Bouton(20,20,"Modif");
    

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
        this.calculed_age = " "+ChronoUnit.YEARS.between(naissance.toLocalDate(),LocalDate.now());


        String [] i  = {prenom+" "+nom, this.calculed_age, this.nationalite,this.contrat+"",this.salaire+"",this.position,this.taille+"",this.numero+"",this.poids+"",this.pied+""};
        this.infos = i;
       
        LocalDate localD = LocalDate.now();
        long ageLong = ChronoUnit.YEARS.between(naissance.toLocalDate(),localD);
        int age = (int)ageLong;
        this.age = age;

    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            try {
                String[] buttons = {"nom", "prenom", "naissance", "nationalite", "position", "pied", "taille", "poids","numero"};
                int choice = JOptionPane.showOptionDialog(null, "Choisir un attribut :", "Attributs",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                String attributToChange = buttons[choice];
                String userInput = JOptionPane.showInputDialog(null, "Par quoi voulez-vous le remplacer ?");
                Player player = this.toPlayer();
                Object newValue = userInput;
                int update = MainInsertClient.updatePlayer(player,attributToChange,newValue);
                if (update == 1) {
                    updateAttribut(choice,userInput);
                    //Iterator<InfosJoueurs> iterator = HomeFrame.effectif.listeInfosJoueurs.iterator();
/*                     while (iterator.hasNext()) {
                        InfosJoueurs joueur = iterator.next();
                        if(joueur.getId() == id) {
                            iterator.remove();
                            //System.out.println(Fenetre.effectif.listeInfosJoueurs.toString());

                        }
                    } */
                    //HomeFrame.effectif.listeInfosJoueurs.add(this);
                    //System.out.println(HomeFrame.effectif.listeInfosJoueurs.toString());
                    //Collections.sort(Fenetre.effectif.listeInfosJoueurs,new JoueursCompare(HomeFrame.effectif.attribut, HomeFrame.effectif.ascending_order));
                    //HomeFrame.effectif.ensembleJoueurs(HomeFrame.effectif.listeInfosJoueurs, HomeFrame.effectif.box);
                    //Fenetre.f.repaint();
                }
            }catch(Exception ex) {
                System.err.println(ex);
            }
        }
    }


    public void updateAttribut(int choice,String newValue){
        switch (choice) {
            case 0:
                nom = newValue;
                break;
            case 1:
                prenom = newValue;
                break;
            case 2:
                naissance = Date.valueOf(newValue);
                break;
            case 3:
                nationalite = newValue;
                break;
            case 4:
                position = newValue;
                break;
            case 5:
                pied = newValue;
                break;
            case 6:
                taille = Integer.valueOf(newValue);
                break;
            case 7:
                poids = Integer.valueOf(newValue);
                break;
            case 8:
                numero = Integer.valueOf(newValue);
                break;
            default:
                
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

    public int getId(){
        return id;
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

    public String[] getTabInfo(){
        return this.infos;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
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