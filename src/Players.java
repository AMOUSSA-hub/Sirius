import java.awt.CardLayout;
import java.awt.Color;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Players extends JPanel implements MouseListener, ItemListener {

    static CardLayout cardLayout = Fenetre.cardLayout;


    List<InfosJoueurs> listeJoueurs = new ArrayList<>();

    int hauteur;

    JComboBox<String> tri = new JComboBox<>();
    Box box = new Box(BoxLayout.Y_AXIS);
    JScrollPane scrollPane = new JScrollPane();

    Bouton addPlayer = new Bouton("Ajouter Joueur");

    String none = "./images/unnamed.jpg";


    static JFrame f = Fenetre.f;
    static ImageIcon moissy = new ImageIcon("./images/Moissy.png");

    JLabel effectif = new LabelTxt("Effectif",50);

   // Bouton voirPlus = new Bouton("Voir Plus");



    Players() {

        //this.setLayout(cardLayout);
        this.setVisible(true);
        this.setLayout(null);
        

        this.add(new Menuhome());
        this.setVisible(true);



        //this.add(lab);
        //this.add(equipe);
        this.add(effectif);
        //this.add(moissyFc);
        this.add(addPlayer);

        tri.addItem("Par Défault");        
        tri.addItem("Par Age");
        tri.addItem("Par Prenom");
        tri.setBackground(Contenu.bg);
        tri.setForeground(Contenu.yellow);
        this.add(tri);

        


        this.setBackground(Contenu.bg);

        this.setLayout(null);
        //lab.setVisible(true);
        //lab.setIcon(moissy);

        
        //equipe.setBounds(Menuhome.WIDTH*2, 0, 250, equipe.getFont().getSize()*2);

        effectif.setSize(242, effectif.getFont().getSize()*2);
        effectif.setBounds(Contenu.WIDTH_WITH_MENUHOME/2 -effectif.getWidth()/2, 0, effectif.getWidth(), effectif.getHeight());
        
        //lab.setBounds(Menuhome.WIDTH*2 - moissy.getIconWidth()/4, equipe.getFont().getSize()*3, moissy.getIconWidth(),moissy.getIconHeight());
        //moissyFc.setBounds(Menuhome.WIDTH*19/10 - 5, lab.getHeight() + equipe.getHeight()*3, 250, moissyFc.getFont().getSize()*2);


        hauteur = effectif.getHeight();

    

        //this.add(voirPlus);
        //voirPlus.setBounds(Contenu.WIDTH/40 + Menuhome.WIDTH, Contenu.HEIGHT - Contenu.BOUTON_HEIGHT*2, Contenu.BOUTON_WIDTH, Contenu.BOUTON_HEIGHT);
        addPlayer.setBounds((Contenu.WIDTH_WITH_MENUHOME - Contenu.BOUTON_WIDTH)/2, Contenu.HEIGHT - Contenu.BOUTON_HEIGHT*3/2, Contenu.BOUTON_WIDTH, Contenu.BOUTON_HEIGHT);    
        
        
        
        this.ReadBaseDonnees();



        tri.setBounds(Contenu.WIDTH-105, 0, 200, effectif.getHeight()/2);
        tri.setFont(new LabelTxt(12).getFont());
        



        addPlayer.addMouseListener(this);
        tri.addItemListener(this);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g1) {
        super.paintComponent(g1);
        //Graphics2D g = (Graphics2D) g1;
        //BasicStroke line = new BasicStroke(7.0f);
        //g.setStroke(line);
        //g.setColor(Contenu.yellow);
        //g.drawLine(Contenu.WIDTH_WITH_MENUHOME/3, 0, Contenu.WIDTH_WITH_MENUHOME/3, Contenu.HEIGHT);
        //g.setStroke(new BasicStroke(1.0f));
    }



    private void ReadBaseDonnees() {
        try
    {
                                                                    
      //Class.forName("com.mysql.cj.jdbc.Driver");

      Connection conn = App.connectBDD();

      Statement stmt = conn.createStatement();
      String sql = "Select Id,nom,prenom,age,numero,poste,pied,taille from joueurs";
      ResultSet res = stmt.executeQuery(sql);
      
    while(res.next()){

    int id = res.getInt("id");
    String nom = res.getString("nom");
    String prenom = res.getString("prenom");
    int age = res.getInt("age");
    int maillot = res.getInt("numero");
    String poste = res.getString("poste");
    String pied = res.getString("pied");
    int taille = res.getInt("taille");

    InfosJoueurs j = new InfosJoueurs(none, nom, prenom, age, poste, pied,maillot,taille);
    listeJoueurs.add(j);
    j.setId(id);
    //placer(j);
      }  
    
      conn.close();
    }
    catch(Exception e){ 
      System.out.println(e);
    }
    ensembleJoueurs(listeJoueurs, box);
  }


/*       public String getAttributTri(String s) {
        int length = s.length();
        System.out.println(s.substring(4, length));
        return s.substring(4,length);
    } */


    private void EnrgistrerBaseDonnees(InfosJoueurs j){
        String age_only = j.age.getText().substring(6);
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = App.connectBDD();
            Statement stmt = conn.createStatement();
            String sql = "Insert into joueurs(Nom,Prenom,Numero,Poste,Pied,taille) values ('" + j.nom.getText() + "','" + j.prenom.getText() + "'," + j.maillot.getText() + "','"  + j.poste.getText() + "','" + j.pied.getText() + "','" + j.taille.getText()  + "')";
            stmt.executeUpdate(sql);
            conn.close();
        }
        catch(Exception e){ 
            System.out.println(e);
        }
    }





    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == addPlayer) {
            AddPlayer joueur = new AddPlayer(null, "Ajouter un joueur ", true);
            joueur.showAddPlayer(); 
            InfosJoueurs j = new InfosJoueurs(none, joueur.nom.getText().toUpperCase(), joueur.prenom.getText(), (int)joueur.agSpinner.getValue(), joueur.poste.getSelectedItem().toString(),joueur.pied.getSelectedItem().toString(),(int)joueur.numeroSpinner.getValue(),(int)joueur.tailleSpinner.getValue());
            EnrgistrerBaseDonnees(j);
            listeJoueurs.add(j);
/*             if (tri.getSelectedItem().toString() == "Par Défault") {
                ensembleJoueurs(listeJoueurs, box);
            }
            else {
                sortBy(getAttributTri(tri.getSelectedItem().toString()), listeJoueurs);
            } */
            this.setVisible(true);
        }
    }






    @Override
    public void mousePressed(MouseEvent e) {
    

    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }


    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == tri.getSelectedItem()){
            
            if (e.getItem() == "Par Défault") {
                ensembleJoueurs(listeJoueurs, box);
            }
            if (e.getItem() == "Par Age") {
            //System.out.println(tri.getSelectedItem());
            sortBy("Age", listeJoueurs);
            }

            if(e.getItem() == "Par Prenom"){
                sortBy("prenom",listeJoueurs); //Finir les autres tris 
            }



        }
    }

    private void sortBy(String attribut,List<InfosJoueurs> listeJoueurs){
        List<InfosJoueurs> liste = new ArrayList<>();
        hauteur = effectif.getHeight();
        try
    {
                                                                    
      //Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = App.connectBDD();
      Statement stmt = conn.createStatement();
      String sql = " Select id from joueurs order by " + attribut;
      ResultSet res = stmt.executeQuery(sql);
      while(res.next()){
        int id = res.getInt("id");
        for (int i = 0; i < listeJoueurs.size(); i++) {
            InfosJoueurs e = listeJoueurs.get(i);
            if (id == e.getId()) {
                liste.add(e);
                //System.out.println("gg wp");
                //placer(e);
            }
        }
      }
      conn.close();
    }
    catch(Exception e){ 
            System.out.println(e);
        }
        //box.removeAll();
        ensembleJoueurs(liste, box);
    }


   // private void filterBy(String attribut, List<InfosJoueurs> listeJoueurs){ //A implementer pour plus tard mais il faut determniner les parametres a filtrer 

    //}


    private void ensembleJoueurs(List<InfosJoueurs> listeJoueurs,Box box){
        for (int i = 0 ; i < listeJoueurs.size(); i++){
            box.add(listeJoueurs.get(i).getViewport());
            
        }
        scrollPane.setViewportView(box);
        scrollPane.getViewport().setBackground(Contenu.bg);  
        scrollPane.getViewport().setForeground(Contenu.yellow);
        this.add(scrollPane);
        scrollPane.getVerticalScrollBar().setBackground(Contenu.bg);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
            this.thumbColor = Color.WHITE;
            }});
        scrollPane.setBounds(Menuhome.WIDTH, effectif.getHeight() + 10,Contenu.WIDTH  -Menuhome.WIDTH  , Contenu.HEIGHT- (effectif.getHeight() + 10) - addPlayer.getHeight()*2);
    }
}