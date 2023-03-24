package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import ui.TeamBuilderApp;
import model.*;
import persistence.*;

public class TeamBuilderAppUI extends JFrame {

    private int width;
    private int height;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/teamsdata.json";
    private TeamBuilderApp teamBuilder;
    private JDesktopPane container;
    private JInternalFrame teamBuilderInterface;
    private JInternalFrame soccerImage;
    private JInternalFrame addPlayerEditPlayerInterface;
    private ListOfTeams teamsSoFar;

    public TeamBuilderAppUI(int w, int h) {
        width = w;
        height = h;
        teamsSoFar = new ListOfTeams();

        setUpGUI();
    }

    public void setUpGUI() {
        container = new JDesktopPane();
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

    private void initializeImage() {
//        soccerImage = new JInternalFrame();
//        soccerImage.setLayout(new BorderLayout());
//        add(soccerImage, BorderLayout.EAST);

        JLabel fieldPanel = new JLabel(); //JLabel Creation
        ImageIcon soccerField = new ImageIcon("data/soccerfield.jpg");
        Image img = soccerField.getImage();
        Image newImg = img.getScaledInstance(320, 650, Image.SCALE_SMOOTH);
        soccerField = new ImageIcon(newImg);

        fieldPanel.setIcon(soccerField); //Setting the image to be displayed as an icon
        Dimension size = fieldPanel.getPreferredSize(); //Getting the size of the image
        fieldPanel.setBounds(200, 0, size.width, size.height); //Setting the location of the image
        add(fieldPanel, BorderLayout.EAST);

//        soccerImage.pack();
//        soccerImage.setVisible(true);
    }

    private void initializeTeamBuilderInterface() {
        teamBuilderInterface = new JInternalFrame("Main Menu");
        teamBuilderInterface.setLayout(new BorderLayout());
        add(teamBuilderInterface, BorderLayout.WEST);

        addBuildingOptions();

        teamBuilderInterface.pack();
        teamBuilderInterface.setVisible(true);
    }

    //EFFECTS: adds menu bar to the team builder frame
    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemSave = new JMenuItem("Save Team");
        JMenuItem menuItemLoad = new JMenuItem("Load Teams");
        menuFile.add(menuItemSave);
        menuFile.add(menuItemLoad);
        menuBar.add(menuFile);

        JMenu menuBuildTeam = new JMenu("Build Team");
        JMenuItem menuItemAP = new JMenuItem("Add Player");
        JMenuItem menuItemEP = new JMenuItem("Edit Player");
        menuBuildTeam.add(menuItemAP);
        menuBuildTeam.add(menuItemEP);
        menuBar.add(menuBuildTeam);

        setJMenuBar(menuBar);
    }

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

    private void initializeAddPlayerEditPlayer() {
        addPlayerEditPlayerInterface = new JInternalFrame();
        addPlayerEditPlayerInterface.setLayout(new GridLayout(2, 1));
        JButton addPlayer = new JButton(new AddPlayer());
        JButton editPlayer = new JButton(new EditPlayer());
        addPlayerEditPlayerInterface.add(addPlayer);
        addPlayerEditPlayerInterface.add(editPlayer);

        addPlayerEditPlayerInterface.setLocation(0, 160);
        add(addPlayerEditPlayerInterface);
        addPlayerEditPlayerInterface.pack();
        addPlayerEditPlayerInterface.setVisible(true);
    }

    private class AddPlayer extends AbstractAction {

        AddPlayer() {
            super("Add Player to Team");
        }

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
                Team myTeam = (Team) teamsSoFar.getTeams().get(teamsSoFar.getNumberOfTeams() - 1);

