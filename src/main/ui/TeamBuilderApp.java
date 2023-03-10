package ui;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;

//Soccer team builder application.
public class TeamBuilderApp {

    private static final String JSON_STORE = "./data/teamsdata.json";
    private Team team;
    private Scanner userInput;
    private Goalie gk;
    private ListOfTeams teamsSoFar;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS:
    public TeamBuilderApp() {
        teamsSoFar = new ListOfTeams();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    //REQUIRES: teamsSoFar is not empty
    //MODIFIES: this
    //EFFECTS: Returns a team previously built for user to view.
    private Team retrieveTeam(ListOfTeams teamsSoFar) {
        ArrayList listOfNames = new ArrayList();
        ArrayList<Team> teams = teamsSoFar.getTeams();
        for (Team team : teams) {
            listOfNames.add(team.getName());
        }
        System.out.println("These are your teams built so far: " + listOfNames);
        System.out.println("Please select which team you would like to view. Teams are organized"
                + "by the same order listed above (1, 2, 3 ...).");
        int selectedTeamIndex = userInput.nextInt();
        return teams.get(selectedTeamIndex - 1);
    }

    //MODIFIES: this
    //EFFECTS: Processes user input.
    private void runApp() {
        Boolean keepRunning = true;
        String command = null;
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
        while (keepRunning) {
            menu();
            command = userInput.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepRunning = false;
            } else {
                process(command);
            }
        }
        System.out.println("Bye!");
    }

    //MODIFIES: this
    //EFFECTS: Allows user to set team name
    private void createTeamName() {
        System.out.println("\nWelcome to Team Builder! Please input your team name:");
        String teamName = userInput.next();

        if (teamName != "") {
            System.out.println("Your team name is: " + teamName);
            createTeam(teamName);
        } else {
            System.out.println("Invalid name inputted, please try again");
            createTeamName();
        }
    }

    //MODIFIES: this
    //EFFECTS: Allows user to set formation for their team
    @SuppressWarnings("methodlength")
    private void createTeam(String teamName) {
        team = new Team(teamName, Formation.FourThreeThree);
        Formation form;
        System.out.println("\nNow enter your team formation! If no valid formation is"
                + " entered, 4-3-3 will be applied. Please enter one of 433, 442, 352:");
        int formationName = userInput.nextInt();

        if (formationName == 433) {
            form = Formation.FourThreeThree;
            System.out.println("Your selected formation is 4-3-3");
            team.setFormation(form);
            buildTeam(team);
        } else if (formationName == 442) {
            System.out.println("Your selected formation is 4-4-2");
            form = Formation.FourFourTwo;
            team.setFormation(form);
            buildTeam(team);
        } else if (formationName == 352) {
            form = Formation.ThreeFiveTwo;
            System.out.println("Your selected formation is 3-5-2");
            team.setFormation(form);
            buildTeam(team);
        }
    }

