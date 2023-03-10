package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTeam(String name, Formation formation, Team team) {
        assertEquals(name, team.getName());
        assertEquals(formation, team.getFormation());
    }

    protected void checkGoalie(String name, int age, int jerseyNumber, int diveRating,
                               int handlingRating, int kickRating, int reflexRating, int speedRating,
                               int positioningRating, Goalie goalie) {
        assertEquals(name, goalie.getName());
        assertEquals(age, goalie.getAge());
        assertEquals(jerseyNumber, goalie.getJerseyNumber());
        assertEquals(diveRating, goalie.getDiveRating());
        assertEquals(handlingRating, goalie.getHandlingRating());
        assertEquals(kickRating, goalie.getKickRating());
        assertEquals(reflexRating, goalie.getReflexRating());
        assertEquals(speedRating, goalie.getSpeedRating());
        assertEquals(positioningRating, goalie.getPositioningRating());
    }

    protected void checkOutfieldPlayer(String name, int jerseyNumber, int age, Position position, int paceRating,
                               int shotRating, int passingRating, int dribblingRating, int physicalityRating,
                               int defendingRating, OutfieldPlayer player) {
        assertEquals(name, player.getName());
        assertEquals(age, player.getAge());
        assertEquals(jerseyNumber, player.getJerseyNumber());
        assertEquals(position, player.getPosition());
        assertEquals(paceRating, player.getPaceRating());
        assertEquals(shotRating, player.getShotRating());
        assertEquals(passingRating, player.getPassingRating());
        assertEquals(dribblingRating, player.getDribblingRating());
        assertEquals(physicalityRating, player.getPhysicalityRating());
        assertEquals(defendingRating, player.getDefendingRating());
    }
}
