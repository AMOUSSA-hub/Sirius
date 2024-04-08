package com.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import edu.ezip.ing1.pds.client.*;
import client.frontend.v2.*;

import java.util.List;

import javax.swing.JLabel;

import javafx.scene.Node;

import java.util.ArrayList;
//import java.util.Date; // Pour la classe Date
import java.sql.*;

public class Controller  {
    List<List<Object>> listOfPlayersInformations;

    int hgap = 15;
    int vgap = 75;

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();

    double screenWidth = primaryScreenBounds.getWidth();
    double screenHeight = primaryScreenBounds.getHeight()-70;
    
    @FXML
    private GridPane titreAttributs;

    @FXML
    private Button accueil;
    @FXML
    private Button ajouter;

    @FXML
    private GridPane MenuLeft;
    
    @FXML
    private Button home;

    @FXML
    private GridPane listePlayers;

    
    @FXML
    private Button addRow;
    
    
    @FXML
    private ScrollPane scrollPlayer;
    

    @FXML
    private AnchorPane ancor;

    @FXML
    void addPlayer(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("new.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.scene.getWindow());
        stage.showAndWait();   
             
    }

    public List<InfosJoueurs> selectBDD(){
        List<InfosJoueurs> liste = new ArrayList<>();
        try {
            listOfPlayersInformations = MainSelectClient.selectAllPlayers();
            for (List<Object> playerInformation : listOfPlayersInformations) {
               System.out.println(playerInformation.toString()); 
               InfosJoueurs rowInfo = new InfosJoueurs((String)playerInformation.get(0),(String)playerInformation.get(1),(Date)playerInformation.get(2),(String)playerInformation.get(3),(Date)playerInformation.get(4),(int)playerInformation.get(5),(String)playerInformation.get(6),(int)playerInformation.get(7),(int)playerInformation.get(8),(int)playerInformation.get(9),(String)playerInformation.get(10),(int)playerInformation.get(11),(byte[])playerInformation.get(12));
               List l = playerInformation;
               int lSize = l.size();
               l.remove(lSize -1);
               //l.remove(lSize -2);
               setRow(l);
               listePlayers.setHgap(hgap);
               listePlayers.setVgap(vgap);
               liste.add(rowInfo);
            }
            return liste;
            //ensembleJoueurs(listeInfosJoueurs, box);
        }catch(Exception execp) {
            System.err.println(execp);
        }
        return null;
    }

    public void setRow(List list) {
        int col = 0;
        for (Object obj : list) {
            if (obj instanceof String || obj instanceof Integer || obj instanceof java.sql.Date || obj instanceof byte[]) {
                // Ajoutez le code pour gérer chaque type d'élément
                // Ici, nous supposons que listePlayers est une grille JavaFX
                Label label = new Label(obj.toString()); // Par exemple, convertissez l'élément en chaîne et créez un Label
                listePlayers.add(label, col, row);
                col++;
            } else {
                // Gérer les autres types d'éléments selon vos besoins
                System.out.println("Type d'élément non pris en charge : " + obj.getClass().getName());
            }
        }
        row++;
    }

    public void setTitle(String[] titles){
        int col = 0;
        for (int i = 0; i < titles.length; i++) {
            titreAttributs.add(new Label(titles[i]),col,0);
            titreAttributs.setHgap(hgap);
            col++;
        }
    }
    


    @FXML
    private Button photo;
    int row = 1;
    int taille = 200;
    @FXML
    void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        
        // Définition du titre de la boîte de dialogue
        fileChooser.setTitle("Sélectionnez un fichier");

        // Affichage de la boîte de dialogue de sélection de fichiers
        File selectedFile = fileChooser.showOpenDialog(App.scene.getWindow());

        // Vérification si un fichier a été sélectionné
        if (selectedFile != null) {
            System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }

    }
    @SuppressWarnings("exports")
    @FXML
    public void accueilBtn(ActionEvent event)  {
   
    }



    @FXML
    void addRowBtn(ActionEvent event) {
        for (int i = 0; i < 7; i++) {
            listePlayers.add(new Label("Valeur : "+ String.valueOf(i)), i, row);
        }
        row++;
        taille += vgap;
        listePlayers.setVgap(vgap);
        listePlayers.setHgap(vgap);
        listePlayers.setPrefHeight(taille);
        if (taille >= screenHeight*3/4) ancor.setPrefHeight(taille + vgap);
    }


    @FXML
    void homeClicked(ActionEvent event) {

    }
public void initialize() {
    String[] tiltles = {"Prenom","Nom","Age","Nat","Contrat","Salaire","Pos","Taille","Num","Poids","Pied"};
    setTitle(tiltles);
    scrollPlayer.setPrefHeight(screenHeight*3/4);
    scrollPlayer.setPrefWidth(screenWidth);
    ancor.setPrefWidth(screenWidth*3/4);
    ancor.setPrefHeight(screenHeight*3/4);
    listePlayers.setPrefHeight(taille);
    listePlayers.setPrefWidth(screenWidth);
    List list = selectBDD();
    System.out.println(list.toString());
    ObservableList<PieChart.Data> pieCharData = FXCollections.observableArrayList(
        new PieChart.Data("Vitesse", 38),
        new PieChart.Data("Puissance", 50),
        new PieChart.Data("Technique", 22)

    );
    
    MenuLeft.setPrefHeight(screenHeight*3/4);
    
    InputStream inputStream = getClass().getResourceAsStream("/home.png");
    if (inputStream != null) {
        Image img = new Image(inputStream);
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        home.setGraphic(view);
        //listePlayers.add(view, 0, 0);
    } else {
        System.err.println("Erreur lors du chargement de l'image.");
    }
}



}
