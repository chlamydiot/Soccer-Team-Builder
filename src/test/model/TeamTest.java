package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    private Team testTeam;
    private OutfieldPlayer testPlayer;
    private Goalie testGoalie;

    @BeforeEach
    void runBefore() {
        testTeam = new Team("Liverpool", Formation.FourThreeThree);
        testPlayer = new OutfieldPlayer("Darwin Nunez", 27, 23, Position.FORWARD
                , 89, 82, 68, 77, 86, 42);
        testGoalie = new Goalie("Allison Becker", 30, 1, 86, 85,
                85, 89, 54, 90);
    }

    @Test
    void testGetName() {
        assertEquals("Liverpool", testTeam.getName());
    }

    @Test
    void testGetRatings() {
        testTeam.addPlayer433(testGoalie);
        testTeam.addPlayer433(testPlayer);
        ArrayList ratings = testTeam.getRatings(testTeam);
        assertEquals(85, ratings.get(0));
        assertEquals(78, ratings.get(1));
    }

    @Test
    void testSetFormation() {
        assertEquals(testTeam.getFormation(), Formation.FourThreeThree);
        testTeam.setFormation(Formation.ThreeFiveTwo);
        assertEquals(testTeam.getFormation(), Formation.ThreeFiveTwo);
    }

    @Test
    void testSetTeamGoaltender() {
        testTeam.setTeamGoaltender(testGoalie);
        assertEquals(testTeam.getGoaltender(), testGoalie);
        testTeam.setTeamGoaltender(testPlayer);
        assertEquals(testTeam.getGoaltender(), testPlayer);
        assertNotEquals(testTeam.getGoaltender(), testGoalie);
    }

    @Test
    void testSetTeamDefenders() {
        assertEquals(0, testTeam.getDefenders().size());
        testPlayer.setPosition(Position.DEFENDER);
        ArrayList defList = new ArrayList();
        defList.add(testPlayer);
        testTeam.setTeamDefenders(defList);
        assertEquals(1, testTeam.getDefenders().size());
        assertEquals(testPlayer, testTeam.getDefenders().get(0));
    }

    @Test
    void testSetTeamMidfielders() {
        assertEquals(0, testTeam.getMidfielders().size());
        testPlayer.setPosition(Position.MIDFIELDER);
        ArrayList midList = new ArrayList();
        midList.add(testPlayer);
        testTeam.setTeamMidfielders(midList);
        assertEquals(1, testTeam.getMidfielders().size());
        assertEquals(testPlayer, testTeam.getMidfielders().get(0));
    }

    @Test
    void testSetTeamForwards() {
        assertEquals(0, testTeam.getTeamForwards().size());
        testPlayer.setPosition(Position.FORWARD);
        ArrayList fwdList = new ArrayList();
        fwdList.add(testPlayer);
        testTeam.setTeamForwards(fwdList);
        assertEquals(1, testTeam.getTeamForwards().size());
        assertEquals(testPlayer, testTeam.getTeamForwards().get(0));
    }

    @Test
    void testGetFormation() {
        assertEquals(Formation.FourThreeThree, testTeam.getFormation());
    }

    @Test
    void testGetGoaltender() {
        testTeam.addPlayer433(testGoalie);
        assertEquals(testTeam.getGoaltender(), testGoalie);
    }

    @Test
    void testConstructor() {
        assertEquals("Liverpool", testTeam.getName());
        assertEquals(Formation.FourThreeThree, testTeam.getFormation());
        assertEquals(0, testTeam.getMemberNames().size());
        assertNull(testTeam.getGoaltender());
        assertEquals(0, testTeam.getDefenders().size());
        assertEquals(0, testTeam.getMidfielders().size());
        assertEquals(0, testTeam.getTeamForwards().size());
    }

    @Test
    void testAddPlayer433() {
        assertEquals(0, testTeam.getTeamMembers().size());
        testTeam.addPlayer433(testGoalie);
        assertEquals(testTeam.getGoaltender(), testGoalie);
        testTeam.addPlayer433(testPlayer);
        assertTrue(testTeam.getTeamForwards().contains(testPlayer));
        testTeam.addPlayer433(testPlayer);
        assertEquals(3, testTeam.getTeamMembers().size());
        testTeam.addPlayer433(testPlayer);
        assertEquals(4, testTeam.getTeamMembers().size());
        testTeam.addPlayer433(testPlayer);
        assertEquals(4, testTeam.getTeamMembers().size());
        assertEquals(0, testTeam.getDefenders().size());
        testPlayer.setPosition(Position.DEFENDER);
        testTeam.addPlayer433(testPlayer);
        assertEquals(1, testTeam.getDefenders().size());
        assertEquals(5, testTeam.getTeamMembers().size());
        testTeam.addPlayer433(testPlayer);
        testTeam.addPlayer433(testPlayer);
        testTeam.addPlayer433(testPlayer);
        assertEquals(4, testTeam.getDefenders().size());
        assertEquals(8, testTeam.getTeamMembers().size());
        testTeam.addPlayer433(testPlayer);
        assertEquals(4, testTeam.getDefenders().size());
        assertEquals(8, testTeam.getTeamMembers().size());
        testPlayer.setPosition(Position.MIDFIELDER);
        testTeam.addPlayer433(testPlayer);
        assertEquals(9, testTeam.getTeamMembers().size());
        assertEquals(1, testTeam.getMidfielders().size());
        testTeam.addPlayer433(testPlayer);
        testTeam.addPlayer433(testPlayer);
        assertEquals(11, testTeam.getTeamMembers().size());
        assertEquals(3, testTeam.getMidfielders().size());
        testTeam.addPlayer433(testPlayer);
        assertEquals(11, testTeam.getTeamMembers().size());
        assertEquals(3, testTeam.getMidfielders().size());
    }

    @Test
    void testAddPlayer422() {
        assertEquals(0, testTeam.getTeamMembers().size());

        testTeam.addPlayer442(testGoalie);
        assertEquals(testTeam.getGoaltender(), testGoalie);
        assertTrue(testTeam.getTeamMembers().contains(testGoalie));
        assertTrue(testTeam.getMemberNames().contains("Allison Becker"));

        testTeam.addPlayer442(testPlayer);
        assertEquals(1, testTeam.getTeamForwards().size());
        assertTrue(testTeam.getTeamMembers().contains(testPlayer));
        assertTrue(testTeam.getMemberNames().contains("Darwin Nunez"));

        testTeam.addPlayer442(testPlayer);
        assertEquals(2, testTeam.getTeamForwards().size());
        assertTrue(testTeam.getTeamMembers().contains(testPlayer));
        assertTrue(testTeam.getMemberNames().contains("Darwin Nunez"));

        testTeam.addPlayer442(testPlayer);
        assertEquals(2, testTeam.getTeamForwards().size());
    }

    @Test
    void testAddPlayer352() {
        assertEquals(0, testTeam.getTeamMembers().size());

        testTeam.addPlayer352(testGoalie);
        assertEquals(testTeam.getGoaltender(), testGoalie);
        assertTrue(testTeam.getTeamMembers().contains(testGoalie));
        assertTrue(testTeam.getMemberNames().contains("Allison Becker"));

        testTeam.addPlayer352(testPlayer);
        assertEquals(1, testTeam.getTeamForwards().size());
        assertTrue(testTeam.getTeamMembers().contains(testPlayer));
        assertTrue(testTeam.getMemberNames().contains("Darwin Nunez"));

        testTeam.addPlayer352(testPlayer);
        assertEquals(2, testTeam.getTeamForwards().size());
        assertTrue(testTeam.getTeamMembers().contains(testPlayer));
        assertTrue(testTeam.getMemberNames().contains("Darwin Nunez"));

        testTeam.addPlayer352(testPlayer);
        assertEquals(2, testTeam.getTeamForwards().size());
    }

    @Test
    void testRemovePlayer() {
        OutfieldPlayer newPlayer = new OutfieldPlayer("Bob", 33, 22, Position.MIDFIELDER,
                44, 55,44, 22, 11,44);

        testTeam.addPlayer(testGoalie, 4, 3, 3);
        assertEquals(1, testTeam.getTeamMembers().size());
        testTeam.addPlayer(testPlayer, 4, 3, 3);
        assertEquals(2, testTeam.getTeamMembers().size());

        testTeam.removePlayer(testGoalie);
        assertEquals(1, testTeam.getTeamMembers().size());
        testTeam.removePlayer(testPlayer);
        assertEquals(0, testTeam.getTeamMembers().size());

        testTeam.addPlayer(testGoalie, 4, 3, 3);
        testTeam.addPlayer(testPlayer, 4, 3, 3);
        testTeam.removePlayer(newPlayer);
        assertEquals(2, testTeam.getTeamMembers().size());
    }

    @Test
    void testTeamRating() {
        testTeam.addPlayer433(testGoalie);
        assertEquals(85, testTeam.teamRating());

        testTeam.addPlayer433(testPlayer);
        assertEquals(81, testTeam.teamRating());
    }

    @Test
    void testSetName() {
        testTeam.setName("Arsenal");
        assertEquals("Arsenal", testTeam.getName());
    }

}
