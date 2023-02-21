package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoalieTest {

    private Goalie testGoalie;

    @BeforeEach
    void runBefore() {
        testGoalie = new Goalie("Allison Becker", 30, 1, 86, 85, 85, 89,
                54, 90);
    }

    @Test
    void testConstructor() {
        assertEquals("Allison Becker", testGoalie.getName());
        assertEquals(30, testGoalie.getAge());
        assertEquals(1, testGoalie.getJerseyNumber());
        assertEquals(Position.GOALTENDER, testGoalie.getPosition());
        assertEquals(86, testGoalie.getDiveRating());
        assertEquals(85, testGoalie.getHandlingRating());
        assertEquals(85, testGoalie.getKickRating());
        assertEquals(89, testGoalie.getReflexRating());
        assertEquals(54, testGoalie.getSpeedRating());
        assertEquals(90, testGoalie.getPositioningRating());
    }

    @Test
    void testSetDiveRatingUp() {
        testGoalie.setDiveRating(90);
        assertEquals(90, testGoalie.getDiveRating());

        testGoalie.setDiveRating(100);
        assertEquals(100, testGoalie.getDiveRating());
    }

    @Test
    void testSetDiveRatingDown() {
        testGoalie.setDiveRating(55);
        assertEquals(55, testGoalie.getDiveRating());

        testGoalie.setDiveRating(0);
        assertEquals(0, testGoalie.getDiveRating());
    }

    @Test
    void testSetHandlingRatingUp() {
        testGoalie.setHandlingRating(90);
        assertEquals(90, testGoalie.getHandlingRating());

        testGoalie.setHandlingRating(100);
        assertEquals(100, testGoalie.getHandlingRating());
    }

    @Test
    void testSetHandlingRatingDown() {
        testGoalie.setHandlingRating(50);
        assertEquals(50, testGoalie.getHandlingRating());

        testGoalie.setHandlingRating(0);
        assertEquals(0, testGoalie.getHandlingRating());
    }

    @Test
    void testSetKickingRatingUp() {
        testGoalie.setKickRating(90);
        assertEquals(90, testGoalie.getKickRating());

        testGoalie.setKickRating(100);
        assertEquals(100, testGoalie.getKickRating());
    }


    @Test
    void testSetJerseyNum() {
        testGoalie.setJerseyNumber(13);
        assertEquals(13, testGoalie.getJerseyNumber());

        testGoalie.setJerseyNumber(1);
        assertEquals(1, testGoalie.getJerseyNumber());
    }

    @Test
    void testSetName() {
        testGoalie.setName("Danesh");
        assertEquals("Danesh", testGoalie.getName());
    }

    @Test
    void testSetAge() {
        testGoalie.setAge(31);
        assertEquals(31, testGoalie.getAge());
    }
    @Test
    void testSetKickingRatingDown() {
        testGoalie.setKickRating(44);
        assertEquals(44, testGoalie.getKickRating());

        testGoalie.setKickRating(0);
        assertEquals(0, testGoalie.getKickRating());
    }

    @Test
    void testSetReflexRatingUp() {
        testGoalie.setReflexRating(90);
        assertEquals(90, testGoalie.getReflexRating());

        testGoalie.setReflexRating(100);
        assertEquals(100, testGoalie.getReflexRating());
    }

    @Test
    void testSetReflexRatingDown() {
        testGoalie.setReflexRating(44);
        assertEquals(44, testGoalie.getReflexRating());

        testGoalie.setReflexRating(0);
        assertEquals(0, testGoalie.getReflexRating());
    }

    @Test
    void testSetSpeedRatingUp() {
        testGoalie.setSpeedRating(90);
        assertEquals(90, testGoalie.getSpeedRating());

        testGoalie.setSpeedRating(100);
        assertEquals(100, testGoalie.getSpeedRating());
    }

    @Test
    void testSetSpeedRatingDown() {
        testGoalie.setSpeedRating(44);
        assertEquals(44, testGoalie.getSpeedRating());

        testGoalie.setSpeedRating(0);
        assertEquals(0, testGoalie.getSpeedRating());
    }

    @Test
    void testSetPositioningRatingUp() {
        testGoalie.setPositioningRating(90);
        assertEquals(90, testGoalie.getPositioningRating());

        testGoalie.setPositioningRating(100);
        assertEquals(100, testGoalie.getPositioningRating());
    }

    @Test
    void testSetPositioningRatingDown() {
        testGoalie.setPositioningRating(44);
        assertEquals(44, testGoalie.getPositioningRating());

        testGoalie.setPositioningRating(0);
        assertEquals(0, testGoalie.getPositioningRating());
    }

    @Test
    void testCalculateRating() {
        assertEquals(85, testGoalie.calculateRating());
    }
}
