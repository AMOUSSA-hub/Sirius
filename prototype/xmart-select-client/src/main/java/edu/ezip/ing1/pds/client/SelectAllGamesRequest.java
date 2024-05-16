package edu.ezip.ing1.pds.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ezip.ing1.pds.business.dto.Games;
import edu.ezip.ing1.pds.business.dto.TeamEvents;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

public class SelectAllGamesRequest extends ClientRequest<Object,Games>{
        public SelectAllGamesRequest(
        NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Games readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Games games = mapper.readValue(body, Games.class);
        return games;
    }

}
