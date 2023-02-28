package boardgame.UI;

import boardgame.tictactoe.TicTacToeView;
import boardgame.numbergame.NumberView;
import boardgame.game.FileHandler;
import boardgame.game.Player;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import java.awt.Color;

/**
 * This class is the main class that creates the GUI and handles the menu bar
 */
public class GameUI extends JFrame{

    // Creating the variables that will be used in the class.
    private Player[] person = new Player[2];
    private int playerTurn = 0; //Player index
    private int loaded = 0;
    private JPanel container;
    private JLabel pLabel;
    private JMenuBar bar;
    
    /**
     * This function creates a new GameUI object and sets it to visible
     */
    public static void main(String[] args){
        GameUI mainUI = new GameUI("Assignment 3");
        mainUI.setVisible(true);
    }    
    /**
     * This is the constructor for the GameUI class. It sets the title of the window, the size of the
     * window, the location of the window, creates the menu bar, creates the container, sets the
     * default close operation, sets the layout of the container, adds the container to the window,
     * adds the button panel to the window, and calls the start function.
     * @param title The title of the window
     */
    public GameUI(String title){
        super();    
        this.setTitle(title);
        this.setPreferredSize(screenSizeFind());
        this.setLocationByPlatform(rootPaneCheckingEnabled);
        
        // Creating a menu bar and adding it to the frame.
        createInfoMenu();
        setJMenuBar(bar);
        container = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new BorderLayout());

