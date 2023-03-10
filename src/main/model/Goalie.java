package model;

import org.json.JSONObject;
import persistence.Writable;

import static java.lang.Math.round;

//Represents a Goaltender having a name, age, jersey number, and dive, handling, kicking, reflex, speed and positioning
//ratings.
public class Goalie implements Player, Writable {

    private static final Position pos = Position.GOALTENDER;

    private String name;
    private int age;
    private int jerseyNumber;
    private Position position;
    private int diveRating;
    private int handlingRating;
    private int kickRating;
    private int reflexRating;
    private int speedRating;
    private int positioningRating;


    //REQUIRES: age, jerseyNumber, diving, handling, kicking, reflexes, speed, positioning >= 0.
    //EFFECTS: Constructs Goalie with name, age, jerseyNumber, diving rating, handling rating, kicking rating,
    // reflexes rating, speed rating, and positioning rating.
    public Goalie(String name, int age, int jerseyNumber, int diving, int handling,
                  int kicking, int reflexes, int speed, int positioning) {
        this.name = name;
        this.age = age;
        this.jerseyNumber = jerseyNumber;
        this.position = pos;
        this.diveRating = diving;
        this.handlingRating = handling;
        this.kickRating = kicking;
        this.reflexRating = reflexes;
        this.speedRating = speed;
        this.positioningRating = positioning;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public int getJerseyNumber() {
        return jerseyNumber;
    }

    @Override
    public void setJerseyNumber(int newJerseyNum) {
        this.jerseyNumber = newJerseyNum;
    }

    @Override
    public void setAge(int newAge) {
        this.age = newAge;
    }

    @Override
    public void setName(String newName) {
        this.name = newName;
    }

    public Position getPosition() {
        return position;
    }

    public int getDiveRating() {
        return diveRating;
    }

    public int getHandlingRating() {
        return handlingRating;
    }

    public int getKickRating() {
        return kickRating;
    }

    public int getReflexRating() {
        return reflexRating;
    }

    public int getSpeedRating() {
        return speedRating;
    }

    public int getPositioningRating() {
        return positioningRating;
    }

    public void setDiveRating(int newDive) {
        this.diveRating = newDive;
    }

    public void setHandlingRating(int newHandle) {
        this.handlingRating = newHandle;
    }

    public void setKickRating(int newKick) {
        this.kickRating = newKick;
    }

    public void setReflexRating(int newReflex) {
        this.reflexRating = newReflex;
    }

    public void setSpeedRating(int newSpeed) {
        this.speedRating = newSpeed;
    }

    public void setPositioningRating(int newPositioning) {
        this.positioningRating = newPositioning;
    }


    //EFFECTS: calculates player rating based on player statistic metrics.
    //returns this overall rating.
    @Override
    public int calculateRating() {
        int majorityOfScore = (diveRating + handlingRating + kickRating + reflexRating + positioningRating) / 5;
        double coefficientOne = 0.95;
        double ratingFromFive = majorityOfScore * coefficientOne;
        double coefficientTwo = 0.05;
        double ratingForSpeed = speedRating * coefficientTwo;

        return (int) round(ratingFromFive + ratingForSpeed);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("jersey number", jerseyNumber);
        json.put("age", age);
        json.put("position", position);
        json.put("diving", diveRating);
        json.put("handling", handlingRating);
        json.put("kicking", kickRating);
        json.put("reflexes", reflexRating);
        json.put("speed", speedRating);
        json.put("positioning", positioningRating);
        return json;
    }
}
