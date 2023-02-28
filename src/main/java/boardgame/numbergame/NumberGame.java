package boardgame.numbergame;

import boardgame.game.BoardGame;
import boardgame.game.Saveable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is the class that contains the logic for Number Tic Tac Toe.
 */
public class NumberGame extends BoardGame implements Saveable {

    // Declaring and initializing the variables that are used in the class.
    private int turn = 1;
    private String error = "";
    private ArrayList<String> evenList = new ArrayList<>(Arrays.asList("0","2", "4", "6", "8"));
    private ArrayList<String> oddList = new ArrayList<>(Arrays.asList("1", "3", "5", "7", "9"));


    /**
     * Constructor foro NumberGame, sets the board size
     * @param wide
     * @param high
    */
    public NumberGame(int wide, int high) {
        super(wide, high);
    }

    /**
     * If the grid is empty, and the input is odd, set the value of the grid to the input
     * 
     * @param across the x coordinate of the grid
     * @param down the row of the grid
     * @param input The value the player wants to place in the grid
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean takeTurn(int across, int down, String input) {
        if (getGrid().getValue(across, down) == " "){
            if (checkMove().equals("Odd")){
                if (isOdd(input)){
                    setValue(across, down, input);
                    return true;
                }
            } else {
                if (isEven(input)){
                    setValue(across, down, input);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * If the winner is 0, 1, or 2, return true. Otherwise, return false
     * 
     * @return The winner of the game.
     */
    @Override
    public boolean isDone() {
        if (getWinner() == 0 || getWinner() == 1 || getWinner() == 2) {
            return true;
        }
        return false;
    }

    /**
     * Returns the corresponding integer to the winner of the game.
     * 
     * @return The corresponding integer to the winner of the game.
     */
    @Override
    public int getWinner() {
        if (checkWin() && checkMove() == "Odd") {
            return 1;
        } else if (checkWin() && checkMove() == "Even") {
            return 2;
        } else if (turn > 9) {
            return 0;
        }
        return -1;
    }

    /**
     * Returns the corresponding integer to the winner of the game.
     * adjusted for when needed.
     * 
     * @return The corresponding integer to the winner of the game.
     */
    protected int getWinnerAdj() {
        if (checkWin() && checkMove() == "Odd") {
            return 2;
        } else if (checkWin() && checkMove() == "Even") {
            return 1;
        } else if (turn > 9) {
            return 0;
        }
        return -1;
    }


    /**
     * If there is a winner, return a message saying who won. Otherwise, return a message saying whose turn
     * it is
     * 
     * @return The game state message is being returned.
     */
    @Override
    public String getGameStateMessage() {
        if (getWinner() == 1) {
            return "Player Odd wins!";
        } else if (getWinner() == 2) {
            return "Player Even wins!";
        } else if (getWinner() == 0) {
            return "Tie game!";
        }
        return "Player " + checkMove() + "'s turn";
    }

    /**
     * If the turn number is odd, return "Odd", otherwise return "Even"
     * 
     * @return The method is returning a string.
     */
    protected String checkMove(){
        if (turn % 2 == 1){
            return "Odd";
        } else {
            return "Even";
        }
    }

    /**
     * It checks if the sum of the numbers in a row or column is 15, or if the sum of the numbers in
     * the diagonal is 15
     * 
     * @return The method is returning a boolean value.
     */
    private boolean checkWin(){
        int vertSum = 0;
        int vertCount = 0;
        int horSum = 0;
        int horCount = 0;
        for (int i = 1; i <= 3; i++){
            vertSum = 0;
            vertCount = 0;
            horSum = 0;
            horCount = 0;
            for (int j = 1; j <= 3; j++){
                if (getCell(i, j) != " "){
                    ++vertCount;
                    vertSum += Integer.parseInt(getCell(i, j));
                }
                if (getCell(j, i) != " "){
                    ++horCount;
                    horSum += Integer.parseInt(getCell(j, i));
                }
            }
            if ((horCount == 3 && horSum == 15)|| (vertCount == 3 && vertSum == 15)){
                return true;
            }
        }
        if (trCheck() || tlCheck()){
            return true;
        }
         return false;
     }

    /**
     * It checks if the sum of the diagonal cells is 15 and if all the diagonal cells are filled
     * 
     * @return The method is returning a boolean value.
     */
    private boolean trCheck(){
        int sum = 0;
        int diagCount = 0;
        for (int i = 1; i <= 3; i++){
            if (getCell(i, i) != " "){
                diagCount++;
                sum += Integer.parseInt(getCell(i, i));
            }
        }
        if (sum == 15 && diagCount ==  3){
            return true;
        }
        return false;
    }

