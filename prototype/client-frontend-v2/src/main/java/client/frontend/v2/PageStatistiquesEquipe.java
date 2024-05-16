package client.frontend.v2;
import javax.swing.*;
import java.awt.*;
import java.util.Set;
import edu.ezip.ing1.pds.business.dto.*;

public class PageStatistiquesEquipe extends JFrame {

    public PageStatistiquesEquipe(Set<Stat> stats) {
        setTitle("Statistiques de l'équipe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ferme uniquement cette fenêtre

        JPanel panel = new JPanel(new GridLayout(4, 2)); // GridLayout avec 4 lignes et 2 colonnes

        // Calcul des statistiques totales de l'équipe
        int totalButs = calculateTotalButs(stats);
        int totalPasses = calculateTotalPasses(stats);
        int totalCartonsJaunes = calculateTotalCartonsJaunes(stats);
        int totalCartonsRouges = calculateTotalCartonsRouges(stats);

        // Ajout des statistiques dans des étiquettes
        JLabel labelButs = new JLabel("Total des buts: " + totalButs);
        JLabel labelPasses = new JLabel("Total des passes décisives: " + totalPasses);
        JLabel labelCartonsJaunes = new JLabel("Total des cartons jaunes: " + totalCartonsJaunes);
        JLabel labelCartonsRouges = new JLabel("Total des cartons rouges: " + totalCartonsRouges);

        // Ajout des étiquettes au panneau
        panel.add(labelButs);
        panel.add(labelPasses);
        panel.add(labelCartonsJaunes);
        panel.add(labelCartonsRouges);

        // Ajout du panneau à la fenêtre
        add(panel);

        // Ajustement de la taille et de la visibilité de la fenêtre
        pack();
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        setVisible(true);
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
