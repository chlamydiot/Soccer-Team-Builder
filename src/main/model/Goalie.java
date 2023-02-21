package model;

import static java.lang.Math.round;

//Represents a Goaltender having a name, age, jersey number, and dive, handling, kicking, reflex, speed and positioning
//ratings.
public class Goalie implements Player {

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


    //TODO specifications
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
}
