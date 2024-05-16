package client.frontend.v2;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Set;
import edu.ezip.ing1.pds.business.dto.*;

public class PageStatistiquesEquipe extends JFrame {

    public PageStatistiquesEquipe(Set<Stat> stats) {
        setTitle("Statistiques de l'équipe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ferme uniquement cette fenêtre

        JPanel panel = new JPanel(new GridLayout(4, 1)); // GridLayout avec 4 lignes et 1 colonne

        // Calcul des statistiques totales de l'équipe
        int totalButs = calculateTotalButs(stats);
        int totalPasses = calculateTotalPasses(stats);
        int totalCartonsJaunes = calculateTotalCartonsJaunes(stats);
        int totalCartonsRouges = calculateTotalCartonsRouges(stats);

        // Création des étiquettes pour afficher les statistiques
        JLabel labelButs = new JLabel("Total des buts: " + totalButs);
        JLabel labelPasses = new JLabel("Total des passes décisives: " + totalPasses);
        JLabel labelCartonsJaunes = new JLabel("Total des cartons jaunes: " + totalCartonsJaunes);
        JLabel labelCartonsRouges = new JLabel("Total des cartons rouges: " + totalCartonsRouges);

        // Définition de la police et de la taille de la police pour les étiquettes
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        for (JLabel label : Arrays.asList(labelButs, labelPasses, labelCartonsJaunes, labelCartonsRouges)) {
            label.setFont(labelFont);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Marge autour du texte
        }

        // Ajout des étiquettes au panneau
        panel.add(labelButs);
        panel.add(labelPasses);
        panel.add(labelCartonsJaunes);
        panel.add(labelCartonsRouges);

        // Personnalisation du panneau
        panel.setBackground(Color.WHITE); // Couleur de fond du panneau
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Bordure du panneau

        // Ajout du panneau à la fenêtre
        add(panel);

        // Ajustement de la taille et de la visibilité de la fenêtre
        setSize(400, 300); // Taille de la fenêtre
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
    }

    // Méthodes pour calculer les statistiques totales de l'équipe
    private int calculateTotalButs(Set<Stat> stats) {
        int totalButs = 0;
        for (Stat stat : stats) {
            totalButs += stat.getButs();
        }
        return totalButs;
    }

    private int calculateTotalPasses(Set<Stat> stats) {
        int totalPasses = 0;
        for (Stat stat : stats) {
            totalPasses += stat.getPassesDecisives();
        }
        return totalPasses;
    }

    private int calculateTotalCartonsJaunes(Set<Stat> stats) {
        int totalCartonsJaunes = 0;
        for (Stat stat : stats) {
            totalCartonsJaunes += stat.getCartonsJaunes();
        }
        return totalCartonsJaunes;
    }

    private int calculateTotalCartonsRouges(Set<Stat> stats) {
        int totalCartonsRouges = 0;
        for (Stat stat : stats) {
            totalCartonsRouges += stat.getCartonsRouges();
        }
        return totalCartonsRouges;
    }
}
