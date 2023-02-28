package boardgame.tictactoe;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * It's a class that handles the text-based user interface of the game
 */
public class TextUI {

    // It's creating a new TicTacToeGame object and a new FileHandler object.
    private TicTacToeGame game = new TicTacToeGame(3, 3);
    /**
     * The main function is the entry point of the program. It creates a new TextUI object, and then
     * calls the loadPrompt function to load the prompt. It then calls the printBoard function to print
     * the board. It then enters a while loop that calls the callPlayerMove function to get the
     * player's move, and then calls the callUpdateBoard function to update the board. It then calls
     * the callPrintBoard function to print the board. It then calls the isDone function to check if
     * the game is done. If the game is done, it exits the while loop. If the game is not done, it
     * loops back to the beginning of the while loop
     */
    public static void main(String[] args){
        TextUI gameText = new TextUI();
        boolean done = false;
        int pMove = -1;
        gameText.printBoard();

        while(!done){
            pMove = gameText.callPlayerMove();
            if (pMove != 0){
                gameText.callUpdateBoard(pMove);
                gameText.game.incrementTurn();
                gameText.callPrintBoard();
                done = gameText.game.isDone();
            } else {
                gameText.callPrintBoard();
            }
        }
    }

    /**
     * It takes an input from the user, checks if it's valid, and if it's not, it asks the user to try
     * again
     * 
     * @return The input from the user.
     */
    private int playerMove(){
        int input = -1;
        boolean valid = false;
        do {
            try (Scanner read = new Scanner(System.in);){
                input = read.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println(exception.getMessage());
            }
            if (input == 0){
                valid = true;
                System.out.println("Game saved!");
            } else {
                valid = validate(input);
                if (!valid){
                    System.out.println("Invalid input, please try again.");
                }
            }
        } while (!valid);
        return input;
    }

    /**
     * It checks if the move is valid
     * 
     * @param move the move the player wants to make
     * @return The method is returning a boolean value.
     */
    private boolean validate(int move){
        switch(move) {
            case 1:
                return(game.getCell(1,1) == " ");
            case 2:
                return(game.getCell(2,1) == " ");
            case 3:
                return(game.getCell(3,1) == " ");
            case 4:
                return(game.getCell(1,2) == " ");
            case 5:
                return(game.getCell(2,2) == " ");
            case 6:
                return(game.getCell(3,2) == " ");
            case 7:
                return(game.getCell(1,3) == " ");
            case 8:
                return(game.getCell(2,3) == " ");
            case 9:
                return(game.getCell(3,3) == " ");
            default:
                return false;
        } 
    }

    /**
     * This function takes in an integer and updates the board accordingly
     * 
     * @param play the number of the square the user wants to play
     */
    private void updateBoard(int play){
        int k = 1;
        for (int i = 1; i < 4; i++){
            for (int j = 1; j < 4; j++){
                if (play == k){
                    if (game.takeTurn(j,i,game.checkMove())){
                        return;
                    }
                    System.out.println("Invalid move, please try again.");
                }
                k++;
            }
        }
    }

    /**
     * It prints the board, the game state message, and the prompt for the next move
     */
    private void printBoard(){
        System.out.println(" " + game.getCell(1,1) + " | " + game.getCell(2,1) + " | " + game.getCell(3,1));
        System.out.println("-----------");
        System.out.println(" " + game.getCell(1,2) + " | " + game.getCell(2,2) + " | " + game.getCell(3,2));
        System.out.println("-----------");
        System.out.println(" " + game.getCell(1,3) + " | " + game.getCell(2,3) + " | " + game.getCell(3,3));
        System.out.println(game.getGameStateMessage());
        System.out.println("Enter 1-9 to play, 0 to save.");
    }

    /**
     * This function calls the playerMove() function from the Player class
     * 
     * @return The return value of the playerMove() method.
     */
    public int callPlayerMove(){
        return playerMove();
    }

    /**
     * The function callUpdateBoard() is a public function that takes an integer as a parameter and
     * calls the function updateBoard() with the same integer as a parameter
     * 
     * @param play The position on the board that the player has chosen.
     */
    public void callUpdateBoard(int play){
        updateBoard(play);
    }

    /**
     * This function is used to call the printBoard function.
     */
    public void callPrintBoard(){
        printBoard();
    }

    /**
     * This function returns a string representation of the game
     * 
     * @return The game object.
     */
    @Override
    public String toString() {
        return "TextUI [game=" + game + "]";
    }
}
