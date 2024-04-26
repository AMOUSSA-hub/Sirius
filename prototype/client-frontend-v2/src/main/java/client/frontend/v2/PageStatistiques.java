package client.frontend.v2;

import edu.ezip.ing1.pds.client.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.MainInsertClient;

public class PageStatistiques extends JPanel {

    private DefaultTableModel modeleButeurs;
    private DefaultTableModel modelePasseurs;
    private DefaultTableModel tablecartonsjaunes;
    private DefaultTableModel tablecartonsrouges;
    private DefaultTableModel tablenote;
    private DefaultTableModel tablemin;

    public PageStatistiques(MainSelectClient msc) {
        Set<Stat> stats = null;
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
        tablecartonsjaunes = new DefaultTableModel(new Object[]{"Joueur", "cartonsjaunes"}, 0);
        JTable tableaucartonsjaunes = new JTable(tablecartonsjaunes);
        JScrollPane defilementcartonsjaunes = new JScrollPane(tableaucartonsjaunes);
        panelcartonsjaunes.add(defilementcartonsjaunes, BorderLayout.CENTER);
        onglets.addTab("Cartonsjaunes", panelcartonsjaunes);

        JPanel panelcartonsrouges = new JPanel(new BorderLayout());
        panelcartonsrouges.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablecartonsrouges = new DefaultTableModel(new Object[]{"Joueur", "cartonsjaunes"}, 0);
        JTable tableaucartonsrouges = new JTable(tablecartonsrouges);
        JScrollPane defilementcartonsrouge = new JScrollPane(tableaucartonsrouges);
        panelcartonsrouges.add(defilementcartonsrouge, BorderLayout.CENTER);
        onglets.addTab("Cartonsrouges", panelcartonsrouges);

        JPanel panelnote = new JPanel(new BorderLayout());
        panelnote.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablenote = new DefaultTableModel(new Object[]{"Joueur", "note"}, 0);
        JTable tableaunote = new JTable(tablenote);
        JScrollPane defilementnote = new JScrollPane(tableaunote);
        panelnote.add(defilementnote, BorderLayout.CENTER);
        onglets.addTab("Note", panelnote);

        JPanel panelmin = new JPanel(new BorderLayout());
        panelmin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablemin = new DefaultTableModel(new Object[]{"Joueur", "min"}, 0);
        JTable tableaumin = new JTable(tablemin);
        JScrollPane defilementmin = new JScrollPane(tableaumin);
        panelmin.add(defilementmin, BorderLayout.CENTER);
        onglets.addTab("minute jouer", panelmin);
        JButton addStats = new JButton("ajouter des statistiques");
        MainInsertClient.sendRequest(new Stat((short)1,(short) 2,(short) 3,(short) 4,(short) 5,(short) 6, 14, 1), "INSERT_STATS");
        panel.add(addStats, BorderLayout.SOUTH);

        //JPanel panneauStatsEquipe = new JPanel(new BorderLayout());
       //panneauStatsEquipe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // JLabel etiquetteStatsEquipe = new JLabel("Statistiques de l'équipe");
        // etiquetteStatsEquipe.setFont(new Font("Arial", Font.BOLD, 16));
        // etiquetteStatsEquipe.setBorder(new EmptyBorder(10, 10, 10, 10));
        // panneauStatsEquipe.add(etiquetteStatsEquipe, BorderLayout.NORTH);

        // JPanel panneauLabelsStats = new JPanel(new GridLayout(0, 2));
        // JLabel labelMatchsJoues = new JLabel("Matchs joués : 20");
        // JLabel labelButsPour = new JLabel("Buts pour : 45");
        // JLabel labelButsContre = new JLabel("Buts contre : 30");
        // JLabel labelCartonsJaunes = new JLabel("Cartons jaunes : 10");
        // JLabel labelCartonsRouges = new JLabel("Cartons rouges : 2");
        // JLabel labelSerie = new JLabel("Série en cours : VVNVNV");
        // panneauLabelsStats.add(labelMatchsJoues);
        // panneauLabelsStats.add(new JLabel());
        // panneauLabelsStats.add(labelButsPour);
        // panneauLabelsStats.add(new JLabel());
        // panneauLabelsStats.add(labelButsContre);
        // panneauLabelsStats.add(new JLabel());
        // panneauLabelsStats.add(labelCartonsJaunes);
        // panneauLabelsStats.add(new JLabel());
        // panneauLabelsStats.add(labelCartonsRouges);
        // panneauLabelsStats.add(new JLabel());
        // panneauLabelsStats.add(labelSerie);
        // panneauLabelsStats.add(new JLabel());
        // panneauStatsEquipe.add(panneauLabelsStats, BorderLayout.CENTER);
        // onglets.addTab("Statistiques de l'équipe", panneauStatsEquipe);

        panel.add(onglets);



        Buteurs(stats);
        Passeurs(stats);
        Cartonsjaunes(stats);
        CartonsRouges(stats);
        Notedumatch(stats);
        Minutesjouees(stats);
    this.setLayout(new GridLayout(1,1));
        add(panel);

        setVisible(true);
    }

    private void Buteurs(Set<Stat>stats) {
        modeleButeurs.setRowCount(0);
        //stats.sort(Comparator.comparingInt(Stat::getButs).reversed());
        for (Stat stat : stats) {
            modeleButeurs.addRow(new Object[]{stat.getIdJoueurs(), stat.getButs()});
        }
    }

    private void Passeurs(Set<Stat>stats) {
        modelePasseurs.setRowCount(0);
        for (Stat stat : stats) {
            modelePasseurs.addRow(new Object[]{stat.getIdJoueurs(), stat.getPassesDecisives()});
        }
    }
    private void Cartonsjaunes(Set<Stat>stats) {
        tablecartonsjaunes.setRowCount(0);
        for (Stat stat : stats) {
            tablecartonsjaunes.addRow(new Object[]{stat.getIdJoueurs(), stat.getCartonsJaunes()});
        }
    }
    private void CartonsRouges(Set<Stat>stats) {
        tablecartonsrouges.setRowCount(0);
        for (Stat stat : stats) {
            tablecartonsrouges.addRow(new Object[]{stat.getIdJoueurs(), stat.getCartonsRouges()});
        }
    }
    private void Notedumatch(Set<Stat>stats) {
        tablemin.setRowCount(0);
        for (Stat stat : stats) {
            tablenote.addRow(new Object[]{stat.getIdJoueurs(), stat.getNoteDuMatch()});
        }
    }
    private void Minutesjouees(Set<Stat>stats) {
        tablemin.setRowCount(0);
        for (Stat stat : stats) {
            tablemin.addRow(new Object[]{stat.getIdJoueurs(), stat.getMinutesJouees()});
        }
    }
    

    

   
}
