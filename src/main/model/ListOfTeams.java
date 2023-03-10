package model;

import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//A list of teams which have been built so far
public class ListOfTeams implements Writable {
    private ArrayList<Team> teamsSoFar;
    private String collectionName;

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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", collectionName);
        json.put("teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teamsSoFar) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
