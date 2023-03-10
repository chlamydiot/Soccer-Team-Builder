package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfTeams listOfTeams = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyListOfTeams() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfTeams.json");
        try {
            ListOfTeams listOfTeams = reader.read();
            assertEquals("Teams Built So Far", listOfTeams.getName());
            assertEquals(0, listOfTeams.getNumberOfTeams());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListOfTeams() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralListOfTeams.json");
        try {
            ListOfTeams listOfTeams = reader.read();
            assertEquals("Teams Built So Far", listOfTeams.getName());
            List<Team> teams = listOfTeams.getTeams();
            assertEquals(1, teams.size());
            checkTeam("live", Formation.FourFourTwo, teams.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
