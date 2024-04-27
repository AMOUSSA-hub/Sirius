package client.frontend.v2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.Image;


@JsonRootName(value = "student")
public class InfosJoueurs implements ActionListener {

    Date contrat,naissance;
    String prenom,nom,nationalite,position,pied,calculed_age;
    int age,salaire,taille,numero,poids,id;
    private String [] infos;
    byte[] photo;
    Bouton btn = new Bouton(200,75,"Modif");
    //Bouton del = new Bouton(200,75,"Supprimer");


    public InfosJoueurs(String prenom,String nom,Date naissance,String nat,Date contrat,int salaire, String pos, int taille, int numero, int poids,String pied,int id,byte[] photo){
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
        this.calculed_age = " "+ChronoUnit.YEARS.between(naissance.toLocalDate(),LocalDate.now());
        //System.out.println("UIEHIUEAHIUDHZIUDHDIUAZH : " + this.poids);

        String [] i  = {prenom+" "+nom, this.calculed_age, this.nationalite,this.contrat+"",this.salaire+"",this.position,this.taille+"",this.numero+"",this.poids+"",this.pied+""};
        this.infos = i;
       
        LocalDate localD = LocalDate.now();
        long ageLong = ChronoUnit.YEARS.between(naissance.toLocalDate(),localD);
        int age = (int)ageLong;
        this.age = age;
        btn.addActionListener(this);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        //UpdatePlayer updatePlayerz = new UpdatePlayer(null, "Modifier un joueur", false, null);
        int choice1 = -1;
        String[] operations = {"Modifier", "Supprimer"};
        if (e.getSource() == btn) {
            choice1 = JOptionPane.showOptionDialog(null, "Que voulez-vous faire ?", "Choix",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, operations, operations[0]);
        }
        if (operations[choice1].equals("Modifier")) {
            UpdatePlayer updatePlayer = new UpdatePlayer(null, "Modifier un joueur", true, this);
            try {
/*                 String[] buttons = {"nom", "prenom", "naissance", "nationalite", "poste", "pied", "taille", "poids","numero","contrat","photo","salaire"};
                int choice = JOptionPane.showOptionDialog(null, "Choisir un attribut :", "Attributs",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                String attributToChange = buttons[choice];
                String userInput = null;
                Object thingsToChange.get(attribut) = null;
                if (attributToChange.equals("photo")) {
                    ImageIcon imageJoueur;
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG images ", "jpg","png");
                    chooser.setFileFilter(filter);
                    int returnVal = chooser.showOpenDialog(null);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        imageJoueur = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
                        imageJoueur.setImage(imageJoueur.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
                        //imgBouton.setIcon(imageJoueur);
                        File imageFile = new File(imageJoueur.toString());
                        System.out.println(imageJoueur.toString());
                        
                        byte[] imageData = new byte[(int) imageFile.length()];
                        try (FileInputStream fis = new FileInputStream(imageFile)) {
                            fis.read(imageData);
                        } catch (IOException e23) {
                            System.err.println(e23);
                        }
                        thingsToChange.get(attribut) = (Object)imageData;
                }}else {
                    userInput = JOptionPane.showInputDialog(null, "Par quoi voulez-vous le remplacer ?");
                    thingsToChange.get(attribut) = userInput;
                }*/
                Player player = this.toPlayer();
                System.out.println("test");
                HashMap<String,Object> attributsHashMap = thingsToUpdate(updatePlayer);
                int update = 0;
                if (!attributsHashMap.isEmpty()) {
                    update = MainInsertClient.updateRequest(player,attributsHashMap,"UPDATE_PLAYER");
                }
                if (update == 1) {
                    updateAttribut(attributsHashMap);
                    Iterator<InfosJoueurs> iterator = Effectif.listeInfosJoueurs.iterator();
                    while (iterator.hasNext()) {
                        InfosJoueurs joueur = iterator.next();
                        if(joueur.getId() == id) {
                            iterator.remove();
                            System.out.println(Effectif.listeInfosJoueurs.toString());

                        }
                    } 
                    Effectif.listeInfosJoueurs.add(this);
                    Effectif.gs.sort(Effectif.listeInfosJoueurs,Effectif.ascending_order,Effectif.attribut);
                    //System.out.println(Arrays.toString(infos));  
                }
                else{
                    JOptionPane.showMessageDialog(null, "Le joueur n'a pas pu être modifié", "Erreur", JOptionPane.ERROR_MESSAGE);

                }
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null,ex);
            }
        }
        if (operations[choice1].equals("Supprimer")) {
            try {
                int choix = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce joueur ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                if (choix == JOptionPane.YES_OPTION) {
                    Player player = this.toPlayer();
                    int delete = MainInsertClient.deleteRequest(player,"DELETE_PLAYER");
                    if (delete == 1){
                        JOptionPane.showMessageDialog(null, "Ce joueur a été supprimé avec succès", "Information", JOptionPane.INFORMATION_MESSAGE);
                        Iterator<InfosJoueurs> iterator = Effectif.listeInfosJoueurs.iterator();
                        while (iterator.hasNext()) {
                            InfosJoueurs joueur = iterator.next();
                            if(joueur.getId() == id) {
                                iterator.remove();
                                System.out.println(Effectif.listeInfosJoueurs.toString());
                            }
                        }
                        Effectif.gs.sort(Effectif.listeInfosJoueurs,Effectif.ascending_order,Effectif.attribut); 
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Le joueur n'a pas pu être supprimé", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null,ex);
            }
        } 
    }


    private HashMap<String,Object> thingsToUpdate(UpdatePlayer p) {

        HashMap<String,Object> hashMap = new HashMap<>();
        if (!(p.getNom().equalsIgnoreCase(this.nom))) {
            hashMap.put("nom",p.getNom());
        }
        if (!(p.getPrenom().equalsIgnoreCase(this.prenom))) {
            hashMap.put("prenom",p.getPrenom());
        }
        if (!(Integer.parseInt(p.getTaille()) == (this.taille))) {
            hashMap.put("taille",p.getTaille());
        }
        if (!(p.getPoste().equalsIgnoreCase(this.position))) {
            hashMap.put("poste",p.getPoste());
        }
        if (!(Integer.parseInt(p.getPoids()) == (this.poids))) {
            hashMap.put("poids",p.getPoids());
        }
        if (!(Integer.parseInt(p.getNumero()) == (this.numero))) {
            hashMap.put("numero",p.getNumero());
        }       
        if (!(Integer.parseInt(p.getSalaire()) == (this.salaire))) {
            hashMap.put("salaire",p.getSalaire());
        }
        if (!(p.getPied().equalsIgnoreCase(this.pied))) {
            hashMap.put("pied",p.getPied());
        }
        if (!(p.getNationalite().equalsIgnoreCase(this.nationalite))) {
            hashMap.put("nationalite",p.getNationalite());
        }
        if (!(p.getNaissance().equals(this.naissance))) {
            hashMap.put("datenaissance",p.getNaissance());
        }
        if (!(p.getContrat().equals(this.contrat))) {
            hashMap.put("datefin",p.getContrat());
        }
        if (!(p.getPhotoBytes().equals(this.photo))) {
            hashMap.put("photo",p.getPhotoBytes());
        }
        return hashMap;
    }


    public void updateAttribut(HashMap<String,Object> thingsToChange){
        for (String attribut : thingsToChange.keySet() ) {
        switch (attribut) {
            case "nom":
                nom = thingsToChange.get(attribut).toString();
                break;
            case "prenom":
                prenom = thingsToChange.get(attribut).toString();
                break;
            case "datenaissance":
                naissance = Date.valueOf(thingsToChange.get(attribut).toString());
                break;
            case "nationalite":
                nationalite = thingsToChange.get(attribut).toString();
                break;
            case "poste":
                position = thingsToChange.get(attribut).toString();
                break;
            case "pied":
                pied = thingsToChange.get(attribut).toString();
                break;
            case "taille":
                taille = Integer.valueOf(thingsToChange.get(attribut).toString());
                break;
            case "poids":
                poids = Integer.valueOf(thingsToChange.get(attribut).toString());
                break;
            case "photo":
                photo = convertToBytes(thingsToChange.get(attribut));
                break;
            case "datefin":
                contrat = Date.valueOf(thingsToChange.get(attribut).toString());
                break;
            case "salaire":
                salaire = Integer.valueOf(thingsToChange.get(attribut).toString());
                break;
            case "numero":
                numero = Integer.valueOf(thingsToChange.get(attribut).toString());
                break;
            default:
        }
                
        }
        String[] i  = {prenom+" "+nom, this.calculed_age, this.nationalite,this.contrat+"",this.salaire+"",this.position,this.taille+"",this.numero+"",this.poids+"",this.pied+""};
        infos = i;
    }


    public static byte[] convertToBytes(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bouton getBoutonModif() {
        return btn;
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


    public Date getContrat(){
        return contrat;
    }

    public int getSalaire(){
        return salaire;
    }

    public Image getImagePhoto() {
        if (photo != null) {
        try {
                ByteArrayInputStream bis = new ByteArrayInputStream(photo);
                Image awtImage = ImageIO.read(bis);
                bis.close();
                return awtImage;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
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

    public static InfosJoueurs playerToInfosJoueurs(Player p) {
        InfosJoueurs j = new InfosJoueurs(p.nom,p.prenom,p.naissance,p.nationalite,p.contrat,p.salaire,p.position,p.taille,p.numero,p.poids,p.pied,p.getId(),p.photo);
        return j;
    }

    public Player toPlayer(){
        return (new Player(nom,prenom,naissance,nationalite,contrat,salaire,position,taille,numero,poids,pied,id,photo)); 
    }

    
}