package model;

import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represents a list of teams which have been built so far
public class ListOfTeams implements Writable {
    private ArrayList<Team> teamsSoFar;
    private String collectionName;

    //EFFECTS: constructs empty list of teams with label collectionName
    public ListOfTeams() {
        this.teamsSoFar = new ArrayList<Team>();
        this.collectionName = "Teams Built So Far";
    }

    public int getNumberOfTeams() {
        return teamsSoFar.size();
    }

    public void addTeamToList(Team team) {
        teamsSoFar.add(team);
    }

    public ArrayList getTeams() {
        return teamsSoFar;
    }

    public String getName() {
        return collectionName;
    }


    //EFFECTS: Returns this as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", collectionName);
        json.put("teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns things in this listOfTeams as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teamsSoFar) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