    /**
     * This function checks the diagonal from the top left to the bottom right
     * 
     * @return The sum of the diagonal values.
     */
    private boolean tlCheck(){
        int sum = 0;
        int diagCount = 0;
        for (int i = 1; i <= 3; i++){
            if (getCell(i, 4 - i) != " "){
                diagCount++;
                sum += Integer.parseInt(getCell(i, 4 - i));
            }
        }
        if (sum == 15 && diagCount ==  3){
            return true;
        }
        return false;
    }

    /**
     * It checks if the string is in the oddList, and if it is, it removes it from the list and returns
     * true
     * 
     * @param cmp The string to compare to the oddList
     * @return A boolean value.
     */
    private boolean isOdd(String cmp){
        for (String s : oddList){
            if (s.equals(cmp)){
                oddList.remove(s);
                return true;
            }
        }
        return false;
    }

    /**
     * It checks if the string is in the evenList, and if it is, it removes it from the list and
     * returns true
     * 
     * @param cmpString The string to compare to the list of even strings.
     * @return A boolean value.
     */
    private boolean isEven(String cmpString){
        for (String s : evenList){
            if (s.equals(cmpString)){
                evenList.remove(s);
                return true;
            }
        }
        return false;
    }
    
    /**
     * It returns a string that contains the current state of the game
     * 
     * @return The save variable is being returned.
     */
    @Override
    public String getStringToSave() {
        String save = "";
        save += checkMove().charAt(0) + "\n";
        for (int i = 1; i <= 3; i++){
            for (int j = 1; j <= 3; j++){
                if (getCell(j, i) == " "){
                    save += ",";
                } else {
                    save += getCell(j, i);
                }
            }
            save += "\n";
        }
        return save;
    }
    
    /**
     * It takes a string, splits it into an array of strings, and then checks if the array is the right
     * length, and if each string in the array is the right length, and if each character in each
     * string is a number between 1 and 9, and if it is, it sets the value of the corresponding cell in
     * the game board to that number
     * 
     * @param toLoad The string to load
     */
    @Override
    public void loadSavedString(String toLoad) {
        String[] lines = toLoad.split("\n");
        if (lines.length != 4){
            error = "Invalid save file";
        } else {
            restoreLists();
            turn = 1;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    if (lines[i + 1].length() != 3){
                        error = "Invalid save file";
                        break;
                    } else if (Character.getNumericValue(lines[i + 1].charAt(j)) < 10
                            && Character.getNumericValue(lines[i + 1].charAt(j)) >= 0){
                        turn++;
                        setValue(j + 1, i + 1, Character.getNumericValue(lines[i + 1].charAt(j)));
                        clearNum(Character.toString(lines[i + 1].charAt(j)));
                    } else if (lines[i + 1].charAt(j) == ','){
                        setValue(j + 1, i + 1, " ");
                    } else {
                        error = "Invalid save file";
                    }
                }
            }
        }
    }

    /**
     * This function clears the number from the respective list
     */
    private void clearNum(String num){
        if (isOdd(num) && oddList.contains(num)){
            oddList.remove(num);
        } else {
            if (evenList.contains(num)){
                evenList.remove(num);
            }
        }
    }
    
    /**
     * This function increments the turn variable by one
     */
    protected void incrementTurn(){
        turn++;
    }
    
    /**
     * This function resets the turn to 1
     */
    protected void restartTurn(){
        turn = 1;
    }

    /**
     * This fuction returns the turn count.
     */
    protected int getTurn(){
        return turn;
    }

    /**
     * Resets the list of moves of the respective player to the original list
     */
    protected void restoreLists(){
        evenList = new ArrayList<>(Arrays.asList("0","2", "4", "6", "8"));
        oddList = new ArrayList<>(Arrays.asList("1", "3", "5", "7", "9"));
    }

    /**
     * This function returns the oddList
     * 
     * @return The oddList arraylist is being returned.
     */
    protected ArrayList<String> getOddList(){
        return oddList;
    }
    
    /**
     * It returns the evenList.
     * 
     * @return The evenList arraylist is being returned.
     */
    protected ArrayList<String> getEvenList(){
        return evenList;
    }
    
    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The evenList and oddList are being returned.
     */
    protected String invalidLoad(){
        return error;
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The evenList and oddList are being returned.
     */
    @Override
    public String toString() {
        return "NumberGame [evenList=" + evenList + ", oddList=" + oddList + "]";
    }
}