                if (pos == Position.GOALTENDER) {
                    createGoalie(pos, myTeam);
                } else {
                    createPlayer(pos);
                }
            }
        }
    }

    @SuppressWarnings({"MethodLength", "checkstyle:SuppressWarnings"})
    private Goalie createGoalie(Position position, Team myTeam) {
        //Need to then have a pop up window for goalie metrics, then add to myTeam
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
        }
        return null;
        //TODO add player icon to field when added to team, create box on right side with team rating and formation
        //TODO player icon should display either jersey num or rating, in the players corresponding position.
        //TODO exception for not fully enterint player metrics?
    }

    private void addPlayerToTeam(Player player, Team myTeam) {
        if (myTeam.getFormation().equals(Formation.FourThreeThree)) {
            myTeam.addPlayer433(player);
        } else if (myTeam.getFormation().equals(Formation.FourFourTwo)) {
            myTeam.addPlayer442(player);
        } else if (myTeam.getFormation().equals(Formation.ThreeFiveTwo)) {
            myTeam.addPlayer352(player);
        }
    }

//    @SuppressWarnings({"MethodLength", "checkstyle:SuppressWarnings"})
//    private JPanel goaliePanel() {
//        JTextField playerName = new JTextField(10);
//        JSpinner playerAge = new JSpinner();
//        JTextField playerJN = new JTextField(10);
//        JTextField goalieDive = new JTextField(10);
//        JTextField goalieHandle = new JTextField(10);
//        JTextField goalieKick = new JTextField(10);
//        JTextField goalieReflex = new JTextField(10);
//        JTextField goalieSpeed = new JTextField(10);
//        JTextField goaliePositioning = new JTextField(10);
//
//        JPanel goaliePanel = new JPanel();
//        goaliePanel.setLayout(new GridLayout(9, 1));
//        goaliePanel.add(new JLabel("Please Enter Goalie name: "));
//        goaliePanel.add(playerName);
//        goaliePanel.add(new JLabel("Please enter Goalie age: "));
//        goaliePanel.add(playerAge);
//        goaliePanel.add(new JLabel("Please enter Goalie Jersey Number: "));
//        goaliePanel.add(playerJN);
//        goaliePanel.add(new JLabel("Please enter Goalie Dive Rating: "));
//        goaliePanel.add(goalieDive);
//        goaliePanel.add(new JLabel("Please enter Goalie Handle Rating: "));
//        goaliePanel.add(goalieHandle);
//        goaliePanel.add(new JLabel("Please enter Goalie Kick Rating: "));
//        goaliePanel.add(goalieKick);
//        goaliePanel.add(new JLabel("Please enter Goalie Reflex Rating: "));
//        goaliePanel.add(goalieReflex);
//        goaliePanel.add(new JLabel("Please enter Goalie Speed Rating: "));
//        goaliePanel.add(goalieSpeed);
//        goaliePanel.add(new JLabel("Please enter Goalie Positioning Rating: "));
//        goaliePanel.add(goaliePositioning);
//
//        return goaliePanel;
//    }

    private OutfieldPlayer createPlayer(Position position) {
        return null;
    }

    private class EditPlayer extends AbstractAction {

        EditPlayer() {
            super("Edit Player On Team");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class BuildTeam extends AbstractAction {

        BuildTeam() {
            super("Build Team");
        }

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
                    initializeAddPlayerEditPlayer();

                    //TODO add action for then adding and editing players, after setting name and form
                    //TODO could add the name and formation onto the image of the field, and maybe add empty circles
                    //TODO with different positions based on formation selected. Pop up window for adding and editing
                    //TODO players???
                }
            }
        }
    }

    private class ViewTeams extends AbstractAction {

        ViewTeams() {
            super("View Teams Previously Built");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class Quit extends AbstractAction {

        Quit() {
            super("Quit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class SaveTeams extends AbstractAction {

        SaveTeams() {
            super("Save Team");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ListOfTeams teamsSoFar = new ListOfTeams();
            try {
                jsonWriter.open();
                jsonWriter.write(teamsSoFar);
                jsonWriter.close();
                System.out.println("Saved " + teamsSoFar.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    private class LoadTeams extends AbstractAction {

        LoadTeams() {
            super("Load Teams");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
