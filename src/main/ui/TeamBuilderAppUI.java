package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.*;
import persistence.*;

//TODO class and method comments for requires, modifies, effects

//Soccer team builder GUI application
public class TeamBuilderAppUI extends JFrame {

    private final int width;
    private final int height;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/teamsdata.json";
    private JInternalFrame teamBuilderInterface;
    private JProgressBar progressBar;
    private ListOfTeams teamsSoFar;

    //EFFECTS: Instantiates GUI with specified width and height, and instantiates a list of teams build and json
    //writer and reader instances for persistence
    public TeamBuilderAppUI(int w, int h) {
        width = w;
        height = h;
        teamsSoFar = new ListOfTeams();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setUpGUI();
    }

    //EFFECTS: Instantiates GUI window, loads in menu bar, main menu interface, and soccer field image
    public void setUpGUI() {
        JDesktopPane container = new JDesktopPane();
        setContentPane(container);
        setSize(width, height);
        setTitle("Personal Project: Team Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        initializeMenuBar();
        initializeTeamBuilderInterface();
        initializeImage();
        setVisible(true);

        //TODO I want to add a popup window below the main manu which appears when adding or editing players,
        //TODO Then maybe adding an icon onto the soccer field image? IDK how to do this.
    }

    //EFFECTS: adds menu bar to the team builder frame
    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemSave = new JMenuItem("Save Team");
        menuItemSave.addActionListener(e1 -> saveTeams());
        JMenuItem menuItemLoad = new JMenuItem("Load Teams");
        menuItemLoad.addActionListener(e1 -> loadTeams());
        menuFile.add(menuItemSave);
        menuFile.add(menuItemLoad);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);
    }

    //EFFECTS: Instantiates soccer field image
    private void initializeImage() {
        JLabel fieldPanel = new JLabel(); //JLabel Creation
        ImageIcon soccerField = new ImageIcon("data/soccerfield.jpg");
        Image img = soccerField.getImage();
        Image newImg = img.getScaledInstance(320, 650, Image.SCALE_SMOOTH);
        soccerField = new ImageIcon(newImg);

        fieldPanel.setIcon(soccerField); //Setting the image to be displayed as an icon
        Dimension size = fieldPanel.getPreferredSize(); //Getting the size of the image
        fieldPanel.setBounds(200, 0, size.width, size.height); //Setting the location of the image
        add(fieldPanel, BorderLayout.EAST);
    }

    //EFFECTS: Instantiates main menu with building options: build team, view teams, save teams, load teams and quit
    private void initializeTeamBuilderInterface() {
        teamBuilderInterface = new JInternalFrame("Main Menu");
        teamBuilderInterface.setLayout(new BorderLayout());
        add(teamBuilderInterface, BorderLayout.WEST);

        addBuildingOptions();
        //addRightPanel(Formation.valueOf("No formation!"), 0);

        teamBuilderInterface.pack();
        teamBuilderInterface.setVisible(true);
    }

    //EFFECTS: adds button panel to main menu
    private void addBuildingOptions() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5,1));
        buttonPanel.add(new JButton(new BuildTeam()));
        buttonPanel.add(new JButton(new ViewTeams()));
        buttonPanel.add(new JButton(new SaveTeams()));
        buttonPanel.add(new JButton(new LoadTeams()));
        buttonPanel.add(new JButton(new Quit()));

        teamBuilderInterface.add(buttonPanel, BorderLayout.WEST);
    }

    //REQUIRES: formation is one of FourThreeThree, FourFourTwo, ThreeFiveTwo. teamName is not an empty string
    //EFFECTS: Instantiates right panel of GUI displaying name/formation of team currently being built, as well as
    //option to edit a player on the team or view the team as of current.
    private void addRightPanel(Formation formation, String teamName) {
        JInternalFrame rightPanel = new JInternalFrame("Team in Progress");
        rightPanel.setLayout(new GridLayout(6, 1));
        JLabel nameLabel = new JLabel("Team Name: " + teamName);
        JLabel formationLabel = new JLabel("Selected Formation: " + formation);
        JButton addPlayer = new JButton(new AddPlayer());
        JButton editPlayer = new JButton(new EditPlayer());
        JButton seeTeam = new JButton(new ViewTeams());
        progressBar = new JProgressBar(SwingConstants.HORIZONTAL,0,  11);
        progressBar.setBounds(40, 40, 160, 30);
        progressBar.setValue(0);
        progressBar.setStringPainted(false);

        rightPanel.setSize(180, 180);
        rightPanel.add(progressBar);
        rightPanel.add(nameLabel);
        rightPanel.add(formationLabel);
        rightPanel.add(addPlayer);
        rightPanel.add(editPlayer);
        rightPanel.add(seeTeam);
        rightPanel.setVisible(true);
        rightPanel.pack();
        rightPanel.setLocation(520, 0);
        add(rightPanel);
    }

    //Instantiates AddPlayer button and defines event performed upon action event
    private class AddPlayer extends AbstractAction {

        //EFFECTS: Creates a button with an action to add player to team
        AddPlayer() {
            super("Add Player to Team");
        }

        //EFFECTS: Creates window prompting user to enter player position to be used for player creation
        @Override
        public void actionPerformed(ActionEvent e) {
            Position[] positions;
            positions = new Position[]{Position.GOALTENDER, Position.DEFENDER, Position.MIDFIELDER, Position.FORWARD};
            JComboBox<Position> positionOptions = new JComboBox<>(positions);

            JPanel posSelect = new JPanel();
            posSelect.add(new JLabel("Please Select Player Position"));
            posSelect.add(positionOptions);

            int posSelected = JOptionPane.showConfirmDialog(null, posSelect, "Please Enter"
                    + " Player Position", JOptionPane.OK_CANCEL_OPTION);

            if (posSelected == JOptionPane.OK_OPTION) {
                Position pos = (Position) positionOptions.getSelectedItem();
                Team myTeam = teamsSoFar.getTeams().get(teamsSoFar.getNumberOfTeams() - 1);

                if (pos == Position.GOALTENDER) {
                    createGoalie(myTeam);
                } else {
                    createPlayer(pos, myTeam);
                }
            }
        }
    }

    //MODIFIES: myTeam
    //EFFECTS: Creates an instance of a Goalie to be added to myTeam and increments progress bar
    @SuppressWarnings({"MethodLength", "checkstyle:SuppressWarnings"})
    private void createGoalie(Team myTeam) {
        JTextField playerName = new JTextField(10);
        JTextField playerAge = new JTextField(10);
        JTextField playerJN = new JTextField(10);
        JTextField goalieDive = new JTextField(10);
        JTextField goalieHandle = new JTextField(10);
        JTextField goalieKick = new JTextField(10);
        JTextField goalieReflex = new JTextField(10);
        JTextField goalieSpeed = new JTextField(10);
        JTextField goaliePositioning = new JTextField(10);

        JPanel goaliePanel = new JPanel();
        goaliePanel.setLayout(new GridLayout(9, 1));
        goaliePanel.add(new JLabel("Please Enter Goalie name: "));
        goaliePanel.add(playerName);
        goaliePanel.add(new JLabel("Please enter Goalie age: "));
        goaliePanel.add(playerAge);
        goaliePanel.add(new JLabel("Please enter Goalie Jersey Number: "));
        goaliePanel.add(playerJN);
        goaliePanel.add(new JLabel("Please enter Goalie Dive Rating: "));
        goaliePanel.add(goalieDive);
        goaliePanel.add(new JLabel("Please enter Goalie Handle Rating: "));
        goaliePanel.add(goalieHandle);
        goaliePanel.add(new JLabel("Please enter Goalie Kick Rating: "));
        goaliePanel.add(goalieKick);
        goaliePanel.add(new JLabel("Please enter Goalie Reflex Rating: "));
        goaliePanel.add(goalieReflex);
        goaliePanel.add(new JLabel("Please enter Goalie Speed Rating: "));
        goaliePanel.add(goalieSpeed);
        goaliePanel.add(new JLabel("Please enter Goalie Positioning Rating: "));
        goaliePanel.add(goaliePositioning);

        int result = JOptionPane.showConfirmDialog(null, goaliePanel, "Please Enter"
                + " Goalie Metrics", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Goalie goalie = new Goalie(playerName.getText(), Integer.parseInt(playerAge.getText()),
                    Integer.parseInt(playerJN.getText()), Integer.parseInt(goalieDive.getText()),
                    Integer.parseInt(goalieHandle.getText()), Integer.parseInt(goalieKick.getText()),
                    Integer.parseInt(goalieReflex.getText()), Integer.parseInt(goalieSpeed.getText()),
                    Integer.parseInt(goaliePositioning.getText()));
            addPlayerToTeam(goalie, myTeam);
            addToProgressBar();
        }
        //TODO add player icon to field when added to team, create box on right side with team rating and formation
        //TODO player icon should display either jersey num or rating, in the players corresponding position.
        //TODO exception for not fully enterint player metrics?
    }

    //REQUIRES: position is one of: GOALTENDER, DEFENDER, MIDFIELDER, FORWARD
    //MODIFIES: myTeam
    //EFFECTS: Creates instance of player with position, adds player to myTeam and increments progress bar
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void createPlayer(Position position, Team myTeam) {
        JTextField playerName = new JTextField(10);
        JTextField playerAge = new JTextField(10);
        JTextField playerJN = new JTextField(10);
        JTextField playerPace = new JTextField(10);
        JTextField playerShot = new JTextField(10);
        JTextField playerPassing = new JTextField(10);
        JTextField playerDribbling = new JTextField(10);
        JTextField playerPhys = new JTextField(10);
        JTextField playerDefending = new JTextField(10);

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(9, 1));
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " name: "));
        playerPanel.add(playerName);
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " age: "));
        playerPanel.add(playerAge);
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " Jersey Number: "));
        playerPanel.add(playerJN);
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " Pace Rating: "));
        playerPanel.add(playerPace);
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " Shot Rating: "));
        playerPanel.add(playerShot);
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " Passing Rating: "));
        playerPanel.add(playerPassing);
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " Dribbling Rating: "));
        playerPanel.add(playerDribbling);
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " Physicality Rating: "));
        playerPanel.add(playerPhys);
        playerPanel.add(new JLabel("Please enter " + position + "'s" + " Defending Rating: "));
        playerPanel.add(playerDefending);

        int result = JOptionPane.showConfirmDialog(null, playerPanel, "Please Enter"
                + " Player Metrics", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Player player = new OutfieldPlayer(playerName.getText(), Integer.parseInt(playerAge.getText()),
                    Integer.parseInt(playerJN.getText()), position, Integer.parseInt(playerPace.getText()),
                    Integer.parseInt(playerShot.getText()), Integer.parseInt(playerPassing.getText()),
                    Integer.parseInt(playerDribbling.getText()), Integer.parseInt(playerPhys.getText()),
                    Integer.parseInt(playerDribbling.getText()));
            addPlayerToTeam(player, myTeam);
            addToProgressBar();
        }
    }

    //MODIFIES: myTeam
    //EFFECTS: Adds player to myTeam based on myTeam formation
    private void addPlayerToTeam(Player player, Team myTeam) {
        if (myTeam.getFormation().equals(Formation.FourThreeThree)) {
            myTeam.addPlayer433(player);
        } else if (myTeam.getFormation().equals(Formation.FourFourTwo)) {
            myTeam.addPlayer442(player);
        } else if (myTeam.getFormation().equals(Formation.ThreeFiveTwo)) {
            myTeam.addPlayer352(player);
        }
    }

    //EFFECTS: Increments progress bar representing team building progress
    private void addToProgressBar() {
        while (progressBar.getValue() <= 11) {
            int current = progressBar.getValue();
            progressBar.setValue(current + 1);
            break;
        }
    }

    ////Represents EditPlayer button and defines event performed upon action event
    private class EditPlayer extends AbstractAction {

        //EFFECTS: Creates button with action to edit player on team
        EditPlayer() {
            super("Edit Player On Team");
        }

        //REQUIRES: teamsSoFar is not empty
        //MODIFIES: myTeam
        //EFFECTS: Edits specified player on current team being built
        @Override
        public void actionPerformed(ActionEvent e) {
            editPlayer(teamsSoFar.getTeams().get(teamsSoFar.getNumberOfTeams() - 1));
        }
    }

    //MODIFIES: myTeam
    //EFFECTS: Creates window showing players currently on the team with their corresponding ratings, removes specified
    //player from the team and creates new player to replace this removed player
    private void editPlayer(Team myTeam) {
        JPanel playersOnTeam = new JPanel(new GridLayout(4, 1));
        playersOnTeam.add(new JLabel("Players on Team :" + myTeam.getMemberNames()));
        playersOnTeam.add(new JLabel("Player Ratings :" + myTeam.getRatings(myTeam)));
        JTextField playerToEdit = new JTextField(10);
        playersOnTeam.add(new JLabel("Enter player name to edit: "));
        playersOnTeam.add(playerToEdit);
        JOptionPane.showConfirmDialog(null, playersOnTeam, "Player Editing", JOptionPane.OK_CANCEL_OPTION);

        String playerName = playerToEdit.getText();
        Player playerToBeEdited = null;
        Position pos = null;
        for (Player player : myTeam.getTeamMembers()) {
            if (player.getName().equals(playerName)) {
                playerToBeEdited = player;
                pos = playerToBeEdited.getPosition();
            }
        }
        myTeam.removePlayer(playerToBeEdited);
        if (pos.equals(Position.GOALTENDER)) {
            createGoalie(myTeam);
        } else {
            createPlayer(pos, myTeam);
        }
    }

    //Instantiates BuildTeam button and defines event performed upon action event
    private class BuildTeam extends AbstractAction {

        //EFFECTS: Creates a button with event to build team
        BuildTeam() {
            super("Build Team");
        }

        //EFFECTS: Creates window prompting user to enter team name and formation, then creates an instance of Team
        //with the specified name and formation. adds this team to teamsSoFar. Initialized progress bar value to 0.
        @Override
        public void actionPerformed(ActionEvent e) {
            {
                JTextField teamName = new JTextField(10);
                Formation[] formations;
                formations = new Formation[]{Formation.FourThreeThree, Formation.FourFourTwo, Formation.ThreeFiveTwo};
                JComboBox<Formation> formation = new JComboBox<>(formations);

                JPanel options = new JPanel();
                options.add(new JLabel("Team Name: "));
                options.add(teamName);
                options.add(new JLabel("Team Formation: "));
                options.add(formation);

                int result = JOptionPane.showConfirmDialog(null, options, "Please Enter Team Name"
                        + " and " + "Team Formation: ", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("Team Name Is: " + teamName.getText());
                    System.out.println("Formations Is: " + formation.getSelectedItem());
                    Team myTeam = new Team(teamName.getText(), (Formation) formation.getSelectedItem());

                    teamsSoFar.addTeamToList(myTeam);
                    addRightPanel(myTeam.getFormation(), myTeam.getName());

                    //TODO add action for then adding and editing players, after setting name and form
                    //TODO could add the name and formation onto the image of the field, and maybe add empty circles
                    //TODO with different positions based on formation selected. Pop up window for adding and editing
                    //TODO players???
                }
                progressBar.setValue(0);
            }
        }
    }

    //Instantiates ViewTeams button and defines event performed upon action event
    private class ViewTeams extends AbstractAction {

        //EFFECTS: Creates a button with action event of viewing teams previously built
        ViewTeams() {
            super("View Teams Previously Built");
        }

        //EFFECTS: Displays a window telling user no teams built yet if teamsSoFar is empty. Otherwise creates
        //a panel displaying each team in teamsSoFar with corresponding buttons for viewing team details and editing
        //players on each team in teamsSoFar.
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(teamsSoFar.getNumberOfTeams());
            if (teamsSoFar.getNumberOfTeams() == 0) {
                JOptionPane.showMessageDialog(null, "No teams built yet! "
                        + "Please make a team then try again!", "error", JOptionPane.WARNING_MESSAGE);
            } else {
                JPanel teamsMessage = new JPanel(new GridLayout(teamsSoFar.getNumberOfTeams(), 1));
                for (Team team : teamsSoFar.getTeams()) {
                    JButton eachTeam = new JButton("View Details");
                    JButton teamEditing = new JButton("Edit");
                    teamsMessage.add(new JLabel("Team : " + team.getName()));
                    teamsMessage.add(eachTeam);
                    teamsMessage.add(teamEditing);
                    eachTeam.addActionListener(e1 -> teamInfo(team));
                    teamEditing.addActionListener(e1 -> editPlayer(team));
                    //TODO could maybe have the button be labeled as "edit team", then if clicked will bring team up on
                    //TODO field and you can select players to be edited.
                }
                JOptionPane.showMessageDialog(null, teamsMessage,
                        "View Teams", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //Instantiates Quit button and defines event performed upon action event
    private static class Quit extends AbstractAction {

        //EFFECTS: Creates a button with event of quitting application
        Quit() {
            super("Quit");
        }

        //EFFECTS: Quits and closes application
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    //Instantiates SaveTeams button and defines event performed upon action event
    private class SaveTeams extends AbstractAction {

        //EFFECTS: Creates a button with the event of saving teams built to file
        SaveTeams() {
            super("Save Teams");
        }

        //EFFECTS: Saves teams to json file
        @Override
        public void actionPerformed(ActionEvent e) {
            saveTeams();
        }
    }

    //EFFECTS: saves teams to file
    private void saveTeams() {
        try {
            jsonWriter.open();
            jsonWriter.write(teamsSoFar);
            jsonWriter.close();
            System.out.println("Saved " + teamsSoFar.getName() + " to " + JSON_STORE);
            JOptionPane.showMessageDialog(null, "Saved " + teamsSoFar.getName()
                    + " to " + JSON_STORE, null, JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //Instantiates LoadTeams button and defines event performed upon action event
    private class LoadTeams extends AbstractAction {

        //EFFECTS: Creates a button with event of loading teams from file
        LoadTeams() {
            super("Load Teams");
        }

        //EFFECTS: loads teams from file
        @Override
        public void actionPerformed(ActionEvent e) {
            loadTeams();
        }
    }

    //EFFECTS: loads teams from file
    private void loadTeams() {
        try {
            teamsSoFar = jsonReader.read();
            System.out.println("Loaded " + teamsSoFar.getName() + " from " + JSON_STORE);
            JOptionPane.showMessageDialog(null, "Loaded " + teamsSoFar.getName()
                    + " from " + JSON_STORE, null, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException exception) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: Creates window displaying the following information pertaining to myTeam: name, formation, rating
    //goaltender, names of defenders, names of midfielders, names of forwards
    private void teamInfo(Team myTeam) {
        JPanel teamInfo = new JPanel(new GridLayout(7, 1));
        teamInfo.add(new JLabel("Team name: " + myTeam.getName()));
        teamInfo.add(new JLabel("Team formation: " + myTeam.getFormation()));
        teamInfo.add(new JLabel("Team rating: " + myTeam.teamRating()));
        teamInfo.add(new JLabel("Team goaltender: " + myTeam.getGoaltender().getName()));
        teamInfo.add(new JLabel("Team defenders: " + getNames(myTeam.getDefenders())));
        teamInfo.add(new JLabel("Team midfielders: " + getNames(myTeam.getMidfielders())));
        teamInfo.add(new JLabel("Team forwards: " + getNames(myTeam.getTeamForwards())));

        JOptionPane.showMessageDialog(null, teamInfo, myTeam.getName(), JOptionPane.INFORMATION_MESSAGE);
    }

    //EFFECTS: Returns an array list with names of each player in players
    private ArrayList<String> getNames(ArrayList<OutfieldPlayer> players) {
        ArrayList<String> playerNames = new ArrayList<>();
        for (OutfieldPlayer player : players) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }
}
