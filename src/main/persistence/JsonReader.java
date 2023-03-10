package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfTeams read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeam(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses listOfTeams from JSON object and returns it
    private ListOfTeams parseTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ListOfTeams listOfTeams = new ListOfTeams();
        addTeams(listOfTeams, jsonObject);
        return listOfTeams;
    }

    // MODIFIES: listOfTeams
    // EFFECTS: parses teams from JSON object and adds them to listOfTeams
    private void addTeams(ListOfTeams listOfTeams, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(listOfTeams, nextTeam);
        }
    }

    // MODIFIES: listOfTeams
    // EFFECTS: parses team from JSON object and adds it to listOfTeams
    private void addTeam(ListOfTeams listOfTeams, JSONObject jsonObject) {
        ArrayList<Player> players;

        String name = jsonObject.getString("team name");
        JSONArray teamMembers = jsonObject.getJSONArray("team members");
        players = getTeamMembers(teamMembers);
        Formation formation = Formation.valueOf(jsonObject.getString("formation"));
        Player goalie = getGoalie(players);
        ArrayList<OutfieldPlayer> teamDefenders = getDefenders(players);
        ArrayList<OutfieldPlayer> teamMidfielders = getMidfielders(players);
        ArrayList<OutfieldPlayer> teamForwards = getForwards(players);

        Team team = new Team(name, formation);
        team.setTeamMembers(players);
        team.setTeamGoaltender(goalie);
        team.setTeamDefenders(teamDefenders);
        team.setTeamMidfielders(teamMidfielders);
        team.setTeamForwards(teamForwards);

        listOfTeams.addTeamToList(team);
    }

    private ArrayList getTeamMembers(JSONArray teamMembers) {
        ArrayList players = new ArrayList<>();

        for (Object player : teamMembers) {
            JSONObject p = (JSONObject) player;
            Player teamMember = generatePlayer(p);
            players.add(teamMember);
        }
        return players;
    }

    private Player generatePlayer(JSONObject player) {
        Position pos = Position.valueOf(player.getString("position"));

        if (pos.equals(Position.GOALTENDER)) {
            return getGoalieFromSave(player);
        } else {
            return getOutfieldPlayerFromSave(pos, player);
        }
    }

    private Player getGoalieFromSave(JSONObject player) {
        String name = player.getString("name");
        int jerseyNum = player.getInt("jerseyNumber");
        int age = player.getInt("age");
        int dr = player.getInt("diveRating");
        int hr = player.getInt("handlingRating");
        int kr = player.getInt("kickRating");
        int rr = player.getInt("reflexRating");
        int sr = player.getInt("speedRating");
        int pr = player.getInt("positioningRating");
        Player p = new Goalie(name, jerseyNum, age, dr, hr, kr, rr, sr, pr);

        return p;
    }

    private Player getOutfieldPlayerFromSave(Position pos, JSONObject player) {
        String name = player.getString("name");
        int jerseyNum = player.getInt("jerseyNumber");
        int age = player.getInt("age");
        int pr = player.getInt("paceRating");
        int sr = player.getInt("shotRating");
        int passr = player.getInt("passingRating");
        int dr = player.getInt("dribblingRating");
        int physr = player.getInt("physicalityRating");
        int defr = player.getInt("defendingRating");
        Player p = new OutfieldPlayer(name, jerseyNum, age, pos, pr, sr, passr, dr, physr, defr);

        return p;
    }

    private Player getGoalie(ArrayList<Player> players) {
        Player g = null;
        for (Player p : players) {
            if (p.getPosition().equals(Position.GOALTENDER)) {
                g = p;
            }
        }
        return g;
    }

    private ArrayList<OutfieldPlayer> getDefenders(ArrayList<Player> players) {
        return getPlayersOfPosition(players, Position.DEFENDER);
    }

    private ArrayList<OutfieldPlayer> getMidfielders(ArrayList<Player> players) {
        return getPlayersOfPosition(players, Position.MIDFIELDER);
    }

    private ArrayList<OutfieldPlayer> getForwards(ArrayList<Player> players) {
        return getPlayersOfPosition(players, Position.FORWARD);
    }

    private ArrayList<OutfieldPlayer> getPlayersOfPosition(ArrayList<Player> players, Position position) {
        ArrayList playersOfPosition = new ArrayList();
        for (Player p : players) {
            if (p.getPosition().equals(position)) {
                playersOfPosition.add(p);
            }
        }
        return playersOfPosition;
    }
}