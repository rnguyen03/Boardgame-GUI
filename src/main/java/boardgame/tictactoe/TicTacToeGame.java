package boardgame.tictactoe;

import boardgame.game.BoardGame;
import boardgame.game.Saveable;
import java.util.HashSet;

/**
 * This class is the contains the logic for Tic Tac Toe.
 */
public class TicTacToeGame extends BoardGame implements Saveable{
    private int turn = 0;
    private String error = "";

    /**
     * This is the constructor for the TicTacToeGame class which sets the board size to 3x3.
     */
    public TicTacToeGame(int wide, int high) {
        super(wide, high);
    }

    /**
     * This function takes in the coordinates of the grid and the input of the player and sets the
     * value of the grid to the input of the player
     * 
     * @param across the x coordinate of the grid
     * @param down the row of the grid
     * @param input The value that the player wants to place on the grid.
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean takeTurn(int across, int down, String input) {
        if (getGrid().getValue(across, down) == " ") {
            setValue(across, down, input);
            return true;
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
     * If the turn is 9, then the game is a draw. If X wins, then return 1. If O wins, then return 2.
     * Otherwise, return -1
     * 
     * @return The winner of the game.
     */
    @Override
    public int getWinner() {
        if (checkWin("X")) {
            return 1;
        } else if (checkWin("O")) {
            return 2;
        } else if (turn == 9) {
            return 0;
        }
        return -1;
    }

    /**
     * If there is a winner, return a message saying who won. If there is no winner, return a message
     * saying whose turn it is
     * 
     * @return The message that is being returned is the message that is being displayed on the screen.
     */
    @Override
    public String getGameStateMessage() {
        if (getWinner() == 0) {
            return "Tie game!";
        } else if (getWinner() == 1) {
            return "Player X wins!";
        } else if (getWinner() == 2) {
            return "Player O wins!";
        }
        return "Player " + checkMove() + "'s turn";
    }

    /**
     * If the turn number is even, return "X", otherwise return "O"
     * 
     * @return The method is returning a string.
     */
    protected String checkMove(){
        if (turn % 2 == 0){
            return "X";
        } else {
            return "O";
        }
    }

    /**
     * It checks if the player has won by checking if the player has three of the same symbol in a row,
     * column, or diagonal
     * 
     * @param symbol The symbol that is being checked for a win.
     * @return The method is returning a boolean value.
     */
    private boolean checkWin(String symbol){
        HashSet<String> row = new HashSet<String>();
        HashSet<String> col = new HashSet<String>();
        for (int i = 1; i <= 3; i++){
            for (int j = 1; j <= 3; j++){
                // It's adding the value of the cell at the position (i, j) to the col HashSet and the
                // value of the cell at the position (j, i) to the row HashSet.
                col.add(getCell(i, j));
                row.add(getCell(j, i));
                // It's checking if the player has three of the same symbol in a row or column.
                if ((col.size() == 1 && col.contains(symbol) || row.size() == 1 && row.contains(symbol)) && j == 3){
                    return true;
                }
            }
          // It's clearing the col and row HashSets so that they can be used again.
            col.clear();
            row.clear();
        }
        // It's checking if the player has three of the same symbol in a diagonal.
        if (getCell(1, 1) == symbol && getCell(2, 2) == symbol && getCell(3, 3) == symbol
            || getCell(1, 3) == symbol && getCell(2, 2) == symbol && getCell(3, 1) == symbol){
            return true;
        }
         return false;
    }

    /**
     * This function increments the turn variable by one
     */
    protected void incrementTurn(){
        turn++;
    }

    /**
     * This function resets the turn to 0
     */
    protected void restartTurn(){
        turn = 0;
    }

    /**
     * This fuction returns the turn count.
     */
    protected int getTurn(){
        return turn;
    }

    /**
     * This function returns a string that contains the current state of the game
     * 
     * @return The save variable is being returned.
     */
    @Override
    public String getStringToSave() {
        String save = "";
        save += checkMove() + "\n";
        for (int i = 1; i <= 3; i++){
            for (int j = 1; j <= 3; j++){
                if (getCell(j, i) == " "){
                    save += ",";
                } else if (getCell(j, i) == "X"){
                    save += "X";
                } else if (getCell(j, i) == "O"){
                    save += "O";
                }
            }
            save += "\n";
        }
        return save;
    }

    /**
     * It takes a string, splits it into an array of strings, and then checks each character in the
     * array to see if it's an X, O, or comma. If it's an X or O, it increments the turn counter and
     * sets the value of the board at that position to X or O. If it's a comma, it does nothing. If
     * it's anything else, it sets the error message to "Invalid save file"
     * 
     * @param toLoad The string that is being loaded
     */
    @Override
    public void loadSavedString(String toLoad) {
        error = "";
        String[] lines = toLoad.split("\n");
        if (lines.length != 4){
            error = "Invalid save file";
        } else {
            turn = 0;
            loadBoard(lines);
            //--turn; // It's decrementing the turn counter by one because it should start counting at 0
        }
    }

    /**
     * This function iterates through a loaded string and checks 
     * if the character is an X, O, or comma and then sets the 
     * value of the board at that position to X, O, or a space.
     * @return
     */
    protected void loadBoard(String[] lines){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (lines[i + 1].length() != 3){
                    error = "Invalid save file";
                    break;
                } else if (lines[i + 1].charAt(j) == 'X'){
                    turn++;
                    setValue(j + 1, i + 1, "X");
                } else if (lines[i + 1].charAt(j) == 'O'){
                    turn++;
                    setValue(j + 1, i + 1, "O");
                } else if (lines[i + 1].charAt(j) == ','){
                    setValue(j + 1, i + 1, " ");
                } else {
                    error = "Invalid save file";
                }
            }
        }
    }

    /**
     * This function returns the error message that is displayed when the user tries to load a file
     * that is not a valid .txt file
     * 
     * @return The error message.
     */
    protected String invalidLoad(){
        return error;
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The turn and error.
     */
    @Override
    public String toString() {
        return "TicTacToeGame [turn=" + turn + ", error=" + error + "]";
    }
}
