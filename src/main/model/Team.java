package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

import static java.lang.Math.round;

//A soccer/football team with name and team members
public class Team implements Writable {

    private String teamName;
    private ArrayList<Player> teamMembers;
    private Formation formation;
    private Player teamGoaltender;
    private ArrayList<OutfieldPlayer> teamDefenders;
    private ArrayList<OutfieldPlayer> teamMidfielders;
    private  ArrayList<OutfieldPlayer> teamForwards;

    //REQUIRES: formation is one of FourThreeThree, FourFourTwo, ThreeFiveTwo
    //EFFECTS: Constructs team with Team name set to name, Team formation set to formation, with empty lists of
    //team members, defenders, midfielders, and forwards.
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

    public void setFormation(Formation formation) {
        this.formation = formation;
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

    public void setTeamGoaltender(Player player) {
        this.teamGoaltender = player;
    }

    public void setTeamDefenders(ArrayList<OutfieldPlayer> defenders) {
        this.teamDefenders = defenders;
    }

    public void setTeamMidfielders(ArrayList<OutfieldPlayer> midfielders) {
        this.teamMidfielders = midfielders;
    }

    public void setTeamForwards(ArrayList<OutfieldPlayer> forwards) {
        this.teamForwards = forwards;
    }

    public void setTeamMembers(ArrayList<Player> members) {
        this.teamMembers = members;
    }

    public ArrayList getRatings(Team team) {
        ArrayList ratings = new ArrayList();
        for (Player player : team.getTeamMembers()) {
            ratings.add(player.calculateRating());
        }
        return ratings;
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
    //its allotted number in the formation (IE. less than defNum, midNum or fwdNum). Also adds player to the
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
    public void removePlayer(Player player) {
        if (teamMembers.contains(player)) {
            teamMembers.remove(player);
            if (player.getPosition().equals(Position.DEFENDER)) {
                teamDefenders.remove(player);
            } else if (player.getPosition().equals(Position.MIDFIELDER)) {
                teamMidfielders.remove(player);
            } else if (player.getPosition().equals(Position.FORWARD)) {
                teamForwards.remove(player);
            }
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("team name", teamName);
        json.put("team members", teamMembers);
        json.put("formation", formation);
        json.put("team goalie", teamGoaltender);
        json.put("team defenders", playersToJson(teamDefenders));
        json.put("team midfielders", playersToJson(teamMidfielders));
        json.put("team forwards", playersToJson(teamForwards));
        return json;
    }

    // EFFECTS: returns players in this team as a JSON array
    private JSONArray playersToJson(ArrayList<OutfieldPlayer> players) {
        JSONArray jsonArray = new JSONArray();

        for (OutfieldPlayer player : players) {
            jsonArray.put(player.toJson());
        }

        return jsonArray;
    }
}
