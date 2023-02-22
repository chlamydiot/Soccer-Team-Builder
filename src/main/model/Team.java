package model;

import java.util.ArrayList;

import static java.lang.Math.round;

//A soccer/football team with name and team members
public class Team {

    private String teamName;
    private final ArrayList<Player> teamMembers;
    private final Formation formation;
    private Player teamGoaltender;
    private final ArrayList<OutfieldPlayer> teamDefenders;
    private final ArrayList<OutfieldPlayer> teamMidfielders;
    private final ArrayList<OutfieldPlayer> teamForwards;
    //TODO thinking of making seperate fields for defender, forward, midfielder,
    //and maybe formation input? then could do if statement for constructor based on the formation selected.

    public Team(String name, Formation formation) {
        this.teamName = name;
        this.formation = formation;
        this.teamGoaltender = null;
        this.teamDefenders = new ArrayList<OutfieldPlayer>();
        this.teamMidfielders = new ArrayList<>();
        this.teamForwards = new ArrayList<>();
        this.teamMembers = new ArrayList<>();
    }

    public void setName(String name) {
        teamName = name;
    }

    public String getName() {
        return teamName;
    }

    public Formation getFormation() {
        return formation;
    }

    public Player getGoaltender() {
        return teamGoaltender;
    }

    public ArrayList<OutfieldPlayer> getDefenders() {
        return teamDefenders;
    }

    public ArrayList<OutfieldPlayer> getMidfielders() {
        return teamMidfielders;
    }

    public ArrayList<OutfieldPlayer> getTeamForwards() {
        return teamForwards;
    }

    public ArrayList<String> getMemberNames() {
        ArrayList<String> membernames = new ArrayList<>();
        for (Player player : teamMembers) {
            membernames.add(player.getName());
        }
        return membernames;
    }

    //REQUIRES: defNum, midNum, fwdNum > 0.
    //MODIFIES: this
    //EFFECTS: adds player to the team, if number of players in the given position is less than
    //its alloted number in the formation (IE. less than defNum, midNum or fwdNum). Also adds player to the
    //list of other players in the team sharing the position.
    public void addPlayer(Player player, int defNum, int midNum, int fwdNum) {
        if (player.getPosition().equals(Position.GOALTENDER)) {
            teamGoaltender = player;
            teamMembers.add(player);
        } else if (player.getPosition().equals(Position.DEFENDER) && teamDefenders.size() < defNum) {
            teamDefenders.add((OutfieldPlayer) player);
            teamMembers.add(player);
        } else if (player.getPosition().equals(Position.MIDFIELDER) && teamMidfielders.size() < midNum) {
            teamMidfielders.add((OutfieldPlayer) player);
            teamMembers.add(player);
        } else if (player.getPosition().equals(Position.FORWARD) && teamForwards.size() < fwdNum) {
            teamForwards.add((OutfieldPlayer) player);
            teamMembers.add(player);
        }
    }


    public void addPlayer433(Player player) {
        addPlayer(player, 4, 3, 3);
    }

    public void addPlayer442(Player player) {
        addPlayer(player, 4, 4, 2);
    }

    public void addPlayer352(Player player) {
        addPlayer(player, 3, 5, 2);
    }

    public ArrayList<Player> getTeamMembers() {
        return teamMembers;
    }

    //REQUIRES: player must already be part of the team.
    //MODIFIES: this
    //EFFECTS: removes player from the team.
    //TODO maybe exception for when requires not met
    public void removePlayer(Player player) {
        if (teamMembers.contains(player)) {
            teamMembers.remove(player);
        }
    }

    //REQUIRES: Team be made up of exactly 11 items
    //EFFECTS: Calculates overall team rating based on individual player ratings.
    public int teamRating() {
        ArrayList<Player> list = teamMembers;
        int ratings = 0;
        for (Player player : list) {
            ratings += player.calculateRating();
        }
        return round(ratings / list.size());
    }
}
