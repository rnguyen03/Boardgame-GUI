package boardgame.numbergame;

import boardgame.UI.PositionAwareButton;
import boardgame.UI.GameUI;
import boardgame.game.FileHandler;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

/**
 * This class is the class creates the GUI for the Number Tic Tac Toe.
 */
public class NumberView extends JPanel {

    
    // The code is declaring the variables that will be used in the program.
    private GameUI numUI;
    private NumberGame numGame;
    private JPanel numPanel;
    private JPanel gapPanel;
    private JPanel flowPanel;
    private JPanel borderPanel;
    private JPanel gridSpacer;
    private JLabel numLabel;
    private JLabel numStatus;
    private JButton startButton; 
    private JButton saveButton;
    private JButton loadButton;
    private PositionAwareButton[][] numButtons;

    /**
     * This is a constructor for the NumberView class.
     * It sents the UI, game, and fileHandler to the class.
     */
    public NumberView(int wide, int tall, GameUI gameFrame){
        // call the superclass constructor
        super();    
        setLayout(new BorderLayout());
        numUI = gameFrame;

        // start game
        initializeGame(new NumberGame(wide, tall));   

        borderPanel = new JPanel();
        borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.Y_AXIS));
        add(borderPanel, BorderLayout.CENTER);

        // make a new label to store messages
        borderPanel.add(createGridSpacer(), BorderLayout.CENTER); 
        gridSpacer.add(createButtonBoard(tall,wide), BorderLayout.CENTER);
        borderPanel.add(createGapPanel(), BorderLayout.SOUTH);
    }

    /**
     * This function creates a panel that contains a grid
     * 
     * @return A JPanel with a FlowLayout.
     */
    private JPanel createGapPanel(){
        gapPanel = new JPanel();

        gapPanel.setLayout(new BoxLayout(gapPanel, BoxLayout.X_AXIS));
        gapPanel.add(createFlowPanel());
        
        return gapPanel;
    }

   /**
    * It creates a JPanel with a BorderLayout, sets the preferred and maximum size, and returns the
    * JPanel.
    * </code>
    * 
    * @return A JPanel with a BorderLayout, a preferred size of 200x200, and a maximum size of 300x300.
    */
    private JPanel createGridSpacer(){
        gridSpacer = new JPanel();
        gridSpacer.setLayout(new BorderLayout());
        gridSpacer.setPreferredSize(new Dimension(200,200)); 
        gridSpacer.setMaximumSize(new Dimension(300,300)); 
        return gridSpacer;
    }

    /**
     * This function creates a JPanel that contains a JLabel that displays the player's game state, a
     * JButton that starts the game, a JButton that saves the game, and a JButton that loads the game
     * 
     * @return The flowPanel is being returned.
     */
    private JPanel createFlowPanel() {
        flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout());
        numStatus = new JLabel(playerGameStateMessage());
        numStatus.setVisible(false);
        flowPanel.add(numStatus, BorderLayout.WEST);
        flowPanel.add(createStartGameButton(), BorderLayout.EAST);
        flowPanel.add(createSaveGameButton(), BorderLayout.EAST);
        flowPanel.add(createLoadGameButton(), BorderLayout.EAST);
        numLabel = new JLabel("Welcome to Number Tic Tac Toe");
        add(numLabel, BorderLayout.NORTH);
        
        
        return flowPanel;
    }

    /**
     * This function creates a grid of buttons, each of which is linked to a coordinate on the grid
     * 
     * @param wide the number of columns in the grid
     * @param tall the number of rows in the grid
     * @return A JPanel with a GridLayout of PositionAwareButtons.
     */
    private JPanel createButtonBoard(int wide, int tall){
        numPanel = new JPanel();
        numPanel.setVisible(false);
        
        numButtons = new PositionAwareButton[tall][wide];
        numPanel.setLayout(new GridLayout(wide, tall));
        // code formatting is influenced by examplegui from the course website
        for (int y=0; y<wide; y++){
                for (int x=0; x<tall; x++){ 
                    //Create numButtons and link each button back to a coordinate on the grid
                    numButtons[y][x] = new PositionAwareButton();
                    numButtons[y][x].setAcross(x+1); //made the choice to be 1-based
                    numButtons[y][x].setDown(y+1);
                    numButtons[y][x].addActionListener(e->{
                                            userMove(e);
                                            checkGameState();
                                            });
                    numPanel.add(numButtons[y][x]);
                }
            }
        return numPanel;
    }

    /**
     * This function creates a button that starts a new game when clicked
     * 
     * @return The startButton is being returned.
     */
    private JButton createStartGameButton(){
        startButton = new JButton("Start Game");
        startButton.addActionListener(e->{
                                        startButton.setVisible(false);
                                        saveButton.setVisible(true);
                                        numPanel.setVisible(true);
                                        numStatus.setVisible(true);
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
     * @return The loadButton is being returned.
     */
    private JButton createLoadGameButton(){
        loadButton = new JButton("Load Game");
        loadButton.addActionListener(e->loadClickEvent());
        return loadButton;
    }

    /**
     * It loads the game and updates the position of the numbers.
     * </code>
     */
    private void loadClickEvent() {
        loadClicked();
        updatePos();
    }

    /**
     * The userMove function takes in an ActionEvent and asks the user to input a number based on the
     * current turn. If the user inputs a valid number, the number is placed in the button that was
     * pressed. If the user inputs an invalid number, the user is asked to try again.
     * </code>
     * 
     * @param e the ActionEvent that occurred
     */
    private void userMove(ActionEvent e){
        String played;
        if (numGame.checkMove() == "Odd"){
            played = JOptionPane.showInputDialog(
                "Please input one of the following integers: " + numGame.getOddList());
        } else {
            played = JOptionPane.showInputDialog(
                "Please input one of the following integers: " + numGame.getEvenList());
        }

        PositionAwareButton pressed = ((PositionAwareButton)(e.getSource()));
        if(numGame.takeTurn(pressed.getAcross(), pressed.getDown(), played)){
            pressed.setText(numGame.getCell(pressed.getAcross(), pressed.getDown()));
            numGame.incrementTurn();
        } else if (played == null){
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid move, please try again",
            "Invalid Move", JOptionPane.ERROR_MESSAGE);
        }
    }

   /**
    * It opens a file selector, and if the user selects a file, it saves the game to that file
    */
    private void saveClicked() {
        FileHandler.save(numUI, numGame);
    }

    /**
     * It opens a file selector, and if the user selects a file, it loads the game.
     * If a game hasn't been started, start the game.
     */
    private void loadClicked(){
        if (!numPanel.isVisible()) {
            startButton.setVisible(false);
            saveButton.setVisible(true);
            numPanel.setVisible(true);
            numStatus.setVisible(true);
            startNewGame();
        }

        int choice = FileHandler.load(numUI, numGame);
        if (choice == JFileChooser.APPROVE_OPTION 
            && numGame.invalidLoad() != "") {
            JOptionPane.showMessageDialog(numUI, 
                                            "Invalid File Format, New game started!", 
                                            "Load Error", JOptionPane.ERROR_MESSAGE);
            numGame.restartTurn();
            setPlayerIndex();
            startNewGame();
            return;
        }
    }

    /**
     * This function updates the labels on the numButtons according to the model
     */
    protected void updatePos(){
        //update the labels on the numButtons according to the model
        for (int i = 0; i<numGame.getHeight(); i++){
            for (int j = 0; j<numGame.getWidth(); j++){  
                numButtons[i][j].setText(numGame.getCell(numButtons[i][j].getAcross(),numButtons[i][j].getDown())); 
            }
        }
    }

    /**
     * This function checks the game state and if the game is over, it records the results and prompts the
     * winner. If the game is not over, it switches the player and updates the status message
     */
    private void checkGameState(){
        if (numGame.isDone()) {
            recordResults();
            if (adjTurnCompare() && numUI.getPlayerName() != ""
                && numGame.getWinner() - 1 == ((numGame.getTurn() + 1) % 2)){
                prompt(numUI.getPlayerName() + " wins!");
            } else if (numUI.getPlayersLoaded() == 2 
                        && numUI.getPlayerName() != "" && numGame.getWinner() != 0){
                prompt(numUI.getPlayerName() + " wins!");
            } else {    
                prompt(numGame.getGameStateMessage());
            }
            return;
        }
        // Sync player turn with turn count
        setPlayerIndex();
        this.numStatus.setText(playerGameStateMessage());
        return;
    }

    /**
     * Prints the appropriate game state message to the screen.
     * 
     * @return The method is returning the game state message.
     */
    private String playerGameStateMessage(){
        if (turnCompare() && numUI.getPlayerName() != ""){
            return "It is " + numUI.getPlayerName() + "'s turn";
        }
        return numGame.getGameStateMessage();
    }

    /**
     * Adds win/loss to the corresponding player's profile.
     * Adjust player turn check because it is incremented 
     * before this method is called. Similarly the winner is
     * decremented by 1 to match the player index's possible output
     * of 0 or 1.
     */
    private void recordResults() {
        if (numGame.getWinner() > 0) {
            // If there's only one player, and the winner is loaded, add a win else add a loss
            // If there's two players loaded add a respective win and loss
            if (numUI.getPlayersLoaded() == 1) {
                if (numUI.isPlayerLoaded()){
                    numUI.addWin();
                } else {
                    numUI.switchPlayer();
                    numUI.addLoss();
                    numUI.switchPlayer();
                }
            } else if (numUI.getPlayersLoaded() == 2) {
                numUI.addWin();
                numUI.switchPlayer();
                numUI.addLoss();
                numUI.switchPlayer();
            }
        }
    }

    /**
     * It prompts the user to play again, and if they say no, it starts a new game
     * 
     * @param response the message that will be displayed in the prompt
     */
    private void prompt(String response) {
        int decision;
        decision = JOptionPane.showConfirmDialog(null, response,
            "Would you like to play again?", JOptionPane.YES_NO_OPTION);
        if (decision == JOptionPane.NO_OPTION){
            numUI.start();
        } else {
            numGame.restartTurn();
            setPlayerIndex();
            numGame.restoreLists();
            startNewGame();
        }
    }

    /**
    * This function sets player index based on the game's current turn.
    */
    private void setPlayerIndex(){
        if (numGame.getTurn() % 2 == 0){
            numUI.setPlayerTurn(0);
        } else {
            numUI.setPlayerTurn(1);
        }
    }

    /**
     * This function compares the player index to the game turn.
     * @param controller
     */
    private boolean turnCompare(){
        if ((numGame.getTurn() % 2) == numUI.getPlayerTurn()
            && numUI.getPlayersLoaded() > 0){
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
        if (((numGame.getTurn() + 1) % 2) == numUI.getPlayerTurn()
            && numUI.getPlayersLoaded() > 0){
            return true;
        }
        return false;
    }

    /**
     * This function is used to initialize the game.
     * 
     * @param controller The NumberGame object that is the controller of the game.
     */
    private void initializeGame(NumberGame controller){
        this.numGame = controller;
    }
    

    /**
     * This function starts a new game by calling the newGame() function in the NumGame class and then
     * updates the position of the numbers on the screen
     */
    protected void startNewGame(){
        numGame.newGame();
        updatePos();
        numGame.restartTurn();
        setPlayerIndex();
        this.numStatus.setText(playerGameStateMessage());
    }

    /**
     * The function is called when the object is printed
     * 
     * @return The toString() method is being returned.
     */
    @Override
    public String toString() {
        return "NumberView [numUI=" + numUI + ", numGame=" + numGame + ", numPanel=" + numPanel + "]";
    }
}
