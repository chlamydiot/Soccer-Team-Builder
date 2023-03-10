package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfTeamsTest {

    private ListOfTeams testListOfTeams;
    private Team testTeam;

    @BeforeEach
    void runBefore() {
        testTeam = new Team("Liverpool", Formation.FourThreeThree);
        testListOfTeams = new ListOfTeams();
    }

    @Test
    void testGetTeamSize() {
        assertEquals(0, testListOfTeams.getNumberOfTeams());
        testListOfTeams.addTeamToList(testTeam);
        assertEquals(1, testListOfTeams.getNumberOfTeams());
    }

    @Test
    void testAddTeamToList() {
        assertEquals(0, testListOfTeams.getTeams().size());
        testListOfTeams.addTeamToList(testTeam);
        assertEquals(1, testListOfTeams.getTeams().size());
    }

    @Test
    void testGetName() {
        assertTrue(testListOfTeams.getName().equals("Teams Built So Far"));
    }


}
