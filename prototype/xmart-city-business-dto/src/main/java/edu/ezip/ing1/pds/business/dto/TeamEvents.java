package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.LinkedHashSet;
import java.util.Set;

public class TeamEvents {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("events")
    private  Set<TeamEvent> events = new LinkedHashSet<TeamEvent>();

    public Set<TeamEvent> getEvents() {
        return events;
    }

    public void setEvents(Set<TeamEvent> players) {
        this.events = players;
    }

    public final TeamEvents add (final TeamEvent player) {
        events.add(player);
        return this;
    }

    @Override
    public String toString() {
        return "Events{" +
                "events=" +events +
                '}';
    }
}
