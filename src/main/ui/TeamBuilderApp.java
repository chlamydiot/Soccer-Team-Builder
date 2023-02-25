package ui;

import model.*;

import java.util.Scanner;

public class TeamBuilderApp {

    private Team team;
    private Scanner userInput;
    private Goalie gk;

    public TeamBuilderApp() {
        runApp();
    }

    private void runApp() {
        Boolean keepRunning = true;
        String command = null;
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
        menu();
        while (keepRunning) {
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

    private void buildTeam(Team myTeam) {
        System.out.println("Input ap to add a player, or ep to edit a player");
        String cmd = userInput.next();

        if (cmd.equals("ap")) {
            addPlayer(myTeam);
        } else if (cmd.equals("ep")) {
            editPlayer(myTeam);
        }
    }

    private void addPlayer(Team myTeam) {
        String position;
        Position pos;

        System.out.println("Enter your player's position: GK, DEF, MID, FWD");
        position = userInput.next().toLowerCase();

        while (myTeam.getTeamMembers().size() < 11) {
            if (myTeam.getFormation().equals(Formation.FourThreeThree)) {
                pos = processPosition(position);
                Player player = createPlayer(myTeam, pos);
                myTeam.addPlayer433(player);
            } else if (myTeam.getFormation().equals(Formation.FourFourTwo)) {
                pos = processPosition(position);
                Player player = createPlayer(myTeam, pos);
                myTeam.addPlayer442(player);
            } else if (myTeam.getFormation().equals(Formation.ThreeFiveTwo)) {
                pos = processPosition(position);
                Player player = createPlayer(myTeam, pos);
                myTeam.addPlayer352(player);
            }
            System.out.println("You've currently added " + myTeam.getTeamMembers().size() + " "
                    + "out of 11 players to your team!");
            buildTeam(myTeam);
        }
        printTeamDetails(myTeam);
        //TODO add option to either edit, or end.
    }

    private void editPlayer(Team myTeam) {
        System.out.println("These are your team members currently: " + myTeam.getMemberNames());
        System.out.println("These are their ratings: " + myTeam.getRatings(myTeam));
        System.out.println("Enter the player index which you would like to edit; 1 to 11: ");
        int indexToEdit = userInput.nextInt() - 1;

        Player playerToEdit = myTeam.getTeamMembers().get(indexToEdit);
        myTeam.removePlayer(playerToEdit);
        playerToEdit = createPlayer(myTeam, playerToEdit.getPosition());
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

    @SuppressWarnings("methodlength")
    private Player createPlayer(Team myTeam, Position pos) {
        Player player;
        if (pos.equals(Position.GOALTENDER)) {
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
            player = gk;
        } else {
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
        }
        return player;
    }

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

    private void process(String command) {
        if (command.equals("bt")) {
            createTeamName();
        }
    }

    private void menu() {
        System.out.println("\nPlease select one of the following:");
        System.out.println("\tbt --> Build Team");
        System.out.println("\tq  --> Quit");
    }

    private void printTeamDetails(Team myTeam) {
        System.out.println("Your team name is" + myTeam.getName());
        System.out.println("Your formation is: " + myTeam.getFormation());
        System.out.println("Your team members are: " + myTeam.getMemberNames());
        System.out.println("Your team rating is: " + myTeam.teamRating());
    }
}
