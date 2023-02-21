package model;

import java.util.ArrayList;
import model.OutfieldPlayer;
import model.Goalie;
import static java.lang.Math.round;

//A soccer/football team with name and team members
public class Team {

    private String teamName;
    private ArrayList<Player> teamMembers;
    private Formation formation;
    private Player teamGoaltender;
    private ArrayList<OutfieldPlayer> teamDefenders;
    private ArrayList<OutfieldPlayer> teamMidfielders;
    private ArrayList<OutfieldPlayer> teamForwards;
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

    public ArrayList getDefenders() {
        return teamDefenders;
    }

    public ArrayList getMidfielders() {
        return teamMidfielders;
    }

    public ArrayList getTeamForwards() {
        return teamForwards;
    }

    public ArrayList<String> getMembers() {
        ArrayList<Player> members = teamMembers;
        ArrayList<String> membernames = new ArrayList<>();
        for (Player player : members) {
            membernames.add(player.getName());
        }
        return membernames;
    }

    //REQUIRES: team cannot already have more than one Goalkeeper, four Defenders,
    // three midfielders and three forwards. team cannot already contain player
    //MODIFIES: this
    //EFFECTS: adds player to the team,
//    public void addPlayer(Player player) {
//        //TODO maybe exception for when requires not met
//        if (!teamMembers.contains(player) && teamMembers.size() < 11) {
//            teamMembers.add(player);
//        }
//    }

    public void addPlayer(Player player, int defNum, int mifNum, int fwdNum) {
        if (player.getPosition().equals(Position.GOALTENDER)) {
            teamGoaltender = player;
            teamMembers.add(player);
        } else if (player.getPosition().equals(Position.DEFENDER) && teamDefenders.size() < defNum) {
            teamDefenders.add((OutfieldPlayer) player);
            teamMembers.add(player);
        } else if (player.getPosition().equals(Position.MIDFIELDER) && teamMidfielders.size() < mifNum) {
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

    public ArrayList getTeamMembers() {
        return teamMembers;
    }

    //CAN maybe make addGoalie, addDefender, etc. methods to cap it at a specific number of players in each position.
    //Would then have one slot in the constructor for goalie, a list for defenders, a list for MF, etc.
    // Then could maybe switch between formations? Or make a new team class for 4-3-3, 4-4-2, 3-5-2?

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
