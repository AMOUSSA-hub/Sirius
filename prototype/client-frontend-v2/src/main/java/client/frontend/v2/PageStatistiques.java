package client.frontend.v2;

import edu.ezip.ing1.pds.client.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    private HashMap<Integer, Game> matchHashMap;
    private PageStatistiquesEquipe pageStatistiquesEquipe;
    public static Set<Stat> stats;
    private HashSet<Integer> joueursAvecStatistiques;
    private int idMatchSelectionneGlobal; // Variable d'instance pour stocker l'ID du match sélectionné

    public PageStatistiques(MainSelectClient msc, JFrame fen) {
        stats = null;
        playerHashMap = Effectif.getPlayerNameHashMap();
        matchHashMap = FootballFormationFrame.getMatchHashMap(); // la clé c'est l id du match et la valeur associé c'est le match de type Game
        System.out.println("blallaalalallalalalallalaaaaaaaaaaaa " + matchHashMap.toString());
        joueursAvecStatistiques = new HashSet<>();
        idMatchSelectionneGlobal = 0; // Initialisation de l'ID du match sélectionné à 0

        try {
            stats = Mastermind.getStatsSet();
            for (Stat s : stats) {
                joueursAvecStatistiques.add((int) s.getIdJoueurs());
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
        onglets.addTab("Buts", panneauMeilleursButeurs);

        JPanel panneauMeilleursPasseurs = new JPanel(new BorderLayout());
        panneauMeilleursPasseurs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modelePasseurs = new DefaultTableModel(new Object[]{"Joueur", "Passes decisives"}, 0);
        JTable tableauMeilleursPasseurs = new JTable(modelePasseurs);
        JScrollPane defilementMeilleursPasseurs = new JScrollPane(tableauMeilleursPasseurs);
        panneauMeilleursPasseurs.add(defilementMeilleursPasseurs, BorderLayout.CENTER);
        onglets.addTab("Passe decisives", panneauMeilleursPasseurs);

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
        pageStatistiquesEquipe.setVisible(false);

        // Ajout de l'actionListener pour le choix des statistiques
        choixStatistiques.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choix = (String) choixStatistiques.getSelectedItem();
                if (choix.equals("Statistiques de l'équipe")) {
                    // Afficher les statistiques de l'équipe
                    pageStatistiquesEquipe.setVisible(true);
                }
            }
        });

        // Ajout du bouton pour sélectionner un match
        Bouton selectMatchButton = new Bouton("Sélectionner un match");
        panel.add(selectMatchButton, BorderLayout.WEST);

        // Ajout du bouton pour ajouter des statistiques
        Bouton addStatsButton = new Bouton("Modification des statistiques");
        panel.add(addStatsButton, BorderLayout.NORTH);

// ActionListener pour le bouton "Sélectionner un match"
selectMatchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Récupérer les noms des matchs disponibles
        String[] nomsMatchs = getMatchNames();

        // Affichage de la boîte de dialogue de sélection du match
        String matchSelectionne = (String) JOptionPane.showInputDialog(
                fen, "Sélectionnez le match :", "Sélection du match",
                JOptionPane.QUESTION_MESSAGE, null, nomsMatchs, nomsMatchs[0]);

        // Si aucun match n'est sélectionné, ne rien faire
        if (matchSelectionne == null) {
            return;
        }

        // Récupération de l'ID du match sélectionné
        int idMatchSelectionne = 0;
        for (Game match : matchHashMap.values()) {
            if (match.getOpponent().equals(matchSelectionne)) {
                idMatchSelectionne = match.getId_Match();
                break;
            }
        }

        // Enregistrer l'ID du match sélectionné dans une variable d'instance
        idMatchSelectionneGlobal = idMatchSelectionne;

        // Afficher un message de confirmation
        JOptionPane.showMessageDialog(fen, "Match sélectionné : " + matchSelectionne, "Information", JOptionPane.INFORMATION_MESSAGE);

        // Mettre à jour les statistiques en fonction du match sélectionné
        Buteurs(stats,idMatchSelectionne);
        Passeurs(stats, idMatchSelectionne);
        Cartonsjaunes(stats, idMatchSelectionne);
        CartonsRouges(stats, idMatchSelectionne);
        Notedumatch(stats, idMatchSelectionne);
        Minutesjouees(stats, idMatchSelectionne);
    }
});


        // ActionListener pour le bouton "Ajouter des statistiques"
