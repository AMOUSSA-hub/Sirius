package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

import edu.ezip.ing1.pds.business.dto.Stats;

public class SelectAllStatRequest extends ClientRequest<Object, Stats> {

    public SelectAllStatRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
public Stats readResult(String body) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    try {
        final Stats stats = mapper.readValue(body, Stats.class);
        System.out.println("Stats lues avec succes : " + stats);
        return stats;
    } catch (IOException e) {
        System.err.println("Erreur lors de la lecture des statistiques : " + e.getMessage());
        throw e;
    }
}
    
}
