import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.ezip.ing1.pds.business.dto.TeamEvent;
import edu.ezip.ing1.pds.client.MainSelectClient;

public class GetAllEventsTest {

    @Test
    public void testGetAllEvents() {
        try {
            // Appel de la méthode à tester
            MainSelectClient selectClient = new MainSelectClient();
            Set<TeamEvent> events = selectClient.getAllEvents();
            
            assertNotNull(events, "La liste des événements ne doit pas être null");
            assertFalse(events.isEmpty(), "La liste des événements ne doit pas être vide");
            
            // Vous pouvez ajouter d'autres assertions pour tester les propriétés des événements si nécessaire
        } catch (IOException | InterruptedException | SQLException e) {
            fail("Une exception s'est produite : " + e.getMessage());
        }
    }
}