// ActionListener pour le bouton "Ajouter des statistiques"
addStatsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Récupérer les noms des joueurs sans statistiques
        String[] nomsJoueurs = getPlayerNamesWithNoStats();

        // Si aucun joueur sans statistiques n'est disponible, afficher un message et retourner
        if (nomsJoueurs.length == 0) {
            JOptionPane.showMessageDialog(fen, "Tous les joueurs ont déjà des statistiques pour ce match.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
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

        // Vérification des valeurs saisies
        if (but < 0 || but > 10) {
            JOptionPane.showMessageDialog(fen, "Le nombre de buts doit être compris entre 0 et 10.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (passeDecisive < 0 || passeDecisive > 10) {
            JOptionPane.showMessageDialog(fen, "Le nombre de passes décisives doit être compris entre 0 et 10.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cartonsJaunes < 0 || cartonsJaunes > 3) {
            JOptionPane.showMessageDialog(fen, "Le nombre de cartons jaunes doit être compris entre 0 et 3.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cartonsRouges < 0 || cartonsRouges > 1) {
            JOptionPane.showMessageDialog(fen, "Le nombre de cartons rouges doit être compris entre 0 et 1.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (note < 0 || note > 10) {
            JOptionPane.showMessageDialog(fen, "La note doit être comprise entre 0 et 10.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (minutesJouees < 0 || minutesJouees > 120) {
            JOptionPane.showMessageDialog(fen, "Le nombre de minutes jouées doit être compris entre 0 et 120.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérification que l'utilisateur a bien sélectionné un match
        if (idMatchSelectionneGlobal == 0) {
            JOptionPane.showMessageDialog(fen, "Veuillez sélectionner un match d'abord.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Création de l'objet Stat avec les valeurs saisies
        Stat nouvelleStat = new Stat();
        nouvelleStat.setIdJoueurs((short) idJoueurSelectionne);
        nouvelleStat.setButs(but);
        nouvelleStat.setPassesDecisives(passeDecisive);
        nouvelleStat.setCartonsJaunes(cartonsJaunes);
        nouvelleStat.setCartonsRouges(cartonsRouges);
        nouvelleStat.setNoteDuMatch(note);
        nouvelleStat.setMinutesJouees((short) minutesJouees);
        nouvelleStat.setIdMatchs((short) idMatchSelectionneGlobal);

        // Envoyer la requête pour insérer la nouvelle statistique
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("buts", nouvelleStat.getButs());
        hashMap.put("passesdecisives", nouvelleStat.getPassesDecisives());
        hashMap.put("cartonsjaunes", nouvelleStat.getCartonsJaunes());
        hashMap.put("cartonsrouges", nouvelleStat.getCartonsRouges());
        hashMap.put("notedumatch", nouvelleStat.getNoteDuMatch());
        hashMap.put("minutesjouees", nouvelleStat.getMinutesJouees());
        MainInsertClient.updateRequestStats(nouvelleStat, hashMap, "UPDATE_STATS");

        // Ajoute les informations saisies dans les tableaux correspondants
        removeRow(modeleButeurs, joueurSelectionne);
        removeRow(modelePasseurs, joueurSelectionne);
        removeRow(tablecartonsjaunes, joueurSelectionne);
        removeRow(tablecartonsrouges, joueurSelectionne);
        removeRow(tablenote, joueurSelectionne);
        removeRow(tablemin, joueurSelectionne);

        modeleButeurs.addRow(new Object[]{joueurSelectionne, but});
        modelePasseurs.addRow(new Object[]{joueurSelectionne, passeDecisive});
        tablecartonsjaunes.addRow(new Object[]{joueurSelectionne, cartonsJaunes});
        tablecartonsrouges.addRow(new Object[]{joueurSelectionne, cartonsRouges});
        tablenote.addRow(new Object[]{joueurSelectionne, note});
        tablemin.addRow(new Object[]{joueurSelectionne, minutesJouees});

        // Ajouter l'ID du joueur aux joueurs avec des statistiques
        joueursAvecStatistiques.add(idJoueurSelectionne);

        // Mettre à jour la liste des joueurs sans statistiques
        updatePlayerListWithoutStats();
    }
});

        panel.add(onglets);

        Buteurs(stats, idMatchSelectionneGlobal);
        Passeurs(stats,idMatchSelectionneGlobal);
        Cartonsjaunes(stats,idMatchSelectionneGlobal);
        CartonsRouges(stats,idMatchSelectionneGlobal);
        Notedumatch(stats,idMatchSelectionneGlobal);
        Minutesjouees(stats,idMatchSelectionneGlobal);

        this.setLayout(new GridLayout(1, 1));
        add(panel);

        setVisible(true);
    }
// Méthode pour obtenir les noms de tous les matchs
private String[] getMatchNames() {
    ArrayList<String> nomsMatchs = new ArrayList<>();
    for (Game match : matchHashMap.values()) {
        nomsMatchs.add(match.getOpponent());
    }
    return nomsMatchs.toArray(new String[0]);
}

private int findRowByName(DefaultTableModel model, String name) {
    for (int i = 0; i < model.getRowCount(); i++) {
        if (name.equals(model.getValueAt(i, 0))) {
            return i;  // Retourne l'index de la ligne trouvée
        }
    }
    return -1;  // Retourne -1 si le nom n'est pas trouvé
}

private void removeRow(DefaultTableModel model,String name){
    model.removeRow(findRowByName(model, name));
}


    // Mettre à jour la liste des joueurs sans statistiques après l'ajout de nouvelles statistiques
    private void updatePlayerListWithoutStats() {
        ArrayList<String> nomsJoueursSansStats = new ArrayList<>();
        for (Player joueur : playerHashMap.values()) {
            // Ajoutez uniquement les joueurs qui n'ont pas encore de statistiques et qui ne sont pas dans la liste des joueurs avec des statistiques
            if (!joueursAvecStatistiques.contains(joueur.getId())) {
                nomsJoueursSansStats.add(joueur.getPrenom() + " " + joueur.getNom());
            }
        }

        // Trouver le JComboBox choixStatistiques
        JComboBox<String> choixStatistiques = null;
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof JComboBox) {
                choixStatistiques = (JComboBox<String>) component;
                break;
            }
        }

        if (choixStatistiques != null) {
            choixStatistiques.setModel(new DefaultComboBoxModel<>(nomsJoueursSansStats.toArray(new String[0])));
        } else {
            System.err.println("JComboBox choixStatistiques non trouvé !");
        }
    }

    // Obtenir les noms des joueurs qui n'ont pas de statistiques
    private String[] getPlayerNamesWithNoStats() {
        ArrayList<String> nomsJoueursSansStats = new ArrayList<>();
        for (Player joueur : playerHashMap.values()) {
                nomsJoueursSansStats.add(joueur.getPrenom() + " " + joueur.getNom());
            
        }
        return nomsJoueursSansStats.toArray(new String[0]);
    }
    private Set<Stat> filterStatsByMatchId(Set<Stat> stats, int matchId) {
        Set<Stat> filteredStats = new HashSet<>();
        for (Stat stat : stats) {
            if (stat.getIdMatchs() == matchId) {
                filteredStats.add(stat);
            }
        }
        return filteredStats;
    }

    private void Buteurs(Set<Stat> stats, int matchId) {
        modeleButeurs.setRowCount(0);
        Set<Stat> filteredStats = filterStatsByMatchId(stats, matchId);
        for (Stat stat : filteredStats) {
            modeleButeurs.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getButs()});
        }
    }
    
    private void Passeurs(Set<Stat> stats, int matchId) {
        modelePasseurs.setRowCount(0);
        Set<Stat> filteredStats = filterStatsByMatchId(stats, matchId);
        for (Stat stat : filteredStats) {
            modelePasseurs.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getPassesDecisives()});
        }
    }
    
    private void Cartonsjaunes(Set<Stat> stats, int matchId) {
        tablecartonsjaunes.setRowCount(0);
        Set<Stat> filteredStats = filterStatsByMatchId(stats, matchId);
        for (Stat stat : filteredStats) {
            tablecartonsjaunes.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getCartonsJaunes()});
        }
    }
    
    private void CartonsRouges(Set<Stat> stats, int matchId) {
        tablecartonsrouges.setRowCount(0);
        Set<Stat> filteredStats = filterStatsByMatchId(stats, matchId);
        for (Stat stat : filteredStats) {
            tablecartonsrouges.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getCartonsRouges()});
        }
    }
    
    private void Notedumatch(Set<Stat> stats, int matchId) {
        tablenote.setRowCount(0);
        Set<Stat> filteredStats = filterStatsByMatchId(stats, matchId);
        for (Stat stat : filteredStats) {
            tablenote.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getNoteDuMatch()});
        }
    }
    
    private void Minutesjouees(Set<Stat> stats, int matchId) {
        tablemin.setRowCount(0);
        Set<Stat> filteredStats = filterStatsByMatchId(stats, matchId);
        for (Stat stat : filteredStats) {
            tablemin.addRow(new Object[]{getNamesPlayer(stat.getIdJoueurs()), stat.getMinutesJouees()});
        }
    }
    

    private String getNamesPlayer(int id_joueur) {
        Player player = playerHashMap.get(id_joueur);
        return (player.getPrenom() + " " + player.getNom());
    }
    // Méthode pour obtenir les noms des joueurs sans statistiques pour le match sélectionné
private String[] getPlayerNamesWithNoStatsForMatch(int matchId) {
    ArrayList<String> nomsJoueursSansStats = new ArrayList<>();
    Set<Stat> statsForMatch = filterStatsByMatchId(stats, matchId);

    HashSet<Integer> joueursAvecStatsPourMatch = new HashSet<>();
    for (Stat stat : statsForMatch) {
        joueursAvecStatsPourMatch.add((int) stat.getIdJoueurs());
    }

    for (Player joueur : playerHashMap.values()) {
        if (!joueursAvecStatsPourMatch.contains(joueur.getId())) {
            nomsJoueursSansStats.add(joueur.getPrenom() + " " + joueur.getNom());
        }
    }
    return nomsJoueursSansStats.toArray(new String[0]);
}
    
}