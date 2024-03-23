package client.frontend.v2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class PageStatistiques extends JPanel {

    private DefaultTableModel modeleMeilleursButeurs;
    private DefaultTableModel modeleMeilleursPasseurs;

    public PageStatistiques() {
        

        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane onglets = new JTabbedPane();

        JPanel panneauMeilleursButeurs = new JPanel(new BorderLayout());
        panneauMeilleursButeurs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeleMeilleursButeurs = new DefaultTableModel(new Object[]{"Joueur", "Buts"}, 0);
        JTable tableauMeilleursButeurs = new JTable(modeleMeilleursButeurs);
        JScrollPane defilementMeilleursButeurs = new JScrollPane(tableauMeilleursButeurs);
        panneauMeilleursButeurs.add(defilementMeilleursButeurs, BorderLayout.CENTER);
        onglets.addTab("Meilleurs buteurs", panneauMeilleursButeurs);

        JPanel panneauMeilleursPasseurs = new JPanel(new BorderLayout());
        panneauMeilleursPasseurs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeleMeilleursPasseurs = new DefaultTableModel(new Object[]{"Joueur", "Passes décisives"}, 0);
        JTable tableauMeilleursPasseurs = new JTable(modeleMeilleursPasseurs);
        JScrollPane defilementMeilleursPasseurs = new JScrollPane(tableauMeilleursPasseurs);
        panneauMeilleursPasseurs.add(defilementMeilleursPasseurs, BorderLayout.CENTER);
        onglets.addTab("Meilleurs passeurs", panneauMeilleursPasseurs);

        JPanel panneauStatsEquipe = new JPanel(new BorderLayout());
        panneauStatsEquipe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel etiquetteStatsEquipe = new JLabel("Statistiques de l'équipe");
        etiquetteStatsEquipe.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetteStatsEquipe.setBorder(new EmptyBorder(10, 10, 10, 10));
        panneauStatsEquipe.add(etiquetteStatsEquipe, BorderLayout.NORTH);

        JPanel panneauLabelsStats = new JPanel(new GridLayout(0, 2));
        JLabel labelMatchsJoues = new JLabel("Matchs joués : 20");
        JLabel labelButsPour = new JLabel("Buts pour : 45");
        JLabel labelButsContre = new JLabel("Buts contre : 30");
        JLabel labelCartonsJaunes = new JLabel("Cartons jaunes : 10");
        JLabel labelCartonsRouges = new JLabel("Cartons rouges : 2");
        JLabel labelSerie = new JLabel("Série en cours : VVNVNV");
        panneauLabelsStats.add(labelMatchsJoues);
        panneauLabelsStats.add(new JLabel());
        panneauLabelsStats.add(labelButsPour);
        panneauLabelsStats.add(new JLabel());
        panneauLabelsStats.add(labelButsContre);
        panneauLabelsStats.add(new JLabel());
        panneauLabelsStats.add(labelCartonsJaunes);
        panneauLabelsStats.add(new JLabel());
        panneauLabelsStats.add(labelCartonsRouges);
        panneauLabelsStats.add(new JLabel());
        panneauLabelsStats.add(labelSerie);
        panneauLabelsStats.add(new JLabel());
        panneauStatsEquipe.add(panneauLabelsStats, BorderLayout.CENTER);
        onglets.addTab("Statistiques de l'équipe", panneauStatsEquipe);

        panel.add(onglets, BorderLayout.CENTER);

        ArrayList<Joueur> joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Lionel Messi"));
        joueurs.add(new Joueur("Cristiano Ronaldo"));
        joueurs.add(new Joueur("Neymar Jr."));
        joueurs.add(new Joueur("Kylian Mbappé"));
        joueurs.add(new Joueur("Robert Lewandowski"));

        Random aleatoire = new Random();
        for (Joueur joueur : joueurs) {
            joueur.setButs(aleatoire.nextInt(20));
            joueur.setPassesDecisives(aleatoire.nextInt(15));
        }

        miseAJourTableauMeilleursButeurs(joueurs);
        miseAJourTableauMeilleursPasseurs(joueurs);

        add(panel);

        setVisible(true);
    }

    private void miseAJourTableauMeilleursButeurs(ArrayList<Joueur> joueurs) {
        modeleMeilleursButeurs.setRowCount(0);
        joueurs.sort(Comparator.comparingInt(Joueur::getButs).reversed());
        for (Joueur joueur : joueurs) {
            modeleMeilleursButeurs.addRow(new Object[]{joueur.getNom(), joueur.getButs()});
        }
    }

    private void miseAJourTableauMeilleursPasseurs(ArrayList<Joueur> joueurs) {
        modeleMeilleursPasseurs.setRowCount(0);
        joueurs.sort(Comparator.comparingInt(Joueur::getPassesDecisives).reversed());
        for (Joueur joueur : joueurs) {
            modeleMeilleursPasseurs.addRow(new Object[]{joueur.getNom(), joueur.getPassesDecisives()});
        }
    }

    private class Joueur {
        private String nom;
        private int buts;
        private int passesDecisives;

        public Joueur(String nom) {
            this.nom = nom;
        }

        public String getNom() {
            return nom;
        }

        public int getButs() {
            return buts;
        }

        public void setButs(int buts) {
            this.buts = buts;
        }

        public int getPassesDecisives() {
            return passesDecisives;
        }

        public void setPassesDecisives(int passesDecisives) {
            this.passesDecisives = passesDecisives;
        }
    }

    public static void main(String[] args) {
        new PageStatistiques(); 
    }
}
