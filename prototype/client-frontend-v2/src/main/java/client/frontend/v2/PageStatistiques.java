package client.frontend.v2;

import edu.ezip.ing1.pds.client.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;
import edu.ezip.ing1.pds.business.dto.*;

public class PageStatistiques extends JPanel {

    private DefaultTableModel modeleButeurs;
    private DefaultTableModel modelePasseurs;
    private DefaultTableModel tablecartonsjaunes;
    private DefaultTableModel tablecartonsrouges;
    private DefaultTableModel tablenote;
    private DefaultTableModel tablemin;
    private HashMap<Integer, Player> playerHashMap;
    private PageStatistiquesEquipe pageStatistiquesEquipe;

    public PageStatistiques(MainSelectClient msc, JFrame fen) {
        Set<Stat> stats = null;
        playerHashMap = Effectif.getPlayerNameHashMap();

        try {
            stats = msc.getAllStats();
            for (Stat s : stats) {
                System.out.println(s.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new BorderLayout());
        setBackground(Color.gray);

        JTabbedPane onglets = new JTabbedPane();

        JPanel panneauMeilleursButeurs = new JPanel(new BorderLayout());
        panneauMeilleursButeurs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeleButeurs = new DefaultTableModel(new Object[]{"Joueur", "Buts"}, 0);
        JTable tableauMeilleursButeurs = new JTable(modeleButeurs);
        JScrollPane defilementMeilleursButeurs = new JScrollPane(tableauMeilleursButeurs);
        panneauMeilleursButeurs.add(defilementMeilleursButeurs, BorderLayout.CENTER);
        onglets.addTab("Meilleurs buteurs", panneauMeilleursButeurs);

        JPanel panneauMeilleursPasseurs = new JPanel(new BorderLayout());
        panneauMeilleursPasseurs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modelePasseurs = new DefaultTableModel(new Object[]{"Joueur", "Passes decisives"}, 0);
        JTable tableauMeilleursPasseurs = new JTable(modelePasseurs);
        JScrollPane defilementMeilleursPasseurs = new JScrollPane(tableauMeilleursPasseurs);
        panneauMeilleursPasseurs.add(defilementMeilleursPasseurs, BorderLayout.CENTER);
        onglets.addTab("Meilleurs passeurs", panneauMeilleursPasseurs);

        JPanel panelcartonsjaunes = new JPanel(new BorderLayout());
        panelcartonsjaunes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablecartonsjaunes = new DefaultTableModel(new Object[]{"Joueur", "Cartons jaunes"}, 0);
        JTable tableaucartonsjaunes = new JTable(tablecartonsjaunes);
        JScrollPane defilementcartonsjaunes = new JScrollPane(tableaucartonsjaunes);
        panelcartonsjaunes.add(defilementcartonsjaunes, BorderLayout.CENTER);
        onglets.addTab("Cartons jaunes", panelcartonsjaunes);

        JPanel panelcartonsrouges = new JPanel(new BorderLayout());
        panelcartonsrouges.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablecartonsrouges = new DefaultTableModel(new Object[]{"Joueur", "Cartons rouges"}, 0);
        JTable tableaucartonsrouges = new JTable(tablecartonsrouges);
        JScrollPane defilementcartonsrouge = new JScrollPane(tableaucartonsrouges);
        panelcartonsrouges.add(defilementcartonsrouge, BorderLayout.CENTER);
        onglets.addTab("Cartons rouges", panelcartonsrouges);

        JPanel panelnote = new JPanel(new BorderLayout());
        panelnote.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablenote = new DefaultTableModel(new Object[]{"Joueur", "Note"}, 0);
        JTable tableaunote = new JTable(tablenote);
        JScrollPane defilementnote = new JScrollPane(tableaunote);
        panelnote.add(defilementnote, BorderLayout.CENTER);
        onglets.addTab("Note", panelnote);

        JPanel panelmin = new JPanel(new BorderLayout());
        panelmin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablemin = new DefaultTableModel(new Object[]{"Joueur", "Minutes jouées"}, 0);
        JTable tableaumin = new JTable(tablemin);
        JScrollPane defilementmin = new JScrollPane(tableaumin);
        panelmin.add(defilementmin, BorderLayout.CENTER);
        onglets.addTab("Minutes jouées", panelmin);

        // Ajout du bouton pour choisir
        JComboBox<String> choixStatistiques = new JComboBox<>(new String[]{"Statistiques des joueurs", "Statistiques de l'équipe"});
        panel.add(choixStatistiques, BorderLayout.SOUTH);

        // Initialisation de la page des statistiques de l'équipe
        pageStatistiquesEquipe = new PageStatistiquesEquipe(stats);

        // Ajout de l'actionListener pour le choix des statistiques
        choixStatistiques.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choix = (String) choixStatistiques.getSelectedItem();
                if (choix.equals("Statistiques des joueurs")) {
                    // Afficher les statistiques des joueurs
                    setVisible(true);
                    pageStatistiquesEquipe.setVisible(false);
                } else {
                    // Afficher les statistiques de l'équipe
                    setVisible(false);
                    pageStatistiquesEquipe.setVisible(true);
                }
            }
        });

