package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

public class Stats {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Stats")
    private  Set<Stat> stats = new LinkedHashSet<Stat>();
    

    public Set<Stat> getStats() {
        return stats;
    }

    public void setStats(Set<Stat> players) {
        this.stats = players;
    }

    public final Stats add (final Stat player) {
        stats.add(player);
        return this;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "stats=" +stats +
                '}';
    }
}
