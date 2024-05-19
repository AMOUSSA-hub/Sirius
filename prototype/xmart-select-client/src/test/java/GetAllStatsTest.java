import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.ezip.ing1.pds.business.dto.Stat;
import edu.ezip.ing1.pds.business.dto.TeamEvent;
import edu.ezip.ing1.pds.client.MainSelectClient;

public class GetAllStatsTest {

    @Test
    public void testGetAllStats() {
        try {
            MainSelectClient selectClient = new MainSelectClient();
            Set<Stat> stats = selectClient.getAllStats();
            
            assertNotNull(stats, "L'ensemble des statistiques ne doit pas être null");
            assertFalse(stats.isEmpty(), "L'ensemble des statistiques ne doit pas être vide");
            
        
            Iterator<Stat> iterator = stats.iterator();
            if (iterator.hasNext()) {
                Stat firstStat = iterator.next();
                assertNotNull(firstStat, "La première statistique ne doit pas être null");
                assertTrue(firstStat.getButs() >= 0, "Le nombre de buts doit être positif ou nul");
                assertTrue(firstStat.getPassesDecisives() >= 0, "Le nombre de passes décisives doit être positif ou nul");
                assertTrue(firstStat.getCartonsJaunes() >= 0, "Le nombre de cartons jaunes doit être positif ou nul");
                assertTrue(firstStat.getCartonsRouges() >= 0, "Le nombre de cartons rouges doit être positif ou nul");
                assertTrue(firstStat.getNoteDuMatch() >= 0, "La note du match doit être positif ou nulle");
                assertTrue(firstStat.getMinutesJouees() >= 0, "Le nombre de minutes jouées doit être positif ou nul");
                assertTrue(firstStat.getIdJoueurs() > 0, "L'identifiant du joueur doit être positif");
                assertTrue(firstStat.getIdMatchs() > 0, "L'identifiant du match doit être positif");
            }
            
        } catch (IOException | InterruptedException | SQLException e) {
            fail("Une exception s'est produite : " + e.getMessage());
        }
    }
}