    //MODIFIES: this
    //EFFECTS: Allows user to add a player to myTeam or edit a player currently in myTeam.
    //if the team is full, allows the user to edit a player or end the program.
    private void buildTeam(Team myTeam) {
        while (myTeam.getTeamMembers().size() < 11) {
            System.out.println("Input ap to add a player, or ep to edit a player");
            String cmd = userInput.next();

            if (cmd.equals("ap")) {
                addPlayer(myTeam);
            } else if (cmd.equals("ep")) {
                editPlayer(myTeam);
            }
        }

        printTeamDetails(myTeam);
        System.out.println("Input ep to edit a player on your team, or f to finish");
        String editOrFinish = userInput.next();
        if (editOrFinish.equals("ep")) {
            editPlayer(myTeam);
        } else if (editOrFinish.equals(editOrFinish)) {
            teamsSoFar.addTeamToList(myTeam);
            runApp();
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds a player to myTeam.
    private void addPlayer(Team myTeam) {
        String position;
        Position pos;

        System.out.println("Enter your player's position: GK, DEF, MID, FWD");
        position = userInput.next().toLowerCase();

        if (myTeam.getFormation().equals(Formation.FourThreeThree)) {
            pos = processPosition(position);
            Player player = createPlayer(pos);
            myTeam.addPlayer433(player);
            printTeamInfo(myTeam);
        } else if (myTeam.getFormation().equals(Formation.FourFourTwo)) {
            pos = processPosition(position);
            Player player = createPlayer(pos);
            myTeam.addPlayer442(player);
            printTeamInfo(myTeam);
        } else if (myTeam.getFormation().equals(Formation.ThreeFiveTwo)) {
            pos = processPosition(position);
            Player player = createPlayer(pos);
            myTeam.addPlayer352(player);
            printTeamInfo(myTeam);
        }
        System.out.println("You've currently added " + myTeam.getTeamMembers().size() + " "
                + "out of 11 players to your team!");
        buildTeam(myTeam);
    }

    //EFFECTS: Prints out team info describing number of players in each position added to team so far.
    private void printTeamInfo(Team myTeam) {
        System.out.println("You've added " + myTeam.getDefenders().size()
                + " defenders, " + myTeam.getMidfielders().size()
                + " midfielders, and " + myTeam.getTeamForwards().size()
                + " forwards to your team.");
    }

    //MODIFIES: this
    //EFFECTS: Allows user to edit a player in myTeam.
    private void editPlayer(Team myTeam) {
        System.out.println("These are your team members currently: " + myTeam.getMemberNames());
        System.out.println("These are their ratings: " + myTeam.getRatings(myTeam));
        System.out.println("Enter the player index which you would like to edit; 1 to 11: ");
        int indexToEdit = userInput.nextInt() - 1;

        Player playerToEdit = myTeam.getTeamMembers().get(indexToEdit);
        myTeam.removePlayer(playerToEdit);
        playerToEdit = createPlayer(playerToEdit.getPosition());
        myTeam.getTeamMembers().add(indexToEdit, playerToEdit);

        if (playerToEdit.getPosition().equals(Position.GOALTENDER)) {
            myTeam.setTeamGoaltender(playerToEdit);
        } else if (playerToEdit.getPosition().equals(Position.DEFENDER)) {
            myTeam.getDefenders().add(indexToEdit, (OutfieldPlayer) playerToEdit);
        } else if (playerToEdit.getPosition().equals(Position.MIDFIELDER)) {
            myTeam.getMidfielders().add(indexToEdit, (OutfieldPlayer) playerToEdit);
        } else {
            myTeam.getTeamForwards().add(indexToEdit, (OutfieldPlayer) playerToEdit);
        }

        System.out.println("Player successfully edited!");
        buildTeam(myTeam);
    }

    //REQUIRES: pos is one of: GOALTENDER, DEFENDER, MIDFIELDER, FORWARD
    //MODIFIES: this
    //EFFECTS: Creates a player and sets their attributes based on user input.
    private Player createPlayer(Position pos) {
        Player player;
        if (pos.equals(Position.GOALTENDER)) {
            player = createGoalie();
        } else {
            player = createNonGoalie(pos);
        }
        return player;
    }

    //EFFECTS: creates a goaltender with metrics based on user input.
    private Goalie createGoalie() {
        System.out.println("Enter Goalkeeper name:");
        String name = userInput.next();
        System.out.println("Enter Goalkeeper Age:");
        int age = userInput.nextInt();
        System.out.println("Enter Player Jersey Number:");
        int jn = userInput.nextInt();
        System.out.println("Enter Goalkeeper Diving Rating:");
        int dr = userInput.nextInt();
        System.out.println("Enter Goalkeeper Handling Rating:");
        int hr = userInput.nextInt();
        System.out.println("Enter Goalkeeper Kicking Rating:");
        int kr = userInput.nextInt();
        System.out.println("Enter Goalkeeper Reflex Rating:");
        int rr = userInput.nextInt();
        System.out.println("Enter Goalkeeper Speed Rating:");
        int sr = userInput.nextInt();
        System.out.println("Enter Goalkeeper Positioning Rating:");
        int pr = userInput.nextInt();
        gk = new Goalie(name, age, jn, dr, hr, kr, rr, sr, pr);

        return gk;
    }

    //EFFECTS: creates an outfield player with metrics based on user input, and position pos.
    private OutfieldPlayer createNonGoalie(Position pos) {
        OutfieldPlayer player;

        System.out.println("Enter Player name:");
        String name = userInput.next();
        System.out.println("Enter Player Age:");
        int age = userInput.nextInt();
        System.out.println("Enter Player Jersey Number");
        int jn = userInput.nextInt();
        System.out.println("Enter Player Pace Rating:");
        int pr = userInput.nextInt();
        System.out.println("Enter Player Shot Rating:");
        int sr = userInput.nextInt();
        System.out.println("Enter Player Passing Rating:");
        int passr = userInput.nextInt();
        System.out.println("Enter Player Dribbling Rating:");
        int dr = userInput.nextInt();
        System.out.println("Enter Player Physicality Rating:");
        int physr = userInput.nextInt();
        System.out.println("Enter Player Defending Rating:");
        int defr = userInput.nextInt();

        player = new OutfieldPlayer(name, age, jn, pos, pr, sr, passr, dr, physr, defr);
        return player;
    }


    //EFFECTS: Sets player position based on user input.
    private Position processPosition(String input) {
        Position pos = null;
        if (input.equals("gk")) {
            pos = Position.GOALTENDER;
        } else if (input.equals("def")) {
            pos = Position.DEFENDER;
        } else if (input.equals("mid")) {
            pos = Position.MIDFIELDER;
        } else if (input.equals("fwd")) {
            pos = Position.FORWARD;
        }
        return pos;
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void process(String command) {
        if (command.equals("bt")) {
            createTeamName();
        } else if (command.equals("v")) {
            retrieveTeam(teamsSoFar);
        } else if (command.equals("s")) {
            saveWorkRoom();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else {
            System.out.println("Selection not valid");
        }
    }

    //EFFECTS: Displays main menu options to user.
    private void menu() {
        System.out.println("\nPlease select one of the following:");
        System.out.println("\tbt --> Build Team");
        System.out.println("\tv --> View Teams Previously Built");
        System.out.println("\tq  --> Quit");
        System.out.println("\ts --> Save Teams Build to File");
        System.out.println("\tl --> Load Teams Built From File");
    }

    //EFFECTS: Displays team information to user, including team name, formation, members and rating.
    private void printTeamDetails(Team myTeam) {
        System.out.println("Your team name is: " + myTeam.getName());
        System.out.println("Your formation is: " + myTeam.getFormation());
        System.out.println("Your team members are: " + myTeam.getMemberNames());
        System.out.println("Your team rating is: " + myTeam.teamRating());
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(teamsSoFar);
            jsonWriter.close();
            System.out.println("Saved " + teamsSoFar.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            teamsSoFar = jsonReader.read();
            System.out.println("Loaded " + teamsSoFar.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
