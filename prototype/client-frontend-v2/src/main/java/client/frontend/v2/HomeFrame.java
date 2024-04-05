package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import edu.ezip.ing1.pds.client.MainSelectClient;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class HomeFrame extends JFrame {

    private int WIDTH = 1000;
    private int HEIGHT = 600;
    

    public HomeFrame(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.gray);
        setSize(WIDTH,HEIGHT);

        
        CardLayout ca = new CardLayout();
        MainSelectClient msc = new MainSelectClient();
        
        JPanel contentPane = new JPanel(ca);
        contentPane.add("Effectif",new Effectif(this,msc));
        
        contentPane.add("Calendrier",new CalendarFrame(msc,this));
        contentPane.add("Stats", new PageStatistiques(msc));
        add(new MenuBar(ca,contentPane),BorderLayout.NORTH);
        add(contentPane,BorderLayout.CENTER);

        ca.show(contentPane,"Calendrier");



        
        this.pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
