package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

@JsonRootName(value = "games")
public class Games {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("games")
    private Set<Game> games = new LinkedHashSet<>();

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void add(Game game) {
        games.add(game);
    }

    @Override
    public String toString() {
        return "Games{" +
                "games=" + games +
                '}';
    }
}
