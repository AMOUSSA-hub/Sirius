import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Page extends JPanel  {
    
    static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); //Recupere les dimensions de l'ecran

    static final int WIDTH = /*1050;*/ (int)(dimension.getWidth());
    static final int HEIGHT = /*600;*/ (int)(dimension.getHeight());

    static final int BOUTON_WIDTH = WIDTH/5;
    static final int BOUTON_HEIGHT = HEIGHT/10;
    
    
    static final Color bg = Color.GRAY;//new Color(18, 18, 18);
    FontMetrics fontMetrics = getFontMetrics(LabelTxt.font);
    DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();

    
    //static Font font = new Font("Audiowide",Font.BOLD, 18);

    Bouton b = new Bouton("Insert");
    Bouton b2 = new Bouton("Select");
    Bouton entrer = new Bouton("Entrer");

    ImageIcon clubManager = (new ImageIcon("../images/clubManagerTransparent.png"));
    JLabel clubManagerLabel = new JLabel(clubManager);
    
    JProgressBar progressBar = new JProgressBar();

    

    Page(){

        this.setLayout(null);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        this.setVisible(true);
        this.setFocusable(true);
        this.setBackground(bg);


        //add(b).setBounds((WIDTH-300)/3,500,300,100);
        //add(b2).setBounds(900, 500, 300, 100);
        //b.addActionListener(this);
        //b2.addActionListener(this);

        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);


        progressBar.setValue(0);    //Initialise la barre a 0%
        progressBar.setStringPainted(false); //Rend invisinle le pourcentage de la barre  
        progressBar.setSize(clubManagerLabel.getIcon().getIconWidth(), 30);
        progressBar.setBackground(bg);
        progressBar.setForeground(Color.YELLOW);

        
        add(clubManagerLabel).setBounds((WIDTH-clubManager.getIconWidth())/2, (HEIGHT - clubManager.getIconHeight()) / 3, clubManager.getIconWidth(), clubManager.getIconHeight());
        add(progressBar).setBounds((WIDTH - progressBar.getWidth())/2, clubManagerLabel.getIcon().getIconHeight(), progressBar.getWidth(), progressBar.getHeight());
        repaint();


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




    protected void placeElement(int x, int y, Component component){
        add(component).setBounds((WIDTH-component.getWidth())/x,(HEIGHT-component.getHeight())/y,component.getWidth(),component.getHeight());
    }

   
    
}
