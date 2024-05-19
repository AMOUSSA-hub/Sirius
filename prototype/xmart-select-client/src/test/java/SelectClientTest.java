import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ezip.ing1.pds.client.MainSelectClient;

public class SelectClientTest {

    @Test
    public void selectPlayerTest() {
        try {
            List<List<Object>> playersInformation = MainSelectClient.selectAllPlayers();
            
            assertNotNull(playersInformation, "La liste des joueurs ne doit pas être null");
            assertFalse(playersInformation.isEmpty(), "La liste des joueurs ne doit pas être vide");
            
            List<Object> firstPlayer = playersInformation.get(0);
            assertNotNull(firstPlayer, "Le premier joueur ne doit pas être null");
            assertEquals("Louis", firstPlayer.get(0), "Le prénom du joueur doit être 'John'");
            assertEquals("TRAN", firstPlayer.get(1), "Le nom du joueur doit être 'Doe'");
            
        } catch (Exception e) {
            fail("Une exception s'est produite : " + e.getMessage());
        }
    }
}