        // Ajout du bouton pour ajouter des statistiques
        Bouton addStatsButton = new Bouton("Ajouter des statistiques");
        panel.add(addStatsButton, BorderLayout.NORTH);

        // ActionListener pour le bouton "Ajouter des statistiques"
        addStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Création d'une liste de noms de joueurs disponibles
                String[] nomsJoueurs = new String[playerHashMap.size()];
                int i = 0;
                for (Player joueur : playerHashMap.values()) {
                    nomsJoueurs[i++] = joueur.getPrenom() + " " + joueur.getNom();
                }

                // Affichage de la boîte de dialogue de sélection du joueur
                String joueurSelectionne = (String) JOptionPane.showInputDialog(
                        fen, "Sélectionnez le joueur :", "Sélection du joueur",
                        JOptionPane.QUESTION_MESSAGE, null, nomsJoueurs, nomsJoueurs[0]);

                // Si aucun joueur n'est sélectionné, ne rien faire
                if (joueurSelectionne == null) {
                    return;
                }

                // Récupération de l'ID du joueur sélectionné
                int idJoueurSelectionne = 0;
                int idMatchSelectionne = 1;
                for (Player joueur : playerHashMap.values()) {
                    if ((joueur.getPrenom() + " " + joueur.getNom()).equals(joueurSelectionne)) {
                        idJoueurSelectionne = joueur.getId();
                        break;
                    }
                }

                // Demande des informations à l'utilisateur via des dialogues de saisie
                short but = Short.parseShort(JOptionPane.showInputDialog("Entrez le nombre de buts:"));
                short passeDecisive = Short.parseShort(JOptionPane.showInputDialog("Entrez le nombre de passes décisives:"));
                short cartonsJaunes = Short.parseShort(JOptionPane.showInputDialog("Entrez le nombre de cartons jaunes:"));
                short cartonsRouges = Short.parseShort(JOptionPane.showInputDialog("Entrez le nombre de cartons rouges:"));
                short note = Short.parseShort(JOptionPane.showInputDialog("Entrez la note:"));
                int minutesJouees = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nombre de minutes jouées:"));

                // Création de l'objet Stat avec les valeurs saisies
                Stat nouvelleStat = new Stat();
                nouvelleStat.setIdJoueurs((short) idJoueurSelectionne);
                nouvelleStat.setButs(but);
                nouvelleStat.setPassesDecisives(passeDecisive);
                nouvelleStat.setCartonsJaunes(cartonsJaunes);
                nouvelleStat.setCartonsRouges(cartonsRouges);
                nouvelleStat.setNoteDuMatch(note);
                nouvelleStat.setMinutesJouees((short) minutesJouees);
                nouvelleStat.setIdMatchs((short)idMatchSelectionne);

                // Envoyer la requête pour insérer la nouvelle statistique
                MainInsertClient.sendRequest(nouvelleStat, "INSERT_STATS");

                // Ajoute les informations saisies dans les tableaux correspondants
                modeleButeurs.addRow(new Object[]{joueurSelectionne, but});
                modelePasseurs.addRow(new Object[]{joueurSelectionne, passeDecisive});
                tablecartonsjaunes.addRow(new Object[]{joueurSelectionne, cartonsJaunes});
                tablecartonsrouges.addRow(new Object[]{joueurSelectionne, cartonsRouges});
                tablenote.addRow(new Object[]{joueurSelectionne, note});
                tablemin.addRow(new Object[]{joueurSelectionne, minutesJouees});
            }
        });

        panel.add(onglets);

        Buteurs(stats);
        Passeurs(stats);
        Cartonsjaunes(stats);
        CartonsRouges(stats);
        Notedumatch(stats);
        Minutesjouees(stats);

        this.setLayout(new GridLayout(1, 1));
        add(panel);

        setVisible(true);
    }

    private void Buteurs(Set<Stat> stats) {
        modeleButeurs.setRowCount(0);
        for (Stat stat : stats) {
            modeleButeurs.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getButs()});
        }
    }

    private void Passeurs(Set<Stat> stats) {
        modelePasseurs.setRowCount(0);
        for (Stat stat : stats) {
            modelePasseurs.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getPassesDecisives()});
        }
    }

    private void Cartonsjaunes(Set<Stat> stats) {
        tablecartonsjaunes.setRowCount(0);
        for (Stat stat : stats) {
            tablecartonsjaunes.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getCartonsJaunes()});
        }
    }

   
    private void CartonsRouges(Set<Stat> stats) {
        tablecartonsrouges.setRowCount(0);
        for (Stat stat : stats) {
            tablecartonsrouges.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getCartonsRouges()});
        }
    }
    
    private void Notedumatch(Set<Stat> stats) {
        tablenote.setRowCount(0);
        for (Stat stat : stats) {
            tablenote.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getNoteDuMatch()});
        }
    }
    
    private void Minutesjouees(Set<Stat> stats) {
        tablemin.setRowCount(0);
        for (Stat stat : stats) {
            tablemin.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getMinutesJouees()});
        }
    }
    
    private String getNamesPlayer(int id_joueur) {
        Player player = playerHashMap.get(id_joueur);
        return (player.getPrenom() + " " + player.getNom());
    }
    }
    