import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Date;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import java.time.LocalDate;

import org.jdatepicker.*;

public class Effectif extends JPanel{
    
    static final Color fondTitre = new Color(96,96,96);
    ImageIcon upArrow = new ImageIcon(new ImageIcon("client-front_V2/images/upArrow.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)) ;
    ImageIcon downArrow = new ImageIcon(new ImageIcon("client-front_V2/images/downArrow.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    ImageIcon swap = downArrow;
    JComboBox<String> tri = new JComboBox<>(); 
    Bouton ordre;
    boolean ascending_order = true;
    String attribut = "";
    JScrollPane scrollPane = new JScrollPane();
    List<InfosJoueurs> listeInfosJoueurs = new ArrayList<>();
    Bouton addPlayer = new Bouton(50, "Ajouter");
    Box box = new Box(BoxLayout.Y_AXIS);
    Box boxTest = new Box(BoxLayout.Y_AXIS);
    Box titre;
    int hauteur_effectif = InfosJoueurs.HeightBox_Y + 3;
    Effectif(JFrame fen) {
        
        setBackground(Color.GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        tri.setRenderer(new DefaultListCellRenderer());


        JPanel sortPane = new JPanel(new FlowLayout());
        sortPane.setOpaque(false);
        

        //menu de tri
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

       
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(100, 1, 1, 1);
        tri.setPreferredSize(new Dimension(300, 30));
        add(sortPane,gbc);



        JButton order = new JButton("",upArrow);
        order.setPreferredSize(new Dimension(50, 30));
        sortPane.add(tri);
        sortPane.add(order);


        // Mise en place du tableau des infos de l'effectif
        String[] columnNames = {"PHOTO","JOUEUR","AGE","NAT","CONTRAT","SALAIRE","POS","TAILLE","N°","POIDS"};
        GridSquad gs = new GridSquad(columnNames,fen);
        JScrollPane scrollPane = new JScrollPane(gs);
        
        //Bouton pour ajouter des joueurs
        JButton addPlayerButton = new JButton("Ajouter");
        addPlayerButton.setBackground(Color.BLACK);
        addPlayerButton.setForeground(Color.WHITE);
        addPlayerButton.setFont(new Font("Arial", Font.BOLD, 12));

         gbc.gridx = 1;
         gbc.gridy = 2;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gbc.fill = GridBagConstraints.NONE;
         gbc.anchor = GridBagConstraints.FIRST_LINE_START;
         gbc.weightx = 1.0;
         gbc.weighty = 0.0;
         gbc.insets = new Insets(1, 1, 1, 1);
         addPlayerButton.setPreferredSize(new Dimension(100, 30));
         add(addPlayerButton,gbc);



        //tableau effectif
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(1, 1, 1, 1);
        add(scrollPane,gbc);




   
        //selectBDD();

        for(InfosJoueurs joueur : listeInfosJoueurs){
           gs.addRow(joueur);
        }
        

        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(!ascending_order){
                    order.setIcon(upArrow);
                    ascending_order = true;
                 }
                
                else{
                    order.setIcon(downArrow);
                    ascending_order = false;
                 };
                
                Collections.sort(listeInfosJoueurs,new JoueursCompare(attribut,ascending_order));
                ensembleJoueurs(listeInfosJoueurs, box);
            }
        });
        
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
                Collections.sort(listeInfosJoueurs,new JoueursCompare(attribut,ascending_order));
                ensembleJoueurs(listeInfosJoueurs, box);
                }
            
            
        });


        addPlayerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AddPlayer joueur = new AddPlayer(null, "Ajouter un joueur ", true);
                joueur.showAddPlayer(); 
                UtilDateModel model = (UtilDateModel)joueur.dateContrat.getModel();
                java.util.Date date = model.getValue();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                UtilDateModel model2 = (UtilDateModel)joueur.dateNaissance.getModel();
                java.util.Date age = model2.getValue();
                java.sql.Date dateNaiss = new java.sql.Date(age.getTime());
                
                if(!joueur.nom.getText().isBlank() && !joueur.prenom.getText().isEmpty() /*&& !joueur.nationalite.getText().isEmpty()*/) {
                    InfosJoueurs j = new InfosJoueurs(joueur.prenom.getText(),joueur.nom.getText(),dateNaiss,joueur.nationalite.getText(),sqlDate,Integer.parseInt(joueur.salaire.getText()),(String)joueur.poste.getSelectedItem(),(int)joueur.tailleSpinner.getValue(),(int)joueur.numeroSpinner.getValue(),(int)joueur.poidsSpinner.getValue(),joueur.pied.getSelectedItem().toString());
                    listeInfosJoueurs.add(j);
                    insereBDD(j);
                    Collections.sort(listeInfosJoueurs,new JoueursCompare(attribut,ascending_order));
                    ensembleJoueurs(listeInfosJoueurs, box);
                    //System.out.println(j.toString());
                }
                
            }
            
        });
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
        scrollPane.getViewport().setBackground(Color.GRAY);  
        scrollPane.getViewport().setForeground(Color.yellow);
        this.add(scrollPane);
        scrollPane.getVerticalScrollBar().setBackground(Color.GRAY);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
            this.thumbColor = Color.BLACK;
            this.scrollBarWidth = 30;
            }});
        int scrollBarSize = ((Integer)UIManager.get("ScrollBar.width")).intValue();
        scrollPane.setBounds(0,y, WIDTH + scrollBarSize  ,hauteur_effectif);
        
    }



    public void insereBDD(InfosJoueurs j){
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
            writeStringYaml(bufferedWriter, "poste", j.postion);
            writeStringYaml(bufferedWriter, "pied", j.pied);
            writeIntYaml(bufferedWriter, "taille", j.taille);
            writeIntYaml(bufferedWriter, "poids", j.poids);
            writeStringYaml(bufferedWriter,"nationalite",j.nationalite);
            bufferedWriter.close();
            fileWriter.close();
            ProcessBuilder processBuilder = new ProcessBuilder("./test.sh");
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
           /*  ProcessBuilder processBuilder = new ProcessBuilder("./select.sh");
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("La commande s'est terminée avec le code de sortie : " + exitCode); */
            FileReader fileReader = new FileReader("./prototype/xmart-select-client/Select.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int nbJoueurs = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < nbJoueurs; i++) {
                String nom = (bufferedReader.readLine());
                String prenom = bufferedReader.readLine();
                int numero = Integer.parseInt(bufferedReader.readLine());
                Date naissance = Date.valueOf(bufferedReader.readLine());
                String nationalite = bufferedReader.readLine();
                String poste = bufferedReader.readLine();
                String pied = bufferedReader.readLine();
                int taille = Integer.parseInt(bufferedReader.readLine());
                int poids = Integer.parseInt(bufferedReader.readLine());
                String finDuJoueur = bufferedReader.readLine(); //C'est juste une ligne vide pour separer chaque joueur 
                listeInfosJoueurs.add(new InfosJoueurs(prenom, nom, naissance, nationalite, Date.valueOf(LocalDate.now()), 0, poste, taille, numero, poids, pied));
                //System.out.println(nom + " " + prenom + " " + numero + " " + naissance + " " + nationalite + " " + poste + " " + pied + " " +taille + " " + poids);
            }
        } catch (Exception e) {
            System.err.println(e);
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
