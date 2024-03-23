package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class HomeFrame extends JFrame {

    private int WIDTH = 1500;
    private int HEIGHT = 900;
    

    public HomeFrame(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.gray);
        setSize(WIDTH,HEIGHT);

        
        CardLayout ca = new CardLayout();
        
        JPanel contentPane = new JPanel(ca);
        contentPane.add("Effectif",new Effectif(this));
        contentPane.add("Calendrier",new CalendarFrame());
        //contentPane.add("Stats", new PageStatistiques());
        add(new MenuBar(ca,contentPane),BorderLayout.NORTH);
        add(contentPane,BorderLayout.CENTER);

        ca.show(contentPane,"Calendrier");



        setLocationRelativeTo(null);
        setVisible(true);
    }


}
