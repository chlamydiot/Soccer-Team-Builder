package model;

    //represents a generic soccer player, with some methods commmon among all players.
public interface Player {

    //getters
    String getName();

    int getAge();

    int getJerseyNumber();

    Position getPosition();


    //setters
    void setJerseyNumber(int newJerseyNum);

    void setAge(int age);

    void setName(String newName);

    //might need set age, or other setters EFFECTS: calculates player rating based on player statistic metrics
    int calculateRating();
        //TODO
        // could instead make an if statement, based on player position, to calculate rating.
        // rather than having DEF, MF, FWD in seperate classes, since calculateRating() is the only thing
        // differentiating these classes.
        // Goalie still in its own class since different metrics.
        // would still keep player interface, but could have only two classes implementing it
        // ; goalie and outfield player.
}
