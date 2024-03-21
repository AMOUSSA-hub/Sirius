package client.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;
import edu.ezip.ing1.pds.client.MainInsertClient;
import edu.ezip.ing1.pds.client.MainSelectClient;

import java.io.InputStream;
import java.awt.Image;
import javax.imageio.ImageIO;

import java.io.IOException;
import edu.ezip.ing1.pds.business.dto.Player;

public class Effectif extends Page {
    
    public int lastIdValue = MainSelectClient.lastIdValue;
    static final Color fondTitre = new Color(96,96,96);
    ImageIcon upArrow,downArrow;
    ImageIcon swap = downArrow;
    JComboBox<String> tri = new JComboBox<>(); 
    Bouton ordre;
    Boolean croissant = true;
    String attribut = "";
    JScrollPane scrollPane = new JScrollPane();
    List<InfosJoueurs> listeInfosJoueurs = new ArrayList<>();
    Bouton addPlayer = new Bouton(50, "Ajouter");
    Box box = new Box(BoxLayout.Y_AXIS);
    Box boxTest = new Box(BoxLayout.Y_AXIS);
    Box titre;
    int hauteur_effectif = InfosJoueurs.HeightBox_Y + 3;
    String insereLinux = "./test.sh";
    String insereWin = ".\\test.bat";
    String os = Fenetre.os;
    Effectif() {
        removeAllExecptedMenuhome();
        try {
            // Charger l'image depuis les ressources
            InputStream inputStream = getClass().getResourceAsStream("/upArrow.png");
            InputStream inputStream2 = getClass().getResourceAsStream("/downArrow.png");
            Image image = ImageIO.read(inputStream);
            Image image2 = ImageIO.read(inputStream2);
            // Créer l'objet ImageIcon à partir de l'objet Image
            upArrow = new ImageIcon(image);
            downArrow = new ImageIcon(image2);
            swap = downArrow;
            // Utiliser clubManagerLabel ici...
        } catch (IOException ex) {
            ex.printStackTrace();
        }




        tri.setRenderer(listRenderer);
        tri.setFont(LabelTxt.font);
        tri.addItem("Trier par");
        tri.addItem("Par nom");
        tri.addItem("Par prenom");
        tri.addItem("Par age");
        tri.addItem("Par contrat");
        tri.addItem("Par salaire");
        tri.addItem("Par poste");
        tri.addItem("Par taille");
        tri.addItem("Par numero");
        tri.addItem("Par poids");
        
        tri.setSize(500, fontMetrics.getHeight()*2);
        placeElement(2, 4, tri);
        add(addPlayer).setBounds(0, tri.getY() + tri.getHeight(), addPlayer.width, addPlayer.height);
        repaint();
        titre = new Box(BoxLayout.X_AXIS);
        titreBox();
        
        selectBDD();
        ordre = new Bouton(tri.getHeight(), tri.getHeight(),upArrow,bg);
        ordre.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        ordre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Icon tmp = ordre.getIcon();
                ordre.setIcon(swap);
                swap = (ImageIcon)tmp;
                croissant = !croissant;
                Collections.sort(listeInfosJoueurs,new JoueursCompare(attribut, croissant));
                ensembleJoueurs(listeInfosJoueurs, box);
            }
        });
        add(ordre).setBounds(tri.getX() + tri.getWidth(), tri.getY(),ordre.width,ordre.height);
        tri.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                tri.removeItem("Trier par");
                if (e.getItem() == "Par age") attribut = "Age";
                if (e.getItem() == "Par nom") attribut = "Nom";
                if (e.getItem() == "Par prenom") attribut = "Prenom";
                if (e.getItem() == "Par contrat") attribut = "Contrat";
                if (e.getItem() == "Par salaire") attribut = "Salaire";
                if (e.getItem() == "Par taille") attribut = "Taille";
                if (e.getItem() == "Par numero") attribut = "Numero";
                if (e.getItem() == "Par poids") attribut = "Poids";
                if (e.getItem() == "Par poste") attribut = "Poste";
                Collections.sort(listeInfosJoueurs,new JoueursCompare(attribut,croissant));
                ensembleJoueurs(listeInfosJoueurs, box);
                }
            
            
        });


        addPlayer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AddPlayer joueur = new AddPlayer(null, "Ajouter un joueur ", true);
                joueur.showAddPlayer(); 
                //UtilDateModel model = (UtilDateModel)joueur.dateContrat.getModel();
                java.util.Date date = (java.util.Date)joueur.dateContratSpinner.getValue();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                //UtilDateModel model2 = (UtilDateModel)joueur.dateNaissance.getModel();
                java.util.Date age = (java.util.Date)joueur.dateNaissanceSpinner.getValue();
                java.sql.Date dateNaiss = new java.sql.Date(age.getTime());
                
                if(!joueur.nom.getText().isBlank() && !joueur.prenom.getText().isEmpty() /*&& !joueur.nationalite.getText().isEmpty()*/) {
                    MainSelectClient.lastIdValue++;
                    lastIdValue = MainSelectClient.lastIdValue;  
                    System.out.println("test");
                    Player j = new Player(joueur.prenom.getText(),joueur.nom.getText(),dateNaiss,joueur.nationalite.getText(),sqlDate,Integer.parseInt(joueur.salaire.getText()),(String)joueur.poste.getSelectedItem(),(int)joueur.tailleSpinner.getValue(),(int)joueur.numeroSpinner.getValue(),(int)joueur.poidsSpinner.getValue(),joueur.pied.getSelectedItem().toString(),lastIdValue);
                    listeInfosJoueurs.add(InfosJoueurs.playerToInfosJoueurs(j));
                    try {
                        MainInsertClient.sendPlayer(j);
                        System.out.println("val : " + lastIdValue);
                        Collections.sort(listeInfosJoueurs,new JoueursCompare(attribut,croissant));
                        ensembleJoueurs(listeInfosJoueurs, box);

                    }catch(Exception exp) {
                        System.err.println(exp);
                    }
/*                     String scp = "";
                    if (Fenetre.os.contains("win")) {scp = insereWin;}
                    if (Fenetre.os.contains("nix")|| Fenetre.os.contains("nux") || Fenetre.os.contains("aix")) {scp = insereLinux;} 
                    insereBDD(j,scp);
                    Collections.sort(listeInfosJoueurs,new JoueursCompare(attribut,croissant));
                    ensembleJoueurs(listeInfosJoueurs, box);
                    //System.out.println(j.toString()); */
                }
                
            }
            
        });
    }

    private void titreBox(){
        InfosJoueurs.addInfosBox("PHOTO", null, new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("JOUEUR", null, new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("AGE", null, new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("NATIONALITE", null, new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("CONTRAT", null, new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("SALAIRE", null, new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("POSTE", null, new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("TAILLE", "(en cm)", new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("NUMERO", null, new Box(BoxLayout.Y_AXIS), titre);
        InfosJoueurs.addInfosBox("POIDS", "(en Kg)", new Box(BoxLayout.Y_AXIS), titre);
        //add(titre).setBounds(0, addPlayer.getY() + addPlayer.height, WIDTH + 100, 100);
        //titre.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        JScrollPane pane = new JScrollPane(titre);
        pane.getViewport().setBackground(fondTitre);
        box.add(pane.getViewport());
        //listeInfosJoueurs.add(pane);
    }

    private void ensembleJoueurs(List<InfosJoueurs> listeJoueurs,Box box){ //Affiche la liste des joueurs du club 
        int y = addPlayer.getY() + addPlayer.height;
        hauteur_effectif = InfosJoueurs.HeightBox_Y + 3;
        for (int i = 0 ; i < listeJoueurs.size(); i++){
            box.add(listeJoueurs.get(i).getViewport());
            if (hauteur_effectif < HEIGHT - (y+50)) hauteur_effectif += InfosJoueurs.HeightBox_Y;
            else hauteur_effectif = HEIGHT -(y+50);
            
        }
        scrollPane.setViewportView(box);
        scrollPane.getViewport().setBackground(bg);  
        scrollPane.getViewport().setForeground(Color.yellow);
        this.add(scrollPane);
        scrollPane.getVerticalScrollBar().setBackground(bg);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
            this.thumbColor = Color.BLACK;
            this.scrollBarWidth = 30;
            }});
        int scrollBarSize = ((Integer)UIManager.get("ScrollBar.width")).intValue();
        scrollPane.setBounds(0,y, WIDTH + scrollBarSize  ,hauteur_effectif);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
/*         int x = 150;
        g.setColor(Color.YELLOW);       
        g.drawLine(x, addPlayer.getY() + addPlayer.height, x,HEIGHT); */
 
    }   

    public void insereBDD(InfosJoueurs j,String script){
        try {
            FileWriter fileWriter = new FileWriter("../../prototype/xmart-insert-client/target/classes/player-to-be-inserted.yaml", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("students:");
            bufferedWriter.newLine();
            bufferedWriter.write("  - nom : \"" +j.nom +"\"");
            bufferedWriter.newLine();
            writeStringYaml(bufferedWriter, "prenom", j.prenom);
            writeIntYaml(bufferedWriter, "numero",j.numero);
            writeDateYaml(bufferedWriter, "naissance", j.naissance);
            writeStringYaml(bufferedWriter, "poste", j.position);
            writeStringYaml(bufferedWriter, "pied", j.pied);
            writeIntYaml(bufferedWriter, "taille", j.taille);
            writeIntYaml(bufferedWriter, "poids", j.poids);
            writeStringYaml(bufferedWriter,"nationalite",j.nationalite);
            bufferedWriter.close();
            fileWriter.close();
            //System.out.println("dujfezjuzefjuifze");
            ProcessBuilder processBuilder = new ProcessBuilder(script);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("La commande s'est terminée avec le code de sortie : " + exitCode);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void selectBDD(){
        try {
            MainSelectClient.selectAllPlayers(listeInfosJoueurs);
            ensembleJoueurs(listeInfosJoueurs, box);
        }catch(Exception execp) {
            System.err.println(execp);
        }
        
    }


    private void writeStringYaml(BufferedWriter bufferedWriter,String name,String attribut) throws Exception {
        bufferedWriter.write("    "+name+" : \"" + attribut +"\"");
        bufferedWriter.newLine();
    }

    private void writeIntYaml(BufferedWriter bufferedWriter,String name,int attribut) throws Exception {
        bufferedWriter.write("    "+name+ " : " + attribut);
        bufferedWriter.newLine();
    }

    private void writeDateYaml(BufferedWriter bufferedWriter,String name,Date attribut) throws Exception {
        bufferedWriter.write("    "+name+" : \"" + attribut +"\"");
        bufferedWriter.newLine();
    }





    
}
