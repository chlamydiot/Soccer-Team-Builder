package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.TeamBuilderApp;
import model.*;

public class TeamBuilderAppUI extends JFrame {

    private int width;
    private int height;
    private JButton button;
    private MyActionListener mal;
    private MyActionListener saveListener;
    private TeamBuilderApp teamBuilder;
    private JDesktopPane container;
    private JInternalFrame teamBuilderInterface;
    private JInternalFrame soccerImage;

    public TeamBuilderAppUI(int w, int h) {
        width = w;
        height = h;
        button = new JButton("Start");
        mal = new MyActionListener();
        saveListener = new MyActionListener();

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
        soccerImage = new JInternalFrame();
        soccerImage.setLayout(new BorderLayout());
        add(soccerImage, BorderLayout.EAST);

        JLabel label = new JLabel(); //JLabel Creation
        ImageIcon soccerField = new ImageIcon("data/soccerfield.jpg");
        Image img = soccerField.getImage();
        Image newImg = img.getScaledInstance(335, 670, Image.SCALE_SMOOTH);
        soccerField = new ImageIcon(newImg);

        label.setIcon(soccerField); //Sets the image to be displayed as an icon
        Dimension size = label.getPreferredSize(); //Gets the size of the image
        label.setBounds(200, 0, size.width, size.height); //Sets the location of the image
        add(label, BorderLayout.EAST);

        soccerImage.pack();
        soccerImage.setVisible(true);
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
