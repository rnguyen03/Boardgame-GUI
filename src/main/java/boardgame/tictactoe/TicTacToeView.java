package boardgame.tictactoe;


import boardgame.UI.PositionAwareButton;
import boardgame.UI.GameUI;
import boardgame.game.FileHandler;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.Dimension;


/**
 * It creates the UI for the game.
 */
public class TicTacToeView extends JPanel{

    // Declaring the variables that will be used in the program.
    private GameUI tttUI;
    private TicTacToeGame tttGame;
    private JPanel tttPanel;
    private JPanel gapPanel;
    private JPanel flowPanel;
    private JPanel borderPanel;
    private JPanel gridSpacer;
    private JLabel tttlabel;
    private JLabel tttStatus;
    private JButton startButton; 
    private JButton saveButton;
    private JButton loadButton;
    private PositionAwareButton[][] tttButtons;
    

    /**
     * This is the constructor for the TicTacToeView class which creates the UI for the game.
     * @param wide
     * @param tall
     * @param gameFrame
     */
    public TicTacToeView(int wide, int tall, GameUI gameFrame){
        // call the superclass constructor
        super();    
        setLayout(new BorderLayout());
        tttUI = gameFrame;

        // start game
        initializeGame(new TicTacToeGame(wide, tall));   

        borderPanel = new JPanel();
        borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.Y_AXIS));
        add(borderPanel, BorderLayout.CENTER);

        // make a new label to store messages
        borderPanel.add(createGridSpacer(), BorderLayout.CENTER); 
        gridSpacer.add(createButtonBoard(tall,wide), BorderLayout.CENTER);
        borderPanel.add(createGapPanel(), BorderLayout.SOUTH);
    }

    /**
     * This method creates a JPanel that is used to create a gap between the grid and the buttons.
     */
    private JPanel createGapPanel(){
        gapPanel = new JPanel();

        gapPanel.setLayout(new BoxLayout(gapPanel, BoxLayout.X_AXIS));
        gapPanel.add(createFlowPanel());
        
        return gapPanel;
    }

    /**
     * A method to create a wrapper pannel for the board grid, so that the grid can be centered.
     * and relatively squared.
     * @return
     */
    private JPanel createGridSpacer(){
        gridSpacer = new JPanel();
        gridSpacer.setLayout(new BorderLayout());
        gridSpacer.setPreferredSize(new Dimension(200,200)); 
        gridSpacer.setMaximumSize(new Dimension(300,300)); 
        return gridSpacer;
    }

    /**
     * This function creates a JPanel that contains a JLabel that displays the current game state, a
     * JButton that starts a new game, a JButton that saves the current game, and a JButton that loads
     * a saved game
     * 
     * @return The flowPanel is being returned.
     */
    private JPanel createFlowPanel() {
        flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout());
        tttStatus = new JLabel(playerGameStateMessage());
        tttStatus.setVisible(false);
        flowPanel.add(tttStatus, BorderLayout.WEST);
        flowPanel.add(createStartGameButton(), BorderLayout.EAST);
        flowPanel.add(createSaveGameButton(), BorderLayout.EAST);
        flowPanel.add(createLoadGameButton(), BorderLayout.EAST);
        tttlabel = new JLabel("Welcome to Tic Tac Toe");
        add(tttlabel, BorderLayout.NORTH);
        
        return flowPanel;
    }

    /**
     * This function creates a grid of buttons, each of which is linked to a coordinate on the grid
     * 
     * @param wide the number of columns in the grid
     * @param tall the number of rows in the grid
     * @return A JPanel with a grid layout of buttons.
     */
    private JPanel createButtonBoard(int wide, int tall){
        tttPanel = new JPanel();
        tttPanel.setVisible(false);

        tttButtons = new PositionAwareButton[tall][wide];
        tttPanel.setLayout(new GridLayout(wide, tall));
            for (int y=0; y<wide; y++){
                for (int x=0; x<tall; x++){ 
                    //Create tttButtons and link each button back to a coordinate on the grid
                    tttButtons[y][x] = new PositionAwareButton();
                    tttButtons[y][x].setAcross(x+1); //made the choice to be 1-based
                    tttButtons[y][x].setDown(y+1);
                    tttButtons[y][x].addActionListener(e->{
                                            userMove(e);
                                            checkGameState();
                                            });
                    tttPanel.add(tttButtons[y][x]);
                }
            }
        return tttPanel;
    }
    
    /**
     * It creates a button that when clicked, starts a new game of Tic Tac Toe
     * 
     * @return The startButton is being returned.
     */
    private JButton createStartGameButton(){
        startButton = new JButton("Start Game");
        startButton.addActionListener(e->{
                                        startButton.setVisible(false);
                                        saveButton.setVisible(true);
                                        tttPanel.setVisible(true);
                                        tttStatus.setVisible(true);
                                        startNewGame();
                                    });
        return startButton;
    }

    /**
     * This function creates a button that is invisible until the user clicks the "New Game" button
     * 
     * @return A JButton object.
     */
    private JButton createSaveGameButton(){
        saveButton = new JButton("Save Game");
        saveButton.setVisible(false);
        saveButton.addActionListener(e->saveClicked());
        return saveButton;
    }

    /**
     * This function creates a button that, when clicked, will load a game
     * 
     * @return A JButton object with the text "Load Game" and an action listener that calls the
     * loadClickEvent() method.
     */
    private JButton createLoadGameButton(){
        loadButton = new JButton("Load Game");
        loadButton.addActionListener(e->loadClickEvent());
        return loadButton;
    }

    /**
     * It loads the game from the file and updates the board
     */
    private void loadClickEvent() {
        loadClicked();
        updatePos();
    }

    /**
     * Updates the game board based off of user action
     * @param e the event that triggered the method
     */
    private void userMove(ActionEvent e){
        String player = tttGame.checkMove();
        
        PositionAwareButton pressed = ((PositionAwareButton)(e.getSource()));
        if(tttGame.takeTurn(pressed.getAcross(), pressed.getDown(), player)){
            pressed.setText(tttGame.getCell(pressed.getAcross(), pressed.getDown()));
            tttGame.incrementTurn();
        } else {
            JOptionPane.showMessageDialog(tttUI, "Invalid move, please try again", 
                                        "Invalid Move", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * When the user clicks the save button, a file selector pops up and the user can choose a file to
     * save the game to
     */
    private void saveClicked() {
        FileHandler.save(tttUI, tttGame);
    }

    /**
     * It opens a file selector, and if the user selects a file, it loads the game.
     * If a game hasn't been started, start the game.
     */
    private void loadClicked(){
        if (!tttPanel.isVisible()) {
            startButton.setVisible(false);
            saveButton.setVisible(true);
            tttPanel.setVisible(true);
            tttStatus.setVisible(true);
            startNewGame();
        }

        int choice = FileHandler.load(tttUI, tttGame);
        if (choice == JFileChooser.APPROVE_OPTION 
            && tttGame.invalidLoad() != "") {
            JOptionPane.showMessageDialog(tttUI, 
                                        "Invalid File Format, New game started!", 
                                        "Load Error", JOptionPane.ERROR_MESSAGE);
            tttGame.restartTurn();
            setPlayerIndex();
            startNewGame();
            return;
        }
    }

    /**
     * This function updates the labels on the tttButtons according to the model
     */
    private void updatePos() {
        //update the labels on the tttButtons according to the model
        for (int i = 0; i<tttGame.getHeight(); i++){
            for (int j = 0; j<tttGame.getWidth(); j++){  
                tttButtons[i][j].setText(tttGame.getCell(tttButtons[i][j].getAcross(),tttButtons[i][j].getDown())); 
            }
        }
    }

    /**
     * If the game is over, record the results and display a message. If the game is not over, and
     * there are two players, set player index and display a game status, or else just display game status.
     * The get turn command is adjusted to correct for the turn incrementing before this method is called.
     */
    private void checkGameState(){
        if (tttGame.isDone()) {
            recordResults();
            if (adjTurnCompare() && tttUI.getPlayerName() != ""
                && tttGame.getWinner() - 1 == ((tttGame.getTurn() + 1) % 2)){
                prompt(tttUI.getPlayerName() + " wins!");
            } else if (tttUI.getPlayersLoaded() == 2 
                        && tttUI.getPlayerName() != "" && tttGame.getWinner() != 0){
                prompt(tttUI.getPlayerName() + " wins!");
            } else {    
                prompt(tttGame.getGameStateMessage());
            }
            return;
        }
        // Sync player turn with turn count
        setPlayerIndex();
        this.tttStatus.setText(playerGameStateMessage());
        return;
    }

    /**
     * Return correct message for player turn.
     * 
     * @return The method is returning the game state message.
     */
    private String playerGameStateMessage(){
        if (turnCompare() && tttUI.getPlayerName() != ""){
            return "It is " + tttUI.getPlayerName() + "'s turn";
        }
        return tttGame.getGameStateMessage();
    }

    /**
     * Adds win/loss to the corresponding player's profile.
     * Adjust player turn check because it is incremented 
     * before this method is called. Similarly the winner is
     * decremented by 1 to match the player index's possible output
     * of 0 or 1.
     */
    private void recordResults() {
        if (tttGame.getWinner() > 0) {
            // If there's only one player, and the winner is loaded, add a win else add a loss
            // If there's two players loaded add a respective win and loss
            if (tttUI.getPlayersLoaded() == 1) {
                if (tttUI.isPlayerLoaded()){
                    tttUI.addWin();
                } else {
                    tttUI.switchPlayer();
                    tttUI.addLoss();
                    tttUI.switchPlayer();
                }
            } else if (tttUI.getPlayersLoaded() == 2) {
                tttUI.addWin();
                tttUI.switchPlayer();
                tttUI.addLoss();
                tttUI.switchPlayer();
            }
        }
    }

    /**
     * If the user clicks "No" on the prompt, the program will restart. If the user clicks "Yes", the
     * program will restart the game.
     * </code>
     * 
     * @param response the message to be displayed
     */
    private void prompt(String response) {
        int decision;
        decision = JOptionPane.showConfirmDialog(null, response,
            "Would you like to play again?", JOptionPane.YES_NO_OPTION);
        if (decision == JOptionPane.NO_OPTION){
            tttUI.start();
        } else {
            tttGame.restartTurn();
            setPlayerIndex();
            startNewGame();
        }
    }

    /**
     * This function sets player index based on the game's current turn.
     */
    private void setPlayerIndex(){
        if (tttGame.getTurn() % 2 == 0){
            tttUI.setPlayerTurn(0);
        } else {
            tttUI.setPlayerTurn(1);
        }
    }

    /**
     * This function compares the player index to the game turn.
     * @param controller
     */
    private boolean turnCompare(){
        if ((tttGame.getTurn() % 2) == tttUI.getPlayerTurn()
            && tttUI.getPlayersLoaded() > 0){
            return true;
        }
        return false;
    }

    /**
     * This function compares the player index to the game turn.
     * This is useful for the 1 loaded player case. Adjust the turn 
     * by 1 because it is incremented before this method is called.
     * @param controller
     */
    private boolean adjTurnCompare(){
        if (((tttGame.getTurn() + 1) % 2) == tttUI.getPlayerTurn()
            && tttUI.getPlayersLoaded() > 0){
            return true;
        }
        return false;
    }

    /**
     * This function is called by the TicTacToeGame class to pass a reference to itself to the
     * TicTacToeView class
     * 
     * @param controller The controller object that will be used to control the game.
     */
    private void initializeGame(TicTacToeGame controller){
        this.tttGame = controller;
    }

    /**
     * This function starts a new game by calling the newGame() function in the TicTacToeGame class
     */
    private void startNewGame(){
        tttGame.newGame();
        updatePos();
        tttGame.restartTurn();
        setPlayerIndex();
        this.tttStatus.setText(playerGameStateMessage());
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The toString() method is being returned.
     */
    @Override
    public String toString() {
        return "TicTacToeView [tttUI=" + tttUI + ", tttGame=" + tttGame + ", tttPanel=" + tttPanel + "]";
    }
}
