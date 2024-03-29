package client.frontend.v2;

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

import edu.ezip.ing1.pds.client.MainInsertClient;
import edu.ezip.ing1.pds.client.MainSelectClient;

import java.io.InputStream;
import java.awt.Image;
import javax.imageio.ImageIO;

import java.io.IOException;
import edu.ezip.ing1.pds.business.dto.Player;
import org.jdatepicker.*;
import javax.swing.JOptionPane;

import java.io.InputStream;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;


public class Effectif extends JPanel{
    public int lastIdValue = MainSelectClient.lastIdValue;

    static final Color fondTitre = new Color(96,96,96);
    ImageIcon upArrow; //= new ImageIcon(new ImageIcon("client-front_V2/images/upArrow.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)) ;
    ImageIcon downArrow; //= new ImageIcon(new ImageIcon("client-front_V2/images/downArrow.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
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
    Color bg = Color.gray;
    Effectif(JFrame fen) {

        try {
            // Charger l'image depuis les ressources
            InputStream inputStream = getClass().getResourceAsStream("/upArrow.png");
            InputStream inputStream2 = getClass().getResourceAsStream("/downArrow.png");
            Image image = ImageIO.read(inputStream).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            Image image2 = ImageIO.read(inputStream2).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            // Créer l'objet ImageIcon à partir de l'objet Image
            upArrow = new ImageIcon(image);
            downArrow = new ImageIcon(image2);
          

        }catch(Exception e){
            System.err.println(e);
        }
        
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

       //mise en place du menu de tri
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(70, 1, 1, 1);
       // tri.setPreferredSize(new Dimension(300, 30));
        add(sortPane,gbc);



        JButton order = new JButton("",upArrow);
        order.setPreferredSize(new Dimension(50, 30));
        sortPane.add(tri);
        sortPane.add(order);


        // Mise en place du tableau des infos de l'effectif
        String[] columnNames = {"PHOTO","JOUEUR","AGE","NAT","CONTRAT","SALAIRE","POS","TAILLE","N°","POIDS","PIED"};
        GridInfoSquad gs = new GridInfoSquad(columnNames,fen);
       
        
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
        gbc.gridwidth = 1;
        gbc.gridheight = 1; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(1, 1, 1, 1);
        add(gs, gbc);





   
        selectBDD(gs);
        

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
            gs.unFillGrid();
               // ensembleJoueurs(listeInfosJoueurs, box);
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
                //ensembleJoueurs(listeInfosJoueurs, box);
                }
            
            
        });


        addPlayerButton.addActionListener(new ActionListener() {

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
                    //listeInfosJoueurs.add(InfosJoueurs.playerToInfosJoueurs(j));
                    try {
                        int insert = (MainInsertClient.sendPlayer(j));
                        if (insert == 1) {
                            System.out.println("val : " + lastIdValue);
                            listeInfosJoueurs.add(InfosJoueurs.playerToInfosJoueurs(j));
                            Collections.sort(listeInfosJoueurs,new JoueursCompare(attribut,ascending_order));
                            gs.addRow(playerInformation(j));
                            repaint();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Le serveur est offline", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }catch(Exception exp) {
                        System.err.println(exp);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Les informations entrées ne sont pas complètes","Erreur", JOptionPane.ERROR_MESSAGE);
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


    public void selectBDD(GridInfoSquad gs){
        try {
            List<List<Object>> listOfPlayersInformations = MainSelectClient.selectAllPlayers();
            for (List<Object> playerInformation : listOfPlayersInformations) {
                gs.addRow(playerInformation);//new InfosJoueurs((String)playerInformation.get(0),(String)playerInformation.get(1),(Date)playerInformation.get(2),(String)playerInformation.get(3),(Date)playerInformation.get(4),(int)playerInformation.get(5),(String)playerInformation.get(6),(int)playerInformation.get(7),(int)playerInformation.get(8),(int)playerInformation.get(9),(String)playerInformation.get(10),(int)playerInformation.get(11)));
            }
            //ensembleJoueurs(listeInfosJoueurs, box);
        }catch(Exception execp) {
            System.err.println(execp);
        }
        
    }

    public List<Object> playerInformation(Player p) {
        List<Object> res = new ArrayList<>();
        res.add(p.prenom);
        res.add(p.nom);
        res.add(p.naissance);
        res.add(p.nationalite);
        res.add(p.contrat);
        res.add(p.salaire);
        res.add(p.position);
        res.add(p.taille);
        res.add(p.numero);
        res.add(p.poids);
        res.add(p.pied);
        res.add(p.id);
        return res;
    }

    
}
