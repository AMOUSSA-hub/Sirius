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
import java.awt.Toolkit;

import edu.ezip.ing1.pds.client.MainSelectClient;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class HomeFrame extends JFrame {

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private int WIDTH = (int)d.getWidth();
    private int HEIGHT = (int)d.getHeight();
    

    public HomeFrame(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.gray);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            this.setUndecorated(true); // Supprimer la décoration de la fenêtre (bordures, barre de titre, etc.)
            gd.setFullScreenWindow(this);
        } else {
            System.out.println("FullScreen not supported");
        }

        
        // Configuration de la taille de la fenêtre
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        //setSize(WIDTH,HEIGHT);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        
        CardLayout ca = new CardLayout();
        MainSelectClient msc = new MainSelectClient();
        
        JPanel contentPane = new JPanel(ca);
        contentPane.add("Effectif",new Effectif(this,msc));
        contentPane.add("Matchs",new FootballFormationFrame(this));
        contentPane.add("Accueil",new Accueil(this));
        contentPane.add("Calendrier",new CalendarFrame(msc,this));
        contentPane.add("Stats", new PageStatistiques(msc,this));
        add(new MenuBar(ca,contentPane),BorderLayout.NORTH);
        add(contentPane,BorderLayout.CENTER);

        ca.show(contentPane,"Calendrier");



        
        this.pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
