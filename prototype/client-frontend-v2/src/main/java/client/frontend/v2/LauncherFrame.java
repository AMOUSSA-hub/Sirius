package client.frontend.v2;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

import java.io.InputStream;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;


public class LauncherFrame  extends JFrame{


    public LauncherFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.gray);
        setSize(1200,600);

        JLabel clubManagerLabel = new JLabel();
        ImageIcon clubManager;

        try {
            // Charger l'image depuis les ressources
            InputStream inputStream = getClass().getResourceAsStream("/clubManagerTransparent.png");
            Image image = ImageIO.read(inputStream).getScaledInstance(500,500, Image.SCALE_DEFAULT);
    
            // Créer l'objet ImageIcon à partir de l'objet Image
            clubManager = new ImageIcon(image);
            clubManagerLabel = new JLabel(clubManager);
    
            // Utiliser clubManagerLabel ici...
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        //add(new JLabel(new ImageIcon(new ImageIcon("client-front_V2/images/clubManagerTransparent.png").getImage().getScaledInstance(500,500, Image.SCALE_DEFAULT))),BorderLayout.CENTER);
        add(clubManagerLabel,BorderLayout.CENTER);
        JButton enterButton = new JButton("Entrer");
        enterButton.setPreferredSize(new Dimension(50,20));
        add(enterButton,BorderLayout.SOUTH);

        enterButton.addActionListener(e -> {this.dispose();new HomeFrame();});
        setLocationRelativeTo(null);
        setVisible(true);



    }
    
}
