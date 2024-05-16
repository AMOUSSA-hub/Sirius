package edu.ezip.ing1.pds.business.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "game")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game extends Data {
    
    private String opponent;
    private String arena;
    private String championship;
    private String MVP;
    private int opponentScore;
    private int teamScore;
    private Timestamp matchDay;
    // Constructeur par défaut requis pour Jackson
    public Game() {
    }

    @JsonCreator
    public Game(
        @JsonProperty("id") int id,
        @JsonProperty("adversaire") String opponent,
        @JsonProperty("stade") String arena,
        @JsonProperty("competition") String championship,
        @JsonProperty("matchday") Timestamp matchDay
    ) {
        this.id = id;
        this.opponent = opponent;
        this.arena = arena;
        this.championship = championship;
        this.matchDay = matchDay;
    }

    public String getOpponent() {
        return opponent;
    }

    public String getArena() {
        return arena;
    }

    public String getChampionship() {
        return championship;
    }

    public String getMVP() {
        return MVP;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public int getTeamScore() {
        return teamScore;
    }

    
    public Timestamp getMatchDay(){
        return matchDay;
    }

    @JsonProperty("adversaire")
    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    @JsonProperty("stade")
    public void setArena(String arena) {
        this.arena = arena;
    }

    @JsonProperty("competition")
    public void setChampionship(String championship) {
        this.championship = championship;
    }

    @JsonProperty("hommeDuMatch")
    public void setMVP(String MVP) {
        this.MVP = MVP;
    }

    @JsonProperty("scoreAdversaire")
    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    @JsonProperty("scoreEquipe")
    public void setTeamScore(int teamScore) {
        this.teamScore = teamScore;
    }

    @JsonProperty("matchday")
    public void setMatchDay(Timestamp matchDay) {
        this.matchDay = matchDay;
    }


    @Override
    public String toString() {
        return "Match contre " + opponent + "le " + matchDay + " dans le stade " + arena + " pour le championnat " + championship + ".";
    }
}
