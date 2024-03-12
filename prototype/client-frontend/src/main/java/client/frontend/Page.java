package client.frontend;

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
import java.io.InputStream;
import java.awt.Image;
import javax.imageio.ImageIO;

import java.io.IOException;


public class Page extends JPanel implements ActionListener {
    
    static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); //Recupere les dimensions de l'ecran

    static final int WIDTH = /*1050;*/ (int)(dimension.getWidth());
    static final int HEIGHT = /*600;*/ (int)(dimension.getHeight());

    static final int BOUTON_WIDTH = WIDTH/5;
    static final int BOUTON_HEIGHT = HEIGHT/10;
    
    static JFrame f = Fenetre.f;
    static final Color bg = Color.GRAY;//new Color(18, 18, 18);
    FontMetrics fontMetrics = getFontMetrics(LabelTxt.font);
    DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
    
    //static Font font = new Font("Audiowide",Font.BOLD, 18);

    Menuhome menuhome = new Menuhome();
    Bouton b = new Bouton("Insert");
    Bouton b2 = new Bouton("Select");
    Bouton entrer = new Bouton("Entrer");
    
    JProgressBar progressBar = new JProgressBar();

    String selectLinux = "./select.sh";
    String selectWin = ".\\select.bat";
    JLabel clubManagerLabel;
    ImageIcon clubManager;
    Page(){

      try {
        // Charger l'image depuis les ressources
        InputStream inputStream = getClass().getResourceAsStream("/clubManagerTransparent.png");
        Image image = ImageIO.read(inputStream);

        // Créer l'objet ImageIcon à partir de l'objet Image
        clubManager = new ImageIcon(image);
        clubManagerLabel = new JLabel(clubManager);

        // Utiliser clubManagerLabel ici...
    } catch (IOException ex) {
        ex.printStackTrace();
    }

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


        add(menuhome);
        menuhome.setVisible(false);
        
        add(clubManagerLabel).setBounds((WIDTH-clubManager.getIconWidth())/2, (HEIGHT - clubManager.getIconHeight()) / 3, clubManager.getIconWidth(), clubManager.getIconHeight());
        add(progressBar).setBounds((WIDTH - progressBar.getWidth())/2, clubManagerLabel.getIcon().getIconHeight(), progressBar.getWidth(), progressBar.getHeight());
        repaint();


    }



    public void chargeBarre()
    {
      int i=0; 
      while(i <= 80)
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
      progressBar.setValue(100);
    }

    public void enter() { //Entrer dans l'appli, cause la disparition de la barre de chargement 
      
      this.add(entrer);
      entrer.setBounds((WIDTH-BOUTON_WIDTH)/2, clubManagerLabel.getIcon().getIconHeight(), BOUTON_WIDTH, BOUTON_HEIGHT);
      entrer.addActionListener(this);
      progressBar.setVisible(false);
    }

    protected void removeAllExecptedMenuhome() {
        removeAll();
        add(menuhome);
        menuhome.setVisible(true);
    }

    protected void placeElement(int x, int y, Component component){
        add(component).setBounds((WIDTH-component.getWidth())/x,(HEIGHT-component.getHeight())/y,component.getWidth(),component.getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* String fichier = "";
        if (e.getSource() == b) {fichier = "./test.sh";}
        if (e.getSource() == b2) {fichier = "./select.sh";}
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(fichier);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("La commande s'est terminée avec le code de sortie : " + exitCode);
        } catch (Exception e1) {
            System.err.println(e1);
        } */
        if (e.getSource() == entrer) {
            remove(clubManagerLabel);//.setVisible(false);
            menuhome.setVisible(true);
            remove(entrer);
            repaint();
            Fenetre.cardLayout.show(Fenetre.container, "Effectif");
            remove(clubManagerLabel);
            repaint();
        }

    }
}
