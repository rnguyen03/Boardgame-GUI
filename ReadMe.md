# Boardgame GUI

A GUI that contains tic tac toe and numerical tic tac toe with save and load. Along with built in player menu with save/load/create/inspect to keep track of name and record.

## Description

The user can run tictactoe in the terminal using the TextUI main. Or run the True main using the GameUI. The user can save, load, create and inspect
player profiles using the player profile menu. Only two players can be loaded and they can be dynamically loaded mid-game. The user can choose from
tictactoe and number tictactoe. Both of which have their respective save/load files. File fromatting should exactly follow that which is given. In the numbers
game the user can only play the numbers that are promtped after selecting a square. Both games have their respective win conditions. When inspecting a player
profile the controller must input the name of the profile they want to inspect. If the input matches a loaded profile, the record is displayed. Similarly, for saving the same mechanic applies, but instead the controller is prompted with a file browser in the assets dirctory. The controller must select the desired file to save to. For profile loading, the controller is promtped with a file select in the assets directory. The controller is to select the desired directory and if the file is valid, the player is loaded in. Only two profiles can be loaded in at a time. The first player loaded in goes first for Tic Tac Toe, but second for the number game. The opposite is true for the second player. This is so there is no clear advantage in loading in first.

## Getting Started
To get started, ensure that the dependencies such as gradle are installed. Ensure all the files from the repo have been installed correctly. After that simply follow the program execution instructions below.


### Dependencies

* Describe any prerequisites, libraries, OS version, etc., needed before installing and running your program.
Gradle and it's requirements are required. This program was developed on windows 11 and other operating systems have not been tested. The imported libraries in the files must be satisfied.


### Executing program

* How to build and run the program
* Step-by-step bullets
```
run: gradle build
    java -jar build/libs/A3.jar
```
* include the expected output

## Limitations

Game save files must be formatted correctly when loading or a new game will be started. 
Player profiles need to be formatted as "Name Wins Losses". 
When running the program, it will default to the main in GameUI, not the textUI main method. 
When saving, you can overwrite any file which means you can break of some save files. 
For example if you save game into a player profile, it will now be treated as a saved gam.
If the user loads two of the same file, or two of the same user it could possibly cause some inconsistencies.
The turn of the first player loaded is determined on a counter. So if one decides to click on numerical tic tac toe and load a player in, the player will be
loaded on the second count. This will cause the first player to be second for tic tac toe but first for numerical tic tac toe and opposite is true for the second player to load in, in this case.

## Author Information
Ryan Nguyen

## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [simple-readme] (https://gist.githubusercontent.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc/raw/d59043abbb123089ad6602aba571121b71d91d7f/README-Template.md)



