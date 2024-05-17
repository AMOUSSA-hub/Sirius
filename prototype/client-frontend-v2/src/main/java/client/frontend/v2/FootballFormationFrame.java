package client.frontend.v2;

import java.util.Comparator;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;

import edu.ezip.ing1.pds.business.dto.Game;
import edu.ezip.ing1.pds.business.dto.Player;
import edu.ezip.ing1.pds.business.dto.Stat;
import edu.ezip.ing1.pds.client.MainInsertClient;
import edu.ezip.ing1.pds.client.MainSelectClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;


class FootballFormationFrame extends JPanel {
    private JPanel fieldPanel;
    private JComboBox<String> formationComboBox;
    private JTextArea formationTextArea;
    private List<JButton> playerButtons;
    private static List<List<String>> availablePlayers;
    private static List<InfosJoueurs> listeInfosJoueurs = Effectif.listeInfosJoueurs;
    private JFrame frame;
    private MainSelectClient msc;
    private JButton select = new JButton("Select matchs");
    public static Set<Game> games;
    private static HashMap<Integer,Game> matchHashMap = new HashMap<>();
    public JPanel pan;
    public JPanel pane;
    public static Set<Stat> stats;
    private HashMap<Integer, Player> playerHashMap;
    LocalDateTime localDateTime = LocalDateTime.now();
    List<JButton> buttonsPlayers = new ArrayList<>();
    public FootballFormationFrame(JFrame frame,MainSelectClient msc) {
        // Crée les composants
        this.frame = frame;
        this.msc = msc;
        playerHashMap = Effectif.playerNameHashMap;
        try {
            games = Mastermind.getGamesSet();
            setMatch();
            sortGamesByDate();
        } catch (Exception e2) {
            System.err.println(e2);
        }
        fieldPanel = new JPanel();
        formationComboBox = new JComboBox<>(new String[]{"4-4-2", "4-3-3", "3-5-2", "5-3-2","5-4-1", "4-5-1","3-4-3"});
        formationTextArea = new JTextArea(10, 30);
        playerButtons = new ArrayList<>();

        // Configure le panneau de terrain
        fieldPanel.setLayout(new GridLayout(7, 1));
        
        fieldPanel.setBackground(new Color(34, 139, 34)); // Couleur verte pour le terrain

        // Ajoute des écouteurs
        formationComboBox.addActionListener(new FormationActionListener());

        // Ajoute les composants à la fenêtre
        setLayout(new BorderLayout());
        add(formationComboBox, BorderLayout.NORTH);
        add(fieldPanel, BorderLayout.CENTER);
        add(new JScrollPane(formationTextArea), BorderLayout.SOUTH);
        int taille = games.size();
        pan = new JPanel(new GridLayout(taille,1));
        pane = new JPanel(new GridLayout(1,taille));
        //pan.add(select);
        setMatchs(pan,pane,games);
        select.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
            
        });
        JScrollPane scrollPane = new JScrollPane(pan);
        JScrollPane scrollPane2 = new JScrollPane(pane);
        scrollPane.setPreferredSize(new Dimension(300,1000));
        scrollPane2.setPreferredSize(new Dimension(100,250));
        add(scrollPane,BorderLayout.EAST);
        add(scrollPane2,BorderLayout.SOUTH);
        availablePlayers = new ArrayList<>();
    

        // Affiche la formation par défaut
        updateFormation("4-3-3");
        formationComboBox.setSelectedItem("4-3-3");
        List<String> positions = Arrays.asList("DG", "DC", "DC", "DD", "MC", "MC", "MC", "AG", "BU", "AD");
        //String[] positionsArray = positions.toArray(new String[positions.size()]);
        //toCompo(positionsArray);

        refreshPlayersAvailable();
        remove(pan);
        refreshMatch();
    }



    private void sortGamesByDate() {
        List<Game> gameList = new ArrayList<>(games);
        Collections.sort(gameList, new GameDateComparator());
        games = new LinkedHashSet<>(gameList); // Si vous avez besoin de réassigner à 'games' en tant que Set
    }
    



    public static void refreshMatch() {
        //pan = new JPanel(new GridLayout(games.size(),1));
        System.out.println(games.toString());
        //setMatchs(pan,games);
    }

    public static void getPlayersAttributs() {
        for(InfosJoueurs infosJoueurs : listeInfosJoueurs) {
            List<String> tmp = new ArrayList<>();
            tmp.add(infosJoueurs.getNom());
            tmp.add(infosJoueurs.getPrenom());
            tmp.add(String.valueOf(infosJoueurs.getNumero()));
            tmp.add(String.valueOf(infosJoueurs.getId()));
            availablePlayers.add(tmp);
            
        }
    }

    public static void refreshPlayersAvailable(){
        availablePlayers.clear();
        getPlayersAttributs();
    }

    private void updateFormation(String formation) {
        fieldPanel.removeAll();
        buttonsPlayers = new ArrayList<>();
        // Ajoute le gardien
        JPanel keeper = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JPanel goalkeeperPanel = new JPanel(new BorderLayout());
        goalkeeperPanel.setOpaque(false);
        JButton goalkeeperButton = createPlayerButton("Gardien","G");
        buttonsPlayers.add(goalkeeperButton);
        goalkeeperButton.setBackground(Color.YELLOW);
        goalkeeperPanel.add(goalkeeperButton, BorderLayout.CENTER);
        JLabel goalkeeperLabel = new JLabel("Nom");
        goalkeeperLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goalkeeperPanel.add(goalkeeperLabel, BorderLayout.SOUTH);
        keeper.add(goalkeeperPanel);
        keeper.setBackground(new Color(34, 139, 34)); // Couleur verte pour le terrain
        fieldPanel.add(keeper);
        playerButtons.add(goalkeeperButton);

        // Exemple de disposition pour différentes formations
        switch (formation) {
            case "4-4-2":
                addPlayersToField(new int[]{4, 4, 2});
                addSubstitutePlayersToField(new int[]{7});
                break;
            case "4-3-3":
                addPlayersToField(new int[]{4, 3, 3});
                addSubstitutePlayersToField(new int[]{7});
                break;
            case "3-5-2":
                addPlayersToField(new int[]{3, 5, 2});
                addSubstitutePlayersToField(new int[]{7});
                break;
            case "5-3-2":
                addPlayersToField(new int[]{5, 3, 2});
                addSubstitutePlayersToField(new int[]{7});
                break;
            case "5-4-1":
                addPlayersToField(new int[]{5, 4, 1});
                addSubstitutePlayersToField(new int[]{7});
                break;
            case "4-5-1":
                addPlayersToField(new int[]{4, 5, 1});
                addSubstitutePlayersToField(new int[]{7});
                break;
            case "3-4-3":
                addPlayersToField(new int[]{3, 4, 3});
                addSubstitutePlayersToField(new int[]{7});
                break;
        }

        fieldPanel.revalidate();
        fieldPanel.repaint();
        refreshPlayersAvailable();
        formationTextArea.setText("Formation sélectionnée : " + formation);
    }

    private void setMatchs(JPanel allMatchFuture,JPanel allMatchPast, Set<Game> games){
        JPanel oneMatch;
        for (Game game : games) {
            JButton button = new JButton("Voir formation");
            oneMatch = new JPanel(new BorderLayout());
            oneMatch.setPreferredSize(new Dimension(280, 150));
            oneMatch.setBorder(BorderFactory.createLineBorder(Color.RED,3));
            JLabel opponent = new JLabel(game.getOpponent());
            opponent.setHorizontalAlignment(SwingConstants.CENTER);
            oneMatch.add(opponent,BorderLayout.NORTH);
            oneMatch.add(button,BorderLayout.SOUTH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String formattedDate = sdf.format(game.getMatchDay());
            oneMatch.add(new JLabel(formattedDate),BorderLayout.WEST);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            Date date = Date.from(zonedDateTime.toInstant());
            if(game.getMatchDay().before(date)){
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDialog dialog = new JDialog(frame, "Sélectionner un joueur", true);
                        dialog.setLayout(new BorderLayout());
                        dialog.setSize(400, 300);
                        DefaultListModel<String> playerListModel = new DefaultListModel<>();
                        for (Stat stat : stats) {
                            if (stat.getIdMatchs() == game.getId_Match()) {
                                Player p = playerHashMap.get(stat.getIdJoueurs());
                                playerListModel.addElement(p.getPrenom() + " " + p.getNom() + " #" + p.getNumero());
                            }
                        }
                        JList<String> playerList = new JList<>(playerListModel);
                        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        // Ajouter un bouton pour sélectionner le joueur
                        //JButton selectButton = new JButton("Sélectionner");
                        dialog.add(new JScrollPane(playerList), BorderLayout.CENTER);
                        //dialog.add(selectButton, BorderLayout.SOUTH);
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                    });
                allMatchPast.add(oneMatch);
            }
            else {
                button.setText("Soumettre la compo");
                button.addActionListener(new ActionListener() {
                
					@Override
					public void actionPerformed(ActionEvent e) {
                        int i = 0;
                        List<JButton> buttonsTmp = new ArrayList<>();
                        for(JButton button : buttonsPlayers){
                            if (!((List<String>)button.getClientProperty("infosJoueurs")).isEmpty()) {
                                buttonsTmp.add(button);
                            }
                        }  
                        if (buttonsTmp.size() >= 11) {
                            
                            MainInsertClient.deleteRequest(game, "DELETE_STATS");
                            for(JButton button : buttonsTmp){
                                List<String> liste = (List<String>)button.getClientProperty("infosJoueurs");
                                Stat stat = new Stat();
                                stat.setIdJoueurs(Integer.parseInt(liste.get(3)));
                                stat.setIdMatchs(game.getId_Match());
                                i += MainInsertClient.sendRequest(stat, "INSERT_STATS");
                          }
                        }else{JOptionPane.showMessageDialog(null,"Pas assez de joueur !");}
                        if (i == buttonsTmp.size() && i!=0) {
                            JOptionPane.showMessageDialog(null,"Votre compo a ete enregistrée !");
                        }else{
                            JOptionPane.showMessageDialog(null, "Une erreur est survenue !");
                        }
					}
                    
                });
                allMatchFuture.add(oneMatch);
            }

        }
    }

    private String determinePoste(int i, int j,int nbPlayer) {
        //System.out.println(" deopdoep " + nbPlayer);
        if (nbPlayer == 4 && i == 0) {
            if (j == 0) {
                return "DD";
            } else if (j == 1 || j == 2) {
                return "DC";
            } else if (j == 3) {
                return "DG";
            }
        }
        else if (nbPlayer == 5 && i ==0) {
            if (j == 0) {
                return "DD";
            } else if (j == 1 || j == 2 || j == 3) {
                return "DC";
            } else if (j == 4) {
                return "DG";
            }
        }
        else if (nbPlayer == 3 && i == 0) {
            return "DC";
        }
        else if (nbPlayer == 3 && i == 1) {
            return "MC";
        }
        else if (nbPlayer == 4 && i == 1) {
            if (j == 0) {
                return "MD";
            } else if (j == 1 || j == 2) {
                return "MC";
            } else if (j == 3) {
                return "MG";
            }
        }
        else if (nbPlayer == 5 && i == 1) {
            if (j == 0) {
                return "MD";
            } else if (j == 1 || j == 2 || j == 3) {
                return "MC";
            } else if (j == 4) {
                return "MG";
            }
        }
        else if (nbPlayer == 2 && i == 2) {
            return "BU";
        }
        else if (nbPlayer == 3 && i == 2 ) {
            if (j == 0) {
                return "AG";
            }
            else if (j == 1) {
                return "BU";
            }
            else if (j == 2) {
                return "AD";
            }
        }
        return null;
    }
    

    private void toCompo(String[] postes) {
        int defense = 0;
        int midfield = 0;
        int forward = 0;
        for(String poste : postes) {
            String pos = poste.substring(0,1);
            if (pos.equals("D")) {
                defense++;
            }
            if (pos.equals("M")) {
                midfield++;
            }
            if (pos.equals("B") || pos.equals("A")) {
                forward++;
            }
        }
        updateFormation(defense+"-"+midfield+"-"+forward);
    }

    private void addPlayersToField(int[] lines) {
        for (int i = 0; i < lines.length; i++) {
            JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            linePanel.setOpaque(false); // Rendre le panneau transparent
            
            for (int j = 0; j < lines[i]; j++) {
                JPanel playerPanel = new JPanel();
                playerPanel.setOpaque(false);
                String poste = determinePoste(i, j,lines[i]); // Déterminez le poste du joueur
                JButton playerButton = createPlayerButton("Joueur", poste);
                buttonsPlayers.add(playerButton);
                System.out.println(poste);
                //playerButton.setPreferredSize(new Dimension(100, 100)); // Taille fixe pour le bouton
                playerPanel.add(playerButton, BorderLayout.CENTER);
                JLabel playerNameLabel = new JLabel("Nom Prenom");
                playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                playerPanel.add(playerNameLabel, BorderLayout.SOUTH);
                playerButtons.add(playerButton);
                linePanel.add(playerPanel);
            }
    
            fieldPanel.add(linePanel);
        }
        JPanel gap = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        gap.setBackground(new Color(34, 139, 34));
        fieldPanel.add(gap);
    }
    

    private void addSubstitutePlayersToField(int[] lines) {
        for (int i = 0; i < lines.length; i++) {
            JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            linePanel.setOpaque(false); // Rendre le panneau transparent

            for (int j = 0; j < lines[i]; j++) {
                JPanel playerPanel = new JPanel(new BorderLayout());
                playerPanel.setOpaque(false);
                JButton playerButton = createPlayerButton("Remplacant","R");
                playerPanel.add(playerButton, BorderLayout.CENTER);
                JLabel playerNameLabel = new JLabel("Nom");
                playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                playerPanel.add(playerNameLabel, BorderLayout.SOUTH);
                linePanel.add(playerPanel);
                
            }
            fieldPanel.add(linePanel);
        }
    }

    private JButton createPlayerButton(String text, String poste) {
        JButton button = new RoundButton(text);
        button.setPreferredSize(new Dimension(70, 70));
        button.setBackground(new Color(173, 216, 230));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPlayerSelectionDialog(button);
            }
        });
        // Associer le poste au bouton
        List<String> tmp = new ArrayList<>(); 
        button.putClientProperty("poste", poste);
        button.putClientProperty("infosJoueurs", tmp);
        button.putClientProperty("id_joueurs", -1);
        return button;
    }


    private void setMatch(){
        for(Game g : games) {
            matchHashMap.put(g.getId(), g);
        }
    }
    
    public static HashMap<Integer,Game> getMatchHashMap(){
        for (int i: matchHashMap.keySet())
        System.out.println("irfjiefjiefji " + matchHashMap.get(i) + " i : " + i);
        return matchHashMap;
    }



    private void openPlayerSelectionDialog(JButton button) {
        // Récupérer le poste associé au bouton
        List<String> infosJoueurs = (List<String>)button.getClientProperty("infosJoueurs");
        System.out.println("fouejfoizjfji + " + infosJoueurs.toString());
/*         if (!(button.getText().equals("Joueur"))|| !(button.getText().equals("Gardien")) || !(button.getText().equals("Remplacant"))) {

        } */
        // Récupérer le numéro du joueur à partir du texte du bouton
        String buttonText = button.getText();
        int playerNumber = 0;
        
        // Créer un dialogue de sélection de joueur
        JDialog dialog = new JDialog(frame, "Sélectionner un joueur", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
    
        // Créer une liste déroulante pour les joueurs
        DefaultListModel<String> playerListModel = new DefaultListModel<>();
        //int index = 0;
        if (!infosJoueurs.isEmpty()) {
            availablePlayers.add(infosJoueurs);
        }
        List<List<String>> playerPut = new ArrayList<>();
        for (List<String> player : availablePlayers) {
            playerListModel.addElement(player.get(0) + " " + player.get(1) + " #" + player.get(2));
            playerPut.add(player);
        }
        JList<String> playerList = new JList<>(playerListModel);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        // Ajouter un bouton pour sélectionner le joueur
        JButton selectButton = new JButton("Sélectionner");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String names = playerList.getSelectedValue();
                int names_lgth = names.length();
                String selectedPlayer = names.substring(0,names_lgth-2);
                String number = names.substring(names_lgth -2);
                if (selectedPlayer != null) {
                    JLabel playerNameLabel = (JLabel) ((JPanel) button.getParent()).getComponent(1);
                    playerNameLabel.setText(selectedPlayer);
                    button.setText(number);
                    int index = playerList.getSelectedIndex();
                    availablePlayers.remove(index);
                    button.putClientProperty("infosJoueurs", playerPut.get(index));
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner un joueur.");
                }
            }
        });
    
        // Ajouter la liste des joueurs et le bouton de sélection au dialogue
        dialog.add(new JScrollPane(playerList), BorderLayout.CENTER);
        dialog.add(selectButton, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    
    

    private class FormationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedFormation = (String) formationComboBox.getSelectedItem();
            if (selectedFormation != null) {
                updateFormation(selectedFormation);
            }
        }
    }
}

class RoundButton extends JButton {
    public RoundButton(String label) {
        super(label);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width, getSize().height);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, getSize().width, getSize().height);
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = getSize().width / 2;
        int centerX = radius;
        int centerY = radius;
        return (Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= Math.pow(radius, 2);
    }

}




