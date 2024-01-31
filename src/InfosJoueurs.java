import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;



//Penser a faire une box honrizontale composee de 2,3 box honrizontales afin de pouvoir ajouter bcp plus de donnees de joueurs 

public class InfosJoueurs extends JScrollPane implements MouseListener  {

    JFrame f = Fenetre.f;
    static CardLayout cardLayout = Fenetre.cardLayout;
    
    Font font = new Font("Audiowide",Font.BOLD, 18);
    Box box = new Box(BoxLayout.X_AXIS);
    Box box2 = new Box(BoxLayout.Y_AXIS);
    Box box3 = new Box(BoxLayout.Y_AXIS);
    Box box4 = new Box(BoxLayout.Y_AXIS); 

    Bouton voirPlus = new Bouton("Voir Plus");
    Bouton modifierJoueur =new Bouton("Modifier");
    JLabel img = new LabelTxt();
    JLabel prenom = new LabelTxt();
    JLabel nom = new LabelTxt();
    JLabel age = new LabelTxt();
    JLabel poste = new LabelTxt();
    JLabel pied = new LabelTxt();
    JLabel maillot = new LabelTxt();
    JLabel taille = new LabelTxt();
    Image image;
    int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    
    static JLabel detailsImg = new JLabel();

    InfosJoueurs(String url,String nom,String prenom, int age, String poste, String pied,int maillot,int taille ) {
        voirPlus.setBorder(new RoundBtn(20));
        modifierJoueur.setBorder(new RoundBtn(20));
        img.setIcon(new ImageIcon(url));
        image = new ImageIcon(url).getImage().getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH);
        detailsImg.setIcon(new ImageIcon(image));
        //this.prenom.setFont(font);
        this.nom.setFont(font);
        this.age.setFont(font);
        this.poste.setFont(font);
        this.pied.setFont(font);
        this.prenom.setText(prenom + " ");
        this.nom.setText( nom + " ");
        this.age.setText("Age : " +String.valueOf(age) );
        this.poste.setText(poste);
        this.pied.setText(pied);
        this.maillot.setText("#"+String.valueOf(maillot));
        this.taille.setText(String.valueOf(taille) + " cm");
        box.add(img);
        
        //box.add(this.prenom);
        //box.add(this.nom);
        box.add(box2);
        //box.add(new Box(BoxLayout.Y_AXIS));
        box.add(Box.createGlue());
        box.add(box3);

        box2.add(this.prenom);
        box2.add(this.nom);
        box2.add(this.age);

        box3.add(this.maillot);
        box3.add(this.poste);
        box3.add(new LabelTxt("Pied fort : " + this.pied.getText()));
        
        box.add(Box.createGlue());
        box.add(Box.createGlue());
        box.add(Box.createGlue());
        box.add(box4);
        box4.add(this.taille);
        box.add(Box.createGlue());
        box.add(Box.createGlue());
        box.add(Box.createGlue());


        box.add(modifierJoueur);
        box.add(voirPlus);

        
        box.setBackground(Contenu.bg);
        box.setBorder(new RoundBtn(1));
        box2.setBorder(new RoundBtn(1));
        box3.setBorder(new RoundBtn(1));
        this.setBackground(Contenu.bg);
        this.setViewportView(box);
        //this.setViewportView(box2);
        
        this.getViewport().setBackground(Contenu.bg);  
        this.getViewport().setForeground(Contenu.yellow);
        //this.getViewport().setLayout(null);
        this.getViewport().setPreferredSize(new Dimension(0, 200)); //Met la taille des box de joueurs, le 0 en largeur n est pas pris en compte car la box prend la largeur de la fenetre ou du Jpanel auquel la box a été ajoutée
        //System.out.println(box.getWidth()); 
        //this.getViewport().setBounds(0, 0, 100, 1000);
        //this.getViewport().add()
        //JScrollPane scrollPane = new JScrollPane();
        //scrollPane.createVerticalScrollBar();
        //this.add(scrollPane);
        voirPlus.addMouseListener(this);
        modifierJoueur.addMouseListener(this);
        System.out.println(taille);

    }





    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == voirPlus) {
            Fenetre.container.add("Voir plus",new Details(this));
            cardLayout.show(Fenetre.container, "Voir plus");
        }
         if(e.getSource() == modifierJoueur) {
            
            String[] options = {"Nom","Prenom","Age","Numero","Poste","Pied fort"};
            int x = JOptionPane.showOptionDialog(null,"Que voulez vous modifier ?","Modification",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
           
            String nouveau = JOptionPane.showInputDialog("Par quoi le remplacer ?");
            int ifInt = 0;
            ifInt = refresh(x, options, nouveau, ifInt);            

        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = App.connectBDD();
            Statement stmt = conn.createStatement();
            String sql;
             if (options[x] == "Age" || options[x] == "Numero"){ sql = "Update joueurs set " + options[x] + "= " + ifInt + " where id = " + this.getId();System.out.println(sql);}
            else {
                sql = "Update joueurs set " + options[x] + "= '" + nouveau + "' where id = " + this.getId();
            }
            stmt.executeUpdate(sql);
            conn.close();

        }
        catch(Exception exce){ 
            System.out.println(exce);
        }
        
        
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

    private int refresh(int x,String[] options,String nouveau, int ifInt){
            if (options[x] == "Nom"){
                this.nom.setText(nouveau.toUpperCase() + " ");
            }
            if (options[x] == "Prenom"){
                this.prenom.setText(nouveau+ " ");
            }
            

            if (options[x] == "Age"){
                ifInt = Integer.parseInt(nouveau);
                System.out.println(" fijj : " + ifInt);
                this.age.setText("Age : " + ifInt+" ");
                System.out.println(this.getId());
            }

            if (options[x] == "Numero"){
                ifInt = Integer.parseInt(nouveau);
                this.maillot.setText(nouveau+" ");
            }
             if (options[x] == "Poste"){
                this.poste.setText(nouveau+" ");
            }
             if (options[x] == "Pied fort"){
                this.pied.setText(nouveau+" ");
            }
            return ifInt;

    }
    

    

}