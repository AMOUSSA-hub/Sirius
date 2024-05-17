package client.frontend.v2;


import edu.ezip.ing1.pds.business.dto.Game;
import edu.ezip.ing1.pds.business.dto.Stat;
import edu.ezip.ing1.pds.business.dto.TeamEvent;
import edu.ezip.ing1.pds.client.MainSelectClient;

import java.util.*;

public class Mastermind {
    private static List<List<Object>> playersList = new ArrayList<>();
    private static Set<TeamEvent> eventsSet = new HashSet<>();
    private static Set<Stat> statsSet = new HashSet<>();
    private static Set<Game> gamesSet = new HashSet<>();

    public static void updateData(MainSelectClient client) {
        try {
            playersList = client.selectAllPlayers();
            
            
            eventsSet = client.getAllEvents();
            statsSet = client.getAllStats();
            gamesSet = client.getAllGames();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des données : " + e.getMessage());
        }
    }

    public static void addPlayer(List<Object> player) {
        playersList.add(player);
    }

    public static void removePlayer(List<Object> player) {
        playersList.remove(player);
    }

    public static void addEvent(TeamEvent event) {
        eventsSet.add(event);
    }

    public static void removeEvent(TeamEvent event) {
        eventsSet.remove(event);
    }

    public static void addStat(Stat stat) {
        statsSet.add(stat);
    }

    public static void removeStat(Stat stat) {
        statsSet.remove(stat);
    }

    public static void addGame(Game game) {
        gamesSet.add(game);
    }

    public static void removeGame(Game game) {
        gamesSet.remove(game);
    }
    

    // Méthodes pour obtenir les listes et ensembles

    public static List<List<Object>> getPlayersList() {
        return playersList;
    }

    public static Set<TeamEvent> getEventsSet() {
        return eventsSet;
    }

    public static Set<Stat> getStatsSet() {
        return statsSet;
    }

    public static Set<Game> getGamesSet() {
        return gamesSet;
    }
}
