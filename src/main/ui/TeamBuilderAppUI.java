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
    private JButton button;
    private MyActionListener mal;
    private MyActionListener saveListener;
    private TeamBuilderApp teamBuilder;
    private JDesktopPane container;
    private JInternalFrame teamBuilderInterface;
    private JInternalFrame soccerImage;
    private ListOfTeams teamsSoFar;

    public TeamBuilderAppUI(int w, int h) {
        width = w;
        height = h;
        button = new JButton("Start");
        mal = new MyActionListener();
        saveListener = new MyActionListener();
        teamsSoFar = new ListOfTeams();

        setUpGUI();
    }

    public void setUpGUI() {
        container = new JDesktopPane();
        setContentPane(container);
        setSize(width, height);
        setTitle("Personal Project: Team Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(button);
        button.addActionListener(mal);
        setLocationRelativeTo(null);
        initializeMenuBar();
        initializeTeamBuilderInterface();
        initializeImage();
        setVisible(true);

        //TODO I want to add a popup window below the main manu which appears when adding or editing players,
        //Then maybe adding an icon onto the soccer field image? IDK how to do this.
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
        menuItemSave.addActionListener(saveListener);
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

                    //TODO add action for then adding and editing players, after setting name and form
                    //TODO could add the name and formation onto the image of the field, and maybe add empty circles
                    //TODO with different positions based on formation selected.
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

    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);
    }

    private class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            teamBuilder = new TeamBuilderApp();
            teamBuilder.saveWorkRoom();
        }
    }
}
