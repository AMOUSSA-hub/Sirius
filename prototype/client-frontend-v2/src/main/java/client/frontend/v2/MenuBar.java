package client.frontend.v2;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MenuBar extends JPanel  {

    public static final int WIDTH_Bouton = 255;
    public static final int HEIGHT_Bouton = 100;
    int x = 0;
    MenuBarButton accueil = new MenuBarButton( "Accueil");
    MenuBarButton effectif = new MenuBarButton("Effectif");
    //MenuBarButton formation = new MenuBarButton("Formation");
    MenuBarButton upcomingMatch = new MenuBarButton("Matchs");
    MenuBarButton stats = new MenuBarButton("Stats");
    MenuBarButton calendrier = new MenuBarButton("Calendrier");
    MenuBarButton quit;
    
    MenuBar( CardLayout layout, Container container) {
       
        this.setVisible(true);
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout());

        try {
            this.quit = new MenuBarButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/on_off.png")).getScaledInstance(30,30, Image.SCALE_DEFAULT)));

        } catch (Exception e) {
            // TODO: handle exception
        }


        add(accueil);
        add(effectif);
        //add(formation);
        add(upcomingMatch);
        add(stats);
        add(calendrier);
        add(quit);
        accueil.addActionListener(e -> layout.show(container, "Accueil"));
        effectif.addActionListener(e -> layout.show(container, "Effectif"));
        ///formation.addActionListener(e -> layout.show(container, "Formation"));
        upcomingMatch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(container, "Matchs");
                FootballFormationFrame.refreshPlayersAvailable();
                FootballFormationFrame.refreshMatch();
            }
            
        });
        stats.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(container, "Stats");
            }
          
            
        });
        calendrier.addActionListener(e -> layout.show(container, "Calendrier"));
        quit.addActionListener(e -> System.exit(0));
    }



    
    
}