        this.add(container, BorderLayout.CENTER);
        this.add(makeButtonPanel(),BorderLayout.EAST);
        start();
    }

    /**
     * This function creates a JPanel with a welcome message
     * 
     * @return A JPanel with a JLabel on it.
     */
    private JPanel startupMessage(){
        JPanel welcome = new JPanel();
        welcome.add(new JLabel("Weclome to Ryan's Assignment 3 Program!"));
        return welcome;
    }
    
    /**
     * It creates a JPanel, adds a JLabel to it, and returns the JPanel
     * 
     * @return A JPanel with a JLabel that displays the number of players loaded.
     */
    private JPanel numLoadedDisplay(){
        JPanel numLoaded = new JPanel();
        pLabel = new JLabel("Players loaded: " + loaded);
        numLoaded.add(pLabel);
        return numLoaded;
    }

    /**
     * It creates a JProgressBar, adds it to a JPanel, and then adds the JPanel to the JFrame.
     * </code>
     */
    public void start(){
        bar.setVisible(true);
        container.removeAll();
        container.add(numLoadedDisplay(), BorderLayout.WEST);
        container.add(startupMessage());
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }    

    /**
     * It creates a menu bar with a menu called "Player Menu" and adds four menu items to it
     */
    private void createInfoMenu(){
        bar = new JMenuBar();
        bar.setBackground(Color.LIGHT_GRAY);
        // Player Menu
        JMenu menu = new JMenu("Player Menu");
        menu.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        // Info display
        JMenuItem inspectPlayer = new JMenuItem("Inspect Player Profile");
        menu.add(inspectPlayer);
        bar.add(menu);
        inspectPlayer.addActionListener(e->displayProfile());
        // Create player profile
        JMenuItem newPlayer = new JMenuItem("New Player Profile");
        menu.add(newPlayer);
        bar.add(menu);
        newPlayer.addActionListener(e->createProfile());
        // Save player
        JMenuItem save = new JMenuItem("Save Player Profile");
        menu.add(save);
        bar.add(menu);
        save.addActionListener(e->saveProfile());
        // Load player
        JMenuItem load = new JMenuItem("Load Player Profile");
        menu.add(load);
        bar.add(menu);
        load.addActionListener(e->loadProfile());
    }

    /**
     * It gets the screen size, then it gets the width and height of the screen, then it gets the width
     * and height of the window, then it subtracts the window width and height from the screen width
     * and height, then it divides the result by 2, then it sets the location of the window to the
     * result, then it sets the size of the dimension to the result, then it returns the dimension
     * 
     * @return The method is returning the dimension of the screen.
     */
    private Dimension screenSizeFind() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int wide = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int height = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        dimension.setSize(wide, height);
        return dimension;
    }

    /**
     * This function creates a panel that contains two buttons, one for Tic Tac Toe and one for Number
     * Guessing
     * 
     * @return A JPanel with two buttons.
     */
    private JPanel makeButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(createTicTacToeButton());
        buttonPanel.add(createNumberButton());
        return buttonPanel;
    }

    /**
     * This function creates a button that, when clicked, will open a new window with a tic tac toe
     * game
     * 
     * @return A button with the text "Tic Tac Toe"
     */
    private JButton createTicTacToeButton(){
        JButton button = new JButton("Tic Tac Toe");
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.addActionListener(e->ticTacToe());
        return button;
    }

    /**
     * This function creates a button that, when clicked, will open a new window with a number 
     * tic tac toe game
     * 
     * @return A button with the text "Number Tic Tac Toe"
     */
    private JButton createNumberButton(){
        JButton button = new JButton("Number Tic Tac Toe");
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.addActionListener(e->number());
        return button;
    }

    /**
     * This function displays the player's record.
     * </code>
     */
    private void displayProfile(){
        // Showing the user a message dialog box with the player's record.
        if(loaded == 0){
            JOptionPane.showMessageDialog(this,"No players loaded...",
                                    "Error",JOptionPane.ERROR_MESSAGE);
        } else {
            // Prompt user to choose a player file to load.
            int pChoice = choosePlayer();
            if (pChoice == -1){
                return;
            }
            JOptionPane.showMessageDialog(this, person[pChoice].getUser() + ". Here is your record " 
            + person[pChoice].getWins() + " wins, " + person[pChoice].getLosses() 
            + " loses ", "Player Record", JOptionPane.INFORMATION_MESSAGE); 
        }
    }

    /**
     * If the number of players loaded is less than 2, then ask the user to enter a username, if the
     * username is not null, then create a new player, set the username, wins, and losses to 0,
     * increment the number of players loaded, and update the label
     */
    private void createProfile(){
        if (loaded < 2){
            String player = JOptionPane.showInputDialog("Enter username: ");
            // Creating a new player object and setting the user name, wins, and losses.
            boolean switched = false;
            if (person[playerTurn] != null 
                && person[playerTurn].getUser() == player){
                JOptionPane.showMessageDialog(this,"Player already exists...",
                                    "Error",JOptionPane.ERROR_MESSAGE);
            } else if (player != null) {
                if (person[playerTurn] != null) {
                    switchPlayer();
                    switched = true;
                }
                person[playerTurn] = new Player();
                person[playerTurn].setUser(player);
                person[playerTurn].setWins(0);
                person[playerTurn].setLosses(0);
                loaded++;
                pLabel.setText("Players loaded: " + loaded);
                if (switched) {
                    switchPlayer();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,"Maximum number of players reached...",
                                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It saves a player's profile to a file using the method taught in class
     */
    private void saveProfile(){
        // Checking if the loaded variable is equal to 0. If it is, it will display an error message.
        if(loaded == 0){
            JOptionPane.showMessageDialog(this,"No Players loaded...", 
                                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int pChoice = choosePlayer();
            // Checking if the player has chosen to quit saving.
            if (pChoice == -1){
                return;
            }
            FileHandler.save(this, person[pChoice]);
        }
    }

    /**
     * It asks the user to enter a username, and then it checks if that username exists in the
     * database. If it does, it returns the index of the player in the database. If it doesn't, it
     * returns -1
     * 
     * @return The index of the player in the array.
     */
    private int choosePlayer() {
        // Asking the user to enter a username and then checking if the player exists.
        String user = JOptionPane.showInputDialog("Enter username: ");
        int result = doesPlayerExist(user);
        // Checking if the player is found or not.
        if (user == null){
            return -1;
        }else if(result == -1){
            JOptionPane.showMessageDialog(this,"Player not found...",
                                    "Error",JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return result;
    }

    /**
     * It loads a profile from a file and displays the user's record
     */
    private void loadProfile(){
        int choice = -1;
        if (loaded < 2){
            boolean switched = false;
            if (person[playerTurn] != null) {
                switchPlayer();
                switched = true;
            }
            person[playerTurn] = new Player();
            choice = FileHandler.load(this, person[playerTurn]);
            if (choice == JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(this, person[playerTurn].getUser() + "! Here is your record " 
                + person[playerTurn].getWins() + " wins, " + person[playerTurn].getLosses() 
                + " loses ", "Player Record", JOptionPane.INFORMATION_MESSAGE);
                loaded++; 
                pLabel.setText("Players loaded: " + loaded);
            }
            if (switched) {
                switchPlayer();
            }
        } else {
            JOptionPane.showMessageDialog(this,"No more players can be loaded...", 
                            "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It removes the current view and replaces it with a new TicTacToeView
     */
    private void ticTacToe(){
        //bar.setVisible(false);
        container.removeAll();
        container.add(new TicTacToeView(3,3, this));
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * It removes the current view and replaces it with a new NumberView
     */
    private void number(){
        //bar.setVisible(false);
        container.removeAll();
        container.add(new NumberView(3,3, this));
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * Checks if the corresponding player exists in the database
     * 
     * @param pName The name of the player
     * @return The index of the player in the array.
     */
    private int doesPlayerExist(String pName) {
        // Checking if the person is in the array.
        if (person[0] != null && person[0].getUser() != null
            && person[0].getUser().equals(pName)) {
            return 0;
        } else if (person[1] != null && person[1].getUser() != null
                    && person[1].getUser().equals(pName)) {
            return 1;
        } else {
            return -1;
        }
    }
    
    /**
     * Used to swap the player index by setting it to the opposite of what it is.
     * I chose to have this seperate from setPlayerTurn because I wanted to be able to
     * switch to the opposite symbol without hard coding who to switch to.
     */
    public void switchPlayer(){
        if (playerTurn == 1){
            this.playerTurn = 0;
        } else if (playerTurn == 0){
            this.playerTurn = 1;
        }
    }

    /**
     * If the person array is not null, return the user name, otherwise return an empty string
     * 
     * @return The player's name.
     */ 
    public String getPlayerName(){
        if (person[playerTurn] != null){
            return person[playerTurn].getUser();
        } else {
            return "";
        }    
    }

    /**
     * If the person array is not null, return true, otherwise return false
     * @param updateTurn
     */
    public boolean isPlayerLoaded(){
        if (person[playerTurn] != null){
            return true;
        } 
        return false;
    }

    /**
     * It sets the player turn to the corresponding index
     * 
     * @param playerTurn The index of the player in the array
     */
    public void setPlayerTurn(int updateTurn){
        playerTurn = updateTurn;
    }

    /**
     * It returns the player turn
     * @return The index of the player in the array
     */
    public int getPlayerTurn(){
        return this.playerTurn;
    }

   /**
    * This function returns the number of players that have been loaded into the game
    * 
    * @return The number of players that have been loaded.
    */
    public int getPlayersLoaded(){
        return loaded;
    }

    /**
     * This function adds a win to the player's score
     */
    public void addWin(){
        person[playerTurn].addWins();
    }

    /**
     * This function adds a loss to the player's record
     */
    public void addLoss(){
        person[playerTurn].addLosses();
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The person and container objects.
     */
    @Override
    public String toString() {
        return "GameUI [person=" + person + ", container=" + container + "]";
    }
}