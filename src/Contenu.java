

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
//import javax.tools.Tool;

//import com.mysql.cj.xdevapi.Client;


public class Contenu extends JPanel implements MouseListener {

    JProgressBar progressBar = new JProgressBar();


    static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); //Recupere les dimensions de l'ecran

    static final int WIDTH = /*1050;*/ (int)(dimension.getWidth());
    static final int HEIGHT = /*600;*/ (int)(dimension.getHeight());
    static final int WIDTH_WITH_MENUHOME = WIDTH + Menuhome.WIDTH;


    static final Color yellow = new Color(240, 237, 64);
    static final Color bg = new Color(18, 18, 18);

    static final int BOUTON_WIDTH = WIDTH/5;
    static final int BOUTON_HEIGHT = HEIGHT/10;

    
    static CardLayout cardLayout = Fenetre.cardLayout;

    //static ClassLoader loader = Thread.currentThread().getContextClassLoader();
    ImageIcon clubManager = new Img().resizeImage(new ImageIcon(/*Contenu.class.getClassLoader().getResource*/(("./images/clubManagerTransparent.png"))));
    ImageIcon cmImage = new ImageIcon("./images/cmTransparent.png");

    Menuhome menuhome = new Menuhome();

    static JFrame f = Fenetre.f;

    JLabel cm = new JLabel(clubManager);

    Bouton entrer = new Bouton("Entrer");

    Contenu() {
        //this.setLayout(cardLayout);
        //System.out.println(clubManager.getIconWidth() + " , " + clubManager.getIconHeight());
        //System.out.println(WIDTH + " , " + HEIGHT);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        this.setVisible(true);
        this.setFocusable(true);
        this.setBackground(bg);

        this.add(cm);
        this.add(progressBar);

        progressBar.setValue(0);    //Initialise la barre a 0%
        progressBar.setStringPainted(false); //Rend invisinle le pourcentage de la barre  
        progressBar.setSize(cm.getIcon().getIconWidth(), 30);
        progressBar.setBackground(bg);
        progressBar.setForeground(yellow);
        

        
        cm.setBounds((WIDTH-cm.getIcon().getIconWidth())/2,(HEIGHT*2/5)-(cm.getIcon().getIconHeight())/2  ,cm.getIcon().getIconWidth(), cm.getIcon().getIconHeight());
        progressBar.setBounds((WIDTH - progressBar.getWidth())/2, cm.getIcon().getIconHeight(), progressBar.getWidth(), progressBar.getHeight());
        //this.add(menuhome);

        
        
    }


    public void chargeBarre()
    {
      int i=0; 
      while(i <= 100)
      {
        // remplit la barre
        progressBar.setValue(i);  
        i = i + 10;  
        try
        {
          // retarder le thread 
          Thread.sleep(200);
        }
        catch(Exception e){}
      }
    }

    public void enter() { //Entrer dans l'appli, cause la disparition de la barre de chargement 
      
      this.add(entrer);
      entrer.setBounds((WIDTH-BOUTON_WIDTH)/2, cm.getIcon().getIconHeight(), BOUTON_WIDTH, BOUTON_HEIGHT);
      entrer.addMouseListener(this);
      progressBar.setVisible(false);
    }




    @Override
    protected void paintComponent(java.awt.Graphics g1) {
        super.paintComponent(g1);

    }


    @Override
    public void mouseClicked(MouseEvent e) {
      if (e.getSource() == entrer ) {
        cardLayout.show(Fenetre.container, "Effectif");
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


}
