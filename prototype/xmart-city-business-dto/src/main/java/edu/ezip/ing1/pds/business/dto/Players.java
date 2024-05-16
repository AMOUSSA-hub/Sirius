package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.LinkedHashSet;
import java.util.Set;

public class Players {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("players")
    private  Set<Player> players = new LinkedHashSet<Player>();

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public final Players add (final Player player) {
        players.add(player);
        return this;
    }

    @Override
    public String toString() {
        return "Players{" +
                "players=" + players +
                '}';
    }
}
