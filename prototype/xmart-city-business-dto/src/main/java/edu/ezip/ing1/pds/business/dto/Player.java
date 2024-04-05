package edu.ezip.ing1.pds.business.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
@JsonRootName(value = "player")
public class Player {
    
    public Date contrat,naissance;
    public String prenom,nom,nationalite,position,pied;
    public int age,salaire,taille,numero,poids,id;
    public byte[] photo;
    public Player(String prenom,String nom,Date naissance,String nat,Date contrat,int salaire, String pos, int taille, int numero, int poids,String pied,int id,byte[] photo){
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
        this.photo = photo;
        //java.util.Date dateUtil = new java.util.Date(contrat.getTime());
        //SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy",Locale.FRENCH);
        //String str = format.format(dateUtil);
        LocalDate localD = LocalDate.now();
        long ageLong = ChronoUnit.YEARS.between(naissance.toLocalDate(),localD);
        int age = (int)ageLong;
        this.age = age;
        //int tailleContrat = str.length();
        //int startAnnee = tailleContrat - 4;
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

    public byte[] getPhoto(){
        return photo;
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

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("photo") 
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


    @JsonProperty("contrat") 
    public void setContrat(Date contrat) {
        this.contrat = contrat;
    }

    @JsonProperty("salaire")
    public void setSalaire(int salaire) {
        this.salaire = salaire;
    } 


    public String toString(){
        return("Le joueur " + nom + " " + prenom + " de " + age + " ans de nationalite " + nationalite + " evoluant au poste " + position + " et ayant un contrat allant jusque le " + contrat + " avec un salaire de " + salaire + "euros mesure " + taille + " cm et fait " + poids + " Kg et porte le numero " + numero );
    }

    public Date getContrat() {
        return contrat;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalaire() {
        return salaire;
    }



/*     public InfosJoueurs toInfosJoueurs(){
        InfosJoueurs j = new InfosJoueurs(nom,prenom,naissance,nationalite,contrat,salaire,position,taille,numero,poids,pied,id);
        return j;
    } */
}
