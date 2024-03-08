import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MenuBar extends JPanel  {

    public static final int WIDTH_Bouton = 255;
    public static final int HEIGHT_Bouton = 100;
    int x = 0;
    MenuBarButton accueil = new MenuBarButton( "Accueil");
    MenuBarButton effectif = new MenuBarButton("Effectif");
    MenuBarButton formation = new MenuBarButton("Formation");
    MenuBarButton upcomingMatch = new MenuBarButton("Match a venir");
    MenuBarButton stats = new MenuBarButton("Stats");
    MenuBarButton calendrier = new MenuBarButton("Calendrier");
    
    MenuBar( CardLayout layout, Container container) {
       
        this.setVisible(true);
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout());

        add(accueil);
        add(effectif);
        add(formation);
        add(upcomingMatch);
        add(stats);
        add(calendrier);

        accueil.addActionListener(e -> layout.show(container, "Accueil"));
        effectif.addActionListener(e -> layout.show(container, "Effectif"));
        formation.addActionListener(e -> layout.show(container, "Formation"));
        upcomingMatch.addActionListener(e -> layout.show(container, "Match a venir"));
        stats.addActionListener(e -> layout.show(container, "Stats"));
        calendrier.addActionListener(e -> layout.show(container, "Calendrier"));

    }



    
    
}