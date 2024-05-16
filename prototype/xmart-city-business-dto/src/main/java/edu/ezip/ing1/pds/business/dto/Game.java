package edu.ezip.ing1.pds.business.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
@JsonRootName(value = "game")
public class Game extends Data {
    
    String opponent,arena, championship,MVP;
    int opponentScore,teamScore;



    public Game( int id,String opponent, String arena, String championship) {
        this.opponent = opponent;
        this.arena = arena;
        this.championship = championship;
        this.id = id;
        
    }

    public String getOpponent() {
        return this.opponent;
    }

    public String getArena() {
        return this.arena;
    }

    public String getChampionship() {
        return this.championship;
    }

    public String getMVP() {
        return this.MVP;
    }


    public int getOpponentScore() {
        return this.opponentScore;
    }

    public int getTeamScore() {
        return teamScore;
    }

    @JsonProperty("adversaire")
    public void setOpponent(String opponent) {
        this.opponent =opponent;
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
    public void setMVP(String mVP) {
        MVP = mVP;
    }

    @JsonProperty("scoreAdversaire")
    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }
    
    @JsonProperty("scoreEquipe")
    public void setTeamScore(int teamScore) {
        this.teamScore = teamScore;
    }

    


    @Override
    public String toString() {
        
        return "Match contre "+this.opponent+" dans le stade "+this.arena+" pour la championnat "+this.championship+".";
    }
}
