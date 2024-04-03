package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.business.dto.TeamEvents;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllEventsRequest extends ClientRequest<Object, TeamEvents> {

    public SelectAllEventsRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public TeamEvents readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final TeamEvents events = mapper.readValue(body, TeamEvents.class);
        return events;
    }

    
}
