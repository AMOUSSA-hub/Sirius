package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.LinkedHashSet;
import java.util.Set;

public class Games {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("games")
    private  Set<Game> games = new LinkedHashSet<Game>();

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public final void add (final Game game) {
        games.add(game);
    }

    @Override
    public String toString() {
        return "Players{" +
                "players=" + games +
                '}';
    }
}

