package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutfieldPlayerTest {

    private OutfieldPlayer testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new OutfieldPlayer("Darwin Nunez", 27, 23, Position.FORWARD
                , 89, 82, 68, 77, 86, 42);
    }

    @Test
    void testConstructor() {
        assertTrue(testPlayer.getName().equals("Darwin Nunez"));
        assertEquals(27, testPlayer.getJerseyNumber());
        assertEquals(23, testPlayer.getAge());
        assertEquals(Position.FORWARD, testPlayer.getPosition());
        assertEquals(89, testPlayer.getPaceRating());
        assertEquals(82, testPlayer.getShotRating());
        assertEquals(68, testPlayer.getPassingRating());
        assertEquals(77, testPlayer.getDribblingRating());
        assertEquals(86, testPlayer.getPhysicalityRating());
        assertEquals(42, testPlayer.getDefendingRating());
    }

    @Test
    void testSetPosition() {
        testPlayer.setPosition(Position.DEFENDER);
        assertTrue(testPlayer.getPosition().equals(Position.DEFENDER));

        testPlayer.setPosition(Position.MIDFIELDER);
        assertTrue(testPlayer.getPosition().equals(Position.MIDFIELDER));

        testPlayer.setPosition(Position.FORWARD);
        assertTrue(testPlayer.getPosition().equals(Position.FORWARD));
    }

    @Test
    void testSetName() {
        testPlayer.setName("Danesh");
        assertFalse(testPlayer.getName().equals("Darwin Nunez"));
        assertTrue(testPlayer.getName().equals("Danesh"));
    }

    @Test
    void testSetJerseyNum() {
        testPlayer.setJerseyNumber(88);
        assertEquals(88, testPlayer.getJerseyNumber());
    }

    @Test
    void testSetAge() {
        testPlayer.setAge(25);
        assertEquals(25, testPlayer.getAge());
    }

    @Test
    void testSetPace() {
        testPlayer.setPace(100);
        assertEquals(100, testPlayer.getPaceRating());

        testPlayer.setPace(0);
        assertEquals(0, testPlayer.getPaceRating());

        testPlayer.setPace(42);
        assertEquals(42, testPlayer.getPaceRating());
    }

    @Test
    void testSetShot() {
        testPlayer.setShot(100);
        assertEquals(100, testPlayer.getShotRating());

        testPlayer.setShot(0);
        assertEquals(0, testPlayer.getShotRating());

        testPlayer.setShot(66);
        assertEquals(66, testPlayer.getShotRating());

        testPlayer.setShot(82);
        assertEquals(82, testPlayer.getShotRating());
    }

    @Test
    void testSetPassing() {
        testPlayer.setPassing(100);
        assertEquals(100, testPlayer.getPassingRating());

        testPlayer.setPassing(0);
        assertEquals(0, testPlayer.getPassingRating());

        testPlayer.setPassing(66);
        assertEquals(66, testPlayer.getPassingRating());

        testPlayer.setPassing(82);
        assertEquals(82, testPlayer.getPassingRating());
    }

    @Test
    void testSetDribbling() {
        testPlayer.setDribbling(100);
        assertEquals(100, testPlayer.getDribblingRating());

        testPlayer.setDribbling(0);
        assertEquals(0, testPlayer.getDribblingRating());

        testPlayer.setDribbling(66);
        assertEquals(66, testPlayer.getDribblingRating());

        testPlayer.setDribbling(82);
        assertEquals(82, testPlayer.getDribblingRating());
    }

    @Test
    void testSetDefending() {
        testPlayer.setDef(100);
        assertEquals(100, testPlayer.getDefendingRating());

        testPlayer.setDef(0);
        assertEquals(0, testPlayer.getDefendingRating());

        testPlayer.setDef(66);
        assertEquals(66, testPlayer.getDefendingRating());

        testPlayer.setDef(82);
        assertEquals(82, testPlayer.getDefendingRating());
    }

    @Test
    void testSetPhysicality() {
        testPlayer.setPhys(100);
        assertEquals(100, testPlayer.getPhysicalityRating());

        testPlayer.setPhys(0);
        assertEquals(0, testPlayer.getPhysicalityRating());

        testPlayer.setPhys(66);
        assertEquals(66, testPlayer.getPhysicalityRating());

        testPlayer.setPhys(82);
        assertEquals(82, testPlayer.getPhysicalityRating());
    }

    @Test
    void testCalculateRating() {
        testPlayer.setPosition(Position.DEFENDER);
        assertEquals(51, testPlayer.calculateRating());

        testPlayer.setPosition(Position.MIDFIELDER);
        assertEquals(71, testPlayer.calculateRating());

        testPlayer.setPosition(Position.FORWARD);
        assertEquals(78, testPlayer.calculateRating());

        testPlayer.setPosition(Position.GOALTENDER);
        assertEquals(0, testPlayer.calculateRating());
    }
}