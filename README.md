# My Personal Project - Soccer Team Constructor

## Purpose of application and target audience:

**Purpose**

This application serves to allow the user to construct soccer teams, 
with different formations, where the user inputs the different players making up
the teams 11 players.

**Target Audience**

This application is for anyone who is a soccer fan and wants to visualize different
team options. Potential user's may also include coaches who would like to explore 
various team formations and compositions - allowing them to have a better understanding
of how the team fits together.

**Why this project is of interest to me**

As a lifelong soccer fan, I want to work on a project which I could see myself using 
for fun. I anticipate using the application myself for the purposes of my own soccer
team, to explore and put into practice different team compositions, as well as using it
to recreate some of my favourite professional teams.


## User stories:
- As a user, I want to be able to add players to my team
- As a user, I want to be able to edit players already in my team
- As a user, I want to be able to remove players from my team
- As a user, I want to be able to save and load previous team's that I've built
- As a user, I want to be able to use a few of the most popular team formations when forming my team.
- As a user, I want to be able to input player statistics/ratings when forming my team
- As a user, I want to be able to see my team's overall rating based on player ratings
- As a user, I want to be able to give my team a name

- As a user, I want to have the option to save my team to file
- As a user, I want to have the option to load previous teams from file, or pick up where I left off if the team
was uncompleted.

## Instructions for Grader:
- Required user action 1: Adding multiple Xs to a Y
  - This user action involves building a team with the "Build Team" button on the left hand side, which will add the
    constructed team to a list of teams (Adding Y to Z, where Y is the team and Z is the list of teams built so far). 
    Then using the "Add player" Button on the right hand panel, the user can add 11 players to their team (Adding X to
    Y).
- Required user action 2: Editing players added to team (Editing X in Y), or editing teams added to list of teams
  (Editing Y in Z).
    - During the team building process, instead of using the "Add Player" button, instead click "Edit Player". This will
      show the user all players on their team so far, with their corresponding ratings, and allow the user to enter the
      name of the player they would like to edit. The user can then edit the player with their preferred attributes.
    - After building the team, the user can click the "View teams Previously Built" Button on the left hand side, which
      will load a window displaying the different teams built previously. The user can choose to view team details, or
      edit any of the teams they have built previously.
- To view players added to team ("Viewing X added to Y") the user can click the "View team" button on the right hand
  panel to see the players they have added so far.
- The visual component of the project is the soccer field image rendered in. I would like to add functionality in the
  future for player icons to be added at their given location on the field when they are added to team
- To save the state of the application to file, the user can use the menu panel at the top of the application and click
  the "save teams" button, or alternatively click the "save teams" button on the left hand panel.
- To load the state of the application to file, the user can use the menu panel at the top of the application and click
  the "load teams" button, or alternatively click the "load teams" button on the left hand panel.

## Citations:
All classes in persistence packages (both in testing and model) were inspired/based on JsonSerializationDemo provided by
the UBC CPSC 210 team.

## Phase 4: Task 2
Mon Apr 10 23:19:40 PDT 2023
Added team: Liverpool to Teams Built So Far
Mon Apr 10 23:19:51 PDT 2023
Added player: Allison to team: Liverpool
Mon Apr 10 23:20:08 PDT 2023
Added player: Virgil van Dijk to team: Liverpool
Mon Apr 10 23:20:22 PDT 2023
Added player: Thiago to team: Liverpool
Mon Apr 10 23:20:37 PDT 2023
Added player: Darwin Nunez to team: Liverpool

## Phase 4: Task 3
If I had more time to work on this project, I would change the design of my player, Goalie and OutfieldPlayer classes
to minimize even somewhat redundant/similar code. I would go back and create an abstract player class which implements 
many of the methods which are similar, but not exactly the same, within the Goalie and OutfieldPlayer classes in
particular.
I would also change the implementation of my TeamBuilderAppGUI class, using many separate classes for the different
functionality within the TeamBuilderAppGUI rather than implementing all of the functionality in that single GUI class.
This would help better compartmentalize my GUI code and make it easier to follow, and hence easier to change in the
future if need be.