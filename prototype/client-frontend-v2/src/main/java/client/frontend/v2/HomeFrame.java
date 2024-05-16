package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

import edu.ezip.ing1.pds.client.MainSelectClient;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeFrame extends JFrame {

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private int WIDTH = (int)d.getWidth();
    private int HEIGHT = (int)d.getHeight();
    

    public HomeFrame(){
        MainSelectClient msc = new MainSelectClient();
        CalendarFrame calendarFrame = new CalendarFrame(msc,this);
        //PageStatistiques pageStatistiques = 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.gray);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            this.setUndecorated(true); // Supprimer la décoration de la fenêtre (bordures, barre de titre, etc.)
            //gd.setFullScreenWindow(this);
        } else {
            System.out.println("FullScreen not supported");
        }

        
        // Configuration de la taille de la fenêtre
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        //setSize(WIDTH,HEIGHT);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        
        CardLayout ca = new CardLayout();
        
        JPanel contentPane = new JPanel(ca);
 /*        JPanel logoPanel = new JPanel();
        InputStream inputStream = getClass().getResourceAsStream("/clubManagerTransparent.png");
        Image image = null;
        try {
            image = ImageIO.read(inputStream).getScaledInstance(500,500, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            System.err.println(e);
        }
        ImageIcon clubManager = new ImageIcon(image);
        contentPane.add(new JLabel(clubManager)); */
        contentPane.add("Effectif",new Effectif(this,msc));
        contentPane.add("Stats",new PageStatistiques(msc,this));
        contentPane.add("Matchs",new FootballFormationFrame(this,msc));
        contentPane.add("Accueil",new Accueil(this));
        contentPane.add("Calendrier",calendarFrame);

        add(new MenuBar(ca,contentPane),BorderLayout.NORTH);
        add(contentPane,BorderLayout.CENTER);
        ca.show(contentPane,"Accueil");



        
        this.pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
