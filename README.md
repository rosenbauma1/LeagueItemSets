# LeagueItemSets
This is Austin Rosenbaum & Caleb Blake's submission for the  Riot API Challenge 2.0 (8/10/2015-8/31/2015).
This application will give users the ability to select their favorite pro, choose a champion match up, and generate an itemset build that they can import directly into their League of Legends config. directory.
We build this project using Apache Wicket, which is a framework that combines HTML and Java. 

Demo can be found here: www.leagueitemsets.com

####The page is made up of several HTML documents:
#####Homepage.html
    Base page design, centered content w/ grey background, and the header information about the project
#####RegionPanel.html 
    3 blocks of html to display all 3 regions - NA, EU, and KR
#####TeamPanel.html
    Block of HTML for the 5 team members, including team name
#####PlayerPanel.html 
    Includes image and name for each player - both of which are clickable
#####ModalPanel.html 
    Block of HTML to display specific player information and itemset; includes matchup, goldspent, 
    items bought, and export option

###In order to render these pages, Wicket uses .java files with the same name as the HTML.
   The following are the java files included in the project:
#####HomePage.java
    loops through the 30 pro players from our DB, gets the distinct regions, and adds these regions 
    using the RegionPanel.java
#####RegionPanel.java
    When RegionPanel is created, a model is passed. This model is the regions name. So again, we pull 
    team names from our DB given the region and create new TeamPanel objects.
#####TeamPanel.java 
    On TeamPanel creation, we loop through all players and get the players w/ appropriate team names.
    Create new PlayerPanel objects for each player
#####PlayerPanel.java
    This will give the user something to click on. For each player for each team, we create a modal 
    view that and display the modal on player click. We also use the riot api here using the method 
    updateGamesPlayed(Long summonerId). We'll explain this method on down, since it is most vital to the app.
#####ModalPanel.java
    Here we create a list of the games a player has played, dating back to 8/14/2015. We plan to
    make this filterable in the near future. A user is able to export a champion matchup and add 
    the exported json file directly to their league directory.
    
#Heartbeat of the Application
###public void updateGamesPlayed(Long summonerId)
3 API Requests per game requested

When the user selects their pro player, we begin by calling this method.
#####Structure:
    1.) Get the players match list
    2.) Get the 5 most recent games of said player
    3.) Get the match info for each of these games, including items built, gold spent, champion match up, and gold spent
    4.) Write this information to our database
    
##Additional Java Objects/Classes:
####CreateJson.java
    Generates the json file with item information, including timestamps of when the item was purchased.
####Games.java
    Bean class for the game information
####Players.java
    Bean class for the player information
    
##Resources:
####Bootstrap (http://getbootstrap.com/)

##Dependencies (found in /lib):
#####Apache Wicket Framework (http://wicket.apache.org/)
#####Spring Framework (http://spring.io/)
#####Riot API Wrapper - rithms (https://github.com/rithms/riot-api-java)
#####Gson (https://github.com/google/gson)
