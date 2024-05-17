package client.frontend.v2;

import java.util.Comparator;

import edu.ezip.ing1.pds.business.dto.Game;

public class GameDateComparator implements Comparator<Game> {

    @Override
    public int compare(Game g1, Game g2) {
        return g1.getMatchDay().compareTo(g2.getMatchDay());

    }
}