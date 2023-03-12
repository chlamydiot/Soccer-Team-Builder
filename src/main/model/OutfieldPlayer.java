package model;

import org.json.JSONObject;
import persistence.Writable;

import static java.lang.Math.round;

// represents a outfield player (IE. non-goalie player) having a name, jersey number, age, position
// and ratings for the following metrics; pace, shot, passing, dribbling, physicality
// and defending.
public class OutfieldPlayer implements Player, Writable {

    private String name;
    private int jerseyNumber;
    private int age;
    private Position position;
    private int paceRating;
    private int shotRating;
    private int passingRating;
    private int dribblingRating;
    private int physicalityRating;
    private int defendingRating;

    //REQUIRES: jerseyNumber, age, pace, shot, passing, dribbling, physicality and
    //defending > 0. pos one of: GOALTENDER, DEFENDER, MIDFIELDER, FORWARD.
    //
    //EFFECTS: Player name is set to name, jerseyNumber is set to jerseyNumber, position is set to pos,
    // paceRating is set to pace, shotRating is set to shot, passingRating is set to passing, dribblingRating
    // is set to dribbling, physicalityRating is set to physicality, defendingRating is set to defending.
    public OutfieldPlayer(String name, int jerseyNumber, int age, Position pos, int pace,
                             int shot, int passing, int dribbling, int physicality, int defending) {
        this.name = name;
        this.jerseyNumber = jerseyNumber;
        this.age = age;
        this.position = pos;
        this.paceRating = pace;
        this.shotRating = shot;
        this.passingRating = passing;
        this.dribblingRating = dribbling;
        this.physicalityRating = physicality;
        this.defendingRating = defending;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getJerseyNumber() {
        return jerseyNumber;
    }

    @Override
    public void setJerseyNumber(int newJerseyNumber) {
        this.jerseyNumber = newJerseyNumber;
    }

    @Override
    public int getAge() {
        return age;
    }

    public Position getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPaceRating() {
        return paceRating;
    }

    public int getPassingRating() {
        return passingRating;
    }

    public int getShotRating() {
        return shotRating;
    }

    public int getDribblingRating() {
        return dribblingRating;
    }

    public int getPhysicalityRating() {
        return physicalityRating;
    }

    public int getDefendingRating() {
        return defendingRating;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    public void setPace(int newPace) {
        this.paceRating = newPace;
    }

    public void setShot(int newShot) {
        this.shotRating = newShot;
    }

    public void setPassing(int newPassing) {
        this.passingRating = newPassing;
    }

    public void setDribbling(int newDribbling) {
        this.dribblingRating = newDribbling;
    }

    public void setPhys(int newPhys) {
        this.physicalityRating = newPhys;
    }

    public void setDef(int newDef) {
        this.defendingRating = newDef;
    }


    @Override
    //REQUIRES: player must have position DEFENDER, MIDFIELDER or FORWARD.
    //EFFECTS: calculates player rating based on player statistic metrics. Overall ratings for each of DEF, MF, and FWD
    //are calculated as follows:
    // DEF Rating = (defRating * 0.75) + ((paceRating + passRating + physRating)/3) * 0.2
    // + ((shotRating + dribRating)/2) * 0.05
    // MF Rating = (passRating * 0.5) + ((paceRating + shotRating + dribRating)/3) * 0.3)
    // + ((defRating + physRating)/2) * 0.2
    // FWD Rating = ((paceRating + shotRating + dribRating + passRating)/4) * 0.90
    // + (defRating * 0.025) + (physRating * 0.075)
    //
    // if given invalid input returns 0.
    //
    //TODO MAYBE implement exception throwing from this method, for when positions are not one of required.
    public int calculateRating() {
        if (position.equals(Position.DEFENDER)) {
            long result = (long) ((defendingRating * 0.75)
                                + ((physicalityRating + paceRating + passingRating) / 3) * 0.2
                                + ((shotRating + dribblingRating) / 2) * 0.05);
            return round(result);
        } else if (position.equals(Position.MIDFIELDER)) {
            long result = (long) ((passingRating * 0.5)
                    + ((paceRating + shotRating + dribblingRating) / 3) * 0.3
                    + ((defendingRating + physicalityRating) / 2) * 0.2);
            return round(result);
        } else if (position.equals(Position.FORWARD)) {
            long result = (long) (((paceRating + shotRating + dribblingRating + passingRating) / 4) * 0.90
                    + (defendingRating * 0.025)
                    + (physicalityRating * 0.075));
            return round(result);
        }
        return 0;
    }

    //EFFECTS: Returns this as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("jersey number", jerseyNumber);
        json.put("age", age);
        json.put("position", position);
        json.put("pace", paceRating);
        json.put("shot", shotRating);
        json.put("passing", passingRating);
        json.put("dribbling", dribblingRating);
        json.put("physicality", physicalityRating);
        json.put("defending", defendingRating);
        return json;
    }
}


