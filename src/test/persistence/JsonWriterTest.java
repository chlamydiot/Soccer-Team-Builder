package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfTeams listOfTeams = new ListOfTeams();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyListOfTeams() {
        try {
            ListOfTeams listOfTeams = new ListOfTeams();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfTeams.json");
            writer.open();
            writer.write(listOfTeams);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfTeams.json");
            listOfTeams = reader.read();
            assertEquals("Teams Built So Far", listOfTeams.getName());
            assertEquals(0, listOfTeams.getNumberOfTeams());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListOfTeams() {
        try {
            ListOfTeams listOfTeams = new ListOfTeams();
            listOfTeams.addTeamToList(new Team("live", Formation.FourFourTwo));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralListOfTeams.json");
            writer.open();
            writer.write(listOfTeams);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralListOfTeams.json");
            listOfTeams = reader.read();
            assertEquals("Teams Built So Far", listOfTeams.getName());
            List<Team> teams = listOfTeams.getTeams();
            assertEquals(1, teams.size());
            checkTeam("live", Formation.FourFourTwo, teams.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
